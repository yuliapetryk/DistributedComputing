package Socket;

import Data.Product;
import Data.Section;
import Database.DatabaseService;

import java.io.StringWriter;
import java.sql.*;
import java.util.ArrayList;

public class ServerService {

   private DatabaseService service;
   private StringWriter result;
    public ServerService()
            throws ClassNotFoundException, InstantiationException, IllegalAccessException, SQLException {
         service = new DatabaseService("jdbc:mysql://localhost:3306/shop", "root", "06102003");
         result = new StringWriter();
    }

    public String showSections() {
        ArrayList<Section> sections = service.showSections();
        result.append("ALL SECTIONS:#");
        for (Section section :sections){
            result.append(">>" + section.getSectionCode() + "-" + section.getSectionName()).append("#");
        }
        return result.toString();
    }

    public String showProductInSection(int code) {
        ArrayList<Product> products = service. showProductInSection(code);
        result.append("ALL PRODUCTS IN SECTION " + code + ":#");
        for (Product product :products){
            result.append(">>" + product.getProductCode() + "-" + product.getProductName() + " $" + product.getProductPrice()).append("#");
        }
        return result.toString();
    }

    public String addSection(int id, String name) {
        try {
            service.addSection(id, name);
            result.append("Section ").append(name).append(" successfully added#");
        } catch (Exception e) {
            result.append("Failed to add Section ").append(name).append("#");
        }
        return result.toString();
    }


    public String updateSection(int id, String name) {
        try {
            service.updateSection(id, name);
            result.append("Section ").append(name).append(" successfully updated#");
        } catch (Exception e) {
            result.append("Failed to update Section ").append(name).append("#");
        }
        return result.toString();
    }

    public String updateProduct(int id, String name, int section, int price) {
        try {
            service.updateProduct(id, name, section, price);
            result.append("Product ").append(name).append(" successfully updated#");
        } catch (Exception e) {
            result.append("Failed to update Product ").append(name).append("#");
        }
        return result.toString();
    }

    public String addProduct(int id, String name,int section, int price ) {
        try {
            service.addProduct(id, name, section, price);
            result.append("Product ").append(name).append(" successfully added#");
        } catch (Exception e) {
            result.append("Failed to add Product ").append(name).append("#");
        }
        return result.toString();
    }

    public String deleteSection(int id) {
        try {
            service.deleteSection(id);
            result.append("Section ").append((char) id).append(" successfully deleted#");
        } catch (Exception e) {
            result.append("Failed to delete Section ").append((char) id).append("#");
        }
        return result.toString();
    }

    public String deleteProduct(int id) {
        try {
            service.deleteProduct(id);
            result.append("Product ").append((char) id).append(" successfully deleted#");
        } catch (Exception e) {
            result.append("Failed to delete Product ").append((char) id).append("#");
        }
        return result.toString();
    }

    public String showProductCountInSection(int code) {
        char productCount = (char) service.showProductCountInSection(code);
        result.append("NUMBER OF PRODUCTS IN SECTION ").append((char) code).append(": ").append(productCount).append("#");
        return result.toString();
    }

    public String getProductByName(String name) {
        Product product = service.getProductByName(name);
        result.append("Product with name ").append(name).append(" has :#");
        result.append("Id: ").append(product.getProductCode().toString())
                .append("#Section: ").append(product.getSectionProductCode().toString())
                .append("#Price: ").append(product.getProductPrice().toString()).append("#");
        return result.toString();
    }


}
