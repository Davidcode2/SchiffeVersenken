//import gui.GUI;

import javax.swing.*;

import gui.Spielgui;

import java.io.IOException;

public class tester {

    public static void main(String[] args) throws IOException {
        SwingUtilities.invokeLater(
                () -> { new Spielgui(1); }
        );
    }
}

