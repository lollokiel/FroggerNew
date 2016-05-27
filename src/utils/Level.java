package utils;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

import objects.River;
import objects.Street;

public class Level {

	public static void main(String[] args) {
		new Level("/level/backgroundStructure/1.txt", "/level/1.txt");
	}
	
	private ArrayList<BufferedImage> 	rows 		= new ArrayList<BufferedImage>();
	private ArrayList<MyObject> 		objects		= new ArrayList<MyObject>();
	private ArrayList<Street> 			streets 	= new ArrayList<Street>();
	private ArrayList<River> 			rivers 		= new ArrayList<River>();
	private Settings 					settings 	= new Settings();
	
	public Level(String sPathBackgroundStructure, String sPathObjects) {

		String[] backgroundStructureLines = Utils.readFile(sPathBackgroundStructure).split("\n");
		int i = 1;
		for(String rowString : backgroundStructureLines) {
			String[] rowSettings = rowString.split("\\s");
			switch(rowSettings[0]) {
				case "2": this.addRiver(rowSettings, i); break;
				case "3": this.addStreet(rowSettings, i); break;
				default: rows.add(settings.GRASS); break;
			}
			i++;
		}
		
		System.out.println(rows);
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
					
			streets.add(new Street(direction, speed, row));
		}
	}
	
	public ArrayList<BufferedImage> getBackgroundStructure() {
		return rows;
	}
	
}
