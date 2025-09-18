package com.easybank.model;

public class CompteCourant extends Compte {

	protected double decouvert ;
	
	public CompteCourant(double decouvert, String code, double soldeInitial) {
		super(code, soldeInitial);
		this.decouvert = decouvert ;
	}
	

	
	@Override
	public boolean retirer(double montant) {
		if (montant < 0) return false ;
		if(this.solde - montant >= -this.decouvert) {
			solde -= montant ;
		}
		return false ;
	}

	@Override
	public double calculerInteret() {
		return 0 ;
	}
	
	@Override 
	public void afficherDetails() {
        System.out.println("=== Détails du Compte Courant ===");
        System.out.println("Code: " + code);
        System.out.println("Solde: " + solde + "dh");
        System.out.println("Découvert autorisé: " + decouvert + "dh");
        
    }
	
	public double getDecouvert() {
		return this.decouvert;
	}
}
