package utils;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;

import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 * Klasse, die alle möglichen, notwendigen Methoden enthällt, die nicht einer genauen Klasse zugewiesen sind.
 * Enthält Methode zum...
 * ... Hintergrundbild formen
 * ... Datei Einlesen und als String zurück geben
 * ... Umwandeln der Sekunden in Min:Sek
 * ... Hinzuügen einer 0 bei Zahlen < 0 für Sekundenanzeige
 * ... Prüfen auf Vollständigkeit eines Levels
 * ... extrahieren des Parent-Ordners einer Datei
 * ... Datei erstellen aus Name, Pfad, und Zeilenliste
 * ... Alert-Fenster anzeigen
 * @author Lollo
 *
 */
public class Utils {

	/**
	 * Erstellt ein BufferedImage von einem übergebenden Pfad und gibt dieses zurück
	 */
	public static BufferedImage formTile(String path, int width, int height) throws IOException {
		return ImageIO.read(Utils.class.getResource(path)).getSubimage(0, 0, width, height);
	}
	
	/**
	 * Ließt eine Datei ein und gibt diese als String zurück
	 */
	public static String readFile(String path) {
		
		try {
			StringBuilder builder = new StringBuilder();

			File file =  new File("res"+path);
		
			if(file.exists()) {
				BufferedReader br = new BufferedReader( new FileReader(file));
				
				String line;
				while((line = br.readLine()) != null) {
					builder.append(line + "\n");
				}
				br.close();
			}
			
			return builder.toString();
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}	
	
	/**
	 * Gibt einen Timer aus Minuten und Sekunden abhängig einer übergegebenen Sekundenzahl zurück
	 */
	public static String getTime(int seconds) {
		return (int)(seconds/60)+":"+addZero(seconds%60);
	}
	
	/**
	 * Hängt bei Zahlen mit einer Ziffer eine führende 0 an -> 9 = 09
	 */
	public static String addZero(int i) {
		if(i < 10) {
			return "0"+i;
		}
		return i+"";
	}
	
	/**
	 * Check Level OK
	 */
	public static boolean checkLevel(int level) {
		if( new File("res/level/backgroundStructure/"+level+".txt").exists() ) {
			if( new File("res/level/objectStructure/"+level+".txt").exists() ) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Extrahiert Parent-Ordnernamen aus Datei
	 */
	public static String getFolder(File file) {
		File fileParent = file.getParentFile();
		String pathParent = fileParent.getParent();
		
		String filePath = fileParent.getPath();
		
		String folder = filePath.substring(pathParent.length()+1, filePath.length());
		return folder;
	}
	
	/**
	 * Erstellt eine Datei aus Name, Pfad und Zeilen
	 */
	public static void createFile(String name, String path, Iterable<String> lines) {
		
			try {
				File file = new File(path+"/"+name);
				if(!file.exists())
					file.createNewFile();
				
				BufferedWriter bw = new BufferedWriter( new FileWriter( file ) );
				
				Iterator<String> it = lines.iterator();
				while(it.hasNext()) {
					bw.write(it.next());
					if(it.hasNext())
						bw.newLine();
				}
				
				bw.close();
				
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		
		
	}
	
	/**
	 * Erstellt eine Alert-Message
	 * @param message anzuzeigende Nachricht
	 */
	public static void alert(String message) {
		JOptionPane.showMessageDialog(null, message, "Achtung!", JOptionPane.PLAIN_MESSAGE);
	}
	
	
}
