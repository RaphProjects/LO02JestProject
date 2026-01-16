package jestPackage.Modele;

import java.util.ArrayList;
import java.io.Serializable;

/**
 * Définit la stratégie de prise (sélection d'une offre) pour un joueur, notamment un joueur virtuel.
 * <p>
 * Une implémentation de cette interface fournit la logique de décision pour :
 * <ul>
 *   <li>choisir une prise parmi les offres adverses ;</li>
 *   <li>choisir une prise lorsque l'on interagit avec sa propre offre.</li>
 * </ul>
 * 
 */
public interface StrategiePrise {
	/**
	 * Choisit une prise parmi les offres adverses disponibles.
	 *
	 * @param jest le jest courant du joueur effectuant le choix
	 * @param offresAdverse les offres adverses disponibles
	 * @return un entier représentant le choix effectué (selon la convention utilisée par le jeu)
	 */
	public int choisirPriseAdverse(Jest jest, ArrayList<Offre> offresAdverse);

	/**
	 * Choisit une prise concernant sa propre offre.
	 *
	 * @param jestDeSoi le jest du joueur effectuant le choix
	 * @param offreDeSoi l'offre du joueur
	 * @return un entier représentant le choix effectué (selon la convention utilisée par le jeu)
	 */
	public int choisirPriseDeSoi(Jest jestDeSoi, Offre offreDeSoi);
}