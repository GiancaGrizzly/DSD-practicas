// Código del servidor

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;


public class Servidor
{
    public static void main(String[] args)
    {
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        try {
            // No hay control de errores porque el servidor lo lanza un admin (yo)
            int nreplicas = Integer.parseInt(args[0]);

            System.out.println("\nCreando réplicas y hebras ...");

            ArrayList<Replica> replicas = new ArrayList<Replica>();
            ArrayList<Thread> replicas_thread = new ArrayList<Thread>();

            for (int i=0; i<nreplicas; i++) {

                replicas.add(new Replica());

                replicas_thread.add(new Thread(replicas.get(i)));
            }

            System.out.println("\nRéplicas y hebras creadas con éxito.");


            System.out.println("\nNotificando a cada réplica la existencia de las demás ...");

            for (Replica r : replicas) {
                
                r.SetReplicas(replicas);
            }
            
            System.out.println("\nRéplicas notificadas con éxito.");


            System.out.println("\nCreando registro ...");

            Registry reg = LocateRegistry.createRegistry(1099);

            System.out.println("\nRegistro creado con éxito.");


            System.out.println("\nLanzando réplicas ...");

            for (Thread t : replicas_thread) {
                
                t.start();
            }
            
            System.out.println("\nRéplicas lanzadas con éxito.");
        }
        catch (RemoteException e) {
            System.out.println("Exception: " + e.getMessage());
        }
    }
}
