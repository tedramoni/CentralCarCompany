package serveur;

import java.util.Scanner;
import java.util.Map.Entry;


public class threadConsole extends threadServeur {
	Scanner sc = new Scanner(System.in);

	public void run(){
		String request="";
		System.out.println("Tapez HELP pour obtenir la liste des commandes");
		while(true){
			request=sc.nextLine();
			request=request.toUpperCase();
			if(request.length() >= 3){
				switch(request.substring(0,3)){
				// on appelle la fonction avec l'immatriculation du v�hicule dans le code de transaction (exemple: BT-45)

				case "DPT" :
					depart(request.substring(4));
					break;
				case "RET" :
					((threadServeur) this.getByName(request.substring(4))).retour(request.substring(4));
					break;
				case "ETF" :
					etat_flotte();;
					break;
				case "VEP" :
					VehiculeEnPreparation();
					break;
				case "VED" :
					VehiculeADispo();
					break;
				case "VES" :
					vehiculeSortis();
					break;
				case "MAD" :
					((threadServeur) this.getByName(request.substring(4))).miseADispo(request.substring(4));
					break;
				case "ACA" :
					afficher_NbrClientEnAttente();
					break;
				case "HEL" :
					afficher_aide();
					break;
				default:
					System.out.println("ERREUR DANS LA REQUETE !");
					break;
				}
			}
			else {
				System.out.println("ERREUR DANS LA REQUETE !");
			}
		}

	}

	public void afficher_aide(){
		System.out.println("DPT/XXX-00 : Signaler le d�part du v�hicule XXX-00\n"
				+ "RET/XXX-00 : Signaler le retour du v�hicule XXX-00\n"
				+ "MAD/XXX-00 : Signaler la mise � disposition du v�hicule XXX-00\n"
				+ "ETF : Afficher l'�tat de la flotte\n"
				+ "VEP : Afficher les v�hicules en pr�paration\n"
				+ "VED : Afficher les v�hicules mis � disposition\n"
				+ "VES : Afficher les v�hicules sortis\n"
				+ "ACA : Afficher le nombre de client en attente\n"
				);
	}

	public void afficher_NbrClientEnAttente(){
		System.out.println("Le nombre de clients en attente pour le v�hicule est de : " + clientEnAttente);
	}

	public void etat_flotte() {
		System.out.println("ETAT FLOTTE \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 

			System.out.println(v.getValue().toStringServeur());
		}

	}


	public void depart(String immatriculation){
		Flotte_vehicules.get(immatriculation).setDispo_en_recuperation(false);
	}
	public void vehiculeSortis() {
		System.out.println("VEHICULES SORTIS \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 
			if (v.getValue().isDispo_reservation() == false && v.getValue().isPreparation() == false && v.getValue().isDispo_en_recuperation() == false){
				System.out.println(v.getValue().toStringServeur());
			}

		}

	}
	public void VehiculeADispo() {
		System.out.println("VEHICULES A DISPOSITION \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 
			if (v.getValue().isDispo_en_recuperation()== true ){
				System.out.println(v.getValue().toStringServeur());
			}

		}

	}
	public void VehiculeEnPreparation() {
		System.out.println("VEHICULES EN PREPARATION \n");
		for(Entry<String, Vehicule> v : Flotte_vehicules.entrySet()){ 
			if (v.getValue().isPreparation() == true){
				System.out.println(v.getValue().toStringServeur());
			}

		}
	}
}