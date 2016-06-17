package frames;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import activeObjects.FieldObject;
import activeObjects.MovableObject;
import activeObjects.Pit;
import activeObjects.Tree;
import activeObjects.Waterlily;
import activeRows.ActiveRow;
import activeRows.River;
import activeRows.Street;
import game.Meeple;
import threads.MoveObjects;
import utils.Countdown;
import utils.FieldKoordinate;
import utils.Settings;
import utils.Utils;

public class Playground extends JPanel {

	private static final long serialVersionUID = 1L;
	
	/*
	 * Deklaration
	 */
	private Game 		gameFrame;
	private Meeple 		meeple;
	private Settings 	settings;
	private Countdown 	countdown;
	private final Lock 	lock = new ReentrantLock();
	private int 		level;
	private boolean 	meepleMoved = false;
	private boolean 	win = false;
	
	private ArrayList<BufferedImage> 	rows 		= new ArrayList<BufferedImage>();
	private ArrayList<Tree>		 		trees		= new ArrayList<Tree>();
	private ArrayList<Pit>		 		pits		= new ArrayList<Pit>();
	private ArrayList<Waterlily> 		waterlilies	= new ArrayList<Waterlily>();
	private ArrayList<Street> 			streets 	= new ArrayList<Street>();
	private ArrayList<River> 			rivers 		= new ArrayList<River>();
		
	private JLabel countdownLabel = new JLabel();
	
