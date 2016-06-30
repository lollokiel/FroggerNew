package main;

import frames.MainFrame;
import server.Server;

/**
 * Main-Klasse
 * Startet beim Ausführen sowohl das Menüfenster, als auch den Server.
 */
public class Start {

	public static void main(String[] args) {
		Server.main(null);
		MainFrame window = new MainFrame();
		window.setVisible(true);
	}
	
}
