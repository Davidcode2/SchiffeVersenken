package GUI;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Random;

import javax.swing.*;

public class Spielgui {

	private JFrame frame;
	private JPanel panel;
	private JLabel label;
	private static int port;
	private static int fieldSize;
	private static int amount2x;
	private static int amount3x;
	private static int amount4x;
	private static int amount5x;
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
		
		JButton button3 = new JButton("Spiel laden");
		button3.setFocusable(false);
		button3.setAlignmentX(Component.CENTER_ALIGNMENT);
		button3.addActionListener((e) -> {
			System.out.println("laden");
		});

		frame.setContentPane(Box.createVerticalBox());

		frame.getContentPane().add(Box.createVerticalStrut(50));
		frame.getContentPane().add(Box.createGlue());

		frame.getContentPane().add(label);

		frame.getContentPane().add(button1);
		frame.getContentPane().add(button2);
		frame.getContentPane().add(button3);

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

		label = new JLabel("Wie lang soll das Spielfeld sein?");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);
		
		label = new JLabel("(Zahlen zwischen 5 und 30 sind möglich)");
		label.setAlignmentX(Component.CENTER_ALIGNMENT);
		frame.getContentPane().add(label);
		
		panel = new JPanel();
		JTextField textfeld = new JTextField();
		textfeld.addActionListener((e) -> {
			try{Integer. parseInt(textfeld.getText());
			}catch(NumberFormatException ex){
				frame.dispose();
				new Spielgui(2);
			}
			int test = Integer.parseInt(textfeld.getText());
			if(test>=5 && test<=30) {
				fieldSize = test;
				shipAmount(fieldSize);
				frame.dispose();
				new Spielgui(6);
			}
			else {
				frame.dispose();
				new Spielgui(2);
			}
		});
		textfeld.setHorizontalAlignment(SwingConstants.CENTER);
		textfeld.setColumns(10);
		panel.add(textfeld);
		frame.getContentPane().add(panel);
		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

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
		
		panel = new JPanel();
		JTextField textfeld = new JTextField();
		textfeld.addActionListener((e) -> {
			try{Integer. parseInt(textfeld.getText());
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
			try{Integer. parseInt(textfeld.getText());
			}catch(NumberFormatException ex){
				frame.dispose();
				new Spielgui(4);
			}
			int test = Integer.parseInt(textfeld.getText());
			if(test>=5 && test<=30) {
				fieldSize = test;
				shipAmount(fieldSize);
				frame.dispose();
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

		panel = new JPanel();
		JTextField textfeld = new JTextField();
		textfeld.addActionListener((e) -> {
			try{Integer. parseInt(textfeld.getText());
			}catch(NumberFormatException ex){
				frame.dispose();
				new Spielgui(5);
			}
			port = Integer.parseInt(textfeld.getText());
		});

		frame.getContentPane().add(Box.createGlue());
		frame.getContentPane().add(Box.createVerticalStrut(50));

	}

	private void schiffeplatzieren() {

		JMenuBar menuBar = new JMenuBar();
		frame.setJMenuBar(menuBar);
		
		schiffe = new boolean[fieldSize][fieldSize];

		JButton beginnen = new JButton("Spiel beginnen");
		beginnen.addActionListener((e) -> {
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
		menuBar.add(beginnen);
		
		JPanel panel = new JPanel();

		panel.setLayout(new GridLayout(fieldSize, fieldSize, 1, 1));
		
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
				panel.add(field[i][j]);
			}
		}
		frame.getContentPane().add(panel);
		frame.pack();
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
				field[i][j].setName(i+" "+j);
				field[i][j].addActionListener((e) -> {
					String[] s = ((JButton)e.getSource()).getName().split(" ");
					int x = Integer.parseInt(s[0]);
					int y = Integer.parseInt(s[1]);
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

	private void placeShipRC(int i, int j){

		if(amount2x!=0 && checkCollisionRC(i,j,2)){
			schiffe[i][j] = true;
			schiffe[i][j+1] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i][j+1].setBackground(new Color(0,255,0));

			amount2x--;

		} else if (amount3x!=0 && checkCollisionRC(i,j,3)){
			schiffe[i][j] = true;
			schiffe[i][j+1] = true;
			schiffe[i][j+2] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i][j+1].setBackground(new Color(0,255,0));
			field[i][j+2].setBackground(new Color(0,255,0));

			amount3x--;

		} else if (amount4x!=0 && checkCollisionRC(i,j,4)){
			schiffe[i][j] = true;
			schiffe[i][j+1] = true;
			schiffe[i][j+2] = true;
			schiffe[i][j+3] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i][j+1].setBackground(new Color(0,255,0));
			field[i][j+2].setBackground(new Color(0,255,0));
			field[i][j+3].setBackground(new Color(0,255,0));

			amount4x--;

		} else if (amount5x!=0 && checkCollisionRC(i,j,5)){
			schiffe[i][j] = true;
			schiffe[i][j+1] = true;
			schiffe[i][j+2] = true;
			schiffe[i][j+3] = true;
			schiffe[i][j+4] = true;

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
			schiffe[i][j] = true;
			schiffe[i+1][j] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i+1][j].setBackground(new Color(0,255,0));

			amount2x--;

		} else if (amount3x!=0  && checkCollisionLC(i,j,3)){
			schiffe[i][j] = true;
			schiffe[i+1][j] = true;
			schiffe[i+2][j] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i+1][j].setBackground(new Color(0,255,0));
			field[i+2][j].setBackground(new Color(0,255,0));

			amount3x--;

		} else if (amount4x!=0 && checkCollisionLC(i,j,4)){
			schiffe[i][j] = true;
			schiffe[i+1][j] = true;
			schiffe[i+2][j] = true;
			schiffe[i+3][j] = true;

			field[i][j].setBackground(new Color(0,255,0));
			field[i+1][j].setBackground(new Color(0,255,0));
			field[i+2][j].setBackground(new Color(0,255,0));
			field[i+3][j].setBackground(new Color(0,255,0));

			amount4x--;

		} else if (amount5x!=0 && checkCollisionLC(i,j,5)){
			schiffe[i][j] = true;
			schiffe[i+1][j] = true;
			schiffe[i+2][j] = true;
			schiffe[i+3][j] = true;
			schiffe[i+4][j] = true;

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
				if (schiffe[i+m][j+n] == true){
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
				if (schiffe[i+n][j+m] == true){
					return false;
				}
			}
		}
		return true;
	}

	private void shipAmount(int x){
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
