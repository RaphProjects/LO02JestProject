package jestPackage.Controleur;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import jestPackage.Vue.VueConsole;

/**
 * Implémentation console du contrôleur.
 * Gère toutes les entrées utilisateur via Scanner.
 */
public class ControleurConsole implements IControleur {
    
    private Scanner scanner;
    private VueConsole vue;
    
    public ControleurConsole() {
        this.scanner = new Scanner(System.in);
        this.vue = new VueConsole();
    }
    
    public ControleurConsole(VueConsole vue) {
        this.scanner = new Scanner(System.in);
        this.vue = vue;
    }
    
    /**
     * Lit un entier avec gestion d'erreur
     */
    private int lireEntier() {
        while (true) {
            try {
                int valeur = scanner.nextInt();
                scanner.nextLine(); // Consommer le retour à la ligne
                return valeur;
            } catch (InputMismatchException e) {
                scanner.nextLine(); // Vider le buffer
                vue.afficherErreurSaisie();
            }
        }
    }
    
    /**
     * Lit un entier dans une plage donnée
     */
    private int lireEntierDansPlage(int min, int max) {
        int valeur;
        do {
            valeur = lireEntier();
            if (valeur < min || valeur > max) {
                vue.afficherErreurPlage(min, max);
            }
        } while (valeur < min || valeur > max);
        return valeur;
    }
    
    /**
     * Lit une chaîne non vide
     */
    private String lireChaine() {
        String ligne = scanner.nextLine().trim();
        return ligne;
    }
    
    // ==================== INITIALISATION ====================
    
    @Override
    public int demanderNombreJoueurs() {
        vue.demanderNombreJoueurs();
        return lireEntierDansPlage(3, 4);
    }
    
    @Override
    public int demanderTypeJoueur(int numeroJoueur) {
        vue.demanderTypeJoueur(numeroJoueur);
        return lireEntierDansPlage(0, 1);
    }
    
    @Override
    public int demanderStrategieJoueurVirtuel(int numeroJoueur) {
        vue.demanderStrategieJoueurVirtuel(numeroJoueur);
        return lireEntier();
    }
    
    @Override
    public int demanderExtension() {
        vue.demanderExtension();
        return lireEntierDansPlage(0, 1);
    }
    
    @Override
    public int demanderVariante() {
        vue.demanderVariante();
        return lireEntierDansPlage(0, 1);
    }
    
    // ==================== MENUS ====================
    
    @Override
    public int afficherMenuPrincipal() {
        vue.afficherMenuPrincipal();
        vue.demanderChoix();
        return lireEntierDansPlage(1, 4);
    }
    
    @Override
    public int afficherMenuPause() {
        vue.afficherMenuPause();
        vue.demanderChoix();
        return lireEntierDansPlage(1, 4);
    }
    
    // ==================== SAUVEGARDE/CHARGEMENT ====================
    
    @Override
    public String demanderNomSauvegarde() {
        vue.demanderNomSauvegarde();
        String nom = lireChaine();
        
        if (nom.isEmpty()) {
            nom = "partie_" + System.currentTimeMillis();
            vue.afficherNomSauvegardeAuto(nom);
        }
        return nom;
    }
    
    @Override
    public int demanderChoixSauvegarde(ArrayList<String> sauvegardes) {
        if (sauvegardes.isEmpty()) {
            vue.afficherAucuneSauvegarde();
            return 0;
        }
        
        vue.afficherListeSauvegardes(sauvegardes);
        vue.demanderChoixSauvegarde();
        return lireEntierDansPlage(0, sauvegardes.size());
    }
    
    @Override
    public int demanderSauvegardeASupprimer(ArrayList<String> sauvegardes) {
        if (sauvegardes.isEmpty()) {
            vue.afficherAucuneSauvegardeASupprimer();
            return 0;
        }
        
        vue.afficherListeSauvegardes(sauvegardes);
        vue.demanderSauvegardeASupprimer();
        return lireEntierDansPlage(0, sauvegardes.size());
    }
    
    // ==================== GAMEPLAY ====================
    
    @Override
    public String attendreEntreePause() {
        vue.afficherInstructionPause();
        return lireChaine();
    }
    
    @Override
    public int demanderCarteARetourner(int numeroJoueur, ArrayList<String> nomsCartes) {
        vue.afficherDemandeCarteARetourner(numeroJoueur, nomsCartes);
        return lireEntierDansPlage(1, nomsCartes.size());
    }
    
    @Override
    public int demanderJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles) {
        vue.afficherDemandeJoueurCible(numeroJoueurActuel, joueursDisponibles);
        
        int choix;
        do {
            choix = lireEntier();
            if (!joueursDisponibles.contains(choix)) {
                vue.afficherJoueurNonDisponible();
            }
        } while (!joueursDisponibles.contains(choix));
        
        return choix;
    }
    
    @Override
    public int demanderTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible) {
        vue.afficherDemandeTypeCarte(numeroJoueurCible, carteVisibleNom, carteCacheeDisponible);
        
        int min = carteVisibleNom != null ? 1 : 2;
        int max = carteCacheeDisponible ? 2 : 1;
        
        return lireEntierDansPlage(min, max);
    }
    
    @Override
    public boolean demanderConfirmation(String message) {
        vue.afficherDemandeConfirmation(message);
        String reponse = lireChaine().toLowerCase();
        return reponse.equals("o") || reponse.equals("oui") || reponse.equals("y") || reponse.equals("yes");
    }
    

    @Override
    public int demanderCarteVisibleOffre(String nomJoueur, ArrayList<String> cartes) {
        vue.afficherChoixOffreJoueur(nomJoueur);
        vue.afficherMainJoueur();
        
        for (int i = 0; i < cartes.size(); i++) {
            vue.afficherCarteMain(i, cartes.get(i));
        }
        
        vue.afficherDemandeCarteVisible();
        return lireEntierDansPlage(1, cartes.size());
    }

    @Override
    public boolean demanderNettoyageConsole() {
        vue.demanderNettoyageConsole();
        int choix = lireEntierDansPlage(0, 1);
        
        if (choix == 0) {
            vue.nettoyerConsole();
            return true;
        }
        return false;
    }

    @Override
    public int demanderChoixPrise(String nomJoueur, int nbPossibilites, ArrayList<String> descriptionsChoix) {
        vue.afficherDemandePriseCarte(nomJoueur);
        
        if (descriptionsChoix != null && !descriptionsChoix.isEmpty()) {
            for (int i = 0; i < descriptionsChoix.size(); i++) {
                vue.afficherOptionPrise(i + 1, descriptionsChoix.get(i));
            }
        }
        
        return lireEntierDansPlage(1, nbPossibilites);
    }

    @Override
    public int demanderChoixPrise(String nomJoueur, int nbPossibilites) {
        return demanderChoixPrise(nomJoueur, nbPossibilites, null);
    }
    
    /**
     * Ferme le scanner proprement
     */
    public void fermer() {
        if (scanner != null) {
            scanner.close();
        }
    }
}