package settings;

import java.awt.image.BufferedImage;
import java.io.IOException;

import utils.Utils;

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
	public BufferedImage PIT;
	
	/*
	 * Play Figures
	 */
	public BufferedImage FROG;
	public BufferedImage CHICKEN;
	
	public Settings() {	
		
		try {
			CAR_L 		= Utils.formTile("/objects/car_l.png", 70, 35);
			CAR_R 		= Utils.formTile("/objects/car_r.png", 70, 35);

			TRUNK 		= Utils.formTile("/objects/trunk.png", 70, 35);
			
			GRASS 		= Utils.formTile("/backgrounds/grass.png", 35, 35);
			WATER 		= Utils.formTile("/backgrounds/water.png", 35, 35);
			STREET 		= Utils.formTile("/backgrounds/street.png", 35, 35);
				
			TREE 		= Utils.formTile("/objects/tree.png", 35, 35);
			WATERLILY 	= Utils.formTile("/objects/waterlily.png", 35, 35);
			PIT 		= Utils.formTile("/objects/pit.png", 35, 35);

			FROG 		= Utils.formTile("/objects/frog.png", 35, 35);
			CHICKEN		= Utils.formTile("/objects/chicken.png", 35, 35);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
