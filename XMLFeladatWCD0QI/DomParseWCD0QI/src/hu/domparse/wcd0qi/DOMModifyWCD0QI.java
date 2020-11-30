package hu.domparse.wcd0qi;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class DOMModifyWCD0QI {

    public static void main(String[] args) throws ParserConfigurationException, IOException, SAXException, TransformerException {
        File xmlFile = new File("src/hu/domparse/wcd0qi/XMLWCD0QI.xml"); //xml fájl bekérése
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //olvasás lehetővé tétele
        DocumentBuilder dBuilder = factory.newDocumentBuilder();
        Document doc = dBuilder.parse(xmlFile);
        doc.getDocumentElement().normalize();
        int rendelesekSzama = doc.getElementsByTagName("geprendeles").getLength(); //rendelések számának lekérdezése
        System.out.println("XML Módosítása");
        System.out.println("Adja meg mit szeretne módosítani: ");
        System.out.println("1 Specifikacio adatok módosítása\n2 Szamitogep alap adatok módosítása\n3 Geprendelés darabszámának módosítása\n4 Vasarlo adatainak módosítása");
        Modify(doc, rendelesekSzama);

    }

    public static void ModifyXML(Document doc) throws TransformerException {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        StreamResult result = new StreamResult(new File("src/hu/domparse/wcd0qi/XMLWCD0QI.xml"));
        transformer.transform(source, result);
    }
    //Modify-ban a felhasználót megkérdezzük, hogy a rendelés mely adatát kívánja módosítani
    public static void Modify(Document doc, int rendelesszam) throws TransformerException {
        Scanner scan = new Scanner(System.in);
        System.out.println("Adja meg a sorszamot: ");
        int readCategory = scan.nextInt();

        switch (readCategory) {
            case 1:
                ModifySpecification(doc, rendelesszam);
                break;
            case 2:
                ModifySzamitogep(doc, rendelesszam);
                break;
            case 3:
                Geprendeles(doc, rendelesszam);
                break;
            case 4:
                ModifyVasarlo(doc, rendelesszam);
                break;

        }
    }

    private static void ModifyVasarlo(Document doc, int rendelesszam) throws TransformerException {
        //Kiiratjuk a jelenlegi vásárlókat, majd lekérdezzük melyiket kívánja módosítani.
        System.out.println("Melyik vásárló adatait szeretné módosítani?");
        for (int i = 0; i <= rendelesszam; i++) {
            System.out.println(i + ". vásárló");
            DOMReadWCD0QI.ReadVasarloById(doc, String.valueOf(i));
            System.out.println("-------------------------------------------");
        }
        String id = ReadId();
        //Bekérjük az új adatokat
        Scanner sc = new Scanner(System.in);
        System.out.print("Név ");
        String nev = sc.nextLine();
        System.out.print("Telefonszám:  ");
        String telefonszam = sc.nextLine();
        System.out.print("E-mail cím: ");
        String email = sc.nextLine();
        System.out.print("Irányítószám: ");
        String irsz = sc.nextLine();
        System.out.print("Város: ");
        String varos = sc.nextLine();
        System.out.print("Utca: ");
        String utca = sc.nextLine();
        System.out.print("Házszám: ");
        String hazszam = sc.nextLine();
        System.out.print("Emelet: ");
        String emelet = sc.nextLine();
        System.out.print("Ajtó: ");
        String ajto = sc.nextLine();
        //lekérdezzük az Elementeket, majd setTextContent-el módosítjuk.
        NodeList nodeList = doc.getElementsByTagName("vasarlo");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                String sid = element.getAttribute("id");
                if (sid.equals(id)) {
                    Node node1 = element.getElementsByTagName("nev").item(0);
                    node1.setTextContent(nev);

                    Node node2 = element.getElementsByTagName("telefonszam").item(0);
                    node2.setTextContent(telefonszam);

                    Node node3 = element.getElementsByTagName("email").item(0);
                    node3.setTextContent(email);

                    Node node4 = element.getElementsByTagName("irsz").item(0);
                    node4.setTextContent(irsz);

                    Node node5 = element.getElementsByTagName("varos").item(0);
                    node5.setTextContent(varos);
                    Node node6 = element.getElementsByTagName("utca").item(0);
                    node6.setTextContent(utca);
                    Node node7 = element.getElementsByTagName("hazszam").item(0);
                    node7.setTextContent(hazszam);
                    Node node8 = element.getElementsByTagName("emelet").item(0);
                    node8.setTextContent(emelet);
                    Node node9 = element.getElementsByTagName("ajto").item(0);
                    node9.setTextContent(ajto);

                    System.out.println("Sikeres módosítás");
                }
            }
        }
        ModifyXML(doc); //Létrehozzuk az XML-t
    }

    private static void Geprendeles(Document doc, int rendelesszam) throws TransformerException {
        System.out.println("Melyik rendelés darabszámát kívánja módosítani?");
        DOMReadWCD0QI.Read(doc);

        String id = ReadId();

        Scanner sc = new Scanner(System.in);
        System.out.print("Darabszám");
        String db = sc.nextLine();


        NodeList nodeList = doc.getElementsByTagName("geprendeles");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                String sid = element.getAttribute("id");
                if (sid.equals(id)) {
                    Node node1 = element.getElementsByTagName("darab").item(0);
                    node1.setTextContent(db);
                    System.out.println("Sikeres módosítás");

                }
            }
        }
        ModifyXML(doc);
    }

    private static void ModifySzamitogep(Document doc, int rendelesszam) throws TransformerException {
        System.out.println("Melyik számítógépet szeretné módosítani?");
        for (int i = 0; i <= rendelesszam; i++) {
            System.out.println(i + ". számítógép");
            DOMReadWCD0QI.ReadSzamitogepById(doc, String.valueOf(i));
            System.out.println("-------------------------------------------");
        }
        String id = ReadId();

        Scanner sc = new Scanner(System.in);
        System.out.print("Ár");
        String ar = sc.nextLine();
        System.out.print("Elnevezés: ");
        String elnevezes = sc.nextLine();
        System.out.print("Kategória: ");
        String kategoria = sc.nextLine();


        NodeList nodeList = doc.getElementsByTagName("szamitogep");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                String sid = element.getAttribute("id");
                if (sid.equals(id)) {
                    Node node1 = element.getElementsByTagName("ar").item(0);
                    node1.setTextContent(ar);

                    Node node2 = element.getElementsByTagName("elnevezes").item(0);
                    node2.setTextContent(elnevezes);

                    Node node3 = element.getElementsByTagName("kategoria").item(0);
                    node3.setTextContent(kategoria);


                    System.out.println("Sikeres módosítás");

                }
            }
        }
        ModifyXML(doc);
    }

    public static String ReadId() {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nid:");
        String id = sc.nextLine();
        return id;
    }

    private static void ModifySpecification(Document doc, int rendelesSzam) throws TransformerException {
        System.out.println("Melyik specifikációt szeretné módosítani?");
        for (int i = 0; i <= rendelesSzam; i++) {
            System.out.println(i + ". specifikacio");
            DOMReadWCD0QI.ReadSpecifikacioById(doc, String.valueOf(i));
            System.out.println("-------------------------------------------");
        }
        String id = ReadId();

        Scanner sc = new Scanner(System.in);
        System.out.print("Alaplap ");
        String alaplap = sc.nextLine();
        System.out.print("Videókártya ");
        String videkoartya = sc.nextLine();
        System.out.print("Memória: ");
        String memoria = sc.nextLine();
        System.out.print("Tápegység: ");
        String tapegyseg = sc.nextLine();
        System.out.print("Processzor: ");
        String processzor = sc.nextLine();
        System.out.print("Tarhely: ");
        String tarhely = sc.nextLine();
        System.out.print("Egyeb: ");
        String egyeb = sc.nextLine();

        NodeList nodeList = doc.getElementsByTagName("specifikacio");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node nNode = nodeList.item(i);

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) nNode;

                String sid = element.getAttribute("id");
                if (sid.equals(id)) {
                    Node node1 = element.getElementsByTagName("alaplap").item(0);
                    node1.setTextContent(alaplap);

                    Node node2 = element.getElementsByTagName("videokartya").item(0);
                    node2.setTextContent(videkoartya);

                    Node node3 = element.getElementsByTagName("memoria").item(0);
                    node3.setTextContent(memoria);

                    Node node4 = element.getElementsByTagName("tapegyseg").item(0);
                    node4.setTextContent(tapegyseg);

                    Node node5 = element.getElementsByTagName("processzor").item(0);
                    node5.setTextContent(processzor);
                    Node node6 = element.getElementsByTagName("tarhely").item(0);
                    node6.setTextContent(tarhely);
                    Node node7 = element.getElementsByTagName("egyeb").item(0);
                    node7.setTextContent(egyeb);

                    System.out.println("Sikeres módosítás");

                }
            }
        }
        ModifyXML(doc);
    }


}
