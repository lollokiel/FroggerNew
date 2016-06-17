package activeRows;


import activeObjects.MovableObject;
import frames.Playground;
import utils.Settings;

public class Street extends ActiveRow {
	
	public Street(int direction, int speed, int row, int wdhSpeed, Playground playground) {
		this.setDirection(direction);
		this.setSpeed(speed);
		this.setRow(row);
		this.setWdhSpeed(wdhSpeed);
		this.playground = playground;
		this.settings = playground.getGameFrame().getMainFrame().getSettings();
		
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
		this.start();
	}

	@Override
	public void run() {
		while(this.playground.getMeeple().isAlive()) {
			this.playground.getLock().lock();
			
				MovableObject newCar = null;
				if(this.getDirection() == 1) {
					newCar = new MovableObject(this.settings.CARS_R.get((int)(Math.random()*this.settings.CARS_R.size())),-70,this);
				} else {
					newCar = new MovableObject(this.settings.CARS_L.get((int)(Math.random()*this.settings.CARS_L.size())),Settings.PLAYGROUND_WIDTH,this);
				}
				this.addMovableObject(newCar);
				
			this.playground.getLock().unlock();
			
			try {

				int t = (2000 + (int)(Math.random()*1000))*(500*getWdhSpeed());
				t = 4000/getWdhSpeed()+(int)(Math.random()*5000);
				Thread.sleep(t);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
