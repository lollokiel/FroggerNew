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
import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
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
import threads.MoveObjects;
import utils.Countdown;
import utils.FieldKoordinate;
import utils.Koordinate;
import utils.Meeple;
import utils.Settings;
import utils.Utils;

/**
 * Diese Klasse umfasst das Spielfelt des Spiels
 * Es beinhaltet alle nötigen Methoden und Objekte, die für das 
 * eigentliche Spielen notwendig sind
 */
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
		
		this.lock.lock();
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
			if(this.countdown.seconds >= 0) {
				this.countdownLabel.setVerticalAlignment(SwingConstants.CENTER);
				this.countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
				this.countdownLabel.setForeground(Color.white);
				this.countdownLabel.setBounds(0, 500, Settings.FIELDSIZE*Settings.COLS, 200);
				if(this.countdown.seconds > 0) {
					this.countdownLabel.setFont(new Font("Calibri",0, this.countdown.seconds%100));
					this.countdownLabel.setText((int)this.countdown.seconds/100+1+"");
				} else {
					this.remove(this.countdownLabel);
				}
				this.add(this.countdownLabel);
				
			}
	
			// Game Over Bild
			if(!this.meeple.isAlive() && !win)
				g2.drawImage(this.settings.GAMEOVER, 175, 200, 250, 240, null);
			
			// Level complete Bild
			if(this.win){
				g2.drawImage(this.settings.LEVELCOMPLETE, (Settings.PG_WIDTH-350)/2, 100, 350, 350, null);
			}
		
		this.lock.unlock();
		
	}
	
	/*
	 * Tastendruck
	 */	
	private void move(KeyEvent e) {
	
		if(this.meeple.isAlive()) {
			
			// Abfrage, ob gedrückte Taste eine Pfeiltaste ist
			// 37 - Links; 38 - Oben; 39 - Rechts; 40 - Links
			if(e.getKeyCode() >=37 && e.getKeyCode() <= 40) { 
				
				// Wenn Startcountdown abgelaufen ist, sonst keine Bewegung erlaubt
				if(countdown.seconds<=0) {
					
					// Bei der ersten Bewegung Timer starten
					if(!meepleMoved) {
						new Thread(gameFrame.getTimer()).start();
						meepleMoved = true;
					}
									
					lock.lock();
					
						// Definiere, in welche Reihe sich die Figur bewegen will
						int newRow = meeple.getRow();
						if(e.getKeyCode() == 38 && meeple.getRow() > 0) {
							newRow--;
						} else
						if(e.getKeyCode() == 40 && meeple.getRow() < Settings.ROWS-1) {
							newRow++;
						}
						
						// Guckt, ob zukünftige Reihe ein Fluss ist
						River riverTo = null;
						for(River river : getRiver()) {
							if(river.getRow() == newRow) {
								riverTo = river;
								break;
							}
						}
						
						// Spielfigur geht auf Fluss
						if(riverTo != null) { 
							
							// Bei rechts-links Bewegung
							if(e.getKeyCode() == 37 || e.getKeyCode() == 39) {
											
								// Neue x-Koordinate festlegen
								int newX = meeple.getX();
								if(e.getKeyCode() == 37) {
									newX -= Settings.FIELDSIZE;
								} else {
									newX += Settings.FIELDSIZE;
								}
								
								// Wenn auf einem Holzstamm, dann besondere Bewegung auf Holzstamm
								if(meeple.getMoveableObject() != null) {
									// Wenn Spielfigur den Stamm nach r/l verlässt, wird Spiel beendet
									if(	(newX - meeple.getMoveableObject().getX() < 0) || 
										(newX + Settings.FIELDSIZE) - (meeple.getMoveableObject().getX()+
												meeple.getMoveableObject().getWidth()) > 0) {
										this.playSound(Settings.waterSound);
										die(1500, Settings.waterMsg);
									}
								} else { //Wenn Spielfigur sich auf dem Wasser befindet, und nicht auf Stamm,
									// und sich nach rechts-links bewegt, automatisch ins Wasser
									this.playSound(Settings.waterSound);
									die(1500, Settings.waterMsg);
								}
								meeple.moveTo(newX, meeple.getY());
							
							// Bei Oben-Unten Bewegung
							} else {
								
								// Wenn vor Bewegung auf Objekt, Verknüpfung zu Objekt lösen
								if(meeple.getMoveableObject() != null) {
									meeple.getMoveableObject().setMeepleOn(null);
									meeple.setMoveableObject(null);
								}
								
								// Prüfen ob bei neuer Position Objekt vorhanden ist
								for(MovableObject obj : riverTo.getMoveableObjects()) {
									if(obj.getX() < meeple.getMiddleX() && obj.getX() + obj.getWidth() > meeple.getMiddleX()) {
										meeple.setMoveableObject(obj); // Verknüpfung zu Objekt herstellen
										break;
									}
								}
								
								int newX = 0;
								// Wenn auf Fluss, aber nicht auf Stamm, prüfen ob Wasserrose
								if(meeple.getMoveableObject() == null) {
									Waterlily goToLily = null;
									
									// Zukünftige Position in grober Koordinate darstellen
									FieldKoordinate fkGoTo = new FieldKoordinate(new Koordinate(meeple.getMiddleX(), newRow*Settings.FIELDSIZE));
					
									// Durch Wasserrosen gehen und prüfen, ob neue Position auf Wasserrose liegt
									for(FieldObject obj : getObjectStructure()) {
										if(obj.getClass() == Waterlily.class) {
											
											if(obj.getFieldKoordinate().isSame(fkGoTo)) {
												goToLily = (Waterlily)obj;
												break;
											}
										}
									}
									
									// Wenn auf eine Wasserrose, dann neues x Berechnen und Wasserrose "starten"
									if(goToLily != null) {
										newX = fkGoTo.getCol()*Settings.FIELDSIZE;
										new Thread(goToLily).start();
										
									// Ansonsten Spiel vorbei und sterben
									} else {
										newX = (int)(this.meeple.getMiddleX()/Settings.FIELDSIZE) *Settings.FIELDSIZE;
										this.playSound(Settings.waterSound);
										die(1500, Settings.waterMsg);
									}
									
								// Wenn Bewegung auf ein Stamm: Position des Stammes einnehmen
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
						
						//Check ob auf Loch
						for(FieldObject obj : getObjectStructure()) {
							if(obj.getClass() == Pit.class) {
								if(obj.getFieldKoordinate().isSame(meeple.getFieldkoordinate())) {
									die(0, Settings.pitMsg);
								}
							}
						}						
						
						// Wenn oberste Reihe : gewonnen
						if(meeple.getY() == 0) {
							win();
						}
					
					lock.unlock();
					this.repaint();
					
				}
			} 
		}	
	}
	
	/*
	 * Spielfigur gewinnt
	 */
	public void win() {
		this.win = true;
		this.meeple.setAlive(false);
		this.playSound(Settings.winSound);
		
		Font f2 = new Font("Arial", Font.ITALIC , 20);
		
		JLabel wonNameLabel = new JLabel("Dein Name:");
		wonNameLabel.setHorizontalAlignment(SwingConstants.LEFT);
		wonNameLabel.setFont(f2);
		wonNameLabel.setForeground(Color.WHITE);
		wonNameLabel.setBounds(140, 505, Settings.FIELDSIZE*Settings.COLS, 100);
		this.add(wonNameLabel);
		
		JTextField wonNameField = new JTextField();
		wonNameField.setBounds(260, 540, 200, 25);
		this.add(wonNameField);
		
		// Prüfen, ob es ein nächstes Level gibt
		boolean hasNext = false;
		ArrayList<Integer> levelList = this.gameFrame.getMainFrame().readLevel();
		for(int i = 0; i < levelList.size(); i++) {
			if(levelList.get(i) == this.level && i < levelList.size()-1) {
				hasNext = true;				
				break;
			}
		}
		
		// Wenn nächstes Level vorhanden: Butten anzeigen
		if(hasNext) {
			JButton btnNextLevel = new JButton("Nächstes Level");
			btnNextLevel.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					
					// Wenn Name eingegeben zur Highscore Liste hinzufügen
					if(wonNameField.getText().length() > 0)
						gameFrame.getMainFrame().addPlayerToHighscore(gameFrame.getLevel(), wonNameField.getText(), gameFrame.getSeconds() );
					
					gameFrame.setVisible(false);
					// Starte nächstes Level
					gameFrame.getMainFrame().start(gameFrame.getLevel()+1, gameFrame.getFigure(), gameFrame.getBounds().x, gameFrame.getBounds().y);
				}
			});
			btnNextLevel.setBounds((Settings.FIELDSIZE * Settings.COLS -200) / 2, 600, 200, 50);
			this.add(btnNextLevel);
		}
		
		JButton btnRestart = new JButton("<html>Nochmal<br>spielen</html>");
		btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Wenn Name eingegeben, dann zum Highscore hinzufügen
				if(wonNameField.getText().length() > 0)
					gameFrame.getMainFrame().addPlayerToHighscore(gameFrame.getLevel(), wonNameField.getText(), gameFrame.getSeconds() );

				gameFrame.setVisible(false);
				// Level neu starten
				gameFrame.getMainFrame().start(gameFrame.getLevel(), gameFrame.getFigure(), gameFrame.getBounds().x, gameFrame.getBounds().y);
			}
		});
		btnRestart.setBounds((int)((Settings.FIELDSIZE * Settings.COLS -200) * (1/10.0)), 600, 100, 50);
		this.add(btnRestart);
		
		JButton btnSave = new JButton("Speichern");
		btnSave.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				// Wenn Name eingegeben, dann zum Highscore hinzufügen
				if(wonNameField.getText().length() > 0)
					gameFrame.getMainFrame().addPlayerToHighscore(gameFrame.getLevel(), wonNameField.getText(), gameFrame.getSeconds() );
				
				// Menüfenster anzeigen und dabei Highscore reloaden
				gameFrame.setVisible(false);
				gameFrame.getMainFrame().reloadHighscore();
				gameFrame.getMainFrame().setVisible(true);
			
			}
		});
		btnSave.setBounds(450, 610, 100, 30);
		this.add(btnSave);
	}
	
	/**
	 * Die Spielfigur stirbt
	 * Führt zu:
	 * - Anzeige des Game-Over Bildes & Neustarten Button
	 * - Unbeweglichkeit der Spielfigur
	 * - Ende des Timers
	 * - Keine neuen Elemente werden hinzugefügt
	 * - Abspielen des Verliertons nach n ms, abhängig, ob noch ein anderer Ton gespielt werden soll
	 */
	public void die(int msToPlaySound, String looseMsg) {
		// Setzt den Status der Spielfigur auf nicht-lebendig
		this.meeple.setAlive(false);
		
		// Thread zum warten, falls ggf. zwei Töne abgespielt werden
		new Thread( new Runnable() {
			@Override
			public void run() {
				try {
					Thread.sleep(msToPlaySound);
					playSound(Settings.dieSound);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();

		JButton btnRestart = new JButton("Neu Starten");
		btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.setVisible(false);
				// Spiel neustarten
				gameFrame.getMainFrame().start(gameFrame.getLevel(), gameFrame.getFigure(), gameFrame.getBounds().x, gameFrame.getBounds().y);
			}
		});
		btnRestart.setBounds((Settings.FIELDSIZE * Settings.COLS - 200) / 2, 450, 200, 50);
		this.add(btnRestart);
		
		// Grund des Sterbens angeben
		JLabel looseText = new JLabel(looseMsg);
		looseText.setForeground(Color.white);
		looseText.setBounds(0,100,Settings.PG_WIDTH, 40);
		looseText.setFont(new Font("Comic Sans MS", Font.PLAIN, 20));
		looseText.setHorizontalAlignment(JLabel.CENTER);
		this.add(looseText);
	}
	
	/*
	 * Struktur Methoden (Hintergrund, Objekte, Reihen,...)
	 */
	
		/**
		 * Ließt Datei für die Hintergrundstruktur und erstellt ein Array mit Reihentypen
		 */
		private void readBackgroundStructure() {
			
			// Array aller Zeilen der Hintergrundstruktur werden gelesen
			String[] backgroundStructureLines = Utils.readFile("/level/backgroundStructure/"+this.level+".txt").split("\n");
			
			int i = 0;
			for(String rowString : backgroundStructureLines) {
				String[] rowSettings = rowString.split("\\s"); // Bei Leerzeichen trennen
				switch(rowSettings[0]) {
					case "2": this.addRiver(rowSettings, i); break;
					case "3": this.addStreet(rowSettings, i); break;
					default: this.rows.add(settings.GRASS); break;
				}
				i++;
			}
		}
		
		/**
		 * Ließt Datei für die Objektstruktur und erstellt für jeden Objekttyp ein Array mit Objekten
		 */
		private void readObjectStructure() {
			// Array aller Zeilen der Objektstruktur
			String[] objectStructureLines = Utils.readFile("/level/objectStructure/"+this.level+".txt").split("\n");
			
			for(int row = 0; row < objectStructureLines.length; row++) {
				String[] rowStructure = objectStructureLines[row].split(","); // Teilt Zeile bei Komma in einzelne Elemente
				for(int col = 0; col < rowStructure.length; col++) {
					int objectType = Integer.parseInt(rowStructure[col]);
					// Fügt je nach Angabe einen Baum, ein Loch oder eine Wasserrose an die Position
					if(objectType == 1) {
						this.trees.add(new Tree(new FieldKoordinate(col, row), this.settings.TREE));
					} else
					if(objectType == 2) {
						this.pits.add(new Pit(new FieldKoordinate(col, row), this.settings.PIT));
					} else
					if(objectType == 3) {
						this.waterlilies.add(new Waterlily(new FieldKoordinate(col, row), this.settings.WATERLILY, this));
					}
				}
			}
		}
		
		/**
		 * Fügt alle Objektarrays zu einem zusammen und gibt die Liste zurück
		 * @return Liste aller Objekte auf dem Spielfeld
		 */
		public ArrayList<FieldObject> getObjectStructure() {
			ArrayList<FieldObject> returnObjects = new ArrayList<FieldObject>();
			
			returnObjects.addAll(this.trees);
			returnObjects.addAll(this.waterlilies);
			returnObjects.addAll(this.pits);
			
			return returnObjects;
		}
		
		/**
		 * Löscht eine Wasserrose aus der Liste der Wasserrosen
		 * @param waterlily Objekt der Wasserrose, das gelöscht werden soll
		 * @return true wenn löschen erfolgreich; false wenn nicht
		 */
		public boolean removeWaterlily(Waterlily waterlily) {
			return this.waterlilies.remove(waterlily);
		}
		
		/**
		 * Fügt der Liste mit Flüssen einen Fluss hinzu
		 * @param rowSettings String mit Einstellungen für den Fluss ( Geschwindigkeit, Richtung, Wiederholungsrate)
		 * @param row int-Wert, in welche Reihe der Fluss hinzugefügt werden soll
		 */
		private void addRiver(String[] rowSettings, int row) {
			if(rowSettings.length == 4) {
				this.rows.add(this.settings.WATER); 
				
				int speed = Integer.parseInt(rowSettings[1]);
				int direction = Integer.parseInt(rowSettings[2]);
				int wdhSpeed = Integer.parseInt(rowSettings[3]);
						
				this.rivers.add(new River(direction, speed, row, wdhSpeed, this));
			}
		}
		
		/**
		 * Fügt dem Spielfeld eine Straße hinzu
		 * @param rowSettings Einstellungen der Straße (Richtung, Geschwindigkeit, Wiederholungsrate)
		 * @param row int-Wert der Reihe, in der die Straße eingefügt werden soll 
		 */
		private void addStreet(String[] rowSettings, int row) {
			if(rowSettings.length == 4) {
				this.rows.add(this.settings.STREET); 
				
				int speed = Integer.parseInt(rowSettings[1]);
				int direction = Integer.parseInt(rowSettings[2]);
				int wdhSpeed = Integer.parseInt(rowSettings[3]);
						
				this.streets.add(new Street(direction, speed, row, wdhSpeed, this));
			}
		}
		
		/**
		 * Fügt alle Reihen zu einer Liste zusammen und gibt diese Zurück
		 * @return
		 */
		public ArrayList<ActiveRow> getRows() {
			ArrayList<ActiveRow> rows = new ArrayList<ActiveRow>();
			rows.addAll(this.streets);
			rows.addAll(this.rivers);
			return rows;
		}
	
	/*
	 * Methoden zum Zeichnen von Elementen 
	 */
	
		/**
		 * Zeichne alle Holzstämme aller Flüsse auf die Graphik
		 * @param g2 Graphik des Spielfeldes, auf der Gezeichnet wird
		 * @return Graphik mit gezeichneten Objekten
		 */
		private Graphics2D paintRiver(Graphics2D g2) {
			for(River row : this.getRiver()) {
				for(MovableObject obj : row.getMoveableObjects()) {
					g2.drawImage(obj.getBackground(), obj.getX(), row.getRow()*Settings.FIELDSIZE, obj.getWidth(), obj.getHeight(), null);
				}
			}
			return g2;
		}
		
		/**
		 * Zeichne alle Autos aller Straßen auf die Graphik
		 * @param g2 Graphik des Spielfeldes, auf der Gezeichnet wird
		 * @return Graphik mit gezeichneten Objekten
		 */
		private Graphics2D paintStreet(Graphics2D g2) {
			for(Street row : this.getStreets()) {
				for(MovableObject obj : row.getMoveableObjects()) {
					g2.drawImage(obj.getBackground(), obj.getX(), row.getRow()*Settings.FIELDSIZE, obj.getWidth(), obj.getHeight(), null);
				}
			}
			return g2;
		}
		
		/**
		 * Zeichne Spielfigur auf das Spielfeld
		 * @param g2 Graphik auf der gezeichnet wird
		 * @return Graphik mit gezeichneter Spielfigur
		 */
		private Graphics2D paintMeeple(Graphics2D g2) {
			g2.drawImage(this.meeple.getImage(), this.meeple.getX(), this.meeple.getY(), Settings.FIELDSIZE, Settings.FIELDSIZE, null);
			return g2;
		}
		
		public void playSound(String sound) {
			try {
				Clip wav = AudioSystem.getClip();
				AudioInputStream inputStreamWAV = AudioSystem
						.getAudioInputStream(new File("res/"+sound).getAbsoluteFile());
				wav.open(inputStreamWAV);
				wav.start();
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}
		}
	
	/*
	 * Getter
	 */
	public Countdown getCountdown() {
		return this.countdown;
	}
	
	public Game getGameFrame() {
		return this.gameFrame;
	}
	
	public Meeple getMeeple() {
		return this.meeple;
	}
	
	public Lock getLock() {
		return this.lock;
	}

	public ArrayList<Street> getStreets() {
		return this.streets;
	}
	
	public ArrayList<River> getRiver() {
		return this.rivers;
	}
	
	public ArrayList<BufferedImage> getBackgroundStructure() {
		return this.rows;
	}
	
}
