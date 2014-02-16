
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Schedule tasks which has to be run at a given point while the server runs
 *
 * @author Vasil Marinkov
 */
public class ScheduledTasks extends TimerTask {

    private static final Logger LOG = Logger.getLogger(ELUBankServer.class.getName());

    private static String executionTime = "23:59:00";

    /**
     * Sets when the schedule should be executed
     *
     * @param when exact time of execution HH:MM:SS
     */
    public static void setExecutionTime(String when) {
        executionTime = when;
    }

    /**
     * Returns the schedule time
     *
     * @return the time (hour, minutes, seconds) at which the task should run
     */
    public Date when() {

        String[] _executionTime = executionTime.split(":");

        int hour = Integer.parseInt(_executionTime[0].toString());
        int min = Integer.parseInt(_executionTime[1].toString());
        int sec = Integer.parseInt(_executionTime[2].toString());

        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, min);
        calendar.set(Calendar.SECOND, sec);
        Date time = calendar.getTime();

        return time;
    }

    /**
     * Currently runs currency and interests updates
     */
    @Override
    public void run() {
        try {
            LOG.info("Starting daily currency and interests update...");
            CurrencyMgmt.parseXML();
            AccountsMgmt.applyInterests();
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
