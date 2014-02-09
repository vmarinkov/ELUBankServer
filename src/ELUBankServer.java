
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Containing the main class that starts server application and its GUI
 *
 * @author Vasil Marinkov & Miglen Evlogiev
 */
public class ELUBankServer {

    private static final Logger LOG = Logger.getLogger(ELUBankServer.class.getName());

    public static void main(String[] args) {

        try {
//            //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
//            String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime());
//            // dateAndTime[0] - date, dateAndTime[1] - time
//            String dateAndTime[] = timeStamp.split(" ");
//            // date[0] - year, date[1] - month, date[2] - day
//            String date[] = dateAndTime[0].split(".");
//            // time[0] - hour, time[1] - minutes, time[2] - seconds 
//            String time[] = dateAndTime[1].split(":");

//            Transactions trs = new Transactions();
//            trs.setAmount("343.33");
//            trs.setCurrency("EUR");
//            trs.setIBAN("BG11ELUB16320299017157");
//            trs.setToIBAN("BG11ELUB16320338853268");
//            trs.setUserEGN("8612123738");
//            trs.setReceiver("Pe6o");
//            trs.setSubject("proba");
//            TransactionsMgmt.newTransaction(trs);
            DatabaseMgmt.connect();
            CurrencyMgmt.parseXML();
            AccountsMgmt.applyInterests();
            LOG.info("Starting server...");
            new SSLServer().startServer();

            //  DatabaseMgmt.disconnect();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
