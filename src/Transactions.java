
import java.io.Serializable;

public class Transactions implements Serializable {

    static final long serialVersionUID = 1L;

    private String username;

    private String request = null, response = null;

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
