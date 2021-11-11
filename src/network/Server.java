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
			System.out.println("Connection established.");

			// Ein- und Ausgabestrom des Sockets ermitteln
			// und als BufferedReader bzw. Writer verpacken
			// (damit man zeilen- bzw. zeichenweise statt byteweise arbeiten kann).
			BufferedReader in =
					new BufferedReader(new InputStreamReader(s.getInputStream()));
			Writer out = new OutputStreamWriter(s.getOutputStream());

			// Standardeingabestrom ebenfalls als BufferedReader verpacken.
			BufferedReader usr =
					new BufferedReader(new InputStreamReader(System.in));

			connection = new Connection(in, out, usr, s);
			setConnection(connection);

//		 Abwechselnd vom Socket lesen und auf den Bildschirm schreiben
//		 bzw. vom Benutzer lesen und ins Socket schreiben.
//		 Abbruch bei EOF vom Socket bzw. bei EOF oder Leerzeile vom Benutzer.
			while (true) {
				// incoming messages
				String line = in.readLine();
				if (line == null) break;
				System.out.println("<<< " + line);

				// outgoing messages
				System.out.print(">>> ");
				line = usr.readLine();
				if (line == null || line.equals("")) break;
				out.write(String.format("%s%n", line));
				out.flush();
				// flush sorgt dafür, dass der Writer garantiert alle Zeichen
				// in den unterliegenden Ausgabestrom schreibt.
			}

		} catch (IOException e) {
			System.out.println("error opening the Server Socket");
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

	public void  communicationLoop() throws IOException {
	}
	public static void stopServer(Connection connection) throws IOException {
			// EOF ins Socket "schreiben".
			connection.getS().shutdownOutput();
			System.out.println("Connection closed.");
		}
}
