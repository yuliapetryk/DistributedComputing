package RMI;

import Data.Product;
import Data.Section;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ShopServer extends Remote {
    public void addSection(int id, String name) throws RemoteException;

    public void deleteSection(int id) throws RemoteException;

    public void updateSection(int id, String name) throws RemoteException;

    public void addProduct(int id, String name, int section, int price) throws RemoteException;

    public void deleteProduct(int id) throws RemoteException;

    public void updateProduct(int id, String name, int section, int price) throws RemoteException;

    public int showProductCountInSection(int id) throws RemoteException;

    public Product showProductCountInSection(String name) throws RemoteException;

    public ArrayList<Product> showProductInSection(int id) throws RemoteException;

    public ArrayList<Section> showSections() throws RemoteException;

    public void stop() throws RemoteException;
}

