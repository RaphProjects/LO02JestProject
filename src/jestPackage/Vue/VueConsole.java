package jestPackage.Vue;

import java.util.ArrayList;
import jestPackage.Modele.Carte;
import jestPackage.Modele.Couleur;

/**
 * Implémentation console de {@link IVue}.
 * <p>
 * Cette vue affiche l'ensemble des messages du jeu dans la sortie standard
 * ({@link System#out}) et les erreurs dans la sortie d'erreur ({@link System#err}).
 * </p>
 * <p>
 * Remarque : la méthode {@link #nettoyerConsole()} simule un nettoyage de console en
 * affichant un grand nombre de lignes vides.
 * </p>
 */
public class VueConsole implements IVue {

    // TROPHÉES

    /** {@inheritDoc} */
    public void annonceTrophees() {
        System.out.println("Les trophées du jeu sont : ");
    }

    /** {@inheritDoc} */
    public void afficherInfosTrophee(Carte carte) {
        System.out.println("- " + carte.getNom());
        System.out.println("  Condition : " + carte.getBandeauTrophee().toString());
    }

    /** {@inheritDoc} */
    public void afficherTropheeRemporte(String nomTrophee, int numJoueur) {
        System.out.println("Le trophée " + nomTrophee + " est remporté par Joueur " + numJoueur + " !");
    }

    /** {@inheritDoc} */
    public void afficherEgaliteTrophee(String nomTrophee) {
        System.out.println("Égalité pour le trophée " + nomTrophee + ". On regarde qui a la carte de plus haute valeur d'abord");
    }

    /** {@inheritDoc} */
    public void afficherEgaliteParfaiteTrophee(String nomTrophee) {
        System.out.println("Égalité parfaite pour le trophée " + nomTrophee + ". Cela est impossible, il y a un bug");
    }

    /** {@inheritDoc} */
    public void afficherTropheeNonAttribue(String conditionTrophee) {
        System.out.println("Le trophée " + conditionTrophee + " n'a été attribué à aucun joueur.");
    }

    /** {@inheritDoc} */
    public void afficherPlusGrandeCarteCouleur(int numJoueur, Couleur couleur, int valeur) {
        System.out.println("Joueur " + numJoueur + " a la plus grande carte de la couleur " + couleur + " avec une valeur de " + valeur + ".");
    }

    /** {@inheritDoc} */
    public void afficherPlusPetiteCarteCouleur(int numJoueur, Couleur couleur, int valeur) {
        System.out.println("Joueur " + numJoueur + " a la plus petite carte de la couleur " + couleur + " avec une valeur de " + valeur + ".");
    }

    // INITIALISATION JOUEURS

    /** {@inheritDoc} */
    public void afficherBienvenue() {
        System.out.println("Bienvenue dans le jeu Jest !");
    }

    /** {@inheritDoc} */
    public void demanderNombreJoueurs() {
        System.out.println("Entrez le nombre de joueurs (3 ou 4) : ");
    }

    /** {@inheritDoc} */
    public void afficherNombreJoueursInvalide() {
        System.out.println("Nombre de joueurs invalide. Le jeu nécessite 3 ou 4 joueurs.");
    }

    /** {@inheritDoc} */
    public void demanderTypeJoueur(int numJoueur) {
        System.out.println("Est-ce que le joueur " + numJoueur + " est un reel ou virtuel ? (0 pour reel et 1 pour virtuel) : ");
    }

    /** {@inheritDoc} */
    public void afficherCreationJoueurReel(int numJoueur) {
        System.out.println("Creation du joueur reel \"Joueur " + numJoueur + "\".");
    }

    /** {@inheritDoc} */
    public void demanderStrategieJoueurVirtuel(int numJoueur) {
        System.out.println("Choisissez la stratégie de prise pour le joueur " + numJoueur + " (1 pour prudente, 2 pour aléatoire) : ");
    }

    /** {@inheritDoc} */
    public void afficherCreationJoueurVirtuel(int numJoueur) {
        System.out.println("Creation du joueur virtuel \"Joueur " + numJoueur + "\" (IA).");
    }

