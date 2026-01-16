package jestPackage.Modele;

/**
 * Interface du patron Visiteur appliqué aux cartes.
 * <p>
 * Permet de définir des traitements spécifiques selon le type concret de carte
 * (double-dispatch).
 * </p>
 */
public interface VisiteurDeCarte {

	/**
	 * Visite une carte de couleur.
	 *
	 * @param carte carte de couleur visitée
	 * @return un résultat dépendant de l'implémentation du visiteur
	 */
	int visit(CarteCouleur carte);

	/**
	 * Visite un {@link Joker}.
	 *
	 * @param joker joker visité
	 * @return un résultat dépendant de l'implémentation du visiteur
	 */
	int visit(Joker joker);
}