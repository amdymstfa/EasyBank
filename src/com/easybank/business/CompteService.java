package com.easybank.business;

import com.easybank.data.DataManager;
import com.easybank.model.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class CompteService {
    private DataManager dataManager;
    private int compteurCompte;
    
    public CompteService() {
        this.dataManager = DataManager.getInstance();
        this.compteurCompte = 1;
    }
    
    public String creerCompteCourant(BigDecimal soldeInitial, BigDecimal decouvert) {
        String code = String.format("CPT-%05d", compteurCompte++);
        CompteCourant compte = new CompteCourant(code, soldeInitial, decouvert);
        dataManager.ajouterCompte(code, compte);
        return code;
    }
    
    public String creerCompteEpargne(BigDecimal soldeInitial, double tauxInteret) {
        String code = String.format("CPT-%05d", compteurCompte++);
        CompteEpargne compte = new CompteEpargne(code, soldeInitial, tauxInteret);
        dataManager.ajouterCompte(code, compte);
        return code;
    }
    
    public Optional<Compte> rechercherCompte(String code) {
        return dataManager.rechercherCompte(code);
    }
    
    public List<Compte> listerComptes() {
        return dataManager.obtenirTousLesComptes().stream()
                .collect(Collectors.toList());
    }
    
    public boolean effectuerVirement(String codeSource, String codeDestination, BigDecimal montant) {
        try {
            Optional<Compte> compteSource = dataManager.rechercherCompte(codeSource);
            Optional<Compte> compteDestination = dataManager.rechercherCompte(codeDestination);
            
            if (compteSource.isPresent() && compteDestination.isPresent()) {
                if (compteSource.get().retirer(montant)) {
                    compteDestination.get().verser(montant);
                    
                    // Ajouter les opérations pour traçabilité
//                    compteSource.get().ajouterOperation(new Retrait(montant, "Virement vers " + codeDestination));
                    compteSource.get().ajouterOperation(new Retrait("Virement vers " + codeDestination, montant));
                    compteDestination.get().ajouterOperation(new Versement(montant, "Virement depuis " + codeSource));
                    
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            System.out.println("Erreur lors du virement: " + e.getMessage());
            return false;
        }
    }
}