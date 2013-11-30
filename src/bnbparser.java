
/**
 * This class is parsing the xml from BNB and updates the currency in the
 * database
 *
 * @author Miglen Evlogiev, Vasil Marinkov
 * @version 0.1
 */

import javax.xml.parsers.*;
import org.w3c.dom.*;
import java.io.*;
import java.net.*;

public class bnbparser {

    /**
     * This function downloads the xml file from bnb.bg to a local copy.
     *
     * @param num The value to be squared.
     * @return nothing
     */
    private DatabaseMgmt databaseDatabaseMgmtw = new DatabaseMgmt();

    public void DownBnbXml(String filename) throws Exception {

        String bnbxml = "http://www.bnb.bg/Statistics/StExternalSector/StExchangeRates/StERForeignCurrencies/?download=xml&search=&lang=EN";

        BufferedInputStream in = null;
        FileOutputStream fout = null;
        try {
            in = new BufferedInputStream(new URL(bnbxml).openStream());
            fout = new FileOutputStream(filename);

            byte data[] = new byte[1024];
            int count;
            while ((count = in.read(data, 0, 1024)) != -1) {
                fout.write(data, 0, count);
            }
        } finally {
            if (in != null) {
                in.close();
            }
            if (fout != null) {
                fout.close();
            }
        }
    }

    public String checkElement(Element eElement, String tagname, String defval) {

        if (eElement.getElementsByTagName(tagname).getLength() > 0) {
            return eElement.getElementsByTagName(tagname).item(0).getTextContent();
        }

        return defval;
    }

    public void parseXML() {
        try {

            //DownBnbXml("bnb.xml");
            File fXmlFile = new File("bnb.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            String ratio = "";
            String rate = "";
            String reverserate = "";
            String code = "";
            String name = "";

	//optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("ROW");

            System.out.println("----------------------------");

            for (int temp = 0; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("\nCurrent Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    ratio = checkElement(eElement, "RATIO", "1");
                    rate = checkElement(eElement, "RATE", "1");
                    reverserate = checkElement(eElement, "REVERSERATE", "1");
                    code = checkElement(eElement, "CODE", "1");
                    name = checkElement(eElement, "NAME_", "");

                           // if(eElement.getElementsByTagName("RATIO").getLength() > 0) System.out.println("RATIO : " + eElement.getElementsByTagName("RATIO").getLength()); 
                    // if(eElement.getElementsByTagName("RATE").getLength() > 0) System.out.println("RATE : " + eElement.getElementsByTagName("RATE").item(0).getTextContent());
 //   database.Execute("UPDATE `proekt1`.`currencies` SET `rate`='" + rate  + "',`reverserate`='" + reverserate  + "',`ratio`='" + ratio + "',`date`='2013-11-17' WHERE `code`='" + code  + "'");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
