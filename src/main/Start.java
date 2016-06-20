package main;

import frames.MainFrame;
import server.Server;

public class Start {

	public static void main(String[] args) {
		Server.main(null);
		MainFrame window = new MainFrame();
		window.setVisible(true);
	}
	
}