	public Playground(Game gameFrame) {
			
		// Referenz zum Spielfenster
		this.gameFrame = gameFrame;
		this.level = this.gameFrame.getLevel();
		this.settings = this.gameFrame.getMainFrame().getSettings();
		
		// Countdown
		this.countdown = new Countdown();
		new Thread(this.countdown).start();

		// Eine Spielfigur anlegen
		this.meeple = new Meeple(Settings.COLS/2, Settings.ROWS-1, gameFrame.getFigure().getImage(), this);
		
		// Spielfeld aufbauen
		this.readBackgroundStructure();
		this.readObjectStructure();
		
		// KeyListener zum Bewegen
		this.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				move(e);
			}
		});
		
		// Thread zum Bewegen der Objekte
		new Thread(new MoveObjects(this)).start();
		
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
			for(BufferedImage background : this.getBackgroundStructure()) {
				for(int i = 0; i < Settings.COLS; i++) {
					g2.drawImage(background, i*Settings.FIELDSIZE, rowNum*Settings.FIELDSIZE, Settings.FIELDSIZE, Settings.FIELDSIZE, null);
				}
				rowNum++;
			}
			
			// Zeichne alle Objekte ( Baum, Wasserrsose, ... )
			for(FieldObject object : this.getObjectStructure()) {
				if(object.getClass() == Pit.class) {
					if(meeple.getFieldkoordinate().isSame(object.getFieldKoordinate())) {
						g2.drawImage(object.getBackground(), object.getCol()*Settings.FIELDSIZE, object.getRow()*Settings.FIELDSIZE, Settings.FIELDSIZE, Settings.FIELDSIZE, null);
					}
				} else
					g2.drawImage(object.getBackground(), object.getCol()*Settings.FIELDSIZE, object.getRow()*Settings.FIELDSIZE, Settings.FIELDSIZE, Settings.FIELDSIZE, null);
			}
			
			// Fahrende Objekt bzw. Spielfigur abhängig vom Lebensstatus
			if(meeple.isAlive()) {
				g2 = paintRiver(g2);
				g2 = paintMeeple(g2);
				g2 = paintStreet(g2);
			} else {
				g2 = paintMeeple(g2);
				g2 = paintRiver(g2);
				g2 = paintStreet(g2);
			}
			
			// Zeichne transparenten schwarzen Hintergrund, wenn Spiel vorbei ist
			if(!this.meeple.isAlive()) {
				g2.setColor(new Color(0,0,0,127));
				g2.fillRect(0,0,Settings.FIELDSIZE*Settings.COLS, Settings.FIELDSIZE*Settings.ROWS);
			}
			
			// Countdown zu beginn des Spiels: 3,2,1, Los
			if(countdown.seconds >= 0) {
				countdownLabel.setVerticalAlignment(SwingConstants.CENTER);
				countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
				countdownLabel.setForeground(Color.white);
				countdownLabel.setBounds(0, 500, Settings.FIELDSIZE*Settings.COLS, 200);
				if(countdown.seconds > 0) {
					countdownLabel.setFont(new Font("Calibri",0, countdown.seconds%100));
					countdownLabel.setText((int)countdown.seconds/100+1+"");
				} else {
					this.remove(countdownLabel);
				}
				this.add(this.countdownLabel);
				
			}
	
			// Game Over Bild
			if(!meeple.isAlive() && !win)
				g2.drawImage(settings.GAMEOVER, 175, 200, 250, 240, null);
			
			// Level complete Bild
			if(win){
				g2.drawImage(settings.LEVELCOMPLETE, (settings.PLAYGROUND_WIDTH-350)/2, 100, 350, 350, null);
			}
		
		lock.unlock();
		
	}
	
	/*
	 * Tastendruck
	 */	
	private void move(KeyEvent e) {
		// Abfrage, ob gedrückte Taste eine Pfeiltaste ist
		// 37 - Links; 38 - Oben; 39 - Rechts; 40 - Links
		if(e.getKeyCode() >=37 && e.getKeyCode() <= 40) { 
			
			// Wenn Startcountdown abgelaufen ist
			if(countdown.seconds<=0) {
				if(!meepleMoved) {
					// Starte Stoppuhr
					new Thread(gameFrame.getTimer()).start();
					meepleMoved = true;
				}
								
				lock.lock();
				
				// Definiere neue Reihe
				int newRow = meeple.getRow();
				if(e.getKeyCode() == 38 && meeple.getRow() > 0) {
					newRow--;
				} else
				if(e.getKeyCode() == 40 && meeple.getRow() < Settings.ROWS-1) {
						newRow++;
				}
				
				River riverTo = null;
				for(River river : getRiver()) {
					if(river.getRow() == newRow) {
						riverTo = river;
						break;
					}
				}
				
				// Spielfigur geht auf Fluss
				if(riverTo != null) { 
					
					if(e.getKeyCode() == 37 || e.getKeyCode() == 39) {
													
						int newX = meeple.getX();
						if(e.getKeyCode() == 37) {
							newX -= Settings.FIELDSIZE;
						} else {
							newX += Settings.FIELDSIZE;
						}
						
						if(newX - meeple.getMoveableObject().getX() < 0 || (newX + Settings.FIELDSIZE) - (meeple.getMoveableObject().getX()+meeple.getMoveableObject().getWidth()) > 0) {
							die();
						}
						meeple.moveTo(newX, meeple.getY());
					} else {

						if(meeple.getMoveableObject() != null) {
							meeple.getMoveableObject().setMeepleOn(null);
							meeple.setMoveableObject(null);
						}
						
						for(MovableObject obj : riverTo.getMoveableObjects()) {
							if(obj.getX() < meeple.getMiddleX() && obj.getX() + obj.getWidth() > meeple.getMiddleX()) {
								meeple.setMoveableObject(obj);
								break;
							}
						}
						
						int newX = 0;
						if(meeple.getMoveableObject() == null) {
							newX = meeple.getX();
							die();
						} else {
							newX = meeple.getMoveableObject().getX()+(Settings.FIELDSIZE)*
									((int)((meeple.getMiddleX()-meeple.getMoveableObject().getX()) / Settings.FIELDSIZE));																
						}
						meeple.moveTo(newX, newRow*Settings.FIELDSIZE);	
						
					}
				} else {	// Spielfigur geht auf anderem Feld
					
					meeple.moveField(e.getKeyCode());
					
					if(meeple.getMoveableObject() != null) {
						meeple.getMoveableObject().setMeepleOn(null);
						meeple.setMoveableObject(null);
					}
				}
				
				//Check ob auf Wasserrose
				
				
				//Check ob auf Loch oder Wasserrose
				for(FieldObject obj : getObjectStructure()) {
					if(obj.getFieldKoordinate().isSame(meeple.getFieldkoordinate())) {
						if(obj.getClass() == Pit.class) {
							die();
						} else if(obj.getClass() == Waterlily.class) {
							Waterlily waterlily = (Waterlily)obj;
							new Thread(waterlily).start();
						}
					} 
				}
				
				
				if(meeple.getY() == 0) {
					win();
				}
				
				lock.unlock();
				repaint();
				
			}
		}
	}
	
	/*
	 * Spielfigur gewinnt
	 */
	public void win() {
		this.win = true;
		this.meeple.setAlive(false);
				
		JLabel wonNameLabel = new JLabel("Dein Name:");
		wonNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		Font f2 = new Font("Arial", Font.ITALIC , 20);
		wonNameLabel.setFont(f2);
		wonNameLabel.setForeground(Color.WHITE);
		wonNameLabel.setBounds(160, 505, Settings.FIELDSIZE*Settings.COLS, 100);
		this.add(wonNameLabel);
		
		int btnWidth = 200;
		JTextField wonNameField = new JTextField();
		wonNameField.setBounds((int)((Settings.FIELDSIZE * Settings.COLS -btnWidth) * (3/4.0)), 530, 200, 50);
		this.add(wonNameField);
		
	
		JButton btnNextLevel = new JButton("Nächstes Level");
		btnNextLevel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(wonNameField.getText().length() > 0)
					gameFrame.getMainFrame().addPlayerToHighscore(gameFrame.getLevel(), wonNameField.getText(), gameFrame.getSeconds() );
				
				gameFrame.setVisible(false);
				gameFrame.getMainFrame().start(gameFrame.getLevel()+1, gameFrame.getFigure(), gameFrame.getBounds().x, gameFrame.getBounds().y);
			}
		});
		btnNextLevel.setBounds((Settings.FIELDSIZE * Settings.COLS -btnWidth) / 2, 600, btnWidth, 50);
		//this.add(btnNextLevel);
		
		JButton btnRestart = new JButton("Nochmal spielen");
