
import java.awt.Color;

public class Logic {
	
	private static int fieldSize = GUI.buttonsUser.length;  //Spielgui.fieldSize;
	protected static int amount2x;
	protected static int amount3x;
	protected static int amount4x;
	protected static int amount5x;
		
	protected static boolean placeShipHorizontal(int positionVertical, int positionHorizontal, int[][] helpArray) {
		int shipLenght=0; //länge des zu setzenden schiffs festlegen
		if(amount5x!=0) {
			shipLenght=5;
		}
		else if(amount4x!=0) {
			shipLenght=4;
		}
		else if(amount3x!=0) {
			shipLenght=3;
		}
		else if(amount2x!=0) {
			shipLenght=2;
		}
		else {
			return false; //wenn schon alle schiffe gesetzt wurden passiert nichts
		}
						
		if(positionHorizontal+shipLenght-1>=fieldSize) {//falls das schiff über das spielfeld hinausragt, kann es nicht gesetzt werden
			return false;
		}
		for(int j=0; j<shipLenght; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
			if(helpArray[positionVertical][positionHorizontal+j]!=0) {
				return false;
			}
		}
		
		switch(shipLenght) { //da das schiff gesetzt werden kann wird die menge verringert
		case 5:
			amount5x--;
			break;
		case 4:
			amount4x--;
			break;
		case 3:
			amount3x--;
			break;
		case 2:
			amount2x--;
			break;
		default:
			System.out.println("Fehler in der Logik Horizontal.");
			return false;
		}
		
		for(int j=0; j<shipLenght; j++) {
			helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
			//Spielgui.field[positionVertical][positionHorizontal].setBackground(new Color(0,255,0));
			if(j==0 && positionHorizontal>0) {//wasser links vom schiff platzieren
				helpArray[positionVertical][positionHorizontal-1]=2;
			}
			if(positionVertical>0) {//wasser oberhalb vom schiff platzieren
				helpArray[positionVertical-1][positionHorizontal]=2;
			}
			if(positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
				helpArray[positionVertical+1][positionHorizontal]=2;
			}
			if(j==shipLenght-1 && positionHorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
				helpArray[positionVertical][positionHorizontal+1]=2;
			}
			if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
				helpArray[positionVertical-1][positionHorizontal-1]=2;
			}
			if(j==0 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
				helpArray[positionVertical+1][positionHorizontal-1]=2;
			}
			if(j==shipLenght-1 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
				helpArray[positionVertical-1][positionHorizontal+1]=2;
			}
			if(j==shipLenght-1 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
				helpArray[positionVertical+1][positionHorizontal+1]=2;
			}
			positionHorizontal+=1;//durch das schiff durchgehen
		}
		return true;
	}
	
	protected static boolean placeShipVertical(int positionVertical, int positionHorizontal, int[][] helpArray) {
		int shipLenght=0; //länge des zu setzenden schiffs festlegen
		if(amount5x!=0) {
			shipLenght=5;
		}
		else if(amount4x!=0) {
			shipLenght=4;
		}
		else if(amount3x!=0) {
			shipLenght=3;
		}
		else if(amount2x!=0) {
			shipLenght=2;
		}
		else {
			return false; //wenn schon alle schiffe gesetzt wurden passiert nichts
		}
						
		if(positionVertical+shipLenght-1>=fieldSize) {//falls das schiff über das spielfeld hinausragt, kann es nicht gesetzt werden
			return false;
		}
		for(int j=0; j<shipLenght; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
			if(helpArray[positionVertical+j][positionHorizontal]!=0) {
				return false;
			}
		}
		
		switch(shipLenght) { //da das schiff gesetzt werden kann wird die menge verringert
		case 5:
			amount5x--;
			break;
		case 4:
			amount4x--;
			break;
		case 3:
			amount3x--;
			break;
		case 2:
			amount2x--;
			break;
		default:
			System.out.println("Fehler in der Logik Vertikal.");
			return false;
		}
		
		for(int j=0; j<shipLenght; j++) {
			helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
			//Spielgui.field[positionVertical][positionHorizontal].setBackground(new Color(0,255,0));
			if(j==0 && positionVertical>0) {//wasser oberhalb vom schiff platzieren
				helpArray[positionVertical-1][positionHorizontal]=2;
			}
			if(positionHorizontal>0) {//wasser links vom schiff platzieren
				helpArray[positionVertical][positionHorizontal-1]=2;
			}
			if(positionHorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
				helpArray[positionVertical][positionHorizontal+1]=2;
			}
			if(j==shipLenght-1 && positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
				helpArray[positionVertical+1][positionHorizontal]=2;
			}
			if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
				helpArray[positionVertical-1][positionHorizontal-1]=2;
			}
			if(j==shipLenght-1 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
				helpArray[positionVertical+1][positionHorizontal-1]=2;
			}
			if(j==0 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
				helpArray[positionVertical-1][positionHorizontal+1]=2;
			}
			if(j==shipLenght-1 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
				helpArray[positionVertical+1][positionHorizontal+1]=2;
			}
			positionVertical+=1;//durch das schiff durchgehen
		}
		return true;
	}
	
	protected static void shipAmount(int x){
		amount2x=0;
		amount3x=0;
		amount4x=0;
		amount5x=0;
		int temp;
		int rest = ((x*x)*3)/10;

		do{
			if(rest<30){
				if(rest>13){
					amount2x++;
					amount3x++;
					amount4x++;
					amount5x++;
					rest = rest - 14;
				} else if (rest > 8){
					amount2x++;
					amount3x++;
					amount4x++;
					rest = rest - 9;
				} else if (rest > 4){
					amount2x++;
					amount3x++;
					rest = rest - 5;
				} else {
					amount2x++;
					rest = rest - 2;
				}
			} else {
				temp = rest/30;
				rest = rest-temp*30;
				amount2x = 4*temp;
				amount3x = 3*temp;
				amount4x = 2*temp;
				amount5x = temp;
			}
		}while(rest != 0 && rest != 1);
		System.out.println("2x: " + amount2x);
		System.out.println("3x: " + amount3x);
		System.out.println("4x: " + amount4x);
		System.out.println("5x: " + amount5x);
	}
}