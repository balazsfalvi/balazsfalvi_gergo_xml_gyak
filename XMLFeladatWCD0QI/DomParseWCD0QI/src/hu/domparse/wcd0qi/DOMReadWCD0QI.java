package hu.domparse.wcd0qi;

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

public class DOMReadWCD0QI {

    public static void main(String[] args) {

        try {
            File xmlFile = new File("src/hu/domparse/wcd0qi/XMLWCD0QI.xml"); //fájl, amiből olvasunk
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance(); //XML dokumentumból DOM objektum - lehetővé tétele
            DocumentBuilder dBuilder = factory.newDocumentBuilder(); //XML fájl, Document lekéréséhez
            Document doc = dBuilder.parse(xmlFile); //dokument lekérése
            doc.getDocumentElement().normalize();
            System.out.println("Számítógép rendelések lekérése");
            Read(doc); //fő metódus, meghívódik a Read
        } catch (ParserConfigurationException pce) {
            pce.printStackTrace();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } catch (SAXException sae) {
            sae.printStackTrace();
        }
    }

    public static void Read(Document doc) {

        NodeList nList = doc.getElementsByTagName("geprendeles"); //géprendelés taggal rendelkező elemek lekérése listába

        for (int i = 0; i < nList.getLength(); i++) { //listán végigmegyünk
            Node nNode = nList.item(i); //lekérjük a lista aktuális elemét, Elementé konvertáljuk
            Element element = (Element) nNode;
            //Lekérjük az attribútumokat, majd azok segítségével meghívjuk a definiált metódusokat
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                String darab = element.getElementsByTagName("darab").item(0).getTextContent(); //darabszám lekérdezése
                String szamitogepId = element.getAttribute("szamitogepId");
                String rendelesId = element.getAttribute("rendelesId");
                System.out.println("\n-----------------------------------" + (i + 1) + ". rendelés-----------------------------------");
                System.out.println("\n\tDarab:\t" + darab);
                ReadSzamitogepById(doc, szamitogepId);
                ReadRendelesById(doc, rendelesId);
            }
        }
    }
    //fa struktúra miatt az attribútumban megadott id alapján kérdezzük le az egyes rendeléshez tartozó elemeket
    // A legtöbb objektum rendelkezik leszármazottal, amelyet egy újabb metódus kérdez le, az attrubútumban megadott ID alapján
    public static void ReadEladoById(Document doc, String id) {

        NodeList nList = doc.getElementsByTagName("elado");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            Element element = (Element) nNode;

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                if (element.getAttribute("id").equals(id)) {
                    String name = element.getElementsByTagName("nev").item(0).getTextContent();
                    String email = element.getElementsByTagName("email").item(0).getTextContent();
                    String telszam = element.getElementsByTagName("telszam").item(0).getTextContent();
                    String telepules = element.getElementsByTagName("telepules").item(0).getTextContent();
                    String ertekeles = element.getElementsByTagName("ertekeles").item(0).getTextContent();
                    System.out.println("Eladó adatok: \n\tNév:\t" + name + "\n\tEmail:\t" + email + "\n\tTelefonszám:\t" + telszam
                            + "\n\tTelepülés:\t" + telepules + "\n\tElégedettségi érték:\t" + ertekeles);
                }
            }
        }

    }

    public static void ReadSzamitogepById(Document doc, String id) {
        NodeList nList = doc.getElementsByTagName("szamitogep");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            Element element = (Element) nNode;

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                if (element.getAttribute("id").equals(id)) {
                    String ar = element.getElementsByTagName("ar").item(0).getTextContent();
                    String elnevezes = element.getElementsByTagName("elnevezes").item(0).getTextContent();
                    String kategoria = element.getElementsByTagName("kategoria").item(0).getTextContent();
                    String specId = element.getAttribute("specId");
                    System.out.println("Számítógép adatok: \n\tÁr:\t" + ar + "\n\tElnevezes:\t" + elnevezes + "\n\tKategoria:\t" + kategoria);
                    ReadSpecifikacioById(doc, specId);
                }
            }
        }

    }


    public static void ReadRendelesById(Document doc, String id) {

        NodeList nList = doc.getElementsByTagName("rendeles");

        for (int i = 0; i < nList.getLength(); i++) {
            Node nNode = nList.item(i);
            Element element = (Element) nNode;

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                if (element.getAttribute("id").equals(id)) {
                    String ar = element.getElementsByTagName("datum").item(0).getTextContent();

                    String eladoId = element.getAttribute("eladoId");
                    String vasarloId = element.getAttribute("vasarloId");

                    System.out.println("Rendelés adatok: \n\tDátum:\t" + ar);
                    ReadEladoById(doc, eladoId);
                    ReadVasarloById(doc, vasarloId);
                }
            }
        }
    }

    public static void ReadVasarloById(Document doc, String id) {

        NodeList nList = doc.getElementsByTagName("vasarlo");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            Element element = (Element) nNode;

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                if (element.getAttribute("id").equals(id)) {
                    String name = element.getElementsByTagName("nev").item(0).getTextContent();
                    String email = element.getElementsByTagName("email").item(0).getTextContent();
                    String telszam = element.getElementsByTagName("telefonszam").item(0).getTextContent();
                    String irsz = element.getElementsByTagName("irsz").item(0).getTextContent();
                    String varos = element.getElementsByTagName("varos").item(0).getTextContent();
                    String utca = element.getElementsByTagName("utca").item(0).getTextContent();
                    String hazszam = element.getElementsByTagName("hazszam").item(0).getTextContent();
                    String emelet = element.getElementsByTagName("emelet").item(0).getTextContent();
                    String ajto = element.getElementsByTagName("ajto").item(0).getTextContent();

                    System.out.println("Eladó adatok: \n\tNév:\t" + name + "\n\tEmail:\t" + email + "\n\tTelefonszám:\t" + telszam
                            + "\n\tLakcím:\t" + irsz + ',' + varos + ',' + utca + " utca," + hazszam + "(emelet:" + emelet + " ajto:" + ajto + ")");
                }
            }
        }
    }

    public static void ReadSpecifikacioById(Document doc, String id) {

        NodeList nList = doc.getElementsByTagName("specifikacio");

        for (int i = 0; i < nList.getLength(); i++) {

            Node nNode = nList.item(i);
            Element element = (Element) nNode;

            if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                if (element.getAttribute("id").equals(id)) {
                    String alaplap = element.getElementsByTagName("alaplap").item(0).getTextContent();
                    String videokartya = element.getElementsByTagName("videokartya").item(0).getTextContent();
                    String memoria = element.getElementsByTagName("memoria").item(0).getTextContent();
                    String tapegyseg = element.getElementsByTagName("tapegyseg").item(0).getTextContent();
                    String processzor = element.getElementsByTagName("processzor").item(0).getTextContent();
                    String tarhely = element.getElementsByTagName("tarhely").item(0).getTextContent();
                    String egyeb = element.getElementsByTagName("egyeb").item(0).getTextContent();

                    System.out.println("Specifikacio adatok: \n\tAlaplap:\t" + alaplap + "\n\tVideokartya:\t" + videokartya + "\n\tMemoria:\t" + memoria
                            + "\n\tTapegyseg:\t" + tapegyseg + "\n\tProcesszor:\t" + processzor + "\n\tTarhely:\t" + tarhely + "\n\tEgyéb:\t" + egyeb);
                }
            }
        }
    }
}

