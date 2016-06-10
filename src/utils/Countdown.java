package utils;

import frames.Playground;

public class Countdown implements Runnable {

	public int seconds = 300;
	private Playground playground;
	
	public Countdown(Playground p) {
		this.playground = p;
	}
	
	@Override
	public void run() {
		
		while(seconds > -100) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			seconds--;
			playground.repaint();
		}
		
	}	
	
}
