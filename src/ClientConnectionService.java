import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

/**
 * class running client side connection in the background
 */
public class ClientConnectionService extends SwingWorker<Socket, Object> {
    private static ClientConnectionService cs;
    Client client = new Client();
    private Socket socketS;
    private final Board board;
    private final String ip;
    private final int port;
    private boolean boardSizeReceived = false;

    /**
     * Constructor
     * @param userBoard
     * @param ip
     * @param port
     */
    public ClientConnectionService(Board userBoard, String ip, int port) {
        this.board = userBoard;
        this.ip = ip;
        this.port = port;
    }

    public static void setService(ClientConnectionService cService) {
        cs = cService;
    }

    public static ClientConnectionService getInstance() {
        return cs;
    }

    /**
     * connect to ServerSocket opened by Server
     * in a background thread to not block the Event dispatch thread
     * @return
     */
    @Override
    public Socket doInBackground() {
        try {
            socketS = client.startConnection(ip, port);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return socketS;
    }

    /**
     * invoked when the above functions returns
     * uses the Socket which is returned by the above function
     * to start the communication loop (Client.java) in a background thread
     */
    @Override
    protected void done() {
        try {
            socketS = get();
            Connection.setS(socketS);
        } catch (Exception ignore) {
            System.out.println("error starting communication loop");
        }
        try {
            client.createConnection(socketS);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        class StartClientCommunicationService extends SwingWorker<String, Object> {
            @Override
            public String doInBackground() {
                client.startCommunicationLoop();
                return null;
            }
        }
        /**
         * once the connection is established, the client needs to wait for the
         * message containing the board size. Before this message is read, no board can be rendered.
         * As soon as the board size is known, the field is rendered.
         * I am aware, that calling an element of the Event Dispatch Thread from within a background thread
         * is troublesome. Unfortunately i was unable to find a better solution.
         */
        new StartClientCommunicationService().execute();
        System.out.print("Client ready to send and receive messages...\n");
        while (!boardSizeReceived) {
            // wait
            try {
                int fieldsize = Integer.parseInt(Connection.getMessage());
                board.setSize(fieldsize);
                boardSizeReceived = true;
            } catch (Exception ignore) {
            }
        }
        // possibly problematic: gui call from within background thread
        System.out.println(String.format("connection data %s %s", ip, port));
        Connection.sendMessage("done");
        Ship.calcAmount(board.getSize());
        if (GUI.kiMultiplayer) {
            new GUI(10);
        } else {
            new GUI(6);
        }
    }
}
