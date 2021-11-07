import game.GameController;

import javax.swing.*;
import java.io.IOException;

import static game.GameController.start;

public class tester {

    public static void main(String[] args) throws IOException {
        GameController gui = new GameController();
        SwingUtilities.invokeLater(
                () -> { start(); }
        );
//        if (args.length > 0 && args[0].equals("host")) {
//            network.Server server = new network.Server();
//            server.startServer(50000);
//        } else {
//            network.Client client = new network.Client();
//            client.startConnection("127.0.0.1", 50000);
//        }
    }
}

