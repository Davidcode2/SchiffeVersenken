package gui;

import network.Client;
import network.Connection;
import network.Server;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import javax.swing.*;

public class Spielgui {

	private JFrame frame;
	
	public static int port;
	public static String ip;
	public Socket socketS;
	
	private static boolean isSingleplayer;
	
	public static int fieldSize;
	public static int amount2x;
	public static int amount3x;
	public static int amount4x;
	public static int amount5x;
	
	private static JButton[][] field;
	private static boolean[][] ships;
	
	private static JButton[][] enemyfield;
	private static boolean[][] enemyschiffe;
	
	private static int hitCounter;
	private static int enemyHitCounter;
	
	public Spielgui(int zahl) {

		frame = new JFrame("Spiel");
		frame.setSize(500, 500);

		switch (zahl) {
			case 1:
				hauptmenue();
				break;
			case 2:
				einzelspieler();
				break;
			case 3:
				mehrspieler();
				break;
			case 4:
				host();
				break;
			case 5:
				client();
				break;
			case 6:
				schiffeplatzieren();
				break;
			case 7:
				spiel();
				break;
			case 8:
				winningScreen();
				break;
			case 9:
				losingScreen();
				break;
			default:
				System.out.println("Programm startet nicht.");
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private void hauptmenue() {

		JLabel label = new JLabel("Schiffe versenken");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton button1 = new JButton("Einzelspieler");
		button1.setFocusable(false);
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		button1.addActionListener((e) -> {
			frame.dispose();
			new Spielgui(2);
		});

		JButton button2 = new JButton("Mehrspieler");
		button2.setFocusable(false);
		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
		button2.addActionListener((e) -> {
			frame.dispose();
			new Spielgui(3);
		});
		/*
		JButton button3 = new JButton("Spiel laden");
		button3.setFocusable(false);
		button3.setAlignmentX(Component.CENTER_ALIGNMENT);
		button3.addActionListener((e) -> {
			System.out.println("laden");
		});
		*/
		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		frame.getContentPane().add(label);

		frame.getContentPane().add(button1);
		frame.getContentPane().add(button2);
		//frame.getContentPane().add(button3);

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

	}

	private void einzelspieler() {
		
		isSingleplayer=true;

		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		JLabel label = new JLabel("Schiffe versenken");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		frame.getContentPane().add(Box.createVerticalStrut(50));

		label = new JLabel("Wie lang soll das Spielfeld sein?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);
		
		label = new JLabel("(Zahlen zwischen 5 und 30 sind möglich)");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);
		
		JPanel panel = new JPanel();
		JTextField textfeld = new JTextField();
		textfeld.setHorizontalAlignment(SwingConstants.CENTER);
		textfeld.setColumns(10);
		panel.add(textfeld);
		
		JButton start = new JButton("Weiter");
		start.addActionListener((e) -> {
			try{Integer. parseInt(textfeld.getText());	//eingabe ist keine zahl
			}catch(NumberFormatException ex){
				frame.dispose();
				new Spielgui(2);
			}
			int test = Integer.parseInt(textfeld.getText());
			if(test>=5 && test<=30) {	//zahl wird überprüft
				fieldSize = test;
				shipAmount(fieldSize);
				hitCounter = 2*amount2x + 3*amount3x + 4*amount4x + 5*amount5x;
				enemyHitCounter = hitCounter;
				System.out.println("benötigte Treffer: "+hitCounter);
				frame.dispose();
				new Spielgui(6);
			}
			else {
				frame.dispose();
				new Spielgui(2);
			}
		});
		panel.add(start);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

	}

	private void mehrspieler() {

		JLabel label = new JLabel("Schiffe versenken");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		JButton button1 = new JButton("Host");
		button1.setFocusable(false);
		button1.setAlignmentX(Component.CENTER_ALIGNMENT);
		button1.addActionListener((e) -> {
			frame.dispose();
			new Spielgui(4);
		});

		JButton button2 = new JButton("Client");
		button2.setFocusable(false);
		button2.setAlignmentX(Component.CENTER_ALIGNMENT);
		button2.addActionListener((e) -> {
			frame.dispose();
			new Spielgui(5);
		});

		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		frame.getContentPane().add(label);

		frame.getContentPane().add(button1);

		frame.getContentPane().add(button2);

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

	}

	private void host() {

		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		JLabel label = new JLabel("Schiffe versenken");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		frame.getContentPane().add(Box.createVerticalStrut(50));

		label = new JLabel("Auf welchem Port möchten sie spielen?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);
		
		JPanel panel = new JPanel();
		JTextField textfeld = new JTextField();
		textfeld.addActionListener((e) -> {
			try{Integer.parseInt(textfeld.getText());
			}catch(NumberFormatException ex){
				frame.dispose();
				new Spielgui(4);
			}
			port = Integer.parseInt(textfeld.getText());
		});
		textfeld.setHorizontalAlignment(SwingConstants.CENTER);
		textfeld.setColumns(10);
		panel.add(textfeld);
		frame.getContentPane().add(panel);
		
		label = new JLabel("Wie lang soll das Spielfeld sein?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);
		
		label = new JLabel("(Zahlen zwischen 5 und 30 sind möglich)");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);
		
		panel = new JPanel();
		JTextField textfeld2 = new JTextField();
		textfeld2.addActionListener((e) -> {
			try{Integer.parseInt(textfeld2.getText());
			}catch(NumberFormatException ex){
				frame.dispose();
				new Spielgui(4);
			}
			int test = Integer.parseInt(textfeld2.getText());
			if(test>=5 && test<=30) {
				fieldSize = test;
				shipAmount(fieldSize);
				frame.dispose();
				network.Connection.setServer(true);
				System.out.println(String.format("setServer is: %s", Connection.isServer()));
				network.Server server = new Server();

				// Worker thread waiting for connection in background
				class StartConnectionService extends SwingWorker<Socket, Object> {
					@Override
					public Socket doInBackground() {
						socketS = server.startConnection(port);
						try {
							server.createConnection(socketS);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
						return socketS;
					}

					@Override
					protected void done() {
						try {
							socketS = get();
							System.out.println("Client connected to socket!");
						} catch (Exception ignore) {
							System.out.println("error");
						}
						class StartCommunicationService extends SwingWorker<String, Object> {
							@Override
							public String doInBackground() {
								server.startCommunicationLoop();
								return null;
							}
						}
						(new StartCommunicationService()).execute();
						System.out.println("Server ready to send and receive messages...\n");

						String x = String.valueOf(fieldSize);
						Connection.sendMessage(x);
					}
				}
				(new StartConnectionService()).execute();
				new Spielgui(6);
			}
			else {
				frame.dispose();
				new Spielgui(4);
			}
		});
		textfeld2.setHorizontalAlignment(SwingConstants.CENTER);
		textfeld2.setColumns(10);
		panel.add(textfeld2);
		frame.getContentPane().add(panel);
		
		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

		// TODO:
		// add submit button
		// actions in textfields should only be executed when pressed
		// TODO:
		// add back button

	}

	private void client() {
		
		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		JLabel label = new JLabel("Schiffe versenken");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		frame.getContentPane().add(Box.createVerticalStrut(50));

		label = new JLabel("Auf welchem Port möchten sie spielen?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		JPanel panel = new JPanel();
		JTextField textfeld = new JTextField();
		textfeld.addActionListener((e) -> {
			try{
				port = Integer.parseInt(textfeld.getText());
			}catch(NumberFormatException ex){
				frame.dispose();
				new Spielgui(5);
			}
		});
		textfeld.setHorizontalAlignment(SwingConstants.CENTER);
		textfeld.setColumns(10);
		panel.add(textfeld);
		frame.getContentPane().add(panel);

		label = new JLabel("Auf welcher IP möchten sie spielen?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		panel = new JPanel();
		JTextField promptIP = new JTextField();
		promptIP.addActionListener((e) -> {
			try{
				ip = promptIP.getText();
				frame.dispose();
			}catch(NumberFormatException ex){
				frame.dispose();
				new Spielgui(5);
			}
			Connection.setServer(false);
			network.Client client = new network.Client();
			System.out.println(String.format("connection data %s %s", ip, port));

			class ConnectionService extends SwingWorker<Socket, Object> {
				@Override
				public Socket doInBackground() {
					try {
						socketS = client.startConnection(ip, port);
						try {
							client.createConnection(socketS);
						} catch (IOException ex) {
							ex.printStackTrace();
						}
					} catch (IOException ex) {
						ex.printStackTrace();
					}
					return socketS;
				}
				@Override
				protected void done() {
					try {
						socketS = get();
					} catch (Exception ignore) {
						System.out.println("error starting communication loop");
					}
					class StartClientCommunicationService extends SwingWorker<String, Object> {
						@Override
						public String doInBackground() {
							client.startCommunicationLoop();
							return null;
						}
					}
					(new StartClientCommunicationService()).execute();
					System.out.print("Client ready to send and receive messages...\n");
					while (fieldSize == 0) {
						try {
							fieldSize = Integer.parseInt(Connection.getMessage());
						} catch (Exception ignore) {
						}
						// wait
					}
					// possibly problematic: gui call from within background thread
					Connection.sendMessage("done");
					new Spielgui(6);
				}
			}
			(new ConnectionService()).execute();
		});

		// TODO:
		// on submit
		// frame.dispose();

		promptIP.setHorizontalAlignment(SwingConstants.CENTER);
		promptIP.setColumns(10);
		panel.add(promptIP);
		frame.getContentPane().add(panel);

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

	}

	private void schiffeplatzieren() {
		
		ships = new boolean[fieldSize][fieldSize];
		
		if(isSingleplayer) {
			enemyschiffe = KI.kiPlacingShips(enemyschiffe);
		}
		else {
			enemyschiffe = new boolean[fieldSize][fieldSize];
		}
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JButton beginnen = new JButton("Spiel beginnen");
		beginnen.addActionListener((e) -> {
	        //Hier soll wenn wir client sind ein "ready" an den host schicken
			//Connection.sendMessage("ready"); habs mal auskommentiert weil es im einzelspieler ne exception schmeißt
			frame.dispose();
			new Spielgui(7);
		});
		menuBar.add(beginnen);
		
		JButton restart = new JButton("Schiffe neu setzen");
		restart.addActionListener((e) -> {
			shipAmount(fieldSize);
			frame.dispose();
			new Spielgui(6);
		});
		menuBar.add(restart);
		
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.75);
		frame.getContentPane().add(splitPane);
		
		JPanel panelLeft = new JPanel();
		splitPane.setLeftComponent(panelLeft);
		panelLeft.setLayout(new GridLayout(fieldSize, fieldSize, 1, 1));
		
		field = new JButton[fieldSize][fieldSize];
		
		for (int i = 0; i < fieldSize; i++) {
			for(int j = 0; j < fieldSize; j++) {
				field[i][j] = new JButton(1+j+i*fieldSize+"");
				field[i][j].setName(i+" "+j);
				field[i][j].addMouseListener(new MouseAdapter(){
				    public void mouseClicked(MouseEvent event){
						String[] s = ((JButton)event.getSource()).getName().split(" ");
						int x = Integer.parseInt(s[0]);
						int y = Integer.parseInt(s[1]);
						
				        if(SwingUtilities.isRightMouseButton(event)){
				        	placeShipRC(x,y);
				        }
				        else {
							placeShipLC(x,y);
				        }
				    }    
				});
				panelLeft.add(field[i][j]);
			}
		}
		
		JPanel panelRight = new JPanel();
		splitPane.setRightComponent(panelRight);
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		
		panelRight.add(Box.createVerticalStrut(50));
		panelRight.add(Box.createGlue());
		
		JLabel ships5x = new JLabel("	"+ amount5x +" 5er Schiffe");
		ships5x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships5x);
		
		JLabel ships4x = new JLabel("	"+ amount4x +" 4er Schiffe");
		ships4x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships4x);
		
		JLabel ships3x = new JLabel("	"+ amount3x +" 3er Schiffe");
		ships3x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships3x);
		
		JLabel ships2x = new JLabel("	"+ amount2x +" 2er Schiffe");
		ships2x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships2x);
		
		panelRight.add(Box.createGlue());
		panelRight.add(Box.createVerticalStrut(50));
		
		frame.pack();
	}

	private void spiel() {
		/*
		JMenuBar menuBar = new JMenuBar();
 		frame.setJMenuBar(menuBar);

		JButton speichern = new JButton("Speichern");
		speichern.addActionListener((e) -> {
			System.out.println("Speichern");
		});
		menuBar.add(speichern);
		*/
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.5);
		frame.getContentPane().add(splitPane);
		
		field = new JButton[fieldSize][fieldSize];
		enemyfield = new JButton[fieldSize][fieldSize];
				
		JPanel panelleft = new JPanel(); //links ist das Gegnerfeld
		splitPane.setLeftComponent(panelleft);
		panelleft.setLayout(new GridLayout(fieldSize, fieldSize, 1, 1));
		for (int i = 0; i < fieldSize; i++) {
			for(int j = 0; j < fieldSize; j++) {
				enemyfield[i][j] = new JButton(1+j+i*fieldSize+"");
				enemyfield[i][j].setName(i+" "+j);
				enemyfield[i][j].addActionListener((e) -> {
					String[] s = ((JButton)e.getSource()).getName().split(" ");
					int x = Integer.parseInt(s[0]);
					int y = Integer.parseInt(s[1]);
					if(enemyschiffe[x][y] == false){
						((JButton)e.getSource()).setBackground(new Color(0,0,255));
					} else {
		            	((JButton)e.getSource()).setBackground(new Color(255,0,0));
		            	hitCounter--;
		            	if(hitCounter==0) {
		            		frame.dispose();
		            		new Spielgui(8);
		            		return;
		            	}
		            }
					if(isSingleplayer) {
						enemyHitCounter -= KI.kiShot(field, ships);
						if(enemyHitCounter==0) {
							frame.dispose();
		            		new Spielgui(9);
		            	}
					}
				});
				panelleft.add(enemyfield[i][j]);
			}
		}
		
		JPanel panelright = new JPanel(); //rechts ist unser Feld hier werden bisher nur die von uns platzierten schiffe angezeigt
		splitPane.setRightComponent(panelright);
		panelright.setLayout(new GridLayout(fieldSize, fieldSize, 1, 1));
		
		for (int i = 0; i < fieldSize; i++) {
			for(int j = 0; j < fieldSize; j++) {
				field[i][j] = new JButton(1+j+i*fieldSize+"");
				field[i][j].setName(i+" "+j);
				if(ships[i][j] == false){
					field[i][j].setBackground(new Color(0,0,255));
				} else {
					field[i][j].setBackground(new Color(0,255,0));
	            }
				panelright.add(field[i][j]);
			}
		}
		
		frame.pack();
		
		/*
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JButton speichern = new JButton("Speichern");
		speichern.addActionListener((e) -> {
			System.out.println("Speichern");
		});
		menuBar.add(speichern);

		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(fieldSize, fieldSize, 1, 1));
		
		field = new JButton[fieldSize][fieldSize];
		
		for (int i = 0; i < fieldSize; i++) {
			for(int j = 0; j < fieldSize; j++) {
				field[i][j] = new JButton(1+j+i*fieldSize+"");
				field[i][j].setName(i+" "+j);
				field[i][j].addActionListener((e) -> {
					String[] s = ((JButton)e.getSource()).getName().split(" ");
					int x = Integer.parseInt(s[0]);
					int y = Integer.parseInt(s[1]);
					if(schiffe[x][y] == false){
						((JButton)e.getSource()).setBackground(new Color(0,255,0));
						Connection.sendMessage(String.format("shot %s %s", x, y));
					} else {
		            	((JButton)e.getSource()).setBackground(new Color(255,0,0));
						Connection.sendMessage(String.format("shot %s %s", x, y));
		            }
					
				});
				
				panel.add(field[i][j]);
			}
		}
		frame.getContentPane().add(panel);
		frame.pack();*/
	}

