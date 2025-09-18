package com.easybank.model;

public class CompteEpargne extends Compte {

	protected double tauxInteret ;
	
	public CompteEpargne(double tauxInteret, String code, double soldeInitial) {
		super(code, soldeInitial);
		this.tauxInteret = tauxInteret ;
	}
	
	
	@Override
	public boolean retirer(double montant) {
		if (montant <= 0) return false;
		if (solde >= montant) {
			solde -= montant ;
			return true ;
		}
		return false ;
	}
	
	@Override 
	public double calculerInteret() {
		return solde*tauxInteret/100 ;
	}
	
	@Override
    public void afficherDetails() {
        System.out.println("=== Détails du Compte Épargne ===");
        System.out.println("Code: " + code);
        System.out.println("Solde: " + solde + "dh");
        System.out.println("Taux d'intérêt: " + tauxInteret + "%");
        System.out.println("Intérêts calculés: " + calculerInteret() + "dh");
        
    }
	
	public double getTauxInteret() {
		return this.tauxInteret ;
	}
}
