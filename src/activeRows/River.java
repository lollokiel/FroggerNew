package activeRows;

import activeObjects.MovableObject;
import frames.Playground;
import utils.Settings;

public class River extends ActiveRow {
	
	public River(int direction, int speed, int row, int wdhSpeed, Playground playground) {
		this.playground = playground;
		this.settings = playground.getGameFrame().getMainFrame().getSettings();
		this.setDirection(direction);
		this.setWdhSpeed(wdhSpeed);
		this.setSpeed(speed);
		this.setRow(row);
		this.addMovableObject(new MovableObject(settings.TRUNK,120,this));
		this.start();
	}
	
	@Override
	public void run() {
		while(this.playground.getMeeple().isAlive()) {
			this.playground.getLock().lock();
			
				MovableObject newTrunk;
				if(this.getDirection() == 1) {
					newTrunk = new MovableObject(this.settings.TRUNK,-70,this);
				} else {
					newTrunk = new MovableObject(this.settings.TRUNK, Settings.PLAYGROUND_WIDTH, this);
				}
				this.addMovableObject(newTrunk);
				
				this.playground.getLock().unlock();
			
			try {
				int t = 2000 + (int)(Math.random()*1000);

				t = 4000/getWdhSpeed()+(int)(Math.random()*5000);
				Thread.sleep(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
}
