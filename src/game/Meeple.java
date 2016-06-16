package game;

import java.awt.image.BufferedImage;

import frames.Playground;
import objects.FieldObject;
import objects.MoveableObject;
import objects.Trunk;
import settings.Settings;
import utils.FieldKoordinate;
import utils.Koordinate;

public class Meeple {

	private int x;
	private int y;
	private BufferedImage image;
	private boolean alive = true;
	private Playground playground;
	private MoveableObject moveableObject = null;
	
	public Meeple (int col, int row, BufferedImage image, Playground playground) {
		this.x = Settings.FIELDSIZE * col;
		this.y = Settings.FIELDSIZE * row;
		this.image = image;
		this.playground = playground;
	}
	
	public boolean moveField(int direction) {
		if(this.isAlive() && this.playground.getCountdown().seconds < 0) {
			
			int aktX = 0;
			if(this.getMoveableObject() != null) {
				this.getMoveableObject().setMeeple(null);
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
					for(FieldObject object : playground.getObjectStructure()) {  	// Überprüft alle Objekte auf begebarkeit
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
	
	public BufferedImage getImage() {
		return this.image;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public boolean isAlive() {
		return alive;
	}
	
	public void setAlive(boolean alive) {
		this.alive = alive;
	}
	
	public int getMiddleX() {
		return this.x + 17;
	}
	
	public void setMoveableObject(MoveableObject object) {
		this.moveableObject = object;
		if(this.moveableObject != null)
			this.moveableObject.setMeeple(this);
	}
	
	public MoveableObject getMoveableObject() {
		return this.moveableObject;
	}
	
	public void raiseX(int dif) {
		this.x += dif;
	}
	
	public int getRow() {
		return this.getY() / Settings.FIELDSIZE;
	}
	
	public void moveTo(int x, int y) {
		this.x = x;
		this.y = y;		
	}
	
	public FieldKoordinate getFieldkoordinate() {
		return new FieldKoordinate(this.getX()/Settings.FIELDSIZE, this.getRow());
	}
}
