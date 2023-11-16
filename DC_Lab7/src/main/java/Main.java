
import data.Shop;
import org.xml.sax.SAXException;
import parser.Parser;

import javax.xml.transform.TransformerException;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws TransformerException, SAXException {
        Parser parser = new Parser();
        Shop shop = new Shop();
        shop = parser.readFromFile();

        Scanner scanner = new Scanner(System.in);
        String command;

        while (true) {
            System.out.println();
            System.out.println("M- Open menu");
            System.out.println("0- Exit");
            command = scanner.nextLine();

            switch (command) {
                case "M" -> menu();
                case "0" -> System.exit(0);
            }

            command = scanner.nextLine();
            switch (command) {
                case "S1":
                    System.out.println("All sections:");
                    shop.showAllSections();
                    break;

                case "S2":
                    System.out.println("Enter section code: ");
                    int newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter section name: ");
                    String newName = scanner.nextLine();
                    shop.addSection(newCode, newName);
                    break;

                case "S3":
                    System.out.println("Enter section code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new section name: ");
                    newName = scanner.nextLine();
                    shop.updateSection(newCode, newName);
                    break;

                case "S4":
                    System.out.println("Enter section code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    shop.deleteSection(newCode);
                    break;

                case "P1":
                    System.out.println("Enter section code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("All products in this section:");
                    shop.showAllProductsInSection(newCode);
                    break;

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
                    break;

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
                    break;

                case "P4":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product name: ");
                    newName = scanner.nextLine();
                    shop.updateProductName(newCode, newName);
                    break;

                case "P5":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product section: ");
                    newSectionCode = Integer.parseInt(scanner.nextLine());
                    shop.updateProductSection(newCode, newSectionCode);
                    break;

                case "P6":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    System.out.println("Enter new product price: ");
                    newPrice = Integer.parseInt(scanner.nextLine());
                    shop.updateProductPrice(newCode, newPrice);
                    break;

                case "P7":
                    System.out.println("Enter product code: ");
                    newCode = Integer.parseInt(scanner.nextLine());
                    shop.deleteProduct(newCode);
                    break;

                case "G1":
                    Parser.saveToFile(shop);
                    break;

                case "0":
                    System.exit(0);

                default :
                    System.out.println("Wrong command!");
                    break;
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

        System.out.println("General");
        System.out.println("G1 - Save all data");
        System.out.println("0 - Exit");
    }
}