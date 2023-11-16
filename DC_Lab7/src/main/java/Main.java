import data.Product;
import data.Shop;
import parser.Parser;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Parser parser = new Parser();
        Shop shop = new Shop();
        shop = parser.readFromFile();

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            menu();
            command = scanner.nextLine();
            switch (command) {
                case "S1":
                    System.out.println("All sections:");
                    shop.showAllSections();

                case "S2":
                    System.out.println("Enter section code: ");
                    int newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter section name: ");
                    String newName = scanner.nextLine();
                    shop.addSection(newCode, newName);

                case "S3":
                    System.out.println("Enter section code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new section name: ");
                    newName = scanner.nextLine();
                    shop.updateSection(newCode, newName);

                case "S4":
                    System.out.println("Enter section code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    shop.deleteSection(newCode);

                case "P1":
                    System.out.println("Enter section code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("All products in this section:");
                    shop.showAllProductsInSection(newCode);

                case "P2":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter product name: ");
                    newName = scanner.nextLine();
                    System.out.println("Enter section code: ");
                    int newSectionCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter product price: ");
                    int newPrice = Integer.parseInt(scanner.nextLine());
                    shop.addProduct(newCode, newName, newSectionCode, newPrice);

                case "P3":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product name: ");
                    newName = scanner.nextLine();
                    System.out.println("Enter new section code: ");
                    newSectionCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product price: ");
                    newPrice = Integer.parseInt(scanner.nextLine());
                    shop.updateProduct(newCode, newName, newSectionCode, newPrice);

                case "P4":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product name: ");
                    newName = scanner.nextLine();
                    shop.updateProductName(newCode, newName);

                case "P5":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product section: ");
                    newSectionCode = Integer.parseInt(scanner.nextLine());
                    shop.updateProductSection(newCode, newSectionCode);

                case "P6":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product price: ");
                    newPrice = Integer.parseInt(scanner.nextLine());
                    shop.updateProductPrice(newCode, newPrice);

                case "P7":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    shop.deleteProduct(newCode);
            }
        }
    }

    public static void menu() {
        System.out.println("For section:");
        System.out.println("S1 - show all sections");
        System.out.println("S2 - add a new section");
        System.out.println("S3 - update a section");
        System.out.println("S4 - delete a section");
        System.out.println();

        System.out.println("For product:");
        System.out.println("P1 - show all products in section");
        System.out.println("P2 - add a new product");
        System.out.println("P3 - update a product");
        System.out.println("P4 - update a product name");
        System.out.println("P5 - update a product section");
        System.out.println("P6 - update a product price");
        System.out.println("P7 - delete a product");
        System.out.println();

    }
}