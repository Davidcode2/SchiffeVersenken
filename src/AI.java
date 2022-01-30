public class AI {

    private static int x;
    private static int y;
    private static int firstHitx;
    private static int firstHity;
    private static String direction;
    private static String flag;

	/**
	 * All ships are randomly placed on the respective board.
	 * @param status on which board the ships should be placed
	 * @return boolean whether the AI ​​could place the ships
	 */
    public static boolean start(String status) {
        flag = "random";
        Ship.calcAmount(GUI.userBoard.getSize());
        int timer = 0;
        if(status.equals("client")){
            while (Ship.getAmounts()[0]+Ship.getAmounts()[1]+Ship.getAmounts()[2]+Ship.getAmounts()[3] != 0){
            	timer++;
				if(timer>1000) {//falls die AI nicht alle Schiffe korrekt platzieren kann
					return false;
				}
                x = (int) (Math.random() * GUI.buttonsEnemy.length);
                y = (int) (Math.random() * GUI.buttonsEnemy.length);
                if (Math.random() < 0.5) {
                    direction = "horizontal";
                } else {
                    direction = "vertical";
                }
                GUI.enemyBoard.place(x, y, direction);
            }
        } else if (status.equals("server")){
            while (Ship.getAmounts()[0]+Ship.getAmounts()[1]+Ship.getAmounts()[2]+Ship.getAmounts()[3] != 0){
            	timer++;
				if(timer>1000) {//falls die AI nicht alle Schiffe korrekt platzieren kann
					return false;
				}
                x = (int) (Math.random() * GUI.buttonsUser.length);
                y = (int) (Math.random() * GUI.buttonsUser.length);
                if (Math.random() < 0.5) {
                    direction = "horizontal";
                } else {
                    direction = "vertical";
                }
                GUI.userBoard.place(x, y, direction);
            }
        }
        return true;
    }
    
    /**
     * Easy AI random shooting.
     */
    public static void shot() {
        x = (int) (Math.random() * GUI.buttonsUser.length);
        y = (int) (Math.random() * GUI.buttonsUser.length);
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()) {
            System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x, y);
            if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                GUI.enemyHitCounter--;
                if (GUI.enemyHitCounter == 0) {
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
    
    /**
     * Determine how the heavy AI should behave.
     */
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
    
    /**
     * Random AI shot.
     */
    private static void randomShot() {
        x = (int) (Math.random() * GUI.buttonsUser.length);
        y = (int) (Math.random() * GUI.buttonsUser.length);
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()) {
            System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x, y);
            if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                GUI.enemyHitCounter--;
                if (GUI.enemyHitCounter == 0) {
                    return;
                }
                firstHitx = x;
                firstHity = y;
                flag = "firstHit";
                shootAbove();
            } else {
                Controller.switchTurn();
            }
        } else {
            randomShot();
        }
    }
    
    /**
     * The next shot is chosen, based on the direction of the ship and the position of the hit.
     * 
     * @param position direction of the ship
     */
    private static void nextHit(String position) {
    	if(position.equals("vertical")) {
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
    
    /**
     * Shot on the field above the last hit.
     */
    private static void shootAbove() {
        if (x - 1 < 0) {
            x = firstHitx;
            shootBelow();
            return;
        }
        x -= 1;
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()) {
            System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x, y);
            if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                GUI.enemyHitCounter--;
                if (GUI.enemyHitCounter == 0) {
                    return;
                }
                colorWaterHorizontal(x, y);
                flag = "shipVertical";
                nextHit("vertical");
            } else {
                colorWaterEdges(x, y, "horizontal");
                x = firstHitx;
                Controller.switchTurn();
            }
        } else {
            x = firstHitx;
            shootBelow();
        }
    }
    
    /**
     * Shot on the field below the last hit.
     */
    private static void shootBelow() {
        if (x + 1 == GUI.userBoard.getSize()) {
            if (flag.equals("firstHit")) {
                shootLeft();
                return;
            } else {
                flag = "random";
                randomShot();
                return;
            }
        }
        x += 1;
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()) {
            System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x, y);
            if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                GUI.enemyHitCounter--;
                if (GUI.enemyHitCounter == 0) {
                    return;
                }
                colorWaterHorizontal(x, y);
                flag = "shipVertical";
                nextHit("vertical");
            } else {
                colorWaterEdges(x, y, "horizontal");
                x = firstHitx;
                Controller.switchTurn();
            }
        } else {
            if (flag.equals("firstHit")) {
                x = firstHitx;
                shootLeft();
            } else {
                flag = "random";
                randomShot();
            }
        }
    }
    
    /**
     * Shot on the field to the left of the last hit.
     */
    private static void shootLeft() {
        if (y - 1 < 0) {
            y = firstHity;
            shootRight();
            return;
        }
        y -= 1;
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()) {
            System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x, y);
            if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                GUI.enemyHitCounter--;
                if (GUI.enemyHitCounter == 0) {
                    return;
                }
                colorWaterVertical(x, y);
                flag = "shipHorizontal";
                nextHit("horizontal");
            } else {
                colorWaterEdges(x, y, "vertical");
                y = firstHity;
                Controller.switchTurn();
            }
        } else {
            y = firstHity;
            shootRight();
        }
    }
    
    /**
     * Shot on the field to the right of the last hit.
     */
    private static void shootRight() {
        if (y + 1 == GUI.userBoard.getSize()) {
            flag = "random";
            randomShot();
            return;
        }
        y += 1;
        if (!GUI.userBoard.getFieldArray()[x][y].isHit() && !GUI.userBoard.getFieldArray()[x][y].isMiss()) {
            System.out.println("AI schießt auf Feld: " + x + "/" + y);
            GUI.userBoard.shot(x, y);
            if (GUI.userBoard.getFieldArray()[x][y].isHit()) {
                GUI.enemyHitCounter--;
                if (GUI.enemyHitCounter == 0) {
                    return;
                }
                colorWaterVertical(x, y);
                flag = "shipHorizontal";
                nextHit("horizontal");
            } else {
                colorWaterEdges(x, y, "vertical");
                y = firstHity;
                Controller.switchTurn();
            }
        } else {
            flag = "random";
            randomShot();
        }
    }
    
    /**
     * AI recognizes the fields above and below the ship as water.
     * 
     * @param x coordinate of the hit
     * @param y coordinate of the hit
     */
    private static void colorWaterVertical(int x, int y) {
        if (x - 1 >= 0) {
            if (flag.equals("firstHit")) {
                int tempx = firstHitx - 1;
                System.out.println("AI erkennt Feld als Wasser: " + tempx + "/" + firstHity);
                GUI.userBoard.colorWater(tempx, firstHity);
            }
            GUI.userBoard.colorWater(--x, y);
            System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
            ++x;
        }
        if (x + 1 < GUI.userBoard.getSize()) {
            if (flag.equals("firstHit")) {
                int tempx = firstHitx + 1;
                System.out.println("AI erkennt Feld als Wasser: " + tempx + "/" + y);
                GUI.userBoard.colorWater(tempx, firstHity);
            }
            GUI.userBoard.colorWater(++x, y);
            System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
        }
    }
    
    /**
     * AI recognizes the fields to the right and left of the ship as water.
     * 
     * @param x coordinate of the hit
     * @param y coordinate of the hit
     */
    private static void colorWaterHorizontal(int x, int y) {
        if (y - 1 >= 0) {
            if (flag.equals("firstHit")) {
                int tempy = firstHity - 1;
                System.out.println("AI erkennt Feld als Wasser: " + firstHitx + "/" + tempy);
                GUI.userBoard.colorWater(firstHitx, tempy);
            }
            GUI.userBoard.colorWater(x, --y);
            System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
            ++y;
        }
        if (y + 1 < GUI.userBoard.getSize()) {
            if (flag.equals("firstHit")) {
                int tempy = firstHity + 1;
                System.out.println("AI erkennt Feld als Wasser: " + firstHitx + "/" + tempy);
                GUI.userBoard.colorWater(firstHitx, tempy);
            }
            GUI.userBoard.colorWater(x, ++y);
            System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
        }
    }
    
   /**
    * AI recognizes the edges around the ship as water.
    * 
    * @param x coordinate of the hit
    * @param y coordinate of the hit
    * @param position direction of the ship
    */
    private static void colorWaterEdges(int x, int y, String position) {
        switch (position) {
            case "horizontal":
                y -= 1;
                if (y >= 0) {
                    GUI.userBoard.colorWater(x, y);
                    System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
                }
                y += 2;
                if (y < GUI.userBoard.getSize()) {
                    GUI.userBoard.colorWater(x, y);
                    System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
                }
                break;
            case "vertical":
                x -= 1;
                if (x >= 0) {
                    GUI.userBoard.colorWater(x, y);
                    System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
                }
                x += 2;
                if (x < GUI.userBoard.getSize()) {
                    GUI.userBoard.colorWater(x, y);
                    System.out.println("AI erkennt Feld als Wasser: " + x + "/" + y);
                }
                break;
            default:
                System.out.println("Fehler bei colorWaterEdges in der AI Klasse.");
        }

    }
}