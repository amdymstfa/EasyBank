package com.easybank.model;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class CompteEpargne extends Compte {
    private double tauxInteret;
    
    public CompteEpargne(String code, BigDecimal soldeInitial, double tauxInteret) {
        super(code, soldeInitial);
        this.tauxInteret = tauxInteret;
    }
    
    @Override
    public boolean retirer(BigDecimal montant) {
        if (montant.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (solde.compareTo(montant) >= 0) {
            solde = solde.subtract(montant);
            return true;
        }
        return false;
    }
    
    @Override
    public BigDecimal calculerInteret() {
        return solde.multiply(BigDecimal.valueOf(tauxInteret))
                   .divide(BigDecimal.valueOf(100), 2, RoundingMode.HALF_UP);
    }
    
    @Override
    public void afficherDetails() {
        System.out.println("=== Détails du Compte Épargne ===");
        System.out.println("Code: " + code);
        System.out.println("Solde: " + solde + "€");
        System.out.println("Taux d'intérêt: " + tauxInteret + "%");
        System.out.println("Intérêts calculés: " + calculerInteret() + "€");
        System.out.println("Nombre d'opérations: " + listeOperations.size());
    }
    
    public double getTauxInteret() { return tauxInteret; }
}