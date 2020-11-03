package domwcd0qi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
public class DomWCD0QI {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException {
        File xmlFile = new File("src/domwcd0qi/szemelyek.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);

        doc.getDocumentElement().normalize();

        System.out.println("Gyökér element: " + doc.getDocumentElement().getNodeName());

        NodeList nList = doc.getElementsByTagName("szemely");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);

            System.out.println("\nJelenlegi element" + nNode.getNodeName());

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                Element elem = (Element) nNode;

                String uid = elem.getAttribute("id");

                Node node1 = elem.getElementsByTagName("nev").item(0);
                String nev = node1.getTextContent();

                Node node2 = elem.getElementsByTagName("kor").item(0);
                String kor = node2.getTextContent();

                Node node3 = elem.getElementsByTagName("varos").item(0);
                String varos = node3.getTextContent();

                System.out.printf("Idd: %s%n", uid);
                System.out.printf("Név: %s%n", nev);
                System.out.printf("Kor: %s%n", kor);
                System.out.printf("Varos: %s%n", varos);
            }
        }
    }
}
