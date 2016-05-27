package objects;

import utils.ActiveRow;

public class Street extends ActiveRow {

	public Street(int direction, int speed, int row) {
		this.setDirection(direction);
		this.setSpeed(speed);
		this.setRow(row);
	}
}
