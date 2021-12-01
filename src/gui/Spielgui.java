package gui;

import network.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.Socket;
import javax.swing.*;

public class Spielgui {

	private JFrame frame;
	
	public static int port;
	public static String ip;
	public Socket socketS;
	
	private static boolean isSingleplayer;
	
	public static int fieldSize;
	
	protected static JButton[][] field;
	private static boolean[][] ships;
	
	private static JButton[][] enemyField;
	private static boolean[][] enemyShips;
	
	private static int[][] helpArray;
	
	private static int hitCounter;
	private static int enemyHitCounter;

	public int getFieldSize() {
		return fieldSize;
	}
	
	public Spielgui(int number) {

		frame = new JFrame("Spiel");
		frame.setSize(1440, 810);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
		switch (number) {
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
			if(test>4 && test<31) {	//zahl wird überprüft
				fieldSize = test;
				Logic.shipAmount(fieldSize);
				hitCounter = 2*Logic.amount2x + 3*Logic.amount3x + 4*Logic.amount4x + 5*Logic.amount5x;
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
				Logic.shipAmount(fieldSize);
				frame.dispose();
				network.Connection.setServer(true);
				System.out.println(String.format("setServer is: %s", Connection.isServer()));

				// start connection
				(new ServerConnectionService()).execute();
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
			// start connection
			(new ClientConnectionService()).execute();
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
		
		enemyShips = new boolean[fieldSize][fieldSize];
		
		if(isSingleplayer) {
			boolean wert = false;
			while(!wert)
				wert = Ki.kiPlacingShips(enemyShips);
		}
		
		//hilfsarray bekommt später 3 verschiedene Werte: 0 für Wasser, 1 für Schiff, 2 für Wasser ums Schiff
		helpArray = new int[fieldSize][fieldSize];
						
		//hilfsarray wird mit 0(Wasser) gefüllt
		for(int i=0; i<fieldSize; i++) {
			for(int j=0; j<fieldSize; j++) {
				helpArray[i][j]=0;
			}
		}
		
		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);

		JButton begin = new JButton("Spiel beginnen");
		begin.addActionListener((e) -> {
			if(Logic.amount2x+Logic.amount3x+Logic.amount4x+Logic.amount5x == 0) {
				for(int i=0; i<fieldSize; i++) {
					for(int j=0; j<fieldSize; j++) {
						if(helpArray[i][j]==1) {
							ships[i][j]=true;	//die schiffe in den boolean array setzen
						}
					}
				}
		        //Hier soll wenn wir client sind ein "ready" an den host schicken
				//Connection.sendMessage("ready"); habs mal auskommentiert weil es im einzelspieler ne exception schmeißt
				frame.dispose();
				new Spielgui(7);
			}
		});
		menuBar.add(begin);
		
		JButton restart = new JButton("Schiffe neu setzen");
		restart.addActionListener((e) -> {
			Logic.shipAmount(fieldSize);
			frame.dispose();
			new Spielgui(6);
		});
		menuBar.add(restart);
		
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.75);
		frame.getContentPane().add(splitPane);

		JPanel panelRight = new JPanel();
		splitPane.setRightComponent(panelRight);
		panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));
		
		panelRight.add(Box.createVerticalStrut(50));
		panelRight.add(Box.createGlue());
		
		JLabel infoLeft = new JLabel(" Linksklick platziert das Schiff vertikal ");
		infoLeft.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(infoLeft);
		
		JLabel infoRight = new JLabel(" Rechtsklick platziert das Schiff horizontal ");
		infoRight.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(infoRight);
		
		JLabel ships5x = new JLabel("	"+ Logic.amount5x +" 5er Schiffe");
		ships5x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships5x);
		
		JLabel ships4x = new JLabel("	"+ Logic.amount4x +" 4er Schiffe");
		ships4x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships4x);
		
		JLabel ships3x = new JLabel("	"+ Logic.amount3x +" 3er Schiffe");
		ships3x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships3x);
		
		JLabel ships2x = new JLabel("	"+ Logic.amount2x +" 2er Schiffe");
		ships2x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships2x);
		
		panelRight.add(Box.createGlue());
		panelRight.add(Box.createVerticalStrut(50));
		
		JPanel panelLeft = new JPanel();
		splitPane.setLeftComponent(panelLeft);
		panelLeft.setLayout(new GridLayout(fieldSize, fieldSize, 1, 1));
		
		field = new JButton[fieldSize][fieldSize];
		
		for (int i = 0; i < fieldSize; i++) {
			for(int j = 0; j < fieldSize; j++) {
				field[i][j] = new JButton();
				field[i][j].setName(i+" "+j);
				field[i][j].addMouseListener(new MouseAdapter(){
				    public void mouseClicked(MouseEvent event){
						String[] s = ((JButton)event.getSource()).getName().split(" ");
						int positionVertical = Integer.parseInt(s[0]);
						int positionHorizontal = Integer.parseInt(s[1]);
						boolean wert = false;
				        if(SwingUtilities.isRightMouseButton(event)){
				        	wert=Logic.placeShipHorizontal(positionVertical,positionHorizontal,helpArray);
				        }
				        else {
				        	wert=Logic.placeShipVertical(positionVertical,positionHorizontal,helpArray);
				        }
				        if(wert) {
				        	ships5x.setText("	"+ Logic.amount5x +" 5er Schiffe");
				        	ships4x.setText("	"+ Logic.amount4x +" 4er Schiffe");
				        	ships3x.setText("	"+ Logic.amount3x +" 3er Schiffe");
				        	ships2x.setText("	"+ Logic.amount2x +" 2er Schiffe");
				        }
				    }
				});
				panelLeft.add(field[i][j]);
			}
		}
		//frame.pack();
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
		enemyField = new JButton[fieldSize][fieldSize];
				
		JPanel panelleft = new JPanel(); //links ist das Gegnerfeld
		splitPane.setLeftComponent(panelleft);
		panelleft.setLayout(new GridLayout(fieldSize, fieldSize, 1, 1));
		for (int i = 0; i < fieldSize; i++) {
			for(int j = 0; j < fieldSize; j++) {
				enemyField[i][j] = new JButton(1+j+i*fieldSize+"");
				enemyField[i][j].setName(i+" "+j);
				enemyField[i][j].addActionListener((e) -> {
					String[] s = ((JButton)e.getSource()).getName().split(" ");
					int x = Integer.parseInt(s[0]);
					int y = Integer.parseInt(s[1]);
					if(enemyShips[x][y] == false){
						((JButton)e.getSource()).setBackground(new Color(0,0,255));
						//Connection.sendMessage(String.format("shot %s %s", x, y));
					} else {
		            	((JButton)e.getSource()).setBackground(new Color(255,0,0));
		            	//Connection.sendMessage(String.format("shot %s %s", x, y));
		            	hitCounter--;
		            	if(hitCounter==0) {
		            		frame.dispose();
		            		new Spielgui(8);
		            		return;
		            	}
		            }
					if(isSingleplayer) {
						enemyHitCounter -= Ki.kiShot(field, ships);
						if(enemyHitCounter==0) {
							frame.dispose();
		            		new Spielgui(9);
		            	}
					}
				});
				panelleft.add(enemyField[i][j]);
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
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Spielgui(1);
		});
	}
}