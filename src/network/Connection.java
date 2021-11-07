package network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;
import java.net.Socket;

public class Connection {

    private BufferedReader in;
    private BufferedReader usr;
    private Writer out;
    private Socket s;

    private Connection connection;

    public Connection(BufferedReader in, Writer out, BufferedReader usr, Socket s) {
        this.in = in;
        this.out = out;
        this.usr = usr;
        this.s = s;
    }

    public Socket getS() {
        return s;
    }

    public BufferedReader getUsr() {
        return usr;
    }

    public BufferedReader getIn() {
        return in;
    }

    public Writer getOut() {
        return out;
    }

    public Connection getConnection() {
        return connection;
    }

}
