package jestPackage.Modele;

import java.util.*;
import java.io.Serializable;

/**
 * Calculateur de score d'un {@link Jest}.
 * <p>
 * Cette classe implémente le patron Visiteur ({@link VisiteurDeCarte}) afin de récupérer la valeur
 * de base des cartes via {@code accept}, puis applique les règles de scoring spécifiques au jeu
 * (coeurs, carreaux, piques/trèfles, joker et bonus de paires).
 * </p>
 */
public class CalculateurDeScore implements VisiteurDeCarte, Serializable {
	/** Identifiant de sérialisation. */
	private static final long serialVersionUID = 1L;

	private Jeu jeu;

	/**
	 * Construit un calculateur de score associé à une instance de jeu.
	 *
	 * @param jeu l'instance de {@link Jeu} associée à ce calculateur
	 */
	public CalculateurDeScore(Jeu jeu) {
		this.jeu = jeu;
	}

	/**
	 * Visite une carte de couleur (non-joker) et renvoie sa valeur de base.
	 *
	 * @param carte la carte visitée
	 * @return la valeur de base de la carte
	 */
	public int visit(CarteCouleur carte) {
		return carte.getValeurBase();
	}

	/**
	 * Visite un joker.
	 * <p>
	 * La valeur effective du joker est gérée au moment du calcul global du score (dans {@link #getScore(Jest)}),
	 * en fonction du nombre de coeurs présents.
	 * </p>
	 *
	 * @param joker le joker visité
	 * @return 0 (valeur de base du joker)
	 */
	public int visit(Joker joker) {
		return 0;
	}

	/**
	 * Calcule le score total d'un {@link Jest} en appliquant les règles de scoring :
	 * <ul>
	 *   <li>Le score est initialisé avec la valeur totale des coeurs (calculée par {@link Jest#valeurTotaleCoeurs()}).</li>
	 *   <li>Le joker rapporte des points selon le nombre de coeurs (cas particulier géré ici).</li>
	 *   <li>Les carreaux soustraient des points (avec règle spéciale si valeur 1 et carte seule de sa couleur).</li>
	 *   <li>Les piques et trèfles ajoutent des points (avec règle spéciale si valeur 1 et carte seule de sa couleur).</li>
	 *   <li>Bonus de paires entre piques et trèfles de même valeur.</li>
	 * </ul>
	 *
	 * @param jest le {@link Jest} dont on souhaite calculer le score
	 * @return le score total calculé
	 */
	public int getScore(Jest jest) {
		ArrayList<Carte> cartes = jest.getCartes();
		int score = jest.valeurTotaleCoeurs(); // On initialise à la valeur des coeurs qui est calculée au sein de la classe jest
		for (Carte carte : cartes) {

			CarteElement visitable = (CarteElement) carte;
			int valeurbasecourante = visitable.accept(this);
			// on vérifie d'abord si c'est le joker, dans quel cas on pourra calculer sa valeur en fonction du nombre de coeurs
			if (carte.getNom() == "Joker") {
				int nbrDeCoeurs = 0;
				for (Carte carteCourante : cartes) {
					if (carteCourante.getCouleur() == Couleur.COEUR) {
						nbrDeCoeurs += 1;
					}
				}
				if (nbrDeCoeurs == 0) {
					score += 4;
				}
			}

			// Si la carte est coeur, on la passe

			if (carte.getCouleur() == Couleur.COEUR) {
				continue;
			}

			// Si la carte est carreau, on enleve les points de la carte
			if (carte.getCouleur() == Couleur.CARREAU) {
				if (valeurbasecourante == 1) {
					boolean estseulecartedecettecouleur = true;
					for (Carte autrecarte : cartes) {
						if (autrecarte.getCouleur() == carte.getCouleur() && autrecarte != carte) {
							estseulecartedecettecouleur = false;
							break;
						}
					}
					if (estseulecartedecettecouleur) {
						score -= 5;
					} else {
						score -= 1;
					}
				} else {
					score -= valeurbasecourante;
				}

				continue;
			}
			if (carte.getCouleur() == Couleur.PIQUE || carte.getCouleur() == Couleur.TREFLE) {

				if (valeurbasecourante == 1) {
					boolean estseulecartedecettecouleur = true;
					for (Carte autrecarte : cartes) {
						if (autrecarte.getCouleur() == carte.getCouleur() && autrecarte != carte) {
							estseulecartedecettecouleur = false;
							break;
						}
					}
					if (estseulecartedecettecouleur) {

						score += 5;

					} else {
						score += 1;
					}
				} else {
					score += valeurbasecourante;
				}
				// gestion des pairs

				for (Carte carteCourante : cartes) {
					if (carte.getCouleur() == Couleur.PIQUE) {
						if (carteCourante.getCouleur() == Couleur.TREFLE
								&& carteCourante.getValeurBase() == carte.getValeurBase()) {
							score += 1; // On ajoute 1 pour chacune des cartes de la paire = 2 en tout
						}
					} else {
						if (carteCourante.getCouleur() == Couleur.PIQUE
								&& carteCourante.getValeurBase() == carte.getValeurBase()) {
							score += 1; // On ajoute 1 pour chacune des cartes de la paire = 2 en tout
						}

					}
				}

			}
		}
		return score;
	}

}