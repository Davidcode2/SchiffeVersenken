package game.Swing2;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.table.*;

import java.util.List;
import java.util.ArrayList;


// Geeignete Implementierung der Schnittstelle TableModel.
class MyTableModel extends AbstractTableModel {
    // Anzahl der Tabellenzeilen.
    private int rows = 0;

    // Inhalte der Tabellenspalten.
    private List<String> gnames = new ArrayList<String>(); // Vornamen.
    private List<String> names = new ArrayList<String>();  // Nachnamen.
    private List<Icon> icons = new ArrayList<Icon>();	   // Piktogramme.

    // Neue leere Tabellenzeile hinzufügen.
    public void addRow () {
	// Leere Inhalte hinzufügen
	// und die GUI über die neue Zeile informieren.
	gnames.add("");
	names.add("");
	icons.add(null);
	fireTableRowsInserted(rows, rows);
	rows++;
    }

    // Tabellenzeile row entfernen.
    public void removeRow (int row) {
	// Inhalte der Zeile entfernen
	// und die GUI über die Entfernung informieren.
	gnames.remove(row);
	names.remove(row);
	icons.remove(row);
	fireTableRowsDeleted(row, row);
	rows--;
    }

    // Inhalt der Tabelle zu Testzwecken ausgeben.
    // (Achtung: Eine Änderung am Inhalt einer Zelle wird offenbar erst wirksam,
    // wenn man die Zelle wieder verlassen hat.)
    public void dump () {
	for (int row = 0; row < rows; row++) {
	    System.out.println(gnames.get(row) + " | " + names.get(row)
	      + " | " + icons.get(row));
	}
    }

    /*
     *  Implementierung von Methoden der Schnittstelle TableModel.
     */

    // Anzahl Zeilen liefern.
    public int getRowCount () { return rows; }

    // Anzahl Spalten liefern.
    public int getColumnCount () { return 3; }

    // Name von Spalte col liefern.
    public String getColumnName (int col) {
	switch (col) {
	case 0: return "Vorname";
	case 1: return "Nachname";
	case 2: return "Zustand";
	default: return null; // Sollte nicht auftreten.
	}
    }

    // Klasse der Objekte in Spalte col liefern
    // (damit die Objekte sinnvoll angezeigt werden).
    public Class getColumnClass (int col) {
	return col < 2 ? String.class : ImageIcon.class;
    }

    // Wert in Zeile row und Spalte col liefern.
    public Object getValueAt (int row, int col) {
	switch (col) {
	case 0: return gnames.get(row);
	case 1: return names.get(row);
	case 2: return icons.get(row);
	default: return null; // Sollte nicht auftreten.
	}
    }

    // Wert val in Zeile row und Spalte col speichern.
    public void setValueAt (Object val, int row, int col) {
	// Inhalt ändern
	// und die GUI über die Änderung informieren.
	switch (col) {
	case 0: gnames.set(row, (String)val); break;
	case 1: names.set(row, (String)val); break;
	case 2: icons.set(row, (Icon)val); break;
	}
	fireTableCellUpdated(row, col);
    }

    // Darf die Zelle in Zeile row und Spalte col geändert werden?
    public boolean isCellEditable (int row, int col) {
	return col < 2;
    }
}

// Zweites Beispiel zur Verwendung von (AWT und) Swing.
public class Swing2 {
    // Graphische Oberfläche aufbauen und anzeigen.
    public static void start () {
	// Hauptfenster mit Titelbalken etc. (JFrame) erzeugen.
	// "Swing2" wird in den Titelbalken geschrieben.
	JFrame frame = new JFrame("Swing2");

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

	// Dreispaltige Tabelle mit Scrollmöglichkeit.
	// (Lokale Variablen, die später in anonymen Funktionen verwendet
	// werden, müssen entweder "final" deklariert werden oder "effectively
	// final" sein, d. h. ihr Wert darf nirgends geändert werden.)
	/*final*/ MyTableModel model = new MyTableModel();
	/*final*/ JTable table = new JTable(model);
	table.setRowHeight(40);
	/*final*/ JScrollPane scrollPane = new JScrollPane(table);
	frame.add(scrollPane);

	// Zwischenraum der Breite 50 oder mehr.
	frame.add(Box.createHorizontalStrut(50));
	frame.add(Box.createHorizontalGlue());

	// Vertikale Box mit diversen Knöpfen.
	Box vbox = Box.createVerticalBox();
	{
	    // "Neuer Eintrag" fügt eine neue Tabellenzeile am Ende hinzu
	    // und "zieht" den Scrollbar ggf. ganz nach unten,
	    // damit die Zeile auch sichtbar ist.
	    JButton button = new JButton("Neuer Eintrag");
	    button.setAlignmentX(Component.CENTER_ALIGNMENT);
	    button.addActionListener(
		(e) -> {
		    model.addRow();
		    SwingUtilities.invokeLater(
			() -> {
			    // Scrollbar ganz nach unten "ziehen".
			    // Damit dies wie gewünscht funktioniert,
			    // darf es erst nach der Verarbeitung des aktuellen
			    // "Knopfdrucks" ausgeführt werden, weil die neue
			    // Zeile erst dann berücksichtigt wird.
			    JScrollBar sb = scrollPane.getVerticalScrollBar();
			    sb.setValue(sb.getMaximum());
			}
		    );
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
		    // Selektierte Tabellenzeile abfragen.
		    // (Negativ, falls keine Zeile selektiert ist.
		    // Wenn zuvor die letzte Zeile entfernt wurde,
		    // ist diese eventuell immer noch die selektierte Zeile.)
		    int row = table.getSelectedRow();
		    if (row < 0 || row >= model.getRowCount()) return;

		    // Diese Tabellenzeile entfernen.
		    model.removeRow(row);
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
			// Selektierte Tabellenzeile abfragen.
			int row = table.getSelectedRow();
			if (row < 0 || row >= model.getRowCount()) return;

			// Piktogramm speichern.
			model.setValueAt(icon, row, 2);
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
		    (e) -> { model.dump(); }
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

    // Hauptprogramm.
    public static void main (String [] args) {
	// Laut Swing-Dokumentation sollte die graphische Oberfläche
	// nicht direkt im Hauptprogramm (bzw. im Haupt-Thread) erzeugt
	// und angezeigt werden, sondern in einem von Swing verwalteten
	// separaten Thread.
	// Hierfür wird der entsprechende Code in eine parameterlose
	// anonyme Funktion () -> { ...... } "verpackt", die an
	// SwingUtilities.invokeLater übergeben wird.
	SwingUtilities.invokeLater(
	    () -> { start(); }
	);
    }
}
