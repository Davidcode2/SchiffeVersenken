import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class GUI {

    private JFrame frame;
    private static Board userBoard;
    private static Board enemyBoard;
    public static JButton[][] buttonsUser;
    private static JButton[][] buttonsEnemy;

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

        JLabel label = new JLabel("Schiffe versenken");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);

        frame.setContentPane(Box.createVerticalBox());

        frame.getContentPane().add(Box.createVerticalStrut(50));
        frame.getContentPane().add(Box.createGlue());

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
                userBoard = new Board(boardSize);
                enemyBoard = new Board(boardSize);
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
            try{Integer.parseInt(textfeld.getText());
            }catch(NumberFormatException ex){
                frame.dispose();
                new GUI(4);
            }
            //port = Integer.parseInt(textfeld.getText());
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
                new GUI(4);
            }
            int test = Integer.parseInt(textfeld2.getText());
            if(test>=5 && test<=30) {
                //fieldSize = test;
                //shipAmount(fieldSize);
                //network.Connection.setServer(true);
                //System.out.println(String.format("setServer is: %s", Connection.isServer()));
                //network.Server server = new Server();
                frame.dispose();

                // Worker thread waiting for connection in background

                /*
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

                 */
                new GUI(6);
            }
            else {
                frame.dispose();
                new GUI(4);
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
                //port = Integer.parseInt(textfeld.getText());
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
                //ip = promptIP.getText();
                frame.dispose();
            }catch(NumberFormatException ex){
                frame.dispose();
                new GUI(5);
            }
            //Connection.setServer(false);
            //network.Client client = new network.Client();
            //System.out.println(String.format("connection data %s %s", ip, port));

            /*
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

             */
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

        //schiffe = new boolean[fieldSize][fieldSize];
        //enemyschiffe = new boolean[fieldSize][fieldSize];

        JButton beginnen = new JButton("Spiel beginnen");
        beginnen.addActionListener((e) -> {

            //Hier soll wenn wir client sind ein "ready" an den host schicken
            //Connection.sendMessage("ready"); habs mal auskommentiert weil es im einzelspieler ne exception schmeißt
            frame.dispose();
            new GUI(7);

        });

        menuBar.add(beginnen);

        JButton restart = new JButton("Schiffe neu setzen");
        restart.addActionListener((e) -> {
            Ship.calcAmount(userBoard.getSize());
            frame.dispose();
            new GUI(6);

        });
        menuBar.add(restart);

        JPanel panel = new JPanel();
        System.out.println(userBoard.getSize());
        panel.setLayout(new GridLayout(userBoard.getSize(), userBoard.getSize(), 1, 1));

        buttonsUser = new JButton[userBoard.getSize()][userBoard.getSize()];

        for (int i = 0; i < userBoard.getSize(); i++) {
            for(int j = 0; j < userBoard.getSize(); j++) {
                
                buttonsUser[i][j] = new JButton(1+j+i*userBoard.getSize()+"");
                buttonsUser[i][j].setName(i+" "+j);
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
                    }
                });
                panel.add(buttonsUser[i][j]);
            }
        }
        frame.getContentPane().add(panel);
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

        buttonsUser = new JButton[userBoard.getSize()][userBoard.getSize()];
        buttonsEnemy = new JButton[enemyBoard.getSize()][enemyBoard.getSize()];

        JPanel panelleft = new JPanel(); //links ist das Gegnerfeld
        splitPane.setLeftComponent(panelleft);
        panelleft.setLayout(new GridLayout(userBoard.getSize(), userBoard.getSize(), 1, 1));
        for (int i = 0; i < enemyBoard.getSize(); i++) {
            for(int j = 0; j < enemyBoard.getSize(); j++) {
                buttonsEnemy[i][j] = new JButton(1+j+i* enemyBoard.getSize()+"");
                buttonsEnemy[i][j].setName(i+" "+j);
                buttonsEnemy[i][j].addActionListener((e) -> {

                });
                panelleft.add(buttonsEnemy[i][j]);
            }
        }

        JPanel panelright = new JPanel(); //rechts ist unser Feld hier werden bisher nur die von uns platzierten schiffe angezeigt
        splitPane.setRightComponent(panelright);
        panelright.setLayout(new GridLayout(userBoard.getSize(), userBoard.getSize(), 1, 1));

        for (int i = 0; i < userBoard.getSize(); i++) {
            for(int j = 0; j < userBoard.getSize(); j++) {
                buttonsUser[i][j] = new JButton(1+j+i*userBoard.getSize()+"");
                buttonsUser[i][j].setName(i+" "+j);
                //userBoard.print();

                /*
                if(schiffe[i][j] == false){
                    buttonsUser[i][j].setBackground(new Color(0,255,0));
                } else {
                    buttonsUser[i][j].setBackground(new Color(255,0,0));
                }

                 */
                panelright.add(buttonsUser[i][j]);


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

    public static void colorButtonsUser(int x, int y, String color) {
        if (color == "Green") {
            buttonsUser[x][y].setBackground(new Color(0, 255, 0));
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
