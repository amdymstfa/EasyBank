package com.easybank.business;

import com.easybank.model.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

public class BanqueService {
    private CompteService compteService;
    private ClientService clientService;
    private static final Pattern CODE_PATTERN = Pattern.compile("^CPT-\\d{5}$");
    
    public BanqueService() {
        this.compteService = new CompteService();
        this.clientService = new ClientService();
    }
    
    // Validations
    public boolean validerMontant(BigDecimal montant) {
        return montant != null && montant.compareTo(BigDecimal.ZERO) > 0;
    }
    
    public boolean validerCodeCompte(String code) {
        return code != null && CODE_PATTERN.matcher(code).matches();
    }
    
    public boolean validerTaux(double taux) {
        return taux >= 0 && taux <= 100;
    }
    
    public boolean validerDecouvert(BigDecimal decouvert) {
        return decouvert != null && decouvert.compareTo(BigDecimal.ZERO) >= 0;
    }
    
    // Services métier
    public String ouvrirCompteCourant(BigDecimal soldeInitial, BigDecimal decouvert) {
        if (!validerMontant(soldeInitial) || !validerDecouvert(decouvert)) {
            throw new IllegalArgumentException("Paramètres invalides pour la création du compte");
        }
        return compteService.creerCompteCourant(soldeInitial, decouvert);
    }
    
    public String ouvrirCompteEpargne(BigDecimal soldeInitial, double taux) {
        if (!validerMontant(soldeInitial) || !validerTaux(taux)) {
            throw new IllegalArgumentException("Paramètres invalides pour la création du compte");
        }
        return compteService.creerCompteEpargne(soldeInitial, taux);
    }
    
    public boolean deposer(String codeCompte, BigDecimal montant, String source) {
        if (!validerCodeCompte(codeCompte) || !validerMontant(montant)) {
            return false;
        }
        
        Optional<Compte> compte = compteService.rechercherCompte(codeCompte);
        if (compte.isPresent() && compte.get().verser(montant)) {
            compte.get().ajouterOperation(new Versement(montant, source));
            return true;
        }
        return false;
    }
    
    public boolean retirer(String codeCompte, BigDecimal montant, String destination) {
        if (!validerCodeCompte(codeCompte) || !validerMontant(montant)) {
            return false;
        }
        
        Optional<Compte> compte = compteService.rechercherCompte(codeCompte);
        if (compte.isPresent() && compte.get().retirer(montant)) {
            compte.get().ajouterOperation(new Retrait(montant, destination));
            return true;
        }
        return false;
    }
    
    public boolean virer(String codeSource, String codeDestination, BigDecimal montant) {
        if (!validerCodeCompte(codeSource) || !validerCodeCompte(codeDestination) || 
            !validerMontant(montant) || codeSource.equals(codeDestination)) {
            return false;
        }
        
        return compteService.effectuerVirement(codeSource, codeDestination, montant);
    }
    
    public Optional<Compte> consulterCompte(String code) {
        return validerCodeCompte(code) ? compteService.rechercherCompte(code) : Optional.empty();
    }
    
    public List<Compte> listerTousLesComptes() {
        return compteService.listerComptes();
    }
    
    public List<Compte> filtrerComptesParSolde(BigDecimal soldeMinimum) {
        return clientService.filtrerComptesParSoldeMinimum(soldeMinimum);
    }
    
    public BigDecimal calculerSoldeTotal() {
        return clientService.calculerSoldeTotal();
    }
    
    public long compterComptesPositifs() {
        return clientService.compterComptesPositifs();
    }
}