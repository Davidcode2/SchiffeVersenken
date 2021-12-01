package network;

import javax.swing.*;
import java.io.IOException;
import java.net.Socket;

import static gui.Spielgui.fieldSize;
import static gui.Spielgui.port;

// Worker thread waiting for connection in background
    public class ServerConnectionService extends SwingWorker<Socket, Object> {
    network.Server server = new Server();
    private Socket socketS;

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
                System.out.println("Client connected to socket!");
            } catch (Exception ignore) {
                System.out.println("error");
            }
            class StartCommunicationService extends SwingWorker<String, Object> {
                @Override
                public String doInBackground() {
                    server.startCommunicationLoop();
                    return null;
                }
            }
            (new StartCommunicationService()).execute();
            System.out.println("Server ready to send and receive messages...\n");

            String x = String.valueOf(fieldSize);
            Connection.sendMessage(x);
        }
    }
