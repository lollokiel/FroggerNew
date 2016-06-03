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
		this.addMoveObject(new Car(s.CAR_R,120,this));
		this.start();
		this.playground = playground;
	}

	@Override
	public void run() {
		while(this.playground.meeple.isAlive()) {
			System.out.println("Add");
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
