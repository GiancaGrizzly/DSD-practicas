// Código del cliente

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Random;
import java.util.Scanner;



public class Cliente
{
    private static void menu_registrar (Scanner scanner, IReplica replica)
    {
        System.out.println("\n\nIntroduzca el identificador con el que desea registrarse: ");
        String entrada = scanner.nextLine();

        try {
            if (replica.Registrar(entrada) == 0) {

                System.out.println("\n\nRegistro realizado con éxito.");
            }
            else {

                System.out.println("\n\nNo ha sido posible registrarse: ya existe un usuario con ese identificador.");
            }
        } catch (RemoteException e) {
            System.out.println("Exception Registrar cliente: " + e.getMessage());
        }
    }

    private static void menu_donar (Scanner scanner, IReplica replica)
    {
        try {
            System.out.println("\n\nIntroduzca el identificador con el que está registrado: ");
            String identificador_suscriptor = scanner.nextLine();

            System.out.println("\n\nIntroduzca la cantidad que desea donar: ");
            String entrada = scanner.nextLine();
            double donacion = Double.parseDouble(entrada);

            if (donacion > 0) {

                if (replica.Donar(identificador_suscriptor, donacion) == 0) {

                    System.out.println("\n\nDonación realizada con éxito. Muchas gracias!!");
                }
                else {
    
                    System.out.println("\n\nNo ha sido posible donar: no existe un usuario con ese identificador.");
                }
            }
            else {

                System.out.println("\n\nNo ha sido posible donar: cantidad inválida.");
            }            
        }
        catch (NumberFormatException e) {
            System.out.println("Error. Formato de la cantidad a donar: 1.0");
        }
        catch (RemoteException e) {
            System.out.println("Exception Donar cliente: " + e.getMessage());
        }
    }

    private static void menu_total_donado (Scanner scanner, IReplica replica)
    {
        try {

            System.out.println("\n\nIntroduzca el identificador con el que está registrado: ");
            String identificador_suscriptor = scanner.nextLine();

            double total = replica.TotalDonado(identificador_suscriptor);

            if (total >= 0) {

                System.out.println("\n\nEl total donado hasta el momento es ==> " + total);
            }
            else {

                System.out.println("\n\nNo existe un usuario con ese identificador o no ha realizado ninguna donación.");
            }
        }
        catch (RemoteException e) {
            System.out.println("Exception TotalDonado cliente: " + e.getMessage());
        }
    }

    
    public static void main(String[] args)
    {
        // Crea e instala el gestor de seguridad
        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }

        // final String menu_inicial = "Bienvenido al servicio de donaciones. Introduzca el número de la acción que desea realizar:" +
        //                             "\n\t 1. Registrarse" +
        //                             "\n\t 2. Donar" +
        //                             "\n\t 3. Consultar total donado" +
        //                             "\n\t 4. Salir";

        final int NREPLICAS = Integer.parseInt(args[0]);
        String entrada, nombre_replica_conectada;
        Random random = new Random();
        Scanner scanner_teclado = new Scanner(System.in);

        // Simulo que el usuario se conecta cada vez desde un "cajero" distinto
        // independientemente de la replica en la que esté registrado y donde se
        // efectúen sus donaciones
        nombre_replica_conectada = "Replica" + (random.nextInt(NREPLICAS)+1);
        System.out.println("Usuario conectandose a " + nombre_replica_conectada + " ...");

        try {

            Registry reg = LocateRegistry.getRegistry("127.0.0.1", 1099);
            System.out.println("Registro recuperado ...");

            
            IReplica replica_conectada = (IReplica) reg.lookup(nombre_replica_conectada);
            System.out.println(nombre_replica_conectada + " encontrada ...");

            while (true) {

                System.out.println("\n\nBienvenido al servicio de donaciones. Introduzca el número de la acción que desea realizar:" +
                                   "\n\t 1. Registrarse" +
                                   "\n\t 2. Donar" +
                                   "\n\t 3. Consultar total donado" +
                                   "\n\t 4. Salir");
                entrada = scanner_teclado.nextLine();
                
                switch (entrada) {

                    case "1":

                        menu_registrar(scanner_teclado, replica_conectada);

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            System.out.println("Exception sleep Registrar cliente: " + e.getMessage());
                        }
                        
                        break;

                    case "2":

                        menu_donar(scanner_teclado, replica_conectada);

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            System.out.println("Exception sleep Donar cliente: " + e.getMessage());
                        }
                        
                        break;

                    case "3":

                        menu_total_donado(scanner_teclado, replica_conectada);

                        try {
                            Thread.sleep(5000);
                        } catch (InterruptedException e) {
                            System.out.println("Exception sleep TotalDonado cliente: " + e.getMessage());
                        }
                        
                        break;

                    case "4":

                        System.out.println("\n\nHasta otra!!");
                        
                        System.exit(0);
                
                    default:

                        System.out.println("\n\nOpción inválida.");

                        break;
                }   
            }
        }
        catch (RemoteException | NotBoundException e) {
            System.out.println("Exception Cliente: " + e.getMessage());
        }
    }

}
