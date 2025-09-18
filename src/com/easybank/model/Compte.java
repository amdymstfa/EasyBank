package com.easybank.model;

import java.util.List; 
import java.util.ArrayList;
import java.math.BigDecimal;

public abstract class Compte {
	
//	Attributes
	protected String code ;
	protected BigDecimal solde ;
	protected List<Operation> listeOperations ;
	
	
//	Constructor 
	public Compte(String code, BigDecimal soldeInitial) {
		this.code = code ;
		this.solde = soldeInitial ;
		this.listeOperations = new ArrayList<>();
	}
	
	
//	Methods
	public String getCode() {
		return this.code;
	}
	public BigDecimal getsolde() {
		return this.solde ;
	}
	public List<Operation> getListeOerations(){
		return this.listeOperations;
	}
	
	public abstract boolean retirer(BigDecimal montant);
    public abstract BigDecimal calculerInteret();
    public abstract void afficherDetails();
	
	public boolean verser(BigDecimal montant) {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) return false;
        solde = solde.add(montant);
        return true;
    }
	
	public void ajouterOperation(Operation operation) {
		this.listeOperations.add(operation);
	}
}
