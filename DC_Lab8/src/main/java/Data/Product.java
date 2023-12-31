package Data;

import java.io.Serializable;
import java.util.ArrayList;

public class Product implements Serializable {
    private final int code;

    private String name;

    private int section;

    private int price;

    public Product(int code, String name, int section, int price){
        this.code = code;
        this.name = name;
        this.section = section;
        this.price = price;
    }

    public String getProductName() {
        return this.name;
    }

    public Integer getProductCode(){
        return this.code;
    }

    public Integer getProductPrice(){
        return this.price;
    }

    public Integer getSectionProductCode(){
        return this.section;
    }

    public void setProductName(String name) {
        this.name = name;
    }

    public void setProductSection(int section) {
        this.section = section;
    }

    public void setProductPrice(int price) {
        this.price = price;
    }

    public static String printProduct(ArrayList<Product> products){
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
