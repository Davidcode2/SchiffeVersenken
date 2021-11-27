package GUI;

public class KI {
	
	private static int fieldSize = Spielgui.fieldSize;
	private static int amount2x = Spielgui.amount2x;
	private static int amount3x = Spielgui.amount3x;
	private static int amount4x = Spielgui.amount4x;
	private static int amount5x = Spielgui.amount5x;
	
	public static boolean[][] kischiffeplatzieren(boolean[][] enemyschiffe) {
		
		enemyschiffe = new boolean[fieldSize][fieldSize];
		
		//hilfsarray bekommt später 3 verschiedene Werte: 0 für Wasser, 1 für Schiff, 2 für Wasser ums Schiff
		int[][] hilfsarray = new int[fieldSize][fieldSize];
		
		//hilfsarray wird mit 0(Wasser) gefüllt
		for(int i=0; i<fieldSize; i++) {
			for(int j=0; j<fieldSize; j++) {
				hilfsarray[i][j]=0;
			}
		}
		
		boolean shipplaced=false;
		boolean flag=true; //bedingung zu überprüfen ob der platz wo das schiff hinsoll möglich ist
		int positionhorizontal = 0;
		int positionvertikal = 0;
		
		//Wenn im Folgenden der random Wert kleiner als 0.5 ist, dann wird das Schiff horizontal gesetzt sonst vertikal.
		
		//5er Schiffe platzieren
		for(int i=0; i<amount5x; i++) {
			shipplaced=false;
			if(Math.random()<0.5) {//horizontal
				while(!shipplaced) {//solange probieren bis das schiff platziert wurde
					flag=true;//bedingung zurücksetzen
					positionhorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionvertikal=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionhorizontal+4>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<5; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(hilfsarray[positionvertikal][positionhorizontal+j]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<5; j++) {
							if(j==0 && positionhorizontal>0) {//wasser links vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal-1]=2;
							}
							hilfsarray[positionvertikal][positionhorizontal]=1;//teil vom schiff platzieren
							if(positionvertikal>0) {//wasser oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal]=2;
							}
							if(positionvertikal+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal]=2;
							}
							if(j==4 && positionhorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal+1]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal>0) {//wasser links oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal-1]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal-1]=2;
							}
							if(j==4 && positionhorizontal+1<fieldSize && positionvertikal>0) {//wasser rechts oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal+1]=2;
							}
							if(j==4 && positionhorizontal+1<fieldSize && positionvertikal+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal+1]=2;
							}
							positionhorizontal+=1;//durch das schiff durchgehen
						}
						shipplaced=true;
					}
				}
			}
			else {//vertikal
				while(!shipplaced) {//solange probieren bis das schiff platziert wurde
					flag=true;//bedingung zurücksetzen
					positionhorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionvertikal=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionvertikal+4>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<5; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(hilfsarray[positionvertikal+j][positionhorizontal]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<5; j++) {
							if(j==0 && positionvertikal>0) {//wasser oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal]=2;
							}
							hilfsarray[positionvertikal][positionhorizontal]=1;//teil vom schiff platzieren
							if(positionhorizontal>0) {//wasser links vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal-1]=2;
							}
							if(positionhorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal+1]=2;
							}
							if(j==4 && positionvertikal+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal>0) {//wasser links oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal-1]=2;
							}
							if(j==4 && positionhorizontal>0 && positionvertikal+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal-1]=2;
							}
							if(j==0 && positionhorizontal+1<fieldSize && positionvertikal>0) {//wasser rechts oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal+1]=2;
							}
							if(j==4 && positionhorizontal+1<fieldSize && positionvertikal+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal+1]=2;
							}
							positionvertikal+=1;//durch das schiff durchgehen
						}
						shipplaced=true;
					}
				}
			}
		}
		//4er Schiffe platzieren
		for(int i=0; i<amount4x; i++) {
			shipplaced=false;
			if(Math.random()<0.5) {//horizontal
				while(!shipplaced) {//solange probieren bis das schiff platziert wurde
					flag=true;//bedingung zurücksetzen
					positionhorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionvertikal=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionhorizontal+3>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<4; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(hilfsarray[positionvertikal][positionhorizontal+j]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<4; j++) {
							if(j==0 && positionhorizontal>0) {//wasser vor dem schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal-1]=2;
							}
							hilfsarray[positionvertikal][positionhorizontal]=1;//teil vom schiff platzieren
							if(positionvertikal>0) {//wasser oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal]=2;
							}
							if(positionvertikal+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal]=2;
							}
							if(j==3 && positionhorizontal+1<fieldSize) {//wasser hinter dem schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal+1]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal>0) {//wasser links oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal-1]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal-1]=2;
							}
							if(j==3 && positionhorizontal+1<fieldSize && positionvertikal>0) {//wasser rechts oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal+1]=2;
							}
							if(j==3 && positionhorizontal+1<fieldSize && positionvertikal+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal+1]=2;
							}
							positionhorizontal+=1;//durch das schiff durchgehen
						}
						shipplaced=true;
					}
				}
			}
			else {//vertikal
				while(!shipplaced) {//solange probieren bis das schiff platziert wurde
					flag=true;//bedingung zurücksetzen
					positionhorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionvertikal=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionvertikal+3>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<4; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(hilfsarray[positionvertikal+j][positionhorizontal]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<4; j++) {
							if(j==0 && positionvertikal>0) {//wasser oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal]=2;
							}
							hilfsarray[positionvertikal][positionhorizontal]=1;//teil vom schiff platzieren
							if(positionhorizontal>0) {//wasser links vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal-1]=2;
							}
							if(positionhorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal+1]=2;
							}
							if(j==3 && positionvertikal+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal>0) {//wasser links oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal-1]=2;
							}
							if(j==3 && positionhorizontal>0 && positionvertikal+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal-1]=2;
							}
							if(j==0 && positionhorizontal+1<fieldSize && positionvertikal>0) {//wasser rechts oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal+1]=2;
							}
							if(j==3 && positionhorizontal+1<fieldSize && positionvertikal+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal+1]=2;
							}
							positionvertikal+=1;//durch das schiff durchgehen
						}
						shipplaced=true;
					}
				}
			}
		}
		//3er Schiffe platzieren
		for(int i=0; i<amount3x; i++) {
			shipplaced=false;
			if(Math.random()<0.5) {//horizontal
				while(!shipplaced) {//solange probieren bis das schiff platziert wurde
					flag=true;//bedingung zurücksetzen
					positionhorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionvertikal=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionhorizontal+2>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<3; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(hilfsarray[positionvertikal][positionhorizontal+j]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<3; j++) {
							if(j==0 && positionhorizontal>0) {//wasser vor dem schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal-1]=2;
							}
							hilfsarray[positionvertikal][positionhorizontal]=1;//teil vom schiff platzieren
							if(positionvertikal>0) {//wasser oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal]=2;
							}
							if(positionvertikal+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal]=2;
							}
							if(j==2 && positionhorizontal+1<fieldSize) {//wasser hinter dem schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal+1]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal>0) {//wasser links oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal-1]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal-1]=2;
							}
							if(j==2 && positionhorizontal+1<fieldSize && positionvertikal>0) {//wasser rechts oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal+1]=2;
							}
							if(j==2 && positionhorizontal+1<fieldSize && positionvertikal+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal+1]=2;
							}
							positionhorizontal+=1;//durch das schiff durchgehen
						}
						shipplaced=true;
					}
				}
			}
			else {//vertikal
				while(!shipplaced) {//solange probieren bis das schiff platziert wurde
					flag=true;//bedingung zurücksetzen
					positionhorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionvertikal=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionvertikal+2>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<3; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(hilfsarray[positionvertikal+j][positionhorizontal]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<3; j++) {
							if(j==0 && positionvertikal>0) {//wasser oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal]=2;
							}
							hilfsarray[positionvertikal][positionhorizontal]=1;//teil vom schiff platzieren
							if(positionhorizontal>0) {//wasser links vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal-1]=2;
							}
							if(positionhorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal+1]=2;
							}
							if(j==2 && positionvertikal+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal>0) {//wasser links oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal-1]=2;
							}
							if(j==2 && positionhorizontal>0 && positionvertikal+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal-1]=2;
							}
							if(j==0 && positionhorizontal+1<fieldSize && positionvertikal>0) {//wasser rechts oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal+1]=2;
							}
							if(j==2 && positionhorizontal+1<fieldSize && positionvertikal+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal+1]=2;
							}
							positionvertikal+=1;//durch das schiff durchgehen
						}
						shipplaced=true;
					}
				}
			}
		}
		//2er Schiffe platzieren
		for(int i=0; i<amount2x; i++) {
			shipplaced=false;
			if(Math.random()<0.5) {//horizontal
				while(!shipplaced) {//solange probieren bis das schiff platziert wurde
					flag=true;//bedingung zurücksetzen
					positionhorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionvertikal=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionhorizontal+1>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<2; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(hilfsarray[positionvertikal][positionhorizontal+j]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<2; j++) {
							if(j==0 && positionhorizontal>0) {//wasser vor dem schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal-1]=2;
							}
							hilfsarray[positionvertikal][positionhorizontal]=1;//teil vom schiff platzieren
							if(positionvertikal>0) {//wasser oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal]=2;
							}
							if(positionvertikal+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal]=2;
							}
							if(j==1 && positionhorizontal+1<fieldSize) {//wasser hinter dem schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal+1]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal>0) {//wasser links oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal-1]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal-1]=2;
							}
							if(j==1 && positionhorizontal+1<fieldSize && positionvertikal>0) {//wasser rechts oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal+1]=2;
							}
							if(j==1 && positionhorizontal+1<fieldSize && positionvertikal+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal+1]=2;
							}
							positionhorizontal+=1;//durch das schiff durchgehen
						}
						shipplaced=true;
					}
				}
			}
			else {//vertikal
				while(!shipplaced) {//solange probieren bis das schiff platziert wurde
					flag=true;//bedingung zurücksetzen
					positionhorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionvertikal=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionvertikal+1>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<2; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(hilfsarray[positionvertikal+j][positionhorizontal]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<2; j++) {
							if(j==0 && positionvertikal>0) {//wasser oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal]=2;
							}
							hilfsarray[positionvertikal][positionhorizontal]=1;//teil vom schiff platzieren
							if(positionhorizontal>0) {//wasser links vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal-1]=2;
							}
							if(positionhorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								hilfsarray[positionvertikal][positionhorizontal+1]=2;
							}
							if(j==1 && positionvertikal+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal]=2;
							}
							if(j==0 && positionhorizontal>0 && positionvertikal>0) {//wasser links oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal-1]=2;
							}
							if(j==1 && positionhorizontal>0 && positionvertikal+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal-1]=2;
							}
							if(j==0 && positionhorizontal+1<fieldSize && positionvertikal>0) {//wasser rechts oberhalb vom schiff platzieren
								hilfsarray[positionvertikal-1][positionhorizontal+1]=2;
							}
							if(j==1 && positionhorizontal+1<fieldSize && positionvertikal+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								hilfsarray[positionvertikal+1][positionhorizontal+1]=2;
							}
							positionvertikal+=1;//durch das schiff durchgehen
						}
						shipplaced=true;
					}
				}
			}
		}
		for(int i=0; i<fieldSize; i++) {
			for(int j=0; j<fieldSize; j++) {
				if(hilfsarray[i][j]==1) {
					enemyschiffe[i][j]=true;
				}
			}
		}
        return enemyschiffe;
	}
	
}
