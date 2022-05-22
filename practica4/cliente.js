/* ------------------------------------------------------------------------------
 * Se registra el evento <event> en la colección <collection>
**/
function loadEstado(sensores) {

    for (const sensoresKey in sensores) {

        document.getElementById(sensoresKey).innerHTML = sensores[sensoresKey];
    }
}
/* ------------------------------------------------------------------------------
 * Se preparan los campos del evento para insertarlo en la base de datos y se
 * emite el evento para que el servidor proceda
**/
function update(param, valor) {

    var serviceURL = document.URL;
    var socket = io.connect(serviceURL);

    socket.emit('logEvent', {parametro:param, valorNuevo:valor, fecha:new Date(), trigger:"Usuario"});
}
/* ------------------------------------------------------------------------------
 * Se actualiza el valor mostrado en <span></span> correspondiente al evento <event>
**/
function updateEstado(event) {

    var span = document.getElementById(event.parametro);
    span.innerHTML = event.valorNuevo;
}