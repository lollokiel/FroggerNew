package frames;

import java.awt.EventQueue;
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

import objects.Figure;
import objects.Player;
import settings.Settings;
import utils.HighscoreTableModel;
import utils.Utils;



public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JTable highscoreTable = new JTable();
	private JComboBox<Integer> level;
	private JComboBox<Figure> figure;
	private Settings settings;
	private MainFrame thisFrame;
	private Game gameWindow;
	private ArrayList<Player> player = new ArrayList<Player>();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainFrame window = new MainFrame();
					window.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainFrame() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		settings = new Settings();

		// Level lesen
		ArrayList<Integer> levelList = readLevel();
		
		this.thisFrame = this;
		this.setTitle("Frogger");
		this.setBounds(100, 100, 270, 500);
		this.setResizable(false);
		this.addWindowListener(new WindowAdapter() {

			
			@Override
			public void windowClosing(WindowEvent e) {
				Gson gson = new Gson();
				String s = gson.toJson(player);
				System.out.println(s);
				try {
					FileWriter bw = new FileWriter("highscore.txt");
					bw.write("test");
					bw.close();
					System.out.println("test");
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
			
		});
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
		
		JPanel pServer = new JPanel();
		pServer.setBorder(new TitledBorder(null, "Server", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.getContentPane().add(pServer, "2, 2, fill, fill");
		pServer.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JButton btnSave = new JButton("Speichern");
		btnSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				
				
				ArrayList<Player> allPlayer = new ArrayList<Player>();
				Gson gson = new Gson();
				
				allPlayer.add(new Player("Lorenz", 20));
				allPlayer.add(new Player("Max", 10));
				
				String json = gson.toJson(allPlayer);
				
				try {
					BufferedWriter writer = new BufferedWriter( new FileWriter( new File("res/level/highscores/1.json") ) );
					writer.append(json);
					writer.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				
				try {
					java.lang.reflect.Type listType = new TypeToken<ArrayList<Player>>(){}.getType();
					ArrayList<Player> getPlayer = gson.fromJson(new FileReader(new File("res/level/highscores/1.json") ), listType);

					System.out.println(getPlayer);
				} catch (JsonSyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
		
				
				
//				try {
//					BufferedReader br = new BufferedReader(new FileReader(Utils.class.getResource("/level/level.txt").getFile()));
//					
//					String s;
//					while((s = br.readLine()) != null) {
//						System.out.println(s);
//					}
//					
//					br.close();
//					
//					BufferedWriter bw = new BufferedWriter(new FileWriter(Utils.class.getResource("/level/level.txt").getFile()));
////					bw.newLine();
////					bw.write("3;192");
//					bw.close();
//					
//					br = new BufferedReader(new FileReader(Utils.class.getResource("/level/level.txt").getFile()));
//					
//					while((s = br.readLine()) != null) {
//						System.out.println(s);
//					}
//					
//					br.close();
//				} catch (IOException e1) {
//					// TODO Auto-generated catch block
//					e1.printStackTrace();
//				}
//				
				
				
			}
		});
		pServer.add(btnSave, "1, 1, default, fill");
		
		JButton btnLoad = new JButton("Laden");
		pServer.add(btnLoad, "1, 3, default, fill");
		
		JPanel panelHighscore = new JPanel();
		panelHighscore.setBorder(new TitledBorder(null, "Highscore", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.getContentPane().add(panelHighscore, "2, 4, fill, fill");
		panelHighscore.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("30px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),}));

			
		
		JButton btnDeleteHighscore = new JButton("Leeren");
		btnDeleteHighscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
				
		JPanel panel = new JPanel();
		panelHighscore.add(panel, "1, 1, fill, fill");
		panel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("50px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		JLabel lblLevel_1 = new JLabel("Level:");
		panel.add(lblLevel_1, "1, 1, left, default");
		
		JComboBox<Integer> comboBoxLevelHighscore = new JComboBox<Integer>();
		comboBoxLevelHighscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				try {
					int levelSelected = (int) comboBoxLevelHighscore.getSelectedItem();
					
					ArrayList<Player> playerHighscore = null;
					File f = new File("res/level/highscores/"+levelSelected+".json");
					if( f.exists()  ) {
						Gson gson = new Gson();					
						java.lang.reflect.Type listType = new TypeToken<ArrayList<Player>>(){}.getType();
						playerHighscore = gson.fromJson(new FileReader( new File( "res/level/highscores/"+levelSelected+".json" )), listType);
					}
					highscoreTable.setModel(new HighscoreTableModel(playerHighscore));	
					
				} catch (JsonIOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (JsonSyntaxException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		panel.add(comboBoxLevelHighscore, "3, 1, fill, default");
		for(int i : levelList) {
			comboBoxLevelHighscore.addItem(i);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelHighscore.add(scrollPane, "1, 3, fill, fill");
			
		scrollPane.setViewportView(highscoreTable);
		
		panelHighscore.add(btnDeleteHighscore, "1, 5, fill, fill");
		
		JPanel pStart = new JPanel();
		pStart.setBorder(new TitledBorder(null, "Starten", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.getContentPane().add(pStart, "2, 6, fill, fill");
		pStart.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("30px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JPanel pStartLevel = new JPanel();
		pStart.add(pStartLevel, "1, 1, fill, fill");
		pStartLevel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("50px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		
		
		level = new JComboBox<Integer>();
		for(int i : levelList) {
			level.addItem(i);
		}
		
		JLabel lblLevel = new JLabel("Level:");
		pStartLevel.add(lblLevel, "1, 1, left, default");
		pStartLevel.add(level, "3,1,default,fill");
		
		JPanel pStartFigur = new JPanel();
		pStart.add(pStartFigur, "1, 3, fill, fill");
		pStartFigur.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("50px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		
		figure = new JComboBox<Figure>();

		figure.addItem(new Figure("Frosch", settings.FROG));
		figure.addItem(new Figure("Huhn", settings.CHICKEN));
		figure.addItem(new Figure("Känguru",  settings.KANGAROO));
		figure.addItem(new Figure("Käfer", settings.BEETLE));
		figure.addItem(new Figure("Schildkröte", settings.TURTLE));
		
		JLabel lblFigur = new JLabel("Figur:");
		pStartFigur.add(lblFigur, "1, 1, left, default");
		pStartFigur.add(figure, "3, 1, default, fill");
		
		JButton btnStart = new JButton("Starten");
		pStart.add(btnStart, "1, 5, default, fill");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int levelSelected = (int)level.getSelectedItem();
				Figure figureSelected = figure.getItemAt(figure.getSelectedIndex());
				thisFrame.start(levelSelected, figureSelected);				
			}
		});
	}
	
	public void start(int level, Figure figure) {
		gameWindow = new Game(level, figure, thisFrame);
		gameWindow.setVisible(true);
		this.dispose();
	}
	
	public void disposeGameFrame(boolean dispose) {
		this.gameWindow.setVisible(dispose);
	}
	
	public Settings getSettings() {
		return settings;
	}

	private ArrayList<Integer> readLevel() {
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(Utils.class.getResource("/level/level.txt").getFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		ArrayList<Integer> level = new ArrayList<Integer>();
		String s;
		try {
			while((s = br.readLine()) != null) {
				String[] strings = s.split(";");
				System.out.println(s);
				level.add(Integer.parseInt(strings[0]));	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return level;
	}

	public int readBest(int level) {
		
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(Utils.class.getResource("/level/level.txt").getFile()));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String s;
		try {
			while((s = br.readLine()) != null) {
				String[] strings = s.split(";");
				if(Integer.parseInt(strings[0]) == level)
					return Integer.parseInt(strings[1]);	
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return 0;
	}


}
