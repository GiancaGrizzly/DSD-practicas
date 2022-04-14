// Objeto remoto -- Implementa la interfaz remota

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class Replica extends UnicastRemoteObject implements IReplica
{
    private double cantidad_donada;



    public Replica() throws RemoteException
    {
        cantidad_donada = 0.0;
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
    public void TotalDonado() throws RemoteException
    {
        
    }
    
}
