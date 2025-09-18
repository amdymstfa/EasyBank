package com.easybank.presentation;

import com.easybank.business.BanqueService;
import com.easybank.model.Compte;
import java.math.BigDecimal;
import java.util.*;

public class Menu {
	
	private BanqueService banqueService;
	private Scanner scanner ;
	
	public Menu(BanqueService banqueService, Scanner scanner) {
		this.banqueService = new BanqueService();
		this.scanner = new Scanner(System.in);
	}
	
	public void afficherMenuPrincipal() {
		int choix ;
		
		do {
            System.out.println("\n=== EASYBANK ===");
            System.out.println("1. Créer un compte");
            System.out.println("2. Effectuer un versement");
            System.out.println("3. Effectuer un retrait");
            System.out.println("4. Effectuer un virement");
            System.out.println("5. Consulter le solde");
            System.out.println("6. Consulter les opérations");
            System.out.println("7. Filtrer comptes par solde");
            System.out.println("8. Statistiques des comptes");
            System.out.println("0. Quitter");
            System.out.print("Choix: ");
            
            try {
                choix = scanner.nextInt();
                scanner.nextLine();
                
                switch (choix) {
                    case 1: creerCompte(); break;
                    case 2: effectuerVersement(); break;
                    case 3: effectuerRetrait(); break;
                    case 4: effectuerVirement(); break;
                    case 5: consulterSolde(); break;
                    case 6: consulterOperations(); break;
                    case 7: filtrerComptesParSolde(); break;
                    case 8: afficherStatistiques(); break;
                    case 0: System.out.println("Au revoir ! Données perdues à la fermeture."); break;
                    default: System.out.println("Choix invalide !");
                }
            } catch (InputMismatchException e) {
                System.out.println("Erreur: Veuillez saisir un nombre valide !");
                scanner.nextLine();
                choix = -1;
            }
        } while (choix != 0);
    }

	private void creerCompte() {
        System.out.println("\n=== Création de compte ===");
        try {
            System.out.println("1. Compte Courant");
            System.out.println("2. Compte Épargne");
            System.out.print("Type: ");
            
            int type = scanner.nextInt();
            System.out.print("Solde initial: ");
            double soldeInput = scanner.nextDouble();
            BigDecimal solde = BigDecimal.valueOf(soldeInput);
            
            if (!banqueService.validerMontant(solde)) {
                System.out.println("Erreur: Le solde initial doit être positif !");
                return;
            }
            
            String code;
            if (type == 1) {
                System.out.print("Découvert autorisé: ");
                double decouvertInput = scanner.nextDouble();
                BigDecimal decouvert = BigDecimal.valueOf(decouvertInput);
                
                if (!banqueService.validerDecouvert(decouvert)) {
                    System.out.println("Erreur: Le découvert doit être positif ou nul !");
                    return;
                }
                
                code = banqueService.ouvrirCompteCourant(solde, decouvert);
            } else if (type == 2) {
                System.out.print("Taux d'intérêt (%): ");
                double taux = scanner.nextDouble();
                
                if (!banqueService.validerTaux(taux)) {
                    System.out.println("Erreur: Le taux doit être entre 0 et 100% !");
                    return;
                }
                
                code = banqueService.ouvrirCompteEpargne(solde, taux);
            } else {
                System.out.println("Type de compte invalide !");
                return;
            }
            
            System.out.println("Compte créé: " + code);
            
        } catch (InputMismatchException e) {
            System.out.println("Erreur: Veuillez saisir des valeurs numériques valides !");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erreur inattendue: " + e.getMessage());
        }
    }
}
