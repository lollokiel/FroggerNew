package utils;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.table.AbstractTableModel;

import objects.Player;

public class HighscoreTableModel extends AbstractTableModel {

	private static final long serialVersionUID = 1L;
	ArrayList<Player> games = new ArrayList<Player>();
	String[] colNames = {"Name", "Punkte"};
	
	public HighscoreTableModel() {
		
	}
	
	/*
	 * Konstrukter zum erstellen der Liste aus Spielern
	 * Das Spielerobjekt beinhaltet Namen und Punktestand
	 */
	public HighscoreTableModel(Iterable<Player> players) {
		Iterator<Player> itPlayer = players.iterator();
		while(itPlayer.hasNext()) {
			Player player = itPlayer.next();
			games.add(player);
		}
	}
	
	public void addPlayer(Player pPlayer) {
		games.add(pPlayer);
	}
	
	@Override
	public String getColumnName(int col) {
	    return colNames[col];
	}
	
	@Override
	public int getRowCount() {
		return games.size();
	}

	@Override
	public int getColumnCount() {
		return 2;
	}

	@Override
	public Object getValueAt(int iRowIndex, int iColumnIndex) {
		Player p = games.get(iRowIndex);
		return(iColumnIndex == 0) ? p.getsName() : p.getiPoints();
	}

}
