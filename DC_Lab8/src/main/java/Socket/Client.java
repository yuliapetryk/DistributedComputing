package Socket;

import java.io.IOException;
import java.net.Socket;
public class Client {

    public static void main(String[] args) {
        Socket sock = null;
        try {
            System.out.println("Connecting to server...");
            sock = new Socket("localhost", 12345);
            System.out.println("Connected");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
