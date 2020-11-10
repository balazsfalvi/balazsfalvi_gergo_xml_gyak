package hu.meiit.xpathparsewcd0qi;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.*;
import java.io.IOException;

public class XpathParseWCD0QI {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setNamespaceAware(true); // never forget this!
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc = builder.parse("studentWCD0QI.xml");

        //Create XPath

        XPathFactory xpathfactory = XPathFactory.newInstance();
        XPath xpath = xpathfactory.newXPath();

        System.out.println("n//7) Get all");

        // 7) Get all writers
        XPathExpression firstNameExpr = xpath.compile("//student/firstname/text()");
        XPathExpression lastNameExpr = xpath.compile("//student/lastname/text()");
        XPathExpression nickNameExpr = xpath.compile("//student/nickname/text()");
        XPathExpression marksExpr = xpath.compile("//student/marks/text()");

        Object firstNameResult = firstNameExpr.evaluate(doc, XPathConstants.NODESET);
        Object lastNameResult = lastNameExpr.evaluate(doc, XPathConstants.NODESET);
        Object nickNameResult = nickNameExpr.evaluate(doc, XPathConstants.NODESET);
        Object marksResult = marksExpr.evaluate(doc, XPathConstants.NODESET);

        NodeList firstNameNodes = (NodeList) firstNameResult;
        NodeList lastNameNodes = (NodeList) lastNameResult;
        NodeList nickNodes = (NodeList) nickNameResult;
        NodeList marksNodes = (NodeList) marksResult;
        System.out.println( "-------------------------------");

        for (int i = 0; i < firstNameNodes.getLength(); i++) {
            System.out.println( firstNameNodes.item(i).getNodeValue());
            System.out.println(lastNameNodes.item(i).getNodeValue());
            System.out.println( nickNodes.item(i).getNodeValue());
            System.out.println( marksNodes.item(i).getNodeValue());
            System.out.println( "-------------------------------");

        }
    }
}
