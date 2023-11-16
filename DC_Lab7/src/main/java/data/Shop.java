package data;

import java.util.ArrayList;
import java.util.Objects;

public class Shop {

    private ArrayList<Section> sections;

    private ArrayList<Product> products;


    public void addSection(int code, String name) {
        for (Section section : this.sections) {
            if (section.getSectionCode() == code || section.getSectionName().equals(name)) {
                System.out.println("Section already exists");
                return;
            }
        }
        this.sections.add(new Section(code, name));
    }

    public void addProduct(int code, String name, int sectionCode) {
        for (Product p : this.products) {
            if (p.getProductCode() == code || p.getProductName().equals(name)) {
                System.out.println("Product already exists");
                return;
            }
        }
        for (Section section : this.sections) {
            if (section.getSectionCode() == sectionCode) {
                this.products.add(new Product(code, name, section));
                return;
            }
        }
        System.out.println("Section with this code does not exist");

    }

    public void updateSection(int code, String name) {
        if (getSection(code) == null) {
            System.out.println("Section with this code does not exist");
        } else {
            for (Section section : this.sections) {
                if (Objects.equals(section.getSectionName(), name)) {
                    System.out.println("Section with this name already exists");
                    return;
                }
            }
            for (Section section : this.sections) {
                if (section.getSectionCode() == code) {
                    section.setSectionName(name);
                    return;
                }
            }
        }
    }

    public void updateProductName(int code, String name) {
        if (getProduct(code) == null) {
            System.out.println("Product with this code does not exist");
        } else {
            for (Product product : this.products) {
                if (Objects.equals(product.getProductName(), name)) {
                    System.out.println("Product with this name already exists");
                    return;
                }
            }
            for (Product product : this.products) {
                if (product.getProductCode() == code) {
                    product.setProductName(name);
                    return;
                }
            }
        }
    }

    public Section getSection(int code) {
        for (Section section : this.sections) {
            if (section.getSectionCode() == code) {
                return section;
            }
        }
        return null;
    }

    public Product getProduct(int code) {
        for (Product product : this.products) {
            if (product.getProductCode() == code) {
                return product;
            }
        }
        return null;
    }

    public void updateProductSection(int code, int sectionCode) {
        if (getProduct(code) == null) {
            System.out.println("Product with this code does not exist");
        } else {
            for (Section section : this.sections) {
                if (Objects.equals(section.getSectionCode(), sectionCode)) {
                    System.out.println("Section with this code does not already exists");
                    return;
                }
            }
            for (Product product : this.products) {
                if (product.getProductCode() == code) {
                    product.setProductSection(getSection(sectionCode));
                    return;
                }
            }
        }
    }


}
