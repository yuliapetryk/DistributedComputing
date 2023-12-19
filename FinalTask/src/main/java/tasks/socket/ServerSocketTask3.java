package tasks.socket;

import tasks.data.Contact;
import tasks.data.Contacts;
import tasks.data.EmailContact;
import tasks.data.SocialMediaContact;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.SQLException;

public class ServerSocketTask3 {
    private static ServerSocket server = null;
    private static Socket sock = null;
    private static PrintWriter out = null;
    private static BufferedReader in = null;

    public static void main(String[] args) {
        try {
            ServerSocketTask3 srv = new ServerSocketTask3();
            srv.start(12345);
        } catch (IOException | SQLException | ClassNotFoundException | InstantiationException |
                 IllegalAccessException e) {
            System.out.println("Error");
        }
    }


    public void start(int port) throws IOException, SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        server = new ServerSocket(port);
        while (true) {
            sock = server.accept();
            in = new BufferedReader(new
                    InputStreamReader(sock.getInputStream()));
            out = new PrintWriter(sock.getOutputStream(), true);
            while (processQuery()) ;
        }
    }

    public static boolean processQuery() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        Contacts contacts= new Contacts();
            contacts = new Contacts();
            contacts.add(new Contact("Stepan", "Kysil", "123-456-7890"));
            contacts.add(new EmailContact("Ivan", "Kysil", "123-456-7890", "kysil@gmail.com"));
            contacts.add(new SocialMediaContact("Petro", "Kysil", "123-456-7123", "kysilpetro@gmail.com", "kysil123"));
            contacts.add(new SocialMediaContact("Ivan", "Kysil", "123-456-7890", "kysil@gmail.com", "kysil456"));

            try {
            String query = in.readLine();
            String[] parameters = query.split("#");
            String command = parameters[0];
            String response = "";
            switch (command) {
                case "1" :
                    response = contacts.findByName(parameters[1]).toString();
                    break;
                case "2" :
                    response = contacts.findByEmailAndPhone(parameters[1], parameters[2]).toString();
                    break;
                case "3" :
                    response = contacts.sortByName().toString();
                    break;
                case "4" :
                    response = String.valueOf(contacts.mergeContacts(Integer.parseInt(parameters[1]), Integer.parseInt(parameters[1])));
                    break;
                case "5" :
                {
                    sock.close();
                    in.close();
                    out.close();
                    return false;
                }
            }
            out.println(response);
            out.flush();
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
