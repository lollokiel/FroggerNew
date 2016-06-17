package utils;

public class Player implements Comparable<Player> {

	private String name;
	public int time;
	
	public Player(String name, int time) {
		this.name = name;
		this.time = time;
	}
	
	public String getName() {
		return name;
	}
	
	public int getTime() {
		return time;
	}

	@Override
	public int compareTo(Player o) {
		return (time < o.time ) ? -1 : (time > o.time )? 1 : 0;
	}
	
}
