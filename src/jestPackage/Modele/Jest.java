package jestPackage.Modele;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Représente un « Jest » (ensemble de cartes) d'un joueur.
 * <p>
 * Cette classe stocke la liste des cartes et fournit des méthodes utilitaires
 * permettant de compter des cartes par valeur/couleur, de déterminer des valeurs
 * extrêmes (plus haute / plus petite) et de calculer certaines valeurs
 * spécifiques au jeu (ex. règle du Joker et des Cœurs).
 * </p>
 */
public class Jest implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Liste des cartes composant le Jest.
	 */
	private ArrayList<Carte> cartes = new ArrayList<Carte>();

	/**
	 * Ajoute une carte au Jest.
	 *
	 * @param carte la carte à ajouter
	 */
	public void ajouterCarte(Carte carte) {
		this.cartes.add(carte);
	}

	/**
	 * Affiche le Jest via la vue du jeu.
	 * <p>
	 * Délègue l'affichage à {@code Jeu.vue}.
	 * </p>
	 */
	public void afficherJest() {
		Jeu.vue.afficherCartesJest(cartes);

	}

	/**
	 * Affiche le contenu du Jest dans la sortie standard.
	 */
	public void afficher() {
		for (Carte carte : cartes) {
			System.out.println("- " + carte.getNom());
		}
	}

	/**
	 * Renvoie la liste des cartes du Jest.
	 *
	 * @return la liste des cartes
	 */
	public ArrayList<Carte> getCartes() {
		return this.cartes;
	}

	/**
	 * Compte le nombre de cartes du Jest ayant une valeur de base donnée.
	 *
	 * @param valeur la valeur de base recherchée
	 * @return le nombre de cartes correspondant à cette valeur
	 */
	public int nbCarteDeLaValeur(int valeur) {
		int compteur = 0;
		for (Carte carte : cartes) {
			if (carte.getValeurBase() == valeur) {
				compteur++;
			}
		}
		return compteur;
	}

	/**
	 * Compte le nombre de cartes du Jest d'une couleur donnée.
	 *
	 * @param couleur la couleur recherchée
	 * @return le nombre de cartes de cette couleur
	 */
	public int nbCarteDeLaCouleur(Couleur couleur) {
		int compteur = 0;
		for (Carte carte : cartes) {
			if (carte.getCouleur() == couleur) {
				compteur++;
			}
		}
		return compteur;
	}

	/**
	 * Renvoie la plus haute valeur (valeur de base) parmi les cartes d'une couleur donnée.
	 * <p>
	 * Cas particulier : un As (valeur de base 1) peut valoir 5 si c'est l'unique carte
	 * de sa couleur dans le Jest.
	 * </p>
	 *
	 * @param couleur la couleur à considérer
	 * @return la plus haute valeur trouvée pour cette couleur (0 si aucune carte de la couleur)
	 */
	public int plusHauteValeurDeLaCouleur(Couleur couleur) {
		int plusHauteV = 0;
		for (Carte carte : cartes) {
			if (carte.getCouleur() != couleur) {
				continue;
			}
			// cas spécial de l'as
			if (carte.getValeurBase() == 1) {
				// On vérifie si on a une autre carte de la même couleur que l'as, si ce n'est pas le cas, il vaut 5
				if (nbCarteDeLaCouleur(carte.getCouleur()) == 1) {
					if (5 > plusHauteV) {
						plusHauteV = 5;
					}
				}
			} else if (carte.getValeurBase() > plusHauteV) {
				plusHauteV = carte.getValeurBase();
			}
		}
		return plusHauteV;

	}

	/**
	 * Renvoie la plus petite valeur (valeur de base) parmi les cartes d'une couleur donnée.
	 * <p>
	 * Cas particulier : un As (valeur de base 1) peut valoir 5 si c'est l'unique carte
	 * de sa couleur dans le Jest.
	 * </p>
	 *
	 * @param couleur la couleur à considérer
	 * @return la plus petite valeur trouvée ; 100 si aucune carte de cette couleur n'est présente
	 */
	public int plusPetiteValeurDeLaCouleur(Couleur couleur) {
		int plusPetiteV = 6; // Valeur maximale + 1
		for (Carte carte : cartes) {
			if (carte.getCouleur() != couleur) {
				continue;
			}
			// cas spécial de l'as
			if (carte.getValeurBase() == 1) {
				// On vérifie si on a une autre carte de la même couleur que l'as, si ce n'est
				// pas le cas, il vaut 5
				if (nbCarteDeLaCouleur(carte.getCouleur()) == 1) {
					if (5 < plusPetiteV) {
						plusPetiteV = 5;
					}
				}
			} else if (carte.getValeurBase() < plusPetiteV) {
				plusPetiteV = carte.getValeurBase();
			}
		}
		// Si aucune carte de la couleur n'a été trouvée, on retourne 100
		if (plusPetiteV == 6) {
			return 100;
		}
		return plusPetiteV;

	}

	/**
	 * Renvoie la plus haute valeur (valeur de base) parmi toutes les cartes du Jest.
	 * <p>
	 * Cas particulier : un As (valeur de base 1) peut valoir 5 si c'est l'unique carte
	 * de sa couleur dans le Jest.
	 * </p>
	 *
	 * @return la plus haute valeur trouvée (0 si le Jest est vide)
	 */
	public int plusHauteValeur() {
		int plusHauteV = 0;
		for (Carte carte : cartes) {
			// cas spécial de l'as
			if (carte.getValeurBase() == 1) {
				// On vérifie si on a une autre carte de la même couleur que l'as, si ce n'est pas le cas, il vaut 5
				if (nbCarteDeLaCouleur(carte.getCouleur()) == 1) {
					if (5 > plusHauteV) {
						plusHauteV = 5;
					}
				}
			} else if (carte.getValeurBase() > plusHauteV) {
				plusHauteV = carte.getValeurBase();
			}
		}
		return plusHauteV;
	}

	/**
	 * Calcule la valeur de couleur associée à la (aux) carte(s) de plus haute valeur du Jest.
	 * <p>
	 * En cas d'égalité sur la plus haute valeur (intra-Jest), on retient la plus grande
	 * {@code valeurCouleur} parmi les cartes ex æquo.
	 * </p>
	 *
	 * @return la valeur de couleur maximale parmi les cartes de plus haute valeur
	 */
	public int valeurDeCouleurDePlusHauteValeur() {
		// On stocke les indices des cartes ayant la plus haute valeur (pour les égalités intra-jest)
		ArrayList<Integer> indicesPlusHauteValeur = new ArrayList<Integer>();
		int plusHauteV = plusHauteValeur();
		for (int i = 0; i < cartes.size(); i++) {
			Carte carte = cartes.get(i);
			// cas spécial de l'as
			if (carte.getValeurBase() == 1) {
				// On vérifie si on a une autre carte de la même couleur que l'as, si ce n'est
				// pas le cas, il vaut 5
				if (nbCarteDeLaCouleur(carte.getCouleur()) == 1) {
					if (5 == plusHauteV) {
						indicesPlusHauteValeur.add(i);
					}
				}
			} else if (carte.getValeurBase() == plusHauteV) {
				indicesPlusHauteValeur.add(i);
			}
		}
		// On cherche la plus haute valeur de couleur parmi les cartes ayant la plus haute valeur
		int valeurCouleur = 0;
		for (int index : indicesPlusHauteValeur) {
			Carte carte = cartes.get(index);
			int valeurCourante = carte.getValeurCouleur();
			if (valeurCourante > valeurCouleur) {
				valeurCouleur = valeurCourante;
			}
		}
		return valeurCouleur;
	}

	/**
	 * Calcule la valeur totale des Cœurs en présence du Joker selon les règles implémentées.
	 * <p>
	 * - Si le Joker n'est pas présent, la méthode retourne 0.<br>
	 * - Si le Joker est présent : la contribution des cartes de Cœur dépend du nombre de Cœurs
	 *   présents (seuil à 4 dans cette implémentation).<br>
	 * </p>
	 *
	 * @return la valeur totale calculée pour les Cœurs (ou 0 si le Joker est absent)
	 */
	public int valeurTotaleCoeurs() {
		// On vérifie si le joker est présent
		int nombreDeCoeurs = 0;
		int valeurTotale = 0;
		boolean jokerPresent = false;
		for (Carte carteCourante : cartes) {
			if (carteCourante.getNom() == "Joker") {

				jokerPresent = true;
			} else if (carteCourante.getCouleur() == Couleur.COEUR) {
				nombreDeCoeurs += 1;
			}
		}
		if (!jokerPresent) {
			// Pas de joker, on retourne forcement 0
			return 0;
		}
		for (Carte carteCourante : cartes) {
			if (nombreDeCoeurs < 4 && carteCourante.estCouleur()) {
				if (carteCourante.getCouleur() == Couleur.COEUR) {
					valeurTotale -= carteCourante.getValeurBase();
				}

			} else if (carteCourante.estCouleur()) {
				if (carteCourante.getCouleur() == Couleur.COEUR) {
					valeurTotale += carteCourante.getValeurBase();
				}

			}
		}

		return valeurTotale;

	}
}