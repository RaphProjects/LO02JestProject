package jestPackage.Controleur;

import java.util.ArrayList;
import javax.swing.*;
import jestPackage.Vue.*;

/**
 * Implémentation graphique du contrôleur.
 * Gère les entrées utilisateur via des boîtes de dialogue JOptionPane.
 * Utilise des images pour les phases de jeu (Offre et Prise).
 */
public class ControleurGraphique implements IControleur {
    
    // Reference vers la vue graphique
    private VueGraphique vue;
    
    public ControleurGraphique(VueGraphique vue) {
        this.vue = vue;
    }
    
    /**
     * Affiche une boite de dialogue avec des boutons d'options
     * Retourne l'index du bouton choisi
     */
    private int showOptionDialog(String message, String title, String[] options, int defaultOption) {
        int choix = JOptionPane.showOptionDialog(
            vue.getFrame(), message, title,
            JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE,
            null, options, options[defaultOption]
        );
        // Si l'utilisateur ferme la fenetre, retourner l'option par defaut
        return choix == -1 ? defaultOption : choix;
    }
    
    /**
     * Affiche une boite de dialogue avec une liste deroulante
     * Retourne l'index de l'element choisi
     */
    private int showListDialog(String message, String title, String[] options) {
        String choix = (String) JOptionPane.showInputDialog(
            vue.getFrame(), message, title,
            JOptionPane.QUESTION_MESSAGE, null,
            options, options[0]
        );
        // Recherche de l'index correspondant au choix
        if (choix == null) return 0;
        for (int i = 0; i < options.length; i++) {
            if (options[i].equals(choix)) return i;
        }
        return 0;
    }
    
    // config de la partie
    
    @Override
    public int demanderNombreJoueurs() {
        vue.demanderNombreJoueurs();
        String[] options = {"3 joueurs", "4 joueurs"};
        // Retourne 3 ou 4 selon le choix
        return showOptionDialog("Combien de joueurs ?", "Nombre de joueurs", options, 0) + 3;
    }
    
    @Override
    public int demanderTypeJoueur(int numeroJoueur) {
        vue.demanderTypeJoueur(numeroJoueur);
        String[] options = {"Joueur reel", "Joueur virtuel (IA)"};
        // Retourne 0 pour reel, 1 pour IA
        return showOptionDialog("Type du joueur " + numeroJoueur + " ?", "Configuration joueur " + numeroJoueur, options, 0);
    }

    @Override
    public int demanderStrategieJoueurVirtuel(int numeroJoueur) {
        vue.demanderStrategieJoueurVirtuel(numeroJoueur);
        String[] options = {"Strategie prudente", "Strategie aleatoire"};
        // Retourne 1 ou 2 selon la strategie
        return showOptionDialog("Quelle strategie pour l'IA " + numeroJoueur + " ?", "Configuration IA", options, 0) + 1;
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
        String[] options = {"Mode classique", "Mode inverse"};
        return showOptionDialog("Variante ?", "Configuration", options, 0);
    }
    
    // Menus

    @Override
    public int afficherMenuPrincipal() {
        vue.afficherMenuPrincipal();
        String[] options = {"Nouvelle partie", "Charger une partie", "Supprimer une sauvegarde", "Quitter"};
        // Retourne 1 a 4 selon le choix
        return showOptionDialog("Bienvenue dans Jest !", "Menu principal", options, 0) + 1;
    }

    @Override
    public int afficherMenuPause() {
        vue.afficherMenuPause();
        String[] options = {"Reprendre", "Sauvegarder", "Sauvegarder et quitter", "Quitter sans sauvegarder"};
        return showOptionDialog("Partie en pause", "Menu pause", options, 0) + 1;
    }
    
    // Gestion des sauvegardes
    
