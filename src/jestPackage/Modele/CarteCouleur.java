package jestPackage.Modele;

import java.io.Serializable;

/**
 * Représente une carte "classique" (non-joker) possédant une {@link Couleur} et une valeur de base.
 * <p>
 * Cette classe étend {@link Carte} et participe au patron Visiteur via {@link #accept(VisiteurDeCarte)}.
 * </p>
 */
public class CarteCouleur extends Carte implements Serializable {
	/** Identifiant de sérialisation. */
	private static final long serialVersionUID = 1L;

	private Couleur couleur;
	private int valeurBase;

	/**
	 * Construit une carte de couleur.
	 *
	 * @param nom le nom de la carte
	 * @param bandeauTrophee le trophée/bandeau associé à la carte
	 * @param couleur la couleur de la carte
	 * @param valeurBase la valeur de base (face value) de la carte
	 */
	public CarteCouleur(String nom, Trophee bandeauTrophee, Couleur couleur, int valeurBase) {
		super(nom, bandeauTrophee);
		this.couleur = couleur;
		this.valeurBase = valeurBase;
	}

	/**
	 * Renvoie la couleur de la carte.
	 *
	 * @return la couleur
	 */
	public Couleur getCouleur() {
		return couleur;
	}

	/**
	 * Renvoie la valeur de base (face value) de la carte.
	 *
	 * @return la valeur de base
	 */
	public int getValeurBase() {
		return valeurBase;
	}

	/**
	 * Représentation textuelle de la carte.
	 *
	 * @return une chaîne décrivant la carte
	 */
	public String toString() {
		return "CarteCouleur[nom=" + nom + ", couleur=" + couleur + ", valeurBase=" + valeurBase + "]";
	}

	/**
	 * Renvoie une valeur numérique associée à la couleur, utilisée pour départager des égalités.
	 * <p>
	 * Convention utilisée :
	 * PIQUE = 4, TREFLE = 3, CARREAU = 2, COEUR = 1.
	 * </p>
	 *
	 * @return la valeur associée à la couleur
	 */
	public int getValeurCouleur() {
		switch (couleur) {
		case PIQUE:
			return 4;
		case TREFLE:
			return 3;
		case CARREAU:
			return 2;
		case COEUR:
			return 1;
		default:
			return 0;
		}
	}

	/**
	 * Indique qu'il s'agit bien d'une carte de couleur (et non d'un joker).
	 *
	 * @return {@code true}
	 */
	public boolean estCouleur() {
		return true;
	}

	/**
	 * Accepte un visiteur et lui délègue le traitement de cette carte.
	 *
	 * @param visiteur le visiteur de cartes
	 * @return la valeur calculée par le visiteur
	 */
	public int accept(VisiteurDeCarte visiteur) {
		return visiteur.visit(this);
	}

}