package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import com.google.gson.Gson;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import server.Client;
import server.Level;
import server.LevelFile;
import utils.Figure;
import utils.HighscoreTableModel;
import utils.Player;
import utils.Settings;
import utils.Utils;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private Settings 	settings;
	private Game 		gameWindow;
	private Client		client;
	private JLabel 		lblLevelSelectHighscore, lblLevelSelectStart, lblFigurSelectStart;	
	private JPanel 		panelHighscoreSelect, panelHighscore, panelStart, panelStartLevel, panelStartFigur, panelServer;
	private JButton 	btnSave, btnLoad, btnStart;
	private JScrollPane scrollPaneHighscore;
	private JTable 		highscoreTable;

	private JComboBox<Integer> 	comboLevelSelectStart;
	private JComboBox<Integer> 	comboLevelSelectHighscore;
	private JComboBox<Figure> 	comboFigureSelectStart;

	private java.lang.reflect.Type playerListType = new TypeToken<ArrayList<Player>>(){}.getType();
	
	public MainFrame() {
				
		// Client für Serververbindung
		this.client = new Client();
		
		// Erstelle eine Settingsklasse 
		this.settings = new Settings();

		// Level lesen
		ArrayList<Integer> levelList = this.readLevel();
		
		this.setTitle("Frogger");
		this.setBounds(100, 100, 270, 500);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_COLSPEC,},
			new RowSpec[] {
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow(5)"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,}));
		
		/*
		 * Haupt Panel
		 */
		
			this.panelServer = new JPanel(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
					RowSpec.decode("default:grow"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"),}));
			this.panelServer.setBorder(new TitledBorder(null, "Server", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			this.getContentPane().add(this.panelServer, "2, 2, fill, fill");
			
				this.btnSave = new JButton("Level updaten");
				this.btnSave.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						updateLevel();
						
					}
				});
				this.panelServer.add(this.btnSave, "1, 1, default, fill");
				
				this.btnLoad = new JButton("Neue Level Suchen");
				this.btnLoad.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						
						switch(loadNewLevel()) {
							case 0: 	new Alert("Keine neuen Level gefunden!"); break;
							case 1: 	new Alert("Es wurden neue Level hinzugefügt!"); break;
							default: 	new Alert("Es wurde mit Fehlern abgeschlossen!"); break;
						}
					
					}
				});
				this.panelServer.add(this.btnLoad, "1, 3, default, fill");
		
			this.panelHighscore = new JPanel(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
					RowSpec.decode("30px:grow"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"),}));
			this.panelHighscore.setBorder(new TitledBorder(null, "Highscore", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			this.getContentPane().add(this.panelHighscore, "2, 4, fill, fill");
				
				this.panelHighscoreSelect = new JPanel(new FormLayout(new ColumnSpec[] {
						ColumnSpec.decode("50px"),
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						RowSpec.decode("default:grow"),}));
				this.panelHighscore.add(this.panelHighscoreSelect, "1, 1, fill, fill");
		
					this.lblLevelSelectHighscore = new JLabel("Level:");
					this.panelHighscoreSelect.add(this.lblLevelSelectHighscore, "1, 1, left, default");
			
					this.comboLevelSelectHighscore = new JComboBox<Integer>();
					for(int i : levelList) {
						this.comboLevelSelectHighscore.addItem(i);
					}
					this.comboLevelSelectHighscore.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							reloadHighscore();
						}
					});
					this.panelHighscoreSelect.add(this.comboLevelSelectHighscore, "3, 1, fill, default");
				
		
				this.scrollPaneHighscore = new JScrollPane();
				this.scrollPaneHighscore.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				this.scrollPaneHighscore.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				this.panelHighscore.add(this.scrollPaneHighscore, "1, 3, fill, fill");
								
					this.highscoreTable = new JTable();
					this.reloadHighscore();
					this.scrollPaneHighscore.setViewportView(this.highscoreTable);
					
		
			this.panelStart = new JPanel(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
					RowSpec.decode("30px:grow"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					RowSpec.decode("30px:grow"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"),}));
			this.panelStart.setBorder(new TitledBorder(null, "Starten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			this.getContentPane().add(this.panelStart, "2, 6, fill, fill");
		
				this.panelStartLevel = new JPanel(new FormLayout(new ColumnSpec[] {
						ColumnSpec.decode("50px"),
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						RowSpec.decode("default:grow"),}));
				this.panelStart.add(this.panelStartLevel, "1, 1, fill, fill");
		
		
					this.lblLevelSelectStart = new JLabel("Level:");
					this.panelStartLevel.add(this.lblLevelSelectStart, "1, 1, left, default");
				
					this.comboLevelSelectStart = new JComboBox<Integer>();
					for(int i : levelList) {
						this.comboLevelSelectStart.addItem(i);
					}
					this.panelStartLevel.add(this.comboLevelSelectStart, "3,1,default,fill");
				
					this.panelStartFigur = new JPanel(new FormLayout(new ColumnSpec[] {
							ColumnSpec.decode("50px"),
							FormSpecs.RELATED_GAP_COLSPEC,
							ColumnSpec.decode("default:grow"),},
						new RowSpec[] {
							RowSpec.decode("default:grow"),}));
					this.panelStart.add(this.panelStartFigur, "1, 3, fill, fill");
		
						this.lblFigurSelectStart = new JLabel("Figur:");
						this.panelStartFigur.add(this.lblFigurSelectStart, "1, 1, left, default");
						
						this.comboFigureSelectStart = new JComboBox<Figure>();
						for(Figure figure : settings.MEEPLES) 
							this.comboFigureSelectStart.addItem(figure);
						this.panelStartFigur.add(this.comboFigureSelectStart, "3, 1, default, fill");
		
					this.btnStart = new JButton("Starten");
					this.btnStart.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int levelSelected = (int)comboLevelSelectStart.getSelectedItem();
							Figure figureSelected = (Figure)comboFigureSelectStart.getSelectedItem();
							
							start(levelSelected, figureSelected, getBounds().x, getBounds().y);				
						}
					});
					this.panelStart.add(this.btnStart, "1, 5, default, fill");
	}	
	
	/**
	 * Startet ein neues Spiel
	 * @param level Levelnummer, welches gestartet werden soll
	 * @param figure Spielfigur, mit der gespielt wird.
	 * @param x x-Koordinate, wo das Spielfenster liegen soll
	 * @param y y-Koordinate, wo das Spielfenster liegen soll
	 */
	public void start(int level, Figure figure, int x, int y) {
		if(Utils.checkLevel(level)) {
			this.gameWindow = new Game(level, figure, this, x, y);
			this.gameWindow.setVisible(true);
			this.dispose();
		} else {
			this.reloadHighscore();
			this.setVisible(true);
			new Alert("Es ist ein Fehler aufgetreten!");
		}
	}
	
	/**
	 * Gibt das Einstellungsobjekt zurück
	 * @return setting Objekt vom Typ Settings; Enthällt alle Einstellungen
	 */
	public Settings getSettings() {
		return this.settings;
	}

	/**
	 * Selektiert alle Levelnummern, die lokal vorhanden sind und gibt sie in einem Array zurück 
	 * @return Liste aller Levelnummer, die lokal verfügbar sind.
	 */
	public ArrayList<Integer> readLevel() {
		
		try {
			ArrayList<Integer> levelList = new ArrayList<Integer>();
			BufferedReader br = new BufferedReader(new FileReader(new File("res/level/level.txt")));
		
			String s;
			while((s = br.readLine()) != null) {
				levelList.add(Integer.parseInt(s));	
			}
			
			br.close();
			
			return levelList;
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return new ArrayList<Integer>();
	}

	/**
	 * Gibt die Bestzeit in Sekunden von einem gefragten Level zurück
	 * @param level Levelnummer, dessen Bestzeit zurückgegeben werden soll
	 * @return Bestzeit des gerfragten Levels
	 */
	public int readBest(int level) {
		
		try {
			
			File file = new File("res/level/highscores/"+level+".json");
			if(file.exists()) {
				
				ArrayList<Player> player = new Gson().fromJson( new FileReader(file) , this.playerListType);
				if(player != null) {
					player.sort(null);
					
					if(player.size() > 0)
						return player.get(0).getTime();
				}
						
			}

		} catch(IOException e) {
			e.printStackTrace();			
		}
		
		return 0;

	}
	
	/**
	 * Fügt dem Highscore einen neuen Eintrag hinzu
	 * @param level Nummer des Levels, zu dessen Highscore der Eintrag hinzugefügt werden soll
	 * @param name Name des Spielers
	 * @param time Zeit, die der Spieler gebraucht hat
	 */
	public boolean addPlayerToHighscore(int level, String name, int time) {
		
		try {
			Gson gson = new Gson();
			
			File file = new File("res/level/highscores/"+level+".json");
			ArrayList<Player> allPlayer = new ArrayList<Player>();
			
			if(file.exists()) {
				allPlayer = gson.fromJson(new FileReader( file ), this.playerListType);
			} else {
				file.createNewFile();
			}
			
			if(allPlayer == null) allPlayer = new ArrayList<Player>();
			allPlayer.add(new Player(name, time));
			
			String json = gson.toJson(allPlayer);
			
			BufferedWriter bw = new BufferedWriter(new FileWriter( file ));
			bw.append(json);
			bw.close();
			
		} catch (IOException | JsonIOException | JsonSyntaxException e) {
			return false;
		}
		
		return true;
	}
	
	/**
	 * Baut das Highscore-Feld neu auf. Benutzt die Levelnummer, die im Dropdown gewählt ist.
	 */
	public void reloadHighscore() {
		if(this.comboLevelSelectHighscore.getItemCount() > 0) {
			int level = (int)this.comboLevelSelectHighscore.getSelectedItem();
			try {
				
				ArrayList<Player> playerHighscore = null;
				File f = new File("res/level/highscores/"+level+".json");
				if( f.exists() ) { 					
					playerHighscore = new Gson().fromJson(new FileReader( new File( "res/level/highscores/"+level+".json" )), playerListType);
				}
				this.highscoreTable.setModel(new HighscoreTableModel(playerHighscore));	
				
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e1) {
				e1.printStackTrace();
			}
		}
		
	}
	
	/**
	 * Ersetzt alle lokalen Leveldateien mit denen auf dem Server
	 */
	private void updateLevel() {
		
		boolean allOkay = false;
		try {
			this.loadNewLevel();
			ArrayList<Level> allLevel = this.client.getAllLevel();
			
			for(Level level : allLevel) {
				
				for(LevelFile file : level.getFiles()) {
					
					BufferedWriter bw = new BufferedWriter( new FileWriter (new File( "res/level/"+file.getName() )));
					for(int i = 0; i < file.getLines().size(); i++) {
						bw.write(file.getLines().get(i));
						if(i < file.getLines().size()-1) bw.newLine();
					}
					bw.close();
					
				}
				allOkay = true;
				new Alert("Update war erfolgreich!");
				
			}
			if(!allOkay) new Alert("Das updaten war nicht erfolgreich!");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Gleicht lokalen Stand mit Server ab und lädt neue, bzw. fehlende Level. Ersetzt nicht existierende level
	 * @return 0 für keine neuen Level
	 * 1 für neue Level ohne Fehler
	 * 2 für neue Level mit Fehlern
	 */
	private int loadNewLevel() {
		ArrayList<Integer> serverLevelList = this.client.getLevelList();
		ArrayList<Integer> localLevelList = this.readLevel();
		
		ArrayList<Integer> missingLevel = new ArrayList<Integer>();
		for(int id : serverLevelList) {
			boolean found = false;
			for(int localId : localLevelList) {
				if(id == localId) {
					found = true;
					break;
				}
			}
			if(!found) missingLevel.add(id);
		}
		
		int fileMissing = 0;
		for(int levelId : missingLevel) {
			Level level = client.getLevel(levelId);
			if(level.hasFile("backgroundStructure/"+levelId+".txt") && level.hasFile("objectStructure/"+levelId+".txt")) {
				localLevelList.add(levelId);
				for(LevelFile file : level.getFiles()) {
					Utils.createFile(file.getName(), "res/level", file.getLines());
				}
				
				try {
					BufferedWriter bw = new BufferedWriter( new FileWriter( new File("res/level/level.txt")));
					for(int i = 0; i < localLevelList.size(); i++) {
						bw.write(localLevelList.get(i)+"");
						if(i < localLevelList.size()-1) bw.newLine();
					}
					bw.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} else {
				fileMissing++;
			}
		}

		localLevelList.sort(null);
		
		if(missingLevel.size() > 0) {
			
			this.comboLevelSelectHighscore.removeAllItems();
			this.comboLevelSelectStart.removeAllItems();

			for(int i : localLevelList) {
				this.comboLevelSelectHighscore.addItem(i);
				this.comboLevelSelectStart.addItem(i);
			}
			
			this.comboLevelSelectHighscore.setSelectedIndex(0);
			this.comboLevelSelectStart.setSelectedIndex(0);
			
			if(fileMissing > 0) {
				return 2;
			} else {
				return 1;
			}
		}
		
		return 0;
	}


}
