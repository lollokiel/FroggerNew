package utils;

import java.awt.image.BufferedImage;

import activeObjects.FieldObject;
import activeObjects.MovableObject;
import frames.Playground;

public class Meeple {

	private int x;
	private int y;
	private BufferedImage image;
	private boolean alive = true;
	private Playground playground;
	private MovableObject moveableObject = null;
	
	public Meeple (int col, int row, BufferedImage image, Playground playground) {
		this.x = Settings.FIELDSIZE * col;
		this.y = Settings.FIELDSIZE * row;
		this.image = image;
		this.playground = playground;
	}
	
	/**
	 * Bewegt die Spielfigur um ein Feld in die angegebene Richtung.
	 * @param direction Richtung, in die Spielfigur bewegt werden soll.
	 * @return
	 * Gibt true zurück, wenn Umsetzung erfolgreich, false, wenn Umsetzung gescheitert ist.
	 */
	public boolean moveField(int direction) {
		if(this.isAlive() && this.playground.getCountdown().seconds < 0) {
			
			int aktX = 0;
			if(this.getMoveableObject() != null) {
				this.getMoveableObject().setMeepleOn(null);
				this.setMoveableObject(null);
				aktX = this.getMiddleX();
			} else { 
				aktX = this.getX();
			}
			
			FieldKoordinate fieldKoord = new FieldKoordinate(new Koordinate(aktX, this.y));
			
			boolean moved = false;
			
			if(direction == 37) { //Links
				fieldKoord.setCol(fieldKoord.getCol()-1);
			} else
			if(direction== 38) { //Oben
				fieldKoord.setRow(fieldKoord.getRow()-1);		
			} else
			if(direction== 39) { //Rechts
				fieldKoord.setCol(fieldKoord.getCol()+1);		
			}  else
			if(direction== 40) { //Unten
				fieldKoord.setRow(fieldKoord.getRow()+1);		
			}
			
			if(fieldKoord.getCol() >= 0 && fieldKoord.getCol() < Settings.COLS) {      		// Überprüft ob neue Koordinate 
				if(fieldKoord.getRow() >= 0 && fieldKoord.getRow() < Settings.ROWS) {		// im Spielfeld liegt
					
					boolean entranceable = true;
					for(FieldObject object : this.playground.getObjectStructure()) {  	// Überprüft alle Objekte auf begebarkeit
						if(object.getFieldKoordinate().isSame(fieldKoord)) {
							entranceable = object.isEntranceable();
							break;
						}
					}
					if(entranceable) {
						this.x = fieldKoord.getCol() * Settings.FIELDSIZE;
						this.y = fieldKoord.getRow() * Settings.FIELDSIZE;
						moved = true;
					}
				}
			}
			return moved;
		}
		return false;
	}
	
	/**
	 * @return Gibt das Bildobjekt der Spielfigur zurück
	 */
	public BufferedImage getImage() {
		return this.image;
	}
	
	/**
	 * @return Gibt x-Koordinate der Spielfigur zurück
	 */
	public int getX() {
		return this.x;
	}
	
	/**
	 * @return Gibt y-Koordinate der Spielfigur zurück
	 */
	public int getY() {
		return this.y;
	}
	
	/**
	 * @return Gibt true zurück, wenn Spielfigur am Leben ist, false, wenn sie nicht am Leben ist.
	 */
	public boolean isAlive() {
		return this.alive;
	}
	
	/**
	 * Ändert den Lebenszustand der Spielfigur
	 * @param alive boolischer Wert
	 */
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	/**
	 * @return Gibt die x-Koordinate der Mitte der Spielfigur zurück
	 */
	public int getMiddleX() {
		return this.x + 17;
	}
	
	/**
	 * Weist der Spielfigur ein bewegendes Objekt zu, mit dem sich die Spielfigur bewegen soll.
	 * @param object Das Objekt, mit dem sich die Spielfigur mitbewegen soll
	 */
	public void setMoveableObject(MovableObject object) {
		this.moveableObject = object;
		if(this.moveableObject != null)
			this.moveableObject.setMeepleOn(this);
	}

	/**
	 * @return Gibt das bewegende Objekt zurück, das der Spielfigur zugewiesen ist.
	 */
	public MovableObject getMoveableObject() {
		return this.moveableObject;
	}
	
	/**
	 * Erhöht die x-Koordinate um 
	 * @param dif
	 */
	public void raiseX(int dif) {
		this.x += dif;
	}
	
	/**
	 * @return Gibt die Reihe zurück, in der sich die Spielfigur befindet
	 */
	public int getRow() {
		return this.getY() / Settings.FIELDSIZE;
	}
	
	
	/**
	 * Zum bewegen zu einer bestimmten Koordinate
	 * @param x
	 * @param y
	 */
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	/**
	 * 
	 * @return Grobe Koordinate
	 */
	public FieldKoordinate getFieldkoordinate() {
		return new FieldKoordinate(this.getX() / Settings.FIELDSIZE, this.getRow());
	}
}
