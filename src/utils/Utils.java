package utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Utils {

	/* 
	 * Erstellt ein BufferedImage von einem übergebenden Pfad und gibt dieses zurück
	 */
	public static BufferedImage formTile(String path) throws IOException {
		return ImageIO.read(Utils.class.getResource(path)).getSubimage(0, 0, Settings.FIELDSIZE, Settings.FIELDSIZE);
	}
	
	/*
	 * Ließt eine Datei ein und gibt diese als String zurück
	 */
	public static String readFile(String path) {
		
		StringBuilder builder = new StringBuilder();

		FileReader file = null;
		try {
			file = new FileReader(Utils.class.getClass().getResource(path).getFile());
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
	
}
