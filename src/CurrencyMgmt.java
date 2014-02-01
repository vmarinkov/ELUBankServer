
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author lmn7
 */
public class CurrencyMgmt {

    /**
     *
     * @param currency
     * @throws SQLException
     */
    public static void getCurrencies(Currency currency) throws SQLException {

        ResultSet _resultSet;

        _resultSet = DatabaseMgmt.select("SELECT count(*) FROM currencies");

        while (_resultSet.next()) {
            currency.createNewCurrenciesArray(_resultSet.getInt(1));
        }

        System.out.println("Total of " + currency.allCurrencies.length + " currencies found");

        _resultSet = DatabaseMgmt.select("SELECT * FROM currencies");

        // EXCTRACT ALL CURRENCIES INFO OUT OF THE CURRENCIES TABLE
        for (Currency currentCurrency : currency.allCurrencies) {
            _resultSet.next();
            currentCurrency.setName(_resultSet.getString("name"));
            currentCurrency.setRate(_resultSet.getString("rate"));
            currentCurrency.setCode(_resultSet.getString("code"));
            currentCurrency.setRatio(_resultSet.getString("ratio"));
            currentCurrency.setReverserate(_resultSet.getString("reverserate"));
            currentCurrency.setDate(_resultSet.getString("date"));
        }

        for (int i = 0; i < currency.allCurrencies.length; i++) {
            System.out.println(currency.allCurrencies[i].getName());
        }
    }

    /**
     * This function downloads the XML file from bnb.bg to a local copy.
     *
     * @param filename
     * @throws MalformedURLException
     * @throws IOException
     */
    public static void downloadBnbXml(String filename) throws MalformedURLException, IOException {

        String bnbXmlUrl = "http://www.bnb.bg/Statistics/StExternalSector/"
                + "StExchangeRates/StERForeignCurrencies/?download=xml&search=&lang=EN";

        BufferedInputStream in = null;
        FileOutputStream fout = null;

        try {

            in = new BufferedInputStream(new URL(bnbXmlUrl).openStream());
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

    /**
     * Used to parse the data from the XML file
     *
     * @param eElement
     * @param tagName
     * @param defVal
     * @return values needed
     */
    public static String checkElement(Element eElement, String tagName, String defVal) {

        if (eElement.getElementsByTagName(tagName).getLength() > 0) {
            return eElement.getElementsByTagName(tagName).item(0).getTextContent();
        }

        return defVal;
    }

    /**
     * Downloads the bnb.xml file and then parses it and inserts the data into
     * currencies table in MySQL
     *
     * @param date - the date that the update occurs
     * @see downloadBnbXml method
     */
    public static void parseXML(String date) {

        try {

            downloadBnbXml("bnb.xml");
            File fXmlFile = new File("bnb.xml");

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            String ratio, rate, reverserate, code, name;

            //optional, but recommended
            //read this - http://stackoverflow.com/questions/13786607/normalization-in-dom-parsing-with-java-how-does-it-work
            doc.getDocumentElement().normalize();

            System.out.println("Root element :" + doc.getDocumentElement().getNodeName());

            NodeList nList = doc.getElementsByTagName("ROW");

            System.out.println("----------------------------");

            DatabaseMgmt.execute("DELETE FROM currencies");

            for (int temp = 1; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                System.out.println("Current Element :" + nNode.getNodeName());

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    ratio = checkElement(eElement, "RATIO", "1");
                    rate = checkElement(eElement, "RATE", "1");
                    reverserate = checkElement(eElement, "REVERSERATE", "1");
                    code = checkElement(eElement, "CODE", "1");
                    name = checkElement(eElement, "NAME_", "");

                    System.out.println(name + " " + rate + " " + code + " " + ratio + " " + reverserate);

                    String[] values = {name, rate, code, ratio, reverserate, date};

                    DatabaseMgmt.execute("INSERT INTO currencies VALUES (?, ?, ?, ?, ?, ?)", values);

//                    if (eElement.getElementsByTagName("RATIO").getLength() > 0) {
//                        System.out.println("RATIO : " + eElement.getElementsByTagName("RATIO").getLength());
//                    }
//                    if (eElement.getElementsByTagName("RATE").getLength() > 0) {
//                        System.out.println("RATE : " + eElement.getElementsByTagName("RATE").item(0).getTextContent());
//                    }
                }
            }
            fXmlFile.delete();
        } catch (IOException | ParserConfigurationException | SAXException | SQLException ex) {
            Logger.getLogger(CurrencyMgmt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
