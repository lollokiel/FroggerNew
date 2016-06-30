package activeRows;

import java.util.ArrayList;

import activeObjects.MovableObject;
import frames.Playground;
import utils.Settings;

/**
 * Erweitert Thread
 * Abstrakte Klasse für Straße und Fluss, bildet Grundgerüst für bewegende Objekte
 * Beinhaltet
 * - Geschwindigkeit der Objekte
 * - Richtung der Objekte
 * - Lage der Reihe
 * - Rate, wie oft Objekte hinzugefügt werden sollen
 * - Liste mit sich bewegenden Objekten
 */
public abstract class ActiveRow extends Thread {
	

	protected Settings settings;
	protected Playground playground;
	
	private int speed;
	private int direction;
	private int row;
	private int wdhSpeed;
	
	private ArrayList<MovableObject> movableObjects = new ArrayList<MovableObject>();
	
	/*
	 * Getter
	 */
	public int getSpeed() {
		return this.speed;
	}
	
	public int getDirection() {
		return this.direction;
	}
	
	public int getRow() {
		return this.row;
	}
	
	public int getWdhSpeed() {
		return this.wdhSpeed;
	}
	
	/*
	 * Setter
	 */	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public void setRow(int row) {
		this.row = row;
	}	
	
	public void setWdhSpeed(int wdhSpeed) {
		this.wdhSpeed = wdhSpeed;
	}
	
	/**
	 * Fügt ein bewegendes Objekt zu der Liste der bewegenden Objekte hinzu.
	 * @param object Bewegendes Objekt, welches hinzugefügt werden soll
	 */
	public void addMovableObject(MovableObject object) {
		this.movableObjects.add(object);
	}
	
	/**
	 * Löscht ein bewegendes Objekt aus der Liste der bewegenden Objekte.
	 * @param object Das zu löschende bewegende Objekt
	 */
	public void removeMovableObject(MovableObject object) {
		this.movableObjects.remove(object);
	}
	
	public ArrayList<MovableObject> getMoveableObjects() {
		return this.movableObjects;
	}
	
}
