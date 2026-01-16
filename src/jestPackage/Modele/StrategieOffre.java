package jestPackage.Modele;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Définit une stratégie de création d'offre à partir d'une main de cartes.
 * <p>
 * Une implémentation de cette interface contient la logique permettant de sélectionner
 * quelles cartes d'une main seront placées dans une {@link Offre} (carte visible/cachée),
 * conformément aux règles ou à l'IA.
 * </p>
 */
public interface StrategieOffre {
	/**
	 * Choisit et construit une offre à partir de la main fournie.
	 *
	 * @param main la main de cartes à partir de laquelle construire l'offre
	 * @return l'offre choisie
	 */
	public Offre choisirOffre(ArrayList<Carte> main);
}