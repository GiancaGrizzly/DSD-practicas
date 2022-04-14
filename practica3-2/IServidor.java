import java.rmi.Remote;
import java.rmi.RemoteException;

// Define la interfaz entre las réplicas

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IServidor extends Remote
{
    int GetTotalSuscriptores() throws RemoteException;
    double GetSubtotalDonado() throws RemoteException;
}
