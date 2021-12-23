import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
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
    /*
    public static boolean checkWin() {
        int tempUser = 0;
        int tempEnemy = 0;
        for (int i=0; i<GUI.userBoard.getShipList().size();i++){
            if (GUI.userBoard.getShipList().get(i) != null){
                tempUser++;
            }
            if (GUI.enemyBoard.getShipList().get(i) != null){

                tempEnemy++;
            }
        }
        System.out.println(tempUser + "//" + tempEnemy);
        if (tempEnemy == 0 || tempUser == 0){
            return true;
        } else {
            return false;
        }
    }
    */
    public static Field[][][] readBoard(ArrayList<String> stringArrayL) {
        int boardCount = 1;
        int max = 0;
        // get size of Board
        for (int i = 0; i < stringArrayL.size(); i++) {
            String[] split = stringArrayL.get(i).split(" ");
            if (Integer.parseInt(split[1]) > max) {
                max = Integer.parseInt(split[1]);
            }
        }
        max += 1;
        if (stringArrayL.size() > max*max) {
            boardCount++;
        }
        Field[][][] arrFieldArray = new Field[boardCount][max][max];
        for (int n=0; n<boardCount;n++) {
            Field[] temp = new Field[(max * max)];
            int i = 0;
            int end = (stringArrayL.size() / 2) - 1;
            if (n == 1) {
                i = (stringArrayL.size() / 2);
                end = stringArrayL.size();
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
                temp[i] = tempField;
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
}
