// Define la interfaz entre las réplicas

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IServidor extends Remote
{
    int GetTotalSuscriptores() throws RemoteException;
    double GetSubtotalDonado() throws RemoteException;
    boolean ExisteSuscriptor(String identificador) throws RemoteException;
    public void AddSuscriptor(String identificador) throws RemoteException;
}
