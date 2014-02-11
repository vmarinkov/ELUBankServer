
import java.io.Serializable;

/**
 * Class used for parsing transaction information from the database table
 * "transactions" and serialize it to an object, that is being transferred
 * between the server and the client
 *
 * @author Vasil Marinkov
 */
public class Transactions implements Serializable {

    public Transactions[] allTransactions = null;

    private static final long serialVersionUID = 1L;
    private static final String DELIMITER = "::";

    /**
     * Representing column name from the database table "transactions"
     */
    private String useregn, subject, receiver, iban, toiban,
            amount, currency, timestamp;

    /**
     * @param request client's request (ex: newTransaction, listTransactions)
     */
    private String request = null;

    /**
     * Server response is used only for exceptional cases (i.e. if MySQL query
     * fails).
     */
    private String response = null;

    /**
     * Creates an array that can be filled with all transaction related data
     * from the database
     *
     * @param arraySize the number of rolls in "transactions" table, in MySQL
     */
    public void initializeTransactionsArray(int arraySize) {

        this.allTransactions = new Transactions[arraySize];

        for (int i = arraySize - 1; i >= 0; i--) {
            allTransactions[ i] = new Transactions();
        }
    }

    public String getUserEGN() {
        return useregn;
    }

    public void setUserEGN(String userEGN) {
        this.useregn = userEGN;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getIBAN() {
        return iban;
    }

    public void setIBAN(String iban) {
        this.iban = iban;
    }

    public String getToIBAN() {
        return toiban;
    }

    public void setToIBAN(String toiban) {
        this.toiban = toiban;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public String getResponse() {
        return response;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    @Override
    public String toString() {
        return useregn + DELIMITER + subject + DELIMITER + receiver + DELIMITER
                + iban + DELIMITER + toiban + DELIMITER + amount + DELIMITER
                + currency + DELIMITER + timestamp;
    }

    public String[] toStringArray() {
        return (this.toString()).split(DELIMITER);
    }
}
