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

    public void addProduct(int code, String name, int sectionCode, int price) {
        for (Product p : this.products) {
            if (p.getProductCode() == code || p.getProductName().equals(name)) {
                System.out.println("Product already exists");
                return;
            }
        }
        for (Section section : this.sections) {
            if (section.getSectionCode() == sectionCode) {
                this.products.add(new Product(code, name, section, price));
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

    public void updateProductPrice(int code, int price) {
        if (getProduct(code) == null) {
            System.out.println("Product with this code does not exist");
        } else {
            for (Product product : this.products) {
                if (product.getProductCode() == code) {
                    product.setProductPrice(price);
                    return;
                }
            }
        }
    }

    public void updateProduct(int code, String name, int sectionCode,  int price) {
        if (getProduct(code) == null) {
            System.out.println("Product with this code does not exist");
        } else {
            for (Product product : this.products) {
                if (Objects.equals(product.getProductName(), name)) {
                    System.out.println("Product with this name already exists");
                    return;
                }
            }
            for (Section section : this.sections) {
                if (Objects.equals(section.getSectionCode(), sectionCode)) {
                    System.out.println("Section with this code does not already exists");
                    return;
                }
            }
            for (Product product : this.products) {
                if (product.getProductCode() == code) {
                    product.setProductName(name);
                    product.setProductSection(getSection(sectionCode));
                    product.setProductPrice(price);
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

    public void deleteSection(int code) {
        if (getSection(code) == null) {
            System.out.println("Section with this code does not exist");
        } else {
            for (Section section : this.sections) {
                if (section.getSectionCode() == code) {
                    for (Product product : this.products) {
                        if (Objects.equals(product.getSectionProductCode(), code)) {
                            this.products.remove(product);
                        }
                    }
                    this.sections.remove(section);
                    return;
                }
            }
        }
    }

    public void deleteProduct(int code) {
        for (Product product : this.products) {
            if (product.getProductCode() == code) {
                this.products.remove(product);
                return;
            }
        }
        System.out.println("Product with this code does not exist");
    }
}
