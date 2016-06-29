package threads;

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
					for(MovableObject obj : row.getMoveableObjects()) {
						if(obj.getMeeple() != null) {
							obj.getMeeple().raiseX(row.getSpeed()*row.getDirection());
							if(obj.getMeeple().getX() < 0 || obj.getMeeple().getX()+Settings.FIELDSIZE > Settings.PG_WIDTH) {
								this.playground.die();
							}
						}
						obj.raiseX(((row.getSpeed())*row.getDirection()));
					}
				}
				
			this.playground.getLock().unlock();
			
			this.playground.repaint();

			this.playground.getLock().lock();
			
				// Auf Kollision prüfen
				for(ActiveRow row : this.playground.getRows()) {
					if(row.getClass() == Street.class && row.getRow() == this.playground.getMeeple().getY()/Settings.FIELDSIZE) {
						for(MovableObject obj : row.getMoveableObjects()) {
							if(!(this.playground.getMeeple().getX() > obj.getX()+obj.getWidth() || this.playground.getMeeple().getX() + Settings.FIELDSIZE < obj.getX() )) {
								this.playground.die();
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
