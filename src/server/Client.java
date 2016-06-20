package server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.ArrayList;

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
	void start() {
				

			/*
				ArrayList<ArrayList<String>> levelFiles = stub.getLevelByID(1);
				for(ArrayList<String> file : levelFiles) {
					for(String line : file) {
						System.out.println(line);
					}
					System.out.println();
				}
			*/
//			ArrayList<Integer> level = stub.getLevel();
//			for(int i : level) {
//				System.out.println(i);
//			}
			
			//Level le = stub.getLevelByID(1);
//			for(LevelFile file : le.getFiles()) {
//				System.out.println("File: "+file.getName());
//				for(String line : file.getLines()) {
//					System.out.println(line);
//				}
//			}
		
	}
	
	public ArrayList<Integer> getLevelList() {
		ArrayList<Integer> level = new ArrayList<Integer>();
		try {
			level = stub.getLevel();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
		return level;
	}
}
