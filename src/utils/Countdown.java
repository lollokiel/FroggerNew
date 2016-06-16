package utils;

public class Countdown implements Runnable {

	public int seconds = 300;
	
	public Countdown() {
	}
	
	@Override
	public void run() {
			while(seconds >= 0) {
				
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				seconds--;

			}
		
	}	
	
}
