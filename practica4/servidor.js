/* ------------------------------------------------------------------------------
 * includes y variables
**/
var http = require("http");
var express = require("express");
var socketio = require("socket.io");
var MongoClient = require('mongodb').MongoClient;
var nodemailer = require("nodemailer");

var lib = require("./lib_servidor");

var urlMongo = "mongodb://localhost:27017/";
var db_name = "domotica";
var collection_name = "eventsLog";

var sensores = {
    Temperatura:"25",
    Luminosidad:"30",
    Aire:"Apagado",
    Persiana:"Cerrada",
    Ventana:"Cerrada"
};

var transporter = nodemailer.createTransport({

    service: 'gmail',
    auth: {
        user: 'practicaDomoticaDSD@gmail.com',
        pass: 'carloscorts'
    }
});
var mail_options = {
    from: 'practicaDomoticaDSD@gmail.com',
    to: 'carloscorts@correo.ugr.es',
    subject: 'Alarmas Domotica',
    text: ''
};
/* ------------------------------------------------------------------------------
 * Configuración del servidor
**/
var app = express();
var httpServer = http.createServer(app);

// Si la url es http://localhost:8080/, se sirve el archivo cliente.html
app.get('/', (request, response) => {

    response.sendFile(__dirname + '/cliente.html');
});

// Necesario para incluir la hoja de estilos cliente.css
app.use(express.static(__dirname));
/* ------------------------------------------------------------------------------
 * Configuración del cliente para mongodb
 * Inicialización del servidor
 * Configuración socket.io
**/
MongoClient.connect(urlMongo, {useUnifiedTopology: true}, function(err_connect, db) {

    if (err_connect) throw err_connect;

    // El servidor comienza a escuchar en el puerto 8080
    httpServer.listen(8080, () => {

        console.log("Servidor Domótica iniciado ...");
    });

    var io = socketio(httpServer);

    var db_domotica = db.db(db_name);
    var eventsLogCollection = db_domotica.collection(collection_name);

    io.on('connection', function (socket) {

        socket.emit('load-estado', sensores);

        socket.on('logEvent', function (event) {

            lib.updateSensores(sensores, event);

            lib.logEvent(eventsLogCollection, event);

            io.emit('update-estado', event);
            io.emit('comprobar', {event, sensores});
        });

        socket.on('alarma', function (msg) {

            socket.broadcast.emit('alarma', msg);

            mail_options.text = msg;
            transporter.sendMail(mail_options, function (error_send, info) {

                if (error_send) console.log(error_send);
                else console.log("Email enviado. " + info.response);
            });
        });

        socket.on('cerrar-persiana', function () {

            sensores.Persiana = "Cerrada";

            var event = {parametro: "Persiana", valorNuevo: "Cerrada", fecha: new Date(), trigger: "Agente"};

            lib.logEvent(eventsLogCollection, event);

            io.emit('update-estado', {parametro:"Persiana", valorNuevo:"Cerrada"});
        });
    });
});