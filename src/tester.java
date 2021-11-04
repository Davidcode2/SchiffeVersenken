import game.Swing2.Swing2;

import javax.swing.*;
import java.io.IOException;

import static game.Swing2.Swing2.start;

public class tester {

    public static void main(String[] args) throws IOException {
        game.Swing2.Swing2 gui = new game.Swing2.Swing2();
        SwingUtilities.invokeLater(
                () -> { start(); }
        );
        if (args.length > 0 && args[0].equals("host")) {
            network.Server server = new network.Server();
            server.startServer(50000);
        } else {
            network.Client client = new network.Client();
            client.startConnection("127.0.0.1", 50000);
        }
    }
}

