package utils;

/**
 * Zählt zu Spielbeginn von 3 auf 0 runter.
 * Implementiert Runnable, um es als Thread parallel zu starten.
 */
public class Countdown implements Runnable {

	public int seconds = 300;
	
	@Override
	public void run() {
		
		while(seconds >= 0) {
			
			try {
				// im 10 ms-Takt, damit die Verkleinerung realisiert wird
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			seconds--;

		}
		
	}	
	
}
