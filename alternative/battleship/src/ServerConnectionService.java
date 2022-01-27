import javax.swing.*;
import java.io.IOException;
import java.net.Socket;


/**
 * Class for handling Server side communication in a background thread
 */
public class ServerConnectionService extends SwingWorker<Socket, Object> {
    private static ServerConnectionService sc;
    Server server = new Server();
    private Socket socketS;
    private final int fieldsize;
    private final int port;

    public ServerConnectionService(int fieldsize, int port) {
        this.fieldsize = fieldsize;
        this.port = port;
    }

    public static void setService(ServerConnectionService scService) {
        sc = scService;
    }

    public static ServerConnectionService getInstance() {
        return sc;
    }

    /**
     * Worker thread waiting for connection in background
     */
    @Override
    public Socket doInBackground() {
        socketS = server.startConnection(port);
        try {
            server.createConnection(socketS);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return socketS;
    }

    /**
     * once the connection is established,
     * start the communicationLoop (Server.java) in the background
     * and send the board size to the client
     */
    @Override
    protected void done() {
        try {
            socketS = get();
            Connection.setS(socketS);
            System.out.println("Client connected to socket!");
        } catch (Exception ignore) {
            System.out.println("error");
            sc.cancel(true);
        }
        class StartCommunicationService extends SwingWorker<String, Object> {
            @Override
            public String doInBackground() {
                server.startCommunicationLoop();
                return null;
            }
        }
        (new StartCommunicationService()).execute();
        Connection.setServer(true);
        System.out.println(String.format("is Server: %s", Connection.isServer()));
        System.out.println("Server ready to send and receive messages...\n");

        String x = String.valueOf(fieldsize);
        Connection.sendMessage(x);
    }

}
