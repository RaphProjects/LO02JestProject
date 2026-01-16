package jestPackage.Modele;

import java.io.Serializable;

/**
 * Représente un trophée incolore.
 * <p>
 * Un trophée incolore est défini par une {@link ConditionIncolore}. Certains trophées
 * (ex. majorité) nécessitent une valeur associée.
 * </p>
 */
public class TropheeIncolore extends Trophee implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Condition incolore du trophée.
	 */
	private ConditionIncolore condition;

	/**
	 * Valeur associée (utile uniquement pour le trophée "majorité").
	 * <p>
	 * La valeur {@code -1} signifie qu'aucune valeur n'est associée.
	 * </p>
	 */
	private int valeurAssociée = -1; // utile uniquement pour le trophée majorité

	/**
	 * Construit un trophée incolore sans valeur associée.
	 *
	 * @param condition condition du trophée
	 */
	public TropheeIncolore(ConditionIncolore condition) {
		this.condition = condition;
	}

	/**
	 * Construit un trophée incolore avec une valeur associée (ex. majorité).
	 *
	 * @param condition condition du trophée
	 * @param valeurAssociée valeur associée à la condition
	 */
	public TropheeIncolore(ConditionIncolore condition, int valeurAssociée) {
		this.condition = condition;
		this.valeurAssociée = valeurAssociée;
	}

	/**
	 * Indique qu'il ne s'agit pas d'un trophée de couleur.
	 *
	 * @return {@code false}
	 */
	@Override
	public boolean estTropheeCouleur() {
		return false;
	}

	/**
	 * Retourne la condition incolore du trophée.
	 *
	 * @return la condition
	 */
	public ConditionIncolore getCondition() {
		return condition;
	}

	/**
	 * Retourne la valeur associée au trophée.
	 *
	 * @return la valeur associée (ou -1 si aucune)
	 */
	public int getValeurAssociée() {
		return valeurAssociée;
	}

	/**
	 * Représentation textuelle du trophée.
	 *
	 * @return une chaîne descriptive
	 */
	@Override
	public String toString() {
		if (valeurAssociée == -1) {
			return "TropheeIncolore[condition=" + condition + "]";
		}
		return "TropheeIncolore[condition=" + condition + ", valeurAssociée=" + valeurAssociée + "]";
	}

}