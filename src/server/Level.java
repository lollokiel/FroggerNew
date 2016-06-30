package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import utils.Utils;

/**
 * Diese Klasse dient als Übertragungsklasse für Level vom Server.
 * Es fasst ein Level zusammen, in dem es die Levelnummer und alle notwendigen
 * Leveldateien beinhaltet.
 */
public class Level implements Serializable {

	private static final long serialVersionUID = 1L;
	private int levelId;
	private ArrayList<LevelFile> files = new ArrayList<LevelFile>();
	
	public Level(int id, Iterable<LevelFile> fileList) {
		this.levelId = id;
		
		Iterator<LevelFile> it = fileList.iterator();
		LevelFile file;
		while((file = it.next()) != null) {
			files.add(file);
		}
	}
	
	public Level(int id) {
		this.levelId = id;

		this.addFile("res/server/level/backgroundStructure/"+id+".txt");
		this.addFile("res/server/level/objectStructure/"+id+".txt");
	}
	
	/**
	 * @return Eine Liste aller LevelDateien
	 */
	public ArrayList<LevelFile> getFiles() {
		return this.files;
	}
	
	/**
	 * @return Level-ID
	 */
	public int getId() {
		return this.levelId;
	}
	
	/**
	 * Fügt eine LevelDatei zum Level hinzu
	 * @param file LevelDatei, die hinzugefügt werden soll
	 * @return true, wenn erfolgreich; false, wenn nicht
	 */
	public boolean addFile(LevelFile file) {
		return this.files.add(file);
	}
	
	/**
	 * Erstellt eine neue LevelFile aus einem Pfad und fügt diese dem Level hinzu
	 * @param path Pfad zur Datei, die dem Level hinzugefügt werden soll
	 * @return true, wenn erfolgreich hinzugefügt; false, wenn nicht
	 */
	public boolean addFile(String path) {
		
		try {
			
			File file = new File(path);
			if(file.exists()) {
				
					ArrayList<String> lines = new ArrayList<String>();
					BufferedReader br = new BufferedReader(new FileReader( file ));
					
					String line;
					while((line = br.readLine()) != null) {
						lines.add(line);
					}
					
					br.close();

					LevelFile levelFile = new LevelFile(Utils.getFolder(file)+"/"+file.getName(), lines);
					this.addFile(levelFile);
					
					return true;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return false;
	}
	
	
	/**
	 * Prüft, ob in dem Level eine Datei vorhanden ist
	 * @param name Name der Datei, die geprüft werden soll
	 * @return true, wenn Datei vorhanden; false, wenn nicht
	 */
	public boolean hasFile(String name) {
		
		boolean found = false;
		
		for(LevelFile file : files) {
			if(file.getName().equals(name) ) {
				found = true;
				break;
			}
		}
		
		return found;
		
	}
	
}
