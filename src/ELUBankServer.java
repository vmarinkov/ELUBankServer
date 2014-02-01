
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
            //Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime());
            // dateAndTime[0] - date, dateAndTime[1] - time
            String dateAndTime[] = timeStamp.split(" ");
            // date[0] - year, date[1] - month, date[2] - day
            String year, month, day, date[] = dateAndTime[0].split(".");
            year = date[0];
            month = date[1];
            day = date[2];
            // time[0] - hour, time[1] - minutes, time[2] - seconds 
            String hours, minutes, seconds, time[] = dateAndTime[1].split(":");
            hours = time[0];
            minutes = time[1];
            seconds = time[2];

            DatabaseMgmt.connect();
            CurrencyMgmt.parseXML(dateAndTime[0]);
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
