
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Containing the main class This is where both the Server and Administrator's
 * GUI will be started
 *
 * @author ELUBank team
 */
public class ELUBankServer {

    public static void main(String[] args) {

        try {
            DatabaseMgmt.connect();
            System.out.println("Server started...");
            new SSLServer().startServer();

            //  UserMgmt.deleteUser("1341");
            //  DatabaseMgmt.disconnect();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Logger.getLogger(ELUBankServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
