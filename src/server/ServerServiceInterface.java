package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

import remote.rmi.server.Student;

public interface ServerServiceInterface extends Remote {

	public File getStudentById(int matriculationId) throws RemoteException;
}
