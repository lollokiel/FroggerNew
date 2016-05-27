package frames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

import game.Meeple;
import utils.Settings;

public class Playground extends JPanel {

	private static final long serialVersionUID = 1L;
	private Game gameFrame;
	private Meeple meeple;
	
	public Playground(Game gameFrame) {
		
		meeple = new Meeple(Settings.COLS/2, Settings.ROWS-1, gameFrame.mainframe.settings.FROG);
		
		this.gameFrame = gameFrame;
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

				meeple.moveField(e.getKeyCode());
				repaint();
			}
		});
	}
	
	@Override
	public void paintComponent(Graphics g) {
		
		Graphics2D g2 = (Graphics2D)g;
		int j = 0;
		for(BufferedImage background : gameFrame.level.getBackgroundStructure()) {
			for(int i = 0; i < Settings.COLS; i++) {
				g2.drawImage(background, i*Settings.FIELDSIZE, j*Settings.FIELDSIZE, Settings.FIELDSIZE, Settings.FIELDSIZE, null);
			}
			j++;
		}
		
		g2.drawImage(meeple.getImage(), meeple.getX(), meeple.getY(), Settings.FIELDSIZE, Settings.FIELDSIZE, null);
		
	}

}
