package objects;

import java.awt.image.BufferedImage;

import frames.Playground;
import utils.FieldKoordinate;

public class Waterlily extends FieldObject implements Runnable {

	private Playground playground;
	
	public Waterlily(FieldKoordinate koordinate, BufferedImage background, Playground playground) {
		this.koordinate = koordinate;
		this.backgorund = background;
		this.playground = playground;
	}

	@Override
	public void run() {
		
		int ms = 0;
		while(playground.getMeeple().getFieldkoordinate().isSame(this.getFieldKoordinate()) && ms < 3000) {
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			ms += 300;
			
			if(ms >= 3000) {
				if(playground.getMeeple().getFieldkoordinate().isSame(this.getFieldKoordinate())) {
					playground.removeWaterlily(this);
					playground.die();
					playground.repaint();
				}
			}
		}
	}
	
}
