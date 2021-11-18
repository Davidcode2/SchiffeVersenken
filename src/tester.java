//import GUI.GUI;

import javax.swing.*;
import java.io.IOException;

//import static GUI.GUI.start;
import static GUI.Spielgui.ip;
import static GUI.Spielgui.port;

import GUI.Spielgui;

public class tester {

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(
                () -> { new Spielgui(1); }
        );
    }
}

