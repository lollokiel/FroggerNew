package objects;

import java.awt.image.BufferedImage;

import utils.FieldKoordinate;

public abstract class FieldObject {
	
	protected FieldKoordinate koordinate;
	protected BufferedImage backgorund;
	protected boolean entranceable = true;
	
	
	public BufferedImage getBackground() {
		return this.backgorund;
	}
	
	public FieldKoordinate getKoordinate() {
		return this.koordinate;
	}
	
	public int getRow() {
		return this.koordinate.getRow();
	}
	
	public int getCol() {
		return this.koordinate.getCol();
	}
	
	public boolean isEntranceable() {
		return entranceable;
	}
	
	//public abstract boolean moveIn();

}
