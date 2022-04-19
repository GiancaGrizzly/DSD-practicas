// Define la interfaz remota

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IReplica extends Remote
{
    int Registrar(String identificador) throws RemoteException;
    void Donar(double donacion) throws RemoteException;
    double TotalDonado() throws RemoteException;
}
