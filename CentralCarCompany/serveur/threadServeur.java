package serveur;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class threadServeur extends Thread  {
	Socket connectionSocket;
	String request;


	@SuppressWarnings("serial")
	static ConcurrentHashMap<String, Vehicule> Flotte_vehicules = new ConcurrentHashMap<String, Vehicule>() {
		{put("TSL-40",new Vehicule("Tesla", "X", 2014,"TSL-40")); 
		put("CIT-28",new Vehicule("Citro�n", "C3", 2003,"CIT-28")); 
		put("PGT-39",new Vehicule("Peugeot", "308GTi", 2008,"PGT-39")); 
		put("MCD-88",new Vehicule("Mercedes", "Citan", 2009,"MCD-88")); 
		put("MIN-27",new Vehicule("Mini", "Cooper", 2010,"MIN-27")); 
		put("MCD-40",new Vehicule("Mercedes", "Citan", 2010,"MCD-40")); 
		put("TSL-90",new Vehicule("Tesla", "S", 2012,"TSL-90")); 
		put("PGT-10",new Vehicule("Peugeot", "208", 2003,"PGT-10")); 
		put("RNT-25",new Vehicule("Renault", "Megane", 1999,"RNT-25")); 
		put("MCD-48",new Vehicule("Mercedes", "Vito", 2004,"MCD-48")); 
		put("RNT-12",new Vehicule("Renault", "Clio", 2005,"RNT-12")); 
		put("PGT-49",new Vehicule("Peugeot", "RCZ", 2006,"PGT-49")); 
		put("TYT-67",new Vehicule("Toyota", "Prius", 2007,"TYT-67")); 
		put("MCD-77",new Vehicule("Mercedes", "SI", 2008,"MCD-77")); 
		put("RNT-48",new Vehicule("Renault", "Megane", 1999,"RNT-48")); 
		put("PGT-97",new Vehicule("Peugeot", "ION", 2005,"PGT-97")); 
		put("FRR-01",new Vehicule("Ferrari", "S415", 2009,"FRR-01")); 
		put("PCH-88",new Vehicule("Porsche", "Panamera", 2000,"PCH-88")); 
		put("MCD-99",new Vehicule("Mercedes", "Cla", 2001,"MCD-99")); 
		put("CIT-24",new Vehicule("Citro�n", "DS", 2002,"CIT-24")); 
		put("CIT-64",new Vehicule("Citro�n", "C4", 2003,"CIT-64")); 
		put("ASM-75",new Vehicule("Aston Martin", "Vanquish", 2011,"ASM-75"));  
		}
	};

	@SuppressWarnings("serial")
	LinkedBlockingQueue<Employe> liste_employe = new LinkedBlockingQueue<Employe>(){
		{add(new Employe()); 
		add(new Employe()); 
		add(new Employe()); 
		add(new Employe()); 
		add(new Employe()); 
		}
	};
	BufferedReader inFromClient;
	PrintWriter outToClient;

	public threadServeur(Socket s){
		connectionSocket = s;
	}

	@Override
	public void run() {

		// Creation du flux en entree attache a la socket

		try {
			inFromClient = new BufferedReader(
					new InputStreamReader(
							connectionSocket.getInputStream()));
			// Creation du flux en sortie attache a la socket
			outToClient = new PrintWriter(
					new BufferedWriter(
							new OutputStreamWriter(
									connectionSocket.getOutputStream())),true);
			afficher_flotte();
			request ="";
			while (!request.equals("STOP")){

				// Lecture des donnees arrivant du client
				request = inFromClient.readLine();
				request = request.toUpperCase();
				// Emission des donnees au client


				if (!request.equals("STOP")){
					switch(request.substring(0,3)){
					// on appelle la fonction avec l'immatriculation du v�hicule dans le code de transaction (exemple: BT-45)
					case "RSV" :
						Serveur.reserver(request.substring(4));
						break;
					case "DPT" :
						depart(request.substring(4));
						break;
					case "RET" :
						retour(request.substring(4));
						break;
					case "QIT" :
						Serveur.quitter(request.substring(4));
						break;
					case "ETF" :
						etat_flotte();;
						break;
					case "VEP" :
						Serveur.preparer(request.substring(4));
						break;
					case "VED" :
						miseADispo(request.substring(4));
						break;
					case "VES" :
						Serveur.sortie(request.substring(4));
						break;
					default:
						System.out.println("ERREUR DANS LA REQUETE !");
						break;

					}
				}
				else {
					outToClient.println("Vous avez demand� l'arret du service!");
				}


			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}


		try {
			connectionSocket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void afficher_flotte(){
		outToClient.println("BIENVENUE SUR NOTRE SITE DE RESERVATION DE VOITURES \n VOICI NOTRE FLOTTE \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 

			outToClient.println(v.getValue().toStringClient());
		}

	}

	public void etat_flotte() {
	outToClient.println("ETAT FLOTTE \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 

			System.out.println(v.getValue().toStringServeur());
		}
		
	}
	public void miseADispo(String immatriculation){
		Flotte_vehicules.get(immatriculation).setDispo_en_recuperation(true);
		outToClient.println("Le vehicule : " + immatriculation + "est � votre disposition dans notre garage \n Vous pouvez venir le r�cuperer");
	}
	
	public void retour(String immatriculation){
		Flotte_vehicules.get(immatriculation).setDispo_reservation(true);
	}
	
	public void depart(String immatriculation){
		Flotte_vehicules.get(immatriculation).setDispo_en_recuperation(false);
	}
	public void vehiculeSortis(String substring) {
		outToClient.println("VEHICULES SORTIS \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 
			if (v.getValue().isDispo_reservation() == false && v.getValue().isPreparation() == false && v.getValue().isDispo_en_recuperation() == false){
				System.out.println(v.getValue().toStringServeur());
			}
			
		}
		
	}
	public void VehiculeADispo(String substring) {
		outToClient.println("VEHICULES A DISPOSITION \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 
			if (v.getValue().isDispo_en_recuperation()== true ){
				System.out.println(v.getValue().toStringServeur());
			}
			
		}
		
	}
	public void VehiculeEnPreparation(String substring) {
		outToClient.println("VEHICULES EN PREPARATION \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 
			if (v.getValue().isPreparation() == true){
				System.out.println(v.getValue().toStringServeur());
			}
			
		}
		
	}
}
