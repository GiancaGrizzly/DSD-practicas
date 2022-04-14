// Objeto remoto -- Implementa la interfaz remota

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Replica extends UnicastRemoteObject implements IReplica, IServidor
{
    private double cantidad_donada;
    private int total_suscriptores;


    public Replica() throws RemoteException
    {
        cantidad_donada = 0.0;
        total_suscriptores = 0;
    }

    @Override
    public String Registrar() throws RemoteException
    {
        

        return "";
    }

    @Override
    public void Donar() throws RemoteException
    {
        
    }

    @Override
    public double TotalDonado() throws RemoteException
    {

        
        return 0;
    }

    @Override
    public int GetTotalSuscriptores() throws RemoteException
    {
        return total_suscriptores;
    }

    @Override
    public double GetSubtotalDonado() throws RemoteException
    {
        return cantidad_donada;
    }
    
}