    /** {@inheritDoc} */
    public void afficherTypeJoueurInvalide() {
        System.out.println("Valeur invalide. Veuillez entrer 0 pour reel ou 1 pour virtuel.");
    }

    // EXTENSION ET VARIANTE

    /** {@inheritDoc} */
    public void demanderExtension() {
        System.out.println("Choisissez l'extension du jeu (0 pour classique, 1 pour plus de cartes) : ");
    }

    /** {@inheritDoc} */
    public void afficherExtensionChoisie(int extension) {
        System.out.println("Extension choisie : " + (extension == 0 ? "Classique" : "Plus de cartes"));
    }

    /** {@inheritDoc} */
    public void afficherExtensionInvalide() {
        System.out.println("Extension invalide. L'Extension classique sera utilisée par défaut.");
    }

    /** {@inheritDoc} */
    public void demanderVariante() {
        System.out.println("Choisissez la Variante du jeu (0 pour classique, 1 pour inversé) : ");
    }

    /** {@inheritDoc} */
    public void afficherVarianteChoisie(int variante) {
        System.out.println("Variante choisie : " + (variante == 0 ? "Classique" : "Inversé"));
    }

    /** {@inheritDoc} */
    public void afficherVarianteInvalide() {
        System.out.println("Variante invalide. La variante classique sera utilisée par défaut.");
    }

    // JEST FINAL ET SCORES

    /** {@inheritDoc} */
    public void afficherJestFinalJoueur(int numJoueur) {
        System.out.println("Jest final du Joueur " + numJoueur + " : ");
    }

    /** {@inheritDoc} */
    public void afficherClassementFinal() {
        System.out.println("Classement final des joueurs : ");
    }

    /** {@inheritDoc} */
    public void afficherScoreJoueur(int rang, int numJoueur, int score) {
        System.out.println(rang + " : Joueur " + numJoueur + " avec un score de " + score);
    }

    /** {@inheritDoc} */
    public void afficherVarianteInversee() {
        System.out.println("Variante inversée : le joueur avec le score le plus bas gagne !");
    }

    // SAUVEGARDE ET CHARGEMENT

    /** {@inheritDoc} */
    public void afficherSauvegardeReussie(String chemin) {
        System.out.println("Partie sauvegardée avec succès dans : " + chemin);
    }

    /** {@inheritDoc} */
    public void afficherErreurSauvegarde(String message) {
        System.err.println("Erreur lors de la sauvegarde : " + message);
    }

    /** {@inheritDoc} */
    public void afficherChargementReussi(String chemin) {
        System.out.println("Partie chargée avec succès depuis : " + chemin);
    }

    /** {@inheritDoc} */
    public void afficherFichierNonTrouve(String chemin) {
        System.err.println("Fichier de sauvegarde non trouvé : " + chemin);
    }

    /** {@inheritDoc} */
    public void afficherErreurLecture(String message) {
        System.err.println("Erreur de lecture : " + message);
    }

    /** {@inheritDoc} */
    public void afficherErreurDeserialisation(String message) {
        System.err.println("Erreur de désérialisation : " + message);
    }

    /** {@inheritDoc} */
    public void afficherSuppressionReussie(String nomFichier) {
        System.out.println("Sauvegarde supprimée : " + nomFichier);
    }

    /** {@inheritDoc} */
    public void afficherErreurSuppression(String nomFichier) {
        System.err.println("Impossible de supprimer : " + nomFichier);
    }

    // MENUS

    /** {@inheritDoc} */
    public void afficherMenuPrincipal() {
        System.out.println("\n╔════════════════════════════════════╗");
        System.out.println("║         BIENVENUE DANS JEST        ║");
        System.out.println("╠════════════════════════════════════╣");
        System.out.println("║  1. Nouvelle partie                ║");
        System.out.println("║  2. Charger une partie             ║");
        System.out.println("║  3. Supprimer une sauvegarde       ║");
        System.out.println("║  4. Quitter                        ║");
        System.out.println("╚════════════════════════════════════╝");
    }

