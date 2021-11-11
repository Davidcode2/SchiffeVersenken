package network;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;

public class Connection {

    private static BufferedReader in;
    private static BufferedReader usr;
    private static Writer out;
    private static Socket s;

    private static boolean isServer;
    private static String message;
    private static boolean turn = true;

    public Connection(BufferedReader in, Writer out, BufferedWriter usr, Socket s) {
        this.in = in;
        this.out = out;
        this.s = s;
    }

    public static void setServer(boolean bool) {
        isServer = bool;
    }
    public static boolean isServer() {
        return isServer;
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

    public static String getMessage() {
        return message;
    }
    public void setMessage(String message) {
        this.message = message;
    }

    public void setTurn(boolean b) {
        this.turn = b;
    }

    public boolean getTurn() {
        return this.turn;
    }
}
