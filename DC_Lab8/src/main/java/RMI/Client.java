package RMI;

import Data.Product;
import Data.Section;

import java.io.IOException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.util.ArrayList;


public class Client {
    private static ShopServer shop;
    public static void main(String[] args) throws IOException, NotBoundException {
           //TODO create scenario
            String url = "//localhost:123/Shop";
            shop = (ShopServer) Naming.lookup(url);
            System.out.println("Connected to the Shop service.");
            addSection("10","Perfume");
            addProduct("33","Dior","10","12000");
            System.out.println(showSections());
            System.out.println(getProductByName("DRESS"));
    }

    public static void addSection(String id, String name) throws IOException {
       shop.addSection(Integer.parseInt(id), name);
    }

    public static void deleteSection(String id) throws IOException {
        shop.deleteSection(Integer.parseInt(id));
    }

    public static void updateSection( String id, String name) throws IOException {
        shop.updateSection(Integer.parseInt(id), name);
    }

    public static void addProduct( String id, String name, String section, String price) throws IOException {
        shop.addProduct(Integer.parseInt(id), name, Integer.parseInt(section), Integer.parseInt(price));
    }

    public static void deleteProduct(String id) throws IOException {
        shop.deleteProduct(Integer.parseInt(id));
    }

    public static void updateProduct(String id, String name, String price, String section) throws IOException {
        shop.updateProduct(Integer.parseInt(id), name, Integer.parseInt(section), Integer.parseInt(price));
    }

    public static String showProductCountInSection(String id) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append(shop.showProductCountInSection(Integer.parseInt(id)) + " products in the section\n");
        return result.toString();
    }

    public static String getProductByName(String name) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("PRODUCT: \n");
        ArrayList<Product> products = new ArrayList<Product>();
        products.add(shop.getProductByName(name));
        result.append(printProduct(products));
        return result.toString();
    }

    public static String showProductInSection(String id) throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("ALL PRODUCT IN SECTION " + id + ":\n");
        result.append(printProduct(shop.showProductInSection(Integer.parseInt(id))));
        return result.toString();
    }

    public static String showSections() throws IOException {
        StringBuilder result = new StringBuilder();
        result.append("ALL SECTIONS:\n");
        result.append(printSection(shop.showSections()));
        return result.toString();
    }
    private static String printSection(ArrayList<Section> sections ){
        StringBuilder result = new StringBuilder();
        for (Section section :sections){
            result.append(">>").append(section.getSectionCode())
                    .append("-").append(section.getSectionName()).append("\n");
        }
        return result.toString();
    }

    private static String printProduct(ArrayList<Product> products ){
        StringBuilder result = new StringBuilder();
        for (Product product :products){
            result.append(">>").append(product.getProductCode()).append("\n")
                    .append("Name: ").append(product.getProductName()).append("\n")
                    .append("Section: ").append(product.getSectionProductCode()).append("\n")
                    .append("Price: ").append(product.getProductPrice()).append("\n");
        }
        return result.toString();
    }

}
