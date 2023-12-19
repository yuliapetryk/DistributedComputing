import org.junit.Before;
import org.junit.Test;
import tasks.rmi.ClientRmiTask3;
import tasks.rmi.Manager;
import tasks.rmi.ManagerImp;
import tasks.socket.ClientSocketTask3;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RmiTaskTests {
    private Manager manager;
    private static final int WAIT_TIME = 100;
    private Thread serverThread;
    @Before
    public void setUp() throws RemoteException, NotBoundException, InterruptedException {
        serverThread = new Thread(() -> {
            try {
                final ManagerImp server = new ManagerImp();
                Registry registry = LocateRegistry.getRegistry(123);
                String url = "Contacts";
                registry.rebind(url, server);
                Thread.sleep(Integer.MAX_VALUE);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();

    }

    @Test
    public void testRmiCommunication() {
        try {
            ClientRmiTask3 clientRmiTask3 = new ClientRmiTask3();

            assertEquals("Expected response", "", clientRmiTask3.findByName("John"));

            assertEquals("Expected response", "",clientRmiTask3.findByEmailAndPhone("john@example.com", "0123-456-7890"));

            assertEquals("Expected response", "Kysil", clientRmiTask3.findByEmailAndPhone("kysilpetro@gmail.com", "123-456-7123"));

            assertEquals("Expected response", "123-456-7890",  clientRmiTask3.findByName("Stepan"));

            String expected = "[Name: Ivan Surname: Kysil Phone: 123-456-7890 Email: kysil@gmail.com, Name: Ivan Surname: Kysil Phone: 123-456-7890 Email: kysil@gmail.com Nickname: kysil456, Name: Petro Surname: Kysil Phone: 123-456-7123 Email: kysilpetro@gmail.com Nickname: kysil123, Name: Stepan Surname: Kysil Phone: 123-456-7890]";
            assertEquals(expected,  clientRmiTask3.sortByName());


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
