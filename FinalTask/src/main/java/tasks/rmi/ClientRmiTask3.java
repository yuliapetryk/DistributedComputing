package tasks.rmi;

import tasks.data.Contact;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.List;

public class ClientRmiTask3 {
    private static Manager manager;
    public ClientRmiTask3 () throws IOException, NotBoundException {
        String url = "//localhost:123/Contacts";
        manager = (Manager) Naming.lookup(url);
        System.out.println("Connected to the service.");
    }

    public static String findByName(String name) throws IOException {
        StringBuilder result= new StringBuilder();
        for (String res: manager.findByName(name) ){
            result.append(res);
        }
        return result.toString();
    }

    public static String  findByEmailAndPhone(String email, String phone) throws IOException {
        StringBuilder result= new StringBuilder();
        for (String res: manager.findByEmailAndPhone(email,phone)){
            result.append(res);
        }
        return result.toString();
    }

    public static String sortByName() throws IOException {
        StringBuilder result= new StringBuilder();
            result.append(manager.sortByName());
        return result.toString();
    }

    public static Contact mergeContacts(String index1, String index2) throws IOException {
       return manager.mergeContacts(index1, index2);
    }

}
