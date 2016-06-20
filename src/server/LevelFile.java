package server;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

public class LevelFile implements Serializable {

	/**
	 * 
	 */
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
	
	public ArrayList<String> getLines() {
		return lines;
	}
	
	public String getName() {
		return name;
	}

}
