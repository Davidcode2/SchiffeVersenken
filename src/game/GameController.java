package game;

import network.Client;
import network.Connection;
import network.Server;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.text.MessageFormat;

// Zweites Beispiel zur Verwendung von (AWT und) Swing.
public class GameController {
    public static String role = "";

    public static String getRole() {
        return role;
    }

    public static void setRole(String role) {
        GameController.role = role;
        System.out.println(String.format("role is %s", role));
    }

    // Graphische Oberfläche aufbauen und anzeigen.
    public static void start () {

        Server server = new Server();
        Client client = new Client();

        // Hauptfenster mit Titelbalken etc. (JFrame) erzeugen.
        // "Swing2" wird in den Titelbalken geschrieben.
        JFrame frame = new JFrame("SchiffeVersenken");

        // Beim Schließen des Fensters (z. B. durch Drücken des
        // X-Knopfs in Windows) soll das Programm beendet werden.
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Der Inhalt des Fensters soll von einem BoxLayout-Manager
        // verwaltet werden, der seine Bestandteile horizontal (von
        // links nach rechts) anordnet.
        frame.setContentPane(Box.createHorizontalBox());

        // Zwischenraum der Breite 50 oder mehr.
        frame.add(Box.createHorizontalStrut(50));
        frame.add(Box.createHorizontalGlue());

        // Vertikale Box mit diversen Knöpfen.
        Box vbox = Box.createVerticalBox();
        vbox.add(Box.createVerticalStrut(20));
        {
            // button um Server Socket zu eröffnen (Host)
            JButton button = new JButton("neues Spiel eröffnen");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(
                    (e) -> {
                        server.startConnection(50000);
                        setRole("host");
                    }
            );
            vbox.add(button);
        }
        vbox.add(Box.createVerticalStrut(20));
        {
            // neue Clientverbindung, Spiel beitreten
            JButton button = new JButton("Spiel beitreten");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(
                    (e) -> {
                        client.startConnection("127.0.0.1", 50000);
                        setRole("client");
                    }
            );
            vbox.add(button);
        }
        vbox.add(Box.createVerticalStrut(20));
        {
            // "Neuer Eintrag" fügt eine neue Tabellenzeile am Ende hinzu
            // und "zieht" den Scrollbar ggf. ganz nach unten,
            // damit die Zeile auch sichtbar ist.
            JButton button = new JButton("Neuer Eintrag");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(
                    (e) -> {
                        System.out.println(role);
                        button.setEnabled(false);
                        Connection connection;
                        if (getRole().equals("")) {
                            System.out.println("no connection");
                        }
                        if (getRole().equals("host")) {
                            connection = server.getConnection();
                        } else {
                            connection = client.getConnection();
                        }
                        try {
                            String message = "neuer Eintrag";
                            connection.getOut().write(String.format("%s%n", message));
                            connection.getOut().flush();
                        }
                        catch (IOException ex) {
                            System.out.println("write to socket failed");
                        }
                    }
            );
            vbox.add(button);
        }
        vbox.add(Box.createVerticalStrut(20));
        {
            // "Eintrag entfernen" entfernt die selektierte Tabellenzeile.
            JButton button = new JButton("Eintrag entfernen");
            button.setAlignmentX(Component.CENTER_ALIGNMENT);
            button.addActionListener(
                    (e) -> {

                    }
            );
            vbox.add(button);
        }
        vbox.add(Box.createVerticalStrut(20));
        {
            // Horizontale Box mit drei Knöpfen (rot, gelb, grün).
            // Wenn einer der Knöpfe gedrückt wird, wird sein
            // Piktogramm in der dritten Spalte der selektierten
            // Tabellenzeile gespeichert.
            Box hbox = Box.createHorizontalBox();
            for (String file : new String []
                    { "red.png", "yellow.png", "green.png" }) {
                /*final*/ Icon icon = new ImageIcon(file);
                JButton button = new JButton(icon);

                // Damit die Knöpfe mit einer ganz bestimmten, festen
                // Größe angezeigt werden, setzt man am besten alle
                // drei möglichen Größenangaben (preferred, minimum
                // und maximum) auf den gewünschten Wert.
                button.setPreferredSize(new Dimension(50, 50));
                button.setMinimumSize(new Dimension(50, 50));
                button.setMaximumSize(new Dimension(50, 50));

                button.addActionListener(
                        (e) -> {
                            Connection connection;
                            if (getRole().equals("")) {
                                System.out.println("no connection");
                            }
                            if (getRole().equals("host")) {
                                connection = server.getConnection();
                            } else {
                                connection = client.getConnection();
                            }

                        }
                );
                hbox.add(button);
            }
            vbox.add(hbox);
        }
        vbox.add(Box.createVerticalGlue());
        frame.add(vbox);

        // Zwischenraum der Breite 50 oder mehr.
        frame.add(Box.createHorizontalStrut(50));
        frame.add(Box.createHorizontalGlue());

        // Menüzeile (JMenuBar) erzeugen und einzelne Menüs (JMenu)
        // mit Menüpunkten (JMenuItem) hinzufügen.
        JMenuBar bar = new JMenuBar();
        {
            JMenu menu = new JMenu("Programm");
            {
                JMenuItem item = new JMenuItem("Beenden");
                item.addActionListener(
                        (e) -> { System.exit(0); }
                );
                menu.add(item);
            }
            bar.add(menu);
        }
        {
            JMenu menu = new JMenu("Tabelle");
            {
                JMenuItem item = new JMenuItem("Ausgeben");
                item.addActionListener(
                        (e) -> {

                        }
                );
                menu.add(item);
            }
            bar.add(menu);
        }

        // Menüzeile zum Fenster hinzufügen.
        frame.setJMenuBar(bar);

        // Am Schluss (!) die optimale Fenstergröße ermitteln (pack)
        // und das Fenster anzeigen (setVisible).
        frame.pack();
        frame.setVisible(true);
    }
}
