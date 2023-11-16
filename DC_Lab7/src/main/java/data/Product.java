package data;

public class Product {
    public int code;
    public String name;
    public Section section;
    public int price;
    public Product(int code, String name, Section section, int price){
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

    public Integer getSectionProductCode(){
        return this.section.getSectionCode();
    }

    public void setProductName(String name) {
        this.name = name;
    }

    public void setProductSection(Section section) {
        this.section = section;
    }

    public void setProductPrice(int price) {
        this.price = price;
    }

}