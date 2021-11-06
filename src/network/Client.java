package network;

import java.net.*;
import java.io.*;

public class Client {

	// Client-Seite eines sehr einfachen Chat-Programms mit Sockets.
	// (Anstelle von "throws IOException" sollte man Ausnahmen besser
	// gezielt mit try-catch auffangen.)
	// Verwendete Portnummer (vgl. Server).
	public Connection startConnection(String ip, int port) throws IOException {

		// Verbindung zum Server mit Name oder IP-Adresse args[0]
		// über Portnummer port herstellen.
		// Als Resultat erhält man ein Socket.
		Socket s = new Socket(ip, port);
		System.out.println("Connection established.");

		BufferedReader in =
				new BufferedReader(new InputStreamReader(s.getInputStream()));
		Writer out = new OutputStreamWriter(s.getOutputStream());

		// Standardeingabestrom ebenfalls als BufferedReader verpacken.
		BufferedReader usr =
				new BufferedReader(new InputStreamReader(System.in));
		Connection connection = new Connection(in, out, usr, s);
		return connection;
	}

		// Ein- und Ausgabestrom des Sockets ermitteln
		// und als BufferedReader bzw. Writer verpacken
		// (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).

		// Abwechselnd vom Benutzer lesen und ins Socket schreiben
		// bzw. vom Socket lesen und auf den Bildschirm schreiben.
		// Abbruch bei EOF oder Leerzeile vom Benutzer bzw. bei EOF vom Socket.
//		while (true) {
//			System.out.print(">>> ");
//			String line = usr.readLine();
//			if (line == null || line.equals("")) break;
//			out.write(String.format("%s%n", line));
//			out.flush();
//			// flush sorgt dafür, dass der Writer garantiert alle Zeichen
//			// in den unterliegenden Ausgabestrom schreibt.
//
//			line = in.readLine();
//			if (line == null) break;
//			System.out.println("<<< " + line);
//		}

		public void stopConnection(Connection connection) throws IOException {
			// EOF ins Socket "schreiben".
			connection.s.shutdownOutput();
			System.out.println("Connection closed.");
	}
}