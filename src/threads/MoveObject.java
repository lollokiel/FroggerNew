package threads;

import activeObjects.MovableObject;
import activeRows.ActiveRow;
import activeRows.Street;
import frames.Playground;
import utils.Settings;

public class MoveObject implements Runnable {

	private Playground playground;
	
	public MoveObject(Playground p) {
		this.playground = p;
	}
	
	@Override
	public void run() {
		while(playground.getMeeple().isAlive()) {
			
			playground.getLock().lock();
				for(ActiveRow row : playground.getRows()) {
					for(MovableObject obj : row.getMoveableObjects()) {
						if(obj.getMeeple() != null) {
							obj.getMeeple().raiseX(row.getSpeed()*row.getDirection());
							if(obj.getMeeple().getX() < 0 || obj.getMeeple().getX()+Settings.FIELDSIZE > Settings.PLAYGROUND_WIDTH) {
								playground.die();
							}
						}
						obj.raiseX(row.getSpeed()*row.getDirection());
					}
				}
			playground.getLock().unlock();
			
			playground.repaint();

			playground.getLock().lock();
			
			// Auf Kollision prüfen
				for(ActiveRow row : playground.getRows()) {
					if(row.getClass() == Street.class && row.getRow() == playground.getMeeple().getY()/Settings.FIELDSIZE) {
						for(MovableObject obj : row.getMoveableObjects()) {
							if(!(playground.getMeeple().getX() > obj.getX()+obj.getWidth() || playground.getMeeple().getX() + Settings.FIELDSIZE < obj.getX() )) {
								playground.die();
								break;
							}
						}
					}
				}
			playground.getLock().unlock();
			
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
