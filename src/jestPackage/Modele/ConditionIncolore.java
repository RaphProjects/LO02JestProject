package jestPackage.Modele;

/**
 * Énumération des conditions applicables aux trophées/effets "incolores" (non liés à une couleur).
 * <p>
 * Ces conditions décrivent des critères globaux (ex. présence du joker, plus grand jest, majorité)
 * pouvant influencer l'attribution d'un trophée ou d'un bonus/malus, selon les règles du jeu.
 * </p>
 */
public enum ConditionIncolore {
	/** Condition liée au joker. */
	JOKER,
	/** Condition : possède le plus grand jest. */
	PLUSGRANDJEST,
	/** Condition : possède le plus grand jest sans joker. */
	PLUSGRANDJEST_SANSJOKER,
	/** Condition : possède la majorité (selon la règle associée). */
	MAJORITÉ
}