package frames;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.FormSpecs;
import com.jgoodies.forms.layout.RowSpec;

import utils.Figure;
import utils.Settings;
import utils.Timer;
import utils.Utils;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private int 		level;
	private MainFrame 	mainframe;
	private Playground 	playground;
	private Figure 		figure;
	
	private Timer timer = new Timer(this);

	private JPanel panelClock;
	private JPanel panelSettingsBar;
	
	private JButton btnGiveup;

	private JLabel lblTimer;
	private JLabel lblLevel;
	private JLabel lblBesttime;
	private JLabel lblBestzeit;
	private JLabel lblZeit;
	
	
	public Game(int levelNum, Figure figure, MainFrame frameFrogger, int posX, int posY) {
		
		this.level = levelNum;
		this.mainframe = frameFrogger;
		this.figure = figure;

		this.setFocusable(false);
		this.setResizable(false);
		this.setTitle("Frogger");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(posX, posY, Settings.COLS*Settings.FIELDSIZE, Settings.ROWS*Settings.FIELDSIZE + 75);
		this.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode(Settings.ROWS*Settings.FIELDSIZE+"px"),}));
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		
			panelSettingsBar = new JPanel(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode(this.getWidth()/3+"px"),
					ColumnSpec.decode(this.getWidth()/3+"px"),
					ColumnSpec.decode(this.getWidth()/3+"px"),},
				new RowSpec[] {
					RowSpec.decode("default:grow"),}));
			getContentPane().add(panelSettingsBar, "1, 1, fill, fill");
		
				panelClock = new JPanel(new FormLayout(new ColumnSpec[] {
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("90px"),
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						RowSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),}));
				panelSettingsBar.add(panelClock, "1, 1, fill, fill");
		
					lblZeit = new JLabel("Zeit:");
					panelClock.add(lblZeit, "2, 1");
		
					lblTimer = new JLabel(Utils.getTime(0));
					panelClock.add(lblTimer, "4, 1, fill, default");
		
					lblBestzeit = new JLabel("Bestzeit:");
					panelClock.add(lblBestzeit, "2, 3");
		
					lblBesttime = new JLabel();
					int best = mainframe.readBest(levelNum);
						if(best > 0) lblBesttime.setText(Utils.getTime(best));
						else lblBesttime.setText("-:--");
					panelClock.add(lblBesttime, "4, 3, fill, default");
		
				lblLevel = new JLabel("Level "+level);
				lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
				panelSettingsBar.add(lblLevel, "2, 1, fill, default");
		
				btnGiveup = new JButton("Aufgeben");
				btnGiveup.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeWindow();
					}
				});
				btnGiveup.setFocusable(false);
				panelSettingsBar.add(btnGiveup, "3, 1, default, fill");

		
			playground = new Playground(this);
			playground.requestFocus();
			playground.setFocusable(true);
			getContentPane().add(playground, "1, 3, fill, fill");
		
	}
	
	/*
	 * Schließt das Spielfenster
	 */
	private void closeWindow() {
		this.dispose();
		this.playground.getMeeple().setAlive(false);
		this.mainframe.setVisible(true);
		this.mainframe.reloadHighscore();
	}
		
	public void setTimer(int seconds) {
		this.lblTimer.setText(Utils.getTime(seconds));
	}
	
	/*
	 * Getter Methodes
	 */	
	public Timer getTimer() {
		return this.timer;
	}
	
	public int getSeconds() {
		return this.timer.getSeconds();
	}
	
	public Playground getPlayground() {
		return this.playground;
	}
	
	public MainFrame getMainFrame() {
		return this.mainframe;
	}
	
	public int getLevel() {
		return this.level;
	}
	
	public Figure getFigure() {
		return this.figure;
	}

}
