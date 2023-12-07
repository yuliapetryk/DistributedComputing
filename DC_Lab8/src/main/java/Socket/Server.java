package Socket;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
public class Server {
    public static void main(String[] args) {
        ServerSocket server = null;
        try {
            server = new ServerSocket(12345);
        } catch (
                IOException e) {
            e.printStackTrace();
        }
        Socket sock = null;
        try {
            System.out.println("Waiting for a client ...");
            sock = server.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
