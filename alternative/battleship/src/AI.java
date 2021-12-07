import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

public class AI {

    private static int x;
    private static int y;
    private static String direction;
    static Timer timer = new Timer();



    public static void start(String status) {
        Ship.calcAmount(GUI.enemyBoard.getSize());
        int temp = 0;

        if(status == "client"){
            //TODO: Endlosschleife abfangen, falls kein Platz mehr für Schiffe
            while (Ship.getAmounts()[0]+Ship.getAmounts()[1]+Ship.getAmounts()[2]+Ship.getAmounts()[3] != 0){
                x = (int) (Math.random() * GUI.buttonsEnemy.length);
                y = (int) (Math.random() * GUI.buttonsEnemy.length);
                if (Math.random() < 0.5){
                    direction = "horizontal";
                } else {
                    direction = "vertical";
                }
                GUI.enemyBoard.place(x, y, direction);
            }
        } else if (status == "server"){
            while (Ship.getAmounts()[0]+Ship.getAmounts()[1]+Ship.getAmounts()[2]+Ship.getAmounts()[3] != 0){
                x = (int) (Math.random() * GUI.buttonsUser.length);
                y = (int) (Math.random() * GUI.buttonsUser.length);
                if (Math.random() < 0.5){
                    direction = "horizontal";
                } else {
                    direction = "vertical";
                }
                GUI.userBoard.place(x, y, direction);
            }
        }
    }

    public static void shot() {
        int var = (int) (Math.random() * 3) + 2;
        //TODO: KI-Delay
        //Thread.sleep(var*1000);
        x = (int) (Math.random() * GUI.buttonsUser.length);
        y = (int) (Math.random() * GUI.buttonsUser.length);
        System.out.println("AI schießt auf Feld: " + x + "/" + y);
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()){
            GUI.userBoard.shot(x,y);
            if(GUI.userBoard.getFieldArray()[x][y].isHit()){
                shot();
            } else {
                Controller.switchTurn();
            }
        } else {
            shot();
        }
    }
}
