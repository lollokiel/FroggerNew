package objects;

public class Player {

	private String sName;
	private int iPoints;
	
	public Player(String sName, int iPoints) {
		this.sName = sName;
		this.iPoints = iPoints;
	}
	
	public String getsName() {
		return sName;
	}
	
	public int getiPoints() {
		return iPoints;
	}
	
}
