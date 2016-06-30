package utils;

/**
 * "feine" Koordinate, exakte x- und y- Koordinate
 */
public class Koordinate {

	private int x;
	private int y;
	
	public Koordinate(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	/*
	 * Getter
	 */
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
}
