package RMI;

import Data.Product;
import Data.Section;
import Database.DatabaseService;

import javax.management.remote.rmi.RMIConnection;
import javax.management.remote.rmi.RMIServer;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.SQLException;
import java.util.ArrayList;

public class RMIServerImpl extends UnicastRemoteObject implements ShopServer{
    private static DatabaseService service;

    public RMIServerImpl() throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, RemoteException {
        super();
        DatabaseService service = new DatabaseService("jdbc:mysql://localhost:3306/shop", "root", "06102003");
    }

    @Override
    public void addSection(int id, String name) throws RemoteException {
        service.addSection(id, name);
    }

    @Override
    public void deleteSection(int id) throws RemoteException {
         service.deleteSection(id);
    }

    @Override
    public void updateSection(int id, String name) throws RemoteException {
          service.updateSection(id, name);
    }

    @Override
    public void addProduct(int id, String name, int section, int price) throws RemoteException {
        service.addProduct(id, name, section, price);
    }

    @Override
    public void deleteProduct(int id) throws RemoteException {
         service.deleteProduct(id);
    }

    @Override
    public void updateProduct(int id, String name, int section, int price) throws RemoteException {
            service.updateProduct(id, name, section, price);
    }

    @Override
    public int showProductCountInSection(int id) throws RemoteException {
        return service.showProductCountInSection(id);
    }

    @Override
    public Product showProductCountInSection(String name) throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<Product> showProductInSection(int id) throws RemoteException {
        return null;
    }

    @Override
    public ArrayList<Section> showSections() throws RemoteException {
        return null;
    }

    @Override
    public void stop() throws RemoteException {

    }
}