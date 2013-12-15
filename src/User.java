
import java.io.Serializable;

public class User implements Serializable {

    static final long serialVersionUID = 1L;

    private String username, password, name, surname, familyname,
            egn, country, city, address, phone, email, userType, request;

    boolean loggedIn = false;

    public User() {
    }

    public User(String username, String password,
            String name, String surname, String familyname,
            String egn, String country, String city, String address,
            String phone, String email, String userType) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.familyname = familyname;
        this.egn = egn;
        this.country = country;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.userType = userType;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getFamilyname() {
        return familyname;
    }

    public void setFamilyname(String familyname) {
        this.familyname = familyname;
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
        this.loggedIn = isloggedIn;
    }
}
