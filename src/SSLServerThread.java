
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

/**
 * Communicates with the client application trough objects, using the object
 * stream. Handles requests and, if necessary, returns response messages (when
 * exceptional event occurs )
 *
 * @author Vasil Marinkov
 */
public class SSLServerThread extends Thread {

    private static final Logger LOG = Logger.getLogger(ELUBankServer.class.getName());
    private SSLSocket sslSocket = null;

    private ObjectOutputStream objOutStream = null;
    private ObjectInputStream objInStream = null;

    private Object receivedObj = null;
    private User user = null;
    private Accounts accounts = null;
    private Transactions transactions = null;
    private Currency currencies = null;

    SSLServerThread(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
    }

    /**
     * The following method is used to handle clients request by receiving an
     * object (user, accounts, transactions or currency) containing the request.
     *
     * @see User.java, Accounts.java, Transactions.java, Currency.java
     * @see UserMgmt.java, AccountsMgmt.java, TransactionsMgmt.java,
     * CurrencyMgmt.java
     */
    @Override
    public void run() {

        try {

            objOutStream = new ObjectOutputStream(sslSocket.getOutputStream());
            objInStream = new ObjectInputStream(sslSocket.getInputStream());

            receivedObj = objInStream.readObject();

            // handle "users" specific client's requests
            if (receivedObj instanceof User) {

                user = (User) receivedObj;
                handleUser();
                objOutStream.writeObject(user);
                // handle "accounts" specific client's requests
            } else if (receivedObj instanceof Accounts) {

                accounts = (Accounts) receivedObj;
                handleAccounts();
                objOutStream.writeObject(accounts);
                // handle "transactions" specific client's requests
            } else if (receivedObj instanceof Transactions) {

                transactions = (Transactions) receivedObj;
                handleTransactions();
                objOutStream.writeObject(transactions);
                // handle "currencies" specific client's requests
            } else if (receivedObj instanceof Currency) {

                currencies = (Currency) receivedObj;
                handleCurrencies();
                objOutStream.writeObject(currencies);
            }
        } catch (IOException | ClassNotFoundException ex) {
            LOG.log(Level.SEVERE, null, ex);
        } finally {
            try {
                objOutStream.close();
                objInStream.close();
                sslSocket.close();
            } catch (IOException ex) {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Performs user requests
     */
    private void handleUser() {
        try {
            // handle requests
            switch (user.getRequest()) {

                case "login":
                    LOG.info("User login request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    if (UserMgmt.login(user.getUsername(), user.getPassword())) {
                        user = UserMgmt.getUserByUsername(user);
                        user.setLoggedIn(true);
                    }
                    break;

                case "create":
                    LOG.info("Create new user request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    UserMgmt.createUser(user);
                    break;

                case "search":
                    LOG.info("Search for user request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    user = UserMgmt.getUserByEGN(user);
                    break;

                case "update":
                    LOG.info("Update user info request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    UserMgmt.updateUser(user);
                    break;

                case "updatePass":
                    LOG.info("Update user password request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    UserMgmt.updatePass(user);
                    break;

                case "getAll":
                    LOG.info("Get all user data request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    user = UserMgmt.getAllUsers(user);
                    break;

                case "delete":
                    LOG.info("Delete user request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    UserMgmt.deleteUser(user);
                    break;
            }
            // handle some "users" table MySQL exceptions
        } catch (SQLException ex) {
            if (ex.getMessage().toLowerCase().contains(user.getUsername().toLowerCase())) {
                user.setResponse("Вече съществува потребител с това потребителско име!");
                LOG.info("An error occured! Response sent: ".concat(user.getResponse()));
            } else if (ex.getMessage().toLowerCase().contains(user.getEgn().toLowerCase())) {
                user.setResponse("Вече съществува потребител с това ЕГН!");
                LOG.info("An error occured! Response sent: ".concat(user.getResponse()));
            } else {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Performs account requests
     */
    private void handleAccounts() {
        try {
            // handle requests
            switch (accounts.getRequest()) {

                case "create":
                    LOG.info("Create new banking account request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    AccountsMgmt.createBankingAccount(accounts);
                    break;

                case "delete":
                    LOG.info("Delete banking account request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    AccountsMgmt.deleteBankingAccount(accounts);
                    break;
            }
            // handle some "accounts" table MySQL exceptions
        } catch (SQLException ex) {
            if (ex.getMessage().toLowerCase().contains(accounts.getIBAN().toLowerCase())) {
                accounts.setResponse("ibanExists");
                LOG.info("An error occured! Response sent: ".concat(accounts.getResponse()));
            } else {
                LOG.log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Performs transaction requests
     */
    private void handleTransactions() {
        try {
            // handle requests
            switch (transactions.getRequest()) {

                case "newTransaction":
                    LOG.info("New transaction request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    TransactionsMgmt.newTransaction(transactions);
                    break;
            }
            // handle some "transactions" table MySQL exceptions
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Performs currency requests
     */
    private void handleCurrencies() {
        try {
            // handle requests
            switch (currencies.getRequest()) {

                case "getAllCurrencyData":
                    LOG.info("Get currency data request from "
                            .concat(sslSocket.getInetAddress().toString()));
                    currencies = CurrencyMgmt.getAllCurrencyData(currencies);
                    break;
            }
            // handle some "currencies" table MySQL exceptions
        } catch (SQLException ex) {
            LOG.log(Level.SEVERE, null, ex);
        }
    }
}
