package RMI;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class Server {
    public static void main(String[] args) throws RemoteException, InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, AlreadyBoundException {
        final ShopServerImpl server = new ShopServerImpl();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("Shop", server);
       Thread.sleep(Integer.MAX_VALUE);
    }
}