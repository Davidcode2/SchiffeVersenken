import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.*;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Scanner;

public class GUI {

    private JFrame frame;
    public static Board userBoard;
    public static Board enemyBoard;
    public static JButton[][] buttonsUser;
    public static JButton[][] buttonsEnemy;
    private static int port;
    public static int hitCounter;
    public static int enemyHitCounter;
    public static boolean difficultAi;

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
            case 10:
                waitForServer();
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

        JButton ButtonSpielLaden = new JButton("Spiel laden");
        ButtonSpielLaden.setFocusable(false);
        ButtonSpielLaden.setAlignmentX(Component.CENTER_ALIGNMENT);
        ButtonSpielLaden.addActionListener((e) -> {
                    System.out.println("laden");
                    FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
                    final JFileChooser fc = new JFileChooser();
                    fc.setFileFilter(filter);
                    int returnVal = fc.showOpenDialog(frame);
                    if (returnVal != -1) {
                        ArrayList<String> fieldStringArray = new ArrayList<String>();
                        File savedSession = fc.getSelectedFile();
                        try {
                            Scanner scanner = new Scanner(savedSession);
                            while (scanner.hasNextLine()) {
                                fieldStringArray.add(scanner.nextLine());
                            }
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        Field[][][] myField = Controller.readBoard(fieldStringArray);
                        //TODO: new Board Constructor
                        System.out.println(myField.length);
                        System.out.println(myField[0].length);
                        System.out.println(myField[0][0].length);
                        userBoard = new Board(myField[0], myField[0][0].length, "server");
                        if (myField.length == 2) {
                            Connection.setMultiplayer(false);
                            enemyBoard = new Board(myField[1], myField[0][0].length, "client");
                        } else {
                            Connection.setMultiplayer(true);
                        }
                        frame.dispose();
                        new GUI(7);
                    }
        });

        frame.setContentPane(Box.createVerticalBox());

        frame.getContentPane().add(Box.createVerticalStrut(50));
        frame.getContentPane().add(Box.createGlue());

        frame.getContentPane().add(label);

        frame.getContentPane().add(ButtonSP);
        frame.getContentPane().add(ButtonMP);
        frame.getContentPane().add(ButtonSpielLaden);

        frame.getContentPane().add(Box.createGlue());
        frame.getContentPane().add(Box.createVerticalStrut(50));

    }

    private void loadSession(File savedSession) {
        if (/*savedSession*/true) {
            for (int i = 0; i < userBoard.getSize(); i++) {
                for (int j = 0; j < userBoard.getSize(); j++) {
//                    buttonsUser[i][j] = savedSession[i][j]
                }
            }
            userBoard.print();
        }
    }

    private void einzelspieler() {

        frame.setContentPane(Box.createVerticalBox());

        frame.getContentPane().add(Box.createVerticalStrut(50));
        frame.getContentPane().add(Box.createGlue());

        JLabel label = new JLabel("Schiffe versenken");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().add(label);

        frame.getContentPane().add(Box.createVerticalStrut(50));

        label = new JLabel("Welche Schwierigkeit soll der Gegner haben ?");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().add(label);

        JButton easy = new JButton("einfacher Gegner");
        JButton hard = new JButton("schwieriger Gegner");

        easy.setFocusable(false);
        easy.setEnabled(false);
        easy.setAlignmentX(Component.CENTER_ALIGNMENT);
        easy.addActionListener((e) -> {
            difficultAi = false;
            easy.setEnabled(false);
            hard.setEnabled(true);
        });

        hard.setFocusable(false);
        hard.setAlignmentX(Component.CENTER_ALIGNMENT);
        hard.addActionListener((e) -> {
            difficultAi = true;
            easy.setEnabled(true);
            hard.setEnabled(false);
        });

        frame.getContentPane().add(easy);
        frame.getContentPane().add(hard);

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
        start.setFocusable(false);
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

        JButton button2 = new JButton("Zurück");
        button2.setFocusable(false);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.addActionListener((e) -> {
            frame.dispose();
            new GUI(1);
        });

        panel.add(start);
        frame.getContentPane().add(panel);
        frame.getContentPane().add(button2);
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

        JButton buttonBack = new JButton("Zurück");
        buttonBack.setFocusable(false);
        buttonBack.setAlignmentX(Component.CENTER_ALIGNMENT);
        buttonBack.addActionListener((e) -> {
            frame.dispose();
            new GUI(1);
        });

        frame.setContentPane(Box.createVerticalBox());

        frame.getContentPane().add(Box.createVerticalStrut(50));
        frame.getContentPane().add(Box.createGlue());

        frame.getContentPane().add(label);

        frame.getContentPane().add(button1);

        frame.getContentPane().add(button2);

        frame.getContentPane().add(buttonBack);

        frame.getContentPane().add(Box.createGlue());
        frame.getContentPane().add(Box.createVerticalStrut(50));
    }

