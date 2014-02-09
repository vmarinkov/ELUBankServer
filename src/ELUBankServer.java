
import java.io.IOException;
import java.sql.SQLException;
import java.util.Timer;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Containing the main class that starts server application and its GUI
 *
 * @author Vasil Marinkov & Miglen Evlogiev
 */
public class ELUBankServer {

    private static final Logger LOG = Logger.getLogger(ELUBankServer.class.getName());
    private static final ScheduledTasks newTask = new ScheduledTasks();
    private static final Timer time = new Timer();

    public static void main(String[] args) {

        try {
//            Transactions trs = new Transactions();
//            trs.setAmount("343.33");
//            trs.setCurrency("EUR");
//            trs.setIBAN("BG11ELUB16320299017157");
//            trs.setToIBAN("BG11ELUB16320338853268");
//            trs.setUserEGN("8612123738");
//            trs.setReceiver("Pe6o");
//            trs.setSubject("proba");
//            TransactionsMgmt.newTransaction(trs);

            // connects to the database
            DatabaseMgmt.connect();
            // Instantly update currencies and applies interests
            CurrencyMgmt.parseXML();
            AccountsMgmt.applyInterests();
            // Create a schedule that updates currencies and applies interests
            // every night at 23:59:00
            time.schedule(newTask, newTask.timeOfExecution());
            // Start the server
            LOG.info("Starting server...");
            new SSLServer().startServer();

            //  DatabaseMgmt.disconnect();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
