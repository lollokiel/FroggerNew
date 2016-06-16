package activeObjects;

import java.awt.image.BufferedImage;

import activeRows.ActiveRow;
import game.Meeple;

public abstract class MoveableObject {
	
	protected int x;
	protected BufferedImage background;
	protected ActiveRow row;
	private Meeple meeple = null;
	
	public int getX() {
		return x;
	}
	
	public void raiseX(int dif) {
		this.x += dif;
	}
	
	public void setBackground(BufferedImage background) {
		this.background = background;
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

	public void setMeeple(Meeple meeple) {
		this.meeple = meeple;
	}
	
	public Meeple getMeeple() {
		return meeple;
	}
}
