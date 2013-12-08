
import java.io.Serializable;

public class User implements Serializable {

    static final long serialVersionUID = 1L;

    private String userId, username, password, name, sirname, familyname,
            egn, address, phone, email, userType, pinCode, isActive, request;

    boolean loggedIn = false;

    public User() {
    }

    public User(String userId, String username, String password, String name,
            String sirname, String familyname, String egn, String address,
            String phone, String email, String userType, String pinCode, String isActive) {
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.name = name;
        this.sirname = sirname;
        this.familyname = familyname;
        this.egn = egn;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.userType = userType;
        this.pinCode = pinCode;
        this.isActive = isActive;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRequest() {
        return request;
    }

    public void setRequest(String request) {
        this.request = request;
    }

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean isloggedIn) {
        this.loggedIn = true;
    }
}
