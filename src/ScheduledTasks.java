
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

    private static final int _HOUR = 23;
    private static final int _MINUTE = 59;
    private static final int _SECOND = 0;

    /**
     * Returns the schedule time
     *
     * @return the time (hour, minutes, seconds) at which the task should run
     */
    public Date timeOfExecution() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, _HOUR);
        calendar.set(Calendar.MINUTE, _MINUTE);
        calendar.set(Calendar.SECOND, _SECOND);
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
