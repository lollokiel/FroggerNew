package utils;
import java.awt.image.BufferedImage;

public class Figure {

	private String sName;
	private BufferedImage image;
	
	public Figure(String sName, BufferedImage image) {
		this.sName = sName;
		this.image = image;
	}
	
	@Override
	public String toString() {
		return this.sName;
	}
	
	public BufferedImage getImage() {
		return this.image;
	}

}
