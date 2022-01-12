public class AI {

    private static int x;
    private static int y;
    private static int firstHitx;
    private static int firstHity;
    private static String direction;
    private static String flag;

    public static boolean start(String status) {
    	flag="random";
        Ship.calcAmount(GUI.enemyBoard.getSize());
        int timer = 0;
        if(status == "client"){
            while (Ship.getAmounts()[0]+Ship.getAmounts()[1]+Ship.getAmounts()[2]+Ship.getAmounts()[3] != 0){
            	timer++;
				if(timer>1000) {
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
				if(timer>1000) {
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
        x = (int) (Math.random() * GUI.buttonsUser.length);
        y = (int) (Math.random() * GUI.buttonsUser.length);
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()){
            System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x,y);
            if(GUI.userBoard.getFieldArray()[x][y].isHit()){
            	GUI.enemyHitCounter--;
            	if(GUI.enemyHitCounter==0) {
            		return;
            	}
                shot();
            } else {
                Controller.switchTurn();
            }
        } else {
            shot();
        }
    }
    
    public static void shotHard() {
    	switch (flag) {
    		case "random":
    			randomShot();
    			break;
    		case "firstHit":
    			shootAbove();
    			break;
    		case "shipVertical":
    			nextHit("vertical");
    			break;
    		case "shipHorizontal":
    			nextHit("horizontal");
    			break;
    		default:
    			System.out.println("Fehler in shotHard");
    	}
    }
    
    private static void randomShot() {
    	x = (int) (Math.random() * GUI.buttonsUser.length);
        y = (int) (Math.random() * GUI.buttonsUser.length);
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()){
        	System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x,y);
            if(GUI.userBoard.getFieldArray()[x][y].isHit()){
            	GUI.enemyHitCounter--;
            	if(GUI.enemyHitCounter==0) {
            		return;
            	}
            	firstHitx=x;
            	firstHity=y;
            	flag="firstHit";
                shootAbove();
            } else {
                Controller.switchTurn();
            }
        } else {
            randomShot();
        }
    }
    
    private static void nextHit(String ship) {
    	if(ship.equals("vertical")) {
    		if(firstHitx<x) {
    			shootBelow();
    		}
    		else {
    			shootAbove();
    		}
    	}
    	else {
    		if(firstHity<y) {
    			shootRight();
    		}
    		else {
    			shootLeft();
    		}
    	}
    }
    
    private static void shootAbove() {
    	if(x-1<0) {
    		x=firstHitx;
    		shootBelow();
    		return;
    	}
    	x-=1;
    	if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()){
    		System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x,y);
            if(GUI.userBoard.getFieldArray()[x][y].isHit()){
            	GUI.enemyHitCounter--;
            	if(GUI.enemyHitCounter==0) {
            		return;
            	}
            	colorWaterHorizontal(x,y);
            	flag="shipVertical";
                nextHit("vertical");
            } else {
            	x=firstHitx;
                Controller.switchTurn();
            }
        } else {
        	x=firstHitx;
            shootBelow();
    	}
    }
    private static void shootBelow() {
    	if(x+1==GUI.enemyBoard.getSize()) {
    		if(flag.equals("firstHit")) {
    			shootLeft();
    			return;
    		}
    		else {
    			flag="random";
            	randomShot();
            	return;
    		}
    	}
    	x+=1;
    	if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()){
    		System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x,y);
            if(GUI.userBoard.getFieldArray()[x][y].isHit()){
            	GUI.enemyHitCounter--;
            	if(GUI.enemyHitCounter==0) {
            		return;
            	}
            	colorWaterHorizontal(x,y);
            	flag="shipVertical";
                nextHit("vertical");
            } else {
            	x=firstHitx;
                Controller.switchTurn();
            }
        } else {
        	if(flag.equals("firstHit")) {
        		x=firstHitx;
    			shootLeft();
    		}
    		else {
    			flag="random";
            	randomShot();
    		}
    	}
    }
    private static void shootLeft() {
    	if(y-1<0) {
    		y=firstHity;
    		shootRight();
    		return;
    	}
    	y-=1;
    	if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()){
    		System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x,y);
            if(GUI.userBoard.getFieldArray()[x][y].isHit()){
            	GUI.enemyHitCounter--;
            	if(GUI.enemyHitCounter==0) {
            		return;
            	}
            	colorWaterVertical(x,y);
            	flag="shipHorizontal";
                nextHit("horizontal");
            } else {
            	y=firstHity;
                Controller.switchTurn();
            }
        } else {
        	y=firstHity;
            shootRight();
    	}
    }
    private static void shootRight() {
    	if(y+1==GUI.enemyBoard.getSize()) {
    		flag="random";
    		randomShot();
    		return;
    	}
    	y+=1;
    	if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()){
    		System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x,y);
            if(GUI.userBoard.getFieldArray()[x][y].isHit()){
            	GUI.enemyHitCounter--;
            	if(GUI.enemyHitCounter==0) {
            		return;
            	}
            	colorWaterVertical(x,y);
            	flag="shipHorizontal";
                nextHit("horizontal");
            } else {
            	y=firstHity;
                Controller.switchTurn();
            }
        } else {
        	flag="random";
        	randomShot();
    	}
    }
    private static void colorWaterVertical(int x, int y) {
    	if(x-1>=0) {
    		if(flag.equals("firstHit")) {
    			int tempx = firstHitx-1;
    			System.out.println("AI erkennt Feld als Wasser: " + tempx + "/" + y);
    			GUI.userBoard.shot(tempx,firstHity);
    		}
    		System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
    		GUI.userBoard.shot(--x,y);
    		++x;
    	}
    	if(x+1<GUI.enemyBoard.getSize()) {
    		if(flag.equals("firstHit")) {
    			int tempx = firstHitx+1;
    			System.out.println("AI erkennt Feld als Wasser: " + tempx + "/" + y);
    			GUI.userBoard.shot(tempx,firstHity);
    		}
    		System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
    		GUI.userBoard.shot(++x,y);
    	}
    }
    private static void colorWaterHorizontal(int x, int y) {
    	if(y-1>=0) {
    		if(flag.equals("firstHit")) {
    			int tempy = firstHity-1;
    			System.out.println("AI erkennt Feld als Wasser: " + x + "/" + tempy);
    			GUI.userBoard.shot(firstHitx,tempy);
    		}
    		System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
    		GUI.userBoard.shot(x,--y);
    		++y;
    	}
    	if(y+1<GUI.enemyBoard.getSize()) {
    		if(flag.equals("firstHit")) {
    			int tempy = firstHity+1;
    			System.out.println("AI erkennt Feld als Wasser: " + x + "/" + tempy);
    			GUI.userBoard.shot(firstHitx,tempy);
    		}
    		System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
    		GUI.userBoard.shot(x,++y);
    	}
    }
}
