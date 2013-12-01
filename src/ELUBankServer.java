
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Containing the main class
 * This is where both the Server and Administrator's GUI
 * will be started
 * 
 * @author ELUBank team
 */
public class ELUBankServer {

    public static void main(String[] args) {

        try {
            DatabaseMgmt.Connect();
            Server srv = new Server();
            srv.startServer();

//            String[] newUser = {null, "test22", "test33", "Pesho", "Petrov", "Kirilov",
//                "1341", "Sofia 300", "08989", "pesho@bank.com",
//                "0", "1341", "324"};
//            UserMgmt.createUser(newUser);
//            // UserMgmt.activateUser("1341");
//            if (UserMgmt.login("test22", "test33")) {
//                System.out.println("SUCCESSFUL LOGIN!");
//            } else {
//                System.out.println("Wrong login credentials or unexisting user!");
//            }
//
//            // start printing the user info
//            ResultSet _resResultSet = UserMgmt.getUser("1341");
//
//            String name = null, sirname = null, familyname = null;
//            String address = null, email = null;
//            int egn = 0, phone = 0;
//
//            while (_resResultSet.next()) {
//                name = _resResultSet.getString("name");
//                sirname = _resResultSet.getString("sirname");
//                familyname = _resResultSet.getString("familyname");
//                address = _resResultSet.getString("address");
//                email = _resResultSet.getString("phone");
//                egn = _resResultSet.getInt("egn");
//                phone = _resResultSet.getInt("phone");
//            }
//
//            System.out.printf("Name: %1$s  Sirname: %2$s  Family Name: %3$s \n",
//                    name, sirname, familyname);
//            System.out.printf("Address: %1$s  Phone: %2$d \n", address, phone);
//            System.out.printf("EGN: %1$d \nemail: %2$s \n", egn, email);
//            //end printing the user info

            //  UserMgmt.deleteUser("1341");
            //  Thread.sleep(120000); // sleep for 2 minutes
            //  DatabaseMgmt.Disconnect();
        } catch (SQLException ex) {
            //System.out.println(ex.getMessage());
            Logger lgr = Logger.getLogger(ELUBankServer.class.getName());
            lgr.log(Level.SEVERE, ex.getMessage(), ex);
        } catch (ClassNotFoundException ex) {
            //   Logger.getLogger(ELUBank_Server.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(ELUBankServer.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
