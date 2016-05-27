package objects;

import utils.ActiveRow;

public class River extends ActiveRow {

	public River(int direction, int speed, int row) {
		this.setDirection(direction);
		this.setSpeed(speed);
		this.setRow(row);
	}
}
