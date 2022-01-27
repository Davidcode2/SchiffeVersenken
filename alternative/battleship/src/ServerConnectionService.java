import javax.swing.*;
import java.io.IOException;
import java.net.Socket;


// Worker thread waiting for connection in background
public class ServerConnectionService extends SwingWorker<Socket, Object> {
    Server server = new Server();
    private Socket socketS;
    private int fieldsize;
    private int port;
    private static ServerConnectionService sc;

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

    void closeSocket() {
        
    }
}
