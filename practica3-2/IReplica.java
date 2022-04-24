// Define la interfaz remota

import java.rmi.Remote;
import java.rmi.RemoteException;


public interface IReplica extends Remote
{
    int Registrar(String identificador_suscriptor) throws RemoteException;
    int Donar(String identificador_suscriptor, double donacion) throws RemoteException;
    double TotalDonado(String identificador_suscriptor) throws RemoteException;
}
