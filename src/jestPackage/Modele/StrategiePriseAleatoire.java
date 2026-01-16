package jestPackage.Modele;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Random;

/**
 * Stratégie de prise IA aléatoire.
 * <p>
 * Cette stratégie :
 * </p>
 * <ul>
 *   <li>pour une prise chez les adversaires : choisit aléatoirement une carte parmi toutes les cartes
 *       disponibles dans les offres adverses (2 cartes par offre), en renvoyant un choix 1-based ;</li>
 *   <li>pour une prise sur sa propre offre : compare la carte visible et la carte cachée et choisit
 *       la plus grande (1 si visible, 2 sinon).</li>
 * </ul>
 */
public class StrategiePriseAleatoire implements StrategiePrise, Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Choisit aléatoirement une prise parmi les offres adverses.
	 * <p>
	 * Le résultat est un entier compris entre 1 et {@code offresAdverse.size() * 2} (inclus),
	 * car chaque offre contient deux cartes.
	 * </p>
	 *
	 * @param jest Jest courant (non utilisé par cette stratégie)
	 * @param offresAdverse liste des offres adverses
	 * @return un choix 1-based correspondant à une carte parmi toutes les offres
	 */
	public int choisirPriseAdverse(Jest jest, ArrayList<Offre> offresAdverse) {
		// On a deux cartes par offre, le nombre à retourner va de 1 à offreAdverse.size*2
		Random rand = new Random();
		return rand.nextInt(offresAdverse.size() * 2) + 1;
	}

	/**
	 * Choisit une prise sur sa propre offre.
	 * <p>
	 * Comme le joueur connaît la carte cachée de sa propre offre, cette stratégie choisit
	 * simplement la carte de plus grande valeur de base :
	 * </p>
	 * <ul>
	 *   <li>renvoie 1 si la carte visible est strictement supérieure à la carte cachée ;</li>
	 *   <li>renvoie 2 sinon.</li>
	 * </ul>
	 *
	 * @param jestDeSoi Jest du joueur (non utilisé par cette stratégie)
	 * @param offreDeSoi offre du joueur (visible + cachée)
	 * @return 1 si la carte visible est la meilleure, 2 sinon
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