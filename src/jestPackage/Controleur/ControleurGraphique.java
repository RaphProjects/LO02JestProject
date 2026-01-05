package jestPackage.Controleur;

import java.util.ArrayList;
import javax.swing.*;
import jestPackage.Vue.*;

/**
 * Implémentation graphique du contrôleur.
 * Gère toutes les entrées utilisateur via JOptionPane et composants Swing.
 * Appelle les méthodes de la vue comme dans la version console.
 */
public class ControleurGraphique implements IControleur {
    
    private VueGraphique vue;
    
    public ControleurGraphique(VueGraphique vue) {
        this.vue = vue;
    }
    
    /**
     * Affiche une boîte de dialogue avec des options et retourne l'index choisi
     */
    private int showOptionDialog(String message, String title, String[] options, int defaultOption) {
        int choix = JOptionPane.showOptionDialog(
            vue.getFrame(),
            message,
            title,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[defaultOption]
        );
        return choix == -1 ? defaultOption : choix;
    }
    
    /**
     * Affiche une boîte de dialogue avec liste déroulante
     */
    private int showListDialog(String message, String title, String[] options) {
        String choix = (String) JOptionPane.showInputDialog(
            vue.getFrame(),
            message,
            title,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[0]
        );
        
        if (choix == null) {
            return 0;
        }
        
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(choix)) {
                return i;
            }
        }
        return 0;
    }
    
    // ==================== INITIALISATION ====================
    
    @Override
    public int demanderNombreJoueurs() {
        vue.demanderNombreJoueurs();
        
        String[] options = {"3 joueurs", "4 joueurs"};
        int choix = showOptionDialog(
            "Combien de joueurs ?",
            "Nombre de joueurs",
            options,
            0
        );
        return choix + 3; // Retourne 3 ou 4
    }
    
    @Override
    public int demanderTypeJoueur(int numeroJoueur) {
        vue.demanderTypeJoueur(numeroJoueur);
        
        String[] options = {"Joueur réel", "Joueur virtuel (IA)"};
        return showOptionDialog(
            "Type du joueur " + numeroJoueur + " ?",
            "Configuration joueur " + numeroJoueur,
            options,
            0
        );
    }

    @Override
    public int demanderStrategieJoueurVirtuel(int numeroJoueur) {
        vue.demanderStrategieJoueurVirtuel(numeroJoueur);
        
        String[] options = {"Stratégie prudente", "Stratégie aléatoire"};
        int choix = showOptionDialog(
            "Quelle stratégie pour le joueur virtuel " + numeroJoueur + " ?",
            "Configuration IA - Joueur " + numeroJoueur,
            options,
            0
        );
        return choix + 1; // Retourne 1 ou 2
    }

    @Override
    public int demanderExtension() {
        vue.demanderExtension();
        
        String[] options = {"Classique", "Plus de cartes"};
        return showOptionDialog(
            "Voulez-vous jouer avec une extension ?",
            "Extension",
            options,
            0
        );
    }

    @Override
    public int demanderVariante() {
        vue.demanderVariante();
        
        String[] options = {"Mode classique", "Mode inversé"};
        return showOptionDialog(
            "Voulez-vous jouer avec une variante ?",
            "Variante",
            options,
            0
        );
    }
    
    // ==================== MENUS ====================

    @Override
    public int afficherMenuPrincipal() {
        vue.afficherMenuPrincipal();
        vue.demanderChoix();
        
        String[] options = {"Nouvelle partie", "Charger une partie", "Supprimer une sauvegarde", "Quitter"};
        int choix = showOptionDialog(
            "Bienvenue dans Jest !\nQue souhaitez-vous faire ?",
            "Menu Principal",
            options,
            0
        );
        return choix + 1; // Retourne 1-4
    }

    @Override
    public int afficherMenuPause() {
        vue.afficherMenuPause();
        vue.demanderChoix();
        
        String[] options = {"Reprendre la partie", "Sauvegarder", "Sauvegarder et quitter", "Quitter sans sauvegarder"};
        int choix = showOptionDialog(
            "Partie en pause",
            "Menu Pause",
            options,
            0
        );
        return choix + 1; // Retourne 1-4
    }
    
    // ==================== SAUVEGARDE/CHARGEMENT ====================

    @Override
    public String demanderNomSauvegarde() {
        vue.demanderNomSauvegarde();
        
        String nom = JOptionPane.showInputDialog(
            vue.getFrame(),
            "Entrez le nom de la sauvegarde :",
            "Sauvegarde",
            JOptionPane.QUESTION_MESSAGE
        );
        
        if (nom == null || nom.trim().isEmpty()) {
            nom = "partie_" + System.currentTimeMillis();
            vue.afficherNomSauvegardeAuto(nom);
        }
        return nom.trim();
    }

    @Override
    public int demanderChoixSauvegarde(ArrayList<String> sauvegardes) {
        if (sauvegardes.isEmpty()) {
            vue.afficherAucuneSauvegarde();
            return 0;
        }
        
        vue.afficherListeSauvegardes(sauvegardes);
        vue.demanderChoixSauvegarde();
        
        // Créer le tableau d'options avec "Annuler" en premier
        String[] options = new String[sauvegardes.size() + 1];
        options[0] = "0 - Annuler";
        for (int i = 0; i < sauvegardes.size(); i++) {
            options[i + 1] = (i + 1) + " - " + sauvegardes.get(i);
        }
        
        int choix = showListDialog(
            "Choisissez une sauvegarde à charger :",
            "Charger partie",
            options
        );
        
        return choix; // 0 = annuler, 1+ = index de sauvegarde
    }

    @Override
    public int demanderSauvegardeASupprimer(ArrayList<String> sauvegardes) {
        if (sauvegardes.isEmpty()) {
            vue.afficherAucuneSauvegardeASupprimer();
            return 0;
        }
        
        vue.afficherListeSauvegardes(sauvegardes);
        vue.demanderSauvegardeASupprimer();
        
        String[] options = new String[sauvegardes.size() + 1];
        options[0] = "0 - Annuler";
        for (int i = 0; i < sauvegardes.size(); i++) {
            options[i + 1] = (i + 1) + " - " + sauvegardes.get(i);
        }
        
        int choix = showListDialog(
            "Choisissez une sauvegarde à supprimer :",
            "Supprimer sauvegarde",
            options
        );
        
        return choix;
    }
    
    // ==================== GAMEPLAY ====================

    @Override
    public String attendreEntreePause() {
        vue.afficherInstructionPause();
        
        String[] options = {"Continuer", "Menu Pause"};
        int choix = showOptionDialog(
            "Cliquez pour continuer ou accéder au menu pause",
            "Tour en cours",
            options,
            0
        );
        
        return choix == 1 ? "p" : "";
    }

    @Override
    public int demanderCarteARetourner(int numeroJoueur, ArrayList<String> nomsCartes) {
        vue.afficherDemandeCarteARetourner(numeroJoueur, nomsCartes);
        
        String[] options = nomsCartes.toArray(new String[0]);
        
        int choix = showListDialog(
            "Joueur " + numeroJoueur + ", quelle carte voulez-vous retourner face visible ?",
            "Choix de carte à retourner",
            options
        );
        
        return choix + 1; // Retourne 1-based index
    }

    @Override
    public int demanderJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles) {
        vue.afficherDemandeJoueurCible(numeroJoueurActuel, joueursDisponibles);
        
        String[] options = new String[joueursDisponibles.size()];
        for (int i = 0; i < joueursDisponibles.size(); i++) {
            options[i] = "Joueur " + joueursDisponibles.get(i);
        }
        
        int choix = showListDialog(
            "Joueur " + numeroJoueurActuel + ", chez quel joueur voulez-vous prendre une carte ?",
            "Choix du joueur cible",
            options
        );
        
        return joueursDisponibles.get(choix);
    }

    @Override
    public int demanderTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible) {
        vue.afficherDemandeTypeCarte(numeroJoueurCible, carteVisibleNom, carteCacheeDisponible);
        
        ArrayList<String> optionsList = new ArrayList<>();
        ArrayList<Integer> returnValues = new ArrayList<>();
        
        if (carteVisibleNom != null) {
            optionsList.add("Carte visible : " + carteVisibleNom);
            returnValues.add(1);
        }
        if (carteCacheeDisponible) {
            optionsList.add("Carte cachée");
            returnValues.add(2);
        }
        
        if (optionsList.size() == 1) {
            // Une seule option disponible
            return returnValues.get(0);
        }
        
        String[] options = optionsList.toArray(new String[0]);
        
        int choix = showOptionDialog(
            "Quelle carte du joueur " + numeroJoueurCible + " voulez-vous prendre ?",
            "Type de carte",
            options,
            0
        );
        
        return returnValues.get(choix);
    }

    @Override
    public boolean demanderConfirmation(String message) {
        vue.afficherDemandeConfirmation(message);
        
        int choix = JOptionPane.showConfirmDialog(
            vue.getFrame(),
            message,
            "Confirmation",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.QUESTION_MESSAGE
        );
        return choix == JOptionPane.YES_OPTION;
    }

    @Override
    public int demanderCarteVisibleOffre(String nomJoueur, ArrayList<String> cartes) {
        vue.afficherChoixOffreJoueur(nomJoueur);
        vue.afficherMainJoueur();
        
        for (int i = 0; i < cartes.size(); i++) {
            vue.afficherCarteMain(i, cartes.get(i));
        }
        
        vue.afficherDemandeCarteVisible();
        
        String[] options = new String[cartes.size()];
        for (int i = 0; i < cartes.size(); i++) {
            options[i] = (i + 1) + " - " + cartes.get(i);
        }
        
        int choix = showListDialog(
            nomJoueur + ", quelle carte voulez-vous mettre en visible dans votre offre ?\n\nVotre main :",
            "Choix de l'offre visible",
            options
        );
        
        return choix + 1; // Retourne 1-based index
    }

    @Override
    public boolean demanderNettoyageConsole() {
        // Non pertinent pour l'interface graphique
        // On peut proposer de masquer temporairement les cartes
        vue.demanderNettoyageConsole();
        return false;
    }

    @Override
    public int demanderChoixPrise(String nomJoueur, int nbPossibilites, ArrayList<String> descriptionsChoix) {
        vue.afficherDemandePriseCarte(nomJoueur);
        
        String[] options;
        
        if (descriptionsChoix != null && !descriptionsChoix.isEmpty()) {
            options = new String[descriptionsChoix.size()];
            for (int i = 0; i < descriptionsChoix.size(); i++) {
                vue.afficherOptionPrise(i + 1, descriptionsChoix.get(i));
                options[i] = (i + 1) + " - " + descriptionsChoix.get(i);
            }
        } else {
            options = new String[nbPossibilites];
            for (int i = 0; i < nbPossibilites; i++) {
                options[i] = "Option " + (i + 1);
            }
        }
        
        int choix = showListDialog(
            nomJoueur + ", quelle carte voulez-vous prendre ?",
            "Choix de prise",
            options
        );
        
        return choix + 1; // Retourne 1-based index
    }

    @Override
    public int demanderChoixPrise(String nomJoueur, int nbPossibilites) {
        return demanderChoixPrise(nomJoueur, nbPossibilites, null);
    }

    @Override
    public void fermer() {
        // Fermeture des ressources graphiques si nécessaire
        if (vue != null && vue.getFrame() != null) {
            vue.getFrame().dispose();
        }
    }
}