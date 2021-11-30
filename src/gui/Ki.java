package gui;

import java.awt.Color;
import javax.swing.JButton;

public class Ki {
	
	private static int fieldSize = Spielgui.fieldSize;
	
	protected static boolean kiPlacingShips(boolean[][] enemyShips) {
		
		int timer=0; //abbruchbedingung falls die ki nicht alle schiffe auf das spielfeld bekommt
				
		//hilfsarray bekommt später 3 verschiedene Werte: 0 für Wasser, 1 für Schiff, 2 für Wasser ums Schiff
		int[][] helpArray = new int[fieldSize][fieldSize];
		
		//hilfsarray wird mit 0(Wasser) gefüllt
		for(int i=0; i<fieldSize; i++) {
			for(int j=0; j<fieldSize; j++) {
				helpArray[i][j]=0;
			}
		}
		
		boolean shipPlaced=false;
		boolean flag=true; //bedingung zu überprüfen ob der platz wo das schiff hinsoll möglich ist
		int positionHorizontal = 0;
		int positionVertical = 0;
		
		//Wenn im Folgenden der random Wert kleiner als 0.5 ist, dann wird das Schiff horizontal gesetzt sonst vertikal.
		
		//5er Schiffe platzieren
		for(int i=0; i<Logic.amount5x; i++) {
			shipPlaced=false;
			if(Math.random()<0.5) {//horizontal
				while(!shipPlaced) {//solange probieren bis das schiff platziert wurde
					timer++;
					if(timer>1000) {
						return false;
					}
					flag=true;//bedingung zurücksetzen
					positionHorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionVertical=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionHorizontal+4>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<5; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(helpArray[positionVertical][positionHorizontal+j]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<5; j++) {
							if(j==0 && positionHorizontal>0) {//wasser links vom schiff platzieren
								helpArray[positionVertical][positionHorizontal-1]=2;
							}
							helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
							if(positionVertical>0) {//wasser oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal]=2;
							}
							if(positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal]=2;
							}
							if(j==4 && positionHorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								helpArray[positionVertical][positionHorizontal+1]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal-1]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal-1]=2;
							}
							if(j==4 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal+1]=2;
							}
							if(j==4 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal+1]=2;
							}
							positionHorizontal+=1;//durch das schiff durchgehen
						}
						shipPlaced=true;
					}
				}
			}
			else {//vertikal
				while(!shipPlaced) {//solange probieren bis das schiff platziert wurde
					timer++;
					if(timer>1000) {
						return false;
					}
					flag=true;//bedingung zurücksetzen
					positionHorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionVertical=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionVertical+4>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<5; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(helpArray[positionVertical+j][positionHorizontal]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<5; j++) {
							if(j==0 && positionVertical>0) {//wasser oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal]=2;
							}
							helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
							if(positionHorizontal>0) {//wasser links vom schiff platzieren
								helpArray[positionVertical][positionHorizontal-1]=2;
							}
							if(positionHorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								helpArray[positionVertical][positionHorizontal+1]=2;
							}
							if(j==4 && positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal-1]=2;
							}
							if(j==4 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal-1]=2;
							}
							if(j==0 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal+1]=2;
							}
							if(j==4 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal+1]=2;
							}
							positionVertical+=1;//durch das schiff durchgehen
						}
						shipPlaced=true;
					}
				}
			}
		}
		//4er Schiffe platzieren
		for(int i=0; i<Logic.amount4x; i++) {
			shipPlaced=false;
			if(Math.random()<0.5) {//horizontal
				while(!shipPlaced) {//solange probieren bis das schiff platziert wurde
					timer++;
					if(timer>1000) {
						return false;
					}
					flag=true;//bedingung zurücksetzen
					positionHorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionVertical=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionHorizontal+3>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<4; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(helpArray[positionVertical][positionHorizontal+j]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<4; j++) {
							if(j==0 && positionHorizontal>0) {//wasser vor dem schiff platzieren
								helpArray[positionVertical][positionHorizontal-1]=2;
							}
							helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
							if(positionVertical>0) {//wasser oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal]=2;
							}
							if(positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal]=2;
							}
							if(j==3 && positionHorizontal+1<fieldSize) {//wasser hinter dem schiff platzieren
								helpArray[positionVertical][positionHorizontal+1]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal-1]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal-1]=2;
							}
							if(j==3 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal+1]=2;
							}
							if(j==3 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal+1]=2;
							}
							positionHorizontal+=1;//durch das schiff durchgehen
						}
						shipPlaced=true;
					}
				}
			}
			else {//vertikal
				while(!shipPlaced) {//solange probieren bis das schiff platziert wurde
					timer++;
					if(timer>1000) {
						return false;
					}
					flag=true;//bedingung zurücksetzen
					positionHorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionVertical=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionVertical+3>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<4; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(helpArray[positionVertical+j][positionHorizontal]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<4; j++) {
							if(j==0 && positionVertical>0) {//wasser oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal]=2;
							}
							helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
							if(positionHorizontal>0) {//wasser links vom schiff platzieren
								helpArray[positionVertical][positionHorizontal-1]=2;
							}
							if(positionHorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								helpArray[positionVertical][positionHorizontal+1]=2;
							}
							if(j==3 && positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal-1]=2;
							}
							if(j==3 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal-1]=2;
							}
							if(j==0 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal+1]=2;
							}
							if(j==3 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal+1]=2;
							}
							positionVertical+=1;//durch das schiff durchgehen
						}
						shipPlaced=true;
					}
				}
			}
		}
		//3er Schiffe platzieren
		for(int i=0; i<Logic.amount3x; i++) {
			shipPlaced=false;
			if(Math.random()<0.5) {//horizontal
				while(!shipPlaced) {//solange probieren bis das schiff platziert wurde
					timer++;
					if(timer>1000) {
						return false;
					}
					flag=true;//bedingung zurücksetzen
					positionHorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionVertical=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionHorizontal+2>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<3; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(helpArray[positionVertical][positionHorizontal+j]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<3; j++) {
							if(j==0 && positionHorizontal>0) {//wasser vor dem schiff platzieren
								helpArray[positionVertical][positionHorizontal-1]=2;
							}
							helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
							if(positionVertical>0) {//wasser oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal]=2;
							}
							if(positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal]=2;
							}
							if(j==2 && positionHorizontal+1<fieldSize) {//wasser hinter dem schiff platzieren
								helpArray[positionVertical][positionHorizontal+1]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal-1]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal-1]=2;
							}
							if(j==2 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal+1]=2;
							}
							if(j==2 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal+1]=2;
							}
							positionHorizontal+=1;//durch das schiff durchgehen
						}
						shipPlaced=true;
					}
				}
			}
			else {//vertikal
				while(!shipPlaced) {//solange probieren bis das schiff platziert wurde
					timer++;
					if(timer>1000) {
						return false;
					}
					flag=true;//bedingung zurücksetzen
					positionHorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionVertical=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionVertical+2>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<3; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(helpArray[positionVertical+j][positionHorizontal]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<3; j++) {
							if(j==0 && positionVertical>0) {//wasser oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal]=2;
							}
							helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
							if(positionHorizontal>0) {//wasser links vom schiff platzieren
								helpArray[positionVertical][positionHorizontal-1]=2;
							}
							if(positionHorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								helpArray[positionVertical][positionHorizontal+1]=2;
							}
							if(j==2 && positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal-1]=2;
							}
							if(j==2 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal-1]=2;
							}
							if(j==0 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal+1]=2;
							}
							if(j==2 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal+1]=2;
							}
							positionVertical+=1;//durch das schiff durchgehen
						}
						shipPlaced=true;
					}
				}
			}
		}
		//2er Schiffe platzieren
		for(int i=0; i<Logic.amount2x; i++) {
			shipPlaced=false;
			if(Math.random()<0.5) {//horizontal
				while(!shipPlaced) {//solange probieren bis das schiff platziert wurde
					timer++;
					if(timer>1000) {
						return false;
					}
					flag=true;//bedingung zurücksetzen
					positionHorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionVertical=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionHorizontal+1>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<2; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(helpArray[positionVertical][positionHorizontal+j]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<2; j++) {
							if(j==0 && positionHorizontal>0) {//wasser vor dem schiff platzieren
								helpArray[positionVertical][positionHorizontal-1]=2;
							}
							helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
							if(positionVertical>0) {//wasser oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal]=2;
							}
							if(positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal]=2;
							}
							if(j==1 && positionHorizontal+1<fieldSize) {//wasser hinter dem schiff platzieren
								helpArray[positionVertical][positionHorizontal+1]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal-1]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal-1]=2;
							}
							if(j==1 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal+1]=2;
							}
							if(j==1 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal+1]=2;
							}
							positionHorizontal+=1;//durch das schiff durchgehen
						}
						shipPlaced=true;
					}
				}
			}
			else {//vertikal
				while(!shipPlaced) {//solange probieren bis das schiff platziert wurde
					timer++;
					if(timer>1000) {
						return false;
					}
					flag=true;//bedingung zurücksetzen
					positionHorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
					positionVertical=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
					if(positionVertical+1>=fieldSize) {//falls das schiff über das spielfeld hinausragt, neu probieren
						continue;
					}
					for(int j=0; j<2; j++) {//überprüfen ob auf den plätzen wo das schiff hinsoll ein anderes schiff ist oder ein wasser ums schiff
						if(helpArray[positionVertical+j][positionHorizontal]!=0) {
							flag=false;
							break;
						}
					}
					if(flag) {//schiff platzieren und drum herum wasser ums schiff platzieren
						for(int j=0; j<2; j++) {
							if(j==0 && positionVertical>0) {//wasser oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal]=2;
							}
							helpArray[positionVertical][positionHorizontal]=1;//teil vom schiff platzieren
							if(positionHorizontal>0) {//wasser links vom schiff platzieren
								helpArray[positionVertical][positionHorizontal-1]=2;
							}
							if(positionHorizontal+1<fieldSize) {//wasser rechts vom schiff platzieren
								helpArray[positionVertical][positionHorizontal+1]=2;
							}
							if(j==1 && positionVertical+1<fieldSize) {//wasser unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal]=2;
							}
							if(j==0 && positionHorizontal>0 && positionVertical>0) {//wasser links oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal-1]=2;
							}
							if(j==1 && positionHorizontal>0 && positionVertical+1<fieldSize) {//wasser links unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal-1]=2;
							}
							if(j==0 && positionHorizontal+1<fieldSize && positionVertical>0) {//wasser rechts oberhalb vom schiff platzieren
								helpArray[positionVertical-1][positionHorizontal+1]=2;
							}
							if(j==1 && positionHorizontal+1<fieldSize && positionVertical+1<fieldSize) {//wasser rechts unterhalb vom schiff platzieren
								helpArray[positionVertical+1][positionHorizontal+1]=2;
							}
							positionVertical+=1;//durch das schiff durchgehen
						}
						shipPlaced=true;
					}
				}
			}
		}
		for(int i=0; i<fieldSize; i++) {
			for(int j=0; j<fieldSize; j++) {
				if(helpArray[i][j]==1) {
					enemyShips[i][j]=true;	//die schiffe in den boolean array setzen
				}
			}
		}
        return true;
	}
	
	protected static int kiShot(JButton[][] field, boolean[][] ships) {
		
		while(true) {
		
			int positionVertical=(int) (Math.random()*fieldSize);//berechnet die y Koordinate
			int positionHorizontal=(int) (Math.random()*fieldSize);//berechnet die x Koordinate
			
			if(field[positionVertical][positionHorizontal].getBackground().equals(new Color(255,0,255)) || field[positionVertical][positionHorizontal].getBackground().equals(new Color(255,255,0))) {
				continue;
			}
			if(ships[positionVertical][positionHorizontal] == false){
				field[positionVertical][positionHorizontal].setBackground(new Color(255,0,255));
				return 0;
			} else {
				field[positionVertical][positionHorizontal].setBackground(new Color(255,255,0));
				return 1;
	        }
		}
	}
}