package utils;

import frames.Game;

public class Timer extends Thread {

	private Game gameFrame;
	
	public Timer(Game gameFrame) {
		this.gameFrame = gameFrame;
	}
	
	@Override
	public void run() {
		while(gameFrame.getPlayground().getMeeple().isAlive()) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			if(gameFrame.getPlayground().getMeeple().isAlive())
				gameFrame.raiseSeconds();
		}
	}
	
}
