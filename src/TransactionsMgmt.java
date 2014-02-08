
import java.sql.SQLException;

/**
 * Class that uses both the database management class as well as the
 * transactions class to manage transactions
 *
 * @see DatabaseMgmt.java
 * @see Transactions.java
 *
 * @author Vasil Marinkov
 */
public class TransactionsMgmt {

    public static void newTransaction(Transactions transaction) throws SQLException {
        // log transaction into transactions
        // - amount to the current IBAN check that there is enough money in that smetka
        // + amount to the receivers IBAN (toiban) if its another ELUBank Client

        /*
         1 check if there is enough money in that account
         -- if there is enought money and toiban is not an internal iban -> just take that
         money out of total amount and log the transaction
         !!! also change lastupdate value in accounts
        
         -- if there is enought money and it is internal toiban ->
         check currency CODES
         normalize currencies
         take that amount from the current iban and log that transaction
         !!! also change lastupdate value in accounts
         add that amount to the receiver's account and log the transaction
        
         */
    }

    /**
     * Removes an existing transaction (DELETE from MySQL)
     *
     * @param egn - transaction that is going to be removed
     * @throws SQLException
     */
    public static void deleteTransactions(String egn) throws SQLException {

        DatabaseMgmt.execute("DELETE FROM transactions WHERE useregn = ?", egn);
    }
}