	private void winningScreen() {
		
		JMenuBar menuBar = new JMenuBar();
 		frame.setJMenuBar(menuBar);

		JButton endGame = new JButton("Spiel schließen");
		endGame.addActionListener((e) -> {
			frame.dispose();
		});
		menuBar.add(endGame);
		
		JLabel label = new JLabel("YOU WON");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		frame.getContentPane().add(label);

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));
	}

	private void losingScreen() {
		
		JMenuBar menuBar = new JMenuBar();
 		frame.setJMenuBar(menuBar);

		JButton endGame = new JButton("Spiel schließen");
		endGame.addActionListener((e) -> {
			frame.dispose();
		});
		menuBar.add(endGame);
		
		JLabel label = new JLabel("YOU LOST");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);

		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		frame.getContentPane().add(label);

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));
	}

	private void placeShipRC(int i, int j){

		if(amount2x!=0 && checkCollisionRC(i,j,2)){
			ships[i][j] = true;
			ships[i][j+1] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i][j+1].setBackground(new Color(0,255,0));

			amount2x--;

		} else if (amount3x!=0 && checkCollisionRC(i,j,3)){
			ships[i][j] = true;
			ships[i][j+1] = true;
			ships[i][j+2] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i][j+1].setBackground(new Color(0,255,0));
			field[i][j+2].setBackground(new Color(0,255,0));

			amount3x--;

		} else if (amount4x!=0 && checkCollisionRC(i,j,4)){
			ships[i][j] = true;
			ships[i][j+1] = true;
			ships[i][j+2] = true;
			ships[i][j+3] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i][j+1].setBackground(new Color(0,255,0));
			field[i][j+2].setBackground(new Color(0,255,0));
			field[i][j+3].setBackground(new Color(0,255,0));

			amount4x--;

		} else if (amount5x!=0 && checkCollisionRC(i,j,5)){
			ships[i][j] = true;
			ships[i][j+1] = true;
			ships[i][j+2] = true;
			ships[i][j+3] = true;
			ships[i][j+4] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i][j+1].setBackground(new Color(0,255,0));
			field[i][j+2].setBackground(new Color(0,255,0));
			field[i][j+3].setBackground(new Color(0,255,0));
			field[i][j+4].setBackground(new Color(0,255,0));

			amount5x--;

		} else {
			//Fehler: keine Schiffe mehr zu platzieren
			return;
		}
	}

	private void placeShipLC(int i, int j){

		if(amount2x!=0 && checkCollisionLC(i,j,2)){
			ships[i][j] = true;
			ships[i+1][j] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i+1][j].setBackground(new Color(0,255,0));

			amount2x--;

		} else if (amount3x!=0  && checkCollisionLC(i,j,3)){
			ships[i][j] = true;
			ships[i+1][j] = true;
			ships[i+2][j] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i+1][j].setBackground(new Color(0,255,0));
			field[i+2][j].setBackground(new Color(0,255,0));

			amount3x--;

		} else if (amount4x!=0 && checkCollisionLC(i,j,4)){
			ships[i][j] = true;
			ships[i+1][j] = true;
			ships[i+2][j] = true;
			ships[i+3][j] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i+1][j].setBackground(new Color(0,255,0));
			field[i+2][j].setBackground(new Color(0,255,0));
			field[i+3][j].setBackground(new Color(0,255,0));

			amount4x--;

		} else if (amount5x!=0 && checkCollisionLC(i,j,5)){
			ships[i][j] = true;
			ships[i+1][j] = true;
			ships[i+2][j] = true;
			ships[i+3][j] = true;
			ships[i+4][j] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i+1][j].setBackground(new Color(0,255,0));
			field[i+2][j].setBackground(new Color(0,255,0));
			field[i+3][j].setBackground(new Color(0,255,0));
			field[i+4][j].setBackground(new Color(0,255,0));

			amount5x--;

		} else {
			//Fehler: keine Schiffe mehr zu platzieren
			return;
		}
	}

	private boolean checkCollisionRC(int i, int j, int x){
		int u = 0;
		int v = 0;
		int h = 1;
		int h2 = x+1;

		//check collision wall
		if (j+x > fieldSize){
			return false;
		}

		//Button Top-Left-Corner
		if (i == 0 && j == 0){
			u = 0;
			v = 0;
			h = 1;
			h2 = x+1;
		}
		//Button left Edge
		else if (i > 0 && i < fieldSize-1 && j == 0){
			u = -1;
			v = 0;
			h = 1;
			h2 = x+1;
		}
		//Button Bottom-Left-Corner
		else if (i == fieldSize-1 && j == 0){
			u = -1;
			v = 0;
			h = 0;
			h2 = x+1;
		}
		//Button in middle of field
		else if (i > 0 && i < fieldSize - 1 && j > 0 && j < fieldSize -1 && j+x != fieldSize){
			u = -1;
			v = -1;
			h = 1;
			h2 = x+1;
		}
		//Button top Edge
		else if (i == 0 && j > 0 && j < fieldSize -1 && j+x != fieldSize){
			u = 0;
			v = -1;
			h = 1;
			h2 = x+1;
		}
		//Button bottom Edge
		else if (i == fieldSize-1 && j > 0 && j < fieldSize -1 && j+x != fieldSize){
			u = -1;
			v = -1;
			h = 0;
			h2 = x+1;
		}
		//Button right Edge
		else if (j+x == fieldSize && i > 0 && i < fieldSize - 1 && j > 0 && j < fieldSize -1) {
			u = 0;
			v = -1;
			h = 1;
			h2 = x;
		}
		//Button Top-Right-Corner
		else if (j+x == fieldSize && i == 0 && j > 0 && j < fieldSize -1){
			u = 0;
			v = -1;
			h = 1;
			h2 = x;
		}
		//Button Bottom-Right-Corner
		else if (i == fieldSize-1 && j > 0 && j < fieldSize -1 && j+x == fieldSize){
			u = -1;
			v = 0;
			h = 0;
			h2 = x;
		}

		for(int m=u; m<h+1; m++){
			for (int n=v; n<h2; n++){
				if (ships[i+m][j+n] == true){
					return false;
				}
			}
		}
		return true;
	}

	private boolean checkCollisionLC(int i, int j, int x){
		int u = 0;
		int v = 0;
		int h = 0;
		int h2 = 0;

		//check collision wall
		if (i+x > fieldSize){
			return false;
		}

		//Button Top-Left-Corner
		if (i == 0 && j == 0){
			u = 0;
			v = 0;
			h = 1;
			h2 = x+1;
		}
		//Button left Edge
		else if (i > 0 && i < fieldSize-1 && j == 0){
			u = 0;
			v = -1;
			h = 1;
			h2 = x+1;
		}
		//TODO
		//Button Bottom-Left-Corner
		else if (i == fieldSize-1 && j == 0){
			u = -1;
			v = 0;
			h = 0;
			h2 = x+1;
		}
		//Button in middle of field
		else if (i > 0 && i < fieldSize - 1 && j > 0 && j < fieldSize -1 && j+x != fieldSize){
			u = -1;
			v = -1;
			h = 1;
			h2 = x+1;
		}
		//Button top Edge
		else if (i == 0 && j > 0 && j < fieldSize -1 && j+x != fieldSize){
			u = -1;
			v = 0;
			h = 1;
			h2 = x+1;
		}
		//TODO
		//Button bottom Edge
		else if (i == fieldSize-1 && j > 0 && j < fieldSize -1 && j+x != fieldSize){
			u = -1;
			v = -1;
			h = 0;
			h2 = x+1;
		}
		//Button right Edge
		else if (j+x == fieldSize && i > 0 && i < fieldSize - 1 && j > 0 && j < fieldSize -1) {
			u = -1;
			v = 0;
			h = 0;
			h2 = x+1;
		}
		//Button Top-Right-Corner
		else if (j+x == fieldSize && i == 0 && j > 0 && j < fieldSize -1){
			u = 0;
			v = -1;
			h = 1;
			h2 = x;
		}
		//Button Bottom-Right-Corner
		else if (i == fieldSize-1 && j > 0 && j < fieldSize -1 && j+x == fieldSize){
			u = -1;
			v = 0;
			h = x;
			h2 = 0;
		}

		for(int m=u; m<h+1; m++){
			for (int n=v; n<h2; n++){
				if (ships[i+n][j+m] == true){
					return false;
				}
			}
		}
		return true;
	}

	private void shipAmount(int x){
		amount2x=0;
		amount3x=0;
		amount4x=0;
		amount5x=0;
		int temp;
		int rest = ((x*x)*3)/10;

		do{
			if(rest<30){
				if(rest>13){
					amount2x++;
					amount3x++;
					amount4x++;
					amount5x++;
					rest = rest - 14;
				} else if (rest > 8){
					amount2x++;
					amount3x++;
					amount4x++;
					rest = rest - 9;
				} else if (rest > 4){
					amount2x++;
					amount3x++;
					rest = rest - 5;
				} else {
					amount2x++;
					rest = rest - 2;
				}
			} else {
				temp = rest/30;
				rest = rest-temp*30;
				amount2x = 4*temp;
				amount3x = 3*temp;
				amount4x = 2*temp;
				amount5x = temp;
			}
		}while(rest != 0 && rest != 1);
		System.out.println("2x: " + amount2x);
		System.out.println("3x: " + amount3x);
		System.out.println("4x: " + amount4x);
		System.out.println("5x: " + amount5x);
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Spielgui(1);
		});
	}

}
