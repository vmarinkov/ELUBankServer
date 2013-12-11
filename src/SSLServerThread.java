
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

    SSLServerThread(SSLSocket sslSocket) {
        this.sslSocket = sslSocket;
    }

    public void run() {

        try {
            objOutStream = new ObjectOutputStream(sslSocket.getOutputStream());
            objInStream = new ObjectInputStream(sslSocket.getInputStream());

            receivedObj = objInStream.readObject();

            if (receivedObj instanceof User) {
                User user = (User) receivedObj;
                if (user.getRequest().equalsIgnoreCase("login")) {
                    if (UserMgmt.login(user.getUsername(), user.getPassword())) {

                        ResultSet _resResultSet = UserMgmt.getUser("8612126737");

                        while (_resResultSet.next()) {
                            user.setName(_resResultSet.getString("name"));
                            user.setSurname(_resResultSet.getString("sirname")); //to fix sirname = surname @ table
                            user.setFamilyname(_resResultSet.getString("familyname"));
                        }

                        user.setLoggedIn(true);
                        objOutStream.writeObject(user);
                    }
                }
                objOutStream.writeObject(user);
            }

        } catch (IOException | ClassNotFoundException | SQLException ex) {
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
