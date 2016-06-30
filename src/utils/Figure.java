package utils;
import java.awt.image.BufferedImage;

/**
 * Hilfsklasse zum Realisieren der Auswahlliste der möglichen Spielfiguren
 * Elemente einer JComboBox im Menüfenster
 */
public class Figure {

	private String sName;
	private BufferedImage image;
	
	public Figure(String sName, BufferedImage image) {
		this.sName = sName;
		this.image = image;
	}
	
	// Damit wird in der ComboBox der Name als Auswahl angezeigt
	@Override
	public String toString() {
		return this.sName;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}

}
