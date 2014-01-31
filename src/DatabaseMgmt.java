
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * MySQL Database management: connect and disconnect as well as executing
 * updates and queries
 *
 * @author ELUBank team
 */
public class DatabaseMgmt {

    // JDBC driver name and database URL & credentials
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DB_URL = "jdbc:mysql://localhost/proekt1?useUnicode=true&characterEncoding=utf-8";
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
    public static void connect() throws SQLException, ClassNotFoundException {

        //Register the JDBC driver
        Class.forName(JDBC_DRIVER);

        System.out.println("Connecting to database...");

        //Start the connectio
        _connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         
        System.out.println("Connected to database...");
    }
   public static void search(String egn)throws SQLException, ClassNotFoundException{
          
         _connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASS);
         Statement stmt = (Statement)_connection.createStatement();
         
         String SQL = "SELECT * FROM proekt1 WHERE egn ='"+ egn + "'";
         _resultSet = stmt.executeQuery(SQL);
         while (_resultSet.next()){
             System.out.println("EGN"+_resultSet.getString("egn"));
             System.out.println("Name"+_resultSet.getString("name"));
         }
             
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
     * Used to execute INSERT, UPDATE && DELETE for a SINGLE column in MySQL DB
     *
     * @param sql - The SQL statement
     * @param value - Value that will be managed
     * @throws SQLException
     */
    public static void execute(String sql,
            String value) throws SQLException {

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
    public static void execute(String sql,
            String[] values) throws SQLException {

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
    public static void execute(String sql, String[] columnNames,
            String[] values) throws SQLException {

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
    public static ResultSet select(String sql, String value) throws SQLException {

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
    public static ResultSet select(String sql, String[] values) throws SQLException {

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
