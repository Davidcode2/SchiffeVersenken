package game;

import network.Connection;
import network.Server;
import network.Client;

import java.awt.*;
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
