package threads;

import frames.Playground;
import objects.MoveableObject;
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
				for(ActiveRow row : playground.gameFrame.level.getRows()) {
					for(MoveableObject obj : row.getMoveableObjects()) {
						obj.raiseX(row.getSpeed()*row.getDirection());
					}
				}
			playground.lock.unlock();
			
			playground.repaint();
			try {
				Thread.sleep(25);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
