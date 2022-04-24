// Define la interfaz entre las réplicas

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IServidor extends Remote
{
    int GetTotalSuscriptores() throws RemoteException;
    double GetSubtotalDonado() throws RemoteException;
    void DonarReplica(int indice, double donacion) throws RemoteException;
    boolean ExisteSuscriptor(Suscriptor suscriptor) throws RemoteException;
    int GetSuscriptor(String identificador_suscriptor) throws RemoteException;
    Suscriptor GetSuscriptor(int indice) throws RemoteException;
    public void AddSuscriptor(String identificador_suscriptor) throws RemoteException;
}
