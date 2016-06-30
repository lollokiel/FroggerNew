package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Ist die Darstellung einer Datei als Klasse, in dem die einzelnen Zeilen
 * als Strings in einer Liste gespeichert werden.
 * Zusätzlich hat diese Klasse noch das Namens-Attribut.
 */
public class LevelFile implements Serializable {

	private static final long serialVersionUID = 1L;
	private String name; 
	private ArrayList<String> lines = new ArrayList<String>();
	
	public LevelFile(String name, Iterable<String> lineList) {
		
		this.name = name;
		
		Iterator<String> it = lineList.iterator();
		String line;
		while(it.hasNext()) {
			line = it.next();
			lines.add(line);
		}
	}
	
	/**
	 * @return Alle Zeilen der Datei
	 */
	public ArrayList<String> getLines() {
		return lines;
	}
	
	/**
	 * @return Den Namen der Datei
	 */
	public String getName() {
		return name;
	}

}
