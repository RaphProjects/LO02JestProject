package jestPackage.Modele;

import java.util.*;
import java.io.Serializable;

/**
 * Stratégie de prise IA "prudente".
 * <p>
 * Cette stratégie privilégie l'information certaine :
 * </p>
 * <ul>
 *   <li>pour une prise chez les adversaires : choisit la carte visible de plus forte valeur de base
 *       parmi toutes les offres adverses ;</li>
 *   <li>pour une prise sur sa propre offre : compare visible/cachée (connue) et choisit la plus forte.</li>
 * </ul>
 * <p>
 * Remarque : l'implémentation actuelle ne gère pas le départage en cas d'égalité sur la valeur
 * (voir commentaire dans le code).
 * </p>
 */
public class StrategiePrisePrudente implements StrategiePrise, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Choisit une carte chez un adversaire en sélectionnant la carte visible de plus forte valeur.
	 * <p>
	 * Le choix retourné est 1-based et correspond à l'index de carte dans une liste linéarisée
	 * des cartes des offres (2 cartes par offre). La carte visible d'une offre est toujours
	 * en position {@code i * 2 + 1}.
	 * </p>
	 *
	 * @param jest Jest courant (non utilisé par cette stratégie)
	 * @param offresAdverse liste des offres adverses
	 * @return le numéro (1-based) correspondant à la carte visible choisie, ou -1 si aucune n'est trouvée
	 */
	public int choisirPriseAdverse(Jest jest, ArrayList<Offre> offresAdverse) {
		// On a deux cartes par offre, le nombre à retourner va de 1 à offreAdverse.size*2
		// On renvoie le numéro de la carte visible avec la plus haute valeur (qui sera donc = 1 modulo 2
		int valeurMax = -1;
		for (Offre offreCourante : offresAdverse) {
			if (offreCourante.getCarteVisible().getValeurBase() > valeurMax) {
				valeurMax = offreCourante.getCarteVisible().getValeurBase();
			}
		}
		// On cherche l'offre qui contient cette carte

		// TEMPORAIRE : il faudrait gérer les égalités pour prendre la meilleure carte de couleur en cas d'égalité, pas juste la premiere
		for (int i = 0; i < offresAdverse.size(); i++) {
			Offre offreCourante = offresAdverse.get(i);
			if (offreCourante.getCarteVisible().getValeurBase() == valeurMax) {
				// On renvoie le numéro de la carte visible
				return i * 2 + 1;
			}
		}
		// On ne devrait jamais arriver ici
		return -1;

	}

	/**
	 * Choisit une carte sur sa propre offre en prenant la carte de plus forte valeur de base.
	 *
	 * @param jestDeSoi Jest du joueur (non utilisé par cette stratégie)
	 * @param offreDeSoi offre du joueur (visible + cachée)
	 * @return 1 si la carte visible est la plus forte, 2 sinon
	 */
	@Override
	public int choisirPriseDeSoi(Jest jestDeSoi, Offre offreDeSoi) {
		// On utilise pas le jest dans cette stratégie
		// on renvoie simplement 1 si la carte visible est plus grande et 2 sinon
		// On a acces à la carte cachée car c'est notre propre offre
		if (offreDeSoi.getCarteVisible().getValeurBase() > offreDeSoi.getCarteCachee().getValeurBase()) {
			return 1;
		}
		return 2;

	}

}