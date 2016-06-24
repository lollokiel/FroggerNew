package utils;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;

public class Settings {

	/*
	 * General Settings
	 */
	public static int ROWS = 19;
	public static int COLS = 17;
	public static int FIELDSIZE = 35;
	public static int PG_WIDTH = FIELDSIZE * COLS;
	public static int PG_HEIGHT = FIELDSIZE * ROWS;
	
	/*
	 * Movable Objects
	 */
	public BufferedImage CAR_L;
	public BufferedImage CAR_R;
	public BufferedImage TRUNK;

	public ArrayList<BufferedImage> CARS_L = new ArrayList<BufferedImage>();
	public ArrayList<BufferedImage> CARS_R = new ArrayList<BufferedImage>();
	
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
	public BufferedImage KANGAROO;
	public BufferedImage BEETLE;
	public BufferedImage TURTLE;
	public ArrayList<Figure> MEEPLES = new ArrayList<Figure>();
	/*
	 * Screens
	 */
	public BufferedImage GAMEOVER;
	public BufferedImage LEVELCOMPLETE;

	
	public Settings() {	
		
		try {
			this.CAR_L 		= Utils.formTile("/objects/car_l.png", 70, 35);
			this.CAR_R 		= Utils.formTile("/objects/car_r.png", 70, 35);

			this.TRUNK 		= Utils.formTile("/objects/trunk.png", 70, 35);
			
			this.GRASS 		= Utils.formTile("/backgrounds/grass.png", 35, 35);
			this.WATER 		= Utils.formTile("/backgrounds/water.png", 35, 35);
			this.STREET 		= Utils.formTile("/backgrounds/street.png", 35, 35);
				
			this.TREE 		= Utils.formTile("/objects/tree.png", 35, 35);
			this.WATERLILY 	= Utils.formTile("/objects/waterlily.png", 35, 35);
			this.PIT 		= Utils.formTile("/objects/pit.png", 35, 35);

			this.FROG 		= Utils.formTile("/objects/frog.png", 35, 35);		this.MEEPLES.add(new Figure("Frosch", this.FROG));
			this.CHICKEN	= Utils.formTile("/objects/chicken.png", 35, 35);	this.MEEPLES.add(new Figure("Huhn", this.CHICKEN));
			this.KANGAROO	= Utils.formTile("/objects/kangaroo.png", 35, 35);	this.MEEPLES.add(new Figure("Kängeru", this.KANGAROO));
			this.BEETLE		= Utils.formTile("/objects/beetle.png", 35, 35);	this.MEEPLES.add(new Figure("Käfer", this.BEETLE));
			this.TURTLE		= Utils.formTile("/objects/turtle.png", 35, 35);	this.MEEPLES.add(new Figure("Schildkröte", this.TURTLE));

			this.CARS_L.add(Utils.formTile("/objects/autoblau_li.png", 70, 35));
			this.CARS_L.add(Utils.formTile("/objects/autogruen_li.png", 70, 35));
			this.CARS_L.add(Utils.formTile("/objects/autorot_li.png", 70, 35));
			this.CARS_L.add(Utils.formTile("/objects/motorbike_li.png", 70, 35));
			
			this.CARS_R.add(Utils.formTile("/objects/autoblau_re.png", 70, 35));
			this.CARS_R.add(Utils.formTile("/objects/autogruen_re.png", 70, 35));
			this.CARS_R.add(Utils.formTile("/objects/autorot_re.png", 70, 35));
			this.CARS_R.add(Utils.formTile("/objects/motorbike_re.png", 70, 35));
			
			this.GAMEOVER      = Utils.formTile("/screens/gameover.png", 250, 240);
			this.LEVELCOMPLETE = Utils.formTile("/screens/levelcomplete.png", 500, 500);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
