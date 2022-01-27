import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.Enumeration;

public class Server {

    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        Server.connection = connection;
    }

    public static String[] ipAdresses() throws SocketException {
        ArrayList<String> inetAddr = new ArrayList<String>();
        Enumeration<NetworkInterface> nis =
                NetworkInterface.getNetworkInterfaces();
        while (nis.hasMoreElements()) {
            NetworkInterface ni = nis.nextElement();
            Enumeration<InetAddress> ias = ni.getInetAddresses();
            while (ias.hasMoreElements()) {
                InetAddress ia = ias.nextElement();
                if (!ia.isLoopbackAddress()) {
                    if (ia instanceof Inet4Address) {
                        inetAddr.add(ia.getHostAddress());
                    }
                }
            }
        }
        String[] inetAddrArray = new String[inetAddr.size()];
        inetAddrArray = inetAddr.toArray(inetAddrArray);
        return inetAddrArray;
    }

    public static void stopServer(Socket s) throws IOException {
        // EOF ins Socket "schreiben".
        Connection.getS().close();
        Connection.getS().shutdownOutput();
        ServerConnectionService.getInstance().cancel(true);
        Connection.getTimer().cancel();
        System.out.println("Connection closed.");
    }

    public Socket startConnection(int port) {
        // Verwendete Portnummer.

        // Server-Socket erzeugen und an diesen Port binden.
        try {
            ServerSocket ss = new ServerSocket(port);

            // Auf eine Client-Verbindung warten und diese akzeptieren.
            // Als Resultat erhält man ein "normales" Socket.
            System.out.println("Waiting for client connection ...");
            System.out.println(String.format("on: %s", port));
            Socket s = ss.accept();
            // print out the ip address to which the socket is connected
            System.out.println(String.format("Connection established on %s ", s.getLocalAddress()));
            System.out.println(String.format("Inet Connection: %s ", s.getInetAddress()));
            return s;
        } catch (IOException e) {
            return null;
        }
    }

    public void createConnection(Socket s) throws IOException {

        // Ein- und Ausgabestrom des Sockets ermitteln
        // und als BufferedReader bzw. Writer verpacken
        // (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).
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

    public void startCommunicationLoop() {

        try {

//		 Abwechselnd vom Socket lesen und auf den Bildschirm schreiben
//		 bzw. vom Benutzer lesen und ins Socket schreiben.
//		 Abbruch bei EOF vom Socket bzw. bei EOF oder Leerzeile vom Benutzer.
            while (true) {
                // outgoing messages
                Connection.getUsr().write(String.format("%s%n", Connection.getOut()));

                // incoming messages
                connection.setMessage(Connection.getIn().readLine());
                connection.setTurn(true);
                if (Connection.getMessage() == null) break;
                System.out.println("<<< " + Connection.getMessage());
            }

        } catch (IOException e) {
            System.out.println("error starting communication loop");
            e.printStackTrace();
        }
    }
}
