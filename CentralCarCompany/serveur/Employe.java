package serveur;

import java.util.Random;

public class Employe{
	
	Random r = new Random();
	float coef_employe = r.nextInt(2 - 1 + 1) + 1;
	
	public Employe(){
	}

	public float getCoef(){
		return coef_employe;
	}
	
}
