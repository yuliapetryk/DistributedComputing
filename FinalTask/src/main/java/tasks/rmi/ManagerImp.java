package tasks.rmi;

import tasks.data.Contact;
import tasks.data.Contacts;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;

public class ManagerImp extends UnicastRemoteObject implements Manager {
Contacts contacts;

    public ManagerImp() throws RemoteException {
        contacts = new Contacts();
    }
    @Override
    public  String findByName(String name) throws IOException {
        return contacts.findByName(name).toString();
    }

    @Override
    public String findByEmailAndPhone(String email, String phone) throws IOException {
        contacts.findByEmailAndPhone(email,phone);
    }

    @Override
    public String sortByName() throws IOException {
        return contacts.sortByName().toString();
    }

    @Override
    public Contact mergeContacts(String index1, String index2) throws IOException {
        return  contacts.mergeContacts(Integer.parseInt(index1),Integer.parseInt(index2));
    }

}
