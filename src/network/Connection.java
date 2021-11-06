package network;

import java.io.BufferedReader;
import java.io.Writer;
import java.net.Socket;

public class Connection {

    BufferedReader in;
    BufferedReader usr;
    Writer out;
    Socket s;

    public Connection(BufferedReader in, Writer out, BufferedReader usr, Socket s) {
        this.in = in;
        this.out = out;
        this.usr = usr;
        this.s = s;
    }
}
