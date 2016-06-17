package threads;

import java.awt.image.BufferedImage;

import activeObjects.MovableObject;
import activeRows.ActiveRow;
import frames.Playground;
import utils.Settings;

public class AddObject implements Runnable {

	private Settings settings;
	private Playground playground;
	
	public AddObject(Playground p) {
		this.playground = p;
		this.settings = p.getGameFrame().getMainFrame().getSettings();
	}
	
	@Override
	public void run() {
		while(this.playground.getMeeple().isAlive()) {

			this.playground.getLock().lock();
			for(ActiveRow row : this.playground.getRows()) {
				int difference = 1000000;
				if(row.getDirection() == 1) {
					for(MovableObject obj : row.getMoveableObjects()) {
						if(obj.getX() < difference) difference = obj.getX();
					}
					BufferedImage newImage = this.settings.CAR_R;
					if(difference > 60) row.addMovableObject(new MovableObject(newImage,0-newImage.getWidth(), row));
				} else {
					for(MovableObject obj : row.getMoveableObjects()) {
						int diffTmp = Settings.PLAYGROUND_WIDTH - obj.getX() + obj.getWidth();
						if(difference < diffTmp) difference =  diffTmp;
					}
					BufferedImage newImage = this.settings.CAR_L;
					if(difference > 60) row.addMovableObject(new MovableObject(newImage,0-newImage.getWidth(), row));
				}
			}
			this.playground.getLock().unlock();
			
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
