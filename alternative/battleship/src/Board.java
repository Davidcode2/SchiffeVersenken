import java.util.ArrayList;
import javax.swing.JOptionPane;

public class Board {
    private Field[][] fieldArray;
    private int size;
    private String status;
    private ArrayList<Ship> shipList = new ArrayList<Ship>();
    private static boolean playersTurn=true;
    private static boolean enemyBoard=false;

    public Board(int size, String status) {
        this.size = size;
        this.status = status;
        fieldArray = new Field[size][size];
        for (int i = 0; i<size ; i++){
            for (int j = 0; j<size; j++){
                fieldArray[i][j] = new Field(i,j);
            }
        }
    }
    // overloaded constructor for loading session
    public Board(Field[][] fieldArray, int size, String status) {
        this.fieldArray = fieldArray;
        this.size = size;
        this.status = status;
    }

    public void place(int x, int y, String direction) {
        if (Ship.getAmounts()[3]!=0){
            if (checkCollision(x,y,5, direction)){
                Ship ship5x = new Ship(5, new int[]{x,y} , direction);
                shipList.add(ship5x);
                Ship.lowerAmounts(3);
                setOccupied(x,y,ship5x);
                setShip(x,y,ship5x);
                print();
            }

        } else if (Ship.getAmounts()[2]!=0){
            if (checkCollision(x,y,4, direction)){
                Ship ship4x = new Ship(4, new int[]{x,y} , direction);
                shipList.add(ship4x);
                Ship.lowerAmounts(2);
                setOccupied(x,y,ship4x);
                setShip(x,y,ship4x);
                print();
            }

        } else if (Ship.getAmounts()[1]!=0){
            if (checkCollision(x,y,3, direction)){
                Ship ship3x = new Ship(3, new int[]{x,y} , direction);
                shipList.add(ship3x);
                Ship.lowerAmounts(1);
                setOccupied(x,y,ship3x);
                setShip(x,y,ship3x);
                print();
            }

        } else if (Ship.getAmounts()[0]!=0){
            if (checkCollision(x,y,2, direction)){
                Ship ship2x = new Ship(2, new int[]{x,y} , direction);
                shipList.add(ship2x);
                Ship.lowerAmounts(0);
                setOccupied(x,y,ship2x);
                setShip(x,y,ship2x);
                print();
            }

        } else {
            System.out.println("all Ships have been placed.");
            JOptionPane.showMessageDialog(null, "Alle Schiffe wurden platziert.");
        }
    }

    private boolean checkCollision(int x, int y, int shipSize, String direction){
        if(direction == "horizontal"){
            if (shipSize + y > size){
                return false;
            } else {
                for(int i=y; i<y+shipSize;i++){
                    if(fieldArray[x][i].isOccupied()){
                        return false;
                    }
                }
            }
        } else if (direction == "vertical"){
            if (shipSize + x > size) {
                return false;
            } else {
                for(int i=x; i<x+shipSize;i++){
                    if(fieldArray[i][y].isOccupied()){
                        return false;
                    }
                }
            }
        } 
        return true;
    }

    public void print(){
        if (status == "server"){
            for (int i=0;i< fieldArray.length;i++){
                for (int j=0;j< fieldArray.length;j++ ){
                    if (fieldArray[i][j].isHit()){
                        GUI.colorButtons(status, i,j ,"Grey");
                    } else if (fieldArray[i][j].isMiss()){
                        GUI.colorButtons(status, i,j ,"Red");
                    } else if (fieldArray[i][j].isMiss()){
                        GUI.colorButtons(status, i,j ,"LightGrey");
                    } else if (fieldArray[i][j].isShip()){
                        GUI.colorButtons(status,i,j ,"Green");
                    } else if (fieldArray[i][j].isWater()){
                        GUI.colorButtons(status, i,j ,"Blue");
                    }
                    //else if (fieldArray[i][j].isOccupied()){
                    //GUI.colorButtonsUser(i,j ,"Blue");
                    //}
                }
            }
        } else if (status == "client"){
            for (int i=0;i< fieldArray.length;i++){
                for (int j=0;j< fieldArray.length;j++ ){
                    if (fieldArray[i][j].isHit()){
                        GUI.colorButtons(status,i,j ,"Grey");
                    } else if (fieldArray[i][j].isMiss()){
                        GUI.colorButtons(status, i,j ,"Red");
                    } else if (fieldArray[i][j].isMiss()){
                        GUI.colorButtons(status, i,j ,"LightGrey");
                    } else {
                        GUI.colorButtons(status, i,j ,"Blue");
                    }
                    //else if (fieldArray[i][j].isOccupied()){
                    //GUI.colorButtonsUser(i,j ,"Blue");
                    //}
                }
            }
        }



    }
    
    public void setEnemyBoard() {
    	enemyBoard=true;
    }
    
    public void colorWater(int x, int y) {
    	if (fieldArray[x][y].isWater()){
            fieldArray[x][y].setMiss(true);
        }
    }
    
