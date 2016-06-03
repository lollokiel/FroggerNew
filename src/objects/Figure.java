package objects;
import java.awt.Component;
import java.awt.image.BufferedImage;

public class Figure extends Component {
	
	private static final long serialVersionUID = 1L;
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
