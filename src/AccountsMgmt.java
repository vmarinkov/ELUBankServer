
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
    public static void createBankingAccount(Accounts account) throws SQLException {
        
        String[] columnNames = {"useregn", "accounttype", "iban", "amount", "currency"};
        
        String[] newAccount;
        newAccount = account.toStringArray();
        
        DatabaseMgmt.execute("INSERT INTO accounts VALUES"
                + "(?, ?, ?, ?, ?)", columnNames, newAccount);
    }
    
    /**
     * Removes an existing transaction account (DELETE from MySQL)
     * 
     * @param account - the user's account that is going to be removed
     * @throws SQLException 
     */
    public static void deleteBankingAccount(Accounts account) throws SQLException {
        
        DatabaseMgmt.execute("DELETE FROM accounts WHERE iban = ? LIMIT 1", account.getIBAN());
    }
}
