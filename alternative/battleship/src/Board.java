import java.awt.*;
import java.util.ArrayList;


public class Board {
    private Field[][] fieldArray;
    private int size;
    private String status;
    private ArrayList<Ship> shipList = new ArrayList<Ship>();

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
            // TODO: display banner saying:
            System.out.println("all Ships have been placed.");
//            System.out.println("reset amounts");
//            Ship.calcAmount(fieldArray.length);
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

    public void shot(int x, int y) {
        if (fieldArray[x][y].isShip()){
            //TODO: check ob Schiff gesunken (wenn gesunken, Schiff aus ArrayList löschen

            fieldArray[x][y].setHit(true);
            for (int i=0; i<shipList.size();i++){
                if (shipList.get(i).getStartPoint()[0] == x && shipList.get(i).getStartPoint()[1] == y && checkSunk(x,y,shipList.get(i))){
                    shipList.remove(i);
                    System.out.println("removed");
                }
            }
        } else if (fieldArray[x][y].isWater()){
            fieldArray[x][y].setMiss(true);
        }
        print();
    }

    private boolean checkSunk(int x, int y, Ship ship) {
        //TODO: fix
        System.out.println(ship.getSize());
        if (ship.getSize() == 1){
            return true;
        } else {
            for (int i=0;i<ship.getSize();i++){
                if (ship.getDirection() == "horizontal"){
                    if (fieldArray[x][y+i].isHit()){
                        ship.setSize(ship.getSize()-1);
                    } else {
                        ship.setStartPoint(new int[]{x,y+i});
                    }
                } else if (ship.getDirection() == "vertical"){
                    if (fieldArray[x+i][y].isHit()){
                        ship.setSize(ship.getSize()-1);

                    } else {
                        ship.setStartPoint(new int[]{x+i,y});
                    }
                }
            }
        }
        return false;
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
