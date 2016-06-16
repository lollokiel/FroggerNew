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
		while(playground.getMeeple().isAlive()) {
			playground.lock.lock();
				for(ActiveRow row : playground.getRows()) {
					for(MoveableObject obj : row.getMoveableObjects()) {
						if(obj.getMeeple() != null) {
							obj.getMeeple().raiseX(row.getSpeed()*row.getDirection());
							if(obj.getMeeple().getX() < 0 || obj.getMeeple().getX()+Settings.FIELDSIZE > Settings.COLS*Settings.FIELDSIZE) {
								playground.die();
							}
						}
						obj.raiseX(row.getSpeed()*row.getDirection());
					}
				}
			playground.lock.unlock();
			
			playground.repaint();
			
			// Auf Kollision prüfen
			playground.lock.lock();
				for(ActiveRow row : playground.getRows()) {
					if(row.getClass() == Street.class && row.getRow() == playground.getMeeple().getY()/Settings.FIELDSIZE) {
						for(MoveableObject obj : row.getMoveableObjects()) {
							if(!(playground.getMeeple().getX() > obj.getX()+obj.getWidth() || playground.getMeeple().getX() + Settings.FIELDSIZE < obj.getX() )) {

								System.out.println("due");
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
