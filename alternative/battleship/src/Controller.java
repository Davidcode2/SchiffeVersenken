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
    public static Field[][] loadSession(ArrayList<String> stringArrayL) {
        System.out.println("laden");
            int max = 0;
            for (int i = 0; i < stringArrayL.size(); i++) {
                String[] split = stringArrayL.get(i).split(" ");
                if (Integer.parseInt(split[1]) > max) {
                    max = Integer.parseInt(split[1]);
                }
            }
            max += 1;
            Field[] temp = new Field[(max * max)];
            for (int i = 0; i < stringArrayL.size() / 2; i++) {
                String[] splitArr = stringArrayL.get(i).split(" ");
                int x = Integer.parseInt(splitArr[0]);
                int y = Integer.parseInt(splitArr[1]);
                boolean[] boolArr = new boolean[6];
                for (int j = 0; j < 6; j++) {
                    boolArr[j] = Boolean.parseBoolean(splitArr[j + 2]);
                }
                Field tempField = new Field(x, y, boolArr[0], boolArr[1], boolArr[2], boolArr[3], boolArr[4], boolArr[5]);
                System.out.println(tempField.toString());
                temp[i] = tempField;
            }
            Field[][] fieldArray = new Field[max][max];
            boolean breakFlag = false;
            int counter = 0;
            for (int i=0; i<temp.length; i++) {
                for (int j=0; j<temp.length/max; j++) {
                    if (i==max || counter==max*max) {
                        breakFlag = true;
                        break;
                    }
                    fieldArray[i][j] = temp[counter];
                    counter++;
                    if (i==max-1) {
                        if (j == max-1) {
                            breakFlag = true;
                            break;
                        }
                        i = 0;
                    }
                }
                if (breakFlag == true) {
                    break;
                }
            }
        return fieldArray;
        }
}
