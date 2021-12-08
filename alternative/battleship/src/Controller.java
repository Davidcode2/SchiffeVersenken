import javax.swing.*;
<<<<<<< HEAD
=======
import java.awt.*;
import java.util.TimerTask;
>>>>>>> refs/remotes/origin/main

public class Controller {

    public static boolean clientTurn;
    public static boolean serverTurn;

    public static void main(String[] args){

        SwingUtilities.invokeLater(() -> {
            new GUI(1);

        });
    }

    public static void startGame() {
        if (Math.random() < 0.5){
            System.out.println("client beginnt");
            clientTurn = true;
            serverTurn = false;
            AI.shot();
        } else {
            System.out.println("server beginnt");
            clientTurn = false;
            serverTurn = true;
        }
    }

    public static void handleShotSP(int x, int y){
        if (clientTurn){
            AI.shot();

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
}
