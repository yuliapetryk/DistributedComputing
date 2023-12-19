package tasks.rmi;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;

public class ClientRmiTask3 {
    private static Manager manager;
    public static void main(String[] args) throws IOException, NotBoundException {
        String url = "//localhost:123/Shop";
        manager = (Manager) Naming.lookup(url);
        System.out.println("Connected to the Shop service.");

    }

    public static void findByName( String name) throws IOException {
        manager.findByName(name);
        StringBuilder result = new StringBuilder();

    }

    public static void findByEmailAndPhone(String email, String phone) throws IOException {
        manager.findByEmailAndPhone(email,phone);
    }

    public static void sortByName() throws IOException {
        manager.sortByName();
    }

    public static void mergeContacts(String index1, String index2) throws IOException {
        manager.mergeContacts(index1, index2);
    }

}
