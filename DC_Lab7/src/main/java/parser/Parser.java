package parser;
import data.Shop;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;

public class Parser {

    public void readFromFile() {

        DocumentBuilder db = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
            db = dbf.newDocumentBuilder();
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
            System.out.println("here");
            NodeList listSections = root.getElementsByTagName("Section");

            for (int i = 0; i < listSections.getLength(); i++) {

                Element section = (Element) listSections.item(i);
                String sectionCode = section.getAttribute("code");
                String sectionName = section.getAttribute("name");

                System.out.println(sectionCode + "\t" + sectionName + ":");

                NodeList listProducts = section.getElementsByTagName("Product");

                for (int j = 0; j < listProducts.getLength(); j++) {

                    Element product = (Element) listProducts.item(j);
                    String productName = product.getAttribute("name");
                    System.out.println("" + productName);
                }
            }
        }
    }

}
