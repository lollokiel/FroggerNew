package utils;

import java.util.ArrayList;

import frames.Playground;
import objects.MoveableObject;

public abstract class ActiveRow extends Thread {
	
	private int speed;
	private int direction;
	private int row;
	protected Playground playground;
	private ArrayList<MoveableObject> moveObjects = new ArrayList<MoveableObject>();
	
	public int getSpeed() {
		return speed;
	}
	
	public void setSpeed(int speed) {
		this.speed = speed;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int direction) {
		this.direction = direction;
	}
	
	public int getRow() {
		return row;
	}
	
	public void setRow(int row) {
		this.row = row;
	}	
	
	public void addMoveObject(MoveableObject object) {
		this.moveObjects.add(object);
	}
	
	public void removeMoveObject(MoveableObject object) {
		this.moveObjects.remove(object);
	}
	
	public ArrayList<MoveableObject> getMoveableObjects() {
		return moveObjects;
	}
	
}
