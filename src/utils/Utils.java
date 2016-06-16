package utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {

	/* 
	 * Erstellt ein BufferedImage von einem übergebenden Pfad und gibt dieses zurück
	 */
	public static BufferedImage formTile(String path, int width, int height) throws IOException {
		return ImageIO.read(Utils.class.getResource(path)).getSubimage(0, 0, width, height);
	}
	
	/*
	 * Ließt eine Datei ein und gibt diese als String zurück
	 */
	public static String readFile(String path) {
		
		StringBuilder builder = new StringBuilder();

		FileReader file = null;
		try {
			file = new FileReader(Utils.class.getResource(path).getFile());
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
		if(file != null) {
			try {
				BufferedReader br = new BufferedReader(file);
				String line;
				while((line = br.readLine()) != null) {
					builder.append(line + "\n");
				}
				br.close();
			} catch(IOException e) {
				e.printStackTrace();
			}
		}
		return builder.toString();
	}	
	
	/*
	 * Gibt einen Timer aus Minuten und Sekunden abhängig einer übergegebenen Sekundenzahl zurück
	 */
	public static String getTime(int seconds) {
		return (int)(seconds/60)+":"+addZero(seconds%60);
	}
	
	public static String addZero(int i) {
		if(i < 10) {
			return "0"+i;
		}
		return i+"";
	}
	
	/*
	 * Check Level OK
	 */
	public static boolean checkLevel(int level) {
		if( new File("res/level/backgroundStructure/"+level+".txt").exists() ) {
			if( new File("res/level/objectStructure/"+level+".txt").exists() ) {
				return true;
			}
		}
		return false;
	}
	
	
}
