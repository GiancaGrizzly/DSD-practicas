/* ------------------------------------------------------------------------------
 * Umbrales
**/
const temperatura_MIN = 20;
const temperatura_MAX = 35;
const luminosidad_MIN = 15;
const luminosidad_MAX = 45;
/* ------------------------------------------------------------------------------
 * Funciones del agente para comprobar
**/
function comprobar_umbrales(variable, sensores) {

    console.log("Agente ...");

    switch (variable) {

        case "Temperatura":

            console.log("En el switch ...");

            if (sensores.Temperatura < temperatura_MIN) {

                io.emit('alarma', "Temperatura por debajo del umbral.");
            }
            else if (sensores.Temperatura > temperatura_MAX) {

                io.emit('alarma', "Temperatura por encima del umbral.");
            }
            break;

        case "Luminosidad":

            console.log("En el switch ...");

            if (sensores.Luminosidad < luminosidad_MIN) {

                io.emit('alarma', "Luminosidad por debajo del umbral.");
            }
            else if (sensores.Luminosidad > luminosidad_MAX) {

                io.emit('alarma', "Luminosidad por encima del umbral.");
            }
            break;
    }

    if (sensores.Temperatura > temperatura_MAX && sensores.Luminosidad > luminosidad_MAX) {

        io.emit('alarma', "Temperatura y luminosidad por encima de sus umbrales -> Cerrando persiana ...")
        io.emit('cerrar-persiana');
    }
}

function comprobar_eventos_complejos(sensores) {


}