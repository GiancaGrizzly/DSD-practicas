// Fichero: contador.java
// Implementa la interfaz remota

package ejemplo3;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class contador extends UnicastRemoteObject implements icontador
{
    private int suma;

    public contador() throws RemoteException {}

    @Override
    public int sumar() throws RemoteException
    {
        return suma;
    }

    @Override
    public void sumar(int valor) throws RemoteException
    {
        suma = valor;
    }

    @Override
    public int incrementar() throws RemoteException
    {
        suma++;
        
        return suma;
    }
    
}
