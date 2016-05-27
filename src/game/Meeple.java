package game;

import java.awt.image.BufferedImage;

import utils.Koordinate;
import utils.Settings;

public class Meeple {

	private int x;
	private int y;
	private BufferedImage image;
	
	public Meeple (int col, int row, BufferedImage image) {
		this.x = Settings.FIELDSIZE * col;
		this.y = Settings.FIELDSIZE * row;
		this.image = image;
	}
	
	public boolean moveField(int direction) {
		
		// TODO!
		// Check if Objekt
		boolean moved = false;
		if(direction == 37) { //Links
			int newX = x-Settings.FIELDSIZE;
			if(newX >= 0 && newX < Settings.COLS*Settings.FIELDSIZE) {
				this.x = newX;
				moved = true;
			}
		} else
		if(direction== 38) { //Oben
			int newY = y-Settings.FIELDSIZE;
			if(newY >= 0 && newY < Settings.ROWS*Settings.FIELDSIZE) {
				this.y = newY;
				moved = true;
			}			
		} else
		if(direction== 39) { //Rechts
			int newX = x+Settings.FIELDSIZE;
			if(newX >= 0 && newX < Settings.COLS*Settings.FIELDSIZE) {
				this.x = newX;
				moved = true;
			}		
		}  else
		if(direction== 40) { //Unten
			int newY = y+Settings.FIELDSIZE;
			if(newY >= 0 && newY < Settings.ROWS*Settings.FIELDSIZE) {
				this.y = newY;
				moved = true;
			}			
		}
		return moved;
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
}
