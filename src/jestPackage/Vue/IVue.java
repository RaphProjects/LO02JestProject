package jestPackage.Vue;

import java.util.ArrayList;
import jestPackage.Modele.Carte;
import jestPackage.Modele.Couleur;

/**
 * Interface de vue (couche présentation) du jeu Jest.
 * <p>
 * Cette interface définit l'ensemble des opérations d'affichage et d'interaction
 * utilisateur nécessaires au déroulement de la partie, indépendamment du support
 * (console, interface graphique, etc.).
 * </p>
 * <p>
 * Les méthodes sont regroupées par grandes fonctionnalités : trophées, initialisation,
 * déroulement de partie, menus, sauvegarde/chargement, affichages génériques, etc.
 * </p>
 */
public interface IVue {

    // TROPHÉES

    /**
     * Annonce la mise en place des trophées en début de partie.
     */
    void annonceTrophees();

    /**
     * Affiche les informations détaillées d'un trophée.
     *
     * @param carte carte représentant le trophée
     */
    void afficherInfosTrophee(Carte carte);

    /**
     * Affiche qu'un joueur a remporté un trophée.
     *
     * @param nomTrophee nom du trophée
     * @param numJoueur numéro du joueur gagnant
     */
    void afficherTropheeRemporte(String nomTrophee, int numJoueur);

    /**
     * Affiche qu'il y a égalité pour un trophée (avant éventuels départages).
     *
     * @param nomTrophee nom du trophée concerné
     */
    void afficherEgaliteTrophee(String nomTrophee);

    /**
     * Affiche qu'il y a égalité parfaite pour un trophée (aucun départage possible).
     *
     * @param nomTrophee nom du trophée concerné
     */
    void afficherEgaliteParfaiteTrophee(String nomTrophee);

    /**
     * Affiche qu'un trophée n'a pas pu être attribué.
     *
     * @param conditionTrophee description/condition du trophée non attribué
     */
    void afficherTropheeNonAttribue(String conditionTrophee);

    /**
     * Affiche le gagnant du trophée "plus grande carte d'une couleur" (et la valeur).
     *
     * @param numJoueur numéro du joueur gagnant
     * @param couleur couleur concernée
     * @param valeur valeur déterminante
     */
    void afficherPlusGrandeCarteCouleur(int numJoueur, Couleur couleur, int valeur);

    /**
     * Affiche le gagnant du trophée "plus petite carte d'une couleur" (et la valeur).
     *
     * @param numJoueur numéro du joueur gagnant
     * @param couleur couleur concernée
     * @param valeur valeur déterminante
     */
    void afficherPlusPetiteCarteCouleur(int numJoueur, Couleur couleur, int valeur);

    //  INITIALISATION JOUEURS 

    /**
     * Affiche le message de bienvenue.
     */
    void afficherBienvenue();

    /**
     * Affiche une demande invitant à saisir le nombre de joueurs.
     */
    void demanderNombreJoueurs();

    /**
     * Affiche un message indiquant que le nombre de joueurs saisi est invalide.
     */
    void afficherNombreJoueursInvalide();

    /**
     * Affiche une demande invitant à choisir le type (réel/virtuel) pour un joueur.
     *
     * @param numJoueur numéro du joueur en cours de création
     */
    void demanderTypeJoueur(int numJoueur);

    /**
     * Affiche la création d'un joueur réel.
     *
     * @param numJoueur numéro du joueur créé
     */
    void afficherCreationJoueurReel(int numJoueur);

    /**
     * Affiche une demande invitant à choisir la stratégie d'un joueur virtuel.
     *
     * @param numJoueur numéro du joueur en cours de création
     */
    void demanderStrategieJoueurVirtuel(int numJoueur);

    /**
     * Affiche la création d'un joueur virtuel.
     *
     * @param numJoueur numéro du joueur créé
     */
    void afficherCreationJoueurVirtuel(int numJoueur);

    /**
     * Affiche un message indiquant que le type de joueur choisi est invalide.
     */
    void afficherTypeJoueurInvalide();

    //  EXTENSION ET VARIANTE 

    /**
     * Affiche une demande invitant à choisir une extension.
     */
    void demanderExtension();

    /**
     * Affiche l'extension choisie.
     *
     * @param extension identifiant/numéro de l'extension
     */
    void afficherExtensionChoisie(int extension);

    /**
     * Affiche un message indiquant que l'extension choisie est invalide.
     */
    void afficherExtensionInvalide();

    /**
     * Affiche une demande invitant à choisir une variante.
     */
    void demanderVariante();

