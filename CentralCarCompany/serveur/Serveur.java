package serveur;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

public class Serveur {
	@SuppressWarnings("serial")
	ConcurrentHashMap<String, Vehicule> Flotte_vehicules = new ConcurrentHashMap<String, Vehicule>() {
		{put("TSL-40",new Vehicule("Tesla", "X", 2014,"TSL-40")); 
		put("CIT-28",new Vehicule("Citroën", "C3", 2003,"CIT-28")); 
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
		put("CIT-24",new Vehicule("Citroën", "DS", 2002,"CIT-24")); 
		put("CIT-64",new Vehicule("Citroën", "C4", 2003,"CIT-64")); 
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
	
	
	static ServerSocket welcomeSocket = null;

	public static void main() throws IOException{
		boolean listeningSocket = true;
		try {
			welcomeSocket = new ServerSocket(8080);
		} catch (IOException e) {
			System.err.println("Could not listen on port: 8080");
		}

		while(listeningSocket){
			Socket connectionSocket = welcomeSocket.accept();
			ServerThread thread = new ServerThread(connectionSocket);
			thread.start();
		}
		welcomeSocket.close();       

	}
	

	
	public static String reserver(String immatriculation){
		return immatriculation;
		//TODO
	}
	
	public static String preparer(String immatriculation){
		//CREER UN POOL DE THREADS !!
		return immatriculation;
	}
	

	
	public static String depart(String immatriculation){
		return immatriculation;
		//TODO
	}
	
	
	
	public static String quitter(String immatriculation){
		return immatriculation;
		//TODO
	}

	public static String sortie(String substring) {
		return substring;
		
	}
	

	
	
}
