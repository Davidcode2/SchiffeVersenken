import javax.swing.*;
import java.awt.*;
import java.util.TimerTask;

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
            if (GUI.enemyBoard.getFieldArray()[x][y].isMiss()){
                switchTurn();
                handleShotSP(x,y);
            }
        }
    }

    static class inboundMessageLoop extends SwingWorker<Object, Object> {
        @Override
        protected Object doInBackground() throws Exception {
            new java.util.Timer().scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    String message = Connection.getMessage();
                    if (message.contains("answer")) {
                        int shipState = Integer.parseInt(message.split(" ")[1]);
                        if (shipState == 1 || shipState == 2) {
                            GUI.colorButtons("client", 0, 0, "Grey");
                        } else {
                            GUI.colorButtons("client", 0, 0, "DarkBlue");
                            Connection.sendMessage("pass");
                        }
                    }
                    if (message.contains("shot")) {
                        int x = Integer.parseInt(message.split(" ")[1]);
                        int y = Integer.parseInt(message.split(" ")[2]);
                        GUI.userBoard.shot(x, y);
                        if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                            if (GUI.userBoard.getFieldArray()[x][y].isSunk()) {
                                Connection.sendMessage(String.format("answer %s", 2));
                            } else {
                                Connection.sendMessage(String.format("answer %s", 1));
                            }
                        } else {
                            Connection.sendMessage(String.format("answer %s", 0));
                        }
                    }
                }
            }, 0, 1000);
            return null;
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
