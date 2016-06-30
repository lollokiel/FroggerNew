package utils;

import frames.Game;

/**
 * Klasse zum Zeit-Stoppen
 * Zählt Sekündlich die Sekunden hoch und ändert den Timer im Spielfenster
 * @author Lollo
 *
 */
public class Timer extends Thread {

	private Game gameFrame;
	public int seconds = 0;
	
	public Timer(Game gameFrame) {
		this.gameFrame = gameFrame;
	}
	
	/*
	 * Für den Zugriff beim Highscore schreiben
	 */
	public int getSeconds() {
		this.gameFrame.getPlayground().getLock().lock();
			int returnSec = this.seconds;
		this.gameFrame.getPlayground().getLock().unlock();	
		return returnSec;	
	}
	
	@Override
	public void run() {
		while(this.gameFrame.getPlayground().getMeeple().isAlive()) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			this.gameFrame.getPlayground().getLock().lock();
						
			if(this.gameFrame.getPlayground().getMeeple().isAlive()) {
				this.seconds++;
				this.gameFrame.setTimer(seconds);
			}
			
			this.gameFrame.getPlayground().getLock().unlock();
		}
	}
	
}