    /**
     * Affiche la variante choisie.
     *
     * @param variante identifiant/numéro de la variante
     */
    void afficherVarianteChoisie(int variante);

    /**
     * Affiche un message indiquant que la variante choisie est invalide.
     */
    void afficherVarianteInvalide();

    //  JEST FINAL ET SCORES 

    /**
     * Affiche le Jest final d'un joueur.
     *
     * @param numJoueur numéro du joueur
     */
    void afficherJestFinalJoueur(int numJoueur);

    /**
     * Affiche le titre/entête du classement final.
     */
    void afficherClassementFinal();

    /**
     * Affiche le score d'un joueur au classement final.
     *
     * @param rang rang dans le classement (1 = premier)
     * @param numJoueur numéro du joueur
     * @param score score final
     */
    void afficherScoreJoueur(int rang, int numJoueur, int score);

    /**
     * Affiche une information indiquant que la variante inversée est appliquée.
     */
    void afficherVarianteInversee();

    //  SAUVEGARDE ET CHARGEMENT 

    /**
     * Indique que la sauvegarde a réussi.
     *
     * @param chemin chemin du fichier sauvegardé
     */
    void afficherSauvegardeReussie(String chemin);

    /**
     * Affiche une erreur lors de la sauvegarde.
     *
     * @param message message d'erreur
     */
    void afficherErreurSauvegarde(String message);

    /**
     * Indique que le chargement a réussi.
     *
     * @param chemin chemin du fichier chargé
     */
    void afficherChargementReussi(String chemin);

    /**
     * Indique qu'un fichier n'a pas été trouvé.
     *
     * @param chemin chemin du fichier recherché
     */
    void afficherFichierNonTrouve(String chemin);

    /**
     * Affiche une erreur de lecture (I/O) lors du chargement.
     *
     * @param message message d'erreur
     */
    void afficherErreurLecture(String message);

    /**
     * Affiche une erreur de désérialisation lors du chargement.
     *
     * @param message message d'erreur
     */
    void afficherErreurDeserialisation(String message);

    /**
     * Indique qu'une suppression de sauvegarde a réussi.
     *
     * @param nomFichier nom (logique) de la sauvegarde supprimée
     */
    void afficherSuppressionReussie(String nomFichier);

    /**
     * Indique qu'une suppression de sauvegarde a échoué.
     *
     * @param nomFichier nom (logique) de la sauvegarde concernée
     */
    void afficherErreurSuppression(String nomFichier);

    //  MENUS 

    /**
     * Affiche le menu principal.
     */
    void afficherMenuPrincipal();

    /**
     * Affiche une demande générique de choix à l'utilisateur.
     */
    void demanderChoix();

    /**
     * Affiche le menu pause.
     */
    void afficherMenuPause();

    /**
     * Affiche une demande de saisie du nom de sauvegarde.
     */
    void demanderNomSauvegarde();

    /**
     * Indique qu'il n'existe aucune sauvegarde disponible.
     */
    void afficherAucuneSauvegarde();

    /**
     * Indique qu'il n'existe aucune sauvegarde à supprimer.
     */
    void afficherAucuneSauvegardeASupprimer();

    /**
     * Affiche le titre de la liste des sauvegardes.
     */
    void afficherTitreSauvegardes();

    /**
     * Affiche un élément de sauvegarde dans une liste numérotée.
     *
     * @param index index/numéro de l'élément
     * @param nomSauvegarde nom de la sauvegarde
     */
    void afficherElementSauvegarde(int index, String nomSauvegarde);

    /**
     * Affiche l'option "retour" dans un menu.
     */
    void afficherOptionRetour();

    /**
     * Affiche une demande invitant à choisir une sauvegarde.
     */
    void demanderChoixSauvegarde();

    /**
     * Affiche une demande invitant à choisir une sauvegarde à supprimer.
     */
    void demanderSauvegardeASupprimer();

    /**
     * Affiche la liste des sauvegardes disponibles.
     *
     * @param sauvegardes liste des noms de sauvegardes
     */
    void afficherListeSauvegardes(ArrayList<String> sauvegardes);

    // DÉROULEMENT DU JEU 

    /**
     * Affiche le début d'un tour.
     *
     * @param numeroTour numéro du tour
     */
    void afficherDebutTour(int numeroTour);

    /**
     * Affiche l'instruction de mise en pause (ex. appuyer sur P).
     */
    void afficherInstructionPause();

    /**
     * Affiche la fin de jeu.
     */
    void afficherFinJeu();

    /**
     * Affiche un message de fermeture / au revoir.
     */
    void afficherAuRevoir();

    /**
     * Affiche un message indiquant que le choix saisi est invalide.
     */
    void afficherChoixInvalide();

