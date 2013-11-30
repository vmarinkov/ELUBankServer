
import java.sql.*;
import java.math.BigInteger;
import java.security.*;

/**
 * User management operations
 * 
 * @author ELUBank team
 */
public class UserMgmt {

    /**
     * Crates a new INACTIVE user (INSERT into MySQL)
     *
     * @param newUser - String[] array containing all user data
     * @throws SQLException
     */
    public static void createUser(String[] newUser) throws SQLException {

        String[] columnNames = {"user_id", "username", "password",
            "name", "sirname", "familyname", "egn", "address", "phone",
            "email", "usertype", "pin_code", "isactive"};

        newUser[2] = hashpass(newUser[2]);

        DatabaseMgmt.Execute("INSERT INTO users VALUES"
                + "(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", columnNames, newUser);
    }

    /**
     * Activates an existing user (UPDATE to MySQL)
     *
     * @param egn - Using EGN as a super key to specify which user should be
     * activated
     * @throws SQLException
     */
    public static void activateUser(String egn) throws SQLException {

        String[] update = new String[2];
        update[0] = "1";
        update[1] = egn;

        DatabaseMgmt.Execute("UPDATE users SET isactive =(?)"
                + " WHERE egn =(?) LIMIT 1", update);
    }

    /**
     * Deletes an existing user (DELETE from MySQL)
     *
     * @param egn - Using EGN as a super key to specify which user should be
     * deleted
     * @throws SQLException
     */
    // @TODO REMOVE USER_ID from users, use egn as super key isntead !!!!
    public static void deleteUser(String egn) throws SQLException {

        DatabaseMgmt.Execute("DELETE FROM users WHERE egn =(?) LIMIT 1", egn);
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

        ResultSet _resultSet = DatabaseMgmt.Select("SELECT isactive FROM users "
                + "WHERE username =(?) AND password =(?)", userCredentials);

        // ако съществува такъв запис в таблицата и е активиран
        if (_resultSet.next() && _resultSet.getInt("isactive") == 1) {
            return true;
        } else { // ако не са изъпълнени горните 2 - логин фейл
            return false;
        }
    }

    /**
     * Retrieves all user information
     * using EGN as super-key to SELECT the user
     * 
     * @param egn - Existing user EGN
     * @return ResultSet - all user personal information
     * @throws SQLException 
     */
    public static ResultSet getUser(String egn) throws SQLException {

        ResultSet _resultSet = DatabaseMgmt.Select("SELECT * FROM users "
                + "WHERE egn =(?)", egn);

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

        } catch (NoSuchAlgorithmException e) {
        }
        
        return md5;
    }
}
