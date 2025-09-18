package com.easybank.model;

import java.math.BigDecimal;

public class CompteCourant extends Compte {
    private BigDecimal decouvert;
    
    public CompteCourant(String code, BigDecimal soldeInitial, BigDecimal decouvert) {
        super(code, soldeInitial);
        this.decouvert = decouvert;
    }
    
    @Override
    public boolean retirer(BigDecimal montant) {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (solde.subtract(montant).compareTo(decouvert.negate()) >= 0) {
            solde = solde.subtract(montant);
            return true;
        }
        return false;
    }
    
    @Override
    public BigDecimal calculerInteret() {
        return BigDecimal.ZERO;
    }
    
    @Override
    public void afficherDetails() {
        System.out.println("=== Détails du Compte Courant ===");
        System.out.println("Code: " + code);
        System.out.println("Solde: " + solde + "€");
        System.out.println("Découvert autorisé: " + decouvert + "€");
        System.out.println("Nombre d'opérations: " + listeOperations.size());
    }
    
    public BigDecimal getDecouvert() { return decouvert; }
}