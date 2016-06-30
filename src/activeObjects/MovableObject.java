package activeObjects;

import java.awt.image.BufferedImage;

import activeRows.ActiveRow;
import utils.Meeple;

/**
 * Umfasst alle sich bewegenden Objekte, die in einem Intervall t durch die Klasse
 * MoveObjects verschoben werden sollen
 * Beinhaltet x-Koordinate (y-Koordinate durch ActiveRow definiert), das Hintergrundbild,
 * die Reihe auf dem es fährt und ein Verweis auf die Spielfigur, wenn diese mit diesem
 * Objekt fahren soll. 
 */
public class MovableObject {
	
	protected int 			x;
	protected BufferedImage background;
	protected ActiveRow 	row;
	protected Meeple		meepleOn;
	
	public MovableObject(BufferedImage image, int x, ActiveRow row) {
		this.background = image;
		this.x = x;
		this.row = row;
	}
	
	/**
	 * Gibt die x-Koordinate des Objektes zurück.
	 * @return die x-Koordinate des Objektes
	 */
	public int getX() {
		return this.x;
	}
		
	/**
	 * Gibt das Bildobjekt des Objektes zurück.
	 * @return die Bilddatei des Objektes
	 */
	public BufferedImage getBackground() {
		return this.background;
	}
	
	/**
	 * Gibt die Breite des Objektes zurück.
	 * @return die Breite des Objektes
	 */
	public int getWidth() {
		return this.background.getWidth();
	}
	
	/**
	 * Gibt die Höhe des Objektes zurück
	 * @return die Höhe des Objektes
	 */
	public int getHeight() {
		return this.background.getHeight();
	}
	
	/**
	 * Gibt die Reihe zurück, in der das Objekt liegt.
	 * @return die Reihe des Objektes
	 */
	public ActiveRow getRow() {
		return this.row;
	}
	
	/**
	 * Gibt das Spielfigurenobjekt zurück, das sich ggf. auf dem Objekt befindet
	 * @return Spielfigurenobjekt, das auf dem Objekt ist
	 */
	public Meeple getMeeple() {
		return this.meepleOn;
	}
	
	/**
	 * Verändert das Hintergrundbild des Objektes.
	 * @param background als BufferedImage
	 */
	public void setBackground(BufferedImage background) {
		this.background = background;
	}
	
	/**
	 * Verändert die Spielfigur, die auf dem Objekt ist.
	 * @param meeple vom Typ Meeple; Spielfigur, die mit dem Objekt gehen soll
	 */
	public void setMeepleOn(Meeple meeple) {
		this.meepleOn = meeple;
	}
	
	/**
	 * Erhöht die x-Koordinate
	 * @param dif int Wert, um den sich die x-Koordinate verändert
	 */
	public void raiseX(int dif) {
		this.x += dif;
	}
}
