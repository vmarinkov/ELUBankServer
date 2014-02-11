
import java.io.Serializable;

/**
 * Class used for parsing currency information from the database table
 * "currencies" and serialize it to an object, that is being transferred between
 * the server and the client
 *
 * @author Vasil Marinkov
 */
public class Currency implements Serializable {

    private Currency[] allCurrencies = null;

    private static final long serialVersionUID = 1L;
    private static final String DELIMITER = "::";

    /**
     * Representing column name from the database table "currencies"
     */
    private String name, rate, code, ratio, reverserate, date;

    /**
     * Client's request (ex: getAllCurrencyData)
     */
    private String request = null;

    /**
     * Server response is used only for exceptional cases (i.e. if MySQL query
     * fails).
     */
    private String response = null;

    /**
     * Creates an array that can be filled with all currency related data from
     * the database
     *
     * @param arraySize the number of rolls in "currencies" table, in MySQL
     */
    public void getAllCurrencyData(int arraySize) {

        this.allCurrencies = new Currency[arraySize];

        for (int i = arraySize - 1; i >= 0; i--) {
            allCurrencies[ i] = new Currency();
        }
    }

    public Currency[] getAllCurrencies() {
        return this.allCurrencies;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRate() {
        return rate;
    }

    public void setRate(String rate) {
        this.rate = rate;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getReverserate() {
        return reverserate;
    }

    public void setReverserate(String reverserate) {
        this.reverserate = reverserate;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
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
        StringBuilder sb = new StringBuilder();
        sb.append(name);
        sb.append(DELIMITER);
        sb.append(rate);
        sb.append(DELIMITER);
        sb.append(code);
        sb.append(DELIMITER);
        sb.append(ratio);
        sb.append(DELIMITER);
        sb.append(reverserate);
        sb.append(DELIMITER);
        sb.append(date);

        return sb.toString();
    }

    public String[] toStringArray() {
        return (this.toString()).split(DELIMITER);
    }
}
