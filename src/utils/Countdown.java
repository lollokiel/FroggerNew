package utils;

public class Countdown implements Runnable {

	public int seconds = 300;
	
	@Override
	public void run() {
			while(seconds >= 0) {
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				seconds--;

			}
		
	}	
	
}
