
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Logger;

/**
 * MySQL Database management: connect and disconnect, as well as executing
 * updates and queries
 *
 * @author Vasil Marinkov & Miglen Evlogiev
 */
public class DatabaseMgmt {

    private static final Logger LOG = Logger.getLogger(DatabaseMgmt.class.getName());

    // JDBC driver name and database URL & credentials
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static String DB_URL = "jdbc:mysql://localhost/proekt1?useUnicode=true&characterEncoding=utf-8";
    private static String DB_USER = "proekt1";
    private static String DB_PASS = "proekt1";
    private static Connection _connection = null;

    // SQL connecting and statement variables
    private static PreparedStatement _preparedStmt = null;
    private static ResultSet _resultSet = null;

    /**
     * Function to set the settings from the configuration config.ini file
     *
     * @param config_host getting the mysql host from the config.ini
     * @param config_db getting the mysql db from the config.ini
     * @param config_user getting the mysql user from the config.ini
     * @param config_pass getting the mysql pass from the config.ini
     * @param config_enc getting the mysql encoding from the config.ini
     */
    public static void setSettings( String config_host,
                                    String config_db,
                                    String config_user,
                                    String config_pass,
                                    String config_enc ) {
        DB_USER = config_user;
        DB_PASS = config_pass;
        DB_URL = "jdbc:mysql://" + config_host + "/" + config_db
                + "?useUnicode=true&characterEncoding=" + config_enc;
    }

    /**
     * Connecting to MySQL DB
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void connect() throws SQLException, ClassNotFoundException {

        LOG.info("Connecting to database...");
        //Register the JDBC driver
        Class.forName(JDBC_DRIVER);
        //Start the connectio
        _connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
    }

    /**
     * Disconnecting from MySQL DB
     *
     * @throws SQLException
     */
    public static void disconnect() throws SQLException {

        if (_preparedStmt != null) {
            _preparedStmt.close();
        }
        if (_resultSet != null) {
            _resultSet.close();
        }
        if (_connection != null) {
            _connection.close();
        }
    }

    /**
     * Used to execute INSERT, UPDATE && DELETE into MySQL DB
     *
     * @param sql The SQL statement
     * @throws SQLException
     */
    public static void execute(String sql) throws SQLException {

        LOG.info("Executing to database: ".concat(sql));

        _preparedStmt = _connection.prepareStatement(sql);
        _preparedStmt.executeUpdate();
    }

    /**
     * Used to execute INSERT, UPDATE && DELETE for a SINGLE column in MySQL DB
     *
     * @param sql The SQL statement
     * @param value Value that will be managed
     * @throws SQLException
     */
    public static void execute(String sql,
            String value) throws SQLException {

        LOG.info("Executing to database: ".concat(sql));

        _preparedStmt = _connection.prepareStatement(sql);
        _preparedStmt.setString(1, value);
        _preparedStmt.executeUpdate();
    }

    /**
     * Used to execute INSERT, UPDATE && DELETE for MULTIPLE columns in MySQL DB
     * without specifying the columns to be updated
     *
     * @param sql The SQL statement
     * @param values Values that will be managed
     * @throws SQLException
     */
    public static void execute(String sql,
            String[] values) throws SQLException {

        LOG.info("Executing to database: ".concat(sql));

        _preparedStmt = _connection.prepareStatement(sql);

        for (int i = 0; i < values.length; i++) {
            _preparedStmt.setString(i + 1, values[i]);
        }
        _preparedStmt.executeUpdate();
    }

    /**
     * Used to execute INSERT, UPDATE && DELETE for MULTIPLE columns in MySQL DB
     * by specifying the columns to be updated
     *
     * @param sql The SQL statement
     * @param columnNames Columns that we are going to work with
     * @param values Values that will be managed
     * @throws SQLException
     */
    public static void execute(String sql, String[] columnNames,
            String[] values) throws SQLException {

        LOG.info("Executing to database: ".concat(sql));

        _preparedStmt = _connection.prepareStatement(sql, columnNames);

        for (int i = 0; i < values.length; i++) {
            _preparedStmt.setString(i + 1, values[i]);
        }
        _preparedStmt.executeUpdate();
    }

    /**
     * Used to execute SELECT in MySQL DB
     *
     * @param sql The SQL statement
     *
     * @return ResultSet result, if any
     * @throws SQLException
     */
    public static ResultSet select(String sql) throws SQLException {

        LOG.info("Selecting from database: ".concat(sql));

        _preparedStmt = _connection.prepareStatement(sql);
        _resultSet = _preparedStmt.executeQuery();

        return _resultSet;
    }

    /**
     * Used to execute SELECT of roll in MySQL DB by specifying super-key
     *
     * @param sql The SQL statement
     * @param value super-key (a roll containing that value will be selected)
     * @return ResultSet information contained in the selected roll
     * @throws SQLException
     */
    public static ResultSet select(String sql, String value) throws SQLException {

        LOG.info("Selecting from database: ".concat(sql));

        _preparedStmt = _connection.prepareStatement(sql);
        _preparedStmt.setString(1, value);
        _resultSet = _preparedStmt.executeQuery();

        return _resultSet;
    }

    /**
     * Used to execute SELECT of roll in MySQL DB by specifying multiple keys
     *
     * @param sql The SQL statement
     * @param values keys (a roll containing those values will be selected)
     * @return ResultSet information contained in the selected roll
     * @throws SQLException
     */
    public static ResultSet select(String sql, String[] values) throws SQLException {

        LOG.info("Selecting from database: ".concat(sql));

        _preparedStmt = _connection.prepareStatement(sql);

        for (int i = 0; i < values.length; i++) {
            _preparedStmt.setString(i + 1, values[i]);
        }
        _resultSet = _preparedStmt.executeQuery();

        return _resultSet;
    }
}