    @Override
    public String demanderNomSauvegarde() {
        vue.demanderNomSauvegarde();
        String nom = JOptionPane.showInputDialog(vue.getFrame(), "Nom de la sauvegarde :", "Sauvegarde", JOptionPane.QUESTION_MESSAGE);
        // Generer un nom automatique si vide
        return (nom == null || nom.trim().isEmpty()) ? "partie_" + System.currentTimeMillis() : nom.trim();
    }

    @Override
    public int demanderChoixSauvegarde(ArrayList<String> sauvegardes) {
        if (sauvegardes.isEmpty()) { 
            vue.afficherAucuneSauvegarde(); 
            return 0; 
        }
        // Construire la liste avec option d'annulation
        String[] options = new String[sauvegardes.size() + 1];
        options[0] = "0 - Annuler";
        for (int i = 0; i < sauvegardes.size(); i++) {
            options[i + 1] = (i + 1) + " - " + sauvegardes.get(i);
        }
        return showListDialog("Choisir une sauvegarde :", "Charger", options);
    }

    @Override
    public int demanderSauvegardeASupprimer(ArrayList<String> sauvegardes) {
        if (sauvegardes.isEmpty()) { 
            vue.afficherAucuneSauvegardeASupprimer(); 
            return 0; 
        }
        String[] options = new String[sauvegardes.size() + 1];
        options[0] = "0 - Annuler";
        for (int i = 0; i < sauvegardes.size(); i++) {
            options[i + 1] = (i + 1) + " - " + sauvegardes.get(i);
        }
        return showListDialog("Choisir une sauvegarde a supprimer :", "Supprimer", options);
    }
    
    // Gameplay avec images

    @Override
    public String attendreEntreePause() {
        vue.afficherInstructionPause();
        String[] options = {"Jouer le tour", "Pause"};
        int choix = JOptionPane.showOptionDialog(
            vue.getFrame(), "Pret pour le prochain tour ?", "Nouveau tour",
            JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE, 
            null, options, options[0]
        );
        // Retourne "p" si pause demandee
        return choix == 1 ? "p" : "";
    }

    /**
     * Affiche les cartes du joueur et demande laquelle mettre face visible
     * Utilise des images pour les boutons de selection
     */
    @Override
    public int demanderCarteVisibleOffre(String nomJoueur, ArrayList<String> cartes) {
        vue.afficherChoixOffreJoueur(nomJoueur);
        
        // Charger les images des cartes
        ImageIcon[] optionsIcons = new ImageIcon[cartes.size()];
        for (int i = 0; i < cartes.size(); i++) {
            optionsIcons[i] = vue.getIconeCarte(cartes.get(i));
        }
        
        // Afficher le dialogue avec les images comme boutons
        int choix = JOptionPane.showOptionDialog(
            vue.getFrame(),
            nomJoueur + ", choisissez la carte a mettre face visible :",
            "Phase d'offre - " + nomJoueur,
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.PLAIN_MESSAGE,
            null,
            optionsIcons,
            optionsIcons.length > 0 ? optionsIcons[0] : null
        );
        
        // Retourne l'index (base 1)
        return (choix == -1) ? 1 : choix + 1;
    }

