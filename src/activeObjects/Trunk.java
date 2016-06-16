package activeObjects;

import java.awt.image.BufferedImage;

import activeRows.ActiveRow;

public class Trunk extends MoveableObject {

	public Trunk(BufferedImage image, int x, ActiveRow row) {
		this.background = image;
		this.x = x;
		this.row = row;
	}
	
}