    private void host() {

        JLabel mainlabel = new JLabel("Schiffe versenken");
        mainlabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel portlabel = new JLabel("Auf welchem Port möchten sie spielen?");
        portlabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel portpanel = new JPanel();
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
        portpanel.add(textfeld);

        JLabel sizelabel = new JLabel("Wie lang soll das Spielfeld sein?");
        sizelabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel size1label = new JLabel("(Zahlen zwischen 5 und 30 sind möglich)");
        size1label.setAlignmentX(Component.CENTER_ALIGNMENT);

        JPanel sizepanel = new JPanel();
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
                Connection.setMultiplayer(true);
                Connection.setServer(true);
                new GUI(6);
            }
            else {
                frame.dispose();
                new GUI(2);
            }
        });
        textfeld2.setHorizontalAlignment(SwingConstants.CENTER);
        textfeld2.setColumns(10);
        sizepanel.add(textfeld2);

        JButton button2 = new JButton("Zurück");
        button2.setFocusable(false);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.addActionListener((e) -> {
            frame.dispose();
            new GUI(3);
        });

        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.75);
        frame.getContentPane().add(splitPane);

        JPanel panelRight = new JPanel();
        splitPane.setRightComponent(panelRight);
        panelRight.setLayout(new BoxLayout(panelRight, BoxLayout.Y_AXIS));

        String[] inetAdr = null;
        try {
            inetAdr = Server.ipAdresses();
        } catch (SocketException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < inetAdr.length; i++) {
            JLabel inetLabel = new JLabel(inetAdr[i]);
            inetLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            panelRight.add(inetLabel);
        }

        panelRight.add(Box.createGlue());
        panelRight.add(Box.createVerticalStrut(50));

        JPanel panelLeft = new JPanel();
        splitPane.setLeftComponent(panelLeft);
        panelLeft.setLayout(new BoxLayout(panelLeft, BoxLayout.Y_AXIS));
        panelLeft.add(mainlabel);
        panelLeft.add(portlabel);
        panelLeft.add(portpanel);
        panelLeft.add(sizelabel);
        panelLeft.add(size1label);
        panelLeft.add(sizepanel);

        frame.getContentPane().add(Box.createGlue());

        panelLeft.add(button2);

        panelLeft.add(Box.createVerticalStrut(50));

        // TODO:
        // add submit button
        // actions in textfields should only be executed when pressed
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
            try {
                String ip = promptIP.getText();
                userBoard = new Board(0, "server");
                ClientConnectionService ccService = new ClientConnectionService(userBoard, ip, port);
                ccService.execute();
                Connection.setMultiplayer(true);
                Connection.setServer(false);
                frame.dispose();
            } catch (NumberFormatException ex) {
                frame.dispose();
                new GUI(5);
            }