    /** {@inheritDoc} */
    public void demanderChoix() {
        System.out.print("Votre choix : ");
    }

    /** {@inheritDoc} */
    public void afficherMenuPause() {
        System.out.println("\n┌──────────────────────────────┐");
        System.out.println("│         MENU PAUSE           │");
        System.out.println("├──────────────────────────────┤");
        System.out.println("│  1. Continuer la partie      │");
        System.out.println("│  2. Sauvegarder la partie    │");
        System.out.println("│  3. Sauvegarder et quitter   │");
        System.out.println("│  4. Quitter sans sauvegarder │");
        System.out.println("└──────────────────────────────┘");
    }

    /** {@inheritDoc} */
    public void demanderNomSauvegarde() {
        System.out.print("Nom de la sauvegarde : ");
    }

    /** {@inheritDoc} */
    public void afficherAucuneSauvegarde() {
        System.out.println("Aucune sauvegarde disponible.");
    }

    /** {@inheritDoc} */
    public void afficherAucuneSauvegardeASupprimer() {
        System.out.println("Aucune sauvegarde à supprimer.");
    }

    /** {@inheritDoc} */
    public void afficherTitreSauvegardes() {
        System.out.println("\nSauvegardes disponibles");
    }

    /** {@inheritDoc} */
    public void afficherElementSauvegarde(int index, String nomSauvegarde) {
        System.out.println(index + ". " + nomSauvegarde);
    }

    /** {@inheritDoc} */
    public void afficherOptionRetour() {
        System.out.println("0. Retour");
    }

    /** {@inheritDoc} */
    public void demanderChoixSauvegarde() {
        System.out.print("Choisissez une sauvegarde : ");
    }

    /** {@inheritDoc} */
    public void demanderSauvegardeASupprimer() {
        System.out.print("Sauvegarde à supprimer : ");
    }

    /** {@inheritDoc} */
    public void afficherListeSauvegardes(ArrayList<String> sauvegardes) {
        afficherTitreSauvegardes();
        for (int i = 0; i < sauvegardes.size(); i++) {
            afficherElementSauvegarde(i + 1, sauvegardes.get(i));
        }
        afficherOptionRetour();
    }

    // DÉROULEMENT DU JEU

    /** {@inheritDoc} */
    public void afficherDebutTour(int numeroTour) {
        System.out.println("\n--- Début du Tour " + numeroTour + " ---");
    }

    /** {@inheritDoc} */
    public void afficherInstructionPause() {
        System.out.println("Appuyez sur 'P' et Entrée pour le menu pause, ou simplement Entrée pour jouer le tour...");
    }

    /** {@inheritDoc} */
    public void afficherFinJeu() {
        System.out.println("La pioche est vide. Fin du jeu.");
    }

    /** {@inheritDoc} */
    public void afficherAuRevoir() {
        System.out.println("Au revoir !");
    }

    /** {@inheritDoc} */
    public void afficherChoixInvalide() {
        System.out.println("Choix invalide.");
    }

    // AFFICHAGES GÉNÉRIQUES

    /** {@inheritDoc} */
    public void afficherMessage(String message) {
        System.out.println(message);
    }

    /** {@inheritDoc} */
    public void afficherErreur(String message) {
        System.err.println(message);
    }

    /** {@inheritDoc} */
    public void afficherLigneVide() {
        System.out.println();
    }

    /** {@inheritDoc} */
    public void afficherSeparateur() {
        System.out.println("──────────────────────────────────────");
    }

    /** {@inheritDoc} */
    public void afficherNumeroTour(int numeroTour) {
        System.out.println("----- Tour " + numeroTour + " -----");
    }

    /** {@inheritDoc} */
    public void afficherErreurNombreCartesNonJouees() {
        System.out.println("Erreur : le nombre de cartes non jouées ne correspond pas au nombre de joueurs.");
    }

    /** {@inheritDoc} */
    public void afficherErreurTousJoueursOntJoue() {
        //System.out.println("Erreur : tous les joueurs ont déjà joué ce tour.");
    }

