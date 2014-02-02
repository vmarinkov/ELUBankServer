
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

    private static final int PORT = 23579;

    private SSLServerSocketFactory sslServerSocketfactory = null;
    private SSLServerSocket sslServerSocket = null;

    public void startServer() throws IOException {
        // Registering the JSSE provider
        Security.addProvider(new Provider());

        // Specifying the Keystore details
        System.setProperty("javax.net.ssl.keyStore", "kstore.jks");
        System.setProperty("javax.net.ssl.keyStorePassword", "SECRET123");

        // Enable debugging to view the handshake and communication
        // which happens between the SSLClient and the SSLServer
        //System.setProperty("javax.net.debug", "all");
        // Initialize the Server Socket
        sslServerSocketfactory = (SSLServerSocketFactory) SSLServerSocketFactory.getDefault();
        sslServerSocket = (SSLServerSocket) sslServerSocketfactory.createServerSocket(PORT);

        while (true) {
            SSLSocket sslSocket = (SSLSocket) sslServerSocket.accept();
            new SSLServerThread(sslSocket).start();
        }
    }
}
