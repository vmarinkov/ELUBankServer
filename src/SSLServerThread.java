
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.SQLException;
import java.sql.Timestamp;
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

    private SSLSocket sslSocket = null;

    private ObjectOutputStream objOutStream = null;
    private ObjectInputStream objInStream = null;

    private Timestamp timestamp;

    private Object receivedObj = null;
    private User user = null;
    private Accounts accounts = null;
    private Transactions transactions = null;
    private Currency currency = null;

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
            timestamp = new Timestamp(System.currentTimeMillis());

            objOutStream = new ObjectOutputStream(sslSocket.getOutputStream());
            objInStream = new ObjectInputStream(sslSocket.getInputStream());

            receivedObj = objInStream.readObject();

            // handle "users" specific client's requests
            if (receivedObj instanceof User) {

                user = (User) receivedObj;

                if (user.getRequest().equalsIgnoreCase("login")) {

                    System.out.println(timestamp + ": User login request from " + sslSocket.getInetAddress());

                    if (UserMgmt.login(user.getUsername(), user.getPassword())) {

                        user = UserMgmt.getUserByUsername(user);
                        user.setLoggedIn(true);
                    }

                } else if (user.getRequest().equalsIgnoreCase("create")) {

                    System.out.println(timestamp + ": Create new user request from " + sslSocket.getInetAddress());
                    UserMgmt.createUser(user);

                } else if (user.getRequest().equalsIgnoreCase("searchByEGN")) {

                    System.out.println(timestamp + ": Search for user by ENG request from " + sslSocket.getInetAddress());
                    user = UserMgmt.getUserByEGN(user);
                } else if (user.getRequest().equalsIgnoreCase("update")) {
                    System.out.println(timestamp + ": Update user info request from " + sslSocket.getInetAddress());
                    UserMgmt.updateUser(user);
                    user = UserMgmt.getUserByEGN(user);
                }

                objOutStream.writeObject(user);

                // handle "accounts" specific client's requests
            } else if (receivedObj instanceof Accounts) {

                accounts = (Accounts) receivedObj;

                if (accounts.getRequest().equalsIgnoreCase("create")) {

                    System.out.println(timestamp + ": Create new account request from " + sslSocket.getInetAddress());
                    AccountsMgmt.createUserAccount(accounts);
                }

                objOutStream.writeObject(accounts);

                // handle "transactions" specific client's requests
            } else if (receivedObj instanceof Transactions) {

                transactions = (Transactions) receivedObj;
                // TODO handle requests.
                objOutStream.writeObject(transactions);

                // handle "currencies" specific client's requests
            } else if (receivedObj instanceof Currency) {

                currency = (Currency) receivedObj;

                if (currency.getRequest().equalsIgnoreCase("getAllCurrencyData")) {

                    currency = CurrencyMgmt.getAllCurrencyData(currency);
                }

                objOutStream.writeObject(currency);
            }

        } catch (SQLException ex) {

            // handle "users" table MySQL exceptions
            if (receivedObj instanceof User) {

                if (ex.getMessage().toLowerCase().contains(user.getUsername().toLowerCase())) {
                    user.setResponse("userExists");
                } else if (ex.getMessage().toLowerCase().contains(user.getEgn().toLowerCase())) {
                    user.setResponse("egnExists");
                } else {
                    Logger.getLogger(SSLServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }

                try {
                    objOutStream.writeObject(user);
                } catch (IOException ex1) {
                    Logger.getLogger(SSLServerThread.class.getName()).log(Level.SEVERE, null, ex1);
                }

                // handle "accounts" table MySQL exceptions
            } else if (receivedObj instanceof Accounts) {

                if (ex.getMessage().toLowerCase().contains(accounts.getIBAN().toLowerCase())) {
                    accounts.setResponse("ibanExists");
                } else {
                    Logger.getLogger(SSLServerThread.class.getName()).log(Level.SEVERE, null, ex);
                    return;
                }

                try {
                    objOutStream.writeObject(accounts);
                } catch (IOException ex1) {
                    Logger.getLogger(SSLServerThread.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        } catch (IOException | ClassNotFoundException ex) {
            Logger.getLogger(SSLServerThread.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                objOutStream.close();
                objInStream.close();
                sslSocket.close();
            } catch (IOException ex) {
                Logger.getLogger(SSLServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
