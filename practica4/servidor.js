/* ------------------------------------------------------------------------------
 * includes
**/

var http = require("http");
var express = require("express");
var socketio = require("socket.io");
var MongoClient = require('mongodb').MongoClient;

/* ------------------------------------------------------------------------------
 * Configuración del servidor
**/

var app = express();
var httpServer = http.createServer(app);

// Si la url es http://localhost:8080/, se sirve el archivo index.html
app.get('/', (request, response) => {

    response.sendFile(__dirname + '/index.html');
});

// Se habilita el uso de los archivos en /static
// Me daba error -≥ No se puede cargar la hoja de estilos index.css porque su tipo
// MIME, text/plain, no es text/css
app.use(express.static(__dirname));

/* ------------------------------------------------------------------------------
 * Configuración del cliente para mongodb
 * Inicialización del servidor
 * Configuración socket.io
**/

var urlMongo = "mongodb://localhost:27017/";
var io = socketio(httpServer);
var connectionEvent = function(socket) {

    console.log("Gianca, tiene un nuevo usuario!");

    socket.on('chat message', function(msg) {

        io.emit('chat message', msg);
    });

    socket.on('disconnect', function() {

        console.log("Gianca, se te ha escapado una escapatraña!");
    })
};

MongoClient.connect(urlMongo, {useUnifiedTopology: true}, function(err, db) {

    // El servidor comienza a escuchar en el puerto 8080
    httpServer.listen(8080, () => {

        console.log("Servidor Domótica iniciado ...");
    });

    io.on('connection', connectionEvent);
    
});