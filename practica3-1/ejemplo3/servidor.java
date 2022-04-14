// Fichero: servidor.java
// Código del servidor

package ejemplo3;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;


public class servidor
{
    public static void main(String[] args)
    {
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // Crea una instancia del contador
            // System.setProperty("java.rmi.server.hostname", "192.168.1.107");
            Registry reg = LocateRegistry.createRegistry(1099);
            contador micontador = new contador();
            Naming.rebind("micontador", micontador);

            // suma = 0;

            System.out.println("Servidor RemoteException | MalformedURLExceptioner preparado");
        }
        catch (RemoteException | MalformedURLException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
