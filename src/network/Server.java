package network;

import javax.swing.*;
import java.net.*;
import java.io.*;

public class Server {

	private static Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public static Connection getConnection() {
		return connection;
	}

	public Socket startConnection(int port) {
		// Verwendete Portnummer.

		// Server-Socket erzeugen und an diesen Port binden.
		try {
			ServerSocket ss = new ServerSocket(port);

			// Auf eine Client-Verbindung warten und diese akzeptieren.
			// Als Resultat erhält man ein "normales" Socket.
			System.out.println("Waiting for client connection ...");
			System.out.println(String.format("on: %s", port));
			Socket s = ss.accept();
			// print out the ip address to which the socket is connected
			System.out.println(String.format("Connection established on %s ", s.getLocalAddress()));
			System.out.println(String.format("Inet Connection: %s ", s.getInetAddress()));
			return s;
		} catch (IOException e) {
			return null;
		}
	}
	public void createConnection(Socket s) throws IOException {

		// Ein- und Ausgabestrom des Sockets ermitteln
		// und als BufferedReader bzw. Writer verpacken
		// (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).
		BufferedReader in =
				new BufferedReader(new InputStreamReader(s.getInputStream()));
		Writer out = new OutputStreamWriter(s.getOutputStream());

		// Standardausgabestrom ebenfalls als BufferedWriter verpacken.
		BufferedWriter usr =
				new BufferedWriter(new OutputStreamWriter(System.out));

		connection = new Connection(in, out, usr);
		setConnection(connection);
	}

	public void startCommunicationLoop() {

		try {

//		 Abwechselnd vom Socket lesen und auf den Bildschirm schreiben
//		 bzw. vom Benutzer lesen und ins Socket schreiben.
//		 Abbruch bei EOF vom Socket bzw. bei EOF oder Leerzeile vom Benutzer.
			while (true) {
				// outgoing messages
				System.out.print(">>> ");
				connection.getUsr().write(String.format("%s%n", connection.getOut()));

				// incoming messages
				connection.setMessage(Connection.getIn().readLine());
				connection.setTurn(true);
				if (connection.getMessage() == null) break;
				System.out.println("<<< " + connection.getMessage());
			}

		} catch (IOException e) {
			System.out.println("error starting communication loop");
			e.printStackTrace();
		}
	}

	public static void sendMessage(String message) {
		try {
			if (connection.getTurn()) {
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

	public static void stopServer(Connection connection) throws IOException {
			// EOF ins Socket "schreiben".
			connection.getS().shutdownOutput();
			System.out.println("Connection closed.");
		}
}
