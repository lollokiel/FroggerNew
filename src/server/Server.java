package server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements ServerServiceInterface {

	public Server() throws RemoteException {
		super();
	}

	public static void main(String[] argv) {

		/*
		 * Use
		 * 		(1) LocateRegistry.createRegistry(...) 
		 * 			Create and export a Registry instance on the local host that accepts requests 
		 * 			on the specified port.
		 * 
		 * 		(2) UnicastRemoteObject.exportObject(...) 
		 * 			Export the Remote object stub ( = instance of StudentServiceLoesung) to make 
		 * 			it available to receive incoming calls
		 *
		 * 		(3) Registry.rebind(...) 
		 * 			Replace the binding for a specified name (e.g., "StudentService")
		 * 		  	in the Registry instance created in (1) with the supplied remote reference
		 *
		 */
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
}
