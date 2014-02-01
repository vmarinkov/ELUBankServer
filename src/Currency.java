
import java.io.Serializable;

public class Currency implements Serializable {

    public Currency[] allCurrencies = null;

    private static final long serialVersionUID = 1L;

    private String name, rate, code, ratio, reverserate, date;

    private String request = null, response = null;

    public void createNewCurrenciesArray(int arraySize) {

        this.allCurrencies = new Currency[arraySize];

        while (arraySize > 0) {
            allCurrencies[arraySize - 1] = new Currency();
            arraySize--;
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