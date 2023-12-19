package tasks.rmi;

import tasks.data.Contact;

import java.io.IOException;
import java.rmi.Remote;
import java.util.List;

public interface Manager extends Remote {

    public  List<String>  findByName(String name) throws IOException ;
    public List<String> findByEmailAndPhone(String email, String phone) throws IOException ;

    public String  sortByName() throws IOException ;

    public String mergeContacts(String index1, String index2) throws IOException ;

}
