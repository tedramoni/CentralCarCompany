package serveur;

import java.io.*;
import java.net.*;


public class ServerThread extends Thread{

	private Socket socket = null;
	public String request;

	public ServerThread(Socket socket) {

		super("ServerThread");
		this.socket = socket;

	}

	public void run(){

		// Création du flux en entrée attaché a la socket
		BufferedReader inFromClient = null;
		try {
			inFromClient = new BufferedReader(
					new InputStreamReader(
							socket.getInputStream()));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Création du flux en sortie attachée a la socket
		PrintWriter outToClient = null;
		try {
			outToClient = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									socket.getOutputStream())),true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// Lecture des données arrivant du client
		request="";

		while(!request.equals("stop")){
			try {
				request = inFromClient.readLine();
				// analyse du code de transaction (exemple: RSV/BT-45)
				// on prend les 3 premieres lettres du code (exemple: RSV)
				switch(request.substring(0,3)){
					// on appelle la fonction avec l'immatriculation du véhicule dans le code de transaction (exemple: BT-45)
					case "RSV" :
						Serveur.reserver(request.substring(4));
						break;
					case "DPT" :
						Serveur.depart(request.substring(4));
						break;
					case "RET" :
						Serveur.retour(request.substring(4));
						break;
					case "QIT" :
						Serveur.quitter(request.substring(4));
						break;
					case "ETF" :
						Serveur.etat_flotte(request.substring(4));
						break;
					case "VEP" :
						Serveur.preparer(request.substring(4));
						break;
					case "VED" :
						Serveur.miseADispo(request.substring(4));
						break;
					case "VES" :
						Serveur.sortie(request.substring(4));
						break;
					default:
						System.out.println("ERREUR DANS LA REQUETE !");
						break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			outToClient.println(socket.getInetAddress() + " " + request.toUpperCase());
		}
	}

}