    public void shot(int x, int y) {
    	boolean flag;
        if (fieldArray[x][y].isShip()){
            fieldArray[x][y].setHit(true);
            if(playersTurn && enemyBoard) {
	            for (int i=0; i<shipList.size(); i++){
	            	if(shipList.get(i).getDirection().equals("horizontal")) {
	            		flag = true;
	            	}
	            	else {
	            		flag = false;
	            	}
	            	if(flag && shipList.get(i).getStartPoint()[0]==x &&
	            	shipList.get(i).getStartPoint()[1]<=y && shipList.get(i).getStartPoint()[1]+shipList.get(i).getSize()>y
	            	|| shipList.get(i).getStartPoint()[1]==y &&
	            	shipList.get(i).getStartPoint()[0]<=x && shipList.get(i).getStartPoint()[0]+shipList.get(i).getSize()>x) {
	            		shipList.get(i).reduceHitCounter();
		                if (shipList.get(i).getHitCounter()==0){
		                	int horizontal = shipList.get(i).getStartPoint()[1]; //y
		                	int vertical = shipList.get(i).getStartPoint()[0]; //x
		                	for(int j=0; j<shipList.get(i).getSize(); j++) {
		                    	if(flag) {
		                    		if(j==0) {
		                    			if(horizontal-1>=0) {
		                    				horizontal--;
		                            		colorWater(vertical,horizontal);
		                            		if(vertical-1>=0) {
				                    			vertical--;
				                    			colorWater(vertical,horizontal);
				                    			vertical++;
				                    		}
		                            		if(vertical+1<size) {
				                    			vertical++;
				                    			colorWater(vertical,horizontal);
				                    			vertical--;
				                    		}
		                            		horizontal++;
		                    			}
		                    		}
		                    		if(vertical-1>=0) {
		                    			vertical--;
		                    			colorWater(vertical,horizontal);
		                    			vertical++;
		                    		}
		                    		if(vertical+1<size) {
		                    			vertical++;
		                    			colorWater(vertical,horizontal);
		                    			vertical--;
		                    		}
		                    		if(j==shipList.get(i).getSize()-1) {
		                    			if(horizontal+1<size) {
		                    				horizontal++;
		                    				colorWater(vertical,horizontal);
		                    				if(vertical-1>=0) {
				                    			vertical--;
				                    			colorWater(vertical,horizontal);
				                    			vertical++;
				                    		}
		                            		if(vertical+1<size) {
				                    			vertical++;
				                    			colorWater(vertical,horizontal);
				                    			vertical--;
				                    		}
		                    				horizontal--;
		                    			}
		                    		}
		                    		
		                    		horizontal++;
		                    	}
		                    	else {
		                    		
		                    		if(j==0) {
		                    			if(vertical-1>=0) {
		                    				vertical--;
		                            		colorWater(vertical,horizontal);
		                            		if(horizontal-1>=0) {
				                    			horizontal--;
				                    			colorWater(vertical,horizontal);
				                    			horizontal++;
				                    		}
				                    		if(horizontal+1<size) {
				                    			horizontal++;
				                    			colorWater(vertical,horizontal);
				                    			horizontal--;
				                    		}
		                            		vertical++;
		                    			}
		                    		}
		                    		if(horizontal-1>=0) {
		                    			horizontal--;
		                    			colorWater(vertical,horizontal);
		                    			horizontal++;
		                    		}
		                    		if(horizontal+1<size) {
		                    			horizontal++;
		                    			colorWater(vertical,horizontal);
		                    			horizontal--;
		                    		}
		                    		if(j==shipList.get(i).getSize()-1) {
		                    			if(vertical+1<size) {
		                    				vertical++;
		                    				colorWater(vertical,horizontal);
		                    				if(horizontal-1>=0) {
				                    			horizontal--;
				                    			colorWater(vertical,horizontal);
				                    			horizontal++;
				                    		}
				                    		if(horizontal+1<size) {
				                    			horizontal++;
				                    			colorWater(vertical,horizontal);
				                    			horizontal--;
				                    		}
		                    				vertical--;
		                    			}
		                    		}
		                    		
		                    		vertical++;
		                    	}
			                }
		                    shipList.remove(i);
		                    System.out.println("removed");
		                }
	                }
	            }
            }
        } else if (fieldArray[x][y].isWater()){
            fieldArray[x][y].setMiss(true);
            playersTurn=!playersTurn;
        }
        print();
    }

    public Field[][] getFieldArray() {
        return fieldArray;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
        // initialize fieldArray
        // da client nicht direkt bei initialisieren des Boards weiß wie groß es sein wird.
        fieldArray = new Field[size][size];
        for (int i = 0; i<size ; i++){
            for (int j = 0; j<size; j++){
                fieldArray[i][j] = new Field(i,j);
            }
        }
    }

    private void setShip(int x, int y, Ship ship) {
        fieldArray[x][y].setShip(true);
        for (int i=1; i<ship.getSize();i++){
            if (ship.getDirection() == "horizontal"){
                fieldArray[x][y+i].setShip(true);
            } else if (ship.getDirection() == "vertical"){
                fieldArray[x+i][y].setShip(true);
            }
        }
    }

    private void setOccupied(int x, int y, Ship ship) {
        if (ship.getDirection() == "horizontal"){
            for(int i=x-1; i<=x+1;i++){
                for(int j=y-1; j<=y+ship.getSize(); j++){
                    if(i< size && j < size && i>=0 && j>=0){
                        fieldArray[i][j].setOccupied(true);
                    }
                }
            }
        } else if(ship.getDirection() == "vertical"){
            for(int i=x-1; i<=x+ship.getSize();i++){
                for(int j=y-1; j<=y+1; j++){
                    if(i< size && j < size && i>=0 && j>=0){
                        fieldArray[i][j].setOccupied(true);
                    }
                }
            }
        }
    }

    public ArrayList<Ship> getShipList() {
        return shipList;
    }
}
