import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

public class ClientConnectionService extends SwingWorker<Socket, Object> {
    Client client = new Client();
    private Socket socketS;
    private Board board;
    private String ip;
    private int port;
    private boolean boardSizeReceived = false;

    public ClientConnectionService(Board userBoard, String ip, int port) {
        this.board = userBoard;
        this.ip = ip;
        this.port = port;
    }

    //        System.out.println(String.format("connection data %s %s", ip, port));
    @Override
    public Socket doInBackground() {
        try {
            socketS = client.startConnection(ip, port);
            try {
                client.createConnection(socketS);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return socketS;
    }
    @Override
    protected void done() {
        try {
            socketS = get();
        } catch (Exception ignore) {
            System.out.println("error starting communication loop");
        }
        class StartClientCommunicationService extends SwingWorker<String, Object> {
            @Override
            public String doInBackground() {
                client.startCommunicationLoop();
                return null;
            }
        }
        (new StartClientCommunicationService()).execute();
        System.out.print("Client ready to send and receive messages...\n");
        while (!boardSizeReceived) {
            try {
                int fieldsize = Integer.parseInt(Connection.getMessage());
                board.setSize(fieldsize);
                boardSizeReceived = true;
            } catch (Exception ignore) {
            }
            // wait
        }
        // possibly problematic: gui call from within background thread
        // TODO:
        // issue: gui doesn't know how many ships it can set (all 0), works on click "schiffe neu setzen"
        Connection.setServer(false);
        System.out.println(String.format("connection data %s %s", ip, port));
        Connection.sendMessage("done");
        new GUI(6);
    }
}
