package activeObjects;

import java.awt.image.BufferedImage;

import activeRows.ActiveRow;
import game.Meeple;

public class MovableObject {
	
	protected int 			x;
	protected BufferedImage background;
	protected ActiveRow 	row;
	private Meeple 			meepleOn;
	
	public MovableObject(BufferedImage image, int x, ActiveRow row) {
		this.background = image;
		this.x = x;
		this.row = row;
	}
	
	/*
	 * Getter
	 */
	public int getX() {
		return this.x;
	}
		
	public BufferedImage getBackground() {
		return this.background;
	}
	
	public int getWidth() {
		return this.background.getWidth();
	}
	
	public int getHeight() {
		return this.background.getHeight();
	}
	
	public ActiveRow getRow() {
		return this.row;
	}
	
	public Meeple getMeeple() {
		return this.meepleOn;
	}
	
	/*
	 * Setter
	 */
	public void setBackground(BufferedImage background) {
		this.background = background;
	}
	public void setMeepleOn(Meeple meeple) {
		this.meepleOn = meeple;
	}
	
	/*
	 * Other
	 */
	public void raiseX(int dif) {
		this.x += dif;
	}
}
