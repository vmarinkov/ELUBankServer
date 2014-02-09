import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

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

    /**
     * Execute a new transaction
     * 
     * @param transaction - object containing all transaction data
     * @return - object containing error response (if there is any)
     * @throws SQLException 
     */
    public static Transactions newTransaction(Transactions transaction) throws SQLException {
        //get rquestedAmount and availableAmount in BGN
        Accounts userAccount = AccountsMgmt.getAccountByIBAN(transaction.getIBAN());

        double requestedAmountInLeva = Double.parseDouble(transaction.getAmount());
        double availableAmountInLeva = Double.parseDouble(userAccount.getAmount());

        // requested amount in leva
        Currency requestedCurrencyInfo = CurrencyMgmt.getCurrencyByCode(transaction.getCurrency());
        if (!transaction.getCurrency().equalsIgnoreCase("BGN")) {
            requestedAmountInLeva *= Double.parseDouble(requestedCurrencyInfo.getRate());
        }

        // available amount in leva
        Currency availableCurrencyInfo = CurrencyMgmt.getCurrencyByCode(transaction.getCurrency());
        if (!transaction.getCurrency().equalsIgnoreCase("BGN")) {
            availableAmountInLeva *= Double.parseDouble(availableCurrencyInfo.getRate());
        }

        // Check if there is enough money for the transfer
        // 10 leva is the minimum amount that has to remain into the banking account
        // In case, it's not enough response will be returned and the method will exit
        if (requestedAmountInLeva > (availableAmountInLeva - 10)) {
            transaction.setResponse("amountNotEnough");
            return transaction;
        }

        // add the transaction, for the current user, into MySQL
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss").format(Calendar.getInstance().getTime());
        transaction.setTimestamp(timeStamp);
        String[] columnNames = {"useregn", "subject", "receiver", "iban", "toiban",
            "amount", "currency", "timestamp"};
        String[] newTransaction;
        newTransaction = transaction.toStringArray();
        DatabaseMgmt.execute("INSERT INTO transactions VALUES"
                + "(?, ?, ?, ?, ?, ?, ?, ?)", columnNames, newTransaction);

        // Update current user's baking account
        double newAmount = availableAmountInLeva - requestedAmountInLeva;
        if (!transaction.getCurrency().equalsIgnoreCase("BGN")) {
            newAmount *= Double.parseDouble(availableCurrencyInfo.getReverserate());
        }

        timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime());
        String[] values = {newAmount + "", timeStamp, userAccount.getIBAN()};
        DatabaseMgmt.execute("UPDATE accounts SET amount = ?, lastupdated = ? "
                + " WHERE iban = ?", values);

        // Check if the ToIBAN is internal or external
        // In case, it is internal - execute the transaction for the receiver as well
        if (Pattern
                .compile(Pattern.quote("ELUB"), Pattern.CASE_INSENSITIVE)
                .matcher(transaction.getToIBAN())
                .find()) {

            Accounts receiverAccout = AccountsMgmt.getAccountByIBAN(transaction.getToIBAN());

            // add the transaction, for the receiver, into MySQL
            transaction.setUserEGN(receiverAccout.getUserEGN());
            newTransaction = transaction.toStringArray();
            DatabaseMgmt.execute("INSERT INTO transactions VALUES"
                    + "(?, ?, ?, ?, ?, ?, ?, ?)", columnNames, newTransaction);

            // Update receiver's banking account
            double receiverAmountInLeva = Double.parseDouble(receiverAccout.getAmount());
            Currency receiverCurrencyInfo = CurrencyMgmt.getCurrencyByCode(receiverAccout.getCurrency());
            if (!receiverAccout.getCurrency().equalsIgnoreCase("BGN")) {
                receiverAmountInLeva *= Double.parseDouble(receiverCurrencyInfo.getRate());
            }

            double receiverNewAmount = receiverAmountInLeva + requestedAmountInLeva;
            if (!receiverAccout.getCurrency().equalsIgnoreCase("BGN")) {
                receiverNewAmount *= Double.parseDouble(receiverCurrencyInfo.getReverserate());
            }

            String[] values2 = {receiverNewAmount + "", timeStamp, receiverAccout.getIBAN()};
            DatabaseMgmt.execute("UPDATE accounts SET amount = ?, lastupdated = ? "
                    + " WHERE iban = ?", values2);
        }

        return transaction;
    }

    /**
     * Removes all existing transactions (DELETE from MySQL)
     *
     * @param egn - all transactions containing that EGN
     * @throws SQLException
     */
    public static void deleteTransactions(String egn) throws SQLException {

        DatabaseMgmt.execute("DELETE FROM transactions WHERE useregn = ?", egn);
    }
}
