import com.sun.net.ssl.internal.ssl.Provider;
import java.security.Security;
import java.io.IOException;
import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;

/**
 * Server class used to implement SSL over a socket and start the server thread
 *
 * @author Vasil Marinkov
 */
public class SSLServer {

    private static int port = 23579;
    private static String keystore_pass = "SECRET123";
    private static String keystore_location = "kstore.jks";    

    private SSLServerSocketFactory sslServerSocketfactory = null;
    private SSLServerSocket sslServerSocket = null;

    public static void setSettings(int config_port, String config_pass, String config_location){
        port = config_port;
        keystore_pass = config_pass;
        keystore_location = config_location;
    }
    
    public void startServer() throws IOException {
        // Registering the JSSE provider
        Security.addProvider(new Provider());

        // Specifying the Keystore details
        System.setProperty("javax.net.ssl.keyStore", keystore_location);
        System.setProperty("javax.net.ssl.keyStorePassword", keystore_pass);

        // Enable debugging to view the handshake and communication
        // which happens between the SSLClient and the SSLServer
        //System.setProperty("javax.net.debug", "all");
        // Initialize the Server Socket
        sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        sslServerSocket = (SSLServerSocket) sslServerSocketfactory.createServerSocket(port);

        while (true) {
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
            new SSLServerThread(sslSocket).start();
        }
    }
}