    /**
     * Affiche les offres disponibles et demande quelle carte prendre
     * Affiche le nom du proprietaire sous chaque carte
     */
    @Override
    public int demanderChoixPrise(String nomJoueur, int nbPossibilites, ArrayList<String> descriptionsChoix) {
        vue.afficherDemandePriseCarte(nomJoueur);
        
        // Recuperer les descriptions depuis la vue si non fournies
        ArrayList<String> descriptions = descriptionsChoix;
        if (descriptions == null || descriptions.isEmpty()) {
            descriptions = vue.getEtViderOptionsPrise();
        }
        
        // Fallback vers une liste textuelle si pas de descriptions
        if (descriptions == null || descriptions.isEmpty()) {
            String[] fallbackOptions = new String[nbPossibilites];
            for (int i = 0; i < nbPossibilites; i++) {
                fallbackOptions[i] = "Option " + (i + 1);
            }
            return showListDialog(nomJoueur + ", choisissez une carte :", "Phase de prise", fallbackOptions) + 1;
        }
        
        // Construire les icones avec le nom du proprietaire
        ImageIcon[] optionsIcons = new ImageIcon[descriptions.size()];
        
        for (int i = 0; i < descriptions.size(); i++) {
            String desc = descriptions.get(i);
            String nomCartePourImage = desc;
            String nomProprietaire = "";
            
            // Parser le format "Visible:NomCarte [Proprietaire]" ou "Cachée:FaceVerso [Proprietaire]"
            if (desc.startsWith("Cachée:") || desc.startsWith("Cachee:")) {
                nomCartePourImage = "FaceVerso";
            } else if (desc.startsWith("Visible:")) {
                String apresVisible = desc.substring("Visible:".length());
                // Trouver le crochet ouvrant qui delimite le proprietaire
                int bracketIndex = apresVisible.lastIndexOf('[');
                if (bracketIndex > 0) {
                    nomCartePourImage = apresVisible.substring(0, bracketIndex).trim();
                } else {
                    nomCartePourImage = apresVisible.trim();
                }
            }
            
            // Extraire le nom du proprietaire entre crochets
            int lastBracketOpen = desc.lastIndexOf('[');
            int lastBracketClose = desc.lastIndexOf(']');
            if (lastBracketOpen != -1 && lastBracketClose != -1 && lastBracketClose > lastBracketOpen) {
                nomProprietaire = desc.substring(lastBracketOpen + 1, lastBracketClose);
            }
            
            // Creer l'icone avec le nom du proprietaire en dessous
            ImageIcon iconBase = vue.getIconeCarte(nomCartePourImage);
            optionsIcons[i] = vue.ajouterTexteSousIcone(iconBase, nomProprietaire);
        }

        // Afficher le dialogue avec les images
        int choix = JOptionPane.showOptionDialog(
            vue.getFrame(),
            nomJoueur + ", choisissez une carte a prendre :",
            "Phase de prise - " + nomJoueur,
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
        // Appel de la version avec descriptions (qui les recuperera depuis la vue)
        return demanderChoixPrise(nomJoueur, nbPossibilites, null);
    }
    
    // Autres méthodes de IControlleur
    
    @Override 
    public int demanderCarteARetourner(int numeroJoueur, ArrayList<String> nomsCartes) {
        String[] options = nomsCartes.toArray(new String[0]);
        return showListDialog("Quelle carte retourner ?", "Action", options) + 1;
    }

    @Override 
    public int demanderJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles) {
        // Construire la liste des joueurs disponibles
        String[] options = new String[joueursDisponibles.size()];
        for(int i = 0; i < joueursDisponibles.size(); i++) {
            options[i] = "Joueur " + joueursDisponibles.get(i);
        }
        int idx = showListDialog("Chez qui prendre ?", "Cible", options);
        return joueursDisponibles.get(idx);
    }

    @Override 
    public int demanderTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible) {
        // Construire les options disponibles
        ArrayList<String> opts = new ArrayList<>();
        ArrayList<Integer> vals = new ArrayList<>();
        if(carteVisibleNom != null) { 
            opts.add("Visible: " + carteVisibleNom); 
            vals.add(1); 
        }
        if(carteCacheeDisponible) { 
            opts.add("Carte cachee"); 
            vals.add(2); 
        }
        
        int choix = showOptionDialog("Quelle carte ?", "Choix", opts.toArray(new String[0]), 0);
        return vals.get(choix);
    }

    @Override 
    public boolean demanderConfirmation(String message) {
        return JOptionPane.showConfirmDialog(
            vue.getFrame(), message, "Confirmation", JOptionPane.YES_NO_OPTION
        ) == JOptionPane.YES_OPTION;
    }

    @Override 
    public boolean demanderNettoyageConsole() { 
        // Non pertinent en mode graphique
        return false; 
    }
    
    @Override 
    public void fermer() { 
        vue.getFrame().dispose(); 
    }
}