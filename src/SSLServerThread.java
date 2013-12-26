
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLSocket;

public class SSLServerThread extends Thread {

    private SSLSocket sslSocket = null;

    private ObjectOutputStream objOutStream = null;
    private ObjectInputStream objInStream = null;

    private Object receivedObj = null;
    private User user;

    SSLServerThread(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
    }

    public void run() {

        try {
            objOutStream = new ObjectOutputStream(sslSocket.getOutputStream());
            objInStream = new ObjectInputStream(sslSocket.getInputStream());

            receivedObj = objInStream.readObject();

            if (receivedObj instanceof User) {

                user = (User) receivedObj;

                if (user.getRequest().equalsIgnoreCase("login")) {

                    if (UserMgmt.login(user.getUsername(), user.getPassword())) {

                        ResultSet _resResultSet = UserMgmt.getUser(user.getUsername());

                        while (_resResultSet.next()) {
                            user.setName(_resResultSet.getString("name"));
                            user.setSurname(_resResultSet.getString("surname"));
                            user.setFamilyname(_resResultSet.getString("familyname"));
                            user.setEgn(_resResultSet.getString("egn"));
                            user.setCountry(_resResultSet.getString("country"));
                            user.setCity(_resResultSet.getString("city"));
                            user.setAddress(_resResultSet.getString("address"));
                            user.setPhone(_resResultSet.getString("phone"));
                            user.setEmail(_resResultSet.getString("email"));
                            user.setUserType(_resResultSet.getString("usertype"));
                        }

                        user.setLoggedIn(true);
                    }

                } else if (user.getRequest().equalsIgnoreCase("create")) {

                    UserMgmt.createUser(user);
                }

                objOutStream.writeObject(user);
            }

        } catch (SQLException ex) {

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
