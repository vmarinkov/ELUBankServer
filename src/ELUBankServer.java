
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

//            User user = new User("test22", "test2", "Petar", "Pertrov", "Georgiev",
//                    "8612125557", "31", "12", "1966", "Bulgaria", "Sofia",
//                    "Sofia Ovcha Kupel 1 524 D", "0878989838", "petko@abv.bg", "3");
//            UserMgmt.createUser(user);
            //  UserMgmt.deleteUser("1341");
            //  DatabaseMgmt.disconnect();
        } catch (SQLException | ClassNotFoundException | IOException ex) {
            Logger.getLogger(ELUBankServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
