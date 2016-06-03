package utils;

import settings.Settings;

public class FieldKoordinate {

	private int row;
	private int col;
	
	public FieldKoordinate(int col, int row) {
		this.row = row;
		this.col = col;
	}
	
	public FieldKoordinate(Koordinate koordinate) {
		this.row = (int)(koordinate.getY() / Settings.FIELDSIZE);
		this.col = (int)(koordinate.getX() / Settings.FIELDSIZE);
	}
	
	public Koordinate zuKoordinate() {
		return new Koordinate(this.col*Settings.FIELDSIZE, this.row * Settings.FIELDSIZE );
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	public void setRow( int row ) {
		this.row = row;
	}
	
	public void setCol( int col ) {
		this.col = col;
	}

	public boolean isSame(FieldKoordinate fieldKoordinate) {
		if(fieldKoordinate.getCol() == this.getCol()) 
			if(fieldKoordinate.getRow() == this.getRow()) 
				return true;
	
		return false;
	}
}

