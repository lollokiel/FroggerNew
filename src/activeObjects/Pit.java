package activeObjects;

import java.awt.image.BufferedImage;

import utils.FieldKoordinate;

/**
 * Loch. 
 * Unsichtbares Objekt auf dem Spielfeld, bei dem bei Eintreten das Spiel
 * beendet ist. Steigt die Schwierigkeit im Levelverlauf.
 * Erweitert FieldObjekt
 */
public class Pit extends FieldObject {

	public Pit(FieldKoordinate koordinate, BufferedImage background) {
		this.koordinate = koordinate;
		this.backgorund = background;
	}
	
}
