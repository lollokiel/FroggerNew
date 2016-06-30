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

/**
 * Spielfenster
 * Diese Klasse umfasst das Spielfenster. Es beinhaltet das Spielfeld, den Timer,
 * die Bestzeit, den Abbruch-Button sowie die Anzeige des Levels. Es ist die Grundlage
 * für das gerade gespielte Level und definiert:
 * - Levelnummer
 * - Verweis auf das Hauptfenster
 * - Spielfeld
 * - Figur, mit der gespielt wird
 * - Timer
 * - Aufgeben Button 
 * - einige Label
 */
public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private int 		level;
	private MainFrame 	mainframe;
	private Playground 	playground;
	private Figure 		figure;
	private Timer 		timer;
	private JPanel 		panelClock, panelSettingsBar;
	private JButton 	btnGiveup;
	private JLabel 		lblTimer, lblLevel, lblBesttime, lblBestzeit, lblZeit;
	
	
	public Game(int levelNum, Figure figure, MainFrame frameFrogger, int posX, int posY) {
		
		this.level = levelNum;
		this.mainframe = frameFrogger;
		this.figure = figure;
		this.timer = new Timer(this);
		
		this.setFocusable(false); // Focus muss auf Spielfeld gelegt werden, KeyEvents
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
			
			// Wird bei Betätigen des Schließenfeldes ausgelöst 
			@Override
			public void windowClosing(WindowEvent e) {
				closeWindow();
			}
		});
		
			this.panelSettingsBar = new JPanel(new FormLayout(new ColumnSpec[] {
					ColumnSpec.decode(this.getWidth()/3+"px"),
					ColumnSpec.decode(this.getWidth()/3+"px"),
					ColumnSpec.decode(this.getWidth()/3+"px"),},
				new RowSpec[] {
					RowSpec.decode("default:grow"),}));
			this.getContentPane().add(this.panelSettingsBar, "1, 1, fill, fill");
		
				this.panelClock = new JPanel(new FormLayout(new ColumnSpec[] {
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("90px"),
						FormSpecs.RELATED_GAP_COLSPEC,
						ColumnSpec.decode("default:grow"),},
					new RowSpec[] {
						RowSpec.decode("default:grow"),
						FormSpecs.RELATED_GAP_ROWSPEC,
						RowSpec.decode("default:grow"),}));
				this.panelSettingsBar.add(this.panelClock, "1, 1, fill, fill");
		
					this.lblZeit = new JLabel("Zeit:");
					this.panelClock.add(this.lblZeit, "2, 1");
		
					this.lblTimer = new JLabel(Utils.getTime(0));
					this.panelClock.add(this.lblTimer, "4, 1, fill, default");
		
					this.lblBestzeit = new JLabel("Bestzeit:");
					this.panelClock.add(this.lblBestzeit, "2, 3");
		
					this.lblBesttime = new JLabel();
					int best = this.mainframe.readBest(levelNum);
						// Bestzeit anzeigen, wenn keine Bestzeit bekannt: -:--
						if(best > 0) 	this.lblBesttime.setText(Utils.getTime(best));
						else 			this.lblBesttime.setText("-:--");
						this.panelClock.add(this.lblBesttime, "4, 3, fill, default");
		
				this.lblLevel = new JLabel("Level "+this.level);
				this.lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
				this.panelSettingsBar.add(this.lblLevel, "2, 1, fill, default");
		
				this.btnGiveup = new JButton("Aufgeben");
				this.btnGiveup.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						closeWindow();
					}
				});
				this.btnGiveup.setFocusable(false);
				this.panelSettingsBar.add(this.btnGiveup, "3, 1, default, fill");

		
			this.playground = new Playground(this);
			this.playground.requestFocus();
			this.playground.setFocusable(true);
			this.getContentPane().add(this.playground, "1, 3, fill, fill");
		
	}
	
	/**
	 * Füllt den Timer mit der übergebenden Zeit
	 * @param seconds Sekunden, die angezeigt werden sollen; Umrechnung in Minuten u. Sekunden erfolgt später
	 */
	public void setTimer(int seconds) {
		this.lblTimer.setText(Utils.getTime(seconds));
	}
	
	/**
	 * Schließt das Spielfenster.
	 * Dabei wird das Hauptfenster gestartet sichtbar.
	 */
	private void closeWindow() {
		this.dispose();
		this.playground.getMeeple().setAlive(false);
		this.mainframe.setVisible(true);
		this.mainframe.reloadHighscore();
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