    //  AFFICHAGES GÉNÉRIQUES & ERREURS

    /**
     * Affiche un message générique (information).
     *
     * @param message texte à afficher
     */
    void afficherMessage(String message);

    /**
     * Affiche un message d'erreur générique.
     *
     * @param message texte d'erreur
     */
    void afficherErreur(String message);

    /**
     * Affiche une ligne vide (séparation).
     */
    void afficherLigneVide();

    /**
     * Affiche un séparateur visuel.
     */
    void afficherSeparateur();

    /**
     * Affiche le numéro du tour.
     *
     * @param numeroTour numéro du tour
     */
    void afficherNumeroTour(int numeroTour);

    /**
     * Affiche une erreur indiquant un nombre incohérent de cartes non jouées.
     */
    void afficherErreurNombreCartesNonJouees();

    /**
     * Affiche une erreur indiquant que tous les joueurs ont déjà joué sur le tour courant.
     */
    void afficherErreurTousJoueursOntJoue();

    /**
     * Affiche le joueur ayant la plus grande valeur visible (et la carte associée).
     *
     * @param nomJoueur nom du joueur
     * @param carte représentation de la carte
     */
    void afficherJoueurAvecPlusGrandeValeurVisible(String nomJoueur, String carte);

    /**
     * Affiche l'information indiquant que c'est au joueur déterminé de jouer.
     */
    void afficherCestAuiDeJouer();

    /**
     * Affiche une erreur liée à la détermination du joueur ayant la plus grande valeur visible.
     */
    void afficherErreurDeterminerJoueurPlusGrandeValeurVisible();

    /**
     * Affiche une erreur de saisie (format/valeur incorrecte).
     */
    void afficherErreurSaisie();

    /**
     * Affiche une erreur indiquant que la valeur est hors plage.
     *
     * @param min borne minimale
     * @param max borne maximale
     */
    void afficherErreurPlage(int min, int max);

    // ACTIONS DE JEU 

    /**
     * Affiche l'entête de la section des offres des joueurs.
     */
    void afficherOffresDesJoueurs();

    /**
     * Affiche l'offre d'un joueur (carte visible et information sur la carte cachée).
     *
     * @param nomJoueur nom du joueur
     * @param carteVisible nom de la carte visible (ou "Aucune")
     * @param carteCachee nom de la carte cachée (ou "Aucune")
     */
    void afficherOffreJoueur(String nomJoueur, String carteVisible, String carteCachee);

    /**
     * Affiche la main d'un joueur (souvent pour debug ou affichage console).
     *
     * @param nomJoueur nom du joueur
     * @param main représentation textuelle de la main
     */
    void afficherMainJoueur(String nomJoueur, String main);

    /**
     * Indique quel joueur est en train de jouer.
     *
     * @param nomJoueur nom du joueur courant
     */
    void afficherTourJoueur(String nomJoueur);

    /**
     * Affiche l'information indiquant que le choix se fait dans sa propre offre.
     */
    void afficherChoixDansPropreOffre();

    /**
     * Affiche l'option de prise de la carte visible.
     *
     * @param carteVisible nom de la carte visible
     */
    void afficherOptionCarteVisible(String carteVisible);

    /**
     * Affiche l'option de prise de la carte cachée.
     */
    void afficherOptionCarteCachee();

    /**
     * Affiche le choix d'un joueur virtuel (IA) pour une prise dans sa propre offre.
     *
     * @param nomJoueur nom du joueur virtuel
     * @param choix numéro du choix effectué
     */
    void afficherChoixCarteJoueurVirtuel(String nomJoueur, int choix);

    /**
     * Affiche l'entête présentant l'offre d'un joueur cible.
     *
     * @param nomJoueur nom du joueur dont l'offre est affichée
     */
    void afficherOffreDeJoueur(String nomJoueur);

    /**
     * Affiche une option numérotée correspondant à une carte visible d'une offre.
     *
     * @param idxVisible index/numéro de l'option (1-based)
     * @param carteVisible nom de la carte visible
     */
    void afficherOptionCarteVisibleOffre(int idxVisible, String carteVisible);

    /**
     * Affiche une option numérotée correspondant à la carte cachée d'une offre.
     *
     * @param idxCache index/numéro de l'option (1-based)
     */
    void afficherOptionCarteCacheeOffre(int idxCache);

    /**
     * Affiche le choix d'un joueur virtuel (IA) lors d'une prise chez un adversaire.
     *
     * @param nomJoueur nom du joueur virtuel
     * @param numCarteChoisie numéro de la carte choisie (1-based)
     */
    void afficherChoixCarteJoueurVirtuelAdverse(String nomJoueur, int numCarteChoisie);

