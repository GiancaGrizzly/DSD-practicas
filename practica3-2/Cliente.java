// Código del cliente




public class Cliente
{
    public static void main(String[] args)
    {
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        /* Interfaz por linea de comandos
         * Bienvenido al servicio de donaciones. Introduzca el numero de la
         * accion que desea realizar
         * 1. Registrarse
         * 2. Donar
         * 3. Consultar total donado
         * 
         * Opcion 1. Registrarse
         * Se ejecuta la funcion Registrar(id) en el servidor o replica principal
         * solicitandole un identificador por linea de comandos
         * En caso de que ya exista un cliente con ese identificador, se le
         * informara y se procedera como sea conveniente (continuar o salir)
         * En caso de que el identificador este libre, se registrara correctamente
         * en la replica que le toque
         * 
         * Opcion 2. 
         * Si es la primera accion que va a realizar el cliente durante esta sesion,
         * se pide al cliente que se identifique con su identificador, momento en
         * el que se carga el objeto replica que este cliente tenga asignado
         * Si no es la primera accion que realiza, ya se ha identificado previamente
         * y por tanto no serian necesarios estos pasos
         * A partir de ahi, se ejecuta la funcion Donar(donacion) en la replica que
         * le corresponda, solicitandole por linea de comandos la cantidad que
         * desea donar
         * 
         * Opcion 3. Consultar total donado
         * Si es la primera accion que va a realizar el cliente durante esta sesion,
         * se pide al cliente que se identifique con su identificador, momento en
         * el que se carga el objeto replica que este cliente tenga asignado
         * Si no es la primera accion que realiza, ya se ha identificado previamente
         * y por tanto no serian necesarios estos pasos
         * A partir de ahi, se ejecuta la funcion TotalDonado() en la replica que
         * le corresponda
         */

        

        // try {    
        // }
        // catch (RemoteException e) {
        // }
    }    
}
