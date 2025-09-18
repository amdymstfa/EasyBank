package com.easybank.data;

import com.easybank.model.Compte;
import java.util.HashMap;
import java.util.Map;
import java.util.Collection;
import java.util.Optional;

public class DataManager {
    private static DataManager instance;
    private Map<String, Compte> comptes;
    
    private DataManager() {
        this.comptes = new HashMap<>();
    }
    
    public static DataManager getInstance() {
        if (instance == null) {
            instance = new DataManager();
        }
        return instance;
    }
    
    public void ajouterCompte(String code, Compte compte) {
        comptes.put(code, compte);
    }
    
    public Optional<Compte> rechercherCompte(String code) {
        return Optional.ofNullable(comptes.get(code));
    }
    
    public Collection<Compte> obtenirTousLesComptes() {
        return comptes.values();
    }
    
    public boolean compteExiste(String code) {
        return comptes.containsKey(code);
    }
    
    public int getNombreComptes() {
        return comptes.size();
    }
}