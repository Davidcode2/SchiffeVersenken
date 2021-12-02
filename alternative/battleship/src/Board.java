import java.util.ArrayList;


public class Board {
    private Field[][] fieldArray;
    private int size;
    private ArrayList<Ship> shipList = new ArrayList<Ship>();

    public Board(int size) {
        this.size = size;
        fieldArray = new Field[size][size];
        for (int i = 0; i<size ; i++){
            for (int j = 0; j<size; j++){
                fieldArray[i][j] = new Field(i,j);
            }
        }
    }

    public void place(int x, int y, String direction) {
        if (Ship.getAmounts()[3]!=0){
            if (checkCollision(x,y,5, direction)){
                Ship ship5x = new Ship(5, new int[]{x,y} , direction);
                shipList.add(ship5x);
                Ship.lowerAmounts(3);
                printShip(x,y,ship5x);
                setOccupied(x,y,ship5x);
            }

        } else if (Ship.getAmounts()[2]!=0){
            if (checkCollision(x,y,4, direction)){
                Ship ship4x = new Ship(4, new int[]{x,y} , direction);
                shipList.add(ship4x);
                Ship.lowerAmounts(2);
                printShip(x,y,ship4x);
                setOccupied(x,y,ship4x);
            }

        } else if (Ship.getAmounts()[1]!=0){
            if (checkCollision(x,y,3, direction)){
                Ship ship3x = new Ship(3, new int[]{x,y} , direction);
                shipList.add(ship3x);
                Ship.lowerAmounts(1);
                printShip(x,y,ship3x);
                setOccupied(x,y,ship3x);
            }

        } else if (Ship.getAmounts()[0]!=0){
            if (checkCollision(x,y,2, direction)){
                Ship ship2x = new Ship(2, new int[]{x,y} , direction);
                shipList.add(ship2x);
                Ship.lowerAmounts(0);
                printShip(x,y,ship2x);
                setOccupied(x,y,ship2x);
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

    public void printShip(int x, int y, Ship ship){

        if(ship.getDirection() == "horizontal"){
            for(int i=0; i<ship.getSize(); i++){
                GUI.colorButtonsUser(x,y+i ,"Green");
            }
        } else if (ship.getDirection() == "vertical"){
            for(int i=0; i<ship.getSize(); i++){
                GUI.colorButtonsUser(x+i,y,"Green");
            }
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

    public Field[][] getFieldArray() {
        return fieldArray;
    }

    public void print(){
        for(int i=0; i<shipList.size();i++){

            printShip(shipList.get(i).getStartPoint()[0],shipList.get(i).getStartPoint()[1],shipList.get(i));
        }
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
}
