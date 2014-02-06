
import java.sql.*;
import java.math.BigInteger;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that uses both the database management class as well as the user class
 * to manage user accounts
 * 
 * @see DatabaseMgmt.java
 * @see User.java
 *
 * @author Miglen Evlogiev & Vasil Marinkov
 */
public class UserMgmt {

    /**
     * Crates a new user account (INSERT into MySQL)
     *
     * @param user - object containing all new user data
     * @throws SQLException
     */
    public static void createUser(User user) throws SQLException {

        String[] columnNames = {"username", "password", "name", "surname",
            "familyname", "egn", "bday", "bmonth", "byear", "country", "city",
            "address", "phone", "email", "usertype"};

        String[] newUser;
        newUser = user.toStringArray();
        newUser[1] = hashpass(newUser[1]);

        DatabaseMgmt.execute("INSERT INTO users VALUES"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                columnNames, newUser);
    }
    
    /**
     * Updates user account (UPDATE into MySQL)
     *
     * @param user - object containing all new user data
     * @throws SQLException
     */
    public static void updateUser(User user) throws SQLException {
        String[] allowedColumns = {"country","city","address","phone","email"};
        
        StringBuilder query = new StringBuilder();
        query.append("UPDATE users SET ");
        for (String colName: allowedColumns) {
            query.append(colName);
            query.append(" = ?,");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(" WHERE egn = ?");
        
        // System.out.println(query.toString());
        
        String[] values = {
            user.getCountry(),
            user.getCity(),
            user.getAddress(),
            user.getPhone(),
            user.getEmail(),
            user.getEgn()
        };
        
        DatabaseMgmt.execute(query.toString(), allowedColumns, values);
    }
    
//    /**
//     * Activates an existing user (UPDATE to MySQL)
//     *
//     * @param egn - Using EGN as a super key to specify which user should be
//     * activated
//     * @throws SQLException
//     */
//    public static void activateUser(String egn) throws SQLException {
//
//        String[] update = new String[2];
//        update[0] = "1";
//        update[1] = egn;
//
//        DatabaseMgmt.execute("UPDATE users SET isactive =(?)"
//                + " WHERE egn =(?) LIMIT 1", update);
//    }
    /**
     * Deletes an existing user (DELETE from MySQL)
     *
     * @param username - Using USERNAME as a super key to specify which user
     * should be deleted
     * @throws SQLException
     */
    public static void deleteUser(String username) throws SQLException {

        DatabaseMgmt.execute("DELETE FROM users WHERE username =(?) LIMIT 1", username);
    }

    /**
     * User logIn function
     *
     * @param username - Existing username from "users" in MySQl
     * @param password - Existing password from "users" in MySQl
     * @return boolean - "true" on successful login, else "false"
     * @throws SQLException
     */
    public static boolean login(String username, String password) throws SQLException {

        password = hashpass(password);

        String[] userCredentials = new String[2];
        userCredentials[0] = username;
        userCredentials[1] = password;

        ResultSet _resultSet = DatabaseMgmt.select("SELECT * FROM users "
                + "WHERE username =(?) AND password =(?)", userCredentials);

        if (_resultSet.next()) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all user information using its Username as key to SELECT it
     *
     * @param user - containing valid username of a user from "users" table in
     * MySQl
     * @return User - object filled with all user personal information
     * @throws SQLException
     * @see user.response: if no data found - "userNotFound" is returned as a
     * response
     */
    public static User getUserByUsername(User user) throws SQLException {

        ResultSet _resultSet;

        _resultSet = DatabaseMgmt.select("SELECT * FROM users "
                + "WHERE username =(?)", user.getUsername());

        if (_resultSet.next()) {
            user.setName(_resultSet.getString("name"));
            user.setSurname(_resultSet.getString("surname"));
            user.setFamilyname(_resultSet.getString("familyname"));
            user.setEgn(_resultSet.getString("egn"));
            user.setCountry(_resultSet.getString("country"));
            user.setCity(_resultSet.getString("city"));
            user.setAddress(_resultSet.getString("address"));
            user.setPhone(_resultSet.getString("phone"));
            user.setEmail(_resultSet.getString("email"));
            user.setUserType(_resultSet.getString("usertype"));
        } else {
            user.setResponse("userNotFound");
        }

        return user;
    }

    /**
     * Retrieves all user information using its EGN as key to SELECT it
     *
     * @param user - containing valid username of a user from "users" table in
     * MySQl
     * @return User - object filled with all user personal information
     * @throws SQLException
     * @see user.response: if no data found - "userNotFound" is returned as a
     * response
     */
    public static User getUserByEGN(User user) throws SQLException {

        ResultSet _resultSet;

        _resultSet = DatabaseMgmt.select("SELECT * FROM users "
                + "WHERE egn =(?)", user.getEgn());

        if (_resultSet.next()) {
            user.setName(_resultSet.getString("name"));
            user.setSurname(_resultSet.getString("surname"));
            user.setFamilyname(_resultSet.getString("familyname"));
            user.setEgn(_resultSet.getString("egn"));
            user.setCountry(_resultSet.getString("country"));
            user.setCity(_resultSet.getString("city"));
            user.setAddress(_resultSet.getString("address"));
            user.setPhone(_resultSet.getString("phone"));
            user.setEmail(_resultSet.getString("email"));
            user.setUserType(_resultSet.getString("usertype"));
        } else {
            user.setResponse("userNotFound");
        }

        return user;
    }

    /**
     * Creates a double hash sum of the user's password + salt
     * 
     * @param password: the password that is going to be hashed
     * @return encrypted user password
     * @see md5 method
     */
    private static String hashpass(String password) {

        String SALT_BEGIN = "n@,k4gj@.@";
        String SALT_END = "4!ok^|</`c";

        return md5(md5(SALT_BEGIN + password + SALT_END));
    }

    /**
     * Used in hashpass method
     * 
     * @param input: user password + salts
     * @return md5 hash sum
     */
    private static String md5(String input) {

        String md5 = null;

        if (input == null) {
            return null;
        }

        try {
            //Create MessageDigest object for MD5
            MessageDigest digest = MessageDigest.getInstance("MD5");
            //Update input string in message digest
            digest.update(input.getBytes(), 0, input.length());
            //Converts message digest value in base 16 (hex) 
            md5 = new BigInteger(1, digest.digest()).toString(16);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(ELUBankServer.class.getName()).log(Level.SEVERE, null, ex);
        }

        return md5;
    }
}
