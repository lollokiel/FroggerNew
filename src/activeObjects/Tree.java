package activeObjects;

import java.awt.image.BufferedImage;

import utils.FieldKoordinate;

/**
 * Baum als Hindernis auf dem Spielfeld. Nicht betretbares FieldObjekt.
 */
public class Tree extends FieldObject {

	public Tree(FieldKoordinate koordinate, BufferedImage background) {
		this.koordinate = koordinate;
		this.backgorund = background;
		this.entranceable = false;
	}
	
}
