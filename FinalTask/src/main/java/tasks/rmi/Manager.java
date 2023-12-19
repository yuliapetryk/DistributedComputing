package tasks.rmi;

import tasks.data.Contact;

import java.io.IOException;
import java.rmi.Remote;

public interface Manager extends Remote {

    public  String findByName(String name) throws IOException ;
    public String findByEmailAndPhone(String email, String phone) throws IOException ;

    public String sortByName() throws IOException ;

    public Contact mergeContacts(String index1, String index2) throws IOException ;

}
