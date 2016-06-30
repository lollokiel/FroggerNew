package activeObjects;

import java.awt.image.BufferedImage;

import frames.Playground;
import utils.FieldKoordinate;
import utils.Settings;

/**
 * Wasserrose.
 * Erbt von FieldObject und implementiert Runnable.
 * Verschwindet nach Eintreten nach 3 sek., ggf. fällt man dann ins Wasser, 
 * was das Spiel beendet. Steigert die Schwierigkeit im Lauf der Level.
 */
public class Waterlily extends FieldObject implements Runnable {

	private Playground playground;
	
	public Waterlily(FieldKoordinate koordinate, BufferedImage background, Playground playground) {
		this.koordinate = koordinate;
		this.backgorund = background;
		this.playground = playground;
	}

	/*
	 * Bei Eintritt auf Wasserrose wird Timer gestartet, nach 3 Sekunden verschwindet Sie und Spieler stirbt
	 * Im Takt von 300 ms, da sonst ggf. Probleme auftreten, wenn Spieler kurz Rose verlässt und sofort wieder betritt,
	 * ggf. wird dabei der Thread nicht gelöscht, sondern läuft weiter. Der Timer wird also ggf. nicht auf 3 Sek. gesetzt.
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
					this.playground.playSound(Settings.waterSound);
					this.playground.die(1500, Settings.lilyMsg);
					this.playground.repaint();
				}
			}
		}
	}
	
}
