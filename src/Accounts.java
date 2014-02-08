
import java.io.Serializable;

/**
 * Class used for parsing account information from the database table "accounts"
 * and serialize it to an object, that is being transferred between the server
 * and the client
 *
 * @author Vasil Marinkov
 */
public class Accounts implements Serializable {

    public Accounts[] allAccounts = null;

    private static final long serialVersionUID = 1L;
    private static final String DELIMITER = "::";

    private String useregn, accounttype, iban, amount, currency, lastupdated;

    /**
     * @param request - client's request (ex: createNewClientAccount)
     * @param response - server response (used only for exceptional cases, if
     * MySQL query fails for any reason)
     */
    private String request = null, response = null;

    /**
     * Creates an array that can be filled with all account related data from
     * the database
     *
     * @param arraySize - the number of rolls in "accounts" table, in MySQL
     */
    public void initializeAccountsArray(int arraySize) {

        this.allAccounts = new Accounts[arraySize];

        for (int i = arraySize - 1; i >= 0; i--) {
            allAccounts[ i] = new Accounts();
        }
    }

    public String getUserEGN() {
        return useregn;
    }

    public void setUserEGN(String userEGN) {
        this.useregn = userEGN;
    }

    public String getAccountType() {
        return accounttype;
    }

    public void setAccountType(String accountType) {
        this.accounttype = accountType;
    }

    public String getIBAN() {
        return iban;
    }

    public void setIBAN(String iban) {
        this.iban = iban;
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
    
    public String getDateUpdated() {
        return lastupdated;
    }

    public void setDateUpdated(String lastUpdated) {
        this.lastupdated = lastUpdated;
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
        return useregn + DELIMITER + accounttype + DELIMITER + iban + DELIMITER
                + amount + DELIMITER + currency + DELIMITER + lastupdated;
    }

    public String[] toStringArray() {
        return (this.toString()).split(DELIMITER);
    }
}
