// Objeto remoto -- Implementa la interfaz remota

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;

public class Replica extends UnicastRemoteObject implements IReplica, IServidor, Runnable
{
    private static int _id = 1;

    private String nombre;
    private double cantidad_donada;
    private ArrayList<Suscriptor> suscriptores;
    private ArrayList<IServidor> replicas;

    private ReentrantLock lock;


    // Métodos públicos de la clase Replica
    // ---------------------------
    public Replica() throws RemoteException
    {
        nombre = "Replica" + _id;
        _id++;
        cantidad_donada = 0.0;
        suscriptores = new ArrayList<Suscriptor>();
        replicas = new ArrayList<IServidor>();

        lock = new ReentrantLock();
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
    private boolean existe_suscriptor(String identificador_suscriptor) throws RemoteException
    {
        boolean existe = false;

        Suscriptor suscriptor = new Suscriptor(identificador_suscriptor);

        if (suscriptores.contains(suscriptor)) {

            existe = true;
        }
        else {
            
            Iterator<IServidor> it = replicas.iterator();
            while (it.hasNext() && !existe) {

                if (it.next().ExisteSuscriptor(suscriptor)) {

                    existe = true;
                }
            }
        }

        return existe;
    }

    private int get_suscriptor (String identificador_suscriptor)
    {
        int indice = 0;
        boolean encontrado = false;

        while ( (indice < suscriptores.size()) && !encontrado ) {

            if (identificador_suscriptor.equals(suscriptores.get(indice).GetId())) {

                encontrado = true;
            }
            else {

                indice++;
            }
        }

        return indice;
    }

    private double get_total_donado () throws RemoteException
    {
        double total;

        lock.lock();
        try {
            total = cantidad_donada;
        }
        finally {
            lock.unlock();
        }

        for (IServidor r : replicas) {
            
            total += r.GetSubtotalDonado();
        }

        return total;
    }


    // Métodos de la interfaz remota IReplica
    // ---------------------------
    @Override
    public int Registrar(String identificador_suscriptor) throws RemoteException
    {
        int ok = -1;

        if (!existe_suscriptor(identificador_suscriptor)) {

            IServidor min_replica = this;

            for (int i=0; i<replicas.size(); i++) {

                if (min_replica.GetTotalSuscriptores() > replicas.get(i).GetTotalSuscriptores()) {

                    min_replica = replicas.get(i);
                    
                }
            }

            min_replica.AddSuscriptor(identificador_suscriptor);

            ok = 0;
        }

        return ok;
    }

    @Override
    public int Donar(String identificador_suscriptor, double donacion) throws RemoteException
    {
        int ok = -1;

        int indice = get_suscriptor(identificador_suscriptor);

        if (indice != suscriptores.size()) {

            lock.lock();
            try{
                cantidad_donada += donacion;
            }
            finally {
                lock.unlock();
            }

            suscriptores.get(indice).Set_donado();

            ok = 0;
        }
        else {

            for (IServidor r : replicas) {
                
                indice = r.GetSuscriptor(identificador_suscriptor);
                if (indice != r.GetTotalSuscriptores()) {

                    r.DonarReplica(indice, donacion);

                    // Se sale del bucle, ya que hemos encontrado el suscriptor
                    ok = 0;
                    break;
                }
            }
        }

        return ok;
    }

    @Override
    public double TotalDonado(String identificador_suscriptor) throws RemoteException
    {
        double total = -1;
        int indice = get_suscriptor(identificador_suscriptor);

        if (indice != suscriptores.size()) {

            if (suscriptores.get(indice).Ha_donado()) {

                total = get_total_donado();
            }
        }
        else {

            for (IServidor r : replicas) {

                indice = r.GetSuscriptor(identificador_suscriptor);
                if (indice != r.GetTotalSuscriptores()) {

                    if (r.GetSuscriptor(indice).Ha_donado()) {

                        total = get_total_donado();
                    }
                    // Se sale del bucle, ya que hemos encontrado el suscriptor
                    // (se sale sin importar si ha donado o no)
                    break;
                }
            }
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
        double total;

        lock.lock();
        try {
            total = cantidad_donada;
        }
        finally {
            lock.unlock();
        }

        return total;
    }

    @Override
    public void DonarReplica(int indice, double donacion) throws RemoteException
    {
        lock.lock();
        try {
            cantidad_donada += donacion;
        }
        finally {
            lock.unlock();
        }

        suscriptores.get(indice).Set_donado();       
    }

    @Override
    public boolean ExisteSuscriptor(Suscriptor suscriptor) throws RemoteException
    {
        return suscriptores.contains(suscriptor);
    }

    @Override
    public int GetSuscriptor (String identificador_suscriptor) throws RemoteException
    {
        return get_suscriptor(identificador_suscriptor);
    }

    @Override
    public Suscriptor GetSuscriptor (int indice) throws RemoteException
    {
        return suscriptores.get(indice);
    }

    @Override
    public void AddSuscriptor(String identificador_suscriptor) throws RemoteException
    {
        suscriptores.add(new Suscriptor(identificador_suscriptor));
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
            
            System.out.println("Exception Replica: " + e.getMessage());
        }
    }
    
}
