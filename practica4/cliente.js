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

    socket.emit('logEvent', {parametro:param, valorNuevo:valor, fecha:new Date(), trigger:"Usuario"});
}
/* ------------------------------------------------------------------------------
 * Se actualiza el valor mostrado en <span></span> correspondiente al evento <event>
**/
function updateEstado(event) {

    var span = document.getElementById(event.parametro);
    span.innerHTML = event.valorNuevo;
}
/* ------------------------------------------------------------------------------
 * Se crea un nuevo item para listar en el post de alarmas
**/
function createAlarmMessage(msg) {

    var f = new Date();
    var fecha = f.getDay() + "/" + f.getMonth() + "/" + f.getFullYear() + "  " + f.getHours() + ":" + f.getMinutes();

    var alarma =document.createElement('li');
    alarma.innerHTML = msg + " " + fecha;

    document.getElementById("alarmas").appendChild(alarma);
}