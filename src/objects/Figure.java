package objects;
import java.awt.Component;

public class Figure extends Component {
	
	private static final long serialVersionUID = 1L;
	private String sName;
	
	public Figure(String sName) {
		this.sName = sName;
	}
	
	@Override
	public String toString() {
		return sName+" test";
	}

}
