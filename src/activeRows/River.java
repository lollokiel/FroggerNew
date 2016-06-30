package activeRows;

import activeObjects.MovableObject;
import frames.Playground;
import utils.Settings;

/**
 * Erweitert ActiveRow
 * Eine der beiden "aktiven Reihen". Bewegende Objekte sind Holzstämme.
 * Fügt unregelmäßig - abhängig von der Wiederholungsrate - neue Holzstämme hinzu.
 * Einstellungen, die in der abstrakten Klasse deklariert wurden, werden gefüllt 
 *
 */
public class River extends ActiveRow {
	
	public River(int direction, int speed, int row, int wdhSpeed, Playground playground) {
		this.playground = playground;
		this.settings = playground.getGameFrame().getMainFrame().getSettings();
		this.setDirection(direction);
		this.setWdhSpeed(wdhSpeed);
		this.setSpeed(speed);
		this.setRow(row);
		
		// Setze schon ein Holzstamm auf den Fluss
		if(this.getWdhSpeed() != 0) {
			this.addMovableObject(new MovableObject(settings.TRUNK,120,this));
		}
		
		// Startet das Hinzufügen von Holzstämmen
		this.start();
	}
	
	/**
	 * Methode zum Hinzufügen neuer bewegender Objekte
	 */
	@Override
	public void run() {
		
		// ggf. Hinzufügen untersagt, z.B. beim Einsatz von Wasserrosen
		if(this.getWdhSpeed() != 0) {
			
			// Solange die Spielfigur am Leben ist sollen neue Objekte zum Fluss hinzugefügt werden
			while(this.playground.getMeeple().isAlive()) {
				
				this.playground.getLock().lock();
				
					MovableObject newTrunk;
					if(this.getDirection() == 1) {
						newTrunk = new MovableObject(this.settings.TRUNK, -70 ,this);
					} else {
						newTrunk = new MovableObject(this.settings.TRUNK, Settings.PG_WIDTH, this);
					}
					this.addMovableObject(newTrunk);
					
				this.playground.getLock().unlock();
				
				// Thread unterbrechen und nach zufällig berechneter Zeit t wieder ausführen
				try {
					
					int frequenzZeit = (int) (newTrunk.getWidth()/(this.getSpeed()*40.0)*1000); // Dauer für Strecke des eigenen Autos
					int t = frequenzZeit; // Mindestzeit zum Neuhinzufügen = frequenzZeit, um Überschneidungen zu verhindern 
				
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
	
}
