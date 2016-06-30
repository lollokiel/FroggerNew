package activeRows;

import activeObjects.MovableObject;
import frames.Playground;
import utils.Settings;

/**
 * Erweitert ActiveRow
 * Eine der beiden "aktiven Reihen". Bewegende Objekte sind Autos.
 * Fügt unregelmäßig - abhängig von der Wiederholungsrate - neue Autos hinzu.
 * Einstellungen, die in der abstrakten Klasse deklariert wurden, werden gefüllt 
 *
 */
public class Street extends ActiveRow {
	
	public Street(int direction, int speed, int row, int wdhSpeed, Playground playground) {
		
		this.setDirection(direction);
		this.setSpeed(speed);
		this.setRow(row);
		this.setWdhSpeed(wdhSpeed);
		
		this.playground = playground;
		this.settings 	= playground.getGameFrame().getMainFrame().getSettings();
		
		// Setze zu Beginn schon Autos auf die Straße
		MovableObject newCar1 = null;
		MovableObject newCar2 = null;
		if(this.getDirection() == 1) {
			newCar1 = new MovableObject(this.settings.CARS_R.get((int)(Math.random()*this.settings.CARS_R.size())),Settings.FIELDSIZE*Settings.COLS/3,this);
			newCar2 = new MovableObject(this.settings.CARS_R.get((int)(Math.random()*this.settings.CARS_R.size())),(Settings.FIELDSIZE*Settings.COLS*2)/3,this);
		} else {
			newCar1 = new MovableObject(this.settings.CARS_L.get((int)(Math.random()*this.settings.CARS_L.size())),Settings.FIELDSIZE*Settings.COLS/3,this);
			newCar2 = new MovableObject(this.settings.CARS_L.get((int)(Math.random()*this.settings.CARS_L.size())),(Settings.FIELDSIZE*Settings.COLS*2)/3,this);
		}
		this.addMovableObject(newCar1);
		this.addMovableObject(newCar2);
		
		// Starte das Hinzufügen von neuen Autos
		this.start();
	}

	@Override
	public void run() {
		
		// Solage die Spielfigur am Leben ist, sollen neue Autos hinzugefügt werden.
		while(this.playground.getMeeple().isAlive()) {
			
			this.playground.getLock().lock();
			
				// Erstelle ein neues Objekt, das sich bewegt. Wähle dafür ein zufälliges Hintergrundbild aller Autos.
				MovableObject newCar = null;
				if(this.getDirection() == 1) {
					newCar = new MovableObject(this.settings.CARS_R.get((int)(Math.random()*this.settings.CARS_R.size())),-70,this);
				} else {
					newCar = new MovableObject(this.settings.CARS_L.get((int)(Math.random()*this.settings.CARS_L.size())),Settings.PG_WIDTH,this);
				}
				this.addMovableObject(newCar);
				
			this.playground.getLock().unlock();
			
			try {
				
				// Thread unterbrechen und nach zufällig berechneter Zeit t wieder ausführen
				int frequenzZeit = (int) (newCar.getWidth()/(this.getSpeed()*40.0)*1000); // Dauer für Strecke des eigenen Autos
				int t = frequenzZeit;  // Mindestzeit zum Neuhinzufügen = frequenzZeit, um Überschneidungen zu verhindern 
			
				int minDifference = (this.getWdhSpeed()*2)-1; // Mindestens 1 Autolänge Abstand
				int maxDifference =  minDifference+2; // Maximal Min +2
				
				int minT = t*minDifference;
				int maxT = t*maxDifference;
				int diffT = maxT-minT;
				int randT = (int)(Math.random()*diffT);				

				t += minT + randT;
				
				Thread.sleep(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
		}
	}
}
