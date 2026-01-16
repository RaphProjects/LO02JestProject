package jestPackage.Modele;

/**
 * Définit l'ordre d'évaluation d'un trophée lié à une couleur.
 * <p>
 * Utilisé pour indiquer si le trophée concerne la valeur la plus grande ou la plus petite
 * (selon les règles associées dans le jeu).
 * </p>
 */
public enum OrdreTropheeCouleur {
	/** Le trophée s'applique au plus grand critère/valeur. */
	PLUSGRAND,
	/** Le trophée s'applique au plus petit critère/valeur. */
	PLUSPETIT

}