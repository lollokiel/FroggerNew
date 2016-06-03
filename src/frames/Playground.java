package frames;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JPanel;

import game.Meeple;
import objects.FieldObject;
import objects.MoveableObject;
import settings.Settings;
import threads.AddObject;
import threads.MoveObject;
import utils.ActiveRow;
import utils.Timer;

public class Playground extends JPanel {

	private static final long serialVersionUID = 1L;
	public Game gameFrame;
	public Meeple meeple;
	public boolean inGame = false;
	public final Lock lock = new ReentrantLock();
	
	public Playground(Game gameFrame) {
		
		// Referenz zum Spielfenster
		this.gameFrame = gameFrame;
		
		// Eine Spielfigur anlegen
		this.meeple = new Meeple(Settings.COLS/2, Settings.ROWS-1, gameFrame.figure.getImage(), this);

		// KeyListener zum Bewegen
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() >=37 && e.getKeyCode() <= 40) {
					if(!inGame) {
						new Thread(new Timer(gameFrame)).start();
						inGame = true;
					}
					meeple.moveField(e.getKeyCode());
					repaint();
				}
			}
		});
		
		
		// Add Thread
		//new Thread(new AddObject(this)).start();
		
		// Move Thread
		new Thread(new MoveObject(this)).start();
		
		for(ActiveRow row : this.gameFrame.level.getRows()) {
			new Thread(row).start();
		}
		
	}
	
	/*
	 * Zeichnet das Panel
	 * mit Prioritäten:
	 * zuerst gezeichnetets liegt unten 
	 */
	@Override
	public void paintComponent(Graphics g) {
		
		lock.lock();
		Graphics2D g2 = (Graphics2D)g;
		
		// Zeichne den Hintergrund
		int rowNum = 0;
		for(BufferedImage background : gameFrame.level.getBackgroundStructure()) {
			for(int i = 0; i < Settings.COLS; i++) {
				g2.drawImage(background, i*Settings.FIELDSIZE, rowNum*Settings.FIELDSIZE, Settings.FIELDSIZE, Settings.FIELDSIZE, null);
			}
			rowNum++;
		}
		
		
		// Zeichne alle Objekte ( Baum, Wasserrsose, ... )
		for(FieldObject object : gameFrame.level.getObjectStructure()) {
			g2.drawImage(object.getBackground(), object.getCol()*Settings.FIELDSIZE, object.getRow()*Settings.FIELDSIZE, Settings.FIELDSIZE, Settings.FIELDSIZE, null);
		}
		
		
		// Zeichne Spielfigur
		g2.drawImage(meeple.getImage(), meeple.getX(), meeple.getY(), Settings.FIELDSIZE, Settings.FIELDSIZE, null);
		
		
		// Zeichne Fahrende Objekte
		for(ActiveRow row : gameFrame.level.getRows()) {
			for(MoveableObject obj : row.getMoveableObjects()) {
				g2.drawImage(obj.getBackground(), obj.getX(), row.getRow()*Settings.FIELDSIZE, obj.getWidth(), obj.getHeight(), null);
			}
		}
		
		lock.unlock();
		
	}

}
