
import java.sql.*;
import java.math.BigInteger;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * User management operations
 *
 * @author ELUBank team
 */
public class UserMgmt {

    /**
     * Crates a new INACTIVE user (INSERT into MySQL)
     *
     * @param user - containing all new user data
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
   
    public static ResultSet searchUser(String egn)throws SQLException {
    
         ResultSet _resultSet = DatabaseMgmt.select("SELECT * FROM users"+" WHERE egn = (?)", egn);
         
         return _resultSet;
       
    }
    /**
     * User logIn function
     *
     * @param username - Existing username from "users" in MySQl
     * @param password - Existing password from "users" in MySQl
     * @return boolean
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
     * Retrieves all user information using EGN as super-key to SELECT the user
     *
     * @param username - Existing username from "users" in MySQl
     * @return ResultSet - all user personal information
     * @throws SQLException
     */
    public static ResultSet getUser(String username) throws SQLException {

        ResultSet _resultSet;

        _resultSet = DatabaseMgmt.select("SELECT * FROM users"
                + "WHERE username =(?)", username);

        return _resultSet;
    }

    // encrypt the user's password
    private static String hashpass(String password) {

        String SALT_BEGIN = "n@,k4gj@.@";
        String SALT_END = "4!ok^|</`c";

        return md5(md5(SALT_BEGIN + password + SALT_END));
    }

    // create md5 sum (used to encrypt the user's password)
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
