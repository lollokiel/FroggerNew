package objects;

import java.awt.image.BufferedImage;

import utils.FieldKoordinate;

public class Pit extends FieldObject {

	public Pit(FieldKoordinate koordinate, BufferedImage background) {
		this.koordinate = koordinate;
		this.backgorund = background;
	}
	
}
