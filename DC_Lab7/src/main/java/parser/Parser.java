package parser;
import data.Product;
import data.Section;
import data.Shop;
import org.w3c.dom.NodeList;
import org.xml.sax.ErrorHandler;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXParseException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

public class Parser {

    public Shop readFromFile() {
        Shop shop = new Shop();
        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        dbf.setValidating(true);

        try {
            db = dbf.newDocumentBuilder();
            db.setErrorHandler(new SimpleErrorHandler());
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
        Document doc = null;

        try {
            doc = db.parse(new File("data.xml"));
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        Element root = doc.getDocumentElement();
        if (root.getTagName().equals("Shop")) {
           
            NodeList listSections = root.getElementsByTagName("Section");

            for (int i = 0; i < listSections.getLength(); i++) {

                Element sectionElement = (Element) listSections.item(i);
                int sectionCode = Integer.parseInt(sectionElement.getAttribute("code"));
                String sectionName = sectionElement.getAttribute("name");

                Section section = new Section(sectionCode, sectionName);
                shop.addSection(section);
                NodeList listProducts = sectionElement.getElementsByTagName("Product");

                for (int j = 0; j < listProducts.getLength(); j++) {

                    Element productElement = (Element) listProducts.item(j);
                    Product product = new Product(
                            Integer.parseInt(productElement.getAttribute("code")),
                            productElement.getAttribute("name"),
                            section,
                            Integer.parseInt(productElement.getAttribute("price"))
                    );
                    shop.addProduct(product);

                }

            }
        }
        return shop;
    }


    public static class SimpleErrorHandler implements ErrorHandler {
        public void warning(SAXParseException exception) {
            System.out.println("Warning: " + exception.getLineNumber());
            System.out.println(exception.getMessage());
        }

        public void error(SAXParseException exception) {
            System.out.println("Error: " + exception.getLineNumber());
            System.out.println(exception.getMessage());
        }

        public void fatalError(SAXParseException exception) throws SAXException {
            System.out.println("Fatal error: " + exception.getLineNumber());
            System.out.println(exception.getMessage());
        }
    }
}

