package jestPackage;
import java.util.*;
import java.io.Serializable;
public class Jest implements Serializable {
	private static final long serialVersionUID = 1L;
	private ArrayList<Carte> cartes = new ArrayList<Carte>();
	
	public void ajouterCarte(Carte carte) {
		this.cartes.add(carte);
	}
	
	public void afficherJest() {
		System.out.println("Cartes dans le Jest :");
		for (Carte carte : cartes) {
			System.out.println("- " + carte.getNom());
		}
	}

	public void afficher() {
		for (Carte carte : cartes) {
            System.out.println("- " + carte.getNom());
        }
	}
	
	public ArrayList<Carte> getCartes() {
		return this.cartes;
	}
	
	public int nbCarteDeLaValeur(int valeur) {
        int compteur = 0;
        for (Carte carte : cartes) {
            if (carte.getValeurBase() == valeur) {
                compteur++;
            }
        }
        return compteur;
    }
	
	public int nbCarteDeLaCouleur(Couleur couleur) {
        int compteur = 0;
        for (Carte carte : cartes) {
            if (carte.getCouleur() == couleur) {
                compteur++;
            }
        }
        return compteur;
    }
	
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
			}
			else if (carte.getValeurBase() > plusHauteV) {
				plusHauteV = carte.getValeurBase();
			}
		}
		return plusHauteV;
		
	}
	
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
			}
			else if (carte.getValeurBase() > plusHauteV) {
				plusHauteV = carte.getValeurBase();
			}
		}
		return plusHauteV;
	}
	
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
}
