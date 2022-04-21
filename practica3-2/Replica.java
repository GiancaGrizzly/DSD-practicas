// Objeto remoto -- Implementa la interfaz remota

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;

public class Replica extends UnicastRemoteObject implements IReplica, IServidor, Runnable
{
    private static int _id = 1;

    private String nombre;
    private double cantidad_donada;
    private ArrayList<String> suscriptores;
    private ArrayList<Replica> replicas;


    // Métodos públicos de la clase Replica
    // ---------------------------
    public Replica() throws RemoteException
    {
        nombre = "Réplica" + _id;
        _id++;
        cantidad_donada = 0.0;
        suscriptores = new ArrayList<String>();
        replicas = new ArrayList<Replica>();
    }

    public void SetReplicas(ArrayList<Replica> replicas) throws RemoteException
    {
        for (Replica r : replicas) {

            if (r != this) {

                this.replicas.add(r);
            }
        }
    }


    // Métodos privados de clase
    // ---------------------------
    private boolean existe_suscriptor(String identificador) throws RemoteException
    {
        boolean existe = false;

        if (suscriptores.contains(identificador)) {

            existe = true;
        }
        else {
            
            Iterator<Replica> it = replicas.iterator();
            while (it.hasNext() && !existe) {

                if (it.next().ExisteSuscriptor(identificador)) {

                    existe = true;
                }
            }
        }

        return existe;
    }


    // Métodos de la interfaz remota IReplica
    // ---------------------------
    @Override
    public int Registrar(String identificador) throws RemoteException
    {
        int ok = 0;

        if (existe_suscriptor(identificador)) {

            // "No ha sido posible registrarse: ya existe un usuario con ese identificador."
            ok = -1;
        }
        else {

            Replica min_replica = this;

            for (int i=0; i<replicas.size(); i++) {

                if (min_replica.GetTotalSuscriptores() > replicas.get(i).GetTotalSuscriptores()) {

                    min_replica = replicas.get(i);
                    
                }
            }

            min_replica.AddSuscriptor(identificador);

            // "Registro realizado con éxito."

        }

        return ok;
    }

    @Override
    public void Donar(double donacion) throws RemoteException
    {
        cantidad_donada += donacion;
    }

    @Override
    public double TotalDonado() throws RemoteException
    {
        double total = cantidad_donada;

        for (Replica r : replicas) {
            
            total += r.GetSubtotalDonado();
        }

        return total;
    }


    // Métodos de la interfaz entre réplicas IServidor
    // ---------------------------
    @Override
    public int GetTotalSuscriptores() throws RemoteException
    {
        return suscriptores.size();
    }

    @Override
    public double GetSubtotalDonado() throws RemoteException
    {
        return cantidad_donada;
    }

    @Override
    public boolean ExisteSuscriptor(String identificador) throws RemoteException
    {
        return suscriptores.contains(identificador);
    }

    @Override
    public void AddSuscriptor(String identificador) throws RemoteException
    {
        suscriptores.add(identificador);
    }
    

    // Método de la interfaz Runnable
    // ---------------------------
    @Override
    public void run()
    {
        // Crea e instala el gestor de seguridad
        // if (System.getSecurityManager() == null) {
        //     System.setSecurityManager(new SecurityManager());
        // }

        try {
            
            Naming.rebind(nombre, this);

            System.out.println(nombre + " ...");
        }
        catch (RemoteException | MalformedURLException e) {
            
            System.out.println("Exception: " + e.getMessage());
        }
    }
    
}
