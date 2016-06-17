package activeRows;

import java.util.ArrayList;

import activeObjects.MovableObject;
import frames.Playground;
import utils.Settings;

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
	
	/*
	 * Other
	 */
	public void addMovableObject(MovableObject object) {
		this.movableObjects.add(object);
	}
	
	public void removeMovableObject(MovableObject object) {
		this.movableObjects.remove(object);
	}
	
	public ArrayList<MovableObject> getMoveableObjects() {
		return movableObjects;
	}
	
}
