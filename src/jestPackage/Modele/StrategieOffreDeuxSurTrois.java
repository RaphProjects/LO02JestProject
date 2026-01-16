package jestPackage.Modele;

import java.util.*;
import java.util.Random;
import java.io.Serializable;

/**
 * Stratégie d'offre IA "Deux sur trois".
 * <p>
 * Cette stratégie choisit, parmi les deux cartes de la main, une carte à mettre visible :
 * </p>
 * <ul>
 *   <li>2 chances sur 3 : la carte de plus faible valeur de base est placée visible</li>
 *   <li>1 chance sur 3 : la carte de plus forte valeur de base est placée visible</li>
 * </ul>
 * <p>
 * La carte non visible devient la carte cachée de l'offre.
 * </p>
 */
public class StrategieOffreDeuxSurTrois implements StrategieOffre, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Choisit une offre à partir de la main.
	 * <p>
	 * Hypothèse implicite de l'implémentation : {@code main} contient au moins 2 cartes.
	 * </p>
	 *
	 * @param main main du joueur (liste de cartes disponibles)
	 * @return une {@link Offre} contenant une carte visible et une carte cachée
	 */
	public Offre choisirOffre(ArrayList<Carte> main) {
		// Trouver la carte la plus faible et la plus forte
		Carte carteFaible = main.get(0);
		Carte carteForte = main.get(1);

		if (carteFaible.getValeurBase() > carteForte.getValeurBase()) {
			// Échanger les références
			Carte temp = carteFaible;
			carteFaible = carteForte;
			carteForte = temp;
		}

		// 2 chances sur 3 de donner la carte faible en visible
		Random random = new Random();
		int tirage = random.nextInt(3); // Génère 0, 1 ou 2

		if (tirage < 2) {
			// 2/3 des cas (tirage = 0 ou 1) : carte faible visible

			Jeu.vue.afficherCarteChoisieIA(carteFaible.toString());
			return new Offre(carteFaible, carteForte);
		} else {
			// 1/3 des cas (tirage = 2) : carte forte visible
			Jeu.vue.afficherCarteChoisieIA(carteForte.toString());
			return new Offre(carteForte, carteFaible);
		}
	}
}