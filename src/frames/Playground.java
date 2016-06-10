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
import javax.swing.SwingConstants;

import game.Meeple;
import objects.FieldObject;
import objects.MoveableObject;
import objects.Pit;
import objects.River;
import objects.Street;
import objects.Tree;
import objects.Waterlily;
import settings.Settings;
import threads.MoveObject;
import utils.ActiveRow;
import utils.Countdown;
import utils.FieldKoordinate;
import utils.Timer;
import utils.Utils;

public class Playground extends JPanel {

	private static final long serialVersionUID = 1L;
	public Game gameFrame;
	public Meeple meeple;
	public boolean inGame = false;
	public final Lock lock = new ReentrantLock();
	private int level;
	private Settings settings;
	public Countdown countdown;
	
	/*
	 * Struktur
	 */
		
	private ArrayList<BufferedImage> 	rows 		= new ArrayList<BufferedImage>();
	private ArrayList<Tree>		 		trees		= new ArrayList<Tree>();
	private ArrayList<Pit>		 		pits		= new ArrayList<Pit>();
	private ArrayList<Waterlily> 		waterlilies	= new ArrayList<Waterlily>();
	private ArrayList<Street> 			streets 	= new ArrayList<Street>();
	private ArrayList<River> 			rivers 		= new ArrayList<River>();
		
	
	private JLabel countdownLabel = new JLabel();
	
	public Playground(Game gameFrame) {
		
		// Countdown
		
	
		// Referenz zum Spielfenster
		this.gameFrame = gameFrame;
		this.level = this.gameFrame.level;
		this.settings = this.gameFrame.mainframe.settings;
		
		// Countdown
		this.countdown = new Countdown(this);
		new Thread(this.countdown).start();

		// Eine Spielfigur anlegen
		this.meeple = new Meeple(Settings.COLS/2, Settings.ROWS-1, gameFrame.figure.getImage(), this);
		
		// Spielfeld aufbauen
		this.readBackgroundStructure();
		this.readObjectStructure();
		

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
		
		// Move Thread
		new Thread(new MoveObject(this)).start();
		
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
			g2.drawImage(object.getBackground(), object.getCol()*Settings.FIELDSIZE, object.getRow()*Settings.FIELDSIZE, Settings.FIELDSIZE, Settings.FIELDSIZE, null);
		}
		
		
		// Zeichne Spielfigur
		g2.drawImage(meeple.getImage(), meeple.getX(), meeple.getY(), Settings.FIELDSIZE, Settings.FIELDSIZE, null);
		
		
		// Zeichne Fahrende Objekte
		for(ActiveRow row : this.getRows()) {
			for(MoveableObject obj : row.getMoveableObjects()) {
				g2.drawImage(obj.getBackground(), obj.getX(), row.getRow()*Settings.FIELDSIZE, obj.getWidth(), obj.getHeight(), null);
			}
		}
		
		if(!this.meeple.isAlive()) {
			g2.setColor(new Color(0,0,0,127));
			g2.fillRect(0,0,Settings.FIELDSIZE*Settings.COLS, Settings.FIELDSIZE*Settings.ROWS);
		}
		
		if(countdown.seconds > -100) {
			countdownLabel.setVerticalAlignment(SwingConstants.CENTER);
			countdownLabel.setHorizontalAlignment(SwingConstants.CENTER);
			countdownLabel.setForeground(Color.white);
			countdownLabel.setBounds(0, 500, Settings.FIELDSIZE*Settings.COLS, 200);
			if(countdown.seconds > 0) {
				countdownLabel.setFont(new Font("Calibri",0, countdown.seconds%100));
				countdownLabel.setText((int)countdown.seconds/100+1+"");
			}
			if(countdown.seconds <= 0 && countdown.seconds > -100) {
				countdownLabel.setFont(new Font("Calibri",0, 50));
				countdownLabel.setText("Los!");
			}
			this.add(this.countdownLabel);
		} else {
			if(countdown.seconds == -100) this.remove(countdownLabel);
		}
		
/*		
		if(countdown.seconds > 0) {
			g2.setColor(new Color(0,0,0,127));
			g2.fillRect(0,0,Settings.FIELDSIZE*Settings.COLS, Settings.FIELDSIZE*Settings.ROWS);
			g2.setFont(new Font("Calibri",0, countdown.seconds%100));
			g2.setColor(Color.WHITE);
			g2.drawString((int)countdown.seconds/100+1+"", 300-(countdown.seconds%100)/10, 300-(countdown.seconds%100)/10);
			System.out.println(countdown.seconds +" : "+ countdown.seconds %100);
		}
		if(countdown.seconds <= 0 && countdown.seconds > -100) {
			g2.setFont(new Font("Calibri",0,50));
			g2.drawString("Los!", 10, 100);
		}*/
		
		lock.unlock();
		
	}
	
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
					waterlilies.add(new Waterlily(new FieldKoordinate(col, row), settings.WATERLILY));
				}
			}
		}
	}
	
	private void addRiver(String[] rowSettings, int row) {
		if(rowSettings.length == 4) {
			rows.add(this.settings.WATER); 
			
			int speed = Integer.parseInt(rowSettings[1]);
			int direction = Integer.parseInt(rowSettings[2]);
					
			rivers.add(new River(direction, speed, row));
		}
	}
	
	private void addStreet(String[] rowSettings, int row) {
		if(rowSettings.length == 4) {
			rows.add(this.settings.STREET); 
			
			int speed = Integer.parseInt(rowSettings[1]);
			int direction = Integer.parseInt(rowSettings[2]);
					
			Street streetNew = new Street(direction, speed, row, this);
			streets.add(streetNew);
		}
	}
	
	public ArrayList<BufferedImage> getBackgroundStructure() {
		return rows;
	}
	
	public ArrayList<FieldObject> getObjectStructure() {
		ArrayList<FieldObject> returnObjects = new ArrayList<FieldObject>();
		
		returnObjects.addAll(trees);
		returnObjects.addAll(waterlilies);
		returnObjects.addAll(pits);
		
		return returnObjects;
	}
	
	public ArrayList<ActiveRow> getRows() {
		ArrayList<ActiveRow> rows = new ArrayList<ActiveRow>();
		rows.addAll(streets);
		rows.addAll(rivers);
		return rows;
	}
	
	public void die() {
		this.meeple.setAlive(false);
		
		JLabel gameOver = new JLabel("Game Over!");
		gameOver.setHorizontalAlignment(SwingConstants.CENTER);
		Font f = new Font("Arial", Font.ITALIC , 80);
		gameOver.setFont(f);
		gameOver.setForeground(Color.WHITE);
		gameOver.setBounds(0, 300, Settings.FIELDSIZE*Settings.COLS, 100);
		this.add(gameOver);
		
		int btnWidth = 200;
		JButton btnRestart = new JButton("Neu Starten");
		btnRestart.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				gameFrame.mainframe.start(gameFrame.level, gameFrame.figure);
			}
		});
		btnRestart.setBounds((Settings.FIELDSIZE * Settings.COLS -btnWidth) / 2, 450, btnWidth, 50);
		this.add(btnRestart);
	}

}
