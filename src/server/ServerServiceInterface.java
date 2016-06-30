package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

/**
 * Interface für das Servermodel. Definiert Methoden, die mit dem Server ausführbar sein sollen.
 */
public interface ServerServiceInterface extends Remote {

	public Level getLevelByID(int levelId) throws RemoteException;
	
	public ArrayList<Integer> getLevel() throws RemoteException;
	
	public ArrayList<Level> getAllLevel() throws RemoteException;
	
}
