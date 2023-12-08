package RMI;

import javax.management.remote.rmi.RMIServerImpl;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Server {
    public static void main(String[] args) {
        try {
            Registry r = LocateRegistry.createRegistry(1099);
           // RMIServerImpl server = new RMIServerImpl();
            //r.rebind("SayHello", server);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}