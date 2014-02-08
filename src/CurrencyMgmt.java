
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
 * Class that uses both the database management class as well as the currency
 * class to manage currency rates, codes etc.
 *
 * Also parses currency info form bnb.bg and then uploads it to the MySQL db
 *
 * @author Miglen Evlogiev & Vasil Marinkov
 */
public class CurrencyMgmt {

    /**
     *
     * @param currency - currency object
     * @return Currency object - containing all currency data extracted from
     * MySQL
     * @throws SQLException
     */
    public static Currency getAllCurrencyData(Currency currency) throws SQLException {

        ResultSet _resultSet;

        _resultSet = DatabaseMgmt.select("SELECT count(*) FROM currencies");

        while (_resultSet.next()) {
            currency.getAllCurrencyData(_resultSet.getInt(1));
        }

        // EXCTRACT ALL CURRENCIES DATA OUT OF THE CURRENCIES TABLE
        _resultSet = DatabaseMgmt.select("SELECT * FROM currencies");

        // EXCTRACT ALL CURRENCIES DATA OUT OF THE CURRENCIES TABLE
        for (Currency currentCurrency : currency.getAllCurrencies()) {
            _resultSet.next();
            currentCurrency.setName(_resultSet.getString("name"));
            currentCurrency.setRate(_resultSet.getString("rate"));
            currentCurrency.setCode(_resultSet.getString("code"));
            currentCurrency.setRatio(_resultSet.getString("ratio"));
            currentCurrency.setReverserate(_resultSet.getString("reverserate"));
            currentCurrency.setDate(_resultSet.getString("date"));
        }

        return currency;
    }

    /**
     * Returns currency info by its code
     *  
     * @param currnecyCode - valid currency code
     * @return - currency data
     * @throws SQLException 
     */
    public static Currency getCurrencyByCode(String currnecyCode) throws SQLException {

        ResultSet _resultSet = DatabaseMgmt.select("SELECT * FROM currencies"
                + " WHERE code = ?", currnecyCode);

        Currency currency = new Currency();

        if (_resultSet.next()) {
            currency.setName(_resultSet.getString("name"));
            currency.setRate(_resultSet.getString("rate"));
            currency.setCode(_resultSet.getString("code"));
            currency.setRatio(_resultSet.getString("ratio"));
            currency.setReverserate(_resultSet.getString("reverserate"));
            currency.setDate(_resultSet.getString("date"));
        }

        return currency;
    }

    /**
     * This function downloads the currency XML file from bnb.bg to a local
     * copy.
     *
     * @param filename - the name of the file that we be created
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
     * @return only the data needed
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
     * @param date - current date
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

            NodeList nList = doc.getElementsByTagName("ROW");

            DatabaseMgmt.execute("DELETE FROM currencies");
            DatabaseMgmt.execute("INSERT INTO currencies VALUES (?, ?, ?, ?, ?, ?)",
                    new String[]{"Euro", "1.95583", "EUR", "1", "0.511292", date});

            for (int temp = 1; temp < nList.getLength(); temp++) {

                Node nNode = nList.item(temp);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    ratio = checkElement(eElement, "RATIO", "1");
                    rate = checkElement(eElement, "RATE", "1");
                    reverserate = checkElement(eElement, "REVERSERATE", "1");
                    code = checkElement(eElement, "CODE", "1");
                    name = checkElement(eElement, "NAME_", "");

                    System.out.println(name + " " + rate + " " + code + " " + ratio + " " + reverserate);

                    String[] valuesDynamic = {name, rate, code, ratio, reverserate, date};

                    DatabaseMgmt.execute("INSERT INTO currencies VALUES (?, ?, ?, ?, ?, ?)", valuesDynamic);
                }
            }
            fXmlFile.delete();
        } catch (IOException | ParserConfigurationException | SAXException | SQLException ex) {
            Logger.getLogger(CurrencyMgmt.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
