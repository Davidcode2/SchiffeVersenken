//import GUI.GUI;

import javax.swing.*;
import java.io.IOException;

//import static GUI.GUI.start;
import static GUI.Spielgui.ip;
import static GUI.Spielgui.port;

import GUI.Spielgui;

public class tester {

    public static void main(String[] args) throws IOException {
        if (args.length > 0 && args[0].equals("host")) {
            network.Connection.setServer("server");
        } else {
        }
        SwingUtilities.invokeLater(
                () -> { new Spielgui(1); }
        );

        System.out.println("before execution of while loop in tester");
        while (true) {
            if (network.Connection.isServer().equals("notset")) {
                System.out.println("is Server is not null");
                if (network.Connection.isServer().equals("server")) {
                    network.Server server = new network.Server();
                    server.startConnection(port);
                } else if (network.Connection.isServer().equals("client")) {
                    network.Client client = new network.Client();
                    client.startConnection(ip, port);
                }
                break;
            }
        }
    }
}

