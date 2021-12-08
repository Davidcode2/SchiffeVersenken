import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;

public class Connection {

    private static BufferedReader in;
    private static BufferedWriter usr;
    private static Writer out;
    private static Socket s;

    private static boolean isServer;
    private static String message;
    private static boolean turn = true;
    private static int readyCounter = 0;

    private static boolean Multiplayer = false;

    public Connection(BufferedReader in, Writer out, BufferedWriter usr) {
        this.in = in;
        this.out = out;
        this.usr = usr;
    }

    public static void setServer(boolean bool) {
        isServer = bool;
    }
    public static boolean isServer() {
        return isServer;
    }

    public static boolean Multiplayer() {
        return Multiplayer;
    }
    public static void setMultiplayer(boolean b) {
        Multiplayer = b;
    }

    public static Socket getS() {
        return s;
    }

    public static BufferedReader getIn() {
        return in;
    }

    public static Writer getOut() {
        return out;
    }

    public static BufferedWriter getUsr() {
        return usr;
    }

    public static String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setTurn(boolean b) {
        turn = b;
    }

    public boolean getTurn() {
        return turn;
    }

    public static void sendMessage(int x, int y) {
        if (isServer()) {
            try {
                if (Server.getConnection().getTurn()) {
                    Server.getConnection().getOut().write(String.format("shot %s %s%n", x,y));
                    Server.getConnection().getOut().flush();
                    Server.getConnection().setTurn(false);
                } else {
                    System.out.println("wait for other players turn");
                }
            } catch (IOException e) {
                System.out.println("write to socket failed");
                e.printStackTrace();
            }
        } else {
            try {
                if (Client.getConnection().getTurn()) {
                    Client.getConnection().getOut().write(String.format("shot %s %s%n", x,y));
                    Client.getConnection().getOut().flush();
                    Client.getConnection().setTurn(false);
                } else {
                    System.out.println("wait for other players turn");
                }
            } catch (IOException e) {
                System.out.println("write to socket failed");
                e.printStackTrace();
            }
        }
    }
    public static void sendMessage(String message) {
        // if turn == true -> Server
        if (isServer()) {
            try {
                if (Server.getConnection().getTurn() || (message.equals("ready") && readyCounter == 0)) {
                    if (message.equals("ready")) {
                        readyCounter = 1;
                    }
                    Server.getConnection().getOut().write(String.format("%s%n", message));
                    Server.getConnection().getOut().flush();
                    System.out.println("outgoing>> " + message);
                    Server.getConnection().setTurn(false);
                } else {
                    System.out.println("wait for other players turn");
                }
            } catch (IOException e) {
                System.out.println("write to socket failed");
                e.printStackTrace();
            }
        } else {
            try {
                if (Client.getConnection().getTurn() || (message.equals("ready") && readyCounter == 0)) {
                    if (message.equals("ready")) {
                        readyCounter = 1;
                    }
                    Client.getConnection().getOut().write(String.format("%s%n", message));
                    Client.getConnection().getOut().flush();
                    System.out.println("outgoing>> " + message);
                    Client.getConnection().setTurn(false);
                } else {
                    System.out.println("wait for other players turn");
                }
            } catch (IOException e) {
                System.out.println("write to socket failed");
                e.printStackTrace();
            }
        }

    }
}
