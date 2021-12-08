import javax.swing.*;

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

    public static void handleShotMP(int x, int y){
        Connection.sendMessage(x,y);
        String message = Connection.getMessage();
        if (message.contains("answer")) {
            int shipState = Integer.parseInt(message.split(" ")[1]);
            if (shipState == 1 || shipState == 2) {
                GUI.colorButtons("server", x,y,"grey");
            }
            else {
                GUI.colorButtons("server", x,y,"#3250FF");
            }
        }
//        if (Connection.getTurn){
//            Connection.getMessage();
//
//        } else if (serverTurn){
//            GUI.enemyBoard.shot(x,y);
//            if (GUI.enemyBoard.getFieldArray()[x][y].isMiss()){
//                switchTurn();
//                handleShotMP(x,y);
//            }
//        }
    }

    public static void inboundShotMP() {
        String message = Connection.getMessage();
        if (message.contains("shot")) {
            int x = Integer.parseInt(message.split(" ")[1]);
            int y = Integer.parseInt(message.split(" ")[2]);
            GUI.userBoard.shot(x,y);
            if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                if (GUI.userBoard.getFieldArray()[x][y].isSunk()) {
                    Connection.sendMessage(String.format("answer %s", 2));
                } else {
                    Connection.sendMessage(String.format("answer %s", 1));
                }
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
