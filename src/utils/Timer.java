package utils;

import frames.Game;

public class Timer extends Thread {

	private Game gameFrame;
	public int seconds = 0;
	
	public Timer(Game gameFrame) {
		this.gameFrame = gameFrame;
	}
	
	public int getSeconds() {
		gameFrame.getPlayground().getLock().lock();
			int returnSec = seconds;
		gameFrame.getPlayground().getLock().unlock();	
		return returnSec;	
	}
	
	@Override
	public void run() {
		while(gameFrame.getPlayground().getMeeple().isAlive()) {
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			gameFrame.getPlayground().getLock().lock();
			
				seconds++;
			
			if(gameFrame.getPlayground().getMeeple().isAlive())
				gameFrame.setTimer(seconds);

			gameFrame.getPlayground().getLock().unlock();
		}
	}
	
}
