package utils;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import frames.Game;
import objects.FieldObject;
import objects.Pit;
import objects.River;
import objects.Street;
import objects.Tree;
import objects.Waterlily;
import settings.Settings;

public class Level {

	
	private ArrayList<BufferedImage> 	rows 		= new ArrayList<BufferedImage>();
	private ArrayList<Tree>		 		trees		= new ArrayList<Tree>();
	private ArrayList<Pit>		 		pits		= new ArrayList<Pit>();
	private ArrayList<Waterlily> 		waterlilies	= new ArrayList<Waterlily>();
	private ArrayList<Street> 			streets 	= new ArrayList<Street>();
	private ArrayList<River> 			rivers 		= new ArrayList<River>();
	private Settings 					settings 	= new Settings();
	
	public Game gameframe;
	
	
	public Level(String sPathBackgroundStructure, String sPathObjects, Game gameframe) {
		
		this.gameframe = gameframe;
		
		/*
		 * Read Background Structure
		 */
		String[] backgroundStructureLines = Utils.readFile(sPathBackgroundStructure).split("\n");
		int i = 0;
		for(String rowString : backgroundStructureLines) {
			String[] rowSettings = rowString.split("\\s");
			switch(rowSettings[0]) {
				case "2": this.addRiver(rowSettings, i); break;
				case "3": this.addStreet(rowSettings, i); break;
				default: rows.add(settings.GRASS); break;
			}
			i++;
		}
		
		/*
		 * Read Objects
		 */
		String[] objectStructureLines = Utils.readFile(sPathObjects).split("\n");
		
		for(int row = 0; row < objectStructureLines.length; row++) {
			String[] rowStructure = objectStructureLines[row].split(",");
			for(int col = 0; col < rowStructure.length; col++) {
				int objectType = Integer.parseInt(rowStructure[col]);
				if(objectType == 1) {
					trees.add(new Tree(new FieldKoordinate(col, row), settings.TREE));
				} else
				if(objectType == 2) {
					pits.add(new Pit(new FieldKoordinate(col, row), settings.PIT));
				} else
				if(objectType == 3) {
					waterlilies.add(new Waterlily(new FieldKoordinate(col, row), settings.WATERLILY));
				}
			}
		}
		
	}
	
	private void addRiver(String[] rowSettings, int row) {
		if(rowSettings.length == 4) {
			rows.add(settings.WATER); 
			
			int speed = Integer.parseInt(rowSettings[1]);
			int direction = Integer.parseInt(rowSettings[2]);
					
			rivers.add(new River(direction, speed, row));
		}
	}
	
	private void addStreet(String[] rowSettings, int row) {
		if(rowSettings.length == 4) {
			rows.add(settings.STREET); 
			
			int speed = Integer.parseInt(rowSettings[1]);
			int direction = Integer.parseInt(rowSettings[2]);
					
			Street streetNew = new Street(direction, speed, row, this.gameframe.playground);
			streets.add(streetNew);
		}
	}
	
	public ArrayList<BufferedImage> getBackgroundStructure() {
		return rows;
	}
	
	public ArrayList<FieldObject> getObjectStructure() {
		ArrayList<FieldObject> returnObjects = new ArrayList<FieldObject>();
		
		returnObjects.addAll(trees);
		returnObjects.addAll(waterlilies);
		returnObjects.addAll(pits);
		
		return returnObjects;
	}
	
	public ArrayList<ActiveRow> getRows() {
		ArrayList<ActiveRow> rows = new ArrayList<ActiveRow>();
		rows.addAll(streets);
		rows.addAll(rivers);
		return rows;
	}
	
}
