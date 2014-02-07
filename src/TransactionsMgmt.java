
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
    }
}