//		btnRestart.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				
//				if(wonNameField.getText().length() > 0)
//				
//				gameFrame.setVisible(false);
//				gameFrame.getMainFrame().start(gameFrame.getLevel(), gameFrame.getFigure(), gameFrame.getBounds().x, gameFrame.getBounds().y);
//			}
//		});
		btnRestart.setVisible(true);
		this.add(btnRestart);
		
		JButton btnSave = new JButton("Speichern");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(wonNameField.getText().length() > 0)
					gameFrame.getMainFrame().addPlayerToHighscore(gameFrame.getLevel(), wonNameField.getText(), gameFrame.getSeconds() );
				
				gameFrame.setVisible(false);
				gameFrame.getMainFrame().setVisible(true);
			}
		});
		btnSave.setBounds((int)((Settings.FIELDSIZE * Settings.COLS -btnWidth) * (3/4.0)), 550, btnWidth/2, 25);
		this.add(btnSave);
	}
	
	/*
	 * Spielfigur stibt
	 */
	public void die() {
		this.meeple.setAlive(false);
			
		int btnWidth = 200;
		JButton btnRestart = new JButton("Neu Starten");
		btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setVisible(false);
				gameFrame.getMainFrame().start(gameFrame.getLevel(), gameFrame.getFigure(), gameFrame.getBounds().x, gameFrame.getBounds().y);
			}
		});
		btnRestart.setBounds((Settings.FIELDSIZE * Settings.COLS -btnWidth) / 2, 450, btnWidth, 50);
		this.add(btnRestart);
	}
	
	/*
	 * Struktur Methoden (Hintergrund, Objekte, Reihen,...)
	 */
	
		/*
		 * Erstellt aus einer Datei ein Array mit der Hintergrundstruktur
		 */
		private void readBackgroundStructure() {
			String[] backgroundStructureLines = Utils.readFile("/level/backgroundStructure/"+this.level+".txt").split("\n");
			int i = 0;
			for(String rowString : backgroundStructureLines) {
				String[] rowSettings = rowString.split("\\s");
				switch(rowSettings[0]) {
					case "2": this.addRiver(rowSettings, i); break;
					case "3": this.addStreet(rowSettings, i); break;
					default: rows.add(settings.GRASS); break;
				}
				i++;
			}
		}
		
		/*
		 * Funktion Gibt die Hintergrundstruktur zurück
		 */
		public ArrayList<BufferedImage> getBackgroundStructure() {
			return rows;
		}
		
		/*
		 * Erstellt aus einer Datei ein Array mit der Objektstruktur
		 */
		private void readObjectStructure() {
			String[] objectStructureLines = Utils.readFile("/level/objectStructure/"+this.level+".txt").split("\n");
			
			for(int row = 0; row < objectStructureLines.length; row++) {
				String[] rowStructure = objectStructureLines[row].split(",");
				for(int col = 0; col < rowStructure.length; col++) {
					int objectType = Integer.parseInt(rowStructure[col]);
					if(objectType == 1) {
						trees.add(new Tree(new FieldKoordinate(col, row), settings.TREE));
					} else
					if(objectType == 2) {
						pits.add(new Pit(new FieldKoordinate(col, row), settings.PIT));
					} else
					if(objectType == 3) {
						waterlilies.add(new Waterlily(new FieldKoordinate(col, row), settings.WATERLILY, this));
					}
				}
			}
		}
		
		/*
		 * Funktion gibt alle objekte in einer ArrayList zurück
		 */
		public ArrayList<FieldObject> getObjectStructure() {
			ArrayList<FieldObject> returnObjects = new ArrayList<FieldObject>();
			
			returnObjects.addAll(trees);
			returnObjects.addAll(waterlilies);
			returnObjects.addAll(pits);
			
			return returnObjects;
		}
		
		/*
		 * Funktion löscht Wasserrose aus Liste
		 */
		public boolean removeWaterlily(Waterlily waterlily) {
			return waterlilies.remove(waterlily);
		}
		
		/*
		 * Füge der Struktur einen Fluss hinzu
		 */
		private void addRiver(String[] rowSettings, int row) {
			if(rowSettings.length == 4) {
				rows.add(this.settings.WATER); 
				
				int speed = Integer.parseInt(rowSettings[1]);
				int direction = Integer.parseInt(rowSettings[2]);
				int wdhSpeed = Integer.parseInt(rowSettings[3]);
						
				rivers.add(new River(direction, speed, row, wdhSpeed, this));
			}
		}
		
		/*
		 * Gib alle Flüsse zurück
		 */
		public ArrayList<River> getRiver() {
			return rivers;
		}
		
		/*
		 * Füge der Struktur eine Straße hinzu
		 */
		private void addStreet(String[] rowSettings, int row) {
			if(rowSettings.length == 4) {
				rows.add(this.settings.STREET); 
				
				int speed = Integer.parseInt(rowSettings[1]);
				int direction = Integer.parseInt(rowSettings[2]);
				int wdhSpeed = Integer.parseInt(rowSettings[3]);
						
				streets.add(new Street(direction, speed, row, wdhSpeed, this));
			}
		}
		
		/*
		 * Gib alle Straßen zurück
		 */
		public ArrayList<Street> getStreets() {
			return streets;
		}
		
		/*
		 * Gib alle aktiven Reihen zurück ( Straßen & Flüsse)
		 */
		public ArrayList<ActiveRow> getRows() {
			ArrayList<ActiveRow> rows = new ArrayList<ActiveRow>();
			rows.addAll(streets);
			rows.addAll(rivers);
			return rows;
		}
	
	/*
	 * Methoden zum Zeichnen von Elementen 
	 */
	
		/*
		 * Zeichne alle Holzstämme auf Flüssen
		 */
		private Graphics2D paintRiver(Graphics2D g2) {
			for(River row : this.getRiver()) {
				for(MovableObject obj : row.getMoveableObjects()) {
					g2.drawImage(obj.getBackground(), obj.getX(), row.getRow()*Settings.FIELDSIZE, obj.getWidth(), obj.getHeight(), null);
				}
			}
			return g2;
		}
		
		/*
		 * Zeichne alle Autos/Motorräder auf Straßen
		 */
		private Graphics2D paintStreet(Graphics2D g2) {
			for(Street row : this.getStreets()) {
				for(MovableObject obj : row.getMoveableObjects()) {
					g2.drawImage(obj.getBackground(), obj.getX(), row.getRow()*Settings.FIELDSIZE, obj.getWidth(), obj.getHeight(), null);
				}
			}
			return g2;
		}
		
		/*
		 * Zeichne Spielfigur
		 */
		private Graphics2D paintMeeple(Graphics2D g2) {
			g2.drawImage(meeple.getImage(), meeple.getX(), meeple.getY(), Settings.FIELDSIZE, Settings.FIELDSIZE, null);
			return g2;
		}
	
	/*
	 * Getter
	 */
	public Countdown getCountdown() {
		return countdown;
	}
	
	public Game getGameFrame() {
		return gameFrame;
	}
	
	public Meeple getMeeple() {
		return meeple;
	}
	
	public Lock getLock() {
		return lock;
	}

}
