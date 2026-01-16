package jestPackage.Modele;

import java.io.Serializable;

/**
 * Représente la carte spéciale "Joker".
 * <p>
 * Le Joker est une carte non colorée (pas de {@link Couleur}) et n'a pas de valeur de base
 * utilisée comme les autres cartes (retourne 0).
 * </p>
 * <p>
 * Cette classe participe également au patron Visiteur via {@link #accept(VisiteurDeCarte)}.
 * </p>
 */
public class Joker extends Carte implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Nom affiché de la carte.
	 */
	private String nom = "Joker";

	/**
	 * Construit un Joker.
	 * <p>
	 * Le paramètre {@code nom} n'est pas utilisé : le Joker est toujours construit avec
	 * le nom "Joker" (tel qu'implémenté par l'appel à {@code super("Joker", ...)}).
	 * </p>
	 *
	 * @param nom paramètre présent dans la signature (non utilisé)
	 * @param bandeauTrophee bandeau trophée associé à la carte
	 */
	public Joker(String nom, Trophee bandeauTrophee) {
		super("Joker", bandeauTrophee);
	}

	/**
	 * Représentation textuelle du Joker.
	 *
	 * @return une représentation au format {@code Joker[nom=Joker]}
	 */
	public String toString() {
		return "Joker[nom=" + nom + "]";
	}

	/**
	 * Retourne la valeur de base du Joker.
	 *
	 * @return 0 (valeur de base du Joker)
	 */
	public int getValeurBase() {
		return 0;
	}

	/**
	 * Retourne la couleur du Joker.
	 *
	 * @return {@code null} car le Joker n'a pas de couleur
	 */
	public Couleur getCouleur() {
		return null;
	}

	/**
	 * Retourne la valeur de couleur du Joker.
	 *
	 * @return 0 (aucune valeur de couleur)
	 */
	public int getValeurCouleur() {
		return 0;
	}

	/**
	 * Indique si la carte est une carte de couleur.
	 *
	 * @return {@code false} car le Joker est incolore
	 */
	public boolean estCouleur() {
		return false;
	}

	/**
	 * Point d'entrée du patron Visiteur.
	 *
	 * @param visiteur visiteur appliqué au Joker
	 * @return le résultat de {@link VisiteurDeCarte#visit(Joker)}
	 */
	public int accept(VisiteurDeCarte visiteur) {
		return visiteur.visit(this);
	}
}