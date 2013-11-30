
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * This class is used to manage Database connection as well as executing updates
 * and queries
 *
 * @author ELU_Bank team
 */
public class DatabaseMgmt {

    // JDBC driver name and database URL & credentials
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/proekt1";
    private static final String DB_USER = "proekt1";
    private static final String DB_PASS = "proekt1";

    // SQL connecting and statement variables
    private static Connection _connection = null;
    private static PreparedStatement _preparedStmt = null;
    private static ResultSet _resultSet = null;

    /**
     * Connecting to MySQL DB
     *
     * @throws SQLException
     * @throws ClassNotFoundException
     */
    public static void Connect() throws SQLException, ClassNotFoundException {

        //Register the JDBC driver
        Class.forName(JDBC_DRIVER);

        System.out.println("Connecting to database...");

        //Start the connection
        _connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);

        System.out.println("Connected to database...");
    }

    /**
     * Disconnecting from MySQL DB
     *
     * @throws SQLException
     */
    public static void Disconnect() throws SQLException {

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
     * Used to execute INSERT, UPDATE && DELETE for a SINGLE column in MySQL DB
     *
     * @param sql - The SQL statement
     * @param value - Value that will be managed
     * @throws SQLException
     */
    public static void Execute(String sql,
            String value) throws SQLException {

        //@TODO remove these when we done
        System.out.println("Executing to Mysql...");
        System.out.println(sql);

        _preparedStmt = _connection.prepareStatement(sql);

        _preparedStmt.setString(1, value);

        _preparedStmt.executeUpdate();
    }

    /**
     * Used to execute INSERT, UPDATE && DELETE for MULTIPLE columns in MySQL DB
     * without specifying the columns to be updated
     *
     * @param sql - The SQL statement
     * @param values - Values that will be managed
     * @throws SQLException
     */
    public static void Execute(String sql,
            String[] values) throws SQLException {

        //@TODO remove these when we done
        System.out.println("Executing to Mysql...");
        System.out.println(sql);

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
     * @param sql - The SQL statement
     * @param columnNames - Columns that we are going to work with
     * @param values - Values that will be managed
     * @throws SQLException
     */
    public static void Execute(String sql, String[] columnNames,
            String[] values) throws SQLException {

        //@TODO remove these when we done
        System.out.println("Executing to Mysql...");
        System.out.println(sql);

        _preparedStmt = _connection.prepareStatement(sql, columnNames);

        for (int i = 0; i < values.length; i++) {
            _preparedStmt.setString(i + 1, values[i]);
        }

        _preparedStmt.executeUpdate();
    }

    /**
     * Used to execute SELECT of roll in MySQL DB by specifying super-key
     *
     * @param sql - The SQL statement
     * @param value - super-key (a roll containing that value will be selected)
     * @return ResultSet - information contained in the selected roll
     * @throws SQLException
     */
    public static ResultSet Select(String sql, String value) throws SQLException {

        //@TODO remove these when we done
        System.out.println("Selecting from Mysql...");
        System.out.println(sql);

        _preparedStmt = _connection.prepareStatement(sql);

        _preparedStmt.setString(1, value);

        _resultSet = _preparedStmt.executeQuery();

        return _resultSet;
    }

    /**
     * Used to execute SELECT of roll in MySQL DB by specifying multiple keys
     *
     * @param sql - The SQL statement
     * @param values - keys (a roll containing those values will be selected)
     * @return ResultSet - information contained in the selected roll
     * @throws SQLException
     */
    public static ResultSet Select(String sql, String[] values) throws SQLException {

        //@TODO remove these when we done
        System.out.println("Selecting from Mysql...");
        System.out.println(sql);

        _preparedStmt = _connection.prepareStatement(sql);

        for (int i = 0; i < values.length; i++) {
            _preparedStmt.setString(i + 1, values[i]);
        }

        _resultSet = _preparedStmt.executeQuery();

        return _resultSet;
    }
}
