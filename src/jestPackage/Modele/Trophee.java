package jestPackage.Modele;

import java.io.Serializable;

/**
 * Représente un trophée attachable à une {@link Carte}.
 * <p>
 * Les trophées peuvent être de différents types (par couleur ou incolores) et influencent
 * généralement le scoring ou l'attribution de bonus selon les règles du jeu.
 * </p>
 */
public abstract class Trophee implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Indique si ce trophée est un trophée lié à une couleur.
	 *
	 * @return {@code true} si le trophée est un trophée de couleur, sinon {@code false}
	 */
	public abstract boolean estTropheeCouleur();

	/**
	 * Représentation textuelle du trophée.
	 *
	 * @return une chaîne décrivant le trophée
	 */
	public abstract String toString();
}