package tasks.socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ClientSocketTask3 {
    private  final Socket sock;
    private static PrintWriter out;
    private static BufferedReader in;

    public ClientSocketTask3(String ip, int port) throws IOException {
        sock = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
    }


    public static void main(String[] args) {
        try {
            ClientSocketTask3 client = new ClientSocketTask3("localhost", 12345);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

        private static String sendRequest(String... requests) {
        StringBuilder request = new StringBuilder();
        for (String arg : requests) {
            request.append(arg).append("#");
        }
        return request.toString();
    }

    public static String readResponse() throws IOException {
        String response = in.readLine();
        StringBuilder result = new StringBuilder();
        String[] fields = response.split("#");
        for (String field : fields) {
            result.append(field);
        }
        return result.toString();
    }

    public static void findByName(String name) throws IOException {
        out.println(sendRequest("1", name));
    }

    public static void findByEmailAndPhone(String email, String phone) throws IOException {
        out.println(sendRequest("2", email, phone));
    }

    public static void sortByName() throws IOException {
        out.println(sendRequest("3"));
    }

    public static void mergeContacts( String index1, String index2) throws IOException {
        out.println(sendRequest("4", index1, index2));
    }

    public static void stop() throws IOException {
        out.println(sendRequest("5"));
    }

    public void disconnect() throws IOException {
        sock.close();
    }
}

