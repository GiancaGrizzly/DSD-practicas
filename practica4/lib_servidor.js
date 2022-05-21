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
 * Se exportan funciones
**/
module.exports = { logEvent };