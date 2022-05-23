/* ------------------------------------------------------------------------------
 * Umbrales
**/
const temperatura_MIN = 20;
const temperatura_MAX = 35;
const luminosidad_MIN = 15;
const luminosidad_MAX = 45;
/* ------------------------------------------------------------------------------
 * Funciones del agente para las comprobaciones
**/
function comprobar_umbrales(variable, sensores) {

    switch (variable) {

        case "Temperatura":

            if (Number(sensores.Temperatura) < temperatura_MIN) {

                socket.emit('alarma', "Temperatura por debajo del umbral.");
            }
            else if (Number(sensores.Temperatura) > temperatura_MAX) {

                socket.emit('alarma', "Temperatura por encima del umbral.");
            }
            break;

        case "Luminosidad":

            if (Number(sensores.Luminosidad) < luminosidad_MIN) {

                socket.emit('alarma', "Luminosidad por debajo del umbral.");
            }
            else if (Number(sensores.Luminosidad) > luminosidad_MAX) {

                socket.emit('alarma', "Luminosidad por encima del umbral.");
            }
            break;
    }

    if (Number(sensores.Temperatura) > temperatura_MAX && Number(sensores.Luminosidad) > luminosidad_MAX) {

        socket.emit('alarma', "Temperatura y luminosidad por encima del umbral -> Cerrando persiana.")
        socket.emit('cerrar-persiana');
    }
}

function comprobar_eventos_complejos(sensores) {

    if (sensores.Aire == "Encendido" && sensores.Ventana == "Abierta") {

        socket.emit('alarma', "Cuidado! Aire acondicionado encendido y ventana abierta.");
    }
}
/* ------------------------------------------------------------------------------
 * El agente se conecta al servidor
**/
var io = require("socket.io-client");
var socket = io("http://localhost:8080/");
/* ------------------------------------------------------------------------------
 * Se definen los eventos a los que el agente escucha
**/
socket.on('comprobar', function (data) {

    switch (data.event.parametro) {

        case "Temperatura":

            comprobar_umbrales("Temperatura", data.sensores);
            break;

        case "Luminosidad":

            comprobar_umbrales("Luminosidad", data.sensores);
            break;

        case "Aire":

            comprobar_eventos_complejos(data.sensores);
            break;

        case "Ventana":

            comprobar_eventos_complejos(data.sensores);
            break;

        default:
            break;
    }
});

console.log("Agente iniciado ...");