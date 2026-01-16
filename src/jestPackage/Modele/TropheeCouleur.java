package jestPackage.Modele;

import java.io.Serializable;

/**
 * Représente un trophée de couleur.
 * <p>
 * Un trophée de couleur impose une condition liée à une {@link Couleur} et à un
 * {@link OrdreTropheeCouleur} (ex. plus grande / plus petite carte de la couleur).
 * </p>
 */
public class TropheeCouleur extends Trophee implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Ordre à appliquer pour déterminer le gagnant du trophée (plus grand / plus petit).
	 */
	private OrdreTropheeCouleur ordre;

	/**
	 * Couleur sur laquelle porte la condition du trophée.
	 */
	private Couleur couleurDeCondition;

	/**
	 * Construit un trophée de couleur.
	 *
	 * @param ordre ordre (plus grand / plus petit) à appliquer
	 * @param couleurDeCondition couleur de condition
	 */
	public TropheeCouleur(OrdreTropheeCouleur ordre, Couleur couleurDeCondition) {
		this.ordre = ordre;
		this.couleurDeCondition = couleurDeCondition;
	}

	/**
	 * Indique qu'il s'agit d'un trophée de couleur.
	 *
	 * @return {@code true}
	 */
	@Override
	public boolean estTropheeCouleur() {
		return true;
	}

	/**
	 * Retourne l'ordre associé au trophée.
	 *
	 * @return l'ordre
	 */
	public OrdreTropheeCouleur getOrdre() {
		return ordre;
	}

	/**
	 * Retourne la couleur de condition.
	 *
	 * @return la couleur de condition
	 */
	public Couleur getCouleurDeCondition() {
		return couleurDeCondition;
	}

	/**
	 * Représentation textuelle du trophée.
	 *
	 * @return une chaîne descriptive
	 */
	@Override
	public String toString() {
		return "TropheeCouleur[ordre=" + ordre + ", couleurDeCondition=" + couleurDeCondition + "]";
	}
}