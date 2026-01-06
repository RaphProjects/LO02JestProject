package jestPackage.Controleur;

import java.util.ArrayList;
import javax.swing.*;
import jestPackage.Vue.*;

/**
 * Implémentation graphique du contrôleur.
 * Utilise des images pour les phases de jeu (Offre et Prise).
 */
public class ControleurGraphique implements IControleur {
    
    private VueGraphique vue;
    
    public ControleurGraphique(VueGraphique vue) {
        this.vue = vue;
    }
    
    private int showOptionDialog(String message, String title, String[] options, int defaultOption) {
        int choix = JOptionPane.showOptionDialog(
            vue.getFrame(), message, title,
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[defaultOption]
        );
        return choix == -1 ? defaultOption : choix;
    }
    
    private int showListDialog(String message, String title, String[] options) {
        String choix = (String) JOptionPane.showInputDialog(
            vue.getFrame(), message, title,
            JOptionPane.QUESTION_MESSAGE, null,
            options, options[0]
        );
        if (choix == null) return 0;
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(choix)) return i;
        }
        return 0;
    }
    
    // ==================== CONFIGURATION ====================
    
    @Override
    public int demanderNombreJoueurs() {
        vue.demanderNombreJoueurs();
        String[] options = {"3 joueurs", "4 joueurs"};
        return showOptionDialog("Combien de joueurs ?", "Nombre de joueurs", options, 0) + 3;
    }
    
    @Override
    public int demanderTypeJoueur(int numeroJoueur) {
        vue.demanderTypeJoueur(numeroJoueur);
        String[] options = {"Joueur réel", "Joueur virtuel (IA)"};
        return showOptionDialog("Type du joueur " + numeroJoueur + " ?", "Configuration joueur " + numeroJoueur, options, 0);
    }

    @Override
    public int demanderStrategieJoueurVirtuel(int numeroJoueur) {
        vue.demanderStrategieJoueurVirtuel(numeroJoueur);
        String[] options = {"Stratégie prudente", "Stratégie aléatoire"};
        return showOptionDialog("Quelle stratégie pour l'IA " + numeroJoueur + " ?", "Configuration IA", options, 0) + 1;
    }

    @Override
    public int demanderExtension() {
        vue.demanderExtension();
        String[] options = {"Classique", "Plus de cartes"};
        return showOptionDialog("Extension ?", "Configuration", options, 0);
    }

    @Override
    public int demanderVariante() {
        vue.demanderVariante();
        String[] options = {"Mode classique", "Mode inversé"};
        return showOptionDialog("Variante ?", "Configuration", options, 0);
    }
    
    // ==================== MENUS ====================

    @Override
    public int afficherMenuPrincipal() {
        vue.afficherMenuPrincipal();
        String[] options = {"Nouvelle partie", "Charger une partie", "Supprimer une sauvegarde", "Quitter"};
        return showOptionDialog("Bienvenue dans Jest !", "Menu Principal", options, 0) + 1;
    }

    @Override
    public int afficherMenuPause() {
        vue.afficherMenuPause();
        String[] options = {"Reprendre", "Sauvegarder", "Sauvegarder et quitter", "Quitter sans sauvegarder"};
        return showOptionDialog("Partie en pause", "Menu Pause", options, 0) + 1;
    }
    
    // ==================== SAUVEGARDE ====================
    
    @Override
    public String demanderNomSauvegarde() {
        vue.demanderNomSauvegarde();
        String nom = JOptionPane.showInputDialog(vue.getFrame(), "Nom de la sauvegarde :", "Sauvegarde", JOptionPane.QUESTION_MESSAGE);
        return (nom == null || nom.trim().isEmpty()) ? "partie_" + System.currentTimeMillis() : nom.trim();
    }

    @Override
    public int demanderChoixSauvegarde(ArrayList<String> sauvegardes) {
        if (sauvegardes.isEmpty()) { vue.afficherAucuneSauvegarde(); return 0; }
        String[] options = new String[sauvegardes.size() + 1];
        options[0] = "0 - Annuler";
        for (int i = 0; i < sauvegardes.size(); i++) options[i + 1] = (i + 1) + " - " + sauvegardes.get(i);
        return showListDialog("Choisir une sauvegarde :", "Charger", options);
    }

    @Override
    public int demanderSauvegardeASupprimer(ArrayList<String> sauvegardes) {
        if (sauvegardes.isEmpty()) { vue.afficherAucuneSauvegardeASupprimer(); return 0; }
        String[] options = new String[sauvegardes.size() + 1];
        options[0] = "0 - Annuler";
        for (int i = 0; i < sauvegardes.size(); i++) options[i + 1] = (i + 1) + " - " + sauvegardes.get(i);
        return showListDialog("Choisir une sauvegarde à supprimer :", "Supprimer", options);
    }
    
    // ==================== GAMEPLAY AVEC IMAGES ====================

    @Override
    public String attendreEntreePause() {
        vue.afficherInstructionPause();
        String[] options = {"Jouer le tour", "Pause"};
        int choix = JOptionPane.showOptionDialog(vue.getFrame(), "Prêt pour le prochain tour ?", "Nouveau Tour",
                JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, null, options, options[0]);
        return choix == 1 ? "p" : "";
    }

    @Override
    public int demanderCarteVisibleOffre(String nomJoueur, ArrayList<String> cartes) {
        vue.afficherChoixOffreJoueur(nomJoueur);
        
        ImageIcon[] optionsIcons = new ImageIcon[cartes.size()];
        for (int i = 0; i < cartes.size(); i++) {
            optionsIcons[i] = vue.getIconeCarte(cartes.get(i));
        }
        
        int choix = JOptionPane.showOptionDialog(
            vue.getFrame(),
            nomJoueur + ", choisissez la carte à mettre face VISIBLE :",
            "Phase d'Offre - " + nomJoueur,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            optionsIcons,
            optionsIcons.length > 0 ? optionsIcons[0] : null
        );
        
        return (choix == -1) ? 1 : choix + 1;
    }

    @Override
    public int demanderChoixPrise(String nomJoueur, int nbPossibilites, ArrayList<String> descriptionsChoix) {
        vue.afficherDemandePriseCarte(nomJoueur);
        
        ArrayList<String> descriptions = descriptionsChoix;
        if (descriptions == null || descriptions.isEmpty()) {
            descriptions = vue.getEtViderOptionsPrise();
        }
        
        if (descriptions == null || descriptions.isEmpty()) {
            String[] fallbackOptions = new String[nbPossibilites];
            for (int i = 0; i < nbPossibilites; i++) {
                fallbackOptions[i] = "Option " + (i + 1);
            }
            return showListDialog(nomJoueur + ", choisissez une carte :", "Phase de Prise", fallbackOptions) + 1;
        }
        
        ImageIcon[] optionsIcons = new ImageIcon[descriptions.size()];
        
        for (int i = 0; i < descriptions.size(); i++) {
            String desc = descriptions.get(i);
            String nomCartePourImage = desc;
            String nomProprietaire = "";
            
            // Extraction du nom de la carte et du propriétaire
            if (desc.startsWith("Cachée:")) {
                nomCartePourImage = "FaceVerso";
            } else if (desc.startsWith("Visible:")) {
                String apresVisible = desc.substring("Visible:".length());
                // Séparation nom carte / propriétaire si parenthèses
                int parenIndex = apresVisible.lastIndexOf('(');
                if (parenIndex > 0) {
                    nomCartePourImage = apresVisible.substring(0, parenIndex).trim();
                } else {
                    nomCartePourImage = apresVisible.trim();
                }
            }
            
            // Extraction du propriétaire (entre parenthèses à la fin)
            int lastParenOpen = desc.lastIndexOf('(');
            int lastParenClose = desc.lastIndexOf(')');
            if (lastParenOpen != -1 && lastParenClose != -1 && lastParenClose > lastParenOpen) {
                nomProprietaire = desc.substring(lastParenOpen + 1, lastParenClose);
            } else if (desc.startsWith("Visible") || desc.startsWith("Cachée")) {
                // Si pas de parenthèses mais format vue (cas rare de propre offre parfois)
            }
            
            ImageIcon iconBase = vue.getIconeCarte(nomCartePourImage);
            optionsIcons[i] = vue.ajouterTexteSousIcone(iconBase, nomProprietaire);
        }

        int choix = JOptionPane.showOptionDialog(
            vue.getFrame(),
            nomJoueur + ", choisissez une carte à prendre :",
            "Phase de Prise - " + nomJoueur,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            optionsIcons,
            optionsIcons.length > 0 ? optionsIcons[0] : null
        );
        
        return (choix == -1) ? 1 : choix + 1;
    }

    @Override
    public int demanderChoixPrise(String nomJoueur, int nbPossibilites) {
        return demanderChoixPrise(nomJoueur, nbPossibilites, null);
    }
    
    @Override public int demanderCarteARetourner(int numeroJoueur, ArrayList<String> nomsCartes) {
        String[] options = nomsCartes.toArray(new String[0]);
        return showListDialog("Quelle carte retourner ?", "Action", options) + 1;
    }

    @Override public int demanderJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles) {
        String[] options = new String[joueursDisponibles.size()];
        for(int i=0; i<joueursDisponibles.size(); i++) options[i] = "Joueur " + joueursDisponibles.get(i);
        int idx = showListDialog("Chez qui prendre ?", "Cible", options);
        return joueursDisponibles.get(idx);
    }

    @Override public int demanderTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible) {
        ArrayList<String> opts = new ArrayList<>();
        ArrayList<Integer> vals = new ArrayList<>();
        if(carteVisibleNom != null) { opts.add("Visible: " + carteVisibleNom); vals.add(1); }
        if(carteCacheeDisponible) { opts.add("Carte Cachée"); vals.add(2); }
        
        int choix = showOptionDialog("Quelle carte ?", "Choix", opts.toArray(new String[0]), 0);
        return vals.get(choix);
    }

    @Override public boolean demanderConfirmation(String message) {
        return JOptionPane.showConfirmDialog(vue.getFrame(), message, "Confirmation", JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION;
    }

    @Override public boolean demanderNettoyageConsole() { return false; }
    
    @Override public void fermer() { vue.getFrame().dispose(); }
}