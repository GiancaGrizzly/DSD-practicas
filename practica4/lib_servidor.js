/* ------------------------------------------------------------------------------
 * Se registra el evento <event> en la colección <collection>
**/
function logEvent(collection, event) {

    collection.insertOne(event, function (err_insert, res) {

        if (err_insert) throw err_insert;
        // console.log("New event inserted on the database...");
    });
}
/* ------------------------------------------------------------------------------
 * Se actualiza el sensor modificado por el evento <event>
**/
function updateSensores(sensores, event) {

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

        case "Ventana":

            sensores.Ventana = event.valorNuevo;
            break;

        default:
            break;
    }
}
/* ------------------------------------------------------------------------------
 * Se exportan funciones
**/
module.exports = { logEvent, updateSensores };