//                JOptionPane.showMessageDialog(null, "Server nicht verfügbar.");
            // TODO: create "Waiting for Server" Screen
            // continue waiting for server, if it isn't started
            // tricky
        });

        // TODO:
        // on submit

        JButton button2 = new JButton("Zurück");
        button2.setFocusable(false);
        button2.setAlignmentX(Component.CENTER_ALIGNMENT);
        button2.addActionListener((e) -> {
            frame.dispose();
            new GUI(3);
        });

        promptIP.setHorizontalAlignment(SwingConstants.CENTER);
        promptIP.setColumns(10);
        panel.add(promptIP);
        frame.getContentPane().add(panel);

        frame.getContentPane().add(button2);

        frame.getContentPane().add(Box.createGlue());
        frame.getContentPane().add(Box.createVerticalStrut(50));

    }
    private void waitForServer() {
        frame.setContentPane(Box.createVerticalBox());

        frame.getContentPane().add(Box.createVerticalStrut(50));
        frame.getContentPane().add(Box.createGlue());

        JLabel label = new JLabel("Warte auf Server");
        label.setAlignmentX(Component.CENTER_ALIGNMENT);
        frame.getContentPane().add(label);

        frame.getContentPane().add(Box.createVerticalStrut(50));

        frame.getContentPane().add(Box.createGlue());
        frame.getContentPane().add(Box.createVerticalStrut(50));
    }

    public void showAlert(String alert) {
        JOptionPane.showMessageDialog(null, "Server nicht verfügbar.");
    }

    private void schiffeplatzieren() {
        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JButton beginnen = new JButton("Spiel beginnen");
        beginnen.addActionListener((e) -> {

            // prüfe ob alle Schiffe gesetzt
            if (Ship.getAmounts()[0] + Ship.getAmounts()[1] + Ship.getAmounts()[2] + Ship.getAmounts()[3] == 0) {
                if (Connection.Multiplayer()) {
                    if (Connection.isServer()) {
                        try {
                            if (Connection.getMessage().equals("done") || Connection.getMessage().equals("ready")) {
                                // wenn bereit, sende 'ready'
                                Connection.sendMessage("ready");
                                frame.dispose();
                                new GUI(7);
                            } else {
                                JOptionPane.showMessageDialog(null, "Warte auf Mitspieler.");
                            }
                        } catch (NullPointerException er) {
                            JOptionPane.showMessageDialog(null, "Warte auf Mitspieler.");
                        }
                    } else if (Connection.isServer() == false) {
                        Connection.sendMessage("ready");
                        if (Ship.getAmounts()[0] + Ship.getAmounts()[1] + Ship.getAmounts()[2] + Ship.getAmounts()[3] == 0) {
                            frame.dispose();
                            new GUI(7);
                        }
                    }
                } else {
                    frame.dispose();
                    new GUI(7);
                }
            } else {
                JOptionPane.showMessageDialog(null, "Noch nicht alle Schiffe gesetzt.");
                if (Ship.getAmounts()[0] + Ship.getAmounts()[1] + Ship.getAmounts()[2] + Ship.getAmounts()[3] == 0) {
                    if (Connection.Multiplayer()) {
                        // wenn bereit, sende 'ready'
                        Connection.sendMessage("ready");
                    }
                    frame.dispose();
                    new GUI(7);
                }
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

        JButton backButton = new JButton("Hauptmenü");
        backButton.setFocusable(false);
        backButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        backButton.addActionListener((e) -> {
            frame.dispose();
            new GUI(1);
        });
        menuBar.add(backButton);

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

        JMenuBar menuBar = new JMenuBar();
        frame.setJMenuBar(menuBar);

        JButton speichern = new JButton("Speichern");
        speichern.addActionListener((e) -> {
            // TODO: move to Controller
            System.out.println("Speichern");
            FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setFileFilter(filter);
            fileChooser.setDialogTitle("Spiel speichern");

            int userSelection = fileChooser.showSaveDialog(frame);
            if (userSelection == JFileChooser.APPROVE_OPTION) {
                File fileToSave = fileChooser.getSelectedFile();
                System.out.print("save file as: " + fileToSave.getAbsolutePath());
                PrintWriter pwriter = null;
                try {
                    pwriter = new PrintWriter(new FileWriter(fileToSave));
                    for (int i=0; i<userBoard.getFieldArray().length; i++) {
                        for (int j = 0; j < userBoard.getFieldArray().length; j++) {
                            System.out.println(userBoard.getFieldArray()[i][j].toString());
                            pwriter.println(userBoard.getFieldArray()[i][j].toString());
                        }
                    }
                    if (!Connection.Multiplayer()) {
                        for (int i=0; i<enemyBoard.getFieldArray().length; i++) {
                            for (int j = 0; j < enemyBoard.getFieldArray().length; j++) {
                                System.out.println(enemyBoard.getFieldArray()[i][j].toString());
                                pwriter.println(enemyBoard.getFieldArray()[i][j].toString());
                            }
                        }
                    }
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    pwriter.close();
                }
            }
        });
        menuBar.add(speichern);


        frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.X_AXIS));
        JSplitPane splitPane = new JSplitPane();
        splitPane.setResizeWeight(0.5);
        frame.getContentPane().add(splitPane);

        buttonsUser = new JButton[userBoard.getSize()][userBoard.getSize()];
        if (!Connection.Multiplayer()) {
            buttonsEnemy = new JButton[enemyBoard.getSize()][enemyBoard.getSize()];

            JPanel panelleft = new JPanel(); //links ist das Gegnerfeld
            splitPane.setLeftComponent(panelleft);
            panelleft.setLayout(new GridLayout(userBoard.getSize(), userBoard.getSize(), 1, 1));
            for (int i = 0; i < enemyBoard.getSize(); i++) {
                for (int j = 0; j < enemyBoard.getSize(); j++) {
                    // TODO: remove after testing
                    System.out.println(enemyBoard.getFieldArray()[i][j].toString());
                    buttonsEnemy[i][j] = new JButton(""); //1 + j + i * enemyBoard.getSize() + "");
                    buttonsEnemy[i][j].setName(i + " " + j);
                    buttonsEnemy[i][j].setEnabled(true);
                    buttonsEnemy[i][j].addActionListener((e) -> {
                        String[] s = ((JButton) e.getSource()).getName().split(" ");
                        int x = Integer.parseInt(s[0]);
                        int y = Integer.parseInt(s[1]);
                        if (Connection.Multiplayer()) {
                            Connection.sendMessage(x, y);
                        } else {
                            Controller.handleShotSP(x, y);
                            buttonsEnemy[x][y].setEnabled(false);
                        }
                        if (hitCounter == 0) {
                            frame.dispose();
                            new GUI(8);
                            return;
                        }
                        if (enemyHitCounter == 0) {
                            frame.dispose();
                            new GUI(9);
                            return;
                        }
                    });
                    panelleft.add(buttonsEnemy[i][j]);
                }
            }
        }

        JPanel panelright = new JPanel(); //rechts ist unser Feld hier werden bisher nur die von uns platzierten schiffe angezeigt
        splitPane.setRightComponent(panelright);
        panelright.setLayout(new GridLayout(userBoard.getSize(), userBoard.getSize(), 1, 1));

        for (int i = 0; i < userBoard.getSize(); i++) {
            for(int j = 0; j < userBoard.getSize(); j++) {
                // TODO: remove after testing
                System.out.println(userBoard.getFieldArray()[i][j].toString());
                buttonsUser[i][j] = new JButton(""); //1+j+i*userBoard.getSize()+"");
                buttonsUser[i][j].setName(i+" "+j);
                buttonsUser[i][j].setEnabled(false);
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
