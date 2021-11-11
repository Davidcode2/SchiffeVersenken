package network;

import java.net.*;
import java.io.*;

public class Server {

	private static Connection connection;

	public void setConnection(Connection connection) {
		this.connection = connection;
	}

	public Connection getConnection() {
		return connection;
	}

	public void startConnection(int port) {
		// Verwendete Portnummer.

		// Server-Socket erzeugen und an diesen Port binden.
		try {
			ServerSocket ss = new ServerSocket(port);

			// Auf eine Client-Verbindung warten und diese akzeptieren.
			// Als Resultat erhält man ein "normales" Socket.
			System.out.println("Waiting for client connection ...");
			Socket s = ss.accept();
			System.out.println(String.format("Connection established on %s ", s.getLocalAddress()));
			System.out.println(String.format("Inet Connection: %s ", s.getInetAddress()));

			// print out the ip address to which the socket is connected

			// Ein- und Ausgabestrom des Sockets ermitteln
			// und als BufferedReader bzw. Writer verpacken
			// (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).
			BufferedReader in =
					new BufferedReader(new InputStreamReader(s.getInputStream()));
			Writer out = new OutputStreamWriter(s.getOutputStream());

			// Standardausgabestrom ebenfalls als BufferedWriter verpacken.
				BufferedWriter usr =
						new BufferedWriter(new OutputStreamWriter(System.out));

			connection = new Connection(in, out, usr, s);
			setConnection(connection);

			connection.setTurn(false);

//		 Abwechselnd vom Socket lesen und auf den Bildschirm schreiben
//		 bzw. vom Benutzer lesen und ins Socket schreiben.
//		 Abbruch bei EOF vom Socket bzw. bei EOF oder Leerzeile vom Benutzer.
			while (true) {
				// incoming messages
				connection.setMessage(in.readLine());
				connection.setTurn(true);
//				String line = in.readLine();
				if (connection.getMessage() == null) break;
				System.out.println("<<< " + connection.getMessage());

				// outgoing messages
				System.out.print(">>> ");
				usr.write(String.format("%s%n", out));
				// flush sorgt dafür, dass der Writer garantiert alle Zeichen
				// in den unterliegenden Ausgabestrom schreibt.
			}

		} catch (IOException e) {
			System.out.println("error opening the Server Socket");
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

	public void  communicationLoop() throws IOException {
	}
	public static void stopServer(Connection connection) throws IOException {
			// EOF ins Socket "schreiben".
			connection.getS().shutdownOutput();
			System.out.println("Connection closed.");
		}
}