    /** {@inheritDoc} */
    public void afficherJoueurAvecPlusGrandeValeurVisible(String nomJoueur, String carte) {
        System.out.println("Le joueur " + nomJoueur + " a la plus grande valeur visible avec la carte " + carte + ".");
    }

    /** {@inheritDoc} */
    public void afficherCestAuiDeJouer() {
        System.out.println("C'est à lui de jouer.");
    }

    /** {@inheritDoc} */
    public void afficherErreurDeterminerJoueurPlusGrandeValeurVisible() {
        System.out.println("Erreur : impossible de déterminer le joueur avec la plus grande valeur visible.");
    }

    /** {@inheritDoc} */
    public void afficherOffresDesJoueurs() {
        System.out.println("Offres des joueurs :");
    }

    /** {@inheritDoc} */
    public void afficherOffreJoueur(String nomJoueur, String carteVisible, String carteCachee) {
        System.out.println(nomJoueur + " - Carte Visible: " + carteVisible + ", Carte Cachee: " + carteCachee);
    }

    /** {@inheritDoc} */
    public void afficherMainJoueur(String nomJoueur, String main) {
        System.out.println("Main de " + nomJoueur + " : " + main);
    }

    /** {@inheritDoc} */
    public void afficherTourJoueur(String nomJoueur) {
        System.out.println("C'est au tour de " + nomJoueur + " de jouer.");
    }

    /** {@inheritDoc} */
    public void afficherChoixDansPropreOffre() {
        System.out.println("Toutes les autres offres sont vides. Choisissez dans votre offre.");
    }

    /** {@inheritDoc} */
    public void afficherOptionCarteVisible(String carteVisible) {
        System.out.println("1 : Carte Visible - " + carteVisible);
    }

    /** {@inheritDoc} */
    public void afficherOptionCarteCachee() {
        System.out.println("2 : Carte Cachée - Inconnue");
    }

    /** {@inheritDoc} */
    public void afficherChoixCarteJoueurVirtuel(String nomJoueur, int choix) {
        System.out.println("Le joueur Virtuel" + nomJoueur + " a choisi la carte " + choix);
    }

    /** {@inheritDoc} */
    public void afficherOffreDeJoueur(String nomJoueur) {
        System.out.println("Offre de " + nomJoueur + " :");
    }

    /** {@inheritDoc} */
    public void afficherOptionCarteVisibleOffre(int idxVisible, String carteVisible) {
        System.out.println(idxVisible + " : Carte Visible - " + carteVisible);
    }

    /** {@inheritDoc} */
    public void afficherOptionCarteCacheeOffre(int idxCache) {
        System.out.println(idxCache + " : Carte Cachée - Inconnue");
    }

    /** {@inheritDoc} */
    public void afficherChoixCarteJoueurVirtuelAdverse(String nomJoueur, int numCarteChoisie) {
        System.out.println("Le joueur Virtuel" + nomJoueur + " a choisi la carte " + numCarteChoisie);
    }

    /** {@inheritDoc} */
    public void afficherChoixCarteJoueur(String nomJoueur, int numCarteChoisie) {
        System.out.println("Le joueur " + nomJoueur + " a choisi la carte " + numCarteChoisie);
    }

    /** {@inheritDoc} */
    public void afficherJoueurSeFaitPrendreCarte(String nomJoueur) {
        System.out.println("Le joueur " + nomJoueur + " s'est fait prendre une carte.");
    }

    /** {@inheritDoc} */
    public void afficherJestDeJoueur(String nomJoueur) {
        System.out.println("Jest de " + nomJoueur + " : ");
    }

    /** {@inheritDoc} */
    public void afficherChoixOffreJoueur(String nomJoueur) {
        System.out.println("Joueur " + nomJoueur + ", veuillez choisir votre offre.");
    }

    /** {@inheritDoc} */
    public void afficherMainJoueur() {
        System.out.println("Votre main :");
    }

    /** {@inheritDoc} */
    public void afficherCarteMain(int index, String carte) {
        System.out.println((index + 1) + ": " + carte);
    }

