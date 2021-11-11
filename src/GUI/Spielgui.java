package GUI;

import java.awt.*;
import java.util.Random;

import javax.swing.*;

public class Spielgui {

	private JFrame frame;
	private JLabel label;
	private static int fieldSize;
	private static JButton[][] field;
	private static boolean[][] schiffe;
	
	public static int getfieldSize() {
		return fieldSize;
	}
	
	public static JButton[][] getfield() {
		return field;
	}
	
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
			default:
				System.out.println("Programm startet nicht.");
		}
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// frame.pack();
		frame.setVisible(true);
	}

	private void hauptmenue() {

		label = new JLabel("Schiffe versenken");
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

		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		frame.getContentPane().add(label);

		frame.getContentPane().add(button1);

		frame.getContentPane().add(button2);

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

	}

	private void einzelspieler() {

		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		label = new JLabel("Schiffe versenken");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		frame.getContentPane().add(Box.createVerticalStrut(50));

		label = new JLabel("Wie groß soll das Spielfeld sein?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		JTextField textfeld = new JTextField(30);
		// textfeld.setSize(50, 50);
		frame.getContentPane().add(textfeld);
		textfeld.addActionListener((e) -> {
			fieldSize = Integer.parseInt(textfeld.getText());
			frame.dispose();
			new Spielgui(6);
		});

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));
		// frame.pack();

	}

	private void mehrspieler() {

		label = new JLabel("Schiffe versenken");
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

		label = new JLabel("Schiffe versenken");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		frame.getContentPane().add(Box.createVerticalStrut(50));

		label = new JLabel("Auf welchem Port möchten sie spielen?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		JTextField textfeld = new JTextField(30);
		textfeld.setSize(50, 50);
		frame.getContentPane().add(textfeld);
		textfeld.addActionListener((e) -> {
			int port = Integer.parseInt(textfeld.getText());
			System.out.println(port);
		});

		label = new JLabel("Wie groß soll das Spielfeld sein?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		JTextField textfeld2 = new JTextField(30);
		// textfeld.setSize(50, 50);
		frame.getContentPane().add(textfeld2);
		textfeld2.addActionListener((e) -> {
			fieldSize = Integer.parseInt(textfeld2.getText());
			System.out.println(fieldSize);
		});

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

	}

	private void client() {
		
		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		label = new JLabel("Schiffe versenken");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		frame.getContentPane().add(Box.createVerticalStrut(50));

		label = new JLabel("Auf welchem Port möchten sie spielen?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);

		JTextField textfeld = new JTextField(30);
		//textfeld.setSize(50, 50);
		frame.getContentPane().add(textfeld);
		textfeld.addActionListener((e) -> {
			int port = Integer.parseInt(textfeld.getText());
			System.out.println(port);
		});

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

	}

	private void schiffeplatzieren() {

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		schiffe = new boolean[fieldSize][fieldSize];

		JButton speichern = new JButton("Spiel beginnen");
		speichern.addActionListener((e) -> {
			Random rand = new Random();

	        for(int i=0;i<10;i++){
	            int x = rand.nextInt((fieldSize - 1) + 1);
	            int y = rand.nextInt((fieldSize - 1) + 1);

	            if(schiffe[x][y] == false){
	                schiffe[x][y] = true;
	            } else if(schiffe[x][y] == true){
	                i=i-1;
	            }
	        }
	        frame.dispose();
			new Spielgui(7);
		});
		menuBar.add(speichern);
		/*
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(fieldSize, fieldSize, 1, 1));
		
		field = new JButton[fieldSize][fieldSize];
		
		for (i = 0; i < fieldSize; i++) {
			
			for(j = 0; j < fieldSize; j++) {
				field[i][j] = new JButton(1+j+fieldSize*i + "");
				field[i][j].addActionListener((e) -> {
					if(schiffe[i][j] == false){
						((JButton)e.getSource()).setBackground(new Color(255,0,0));
		            } else {
		            	((JButton)e.getSource()).setBackground(new Color(0,255,0));
		            }
					
				});
				
				panel.add(field[i][j]);
			}
		}
		frame.getContentPane().add(panel);
		frame.pack();
		*/
	}
	
	private void spiel() {

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
				field[i][j].setName(i+""+j);
				field[i][j].addActionListener((e) -> {
					int x = Integer.parseInt(((JButton)e.getSource()).getName())/10;
					int y = Integer.parseInt(((JButton)e.getSource()).getName())%10;
					if(schiffe[x][y] == false){
						((JButton)e.getSource()).setBackground(new Color(0,255,0));
		            } else {
		            	((JButton)e.getSource()).setBackground(new Color(255,0,0));
		            }
					
				});
				
				panel.add(field[i][j]);
			}
		}
		frame.getContentPane().add(panel);
		frame.pack();
	}
	
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			new Spielgui(1);
		});
	}

}
