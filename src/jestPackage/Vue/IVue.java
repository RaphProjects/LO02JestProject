package jestPackage.Vue;

import java.util.ArrayList;
import jestPackage.Modele.Carte;
import jestPackage.Modele.Couleur;

public interface IVue {

    // --- TROPHÉES ---
    void annonceTrophees();
    void afficherInfosTrophee(Carte carte);
    void afficherTropheeRemporte(String nomTrophee, int numJoueur);
    void afficherEgaliteTrophee(String nomTrophee);
    void afficherEgaliteParfaiteTrophee(String nomTrophee);
    void afficherTropheeNonAttribue(String conditionTrophee);
    void afficherPlusGrandeCarteCouleur(int numJoueur, Couleur couleur, int valeur);
    void afficherPlusPetiteCarteCouleur(int numJoueur, Couleur couleur, int valeur);

    // --- INITIALISATION JOUEURS ---
    void afficherBienvenue();
    void demanderNombreJoueurs();
    void afficherNombreJoueursInvalide();
    void demanderTypeJoueur(int numJoueur);
    void afficherCreationJoueurReel(int numJoueur);
    void demanderStrategieJoueurVirtuel(int numJoueur);
    void afficherCreationJoueurVirtuel(int numJoueur);
    void afficherTypeJoueurInvalide();

    // --- EXTENSION ET VARIANTE ---
    void demanderExtension();
    void afficherExtensionChoisie(int extension);
    void afficherExtensionInvalide();
    void demanderVariante();
    void afficherVarianteChoisie(int variante);
    void afficherVarianteInvalide();

    // --- JEST FINAL ET SCORES ---
    void afficherJestFinalJoueur(int numJoueur);
    void afficherClassementFinal();
    void afficherScoreJoueur(int rang, int numJoueur, int score);
    void afficherVarianteInversee();

    // --- SAUVEGARDE ET CHARGEMENT ---
    void afficherSauvegardeReussie(String chemin);
    void afficherErreurSauvegarde(String message);
    void afficherChargementReussi(String chemin);
    void afficherFichierNonTrouve(String chemin);
    void afficherErreurLecture(String message);
    void afficherErreurDeserialisation(String message);
    void afficherSuppressionReussie(String nomFichier);
    void afficherErreurSuppression(String nomFichier);

    // --- MENUS ---
    void afficherMenuPrincipal();
    void demanderChoix();
    void afficherMenuPause();
    void demanderNomSauvegarde();
    void afficherAucuneSauvegarde();
    void afficherAucuneSauvegardeASupprimer();
    void afficherTitreSauvegardes();
    void afficherElementSauvegarde(int index, String nomSauvegarde);
    void afficherOptionRetour();
    void demanderChoixSauvegarde();
    void demanderSauvegardeASupprimer();
    void afficherListeSauvegardes(ArrayList<String> sauvegardes);

    // --- DÉROULEMENT DU JEU ---
    void afficherDebutTour(int numeroTour);
    void afficherInstructionPause();
    void afficherFinJeu();
    void afficherAuRevoir();
    void afficherChoixInvalide();
    
    // --- AFFICHAGES GÉNÉRIQUES & ERREURS ---
    void afficherMessage(String message);
    void afficherErreur(String message);
    void afficherLigneVide();
    void afficherSeparateur();
    void afficherNumeroTour(int numeroTour);
    void afficherErreurNombreCartesNonJouees();
    void afficherErreurTousJoueursOntJoue();
    void afficherJoueurAvecPlusGrandeValeurVisible(String nomJoueur, String carte);
    void afficherCestAuiDeJouer();
    void afficherErreurDeterminerJoueurPlusGrandeValeurVisible();
    void afficherErreurSaisie();
    void afficherErreurPlage(int min, int max);

    // --- ACTIONS DE JEU ---
    void afficherOffresDesJoueurs();
    void afficherOffreJoueur(String nomJoueur, String carteVisible, String carteCachee);
    void afficherMainJoueur(String nomJoueur, String main);
    void afficherTourJoueur(String nomJoueur);
    void afficherChoixDansPropreOffre();
    void afficherOptionCarteVisible(String carteVisible);
    void afficherOptionCarteCachee();
    void afficherChoixCarteJoueurVirtuel(String nomJoueur, int choix);
    void afficherOffreDeJoueur(String nomJoueur);
    void afficherOptionCarteVisibleOffre(int idxVisible, String carteVisible);
    void afficherOptionCarteCacheeOffre(int idxCache);
    void afficherChoixCarteJoueurVirtuelAdverse(String nomJoueur, int numCarteChoisie);
    void afficherChoixCarteJoueur(String nomJoueur, int numCarteChoisie);
    void afficherJoueurSeFaitPrendreCarte(String nomJoueur);
    void afficherJestDeJoueur(String nomJoueur);
    void afficherChoixOffreJoueur(String nomJoueur);
    void afficherMainJoueur(); // Surcharge sans argument (vue joueur local)
    void afficherCarteMain(int index, String carte);
    void afficherDemandeCarteVisible();
    void afficherCarteChoisiePourOffre(String carte);
    void demanderNettoyageConsole();
    void afficherValeurInvalideNettoyageConsole();
    void afficherDemandePriseCarte(String nomJoueur);
    void afficherChoixInvalidePrise();
    void afficherCartesJest(ArrayList<Carte> cartes);
    void afficherStrategieDefaut();
    void afficherPiocheVide();
    void afficherCarteChoisieIA(String stringCarte);
    void afficherNomSauvegardeAuto(String nom);
    
    // --- NOUVELLES MÉTHODES INTERACTIVES ---
    void afficherDemandeCarteARetourner(int numeroJoueur, ArrayList<String> nomsCartes);
    void afficherDemandeJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles);
    void afficherJoueurNonDisponible();
    void afficherDemandeTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible);
    void afficherDemandeConfirmation(String message);
    void nettoyerConsole();
    void afficherOptionPrise(int numero, String description);
}