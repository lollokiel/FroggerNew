package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
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

import utils.Figure;
import utils.HighscoreTableModel;
import utils.Player;
import utils.Settings;
import utils.Utils;

public class MainFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private Settings 	settings;
	private MainFrame 	thisFrame;
	private Game 		gameWindow;

	private JLabel lblLevelSelectHighscore;
	private JLabel lblLevelSelectStart;
	private JLabel lblFigurSelectStart;
	
	private JPanel panelHighscoreSelect;
	private JPanel panelHighscore;
	private JPanel panelStart;
	private JPanel panelStartLevel;
	private JPanel panelStartFigur;
	private JPanel panelServer;
	
	private JButton btnSave;
	private JButton btnLoad;
	private JButton btnStart;
	
	private JScrollPane scrollPaneHighscore;

	private JComboBox<Integer> 	comboLevelSelectStart;
	private JComboBox<Integer> 	comboLevelSelectHighscore;
	private JComboBox<Figure> 	comboFigureSelectStart;

	private JTable highscoreTable;
	
	private java.lang.reflect.Type playerListType = new TypeToken<ArrayList<Player>>(){}.getType();
	
	/*
	 * Create the application.
	 */
	public MainFrame() {
				
		// Erstelle eine Settingsklasse 
		settings = new Settings();

		// Level lesen
		ArrayList<Integer> levelList = readLevel();
		
		this.thisFrame = this;
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
		this.addWindowListener(new WindowAdapter() {
			
			@Override
			public void windowClosing(WindowEvent e) {
				
				/*
				 * Server abgleich
				 */
			}
			
		});
		
		/*
		 * Haupt Panel
		 */
		
			panelServer = new JPanel(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
					RowSpec.decode("default:grow"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"),}));
			panelServer.setBorder(new TitledBorder(null, "Server", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			this.getContentPane().add(panelServer, "2, 2, fill, fill");
			
				btnSave = new JButton("Speichern");
				panelServer.add(btnSave, "1, 1, default, fill");
				
				btnLoad = new JButton("Laden");
				panelServer.add(btnLoad, "1, 3, default, fill");
		
			panelHighscore = new JPanel(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
					RowSpec.decode("30px:grow"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"),}));
			panelHighscore.setBorder(new TitledBorder(null, "Highscore", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			this.getContentPane().add(panelHighscore, "2, 4, fill, fill");
				
				panelHighscoreSelect = new JPanel(new FormLayout(new ColumnSpec[] {
						ColumnSpec.decode("50px"),
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						RowSpec.decode("default:grow"),}));
				panelHighscore.add(panelHighscoreSelect, "1, 1, fill, fill");
		
					lblLevelSelectHighscore = new JLabel("Level:");
					panelHighscoreSelect.add(lblLevelSelectHighscore, "1, 1, left, default");
			
					comboLevelSelectHighscore = new JComboBox<Integer>();
					for(int i : levelList) {
						comboLevelSelectHighscore.addItem(i);
					}
					comboLevelSelectHighscore.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							reloadHighscore();
						}
					});
					panelHighscoreSelect.add(comboLevelSelectHighscore, "3, 1, fill, default");
				
		
				scrollPaneHighscore = new JScrollPane();
				scrollPaneHighscore.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
				scrollPaneHighscore.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
				panelHighscore.add(scrollPaneHighscore, "1, 3, fill, fill");
								
					highscoreTable = new JTable();
					reloadHighscore();
					scrollPaneHighscore.setViewportView(highscoreTable);
					
		
			panelStart = new JPanel(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode("default:grow"),},
				new RowSpec[] {
					RowSpec.decode("30px:grow"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					RowSpec.decode("30px:grow"),
					FormSpecs.RELATED_GAP_ROWSPEC,
					RowSpec.decode("default:grow"),}));
			panelStart.setBorder(new TitledBorder(null, "Starten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
			this.getContentPane().add(panelStart, "2, 6, fill, fill");
		
				panelStartLevel = new JPanel(new FormLayout(new ColumnSpec[] {
						ColumnSpec.decode("50px"),
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						RowSpec.decode("default:grow"),}));
				panelStart.add(panelStartLevel, "1, 1, fill, fill");
		
		
					lblLevelSelectStart = new JLabel("Level:");
					panelStartLevel.add(lblLevelSelectStart, "1, 1, left, default");
				
					comboLevelSelectStart = new JComboBox<Integer>();
					for(int i : levelList) {
						comboLevelSelectStart.addItem(i);
					}
					panelStartLevel.add(comboLevelSelectStart, "3,1,default,fill");
				
					panelStartFigur = new JPanel(new FormLayout(new ColumnSpec[] {
							ColumnSpec.decode("50px"),
							FormSpecs.RELATED_GAP_COLSPEC,
							ColumnSpec.decode("default:grow"),},
						new RowSpec[] {
							RowSpec.decode("default:grow"),}));
					panelStart.add(panelStartFigur, "1, 3, fill, fill");
		
						lblFigurSelectStart = new JLabel("Figur:");
						panelStartFigur.add(lblFigurSelectStart, "1, 1, left, default");
						
						comboFigureSelectStart = new JComboBox<Figure>();
						for(Figure figure : settings.MEEPLES) 
							comboFigureSelectStart.addItem(figure);
						panelStartFigur.add(comboFigureSelectStart, "3, 1, default, fill");
		
					btnStart = new JButton("Starten");
					btnStart.addActionListener(new ActionListener() {
						public void actionPerformed(ActionEvent e) {
							int levelSelected = (int)comboLevelSelectStart.getSelectedItem();
							Figure figureSelected = (Figure)comboFigureSelectStart.getSelectedItem();
							
							thisFrame.start(levelSelected, figureSelected, getBounds().x, getBounds().y);				
						}
					});
					panelStart.add(btnStart, "1, 5, default, fill");
	}	
	
	/*
	 * Baue ein neues Spielfenster
	 */
	public void start(int level, Figure figure, int x, int y) {
		if(Utils.checkLevel(level)) {
			gameWindow = new Game(level, figure, thisFrame, x, y);
			gameWindow.setVisible(true);
			this.dispose();
		} else {
			this.reloadHighscore();
			this.setVisible(true);
			System.out.println("Nicht alle notwendigen Dateien vorhanden!");
		}
	}
	
	public Settings getSettings() {
		return settings;
	}

	/*
	 * Gibt Liste aller Levelnummern zurück
	 */
	private ArrayList<Integer> readLevel() {
		
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

	/*
	 * Gibt die Bestzeit in Sekunden eines Levels zurück
	 */
	public int readBest(int level) {
		
		try {
			File file = new File("res/level/highscores/"+level+".json");
			if(file.exists()) {
				
				
				Gson gson = new Gson();
				ArrayList<Player> player = gson.fromJson( new FileReader(file) , playerListType);
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
	
	/*
	 * Fügt einen neuen Eintrag in die Highscoreliste
	 */
	public boolean addPlayerToHighscore(int level, String name, int time) {
		
		try {
			Gson gson = new Gson();
			
			File file = new File("res/level/highscores/"+level+".json");
			ArrayList<Player> allPlayer = new ArrayList<Player>();
			
			if(file.exists()) {
				allPlayer = gson.fromJson(new FileReader( file ), playerListType);
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
			e.printStackTrace();	
		}
		
		return false;
	}
	
	/*
	 * Aktuallisiert die Highscoreliste
	 */
	public void reloadHighscore() {
		int level = (int)comboLevelSelectHighscore.getSelectedItem();
		try {
			
			ArrayList<Player> playerHighscore = null;
			File f = new File("res/level/highscores/"+level+".json");
			if( f.exists()  ) {
				Gson gson = new Gson();					
				
				playerHighscore = gson.fromJson(new FileReader( new File( "res/level/highscores/"+level+".json" )), playerListType);
			}
			highscoreTable.setModel(new HighscoreTableModel(playerHighscore));	
			
		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e1) {
			e1.printStackTrace();
		}
		
	}


}
