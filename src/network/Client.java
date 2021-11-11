package network;

import java.net.*;
import java.io.*;

public class Client {

	private static Connection connection;

	public Connection getConnection() {
		return connection;
	}

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	// Client-Seite eines sehr einfachen Chat-Programms mit Sockets.
	// (Anstelle von "throws IOException" sollte man Ausnahmen besser
	// gezielt mit try-catch auffangen.)
	// Verwendete Portnummer (vgl. Server).
	public void startConnection(String ip, int port) {

		try {
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
			connection = new Connection(in, out, usr, s);
			setConnection(connection);

			// Abwechselnd vom Benutzer lesen und ins Socket schreiben
			// bzw. vom Socket lesen und auf den Bildschirm schreiben.
			// Abbruch bei EOF oder Leerzeile vom Benutzer bzw. bei EOF vom Socket.

		while (true) {
			// outgoing messages
			System.out.print(">>> ");
			String line = usr.readLine();
			if (line == null || line.equals("")) break;
			out.write(String.format("%s%n", line));
			out.flush();
			// flush sorgt dafür, dass der Writer garantiert alle Zeichen
			// in den unterliegenden Ausgabestrom schreibt.

			// incoming messages
			line = in.readLine();
			if (line == null) break;
			System.out.println("<<< " + line);
		}
		} catch (IOException e) {
			System.out.println(e.getStackTrace());
		}
	}

	public static void sendMessage(String message) {
		try {
			connection.getOut().write(message);
			connection.getOut().flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


		// Ein- und Ausgabestrom des Sockets ermitteln
		// und als BufferedReader bzw. Writer verpacken
		// (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).


		public void stopConnection(Connection connection) throws IOException {
			// EOF ins Socket "schreiben".
			connection.getS().shutdownOutput();
			System.out.println("Connection closed.");
	}
}