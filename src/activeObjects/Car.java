package activeObjects;

import java.awt.image.BufferedImage;

import activeRows.ActiveRow;

public class Car extends MoveableObject {

	public Car(BufferedImage image, int x, ActiveRow row) {
		this.background = image;
		this.x = x;
		this.row = row;
	}
	
}
