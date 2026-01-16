package jestPackage.Modele;

import java.io.Serializable;

/**
 * Élément visitable du modèle de cartes, utilisé dans le cadre du patron Visiteur.
 * <p>
 * Les classes représentant des cartes (ex. {@link CarteCouleur}, {@code Joker}, etc.) implémentent
 * cette interface afin de permettre à un {@link VisiteurDeCarte} d'exécuter un traitement spécifique
 * en fonction du type concret de carte.
 * </p>
 */
public interface CarteElement {
	/**
	 * Accepte un visiteur et lui délègue le traitement de l'élément courant.
	 *
	 * @param visiteur le visiteur de cartes
	 * @return la valeur calculée par le visiteur
	 */
	int accept(VisiteurDeCarte visiteur);

}