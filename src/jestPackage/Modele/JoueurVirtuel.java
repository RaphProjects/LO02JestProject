package jestPackage.Modele;

import java.util.*;
import java.io.Serializable;

/**
 * Représente un joueur virtuel (IA).
 * <p>
 * Le joueur virtuel délègue ses décisions de composition d'offre et de prise à des stratégies
 * ({@link StrategieOffre} et {@link StrategiePrise}).
 * </p>
 */
public class JoueurVirtuel extends Joueur implements Serializable {
	private static final long serialVersionUID = 1L;

	private StrategieOffre strategyOffre;
	private StrategiePrise strategyPrise;

	/**
	 * Construit un joueur virtuel.
	 *
	 * @param nom le nom du joueur
	 * @param numJoueur le numéro du joueur
	 * @param numStrategy identifiant de stratégie de prise :
	 *        <ul>
	 *          <li>1 : {@link StrategiePrisePrudente}</li>
	 *          <li>2 : {@link StrategiePriseAleatoire}</li>
	 *          <li>autre : stratégie par défaut (prudente) avec message via la vue</li>
	 *        </ul>
	 */
	public JoueurVirtuel(String nom, int numJoueur, int numStrategy) {
		this.nom = nom;
		this.numJoueur = numJoueur;
		this.strategyOffre = new StrategieOffreDeuxSurTrois();
		if (numStrategy == 1)
			this.strategyPrise = new StrategiePrisePrudente();
		else if (numStrategy == 2)
			this.strategyPrise = new StrategiePriseAleatoire();
		else {
			Jeu.vue.afficherStrategieDefaut();

			this.strategyPrise = new StrategiePrisePrudente();
		}

	}

	/**
	 * Indique qu'il s'agit d'un joueur virtuel.
	 *
	 * @return {@code true}
	 */
	public boolean isVirtuel() {
		return true;
	}

	/**
	 * Renvoie l'offre actuellement associée au joueur.
	 *
	 * @return l'offre du joueur
	 */
	public Offre getOffre() {
		return this.offre;
	}

	/**
	 * Définit l'offre du joueur.
	 *
	 * @param offreChoisie l'offre choisie
	 */
	public void setOffre(Offre offreChoisie) {
		this.offre = offreChoisie;
	}

	/**
	 * Décide l'offre à proposer en utilisant la stratégie d'offre, puis retire les cartes de la main.
	 */
	public void deciderOffre() {
		this.setOffre(this.strategyOffre.choisirOffre(this.main));

		// On enlève les cartes de la main du joueur
		this.main.clear();
	}

	/**
	 * Choisit une prise parmi un certain nombre de possibilités.
	 * <p>
	 * Méthode indiquée comme temporaire dans le code.
	 * </p>
	 *
	 * @param nbpossibilite le nombre de possibilités
	 * @return l'index/choix de prise (actuellement 0)
	 */
	public int choisirPrise(int nbpossibilite) {
		// temporaire en attendant d'avoir des strategies pour l'ia
		return 0;
	}

	/**
	 * Choisit une prise concernant sa propre offre, en déléguant à la stratégie de prise.
	 *
	 * @param jestDeSoi le jest du joueur
	 * @param offreDeSoi l'offre du joueur
	 * @return le choix de prise
	 */
	public int choisirPriseDeSoi(Jest jestDeSoi, Offre offreDeSoi) {
		return this.strategyPrise.choisirPriseDeSoi(jestDeSoi, offreDeSoi);
	}

	/**
	 * Choisit une prise parmi les offres adverses, en déléguant à la stratégie de prise.
	 *
	 * @param jestDeSoi le jest du joueur
	 * @param offresAdverse la liste des offres adverses disponibles
	 * @return le choix de prise
	 */
	public int choisirPriseAdverse(Jest jestDeSoi, ArrayList<Offre> offresAdverse) {
		return this.strategyPrise.choisirPriseAdverse(this.jest, offresAdverse);
	}

}