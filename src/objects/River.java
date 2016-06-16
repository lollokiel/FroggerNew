package objects;

import frames.Playground;
import settings.Settings;
import utils.ActiveRow;

public class River extends ActiveRow {

	private Settings s = new Settings();
	
	public River(int direction, int speed, int row, Playground playground) {
		this.setDirection(direction);
		this.setSpeed(speed);
		this.setRow(row);
		this.addMoveObject(new Trunk(s.TRUNK,120,this));
		this.start();
		this.playground = playground;
	}
	
	@Override
	public void run() {
		while(this.playground.getMeeple().isAlive()) {
			playground.lock.lock();
				Trunk newTrunk = null;
				if(this.getDirection() == 1) {
					newTrunk = new Trunk(s.TRUNK,-70,this);
				} else {
					newTrunk = new Trunk(s.TRUNK,Settings.FIELDSIZE*Settings.COLS,this);
				}
				this.addMoveObject(newTrunk);
			playground.lock.unlock();
			try {

				int t = 2000 + (int)(Math.random()*1000);
				Thread.sleep(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
