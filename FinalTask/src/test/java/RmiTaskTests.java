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
    @Before
    public void setUp() throws RemoteException, NotBoundException, InterruptedException {
        final ManagerImp server = new ManagerImp();
        Registry registry = LocateRegistry.createRegistry(123);
        registry.rebind("Manager", server);
        Thread.sleep(Integer.MAX_VALUE);
    }

    @Test
    public void testRmiCommunication() {
        try {
            ClientRmiTask3 client = new ClientRmiTask3();

            ClientRmiTask3.findByName("John");
            Thread.sleep(WAIT_TIME);
            assertEquals("Expected response", "[]", ClientSocketTask3.readResponse());

            ClientRmiTask3.findByEmailAndPhone("john@example.com", "0123-456-7890");
            Thread.sleep(WAIT_TIME);
            assertEquals("Expected response", "[]", ClientSocketTask3.readResponse());

            ClientRmiTask3.findByEmailAndPhone("kysilpetro@gmail.com", "123-456-7123");
            Thread.sleep(WAIT_TIME);
            assertEquals("Expected response", "[Kysil]",ClientSocketTask3.readResponse());

            ClientRmiTask3.findByName("Stepan");
            Thread.sleep(WAIT_TIME);
            assertEquals("Expected response", "[123-456-7890]", ClientSocketTask3.readResponse());

            ClientRmiTask3.sortByName();
            String expected = "[Name: Ivan Surname: Kysil Phone: 123-456-7890 Email: kysil@gmail.com, Name: Ivan Surname: Kysil Phone: 123-456-7890 Email: kysil@gmail.com Nickname: kysil456, Name: Petro Surname: Kysil Phone: 123-456-7123 Email: kysilpetro@gmail.com Nickname: kysil123, Name: Stepan Surname: Kysil Phone: 123-456-7890]";
            Thread.sleep(WAIT_TIME);
            assertEquals(expected,  ClientSocketTask3.readResponse());
            

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
