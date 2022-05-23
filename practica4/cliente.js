/* ------------------------------------------------------------------------------
 * Carga el estado de los sensores
 * Función que se realiza ante el evento 'load-estado'
**/
function loadEstado(sensores) {

    for (const sensoresKey in sensores) {

        document.getElementById(sensoresKey).innerHTML = sensores[sensoresKey];
    }
}
/* ------------------------------------------------------------------------------
 * Prepara los campos del evento para insertarlo en la base de datos y emite el
 * evento para que el servidor proceda con el registro
 * Función que se realiza al pulsar el botón de actualizar medida
**/
function update(param, valor) {

    socket.emit('logEvent', {parametro:param, valorNuevo:valor, fecha:new Date(), trigger:"Usuario"});
}
/* ------------------------------------------------------------------------------
 * Actualiza el valor mostrado en <span></span> correspondiente al evento <event>
 * Función que se realiza ante el evento 'update-estado'
**/
function updateEstado(event) {

    var span = document.getElementById(event.parametro);
    span.innerHTML = event.valorNuevo;
}
/* ------------------------------------------------------------------------------
 * Crea un nuevo item para listar en el post de alarmas
 * Función que se realiza ante el evento 'alarma'
**/
function createAlarmMessage(msg) {

    var f = new Date();
    var fecha = f.getDay() + "/" + f.getMonth() + "/" + f.getFullYear() + "  " + f.getHours() + ":" + f.getMinutes();

    var alarma =document.createElement('li');
    alarma.innerHTML = msg + " " + fecha;

    document.getElementById("alarmas").appendChild(alarma);
}