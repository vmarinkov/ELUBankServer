
import java.io.Serializable;

/**
 * Class used for parsing user information from the database table "users", as
 * well as any data related to the specified user - accounts, transactions. This
 * data is being serialized an object, that is being transferred between the
 * server and the client
 *
 * @author Vasil Marinkov
 */
public class User implements Serializable {

    public User[] allUsers = null;
    // TODO - extend user class to create data for its own accounts/transactions
    public Accounts[] currnetUserAccounts = null;
    public Transactions[] currentUserTransactions = null;

    private static final long serialVersionUID = 1L;
    private static final String DELIMITER = "::";

    private String username, password, name, surname, familyname,
            egn, dayOfBirth, monthOfBirth, yearOfBirth, country,
            city, address, phone, email, userType;

    /**
     * @param request - client's request (ex: login, create)
     * @param response - server response (used only for exceptional cases, if
     * MySQL query fails for any reason)
     */
    private String request = null, response = null;
    private boolean loggedIn = false;

    public User() {
    }

    public User(String username, String password, String name, String surname,
            String familyname, String egn, String dayOfBirth, String monthOfBirth,
            String yearOfBirth, String country, String city, String address,
            String phone, String email, String userType) {

        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.familyname = familyname;
        this.egn = egn;
        this.dayOfBirth = dayOfBirth;
        this.monthOfBirth = monthOfBirth;
        this.yearOfBirth = yearOfBirth;
        this.country = country;
        this.city = city;
        this.address = address;
        this.phone = phone;
        this.email = email;
        this.userType = userType;
    }

    /**
     * Creates an array that can be filled with all user related data from the
     * database
     *
     * @param arraySize - the number of rolls in "users" table, in MySQL
     */
    public void getAllUserData(int arraySize) {

        this.allUsers = new User[arraySize];

        for (int i = arraySize - 1; i >= 0; i--) {
            allUsers[ i] = new User();
        }
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

    public String getEgn() {
        return egn;
    }

    public void setEgn(String egn) {
        this.egn = egn;
    }

    public String getDayOfBirth() {
        return dayOfBirth;
    }

    public void setDayOfBirth(String dayOfBirth) {
        this.dayOfBirth = dayOfBirth;
    }

    public String getMonthOfBirth() {
        return monthOfBirth;
    }

    public void setMonthOfBirth(String monthOfBirth) {
        this.monthOfBirth = monthOfBirth;
    }

    public String getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(String yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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

    public boolean getLoggedIn() {
        return loggedIn;
    }

    public void setLoggedIn(boolean isloggedIn) {
        this.loggedIn = isloggedIn;
    }

    @Override
    public String toString() {
        return username + DELIMITER + password + DELIMITER + name + DELIMITER + surname + DELIMITER
                + familyname + DELIMITER + egn + DELIMITER + dayOfBirth + DELIMITER
                + monthOfBirth + DELIMITER + yearOfBirth + DELIMITER + country + DELIMITER
                + city + DELIMITER + address + DELIMITER + phone + DELIMITER + email + DELIMITER
                + userType;
    }

    public String[] toStringArray() {
        return (this.toString()).split(DELIMITER);
    }
}