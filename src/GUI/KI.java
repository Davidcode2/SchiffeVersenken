package GUI;

public class KI {
	
	private static int fieldSize = Spielgui.fieldSize;
	private static int amount2x = Spielgui.amount2x;
	private static int amount3x = Spielgui.amount3x;
	private static int amount4x = Spielgui.amount4x;
	private static int amount5x = Spielgui.amount5x;
	
	public static boolean[][] kischiffeplatzieren(boolean[][] enemyschiffe) {
		
		enemyschiffe = new boolean[fieldSize][fieldSize];
		
		int[][] hilfsarray = new int[fieldSize][fieldSize];
		//array bekommt später 3 verschiedene Werte: 0 für Wasser, 1 für Schiff, 2 für Wasser ums Schiff
		//array wird mit 0(Wasser) gefüllt
		for(int i=0; i<fieldSize; i++) {
			for(int j=0; j<fieldSize; j++) {
				hilfsarray[i][j]=0;
			}
		}
		
		int positionhorizontal = 0;
		int positionvertikal = 0;
		
		//Wenn im Folgenden der random Wert kleiner als 0.5 ist, dann wird das Schiff horizontal gesetzt sonst vertikal.
		
		//5er Schiffe platzieren
		for(int i=0; i<amount5x; i++) {
			if(Math.random()<0.5) {//horizontal
				
			}
			else {//vertikal
				
			}
		}
		//4er Schiffe platzieren
		for(int i=0; i<amount4x; i++) {
			if(Math.random()<0.5) {
				
			}
			else {
				
			}
		}
		//3er Schiffe platzieren
		for(int i=0; i<amount3x; i++) {
			if(Math.random()<0.5) {
				
			}
			else {
				
			}
		}
		//2er Schiffe platzieren
		for(int i=0; i<amount2x; i++) {
			if(Math.random()<0.5) {
				
			}
			else {
				
			}
		}
		
		/*
		Random rand = new Random();

        for(int i=0;i<10;i++){
            int x = rand.nextInt((Spielgui.getfieldSize() - 1) + 1);
            int y = rand.nextInt((Spielgui.getfieldSize() - 1) + 1);

            if(enemyschiffe[x][y] == false){
                enemyschiffe[x][y] = true;
            } else if(enemyschiffe[x][y] == true){
                i=i-1;
            }
        }
        */
        return enemyschiffe;
	}
	
}
