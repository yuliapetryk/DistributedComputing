package Socket;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private  final Socket sock;
    private static PrintWriter out;
    private static  BufferedReader in;

    public Client(String ip, int port) throws IOException {
        sock = new Socket(ip, port);
        in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
        out = new PrintWriter(sock.getOutputStream(), true);
    }

    public static void main(String[] args) {
        try {
            Client client = new Client("localhost", 12345);
            showSections();
            readResponse();

            getProductByName("DRESS");
            readResponse();

            addProduct("44","Sandwich","55", "2");
            readResponse();

            showProductInSection("2");
            readResponse();

            stop();
            client.disconnect();
        } catch (IOException e) {
            System.out.println("CLIENT: Error");
            e.printStackTrace();
        }
    }

    private static String sendRequest(String... requests) {
        StringBuilder request = new StringBuilder();
        for (String arg : requests) {
            request.append(arg).append("#");
        }
       return request.toString();
    }

    private static void readResponse() throws IOException {
        String response = in.readLine();
        String[] fields = response.split("#");
        for (String field : fields) {
            System.out.println(field);
        }
        System.out.println();
    }

    public static void addSection(String id, String name) throws IOException {
        out.println(sendRequest("1", id, name));
    }

    public static void deleteSection(String id) throws IOException {
        out.println(sendRequest("2", id));
    }

    public static void updateSection( String id, String name) throws IOException {
        out.println(sendRequest("3", id, name));
    }

    public static void addProduct( String id, String name, String price, String section) throws IOException {
        out.println(sendRequest("4", id, name, section, price));
    }

    public static void deleteProduct(String id) throws IOException {
        out.println(sendRequest("5", id));
    }

    public static void updateProduct(String id, String name, String price, String section) throws IOException {
        out.println(sendRequest("6", id, name, section, price));
    }

    public static void showProductCountInSection(String id) throws IOException {
        out.println(sendRequest("7", id));
    }

    public static void getProductByName(String name) throws IOException {
        out.println(sendRequest("8",name));
    }

    public static void showProductInSection(String id) throws IOException {
        out.println(sendRequest("9", id));
    }

    public static void showSections() throws IOException {
        out.println(sendRequest("10"));
    }

    public static void stop() throws IOException {
        out.println(sendRequest("11"));
    }


    public void disconnect() throws IOException {
        sock.close();
    }
}