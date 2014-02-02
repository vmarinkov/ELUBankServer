
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Containing the main class that starts server application and its GUI
 *
 * @author Vasil Marinkov & Miglen Evlogiev
 */
public class ELUBankServer {

    public static void main(String[] args) {

        try {
            //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime());
            // dateAndTime[0] - date, dateAndTime[1] - time
            String dateAndTime[] = timeStamp.split(" ");
            // date[0] - year, date[1] - month, date[2] - day
            String date[] = dateAndTime[0].split(".");
            // time[0] - hour, time[1] - minutes, time[2] - seconds 
            String time[] = dateAndTime[1].split(":");

            DatabaseMgmt.connect();
            CurrencyMgmt.parseXML(dateAndTime[0]);
            System.out.println("Starting server...");
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
