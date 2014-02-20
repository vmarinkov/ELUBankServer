
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
    private static boolean updateOnStart = false;
    private static boolean updateOnSchedule = true;

    public static void main(String[] args) {

        try {
            /**
             * Getting the settings from the configuration file and updating
             * them into the sub classes
             */
            IniParser settingi = new IniParser("config.ini");

            DatabaseMgmt.setSettings(
                    // Get the mysql host from config.ini file
                    settingi.getString("mysql", "mysql_host", "localhost"),
                    // Get the mysql database from config.ini file
                    settingi.getString("mysql", "mysql_db", "bank"),
                    // Get the mysql user from config.ini file
                    settingi.getString("mysql", "mysql_user", "root"),
                    // Get the mysql password from config.ini file
                    settingi.getString("mysql", "mysql_password", ""),
                    // Get the mysql encoding from config.ini file
                    settingi.getString("mysql", "mysql_encoding", "utf-8")
            );
            // connects to the database
            DatabaseMgmt.connect();

            // Get update values from config.ini file
            updateOnStart = Boolean.parseBoolean(settingi.getString("update",
                    "on_start_up", "false"));
            updateOnSchedule = Boolean.parseBoolean(settingi.getString("update",
                    "on_schedule", "false"));

            // Get the url of the xml file of BNB from config.ini file
            CurrencyMgmt.setbnbXmlUrl(settingi.getString("currency", "currency_xmlurl", ""));

            if (updateOnStart) {
                // Instantly update currencies and applies interests
                CurrencyMgmt.parseXML();
                AccountsMgmt.applyInterests();
            }

            if (updateOnSchedule) {
                // Get the scheduled time for the currencu from config.ini file
                ScheduledTasks.setExecutionTime(settingi.getString("update",
                        "update_time", "23:59:00"));
                // Create a schedule that updates currencies and applies interests
                time.schedule(newTask, newTask.when());
            }

            SSLServer.setSettings(
                    // Get the server port from config.ini file
                    settingi.getInt("server", "server_port", 80),
                    // Get the trustore password from config.ini file
                    settingi.getString("keystore", "keystore_pass", ""),
                    // Get the trustore location file from config.ini file
                    settingi.getString("keystore", "keystore_location", "")
            );
            // Start the server
            LOG.info("Starting server...");
            new SSLServer().startServer();

            //  DatabaseMgmt.disconnect();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
