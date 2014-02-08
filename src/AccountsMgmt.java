
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Class that uses both the database management class as well as the accounts
 * class to manage user's transaction accounts
 *
 * @see DatabaseMgmt.java
 * @see Accounts.java
 *
 * @author Vasil Marinkov
 */
public class AccountsMgmt {

    /**
     * Crates a new banking account (INSERT into MySQL)
     *
     * @param account - object containing all new account data
     * @throws SQLException
     */
    public static void createBankingAccount(Accounts account) throws SQLException {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime());
        String[] columnNames = {"useregn", "accounttype", "iban", "amount", "currency", "lastupdated"};

        String[] newAccount;
        newAccount = account.toStringArray();
        newAccount[5] = timeStamp;

        DatabaseMgmt.execute("INSERT INTO accounts VALUES"
                + "(?, ?, ?, ?, ?, ?)", columnNames, newAccount);
    }

    /**
     * Removes an existing banking account (DELETE from MySQL)
     *
     * @param account - the user's account that is going to be removed
     * @throws SQLException
     */
    public static void deleteBankingAccount(Accounts account) throws SQLException {

        DatabaseMgmt.execute("DELETE FROM accounts WHERE iban = ? LIMIT 1", account.getIBAN());
    }

    /**
     * Applies banking accounts interests (2% increase every 3 months)
     * 
     * @throws SQLException
     */
    public static void applyInterests() throws SQLException {

        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(Calendar.getInstance().getTime());

        System.out.println("Checking if banking accounts interestes need to be applied...");

        String statement = "SELECT * FROM accounts WHERE accounttype = ?";
        String where = "С фиксирана лихва";
        ResultSet _resultSet = DatabaseMgmt.select(statement, where);

        int yearUpdated, currentYear = Integer.parseInt(timeStamp.substring(0, 4));
        int monthUpdated, currentMonth = Integer.parseInt(timeStamp.substring(5, 7));
        int dateUpdated, currentDate = Integer.parseInt(timeStamp.substring(8, 10));

        while (_resultSet.next()) {

            yearUpdated = Integer.parseInt(_resultSet.getString("lastupdated").substring(0, 4));
            monthUpdated = Integer.parseInt(_resultSet.getString("lastupdated").substring(5, 7));
            dateUpdated = Integer.parseInt(_resultSet.getString("lastupdated").substring(8, 10));

            if (currentYear > yearUpdated) {
                currentMonth += 12;
            }

            if ((currentMonth - monthUpdated) > 3
                    || ((currentMonth - monthUpdated) == 3 && (currentDate >= dateUpdated))) {

                System.out.println("Applying insterest for bank account: "
                        + _resultSet.getString("IBAN"));
                // TODO - Бубето ми е направила много сложна система за различни лихви
                // аз в момента олихвявам с 2% на 3 месеца. Евентуална промяна.
                Double amount = _resultSet.getDouble("amount");
                amount += ((amount / 100) * 2);

                String[] values = {amount + "", timeStamp, _resultSet.getString("iban")};
                DatabaseMgmt.execute("UPDATE accounts SET amount = ?, lastupdated = ? "
                        + " WHERE iban = ?", values);
            }
            currentMonth -= 12;
        }
    }
}
