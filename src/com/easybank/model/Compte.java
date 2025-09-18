package com.easybank.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public abstract class Compte {
    protected String code;
    protected BigDecimal solde;
    protected List<Operation> listeOperations;
    
    public Compte(String code, BigDecimal soldeInitial) {
        this.code = code;
        this.solde = soldeInitial;
        this.listeOperations = new ArrayList<>();
    }
    
    public String getCode() { return code; }
    public BigDecimal getSolde() { return solde; }
    public List<Operation> getListeOperations() { return listeOperations; }
    
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
    
    @Override
    public String toString() {
        return String.format("%s - Solde: %s€ - %d opérations", 
            code, solde.toString(), listeOperations.size());
    }
}