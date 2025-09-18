package com.easybank.presentation;

import com.easybank.business.BanqueService;
import com.easybank.model.Compte;
import java.math.BigDecimal;
import java.util.*;

public class Menu {
    private BanqueService banqueService;
    private Scanner scanner;
    
    public Menu() {
        this.banqueService = new BanqueService();
        this.scanner = new Scanner(System.in);
    }
    
    public void afficherMenuPrincipal() {
        int choix;
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
    
    private void effectuerVersement() {
        System.out.println("\n=== Versement ===");
        try {
            System.out.print("Code compte: ");
            String code = scanner.nextLine().trim().toUpperCase();
            
            if (!banqueService.validerCodeCompte(code)) {
                System.out.println("Erreur: Format de code invalide ! (Format: CPT-XXXXX)");
                return;
            }
            
            System.out.print("Montant: ");
            double montantInput = scanner.nextDouble();
            scanner.nextLine();
            BigDecimal montant = BigDecimal.valueOf(montantInput);
            
            if (!banqueService.validerMontant(montant)) {
                System.out.println("Erreur: Le montant doit être positif !");
                return;
            }
            
            System.out.print("Source: ");
            String source = scanner.nextLine().trim();
            
            if (source.isEmpty()) {
                System.out.println("Erreur: La source ne peut pas être vide !");
                return;
            }
            
            if (banqueService.deposer(code, montant, source)) {
                System.out.println("Versement effectué avec succès !");
            } else {
                System.out.println("Erreur: Compte introuvable !");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Erreur: Veuillez saisir un montant valide !");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erreur inattendue: " + e.getMessage());
        }
    }
    
    private void effectuerRetrait() {
        System.out.println("\n=== Retrait ===");
        try {
            System.out.print("Code compte: ");
            String code = scanner.nextLine().trim().toUpperCase();
            
            if (!banqueService.validerCodeCompte(code)) {
                System.out.println("Erreur: Format de code invalide !");
                return;
            }
            
            System.out.print("Montant: ");
            double montantInput = scanner.nextDouble();
            scanner.nextLine();
            BigDecimal montant = BigDecimal.valueOf(montantInput);
            
            if (!banqueService.validerMontant(montant)) {
                System.out.println("Erreur: Le montant doit être positif !");
                return;
            }
            
            System.out.print("Destination: ");
            String destination = scanner.nextLine().trim();
            
            if (destination.isEmpty()) {
                System.out.println("Erreur: La destination ne peut pas être vide !");
                return;
            }
            
            if (banqueService.retirer(code, montant, destination)) {
                System.out.println("Retrait effectué avec succès !");
            } else {
                System.out.println("Erreur: Solde insuffisant ou compte introuvable !");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Erreur: Veuillez saisir un montant valide !");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erreur inattendue: " + e.getMessage());
        }
    }
    
    private void effectuerVirement() {
        System.out.println("\n=== Virement ===");
        try {
            System.out.print("Compte source: ");
            String source = scanner.nextLine().trim().toUpperCase();
            System.out.print("Compte destination: ");
            String destination = scanner.nextLine().trim().toUpperCase();
            
            if (!banqueService.validerCodeCompte(source) || !banqueService.validerCodeCompte(destination)) {
                System.out.println("Erreur: Format de code invalide !");
                return;
            }
            
            if (source.equals(destination)) {
                System.out.println("Erreur: Les comptes source et destination doivent être différents !");
                return;
            }
            
            System.out.print("Montant: ");
            double montantInput = scanner.nextDouble();
            BigDecimal montant = BigDecimal.valueOf(montantInput);
            
            if (!banqueService.validerMontant(montant)) {
                System.out.println("Erreur: Le montant doit être positif !");
                return;
            }
            
            if (banqueService.virer(source, destination, montant)) {
                System.out.println("Virement effectué avec succès !");
            } else {
                System.out.println("Erreur lors du virement !");
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Erreur: Veuillez saisir un montant valide !");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erreur inattendue: " + e.getMessage());
        }
    }
    
    private void consulterSolde() {
        try {
            System.out.print("Code compte: ");
            String code = scanner.nextLine().trim().toUpperCase();
            
            if (!banqueService.validerCodeCompte(code)) {
                System.out.println("Erreur: Format de code invalide !");
                return;
            }
            
            Optional<Compte> compte = banqueService.consulterCompte(code);
            
            if (compte.isPresent()) {
                compte.get().afficherDetails();
            } else {
                System.out.println("Compte introuvable !");
            }
            
        } catch (Exception e) {
            System.out.println("Erreur inattendue: " + e.getMessage());
        }
    }

    
    private void consulterOperations() {
        try {
            System.out.print("Code compte: ");
            String code = scanner.nextLine().trim().toUpperCase();
            
            if (!banqueService.validerCodeCompte(code)) {
                System.out.println("Erreur: Format de code invalide !");
                return;
            }
            
            Optional<Compte> compte = banqueService.consulterCompte(code);
            
            if (compte.isPresent()) {
                System.out.println("=== Opérations du compte " + code + " ===");
                compte.get().getListeOperations().stream()
                    .sorted((o1, o2) -> o2.getDate().compareTo(o1.getDate()))
                    .forEach(System.out::println);
            } else {
                System.out.println("Compte introuvable !");
            }
            
        } catch (Exception e) {
            System.out.println("Erreur inattendue: " + e.getMessage());
        }
    }
    
    private void filtrerComptesParSolde() {
        System.out.println("\n=== Filtrage par solde minimum ===");
        try {
            System.out.print("Solde minimum: ");
            double soldeMinInput = scanner.nextDouble();
            BigDecimal soldeMin = BigDecimal.valueOf(soldeMinInput);
            
            List<Compte> comptesFiltres = banqueService.filtrerComptesParSolde(soldeMin);
            
            if (comptesFiltres.isEmpty()) {
                System.out.println("Aucun compte trouvé avec ce solde minimum.");
            } else {
                System.out.println("Comptes trouvés:");
                comptesFiltres.forEach(System.out::println);
            }
            
        } catch (InputMismatchException e) {
            System.out.println("Erreur: Veuillez saisir un montant valide !");
            scanner.nextLine();
        } catch (Exception e) {
            System.out.println("Erreur inattendue: " + e.getMessage());
        }
    }
    
    private void afficherStatistiques() {
        System.out.println("\n=== Statistiques ===");
        try {
            List<Compte> comptes = banqueService.listerTousLesComptes();
            
            if (comptes.isEmpty()) {
                System.out.println("Aucun compte créé.");
                return;
            }
            
            BigDecimal soldeTotal = banqueService.calculerSoldeTotal();
            long nombreComptesPositifs = banqueService.compterComptesPositifs();
            
            long nombreComptesCourants = comptes.stream()
                .filter(c -> c instanceof com.easybank.model.CompteCourant)
                .count();
            
            long nombreComptesEpargne = comptes.stream()
                .filter(c -> c instanceof com.easybank.model.CompteEpargne)
                .count();
            
            System.out.println("Nombre total de comptes: " + comptes.size());
            System.out.println("Comptes courants: " + nombreComptesCourants);
            System.out.println("Comptes épargne: " + nombreComptesEpargne);
            System.out.println("Solde total: " + soldeTotal + "€");
            System.out.println("Comptes avec solde positif: " + nombreComptesPositifs);
            
        } catch (Exception e) {
            System.out.println("Erreur lors du calcul des statistiques: " + e.getMessage());
        }
    }
}