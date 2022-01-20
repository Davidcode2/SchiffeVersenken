import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Controller {

    public static boolean clientTurn;
    public static boolean serverTurn;

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {
            new GUI(1);

        });
    }

    public static void startGame() {
    	/*
        if (Math.random() < 0.5){
            System.out.println("client beginnt");
            clientTurn = true;
            serverTurn = false;
            AI.shot();
        } else {
        */
        System.out.println("server beginnt");
        clientTurn = false;
        serverTurn = true;
    }

    public static void handleShotSP(int x, int y){
        if (clientTurn){
            if(GUI.difficultAi) {
            	AI.shotHard();
            }else {
            	AI.shot();
            }
        } else if (serverTurn){
            GUI.enemyBoard.shot(x,y);
            if(GUI.enemyBoard.getFieldArray()[x][y].isHit()) {
            	GUI.hitCounter--;
            }
            if (GUI.enemyBoard.getFieldArray()[x][y].isMiss()){
                switchTurn();
                handleShotSP(x,y);
            }
        }
    }

    public static void switchTurn() {
        clientTurn = !clientTurn;
        serverTurn = !serverTurn;
    }
    
    public static ArrayList loadPrompt(Frame frame) {
        FileNameExtensionFilter filter = new FileNameExtensionFilter("text files", "txt");
        ArrayList<String> fieldStringArray = new ArrayList();
        final JFileChooser fc = new JFileChooser();
        fc.setFileFilter(filter);
        int returnVal = fc.showOpenDialog(frame);
        if (returnVal != -1) {
            File savedSession = fc.getSelectedFile();
            try {
                Scanner scanner = new Scanner(savedSession);
                while (scanner.hasNextLine()) {
                    fieldStringArray.add(scanner.nextLine());
                }
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        }
        return fieldStringArray;
    }
    
	public static Field[][][] readBoard(ArrayList<String> stringArrayL) {
        int boardCount = 1;
        int max = 0;
        // get size of Board
        for (int i = 1; i < stringArrayL.size(); i++) {
            String[] split = stringArrayL.get(i).split(" ");
            if (Integer.parseInt(split[1]) > max) {
                max = Integer.parseInt(split[1]);
            }
        }
        int size = stringArrayL.size() - 1;
        max += 1;
        if (size > max*max) {
            boardCount++;
        }
        Field[][][] arrFieldArray = new Field[boardCount][max][max];
        for (int n=0; n<boardCount;n++) {
            Field[] temp = new Field[(max * max)];
            int i = 1;
            int end = 0;
            if (boardCount == 2) {
                end = (size / 2)+1;
            } else {
                end = size+1;
            }
            if (n == 1) {
                i = (size / 2);
                end = size+1;
            }
            for (; i < end; i++) {
                String[] splitArr = stringArrayL.get(i).split(" ");
                int x = Integer.parseInt(splitArr[0]);
                int y = Integer.parseInt(splitArr[1]);
                boolean[] boolArr = new boolean[6];
                for (int j = 0; j < boolArr.length; j++) {
                    boolArr[j] = Boolean.parseBoolean(splitArr[j + 2]);
                }
                Field tempField = new Field(x, y, boolArr[0], boolArr[1], boolArr[2], boolArr[3], boolArr[4], boolArr[5]);
                System.out.println(tempField.toString());
                if (i >= max*max+1) {
                    temp[i-(max*max)-1] = tempField;
                } else {
                    temp[i-1] = tempField;
                }
            }
            Field[][] fieldArray = new Field[max][max];
            boolean breakFlag = false;
            int counter = 0;
            for (int s=0; s<max; s++) {
                for (int j=0; j<max; j++) {
                    if (counter==max*max) {
                        breakFlag = true;
                        break;
                    }
                    fieldArray[s][j] = temp[counter];
                    counter++;
                }
            }
            if (breakFlag == true) {
                break;
            }
            arrFieldArray[n] = fieldArray;
        }
        return arrFieldArray;
    }

    public static void saveSession(JFrame frame, Board userBoard, Board enemyBoard) {

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
                Date date = new Date();
                long saveId = date.getTime();
                String saveIdStr = String.valueOf(saveId);
                pwriter.println(saveIdStr);
                for (int i=0; i<userBoard.getFieldArray().length; i++) {
                    for (int j = 0; j < userBoard.getFieldArray().length; j++) {
                        System.out.println(userBoard.getFieldArray()[i][j].toString());
                        pwriter.println(userBoard.getFieldArray()[i][j].toString());
                    }
                }
//                if (!Connection.Multiplayer()) {
                    for (int i=0; i<enemyBoard.getFieldArray().length; i++) {
                        for (int j = 0; j < enemyBoard.getFieldArray().length; j++) {
                            System.out.println(enemyBoard.getFieldArray()[i][j].toString());
                            pwriter.println(enemyBoard.getFieldArray()[i][j].toString());
                        }
                    }
//                } else {
                if (Connection.Multiplayer()) {
                    Connection.sendMessage(String.format("save %s", saveIdStr));
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                pwriter.close();
            }
        }
    }
    
    public static void saveSession(Board userBoard, Board enemyBoard, long id) throws IOException {

        System.out.println("Speichern");
        File fileToSave = new File(System.getProperty("user.home"), Long.toString(id) + ".txt");

        System.out.print("save file as: " + fileToSave.getAbsolutePath());
            PrintWriter pwriter = null;
            try {
                pwriter = new PrintWriter(new FileWriter(fileToSave));
                pwriter.println(id);
                for (int i = 0; i < userBoard.getFieldArray().length; i++) {
                    for (int j = 0; j < userBoard.getFieldArray().length; j++) {
                        System.out.println(userBoard.getFieldArray()[i][j].toString());
                        pwriter.println(userBoard.getFieldArray()[i][j].toString());
                    }
                }
//                if (!Connection.Multiplayer()) {
                    for (int i = 0; i < enemyBoard.getFieldArray().length; i++) {
                        for (int j = 0; j < enemyBoard.getFieldArray().length; j++) {
                            System.out.println(enemyBoard.getFieldArray()[i][j].toString());
                            pwriter.println(enemyBoard.getFieldArray()[i][j].toString());
                        }
                    }
//                }
            } catch (IOException ex) {
                ex.printStackTrace();
            } finally {
                pwriter.close();
            }
        }
}