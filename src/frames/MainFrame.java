package frames;

import java.awt.EventQueue;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.border.TitledBorder;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import objects.Figure;
import objects.Player;
import utils.*;



public class MainFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable highscoreTable;
	private JTextField textName;
	private JComboBox<String> level;
	private JComboBox<Figure> figure;
	public Settings settings;
	private MainFrame thisFrame;
	private Game gameWindow;
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
		pServer.add(btnSave, "1, 1, default, fill");
		
		JButton btnLoad = new JButton("Laden");
		pServer.add(btnLoad, "1, 3, default, fill");
		
		JPanel panelHighscore = new JPanel();
		panelHighscore.setBorder(new TitledBorder(null, "Highscore", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		this.getContentPane().add(panelHighscore, "2, 4, fill, fill");
		panelHighscore.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("30px"),}));
		
		JButton btnDeleteHighscore = new JButton("Leeren");
		btnDeleteHighscore.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		panelHighscore.add(scrollPane, "1, 1, fill, fill");
		
		ArrayList<Player> player = new ArrayList<Player>();
		player.add(new Player("Lorenz", 2));
		player.add(new Player("ETst", 21));
		
		HighscoreTableModel m = new HighscoreTableModel(player);
		m.addPlayer(new Player("Lorenz", 1));
		m.addPlayer(new Player("Erik", 2));
		m.addPlayer(new Player("Felix", 3));		
		
		highscoreTable = new JTable(m);
		scrollPane.setViewportView(highscoreTable);
		
		panelHighscore.add(btnDeleteHighscore, "1, 3, fill, fill");
		
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
				RowSpec.decode("30px:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JPanel pStartName = new JPanel();
		pStart.add(pStartName, "1, 1, fill, fill");
		pStartName.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("50px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		JLabel lblName = new JLabel("Name:");
		pStartName.add(lblName, "1, 1, left, default");
		
		textName = new JTextField();
		pStartName.add(textName, "3, 1, fill, default");
		textName.setColumns(10);
		
		JPanel pStartLevel = new JPanel();
		pStart.add(pStartLevel, "1, 3, fill, fill");
		pStartLevel.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("50px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		
		
		level = new JComboBox<String>();
		level.addItem("1");
		
		JLabel lblLevel = new JLabel("Level:");
		pStartLevel.add(lblLevel, "1, 1, left, default");
		pStartLevel.add(level, "3,1,default,fill");
		
		JPanel pStartFigur = new JPanel();
		pStart.add(pStartFigur, "1, 5, fill, fill");
		pStartFigur.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("50px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		
		figure = new JComboBox<Figure>();
		figure.addItem(new Figure("Frosch"));
		figure.addItem(new Figure("Huhn"));
		figure.addItem(new Figure("Känguru"));
		figure.addItem(new Figure("Schildkröte"));
		figure.addItem(new Figure("Käfer"));


		
		JLabel lblFigur = new JLabel("Figur:");
		pStartFigur.add(lblFigur, "1, 1, left, default");
		pStartFigur.add(figure, "3, 1, default, fill");
		
		JButton btnStart = new JButton("Starten");
		pStart.add(btnStart, "1, 7, default, fill");
		btnStart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
					int levelSelected = Integer.parseInt((String) level.getSelectedItem());
					Figure figurSelected = figure.getItemAt(figure.getSelectedIndex());
					gameWindow = new Game(levelSelected, figurSelected, thisFrame);
					gameWindow.setVisible(true);
					thisFrame.dispose();					
			}
		});
	}


}
