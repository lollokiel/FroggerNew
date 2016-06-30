package utils;

/**
 * Feld-Koordinate, oder auch "grobe" Koordinate
 * Richtet sich im Gegensatz zur Klasse "Koordinate" nicht nach genauen Pixeln,
 * sondern nach Kacheln auf dem Spielfeld. 
 */
public class FieldKoordinate {

	private int row;
	private int col;
	
	/*
	 * Konstruktoren
	 */
	public FieldKoordinate(int col, int row) {
		this.row = row;
		this.col = col;
	}
	
	// Konstruktur um aus einer Koordinate zu erstellen; Rundet ggf. ab
	public FieldKoordinate(Koordinate koordinate) {
		this.row = (int)(koordinate.getY() / Settings.FIELDSIZE);
		this.col = (int)(koordinate.getX() / Settings.FIELDSIZE);
	}
	

	/*
	 * Getter
	 */
	public int getRow() {
		return this.row;
	}
	
	public int getCol() {
		return this.col;
	}
	
	/*
	 * Setter
	 */
	public void setRow( int row ) {
		this.row = row;
	}
	
	public void setCol( int col ) {
		this.col = col;
	}
	
	/*
	 * Other
	 */
	
	/**
	 * Wandelt von "grober" in "feine" Koordinate um
	 */
	public Koordinate zuKoordinate() {
		return new Koordinate(this.col*Settings.FIELDSIZE, this.row * Settings.FIELDSIZE );
	}
	
	/**
	 * Prüft, ob übergebene Koordinate gleiche Reihen und Zeilen-Werte hat.
	 * @param fieldKoordinate Zu vergleichende Koordinate
	 * @return true, wenn gleich; false, wenn unterschiedlich
	 */
	public boolean isSame(FieldKoordinate fieldKoordinate) {
		if(fieldKoordinate.getCol() == this.getCol()) {
			if(fieldKoordinate.getRow() == this.getRow()) {
				return true;
			}
		}
		return false;
	}
}

