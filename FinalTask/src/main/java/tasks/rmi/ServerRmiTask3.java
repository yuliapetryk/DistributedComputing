package tasks.rmi;

import java.rmi.AlreadyBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.sql.SQLException;

public class ServerRmiTask3 {
    public static void main(String[] args) throws RemoteException, InterruptedException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, AlreadyBoundException {
        final ManagerImp server = new ManagerImp();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("Manager", server);
        Thread.sleep(Integer.MAX_VALUE);
    }
}
