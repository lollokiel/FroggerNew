package threads;

import frames.Playground;
import objects.Car;
import objects.MoveableObject;
import objects.Street;
import settings.Settings;
import utils.ActiveRow;

public class MoveObject implements Runnable {

	private Playground playground;
	
	public MoveObject(Playground p) {
		this.playground = p;
	}
	
	@Override
	public void run() {
		while(playground.meeple.isAlive()) {
			playground.lock.lock();
				for(ActiveRow row : playground.getRows()) {
					for(MoveableObject obj : row.getMoveableObjects()) {
						obj.raiseX(row.getSpeed()*row.getDirection());
					}
				}
			playground.lock.unlock();
			
			playground.repaint();
			
			// Auf Kollision prüfen
			playground.lock.lock();
				for(ActiveRow row : playground.getRows()) {
					if(row.getClass() == Street.class && row.getRow() == playground.meeple.getY()/Settings.FIELDSIZE) {
						for(MoveableObject obj : row.getMoveableObjects()) {
							if(!(playground.meeple.getX() > obj.getX()+obj.getWidth() || playground.meeple.getX() + Settings.FIELDSIZE < obj.getX() )) {
								playground.die();
								break;
							}
						}
					}
				}
			playground.lock.unlock();
			
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
