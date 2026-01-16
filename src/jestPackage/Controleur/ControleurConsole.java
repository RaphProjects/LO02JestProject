package jestPackage.Controleur;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

import jestPackage.Vue.VueConsole;

/**
 * Implémentation console du contrôleur.
 * <p>
 * Cette classe gère toutes les entrées utilisateur via {@link Scanner} et délègue
 * les affichages à une {@link VueConsole}.
 * </p>
 */
public class ControleurConsole implements IControleur {

    private Scanner scanner;
    private VueConsole vue;

    /**
     * Construit un contrôleur console avec sa propre vue console et un scanner sur {@code System.in}.
     */
    public ControleurConsole() {
        this.scanner = new Scanner(System.in);
        this.vue = new VueConsole();
    }

    /**
     * Construit un contrôleur console avec une vue console fournie et un scanner sur {@code System.in}.
     *
     * @param vue vue console à utiliser
     */
    public ControleurConsole(VueConsole vue) {
        this.scanner = new Scanner(System.in);
        this.vue = vue;
    }

    /**
     * Lit un entier avec gestion d'erreur.
     * <p>
     * Tant que la saisie n'est pas un entier, la méthode redemande une entrée.
     * </p>
     *
     * @return l'entier saisi
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
     * Lit un entier contraint dans une plage inclusive.
     * <p>
     * Tant que la valeur saisie n'est pas dans {@code [min, max]}, la méthode redemande une saisie.
     * </p>
     *
     * @param min borne minimale (incluse)
     * @param max borne maximale (incluse)
     * @return l'entier saisi, garanti dans la plage
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
     * Lit une chaîne (ligne) et la retourne après {@link String#trim()}.
     *
     * @return la ligne saisie (pouvant être vide)
     */
    private String lireChaine() {
        String ligne = scanner.nextLine().trim();
        return ligne;
    }

    // ==================== INITIALISATION ====================

    /** {@inheritDoc} */
    @Override
    public int demanderNombreJoueurs() {
        vue.demanderNombreJoueurs();
        return lireEntierDansPlage(3, 4);
    }

    /** {@inheritDoc} */
    @Override
    public int demanderTypeJoueur(int numeroJoueur) {
        vue.demanderTypeJoueur(numeroJoueur);
        return lireEntierDansPlage(0, 1);
    }

    /** {@inheritDoc} */
    @Override
    public int demanderStrategieJoueurVirtuel(int numeroJoueur) {
        vue.demanderStrategieJoueurVirtuel(numeroJoueur);
        return lireEntier();
    }

    /** {@inheritDoc} */
    @Override
    public int demanderExtension() {
        vue.demanderExtension();
        return lireEntierDansPlage(0, 1);
    }

    /** {@inheritDoc} */
    @Override
    public int demanderVariante() {
        vue.demanderVariante();
        return lireEntierDansPlage(0, 1);
    }

    // ==================== MENUS ====================

    /** {@inheritDoc} */
    @Override
    public int afficherMenuPrincipal() {
        vue.afficherMenuPrincipal();
        vue.demanderChoix();
        return lireEntierDansPlage(1, 4);
    }

    /** {@inheritDoc} */
    @Override
    public int afficherMenuPause() {
        vue.afficherMenuPause();
        vue.demanderChoix();
        return lireEntierDansPlage(1, 4);
    }

    // ==================== SAUVEGARDE/CHARGEMENT ====================

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public String attendreEntreePause() {
        vue.afficherInstructionPause();
        return lireChaine();
    }

    /** {@inheritDoc} */
    @Override
    public int demanderCarteARetourner(int numeroJoueur, ArrayList<String> nomsCartes) {
        vue.afficherDemandeCarteARetourner(numeroJoueur, nomsCartes);
        return lireEntierDansPlage(1, nomsCartes.size());
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public int demanderTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible) {
        vue.afficherDemandeTypeCarte(numeroJoueurCible, carteVisibleNom, carteCacheeDisponible);

        int min = carteVisibleNom != null ? 1 : 2;
        int max = carteCacheeDisponible ? 2 : 1;

        return lireEntierDansPlage(min, max);
    }

    /** {@inheritDoc} */
    @Override
    public boolean demanderConfirmation(String message) {
        vue.afficherDemandeConfirmation(message);
        String reponse = lireChaine().toLowerCase();
        return reponse.equals("o") || reponse.equals("oui") || reponse.equals("y") || reponse.equals("yes");
    }

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
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

    /** {@inheritDoc} */
    @Override
    public int demanderChoixPrise(String nomJoueur, int nbPossibilites) {
        return demanderChoixPrise(nomJoueur, nbPossibilites, null);
    }

    /**
     * Ferme le scanner proprement.
     * <p>
     * Attention : fermer un {@link Scanner} construit sur {@code System.in} ferme également
     * {@code System.in}.
     * </p>
     */
    public void fermer() {
        if (scanner != null) {
            scanner.close();
        }
    }
}