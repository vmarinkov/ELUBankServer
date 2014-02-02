
import java.sql.SQLException;

/**
 * Class that uses both the database management class as well as the accounts
 * class to manage user's transaction accounts
 *
 * @see DatabaseMgmt.java
 * @see Accounts.java
 *
 * @author Vasil Marinkov
 */
public class AccountsMgmt {

    /**
     * Crates a new transaction account (INSERT into MySQL)
     *
     * @param account - object containing all new account data
     * @throws SQLException
     */
    public static void createUserAccount(Accounts account) throws SQLException {

        String[] columnNames = {"useregn", "accounttype", "iban", "amount", "currency"};

        String[] newAccount;
        newAccount = account.toStringArray();

        DatabaseMgmt.execute("INSERT INTO accounts VALUES"
                + "(?, ?, ?, ?, ?)", columnNames, newAccount);
    }
}
