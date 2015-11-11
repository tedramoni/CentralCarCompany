package serveur;

import java.util.Scanner;


public class threadConsole extends NamedThread {
	private threadServeur ts = new threadServeur();
	Scanner sc = new Scanner(System.in);
	
	public void run(){
		String request="";
		
		while(true){
			request=sc.nextLine();
			
			switch(request.substring(0,3)){
			// on appelle la fonction avec l'immatriculation du v�hicule dans le code de transaction (exemple: BT-45)

			case "DPT" :
				ts.depart(request.substring(4));
				break;
			case "RET" :
				((threadServeur) this.getByName(request.substring(4))).retour(request.substring(4));
				break;
			case "ETF" :
				ts.etat_flotte();;
				break;
			case "VEP" :
				ts.VehiculeEnPreparation();
				break;
			case "VED" :
				ts.VehiculeADispo();
				break;
			case "VES" :
				ts.vehiculeSortis();
				break;
			case "MAD" :
				((threadServeur) this.getByName(request.substring(4))).miseADispo(request.substring(4));
				break;
			case "ACA" :
				ts.afficher_NbrClientEnAttente();
				break;
			default:
				System.out.println("ERREUR DANS LA REQUETE !");
				break;
			}
		}
		
	}
	
}