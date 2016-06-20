package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

import utils.Utils;

public class Level implements Serializable {

	/**
	 * 
	 */
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
	
	public ArrayList<LevelFile> getFiles() {
		return files;
	}
	
	public int getId() {
		return levelId;
	}
	
	public boolean addFile(LevelFile file) {
		return this.files.add(file);
	}
	
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
	
}
