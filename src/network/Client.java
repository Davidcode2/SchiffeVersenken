package network;

import javax.swing.*;
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
	public void startConnection(String ip, int port) throws IOException {

//		try {
			// Verbindung zum Server mit Name oder IP-Adresse args[0]
			// über Portnummer port herstellen.
			// Als Resultat erhält man ein Socket.
			Socket s = new Socket(ip, port);
			System.out.println(connection.isServer());
			System.out.println(String.format("Connection established on %s ", s.getLocalAddress()));
			System.out.println(String.format("Inet Connection: %s ", s.getInetAddress()));


			BufferedReader in =
					new BufferedReader(new InputStreamReader(s.getInputStream()));
			Writer out = new OutputStreamWriter(s.getOutputStream());

			// Standardausgabestrom ebenfalls als BufferedWriter verpacken.
			BufferedWriter usr =
					new BufferedWriter(new OutputStreamWriter(System.out));

			connection = new Connection(in, out, usr, s);
			setConnection(connection);

			// Abwechselnd vom Benutzer lesen und ins Socket schreiben
			// bzw. vom Socket lesen und auf den Bildschirm schreiben.
			// Abbruch bei EOF oder Leerzeile vom Benutzer bzw. bei EOF vom Socket.

			while (true) {
				// outgoing messages
				System.out.print(">>> ");
				usr.write(String.format("%s%n", out));
//			String line = usr.readLine();
//			if (line == null || line.equals("")) break;
//			out.write(String.format("%s%n", line));
//			out.flush();
				// flush sorgt dafür, dass der Writer garantiert alle Zeichen
				// in den unterliegenden Ausgabestrom schreibt.

				// incoming messages
				connection.setMessage(in.readLine());
				connection.setTurn(true);
				if (connection.getMessage() == null) break;
				System.out.println(connection.getMessage());
			}
//		} catch (IOException e) {
//			System.out.println(e.getStackTrace());
//		}
	}

	public static void sendMessage(String message) {
		try {
			if (connection.getTurn() == true) {
//			System.out.println(connection.isServer());
				connection.getOut().write(String.format("%s%n", message));
				connection.getOut().flush();
				connection.setTurn(false);
//			connection.getIn().readLine();
			} else {
				System.out.println("wait for other players turn");
			}
		} catch (IOException e) {
			System.out.println("write to socket failed");
			e.printStackTrace();
		}
	}


	// Ein- und Ausgabestrom des Sockets ermitteln
	// und als BufferedReader bzw. Writer verpacken
	// (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).


	public static void stopConnection(Connection connection) throws IOException {
		// EOF ins Socket "schreiben".
		connection.getS().shutdownOutput();
		System.out.println("Connection closed.");
	}
}