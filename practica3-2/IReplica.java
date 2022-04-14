import java.rmi.Remote;

// Define la interfaz remota

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IReplica extends Remote
{
    String Registrar() throws RemoteException;
    void Donar() throws RemoteException;
    double TotalDonado() throws RemoteException;
}
