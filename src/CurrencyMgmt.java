
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author lmn7
 */
public class CurrencyMgmt {

    public static void getCurrencies(Currency currency) throws SQLException {

        ResultSet _resultSet;

        _resultSet = DatabaseMgmt.select("SELECT count(*) FROM currencies");

        while (_resultSet.next()) {
            currency.createNewCurrenciesArray(_resultSet.getInt(1));
        }

        System.out.println("Total of " + currency.allCurrencies.length + " currencies found");

        _resultSet = DatabaseMgmt.select("SELECT * FROM currencies");

        // GET ALL CURRENCIES INFO OUT OF THE CURRENCIES TABLE
        for (Currency currentCurrency : currency.allCurrencies) {
            _resultSet.next();
            currentCurrency.setName(_resultSet.getString("name"));
            currentCurrency.setRate(_resultSet.getString("rate"));
            currentCurrency.setCode(_resultSet.getString("code"));
            currentCurrency.setRatio(_resultSet.getString("ratio"));
            currentCurrency.setReverserate(_resultSet.getString("reverserate"));
            currentCurrency.setDate(_resultSet.getString("date"));
        }

        for (int i = 0; i < 130; i++) {
            System.out.println(currency.allCurrencies[i].getName());
        }
    }
}
