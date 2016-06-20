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

	@Override
	public Level getLevelByID(int levelId) throws RemoteException {
		
		Level level = new Level(levelId);	
//		ArrayList<ArrayList<String>> files = new ArrayList<ArrayList<String>>();
//		
//		for(LevelFile levelFile : level.getFiles()) {
//			files.add(levelFile.getLines());
//		}
		
		return level;
	}

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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return level;
	}

}
