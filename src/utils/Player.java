package utils;

/**
 * Hilfsklasse für Table-Model.
 * 1 Playerobjekt = 1 Zeile im Highscore. Enthällt Namen und Zeit des Spielers
 */
public class Player implements Comparable<Player> {

	private String name;
	public int time;
	
	public Player(String name, int time) {
		this.name = name;
		this.time = time;
	}
	
	public String getName() {
		return this.name;
	}
	
	public int getTime() {
		return this.time;
	}

	@Override
	public int compareTo(Player o) {
		return (this.time < o.time ) ? -1 : (this.time > o.time )? 1 : 0;
	}
	
}
