import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {

    private static Connection connection;

    private static boolean clientLoopReady;
    private static boolean clientConnected = false;

    public static Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        Client.connection = connection;
    }

    public static boolean getClientConnected() {
        return clientConnected;
    }

    /**
     * cancel Client Connection
     * @param s Socket to terminate
     * @throws IOException
     */
    public static void stopConnection(Socket s) throws IOException {
        // EOF ins Socket "schreiben".
        s.close();
        s.shutdownOutput();
        ClientConnectionService.getInstance().cancel(true);
        Connection.getTimer().cancel();
        System.out.println("Connection closed.");
    }

    /**
     * connect to the socket which was previously opened by the Server
     * with the specified connection values
     * @param ip ip adress to connect to
     * @param port port to connect to
     * @return Socket
     * @throws IOException
     */
    public Socket startConnection(String ip, int port) throws IOException {

        InetSocketAddress inSocket = new InetSocketAddress(ip, port);
        // Als Resultat erhält man ein Socket.
        Socket s = new Socket();
        s.connect(inSocket, 10000);
        clientConnected = true;
        System.out.println(String.format("is Server: %s", Connection.isServer()));
        System.out.println(String.format("Connection established on %s ", s.getLocalAddress()));
        System.out.println(String.format("Inet Connection: %s ", s.getInetAddress()));
        return s;
    }


    /**
     * Create a new Connection Object with the necessary Readers and Writers
     * @param s Socket for which a connection will be created
     * @throws IOException
     */
    public void createConnection(Socket s) throws IOException {

        BufferedReader in =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        Writer out = new OutputStreamWriter(s.getOutputStream());

        // Standardausgabestrom ebenfalls als BufferedWriter verpacken.
        BufferedWriter usr =
                new BufferedWriter(new OutputStreamWriter(System.out));

        connection = new Connection(in, out, usr);
        Connection.setMultiplayer(true);
        setConnection(connection);
    }

    /**
     * infinte loop to continuously listen for incoming messages
     * as well as write messages to the output stream
     */
    public void startCommunicationLoop() {
        try {
            connection.setTurn(false);
            while (true) {
                // incoming messages
                clientLoopReady = true;
                connection.setMessage(Connection.getIn().readLine());
                connection.setTurn(true);
                if (Connection.getMessage() == null) break;
                System.out.println("<<< " + Connection.getMessage());

                // outgoing messages
                Connection.getUsr().write(String.format("%s%n", Connection.getOut()));
            }
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
    }
}