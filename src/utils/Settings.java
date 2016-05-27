package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;

public class Settings {

	/*
	 * General Settings
	 */
	public static int ROWS = 19;
	public static int COLS = 17;
	public static int FIELDSIZE = 32;
	
	/*
	 * Movable Objects
	 */
	public BufferedImage CAR_L;
	public BufferedImage CAR_R;
	public BufferedImage TRUNK;
	
	
	/*
	 * Backgrounds
	 */
	public BufferedImage GRASS;
	public BufferedImage WATER;
	public BufferedImage STREET;
	
	/*
	 *  Special Elements
	 */
	public BufferedImage TREE;
	public BufferedImage WATERLILY;
	public BufferedImage HOLE;
	
	/*
	 * Play Figures
	 */
	public BufferedImage FROG;
	
	public Settings() {	
		
		try {
			CAR_L 		= Utils.formTile("/objects/car_l.png");
			CAR_R 		= Utils.formTile("/objects/car_r.png");

			TRUNK 		= Utils.formTile("/objects/trunk.png");
			
			GRASS 		= Utils.formTile("/backgrounds/grass.png");
			WATER 		= Utils.formTile("/backgrounds/water.png");
			STREET 		= Utils.formTile("/backgrounds/street.png");
				
			TREE 		= Utils.formTile("/objects/tree.png");
			//WATERLILY 	= Utils.formTile("/objects/waterlily.png");
			//HOLE 		= Utils.formTile("/objects/hole.png");
			
			FROG 		= Utils.formTile("/objects/frog.png");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
