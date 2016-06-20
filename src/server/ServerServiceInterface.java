package server;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;

public interface ServerServiceInterface extends Remote {

	public Level getLevelByID(int levelId) throws RemoteException;
	
	public ArrayList<Integer> getLevel() throws RemoteException;
	
}
