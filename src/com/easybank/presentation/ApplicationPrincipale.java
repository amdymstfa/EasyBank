package com.easybank.presentation;

public class ApplicationPrincipale {
    public static void main(String[] args) {
        System.out.println("=== Démarrage d'EasyBank ===");
        System.out.println("Les données persistent en mémoire jusqu'à la fermeture de l'application.");
        
        Menu menu = new Menu();
        menu.afficherMenuPrincipal();
    }
}