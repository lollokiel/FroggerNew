package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

/**
 * Diese Klasse gilt als Schnittstelle zum Server. In dieser Klasse wird die Verbindung zum Server aufgebaut,
 * und Anfragen an den Server gestellt.
 */
public class Client {

	Registry registry;
	ServerServiceInterface stub;
	
	public Client() {
		try {
			
			registry = LocateRegistry.getRegistry("localhost", 1099);
			stub = (ServerServiceInterface) 
					registry.lookup("ServerService");
			
		} catch (RemoteException | NotBoundException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Übergibt alle nötigen Dateien für ein Level
	 * @param id LevelID
	 * @return Levelobjekt mit allen Leveldateien
	 */
	public Level getLevel(int id) {
		try {
			return stub.getLevelByID(id);
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Übergibt Liste der Levelnummern
	 * @return Levelnummern
	 */
	public ArrayList<Integer> getLevelList() {
		ArrayList<Integer> level = new ArrayList<Integer>();
		try {
			level = stub.getLevel();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return level;
	}
	
	/**
	 * @return Übergibt alle Level mit allen Leveldateien
	 */
	public ArrayList<Level> getAllLevel() {
		try {
			return stub.getAllLevel();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return null;
	}
}
