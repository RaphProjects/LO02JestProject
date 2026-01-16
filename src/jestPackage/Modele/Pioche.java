package jestPackage.Modele;

import java.util.*;
import java.io.Serializable;

/**
 * Représente la pioche du jeu (le paquet de cartes à distribuer).
 * <p>
 * Cette classe est responsable de :
 * <ul>
 *   <li>l'initialisation des cartes en fonction de l'extension choisie ;</li>
 *   <li>le mélange des cartes ;</li>
 *   <li>la vérification qu'il reste suffisamment de cartes pour un tour ;</li>
 *   <li>le tirage (piochage) d'une carte.</li>
 * </ul>
 *
 */
public class Pioche implements Serializable {
	private static final long serialVersionUID = 1L;

	private ArrayList<Carte> cartes;
	private Jeu jeu;

	/**
	 * Construit une pioche associée à une instance de {@link Jeu}.
	 *
	 * @param jeu l'instance de jeu à laquelle la pioche est rattachée
	 */
	public Pioche(Jeu jeu) {
		this.cartes = new ArrayList<Carte>();
		this.jeu = jeu;
	}

	/**
	 * Initialise la liste de cartes de la pioche en fonction de l'extension du jeu.
	 * <p>
	 * Cette méthode ajoute manuellement les cartes (et leurs trophées) car la logique d'association
	 * des trophées n'est pas généralisée dans le code.
	 * </p>
	 * <ul>
	 *   <li>Extension = 0 : jeu de base</li>
	 *   <li>Extension = 1 : variante étendue (ajout des cartes de valeur 5)</li>
	 * </ul>
	 * <p>
	 * À la fin, les cartes sont mélangées via {@link #melangerCartes()}.
	 * </p>
	 */
	public void initialiserCartes() {
		if (this.jeu.getExtension() == 0) {
			//Tres long : il faut initialiser chaque carte manuellement car il n'y a pas de logique dans les trophées
			// Le joker
			this.cartes.add(new Joker("Joker", new TropheeIncolore(ConditionIncolore.PLUSGRANDJEST)));
			// As de coeur
			this.cartes.add(new CarteCouleur("As de coeur", new TropheeIncolore(ConditionIncolore.JOKER), Couleur.COEUR, 1));
			// 2 de coeur
			this.cartes.add(new CarteCouleur("2 de coeur", new TropheeIncolore(ConditionIncolore.JOKER), Couleur.COEUR, 2));
			// 3 de coeur
			this.cartes.add(new CarteCouleur("3 de coeur", new TropheeIncolore(ConditionIncolore.JOKER), Couleur.COEUR, 3));
			// 4 de coeur
			this.cartes.add(new CarteCouleur("4 de coeur", new TropheeIncolore(ConditionIncolore.JOKER), Couleur.COEUR, 4));
			// As de carreau
			this.cartes.add(new CarteCouleur("As de carreau", new TropheeIncolore(ConditionIncolore.MAJORITÉ, 4), Couleur.CARREAU, 1));
			// 2 de carreau
			this.cartes.add(new CarteCouleur("2 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND, Couleur.CARREAU), Couleur.CARREAU, 2));
			// 3 de carreau
			this.cartes.add(new CarteCouleur("3 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.CARREAU), Couleur.CARREAU, 3));
			// 4 de carreau
			this.cartes.add(new CarteCouleur("4 de carreau", new TropheeIncolore(ConditionIncolore.PLUSGRANDJEST_SANSJOKER), Couleur.CARREAU, 4));
			// As de trefle
			this.cartes.add(new CarteCouleur("As de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND, Couleur.PIQUE), Couleur.TREFLE, 1));
			// 2 de trefle
			this.cartes.add(new CarteCouleur("2 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.COEUR), Couleur.TREFLE, 2));
			// 3 de trefle
			this.cartes.add(new CarteCouleur("3 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND, Couleur.COEUR), Couleur.TREFLE, 3));
			// 4 de trefle
			this.cartes.add(new CarteCouleur("4 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.PIQUE), Couleur.TREFLE, 4));
			// As de pique
			this.cartes.add(new CarteCouleur("As de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND, Couleur.TREFLE), Couleur.PIQUE, 1));
			// 2 de pique
			this.cartes.add(new CarteCouleur("2 de pique", new TropheeIncolore(ConditionIncolore.MAJORITÉ, 3), Couleur.PIQUE, 2));
			// 3 de pique
			this.cartes.add(new CarteCouleur("3 de pique", new TropheeIncolore(ConditionIncolore.MAJORITÉ, 2), Couleur.PIQUE, 3));
			// 4 de pique
			this.cartes.add(new CarteCouleur("4 de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.TREFLE), Couleur.PIQUE, 4));
		}

		else if (this.jeu.getExtension() == 1) {
			// Le joker
			this.cartes.add(new Joker("Joker", new TropheeIncolore(ConditionIncolore.PLUSGRANDJEST)));
			// As de coeur
			this.cartes.add(new CarteCouleur("As de coeur", new TropheeIncolore(ConditionIncolore.JOKER), Couleur.COEUR, 1));
			// 2 de coeur
			this.cartes.add(new CarteCouleur("2 de coeur", new TropheeIncolore(ConditionIncolore.JOKER), Couleur.COEUR, 2));
			// 3 de coeur
			this.cartes.add(new CarteCouleur("3 de coeur", new TropheeIncolore(ConditionIncolore.JOKER), Couleur.COEUR, 3));
			// 4 de coeur
			this.cartes.add(new CarteCouleur("4 de coeur", new TropheeIncolore(ConditionIncolore.JOKER), Couleur.COEUR, 4));
			// As de carreau
			this.cartes.add(new CarteCouleur("As de carreau", new TropheeIncolore(ConditionIncolore.MAJORITÉ, 4), Couleur.CARREAU, 1));
			// 2 de carreau
			this.cartes.add(new CarteCouleur("2 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND, Couleur.CARREAU), Couleur.CARREAU, 2));
			// 3 de carreau
			this.cartes.add(new CarteCouleur("3 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.CARREAU), Couleur.CARREAU, 3));
			// 4 de carreau
			this.cartes.add(new CarteCouleur("4 de carreau", new TropheeIncolore(ConditionIncolore.PLUSGRANDJEST_SANSJOKER), Couleur.CARREAU, 4));
			// As de trefle
			this.cartes.add(new CarteCouleur("As de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND, Couleur.PIQUE), Couleur.TREFLE, 1));
			// 2 de trefle
			this.cartes.add(new CarteCouleur("2 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.COEUR), Couleur.TREFLE, 2));
			// 3 de trefle
			this.cartes.add(new CarteCouleur("3 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND, Couleur.COEUR), Couleur.TREFLE, 3));
			// 4 de trefle
			this.cartes.add(new CarteCouleur("4 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.PIQUE), Couleur.TREFLE, 4));
			// As de pique
			this.cartes.add(new CarteCouleur("As de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND, Couleur.TREFLE), Couleur.PIQUE, 1));
			// 2 de pique
			this.cartes.add(new CarteCouleur("2 de pique", new TropheeIncolore(ConditionIncolore.MAJORITÉ, 3), Couleur.PIQUE, 2));
			// 3 de pique
			this.cartes.add(new CarteCouleur("3 de pique", new TropheeIncolore(ConditionIncolore.MAJORITÉ, 2), Couleur.PIQUE, 3));
			// 4 de pique
			this.cartes.add(new CarteCouleur("4 de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.TREFLE), Couleur.PIQUE, 4));

			// Cartes de la variante étendue

			// 5 de coeur
			this.cartes.add(new CarteCouleur("5 de coeur", new TropheeIncolore(ConditionIncolore.JOKER), Couleur.COEUR, 5));
			// 5 de carreau
			this.cartes.add(new CarteCouleur("5 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND, Couleur.CARREAU), Couleur.CARREAU, 5));
			// 5 de trefle
			this.cartes.add(new CarteCouleur("5 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.PIQUE), Couleur.TREFLE, 5));
			// 5 de pique
			this.cartes.add(new CarteCouleur("5 de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT, Couleur.TREFLE), Couleur.PIQUE, 5));
		}

		this.melangerCartes();
	}

	/**
	 * Mélange aléatoirement les cartes de la pioche.
	 */
	public void melangerCartes() {
		Collections.shuffle(this.cartes);
	}

	/**
	 * Indique si la pioche contient suffisamment de cartes pour être utilisée lors du prochain tour.
	 * <p>
	 * Règle appliquée : il doit rester au moins une carte par joueur, car à chaque tour on récupère
	 * une carte par joueur (cartes non jouées au tour précédent). Il faut donc contenir au moins une
	 * carte par joueur pour que chaque joueur ait deux cartes au total après distribution.
	 * </p>
	 *
	 * @return {@code true} si la pioche contient assez de cartes, sinon {@code false}
	 */
	public boolean estPiochable() {
		return this.cartes.size() >= (this.jeu.getJoueurs().size()); // A chaque tour on recupere une carte par joueur (cartes non jouéees au tour precedent)
		// Il faut donc contenir au moins une carte par joueur pour que chaque joueur ait deux cartes au total apres distribution
	}

	/**
	 * Pioche (retire) la première carte de la pioche.
	 * <p>
	 * Si la pioche est vide, un message est affiché via la vue et {@code null} est renvoyé.
	 * </p>
	 *
	 * @return la carte piochée, ou {@code null} si la pioche est vide
	 */
	public Carte piocher() {
		if (this.cartes.isEmpty()) {
			Jeu.vue.afficherPiocheVide();

			return null;
		}
		return this.cartes.remove(0);
	}
}