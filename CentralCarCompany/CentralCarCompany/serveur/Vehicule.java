package serveur;

import java.util.Random;

public class Vehicule {
	String marque;
	String modele;
	int annee;
	String immatriculation;
	boolean dispo_reservation;
	boolean dispo_en_recuperation;
	boolean preparation;
	Random r = new Random();
	int coef_vehicule = r.nextInt(30000 - 10000) + 10000;

	
	static int compteur=0;

	public Vehicule(String marque, String modele, int annee, String immatriculation){
		this.marque=marque;
		this.modele=modele;
		this.annee=annee;
		this.immatriculation=immatriculation;

		dispo_reservation=true;
		dispo_en_recuperation=false;
		preparation=false;
	}

	public boolean isDispo_reservation() {
		return dispo_reservation;
	}

	public void setDispo_reservation(boolean dispo_reservation) {
		this.dispo_reservation = dispo_reservation;
	}

	public boolean isDispo_en_recuperation() {
		return dispo_en_recuperation;
	}

	public void setDispo_en_recuperation(boolean dispo_en_recuperation) {
		this.dispo_en_recuperation = dispo_en_recuperation;
	}

	public boolean isPreparation() {
		return preparation;
	}

	public void setPreparation(boolean preparation) {
		this.preparation = preparation;
	}
	
	public int getCoef_vehicule() {
		return coef_vehicule;
	}

	public String toStringClient() {
		return "Marque= " + marque + ", Modele= " + modele + ", Annee= "
				+ annee + ", Immatriculation= " + immatriculation
				;}
	public String toStringServeur() {
		return "Marque= " + marque + ", Modele= " + modele + ", Annee= "
				+ annee + ", Immatriculation= " + immatriculation
				+ ", dispo_reservation= " + dispo_reservation
				+ ", dispo_en_recuperation= " + dispo_en_recuperation
				+ ", preparation= " + preparation ;
	}

}
