
import java.io.Serializable;

/**
 * Class used for parsing currency information from the database table
 * "currencies" and serialize it to an object, that is being transferred between
 * the server and the client
 *
 * @author Vasil Marinkov
 */
public class Currency implements Serializable {

    public Currency[] allCurrencies = null;

    private static final long serialVersionUID = 1L;

    private String name, rate, code, ratio, reverserate, date;

    /**
     * @param request - client's request (ex: getAllCurrencyData)
     * @param response - server response (used only for exceptional cases, if
     * MySQL query fails for any reason)
     */
    private String request = null, response = null;

    /**
     * Creates an array that can be filled with all currency related data from
     * the database
     *
     * @param arraySize - the number of rolls in "currencies" table, in MySQL
     */
    public void getAllCurrencyData(int arraySize) {

        this.allCurrencies = new Currency[arraySize];

        for (int i = arraySize - 1; i >= 0; i--) {
            allCurrencies[ i] = new Currency();
        }
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
}