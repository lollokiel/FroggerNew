package objects;


import frames.Playground;
import settings.Settings;
import utils.ActiveRow;

public class Street extends ActiveRow {

	private Settings s = new Settings();
	
	public Street(int direction, int speed, int row, Playground playground) {
		this.setDirection(direction);
		this.setSpeed(speed);
		this.setRow(row);
		Car newCar1 = null;
		Car newCar2 = null;
		if(this.getDirection() == 1) {
			newCar1 = new Car(s.CARS_R.get((int)(Math.random()*s.CARS_R.size())),Settings.FIELDSIZE*Settings.COLS/3,this);
			newCar2 = new Car(s.CARS_R.get((int)(Math.random()*s.CARS_R.size())),(Settings.FIELDSIZE*Settings.COLS*2)/3,this);
		} else {
			newCar1 = new Car(s.CARS_L.get((int)(Math.random()*s.CARS_L.size())),Settings.FIELDSIZE*Settings.COLS/3,this);
			newCar2 = new Car(s.CARS_L.get((int)(Math.random()*s.CARS_L.size())),(Settings.FIELDSIZE*Settings.COLS*2)/3,this);
		}
		this.addMoveObject(newCar1);
		this.addMoveObject(newCar2);
		this.start();
		this.playground = playground;
	}

	@Override
	public void run() {
		while(this.playground.getMeeple().isAlive()) {
			playground.lock.lock();
				Car newCar = null;
				if(this.getDirection() == 1) {
					newCar = new Car(s.CARS_R.get((int)(Math.random()*s.CARS_R.size())),-70,this);
				} else {
					newCar = new Car(s.CARS_L.get((int)(Math.random()*s.CARS_L.size())),Settings.FIELDSIZE*Settings.COLS,this);
				}
				this.addMoveObject(newCar);
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
