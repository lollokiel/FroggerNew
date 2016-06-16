package frames;

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

import objects.Figure;
import settings.Settings;
import utils.Utils;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class Game extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private int level;
	private MainFrame mainframe;
	private Game gameFrame;
	private Playground playground;
	private Figure figure;
	private JLabel gametimer;
	private int playedSeconds = 0;
	
	public Game(int levelNum, Figure figure, MainFrame frameFrogger) {
		
		this.level = levelNum;
		this.mainframe = frameFrogger;
		this.figure = figure;
		this.gameFrame = this;
		
		this.setResizable(false);
		this.setTitle("Frogger");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setBounds(100, 100, Settings.COLS*Settings.FIELDSIZE, Settings.ROWS*Settings.FIELDSIZE + 75);
		this.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode(Settings.ROWS*Settings.FIELDSIZE+"px"),}));
		
		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				// TODO Auto-generated method stub
				gameFrame.dispose();
				gameFrame.playground.getMeeple().setAlive(false);
				mainframe.setVisible(true);
			}
			
		});
		JPanel gameSettingsBar = new JPanel();
		getContentPane().add(gameSettingsBar, "1, 1, fill, fill");
		gameSettingsBar.setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode(this.getWidth()/3+"px"),
				ColumnSpec.decode(this.getWidth()/3+"px"),
				ColumnSpec.decode(this.getWidth()/3+"px"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),}));
		
		JPanel panelClock = new JPanel();
		gameSettingsBar.add(panelClock, "1, 1, fill, fill");
		panelClock.setLayout(new FormLayout(new ColumnSpec[] {
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("90px"),
				FormSpecs.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		JLabel lblZeit = new JLabel("Zeit:");
		panelClock.add(lblZeit, "2, 1");
		
		gametimer = new JLabel(Utils.getTime(0));
		panelClock.add(gametimer, "4, 1, fill, default");
		
		JLabel lblBestzeit = new JLabel("Bestzeit:");
		panelClock.add(lblBestzeit, "2, 3");
		
		
		int best = mainframe.readBest(levelNum);
		
		JLabel besttime = new JLabel();
		if(best > 0) besttime.setText(Utils.getTime(best));
		else besttime.setText("-:--");
		panelClock.add(besttime, "4, 3, fill, default");
		
		JLabel lblLevel = new JLabel("Level 1");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		gameSettingsBar.add(lblLevel, "2, 1, fill, default");
		
		JButton btnGiveup = new JButton("Aufgeben");
		btnGiveup.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				playground.repaint();
			}
		});
		btnGiveup.setFocusable(false);
		gameSettingsBar.add(btnGiveup, "3, 1, default, fill");

		this.setFocusable(false);
		
		playground = new Playground(this);
		getContentPane().add(playground, "1, 3, fill, fill");
		playground.setFocusable(true);
		playground.requestFocus();
		
	}
	
	public void raiseSeconds() {
		this.playedSeconds++;
		this.setTimer(this.playedSeconds);
	}
	
	public void setTimer(int seconds) {
		this.gametimer.setText(Utils.getTime(seconds));
	}
	
	/*
	 * Getter Methodes
	 */
	
	public int getSeconds() {
		return this.playedSeconds;
	}
	
	public Playground getPlayground() {
		return playground;
	}
	
	public MainFrame getMainFrame() {
		return mainframe;
	}
	
	public int getLevel() {
		return level;
	}
	
	public Figure getFigure() {
		return figure;
	}

}
