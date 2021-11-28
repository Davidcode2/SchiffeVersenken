//package gui;
//
//import javax.swing.*;
//import java.awt.*;
//import java.io.IOException;
//
//// Testgui für Netzwerkverbindung
//// Zweites Beispiel zur Verwendung von (AWT und) Swing.
//public class GUI {
//
//    // Graphische Oberfläche aufbauen und anzeigen.
//    public static void start () {
//
//        // Hauptfenster mit Titelbalken etc. (JFrame) erzeugen.
//        // "Swing2" wird in den Titelbalken geschrieben.
//        boolean connectionType = network.Connection.isServer();
//        String describer;
//        if (connectionType) {
//            describer = "Host";
//        } else {
//            describer = "Client";
//        }
//
//        JFrame frame = new JFrame(String.format("SchiffeVersenken %s", describer));
//
//        // Beim Schließen des Fensters (z. B. durch Drücken des
//        // X-Knopfs in Windows) soll das Programm beendet werden.
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//
//        // Der Inhalt des Fensters soll von einem BoxLayout-Manager
//        // verwaltet werden, der seine Bestandteile horizontal (von
//        // links nach rechts) anordnet.
//        frame.setContentPane(Box.createHorizontalBox());
//
//        // Zwischenraum der Breite 50 oder mehr.
//        frame.add(Box.createHorizontalStrut(50));
//        frame.add(Box.createHorizontalGlue());
//
//        // Vertikale Box mit diversen Knöpfen.
//        Box vbox = Box.createVerticalBox();
//        vbox.add(Box.createVerticalStrut(20));
//        {
//            // button um Server Socket zu eröffnen (Host)
//            JButton button = new JButton("Feuer auf 12");
//            button.setAlignmentX(Component.CENTER_ALIGNMENT);
//            button.addActionListener(
//                    (e) -> {
//                        if (network.Connection.isServer()) {
//                            network.Server.sendMessage("12");
//                        } else {
//                            network.Client.sendMessage("12");
//                        }
//                    }
//            );
//            vbox.add(button);
//        }
//        vbox.add(Box.createVerticalStrut(20));
//        {
//            // neue Clientverbindung, Spiel beitreten
//            JButton button = new JButton("Feuer auf 32");
//            button.setAlignmentX(Component.CENTER_ALIGNMENT);
//            button.addActionListener(
//                    (e) -> {
//                        if (network.Connection.isServer()) {
//                            network.Server.sendMessage("32");
//                        } else {
//                            network.Client.sendMessage("32");
//                        }
//
//                    }
//            );
//            vbox.add(button);
//        }
//        vbox.add(Box.createVerticalStrut(20));
//        {
//            // "Neuer Eintrag" fügt eine neue Tabellenzeile am Ende hinzu
//            // und "zieht" den Scrollbar ggf. ganz nach unten,
//            // damit die Zeile auch sichtbar ist.
//            JButton button = new JButton("Nachricht versenden");
//            button.setAlignmentX(Component.CENTER_ALIGNMENT);
//            button.addActionListener(
//                    (e) -> {
//                        if (network.Connection.isServer()) {
//                            network.Server.sendMessage("random Nachricht");
//                        } else {
//                            network.Client.sendMessage("random Nachricht");
//                        }
//                    }
//            );
//            vbox.add(button);
//        }
//        vbox.add(Box.createVerticalStrut(20));
//        {
//            // "Eintrag entfernen" entfernt die selektierte Tabellenzeile.
//            JButton button = new JButton("Aktuelle Nachricht lesen");
//            button.setAlignmentX(Component.CENTER_ALIGNMENT);
//            button.addActionListener(
//                    (e) -> {
//                        System.out.println(String.format("incoming message <<< %s%n", network.Connection.getMessage()));
//                    }
//            );
//            vbox.add(button);
//        }
//        vbox.add(Box.createVerticalStrut(20));
//        {
//            // Horizontale Box mit drei Knöpfen (rot, gelb, grün).
//            // Wenn einer der Knöpfe gedrückt wird, wird sein
//            // Piktogramm in der dritten Spalte der selektierten
//            // Tabellenzeile gespeichert.
//            Box hbox = Box.createHorizontalBox();
//            for (String file : new String []
//                    { "red.png", "yellow.png", "green.png" }) {
//                /*final*/ Icon icon = new ImageIcon(file);
//                JButton button = new JButton(icon);
//
//                // Damit die Knöpfe mit einer ganz bestimmten, festen
//                // Größe angezeigt werden, setzt man am besten alle
//                // drei möglichen Größenangaben (preferred, minimum
//                // und maximum) auf den gewünschten Wert.
//                button.setPreferredSize(new Dimension(50, 50));
//                button.setMinimumSize(new Dimension(50, 50));
//                button.setMaximumSize(new Dimension(50, 50));
//
//                button.addActionListener(
//                        (e) -> {
//                            if (network.Connection.isServer()) {
//                                network.Server.sendMessage("Button gedrückt");
//                            } else {
//                                network.Client.sendMessage("Button gedrückt");
//                            }
//                        }
//                );
//                hbox.add(button);
//            }
//            vbox.add(hbox);
//        }
//        vbox.add(Box.createVerticalGlue());
//        frame.add(vbox);
//
//        // Zwischenraum der Breite 50 oder mehr.
//        frame.add(Box.createHorizontalStrut(50));
//        frame.add(Box.createHorizontalGlue());
//
//        // Menüzeile (JMenuBar) erzeugen und einzelne Menüs (JMenu)
//        // mit Menüpunkten (JMenuItem) hinzufügen.
//        JMenuBar bar = new JMenuBar();
//        {
//            JMenu menu = new JMenu("Programm");
//            {
//                JMenuItem item = new JMenuItem("Beenden");
//                item.addActionListener(
//                        (e) -> { System.exit(0); }
//                );
//                menu.add(item);
//            }
//            bar.add(menu);
//        }
//        {
//            JMenu menu = new JMenu("Tabelle");
//            {
//                JMenuItem item = new JMenuItem("Ausgeben");
//                item.addActionListener(
//                        (e) -> {
//
//                        }
//                );
//                menu.add(item);
//            }
//            bar.add(menu);
//        }
//
//        // Menüzeile zum Fenster hinzufügen.
//        frame.setJMenuBar(bar);
//
//        // Am Schluss (!) die optimale Fenstergröße ermitteln (pack)
//        // und das Fenster anzeigen (setVisible).
//        frame.pack();
//        frame.setVisible(true);
//    }
//}
