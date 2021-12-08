public class AI {

    private static int x;
    private static int y;
    private static String direction;

    public static boolean start(String status) {
        Ship.calcAmount(GUI.enemyBoard.getSize());
        int timer = 0;
        if(status == "client"){
            while (Ship.getAmounts()[0]+Ship.getAmounts()[1]+Ship.getAmounts()[2]+Ship.getAmounts()[3] != 0){
            	timer++;
				if(timer>1500) {
					return false;
				}
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
            	timer++;
				if(timer>1500) {
					return false;
				}
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
        return true;
    }

    public static void shot() {
        //TODO: KI-Delay
        //Thread.sleep(var*1000);
        x = (int) (Math.random() * GUI.buttonsUser.length);
        y = (int) (Math.random() * GUI.buttonsUser.length);
        System.out.println("AI schießt auf Feld: " + x + "/" + y);
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()){
            GUI.userBoard.shot(x,y);
            if(GUI.userBoard.getFieldArray()[x][y].isHit()){
            	GUI.enemyHitCounter--;
                shot();
            } else {
                Controller.switchTurn();
            }
        } else {
            shot();
        }
    }
}
