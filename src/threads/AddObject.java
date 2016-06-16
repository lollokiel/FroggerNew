package threads;

import java.awt.image.BufferedImage;

import activeObjects.Car;
import activeObjects.MoveableObject;
import activeRows.ActiveRow;
import frames.Playground;
import utils.Settings;

public class AddObject implements Runnable {

	private Playground playground;
	
	public AddObject(Playground p) {
		this.playground = p;
	}
	
	@Override
	public void run() {
		while(playground.getMeeple().isAlive()) {

			playground.lock.lock();
			for(ActiveRow row : playground.getRows()) {
				int difference = 1000000;
				if(row.getDirection() == 1) {
					for(MoveableObject obj : row.getMoveableObjects()) {
						if(obj.getX() < difference) difference = obj.getX();
					}
					BufferedImage newImage = playground.getGameFrame().getMainFrame().getSettings().CAR_R;
					if(difference > 60) row.addMoveObject(new Car(newImage,0-newImage.getWidth(), row));
				} else {
					for(MoveableObject obj : row.getMoveableObjects()) {
						int diffTmp = Settings.FIELDSIZE*Settings.COLS - obj.getX()+obj.getWidth();
						if(difference < diffTmp) difference =  diffTmp;
					}
					BufferedImage newImage = playground.getGameFrame().getMainFrame().getSettings().CAR_L;
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
