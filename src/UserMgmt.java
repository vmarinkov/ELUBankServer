
import java.sql.*;
import java.math.BigInteger;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

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
     * @param user object containing all new user data
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
     * @param user object containing all new user data
     * @throws SQLException
     */
    public static void updateUser(User user) throws SQLException {
        String[] allowedColumns = {"country", "city", "address", "phone", "email"};

        StringBuilder query = new StringBuilder();
        query.append("UPDATE users SET ");
        for (String colName : allowedColumns) {
            query.append(colName);
            query.append(" = ?,");
        }
        query.deleteCharAt(query.length() - 1);
        query.append(" WHERE egn = ?");

        String[] values = {
            user.getCountry(),
            user.getCity(),
            user.getAddress(),
            user.getPhone(),
            user.getEmail(),
            user.getEgn()
        };

        DatabaseMgmt.execute(query.toString(), values);
    }

    /**
     * Updates user password (UPDATE into MySQL)
     *
     * @param user object containing new user password
     * @throws SQLException
     */
    public static void updatePass(User user) throws SQLException {
        // Prepare needed values.
        String[] values = {hashpass(user.getPassword()), user.getUsername(), user.getEgn()};

        // Check if user exists.
        String query = "SELECT egn FROM users WHERE username = ? AND egn = ? LIMIT 1";
        ResultSet res = DatabaseMgmt.select(query, java.util.Arrays.copyOfRange(values, 1, 3));
        if (!res.next()) {
            user.setResponse("userNotFound");
            return;
        }

        // Update password.
        query = "UPDATE users SET password = ? WHERE username = ? AND egn = ? LIMIT 1";
        DatabaseMgmt.execute(query, values);
    }

    /**
     * Deletes an existing user (DELETE from MySQL)
     *
     * @param user Using user's EGN as a super key to specify which user
     * @throws SQLException
     */
    public static void deleteUser(User user) throws SQLException {
        // delete user's banking accounts
        if (user.getAccounts() != null) {
            for (Accounts currentAccount : user.getAccounts()) {
                AccountsMgmt.deleteBankingAccount(currentAccount);
            }
        }
        // delete user's transactions
        TransactionsMgmt.deleteTransactions(user.getEgn());
        // delete user itself
        DatabaseMgmt.execute("DELETE FROM users WHERE egn = ? LIMIT 1", user.getEgn());
    }

    /**
     * User logIn function
     *
     * @param username Existing username from "users" in MySQL
     * @param password Existing password from "users" in MySQL
     * @return boolean "true" on successful login, else "false"
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
     * @param user containing valid username of a user from "users" table in
     * MySQl
     * @return User object filled with all user personal information
     * @throws SQLException
     */
    public static User getUserByUsername(User user) throws SQLException {

        ResultSet _resultSet;

        _resultSet = DatabaseMgmt.select("SELECT * FROM users "
                + "WHERE username =(?)", user.getUsername());

        if (_resultSet.next()) {
            user.setUsername(_resultSet.getString("username"));
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
            return user;
        }

        if (user.getUserType().equalsIgnoreCase("2")
                || user.getUserType().equalsIgnoreCase("3")) {
            user = getUserAccounts(user);
            user = getUserTransactions(user);
        }

        return user;
    }

    /**
     * Retrieves all user information using its EGN as key to SELECT it
     *
     * @param user containing valid EGN of a user from "users" table in MySQl
     * @return User object filled with all user personal information
     * @throws SQLException
     */
    public static User getUserByEGN(User user) throws SQLException {

        ResultSet _resultSet;

        _resultSet = DatabaseMgmt.select("SELECT * FROM users "
                + "WHERE egn =(?)", user.getEgn());

        if (_resultSet.next()) {
            user.setUsername(_resultSet.getString("username"));
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
            return user;
        }

        if (user.getUserType().equalsIgnoreCase("2")
                || user.getUserType().equalsIgnoreCase("3")) {
            user = getUserAccounts(user);
            user = getUserTransactions(user);
        } else if (user.getUserType().equalsIgnoreCase("1")){
            JOptionPane.showMessageDialog(null, "Този клиент няма сметки, поради ограничение "
                    + "наложено от типа на достъп !");
        }

        return user;
    }

    /**
     * Retrieves user's accounts information using its EGN as key to SELECT it
     *
     * @param user containing valid EGN of a user from "users" table in MySQl
     * @return a new user.accounts [] array properly filled with information
     * @throws SQLException
     */
    public static User getUserAccounts(User user) throws SQLException {

        ResultSet _resultSet = DatabaseMgmt.select("SELECT count(*) FROM accounts"
                + " WHERE useregn = ?", user.getEgn());
        if (_resultSet.next()) {
            user.initializeUserAccountsArray(_resultSet.getInt(1));
        }

        _resultSet = DatabaseMgmt.select("SELECT * FROM accounts WHERE useregn = ?", user.getEgn());

        for (Accounts currentAccount : user.getAccounts()) {
            _resultSet.next();
            currentAccount.setAccountType(_resultSet.getString("accounttype"));
            currentAccount.setAmount(_resultSet.getString("amount"));
            currentAccount.setCurrency(_resultSet.getString("currency"));
            currentAccount.setIBAN(_resultSet.getString("iban"));
            currentAccount.setUserEGN(_resultSet.getString("useregn"));
        }

        return user;
    }

    /**
     * Retrieves user's transactions information using its EGN as key to SELECT
     * it
     *
     * @param user containing valid EGN of a user from "users" table in MySQl
     * @return a new user.currnetUserTransactions [] array properly filled with
     * information
     * @throws SQLException
     */
    public static User getUserTransactions(User user) throws SQLException {

        ResultSet _resultSet = DatabaseMgmt.select("SELECT count(*) FROM transactions"
                + " WHERE useregn = ?", user.getEgn());
        if (_resultSet.next()) {
            user.initializeUserTransactionsArray(_resultSet.getInt(1));
        }

        _resultSet = DatabaseMgmt.select("SELECT * FROM transactions WHERE useregn = ?", user.getEgn());

        for (Transactions currentTransaction : user.getTransactions()) {
            _resultSet.next();
            currentTransaction.setUserEGN(_resultSet.getString("useregn"));
            currentTransaction.setAmount(_resultSet.getString("amount"));
            currentTransaction.setCurrency(_resultSet.getString("currency"));
            currentTransaction.setReceiver(_resultSet.getString("receiver"));
            currentTransaction.setSubject(_resultSet.getString("subject"));
            currentTransaction.setIBAN(_resultSet.getString("iban"));
            currentTransaction.setToIBAN(_resultSet.getString("toiban"));
            currentTransaction.setTimestamp(_resultSet.getString("timestamp"));
        }

        return user;
    }

    /**
     * Returns all user data along with their banking accounts and transaction
     * information
     *
     * @param user containing "getAll" request
     * @return returns all user data
     * @throws SQLException
     */
    public static User getAllUsers(User user) throws SQLException {
        ResultSet _resultSet;

        _resultSet = DatabaseMgmt.select("SELECT count(*) FROM users");

        while (_resultSet.next()) {
            user.initializeUserArray(_resultSet.getInt(1));
        }

        // EXCTRACT ALL CURRENCIES DATA OUT OF THE CURRENCIES TABLE
        _resultSet = DatabaseMgmt.select("SELECT * FROM users");

        // EXCTRACT ALL CURRENCIES DATA OUT OF THE CURRENCIES TABLE
        for (User currentUser : user.getAllUser()) {
            _resultSet.next();
            currentUser.setUsername(_resultSet.getString("username"));
            currentUser.setName(_resultSet.getString("name"));
            currentUser.setSurname(_resultSet.getString("surname"));
            currentUser.setFamilyname(_resultSet.getString("familyname"));
            currentUser.setEgn(_resultSet.getString("egn"));
            currentUser.setCountry(_resultSet.getString("country"));
            currentUser.setCity(_resultSet.getString("city"));
            currentUser.setAddress(_resultSet.getString("address"));
            currentUser.setPhone(_resultSet.getString("phone"));
            currentUser.setEmail(_resultSet.getString("email"));
            currentUser.setUserType(_resultSet.getString("usertype"));
            if (currentUser.getUserType().equalsIgnoreCase("2")
                    || currentUser.getUserType().equalsIgnoreCase("3")) {
                currentUser = getUserAccounts(currentUser);
                currentUser = getUserTransactions(currentUser);
            }
        }

        return user;
    }

    /**
     * Creates a double hash sum of the user's password + salt
     *
     * @param password the password that is going to be hashed
     * @return encrypted user password
     * @see md5 method
     */
    private static String hashpass(String password) {

        String SALT_BEGIN = "n@,k4gj@.@";
        String SALT_END = "4!ok^|</`c";

        return md5(md5(SALT_BEGIN + password + SALT_END));
    }

    /**
     * Used in {@link #hashpass() hashpass()}
     *
     * @param input a string to hash.
     * @return Hash of the given string.
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
