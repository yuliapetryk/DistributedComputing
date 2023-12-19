import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import tasks.data.Contact;
import tasks.socket.ClientSocketTask3;
import tasks.socket.ServerSocketTask3;

import static org.junit.Assert.assertEquals;

public class SocketTaskTests {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;
    private static final int WAIT_TIME = 100;

    private Thread serverThread;

    @Before
    public void setUp() {
        serverThread = new Thread(() -> {
            try {
                ServerSocketTask3 server = new ServerSocketTask3();
                server.start(PORT);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        serverThread.start();
    }

    @After
    public void tearDown() {
        serverThread.interrupt();
    }

    @Test
    public void testSocketCommunication() {
        try {
            ClientSocketTask3 client = new ClientSocketTask3(HOST, PORT);

            ClientSocketTask3.findByName("John");
            Thread.sleep(WAIT_TIME);
            assertEquals("Expected response", "[]", ClientSocketTask3.readResponse());

            ClientSocketTask3.findByEmailAndPhone("john@example.com", "0123-456-7890");
            Thread.sleep(WAIT_TIME);
            assertEquals("Expected response", "[]", ClientSocketTask3.readResponse());

            ClientSocketTask3.findByEmailAndPhone("kysilpetro@gmail.com", "123-456-7123");
            Thread.sleep(WAIT_TIME);
            assertEquals("Expected response", "[Kysil]",ClientSocketTask3.readResponse());

            ClientSocketTask3.findByName("Stepan");
            Thread.sleep(WAIT_TIME);
            assertEquals("Expected response", "[123-456-7890]", ClientSocketTask3.readResponse());

            ClientSocketTask3.sortByName();
            String expected = "[Name: Ivan Surname: Kysil Phone: 123-456-7890 Email: kysil@gmail.com, Name: Ivan Surname: Kysil Phone: 123-456-7890 Email: kysil@gmail.com Nickname: kysil456, Name: Petro Surname: Kysil Phone: 123-456-7123 Email: kysilpetro@gmail.com Nickname: kysil123, Name: Stepan Surname: Kysil Phone: 123-456-7890]";
            Thread.sleep(WAIT_TIME);
            assertEquals(expected,  ClientSocketTask3.readResponse());

            client.disconnect();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}