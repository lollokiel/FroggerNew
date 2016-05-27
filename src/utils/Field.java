package utils;
import java.awt.image.BufferedImage;

public class Field {

	private BufferedImage background;
	private String bezeichnung;
	
	public Field(BufferedImage background, String bezeichnung) {
		super();
		this.background = background;
		this.bezeichnung = bezeichnung;
	}

	public BufferedImage getBackground() {
		return background;
	}

	public void setBackground(BufferedImage background) {
		this.background = background;
	}

	public String getBezeichnung() {
		return bezeichnung;
	}

	public void setBezeichnung(String bezeichnung) {
		this.bezeichnung = bezeichnung;
	}
	
	
	
	
}
