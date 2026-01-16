package jestPackage.Modele;

import java.util.*;
import java.io.Serializable;

/**
 * Représente un joueur réel (humain).
 * <p>
 * Les décisions (constitution de l'offre et choix des prises) sont demandées via le
 * contrôleur ({@link Jeu#controleur}) et certains affichages passent par la vue
 * ({@link Jeu#vue}).
 * </p>
 */
public class JoueurReel extends Joueur implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * Construit un joueur réel.
     *
     * @param nom nom du joueur
     * @param numJoueur numéro du joueur dans la partie
     */
    public JoueurReel(String nom, int numJoueur) {
        this.nom = nom;
        this.numJoueur = numJoueur;
    }

    /**
     * Indique que ce joueur n'est pas une IA.
     *
     * @return {@code false}
     */
    @Override
    public boolean isVirtuel() {
        return false;
    }

    /**
     * Retourne l'offre courante du joueur.
     *
     * @return l'offre
     */
    @Override
    public Offre getOffre() {
        return this.offre;
    }

    /**
     * Demande au joueur de choisir quelle carte de sa main sera visible dans l'offre.
     * <p>
     * La carte non choisie devient la carte cachée. La main est ensuite vidée.
     * </p>
     */
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

    /**
     * Demande au joueur de choisir une prise parmi {@code nbPossibilites}.
     *
     * @param nbPossibilites nombre de prises disponibles
     * @return le choix (numérotation 1-based selon l'interface de contrôle)
     */
    @Override
    public int choisirPrise(int nbPossibilites) {
        return Jeu.controleur.demanderChoixPrise(this.nom, nbPossibilites);
    }

    /**
     * Variante de {@link #choisirPrise(int)} permettant de fournir des descriptions
     * textuelles pour chaque choix.
     *
     * @param nbPossibilites nombre de choix possibles
     * @param descriptionsChoix descriptions textuelles des choix
     * @return le numéro du choix (1-based)
     */
    public int choisirPrise(int nbPossibilites, ArrayList<String> descriptionsChoix) {
        return Jeu.controleur.demanderChoixPrise(this.nom, nbPossibilites, descriptionsChoix);
    }
}