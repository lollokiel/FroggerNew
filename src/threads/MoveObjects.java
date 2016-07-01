package threads;

import java.util.ArrayList;

import activeObjects.MovableObject;
import activeRows.ActiveRow;
import activeRows.Street;
import frames.Playground;
import utils.Settings;

public class MoveObjects implements Runnable {

	private Playground playground;
	
	public MoveObjects(Playground p) {
		this.playground = p;
	}
	
	@Override
	public void run() {
		while(playground.getMeeple().isAlive()) {
		
			this.playground.getLock().lock();
			
				for(ActiveRow row : this.playground.getRows()) {
					ArrayList<MovableObject> toDelete = new ArrayList<>();
					for(MovableObject obj : row.getMoveableObjects()) {
						if(obj.getMeeple() != null) { // Mitbewegen der Spielfigur auf Stamm
							obj.getMeeple().raiseX(row.getSpeed()*row.getDirection()); // Position d. Spielfigur verschoben
							if(obj.getMeeple().getX() < 0 || obj.getMeeple().getX()+Settings.FIELDSIZE > Settings.PG_WIDTH) {
								this.playground.die(0, Settings.leaveMsg);
							}
						}
						// Position des bewegenden Objektes verschoben
						obj.raiseX(((row.getSpeed())*row.getDirection()));
						if(obj.getX()>Settings.PG_WIDTH+5 || obj.getX() < -75) toDelete.add(obj);
					}
					for(MovableObject objToDelete : toDelete) {
						row.removeMovableObject(objToDelete);
					}
					System.out.println(row.getMoveableObjects());
				}
				
			this.playground.getLock().unlock();
			
			this.playground.repaint();

			this.playground.getLock().lock();
			
				// Auf Kollision prüfen
				for(ActiveRow row : this.playground.getRows()) {
					// Reihe Straße? u. Spielfigur auf Höhe der Reihe
					if(row.getClass() == Street.class && row.getRow() == this.playground.getMeeple().getY()/Settings.FIELDSIZE) {
						for(MovableObject obj : row.getMoveableObjects()) {
							if(!(this.playground.getMeeple().getX() > obj.getX()+obj.getWidth() || 
									this.playground.getMeeple().getX() + Settings.FIELDSIZE < obj.getX() )) {
								this.playground.playSound(Settings.streetSound);
								this.playground.die(2500, Settings.streetMsg);
								break;
							}
						}
					}
				}
			
			this.playground.getLock().unlock();
		
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
