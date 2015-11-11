package serveur;


import java.net.*;

public class Serveur2 {
	
	public static void main(String argv[]) throws Exception {
		
		// Creation de la socket d'accueil au port 8080
		@SuppressWarnings("resource")
		ServerSocket welcomeSocket = new ServerSocket(8080);
		new threadConsole().start();
		while(true) {

			// Attente d'une demande de connexion sur la socket d'accueil
			Socket connectionSocket = welcomeSocket.accept();
			 new threadServeur(connectionSocket).start();
			
		

		} // boucle et attend la connexion d'un nouveau client
	}


}
