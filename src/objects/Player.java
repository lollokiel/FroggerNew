package objects;

public class Player implements Comparable<Player> {

	private String sName;
	public int iPoints;
	
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


	@Override
	public int compareTo(Player o) {

		// TODO Auto-generated method stub
		return (iPoints < o.iPoints ) ? -1 : (iPoints > o.iPoints )? 1 : 0;
	}
	
}
