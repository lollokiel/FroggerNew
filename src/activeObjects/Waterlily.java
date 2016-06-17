package activeObjects;

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

	/*
	 * Bei Eintritt auf Wasserrose wird Timer gestartet, nach 3 Sekunden verschwindet Sie und 
	 * Spieler stirbt
	 */
	@Override
	public void run() {
		
		int ms = 0;
		while(this.playground.getMeeple().getFieldkoordinate().isSame(this.getFieldKoordinate()) && ms < 3000) {
			
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			
			ms += 300;
			
			if(ms >= 3000) {
				if(this.playground.getMeeple().getFieldkoordinate().isSame(this.getFieldKoordinate())) {
					this.playground.removeWaterlily(this);
					this.playground.die();
					this.playground.repaint();
				}
			}
		}
	}
	
}
