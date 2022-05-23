/* ------------------------------------------------------------------------------
 * includes
**/

var http = require("http");
var express = require("express");
var socketio = require("socket.io");
var MongoClient = require('mongodb').MongoClient;

var lib = require("./lib_servidor");

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
// Me daba error -≥ No se puede cargar la hoja de estilos cliente.css porque su tipo
// MIME, text/plain, no es text/css
app.use(express.static(__dirname));

/* ------------------------------------------------------------------------------
 * Configuración del cliente para mongodb
 * Inicialización del servidor
 * Configuración socket.io
**/

var sensores = {Temperatura:"25", Luminosidad:"30", Aire:"Apagado", Persiana:"Cerrada", Ventana:"Cerrada"};
var urlMongo = "mongodb://localhost:27017/";

MongoClient.connect(urlMongo, {useUnifiedTopology: true}, function(err_connect, db) {

    if (err_connect) throw err_connect;

    // El servidor comienza a escuchar en el puerto 8080
    httpServer.listen(8080, () => {

        console.log("Servidor Domótica iniciado ...");
    });

    var io = socketio(httpServer);

    var db_domotica = db.db("domotica");
    var eventsLogCollection = db_domotica.collection("eventsLog");

    io.on('connection', function (socket) {

        socket.emit('load-estado', sensores);

        socket.on('logEvent', function (event) {

            io.emit('comprobar', {event, sensores});

            lib.logEvent(eventsLogCollection, event);

            lib.updateSensores(sensores, event);

            io.emit('update-estado', event);
        });

        socket.on('cerrar-persiana', function () {

            sensores.Persiana = "Cerrada";

            var event = {parametro: "Persiana", valorNuevo: "Cerrada", fecha: new Date(), trigger: "Agente"};

            lib.logEvent(eventsLogCollection, event);

            io.emit('update-estado', {parametro:"Persiana", valorNuevo:"Cerrada"});
        });

        socket.on('alarma', function (msg) {

            socket.broadcast.emit('alarma', msg);
        });
    });

    
});