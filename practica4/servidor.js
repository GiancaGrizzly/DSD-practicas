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

// Se habilita el uso de los archivos en /static
// Me daba error -≥ No se puede cargar la hoja de estilos cliente.css porque su tipo
// MIME, text/plain, no es text/css
app.use(express.static(__dirname));

/* ------------------------------------------------------------------------------
 * Configuración del cliente para mongodb
 * Inicialización del servidor
 * Configuración socket.io
**/

var sensores = {Temperatura:"20", Luminosidad:"45", Aire:"Off", Persiana:"Off"};

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

            lib.logEvent(eventsLogCollection, event);

            switch (event.parametro) {

                case "Temperatura":

                    sensores.Temperatura = event.valorNuevo;
                    break;

                case "Luminosidad":

                    sensores.Luminosidad = event.valorNuevo;
                    break;

                case "Aire":

                    sensores.Aire = event.valorNuevo;
                    break;

                case "Persiana":

                    sensores.Persiana = event.valorNuevo;
                    break;

                default:
                    break;
            }

            io.emit('update-estado', event);
        });
    });

    
});