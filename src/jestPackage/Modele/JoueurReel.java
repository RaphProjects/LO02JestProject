package jestPackage.Modele;

import java.util.*;
import java.io.Serializable;

public class JoueurReel extends Joueur implements Serializable {
    private static final long serialVersionUID = 1L;
    
    public JoueurReel(String nom, int numJoueur) {
        this.nom = nom;
        this.numJoueur = numJoueur;
    }

    @Override
    public boolean isVirtuel() {
        return false;
    }

    @Override
    public Offre getOffre() {
        return this.offre;
    }

    @Override
    public void deciderOffre() {
        // Préparer la liste des descriptions de cartes
        ArrayList<String> descriptionsCartes = new ArrayList<>();
        for (Carte carte : this.main) {
            descriptionsCartes.add(carte.toString());
        }
        
        // Demander au joueur quelle carte mettre visible via le contrôleur
        int numchoix = Jeu.controleur.demanderCarteVisibleOffre(this.nom, descriptionsCartes);
        
        // Afficher la carte choisie
        Jeu.vue.afficherCarteChoisiePourOffre(this.main.get(numchoix - 1).toString());

        // On met la carte choisie dans l'offre visible
        this.offre.setCarteVisible(this.main.get(numchoix - 1));
        
        // On met la carte restante dans l'offre cachée
        int indexCarteCachee = (numchoix == 1) ? 1 : 0;
        this.offre.setCarteCachee(this.main.get(indexCarteCachee));
        
        // On enlève les cartes de la main du joueur
        this.main.clear();

        // Demander si le joueur veut nettoyer la console
        Jeu.controleur.demanderNettoyageConsole();
    }

    @Override
    public int choisirPrise(int nbPossibilites) {
        return Jeu.controleur.demanderChoixPrise(this.nom, nbPossibilites);
    }
    
    /**
     * Version améliorée avec descriptions des choix
     * @param nbPossibilites nombre de choix possibles
     * @param descriptionsChoix descriptions textuelles des choix
     * @return le numéro du choix (1-based)
     */
    public int choisirPrise(int nbPossibilites, ArrayList<String> descriptionsChoix) {
        return Jeu.controleur.demanderChoixPrise(this.nom, nbPossibilites, descriptionsChoix);
    }
}