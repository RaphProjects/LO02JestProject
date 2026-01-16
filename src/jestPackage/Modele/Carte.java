package jestPackage.Modele;

import java.io.Serializable;

/**
 * Représente une carte du jeu.
 * <p>
 * Cette classe est la base commune aux différents types de cartes (cartes de couleur, joker, etc.).
 * Elle implémente {@link CarteElement} afin de pouvoir être visitée via le patron Visiteur, et
 * {@link Serializable} pour permettre la sérialisation.
 * </p>
 */
public abstract class Carte implements CarteElement, Serializable {
	/** Identifiant de sérialisation. */
	private static final long serialVersionUID = 1L;

	/** Nom de la carte (ex. "Joker", "As", etc.). */
	protected String nom;

	/** Trophée associé à la carte (bandeau). */
	protected Trophee bandeauTrophee;

	/**
	 * Construit une carte.
	 *
	 * @param nom le nom de la carte
	 * @param bandeauTrophee le trophée/bandeau associé à la carte (peut être {@code null} selon les règles du jeu)
	 */
	public Carte(String nom, Trophee bandeauTrophee) {
		this.nom = nom;
		this.bandeauTrophee = bandeauTrophee;
	}

	/**
	 * Renvoie le nom de la carte.
	 *
	 * @return le nom de la carte
	 */
	public String getNom() {
		return nom;
	}

	/**
	 * Renvoie le trophée (bandeau) associé à la carte.
	 *
	 * @return le trophée associé à la carte
	 */
	public Trophee getBandeauTrophee() {
		return bandeauTrophee;
	}

	/**
	 * Renvoie la valeur de base de la carte (face value).
	 *
	 * @return la valeur de base
	 */
	public abstract int getValeurBase(); // correspond à la face value de l'énoncé

	/**
	 * Renvoie la couleur (signe) de la carte.
	 *
	 * @return la couleur de la carte
	 */
	public abstract Couleur getCouleur();

	/**
	 * Renvoie une valeur associée à la couleur, utilisée notamment pour départager des égalités.
	 *
	 * @return la valeur associée à la couleur
	 */
	public abstract int getValeurCouleur(); // utilisé pour gérer les égalités

	/**
	 * Indique si cette carte est une carte de couleur (par opposition à un joker, par exemple).
	 *
	 * @return {@code true} si la carte est une carte de couleur, sinon {@code false}
	 */
	public abstract boolean estCouleur();

}