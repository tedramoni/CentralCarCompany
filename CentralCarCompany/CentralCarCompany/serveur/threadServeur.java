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

import javax.management.ImmutableDescriptor;

public class threadServeur extends NamedThread  {
	Socket connectionSocket;
	String request;
	static int clientEnAttente = 0;
	boolean attend = false;


	@SuppressWarnings("serial")
	final static ConcurrentHashMap<String, Vehicule> Flotte_vehicules = new ConcurrentHashMap<String, Vehicule>() {
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
	LinkedBlockingQueue<Employe> liste_employe = new LinkedBlockingQueue<Employe>();

	BufferedReader inFromClient;
	PrintWriter outToClient;

	public threadServeur(Socket s, int nbemployes){
		connectionSocket = s;
		for (int i = 1; i <= nbemployes; ++i){
			liste_employe.add(new Employe());
		}
	}

	public threadServeur(){
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
			outToClient.println("\nPour r�server un v�hicule vous devez entrer le code de transaction tel que pr�sent� ci-dessous :\n RSV/XXX-00");
			request ="";
			while (((request = inFromClient.readLine()) != null)){

				// Lecture des donnees arrivant du client
				request = request.toUpperCase();
				// Emission des donnees au client


				if (request.length()>=3){
					switch(request.substring(0,3)){
					// on appelle la fonction avec l'immatriculation du v�hicule dans le code de transaction (exemple: BT-45)
					case "RSV" :
						reserver(request.substring(4));
						break;
					case "QIT" :
						quitter();
						break;
					default:
						if (!request.equals("o")|| !request.equals("n") ){
							outToClient.println("ERREUR DANS LA REQUETE ! \n Veuillez r�essayer!");
						}
						break;
					}
				}
				else {
					outToClient.println("ERREUR DANS LA REQUETE ! \n Veuillez r�essayer!");
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

	private void quitter() {
		try {
			inFromClient.close();
			outToClient.close();
			connectionSocket.close();
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void reserver(String substring) {
		Vehicule v = Flotte_vehicules.get(substring);
		if(v.isDispo_reservation() == false){
			outToClient.println("Le v�hicule "+substring+ "n'est actuellement pas disponible � la r�servation pour le moment. \n Voulez vous patienter ? \n o/n ");

			try {
				switch(inFromClient.readLine().toUpperCase()){
				case "O" :
					++clientEnAttente;
					attend = true;
					String msg = "Veuillez patientier";
					outToClient.println(msg); 
					while(v.isDispo_reservation() == false && attend==true){
						try {
							this.sleep(50000);
							msg = msg.concat(".");
							outToClient.println(msg); 
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					break;
				case "N" :
					afficher_flotte();
					break;
				default:
					outToClient.println("ERREUR DANS LA REPONSE ! \n Veuillez r�essayer!");
					break;
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
		if(attend == true){
			attend = false;
			--clientEnAttente;
			outToClient.println("Le v�hicule est revenu. \n Nous allons pouvoir proc�der � la r�servation");
		}


		v.setDispo_reservation(false);
		outToClient.println("Votre v�hicule est reserv�, la pr�paration va �tre lanc�e.");
		this.setName(substring);
		this.register();
		this.preparer(substring);
	}

	public void afficher_flotte(){
		outToClient.println("BIENVENUE SUR NOTRE SITE DE RESERVATION DE VOITURES \n VOICI NOTRE FLOTTE \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 

			outToClient.println(v.getValue().toStringClient());
		}

	}


	public void miseADispo(String immatriculation){
		Flotte_vehicules.get(immatriculation).setDispo_en_recuperation(true);
		Flotte_vehicules.get(immatriculation).setPreparation(false);
		outToClient.println("Le vehicule : " + immatriculation + " est � votre disposition dans notre garage \n Vous pouvez venir le r�cuperer");
	}



	public synchronized void preparer(String immatriculation){
		Flotte_vehicules.get(immatriculation).setPreparation(true);
		Employe emp;
		try {
			outToClient.println("Votre v�hicule ("+immatriculation+") est en pr�paration.");
			emp = liste_employe.take();
			this.wait((long)(emp.getCoef()* Flotte_vehicules.get(immatriculation).getCoef_vehicule()));
			liste_employe.add(emp);
			this.miseADispo(immatriculation);
		} catch (InterruptedException e) {
			Flotte_vehicules.get(immatriculation).setDispo_reservation(true);
			Flotte_vehicules.get(immatriculation).setPreparation(false);
			outToClient.println("Suite a un probl�me ind�pendant de notre volont�, votre r�servation n'a pas abouti. \n Veuillez reessayer ult�rieurement.");

			e.printStackTrace();
		}


	}
	public void retour(String immatriculation){
		Flotte_vehicules.get(immatriculation).setDispo_reservation(true);
		outToClient.println("Le retour du v�hicule "+immatriculation + " � bien �t� enregistr�. \n Nous esp�rons que l'exp�rience vous a plu");
		outToClient.println("CONNEXION FERM�E");
		try {
			/*inFromClient.close();
			outToClient.close();
			connectionSocket.close();*/
			this.finalize();
		} catch (Throwable e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}




