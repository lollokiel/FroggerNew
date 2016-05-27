package frames;

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
import utils.Level;
import utils.Settings;

public class Game extends JFrame {

	public Level level;
	public MainFrame mainframe;
	private static final long serialVersionUID = 1L;

	public Game(int levelNum, Figure figure, MainFrame frameFrogger) {
		this.level = new Level("/level/backgroundStructure/"+levelNum+".txt", null);
		this.mainframe = frameFrogger;
		this.setResizable(false);
		this.setTitle("Frogger");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setBounds(100, 100, Settings.COLS*Settings.FIELDSIZE, Settings.ROWS*Settings.FIELDSIZE + 75);
		this.getContentPane().setLayout(new FormLayout(new ColumnSpec[] {
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				RowSpec.decode("default:grow"),
				FormSpecs.RELATED_GAP_ROWSPEC,
				RowSpec.decode(Settings.ROWS*Settings.FIELDSIZE+"px"),}));
		
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
		
		JLabel gametimer = new JLabel("ZEIT_csd");
		panelClock.add(gametimer, "4, 1, fill, default");
		
		JLabel lblBestzeit = new JLabel("Bestzeit:");
		panelClock.add(lblBestzeit, "2, 3");
		
		JLabel besttime = new JLabel("BESTZEIT");
		panelClock.add(besttime, "4, 3, fill, default");
		
		JLabel lblLevel = new JLabel("Level 1");
		lblLevel.setHorizontalAlignment(SwingConstants.CENTER);
		gameSettingsBar.add(lblLevel, "2, 1, fill, default");
		
		JButton btnGiveup = new JButton("Aufgeben");
		btnGiveup.setFocusable(false);
		gameSettingsBar.add(btnGiveup, "3, 1, default, fill");

		this.setFocusable(false);
		
		Playground playground = new Playground(this);
		getContentPane().add(playground, "1, 3, fill, fill");
		playground.setFocusable(true);
		playground.requestFocus();
		
	}

}
