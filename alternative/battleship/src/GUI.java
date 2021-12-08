import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {

    private JFrame frame;
    public static Board userBoard;
    public static Board enemyBoard;
    public static JButton[][] buttonsUser;
    public static JButton[][] buttonsEnemy;
    private static int port;
    public static int hitCounter;
    public static int enemyHitCounter;

    public GUI(int window){

        frame = new JFrame("Spiel");
        frame.setSize(1440, 810);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        switch (window) {
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

        JButton ButtonSP = new JButton("Einzelspieler");
        ButtonSP.setFocusable(false);
        ButtonSP.setAlignmentX(Component.CENTER_ALIGNMENT);
        ButtonSP.addActionListener((e) -> {
            frame.dispose();
            new GUI(2);
        });

        JButton ButtonMP = new JButton("Mehrspieler");
        ButtonMP.setFocusable(false);
        ButtonMP.setAlignmentX(Component.CENTER_ALIGNMENT);
        ButtonMP.addActionListener((e) -> {
            frame.dispose();
            new GUI(3);
        });
		/*
		JButton ButtonSpielLaden = new JButton("Spiel laden");
		ButtonSpielLaden.setFocusable(false);
		ButtonSpielLaden.setAlignmentX(Component.CENTER_ALIGNMENT);
		ButtonSpielLaden.addActionListener((e) -> {
			System.out.println("laden");
		});
		*/
        frame.setContentPane(Box.createVerticalBox());

        frame.getContentPane().add(Box.createVerticalStrut(50));
        frame.getContentPane().add(Box.createGlue());

        frame.getContentPane().add(label);

        frame.getContentPane().add(ButtonSP);
        frame.getContentPane().add(ButtonMP);
        //frame.getContentPane().add(ButtonSpielLaden);

        frame.getContentPane().add(Box.createGlue());
        frame.getContentPane().add(Box.createVerticalStrut(50));

    }

    private void einzelspieler() {

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
        JTextField textfield = new JTextField();
        textfield.setHorizontalAlignment(SwingConstants.CENTER);
        textfield.setColumns(10);
        panel.add(textfield);

        JButton start = new JButton("Weiter");
        start.addActionListener((e) -> {
            try{Integer. parseInt(textfield.getText());
            }catch(NumberFormatException ex){
                frame.dispose();
                new GUI(2);
            }
            int boardSize = Integer.parseInt(textfield.getText());
            if(boardSize>=5 && boardSize<=30) {
                userBoard = new Board(boardSize, "server");
                enemyBoard = new Board(boardSize, "client");
                Ship.calcAmount(userBoard.getSize());
                frame.dispose();
                new GUI(6);
            }
            else {
                frame.dispose();
                new GUI(2);
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
            new GUI(4);
        });

        JButton button2 = new JButton("Client");
        button2.setFocusable(false);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.addActionListener((e) -> {
            frame.dispose();
            new GUI(5);
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
            try{
                port = Integer.parseInt(textfeld.getText());
            } catch(NumberFormatException ex) {
                frame.dispose();
                new GUI(4);
            }
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
            try {
                Integer.parseInt(textfeld2.getText());
            }catch(NumberFormatException ex){
                frame.dispose();
                new GUI(4);
            }
            int boardSize = Integer.parseInt(textfeld2.getText());
            if(boardSize>=5 && boardSize<=30) {
                userBoard = new Board(boardSize, "server");
                int fieldsize = userBoard.getSize();
                Ship.calcAmount(fieldsize);
                frame.dispose();
                (new ServerConnectionService(fieldsize, port)).execute();
                new GUI(6);
            }
            else {
                frame.dispose();
                new GUI(2);
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
                new GUI(5);
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
                String ip = promptIP.getText();
                userBoard = new Board(0, "server");
                (new ClientConnectionService(userBoard, ip, port)).execute();
                frame.dispose();
            }catch(NumberFormatException ex){
                frame.dispose();
                new GUI(5);
            }
//            if (Connection.getIn() == null) {
                // TODO: create "Waiting for Server" Screen
                // TODO: continue waiting for server, if it isn't started

//            }
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
    	JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JButton beginnen = new JButton("Spiel beginnen");
        beginnen.addActionListener((e) -> {

            if (Connection.Multiplayer()) {
                // wenn bereit, sende 'ready'
                Connection.sendMessage("ready");
                // TODO: warten bis Spielpartner bereit
            }

            if (Ship.getAmounts()[0] + Ship.getAmounts()[1] + Ship.getAmounts()[2]+ Ship.getAmounts()[3] == 0){
                frame.dispose();
                new GUI(7);
            }


        });

        menuBar.add(beginnen);

        JButton restart = new JButton("Schiffe neu setzen");
        restart.addActionListener((e) -> {
        	userBoard = new Board(userBoard.getSize(), "server");
            Ship.calcAmount(userBoard.getSize());
            frame.dispose();
            new GUI(6);

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
		
		JLabel ships5x = new JLabel("	"+Ship.getAmounts()[3]+" 5er Schiffe");
		ships5x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships5x);
		
		JLabel ships4x = new JLabel("	"+Ship.getAmounts()[2]+" 4er Schiffe");
		ships4x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships4x);
		
		JLabel ships3x = new JLabel("	"+Ship.getAmounts()[1]+" 3er Schiffe");
		ships3x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships3x);
		
		JLabel ships2x = new JLabel("	"+Ship.getAmounts()[0]+" 2er Schiffe");
		ships2x.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelRight.add(ships2x);
		
		panelRight.add(Box.createGlue());
		panelRight.add(Box.createVerticalStrut(50));

		JPanel panelLeft = new JPanel();
		splitPane.setLeftComponent(panelLeft);
        panelLeft.setLayout(new GridLayout(userBoard.getSize(), userBoard.getSize(), 1, 1));

        buttonsUser = new JButton[userBoard.getSize()][userBoard.getSize()];

        for (int i = 0; i < userBoard.getSize(); i++) {
            for(int j = 0; j < userBoard.getSize(); j++) {

                buttonsUser[i][j] = new JButton("");
                buttonsUser[i][j].setName(i+" "+j);
                colorButtons("server",i,j,"Blue");
                buttonsUser[i][j].addMouseListener(new MouseAdapter(){
                    public void mouseClicked(MouseEvent event){
                        String[] s = ((JButton)event.getSource()).getName().split(" ");
                        int x = Integer.parseInt(s[0]);
                        int y = Integer.parseInt(s[1]);

                        if(SwingUtilities.isRightMouseButton(event)){
                            userBoard.place(x,y,"horizontal");
                        }
                        else {
                            userBoard.place(x,y,"vertical");
                        }
                        ships5x.setText("	"+Ship.getAmounts()[3]+" 5er Schiffe");
			        	ships4x.setText("	"+Ship.getAmounts()[2]+" 4er Schiffe");
			        	ships3x.setText("	"+Ship.getAmounts()[1]+" 3er Schiffe");
			        	ships2x.setText("	"+Ship.getAmounts()[0]+" 2er Schiffe");
                    }
                });
                panelLeft.add(buttonsUser[i][j]);
            }
        }
    }

    private void spiel() {
    	frame.setMinimumSize(new Dimension(1920/2, 1080/2));
        if (Connection.Multiplayer()) {
            enemyBoard = new Board(userBoard.getSize(), "client");
            (new Connection.inboundMessageLoop()).execute();
        }
        
        Ship.calcAmount(userBoard.getSize());
        hitCounter = 5*Ship.getAmounts()[3]+4*Ship.getAmounts()[2]+3*Ship.getAmounts()[1]+2*Ship.getAmounts()[0];
        enemyHitCounter=hitCounter;
        
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

        buttonsUser = new JButton[userBoard.getSize()][userBoard.getSize()];
        buttonsEnemy = new JButton[enemyBoard.getSize()][enemyBoard.getSize()];

        JPanel panelleft = new JPanel(); //links ist das Gegnerfeld
        splitPane.setLeftComponent(panelleft);
        panelleft.setLayout(new GridLayout(userBoard.getSize(), userBoard.getSize(), 1, 1));
        for (int i = 0; i < enemyBoard.getSize(); i++) {
            for (int j = 0; j < enemyBoard.getSize(); j++) {
                buttonsEnemy[i][j] = new JButton(""); //1 + j + i * enemyBoard.getSize() + "");
                buttonsEnemy[i][j].setName(i + " " + j);
                buttonsEnemy[i][j].addActionListener((e) -> {
                    String[] s = ((JButton)e.getSource()).getName().split(" ");
                    int x = Integer.parseInt(s[0]);
                    int y = Integer.parseInt(s[1]);
                    if (Connection.Multiplayer()) {
                        Connection.sendMessage(x,y);
                    } else {
                        Controller.handleShotSP(x, y);
                    }
                    if(hitCounter==0){
                        frame.dispose();
                        new GUI(8);
                        return;
                    }
                    if(enemyHitCounter==0){
                    	frame.dispose();
                        new GUI(9);
                        return;
                    }
                });
                panelleft.add(buttonsEnemy[i][j]);
            }
        }


        JPanel panelright = new JPanel(); //rechts ist unser Feld hier werden bisher nur die von uns platzierten schiffe angezeigt
        splitPane.setRightComponent(panelright);
        panelright.setLayout(new GridLayout(userBoard.getSize(), userBoard.getSize(), 1, 1));

        for (int i = 0; i < userBoard.getSize(); i++) {
            for(int j = 0; j < userBoard.getSize(); j++) {
                buttonsUser[i][j] = new JButton(""); //1+j+i*userBoard.getSize()+"");
                buttonsUser[i][j].setName(i+" "+j);
                panelright.add(buttonsUser[i][j]);
            }
        }
        if (!Connection.Multiplayer()) {
            Controller.startGame();
            boolean wert = false;
            while(!wert) {
            	enemyBoard = new Board(userBoard.getSize(), "client");
            	wert = AI.start("client");
            }
        }
        userBoard.print();
        enemyBoard.print();
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

    public static void colorButtons(String status, int x, int y, String color) {
        if (status == "server"){
            if (color == "Green") {
                buttonsUser[x][y].setBackground(new Color(102, 255, 102));
            } else if (color == "Blue") {
                buttonsUser[x][y].setBackground(new Color(102, 178, 255));
            } else if (color == "DarkBlue") {
                buttonsUser[x][y].setBackground(new Color(50, 95, 255));
            } else if (color == "Red") {
                buttonsUser[x][y].setBackground(new Color(255, 102, 102));
            } else if (color == "Grey") {
                buttonsUser[x][y].setBackground(new Color(100, 100, 100));
            } else if (color == "LightGrey") {
                buttonsUser[x][y].setBackground(new Color(192, 192, 192));
            }
        } else if (status == "client"){
            if (color == "Green") {
                buttonsEnemy[x][y].setBackground(new Color(102, 255, 102));
            } else if (color == "Blue") {
                buttonsEnemy[x][y].setBackground(new Color(102, 178, 255));
            } else if (color == "DarkBlue") {
                buttonsUser[x][y].setBackground(new Color(50, 95, 255));
            } else if (color == "Red") {
                buttonsEnemy[x][y].setBackground(new Color(255, 102, 102));
            } else if (color == "Grey") {
                buttonsEnemy[x][y].setBackground(new Color(100, 100, 100));
            } else if (color == "LightGrey") {
                buttonsEnemy[x][y].setBackground(new Color(192, 192, 192));
            }
        }

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

}
