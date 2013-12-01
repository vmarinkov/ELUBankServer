
import java.net.*;
import java.io.*;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Demo Server: Contains a multi-threaded socket server sample code.
 */
public class Server extends Thread {

    private int _portNumber = 25559; //Arbitrary port number

    public void startServer() throws Exception {
        ServerSocket serverSocket = null;
        boolean listening = true;

        try {
            serverSocket = new ServerSocket(_portNumber);
        } catch (IOException e) {
            System.err.println("Could not listen on port: " + _portNumber);
            System.exit(-1);
        }

        while (listening) {
            handleClientRequest(serverSocket);
        }

        serverSocket.close();
    }

    private void handleClientRequest(ServerSocket serverSocket) {
        try {
            new ConnectionRequestHandler(serverSocket.accept()).run();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
    // vasko pisal glupava funkciq vikana po-dolu
    private String[] clientRequestParser(String clientRequest){
        String delimiter = ", ";
        String[] temp;
        /* given string will be split by the argument delimiter provided. */
         temp = clientRequest.split(delimiter);
        /* print substrings */
        for(int i =0; i < temp.length ; i++)
        System.out.println(temp[i]);
        return temp;
    }


    /**
     * Handles client connection requests.
     */
    public class ConnectionRequestHandler implements Runnable {

        private Socket _socket = null;
        private PrintWriter _out = null;
        private BufferedReader _in = null;

        public ConnectionRequestHandler(Socket socket) {
            _socket = socket;
        }

        public void run() {
            System.out.println("Client connected to socket: " + _socket.toString());

            try {
                _out = new PrintWriter(_socket.getOutputStream(), true);
                _in = new BufferedReader(new InputStreamReader(_socket.getInputStream()));

                String inputLine, outputLine;
                // trqbwa da pratq ne6to ot servera za da sraboti
                outputLine = "izprati mi zaqvka(string) ot vida: ime na funkciq, parametyr 1, parametyr 2... pr: login, username, pass";
                _out.println(outputLine);
       
                //   Read from socket and write back the response to client. 
                while ((inputLine = _in.readLine()) != null) {
                    
                    // vasko pisal glupavi metodi da raboti...
                    String [] clientRequest;
                    clientRequest = clientRequestParser(inputLine);
                    switch (clientRequest[0]){
                         case "login":
                             if(UserMgmt.login(clientRequest[1], clientRequest[2])){
                                 outputLine = "success";
                             } else outputLine = "fail";
                             break;
                    }                     
                     
                    if (outputLine != null) {
                        _out.println(outputLine);
                        if (outputLine.equals("exit")) {
                            System.out.println("Server is closing socket for client:" + _socket.getLocalSocketAddress());
                            break;
                        }
                    } else {
                        System.out.println("OutputLine is null!!!");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (SQLException ex) {
                Logger.getLogger(Server.class.getName()).log(Level.SEVERE, null, ex);
            } finally { //In case anything goes wrong we need to close our I/O streams and sockets.
                try {
                    _out.close();
                    _in.close();
                    _socket.close();
                } catch (Exception e) {
                    System.out.println("Couldn't close I/O streams");
                }
            }
        }
    }
}
