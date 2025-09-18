package com.easybank.model;

public abstract class Compte {
	
//	Attributes
	protected String code ;
	protected double solde ;
	
	
//	Constructor 
	public Compte(String code, double soldeInitial) {
		this.code = code ;
		this.solde = soldeInitial ;
	}
	
	
//	Methods
	public String getCode() {
		return this.code;
	}
	public double getsolde() {
		return this.solde ;
	}
	
	public abstract double retirer(double montant);
	public abstract double calculerInteret();
	public abstract void afficherDetails();
	
	public boolean verser(double montant) {
		if(montant <= 0) return false;
		this.solde += montant ;
		return true ; 
	}
	
}
