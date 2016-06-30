package server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

/**
 * Server Klasse
 * Aufgaben sind es, Level-Objekte aus Dateien zu erstellen und diese dem Client zu übergeben.
 *
 */
public class Server implements ServerServiceInterface {

	public Server() throws RemoteException {
		super();
	}

	public static void main(String[] argv) {
		try {
			Registry registry = LocateRegistry.createRegistry(1099);
						
			ServerServiceInterface stub = (ServerServiceInterface) 
					UnicastRemoteObject.exportObject(new Server(), 0);

			registry.rebind("ServerService", stub);

		}
		catch (RemoteException ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Gibt alle Leveldatein eines Levels zurück
	 */
	@Override
	public Level getLevelByID(int levelId) throws RemoteException {
		
		Level level = new Level(levelId);		
		return level;
	}

	/**
	 * Gibt alle Levelnummern der Level, die auf dem Serverliegen, zurück
	 */
	@Override
	public ArrayList<Integer> getLevel() throws RemoteException {
		ArrayList<Integer> level = new ArrayList<Integer>();
		try {
			BufferedReader br = new BufferedReader(new FileReader(new File("res/server/level/list.txt")));
		
			String line;
			while((line = br.readLine()) != null) {
				level.add(Integer.parseInt(line));
			}
			br.close();
			
		} catch (NumberFormatException |IOException e) {
			e.printStackTrace();
		}
		return level;
	}

	/**
	 * Gibt alle Leveldateien aller Level zurück
	 */
	@Override
	public ArrayList<Level> getAllLevel() throws RemoteException {
		ArrayList<Integer> levelNumList = getLevel();
		ArrayList<Level> levelList = new ArrayList<Level>();
		for(int l : levelNumList)
			levelList.add(getLevelByID(l));
		return levelList;
	}

}
