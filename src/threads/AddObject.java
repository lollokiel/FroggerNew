package threads;

import java.awt.image.BufferedImage;

import frames.Playground;
import objects.Car;
import objects.MoveableObject;
import settings.Settings;
import utils.ActiveRow;

public class AddObject implements Runnable {

	private Playground playground;
	
	public AddObject(Playground p) {
		this.playground = p;
	}
	
	@Override
	public void run() {
		while(playground.meeple.isAlive()) {

			playground.lock.lock();
			for(ActiveRow row : playground.getRows()) {
				int difference = 1000000;
				if(row.getDirection() == 1) {
					for(MoveableObject obj : row.getMoveableObjects()) {
						if(obj.getX() < difference) difference = obj.getX();
					}
					BufferedImage newImage = playground.gameFrame.mainframe.settings.CAR_R;
					if(difference > 60) row.addMoveObject(new Car(newImage,0-newImage.getWidth(), row));
				} else {
					for(MoveableObject obj : row.getMoveableObjects()) {
						int diffTmp = Settings.FIELDSIZE*Settings.COLS - obj.getX()+obj.getWidth();
						if(difference < diffTmp) difference =  diffTmp;
					}
					BufferedImage newImage = playground.gameFrame.mainframe.settings.CAR_L;
					if(difference > 60) row.addMoveObject(new Car(newImage,0-newImage.getWidth(), row));
				}
			}
			playground.lock.unlock();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