    /**
     * Affiche le choix d'un joueur (humain) lors d'une prise.
     *
     * @param nomJoueur nom du joueur
     * @param numCarteChoisie numéro de la carte choisie (1-based)
     */
    void afficherChoixCarteJoueur(String nomJoueur, int numCarteChoisie);

    /**
     * Affiche qu'un joueur se fait prendre une carte de son offre.
     *
     * @param nomJoueur nom du joueur ciblé
     */
    void afficherJoueurSeFaitPrendreCarte(String nomJoueur);

    /**
     * Affiche le Jest d'un joueur (souvent pour debug).
     *
     * @param nomJoueur nom du joueur
     */
    void afficherJestDeJoueur(String nomJoueur);

    /**
     * Affiche la demande de choix de carte visible pour constituer une offre.
     *
     * @param nomJoueur nom du joueur
     */
    void afficherChoixOffreJoueur(String nomJoueur);

    /**
     * Affiche la main du joueur local (surcharge sans argument, dépend de l'implémentation).
     */
    void afficherMainJoueur(); // Surcharge sans argument (vue joueur local)

    /**
     * Affiche une carte de la main à un index donné.
     *
     * @param index index/numéro affiché
     * @param carte représentation textuelle de la carte
     */
    void afficherCarteMain(int index, String carte);

    /**
     * Affiche la demande invitant à choisir la carte visible à placer dans l'offre.
     */
    void afficherDemandeCarteVisible();

    /**
     * Affiche la carte choisie pour l'offre.
     *
     * @param carte représentation textuelle de la carte
     */
    void afficherCarteChoisiePourOffre(String carte);

    /**
     * Demande si l'utilisateur souhaite nettoyer la console.
     */
    void demanderNettoyageConsole();

    /**
     * Affiche que la valeur saisie pour le nettoyage de console est invalide.
     */
    void afficherValeurInvalideNettoyageConsole();

    /**
     * Affiche la demande de prise de carte pour un joueur.
     *
     * @param nomJoueur nom du joueur
     */
    void afficherDemandePriseCarte(String nomJoueur);

    /**
     * Affiche un message indiquant que le choix de prise est invalide.
     */
    void afficherChoixInvalidePrise();

    /**
     * Affiche les cartes du Jest (liste).
     *
     * @param cartes cartes à afficher
     */
    void afficherCartesJest(ArrayList<Carte> cartes);

    /**
     * Affiche un message indiquant qu'une stratégie par défaut a été sélectionnée.
     */
    void afficherStrategieDefaut();

    /**
     * Affiche un message indiquant que la pioche est vide.
     */
    void afficherPiocheVide();

    /**
     * Affiche la carte choisie par une IA.
     *
     * @param stringCarte représentation textuelle de la carte
     */
    void afficherCarteChoisieIA(String stringCarte);

    /**
     * Affiche le nom de sauvegarde automatique proposé/utilisé.
     *
     * @param nom nom de la sauvegarde automatique
     */
    void afficherNomSauvegardeAuto(String nom);


    /**
     * Affiche une demande invitant un joueur à choisir une carte à retourner.
     *
     * @param numeroJoueur numéro du joueur qui effectue l'action
     * @param nomsCartes noms/labels des cartes disponibles
     */
    void afficherDemandeCarteARetourner(int numeroJoueur, ArrayList<String> nomsCartes);

    /**
     * Affiche une demande invitant à choisir un joueur cible parmi une liste.
     *
     * @param numeroJoueurActuel numéro du joueur qui effectue l'action
     * @param joueursDisponibles liste des numéros de joueurs disponibles
     */
    void afficherDemandeJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles);

    /**
     * Affiche un message indiquant que le joueur choisi n'est pas disponible.
     */
    void afficherJoueurNonDisponible();

    /**
     * Affiche une demande invitant à choisir le type de carte (visible/cachée) à cibler.
     *
     * @param numeroJoueurCible numéro du joueur cible
     * @param carteVisibleNom nom de la carte visible
     * @param carteCacheeDisponible indique si une carte cachée est disponible
     */
    void afficherDemandeTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible);

    /**
     * Affiche une demande de confirmation.
     *
     * @param message message à confirmer
     */
    void afficherDemandeConfirmation(String message);

    /**
     * Nettoie la console (si applicable à l'implémentation).
     */
    void nettoyerConsole();

    /**
     * Affiche une option de prise détaillée.
     *
     * @param numero numéro de l'option
     * @param description description textuelle
     */
    void afficherOptionPrise(int numero, String description);
}