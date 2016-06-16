package activeObjects;

import java.awt.image.BufferedImage;

import utils.FieldKoordinate;

public class Tree extends FieldObject {

	public Tree(FieldKoordinate koordinate, BufferedImage background) {
		this.koordinate = koordinate;
		this.backgorund = background;
		this.entranceable = false;
	}
	
}
