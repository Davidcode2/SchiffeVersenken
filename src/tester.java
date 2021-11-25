//import GUI.GUI;

import javax.swing.*;
import java.io.IOException;

import GUI.Spielgui;

public class tester {

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(
                () -> { new Spielgui(1); }
        );
    }
}

