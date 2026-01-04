package jestPackage.Vue;

import java.util.ArrayList;

import jestPackage.Modele.Carte;
import jestPackage.Modele.Couleur;

public interface IVue {

	void afficherInfosTrophee(Carte carte);

	void afficherAuRevoir();

	void afficherBienvenue();

	void afficherCarteChoisieIA(String string);

	void afficherChoixInvalide();

	void afficherCarteChoisiePourOffre(String string);

	void afficherCartesJest(ArrayList<Carte> cartes);

	void afficherCestAuiDeJouer();

	void afficherChargementReussi(String cheminComplet);

	void afficherChoixCarteJoueur(String nom, int numCarteChoisie);

	void afficherChoixCarteJoueurVirtuel(String nom, int choix);

	void afficherChoixCarteJoueurVirtuelAdverse(String nom, int numCarteChoisie);

	void afficherChoixDansPropreOffre();

	void afficherClassementFinal();

	void afficherCreationJoueurReel(int compteurJoueur);

	void afficherCreationJoueurVirtuel(int compteurJoueur);

	void afficherDebutTour(int numeroTour);

	void afficherEgaliteParfaiteTrophee(String nom);


	void afficherEgaliteTrophee(String nom);

	void afficherErreurDeterminerJoueurPlusGrandeValeurVisible();

	void afficherErreurNombreCartesNonJouees();

	void afficherErreurSauvegarde(String message);

	void afficherErreurSuppression(String nomFichier);

	void afficherErreurTousJoueursOntJoue();

	void afficherExtensionChoisie(int extensionChoisie);

	void afficherFinJeu();

	void afficherJestFinalJoueur(int numJoueur);

	void afficherJoueurAvecPlusGrandeValeurVisible(String nom, String string);

	void afficherJoueurSeFaitPrendreCarte(String nom);

	void afficherMainJoueur(String nom, String string);

	void afficherNumeroTour(int numeroTour);

	void afficherOffreDeJoueur(String nom);

	void afficherOffreJoueur(String nom, String carteVisible, String carteCachee);

	void afficherOffresDesJoueurs();

	void afficherOptionCarteCachee();

	void afficherOptionCarteCacheeOffre(int idxCache);

	void afficherOptionCarteVisible(String nom);

	void afficherOptionCarteVisibleOffre(int idxVisible, String nom);

	void afficherPiocheVide();

	void afficherPlusGrandeCarteCouleur(int numJoueur, Couleur couleurDeCondition, int maxValeur);

	void afficherPlusPetiteCarteCouleur(int numJoueur, Couleur couleurDeCondition, int minValeur);

	void afficherSauvegardeReussie(String cheminComplet);

	void afficherScoreJoueur(int i, int numJoueur, int scoreFinal);

	void afficherStrategieDefaut();

	void afficherSuppressionReussie(String nomFichier);

	void afficherTourJoueur(String nom);

	void afficherTropheeNonAttribue(String string);

	void afficherTropheeRemporte(String nom, int numJoueur);

	void afficherVarianteChoisie(int varianteChoisie);

	void afficherVarianteInversee();

	void annonceTrophees();



}
