package utils;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

public class HighscoreTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	ArrayList<Player> games = new ArrayList<Player>();
	String[] colNames = {"Name", "Zeit"};
	
	public HighscoreTableModel() {
		
	}
	
	/*
	 * Konstrukter zum erstellen der Liste aus Spielern
	 * Das Spielerobjekt beinhaltet Namen und Punktestand
	 */
	public HighscoreTableModel(Iterable<Player> players) {
		
		if(players != null) {
			Iterator<Player> itPlayer = players.iterator();
			while(itPlayer.hasNext()) {
				Player player = itPlayer.next();
				this.games.add(player);
			}
		}
		
		this.games.sort(null);
	}
	
	public void addPlayer(Player pPlayer) {
		this.games.add(pPlayer);
	}
	
	@Override
	public String getColumnName(int col) {
	    return this.colNames[col];
	}
	
	@Override
	public int getRowCount() {
		return this.games.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int iRowIndex, int iColumnIndex) {
		Player p = this.games.get(iRowIndex);
		return(iColumnIndex == 0) ? p.getName() : (int)(p.getTime()/60)+":"+Utils.addZero((int)(p.getTime()%60));
	}

}