    /** {@inheritDoc} */
    public void afficherDemandeCarteVisible() {
        System.out.println("Entrez le numéro de la carte que vous souhaitez offrir visible: ");
    }

    /** {@inheritDoc} */
    public void afficherCarteChoisiePourOffre(String carte) {
        System.out.println("Vous avez choisi de faire une offre avec la carte : " + carte);
    }

    /** {@inheritDoc} */
    public void demanderNettoyageConsole() {
        System.out.print("Voulez vous nettoyer la console pour dissimuler votre choix ? (0 pour oui, 1 pour non) : ");
    }

    /** {@inheritDoc} */
    public void afficherValeurInvalideNettoyageConsole() {
        System.out.print("Valeur invalide. Veuillez entrer 0 pour oui ou 1 pour non : ");
    }

    /** {@inheritDoc} */
    public void afficherDemandePriseCarte(String nomJoueur) {
        System.out.println("Joueur " + nomJoueur + ", veuillez entrer le numéro de la carte que vous souhaitez prendre : ");
    }

    /** {@inheritDoc} */
    public void afficherChoixInvalidePrise() {
        System.out.println("Choix invalide. Veuillez entrer un numéro valide : ");
    }

	public void afficherCartesJest(ArrayList<Carte> cartes) {
		System.out.println("Cartes dans le Jest :");
		for (Carte carte : cartes) {
			System.out.println("- " + carte.getNom());
		}

	}

	public void afficherStrategieDefaut() {
		System.out.println("Numéro de stratégie invalide, on utilise la stratégie prudente par défaut.");

	}

	public void afficherPiocheVide() {
		System.out.println("La pioche est vide.");
	}

	public void afficherCarteChoisieIA(String stringCarte) {
		System.out.print("L'IA a choisi " + stringCarte + " comme carte visible.\n");

	}

	public void afficherErreurSaisie() {
	    System.out.println(" Entrée invalide. Veuillez entrer un nombre.");
	}

	public void afficherErreurPlage(int min, int max) {
	    System.out.println(" Veuillez entrer un nombre entre " + min + " et " + max + ".");
	}

	public void afficherNomSauvegardeAuto(String nom) {
	    System.out.println(" Nom de sauvegarde automatique : " + nom);
	}

	public void afficherDemandeCarteARetourner(int numeroJoueur, ArrayList<String> nomsCartes) {
	    System.out.println("\n Joueur " + numeroJoueur + ", choisissez la carte à retourner face visible :");
	    for (int i = 0; i < nomsCartes.size(); i++) {
	        System.out.println("  " + (i + 1) + ". " + nomsCartes.get(i));
	    }
	    System.out.print("Votre choix : ");
	}

	public void afficherDemandeJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles) {
	    System.out.println("\n Joueur " + numeroJoueurActuel + ", chez quel joueur voulez-vous prendre une carte ?");
	    System.out.print("Joueurs disponibles : ");
	    for (Integer num : joueursDisponibles) {
	        System.out.print(num + " ");
	    }
	    System.out.print("\nVotre choix : ");
	}

	public void afficherJoueurNonDisponible() {
	    System.out.println(" Ce joueur n'est pas disponible. Réessayez.");
	}

	public void afficherDemandeTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible) {
	    System.out.println("\n Chez le joueur " + numeroJoueurCible + ", quelle carte voulez-vous prendre ?");
	    if (carteVisibleNom != null) {
	        System.out.println("  1. Carte visible : " + carteVisibleNom);
	    }
	    if (carteCacheeDisponible) {
	        System.out.println("  2. Carte cachée");
	    }
	    System.out.print("Votre choix : ");
	}

	public void afficherDemandeConfirmation(String message) {
	    System.out.print(message + " (O/N) : ");
	}

	public void nettoyerConsole() {
	    for (int i = 0; i < 50; i++) {
	        System.out.println();
	    }
	}

	public void afficherOptionPrise(int numero, String description) {
	    System.out.println("  " + numero + ". " + description);
	}

}