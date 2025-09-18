package com.easybank.business;

import com.easybank.model.Compte;
import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

public class ClientService {
    private CompteService compteService;
    
    public ClientService() {
        this.compteService = new CompteService();
    }
    
    public List<Compte> filtrerComptesParSoldeMinimum(BigDecimal soldeMinimum) {
        return compteService.listerComptes().stream()
                .filter(compte -> compte.getSolde().compareTo(soldeMinimum) >= 0)
                .sorted((c1, c2) -> c2.getSolde().compareTo(c1.getSolde()))
                .collect(Collectors.toList());
    }
    
    public BigDecimal calculerSoldeTotal() {
        return compteService.listerComptes().stream()
                .map(Compte::getSolde)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public long compterComptesPositifs() {
        return compteService.listerComptes().stream()
                .filter(compte -> compte.getSolde().compareTo(BigDecimal.ZERO) > 0)
                .count();
    }
}