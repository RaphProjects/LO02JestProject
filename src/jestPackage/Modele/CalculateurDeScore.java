package jestPackage.Modele;
import java.util.*;
import java.io.Serializable;

public class CalculateurDeScore implements VisiteurDeCarte, Serializable{
	private static final long serialVersionUID = 1L;
	private Jeu jeu;
	
	public CalculateurDeScore(Jeu jeu) {
		this.jeu = jeu;
	}
	
	public int visit(CarteCouleur carte) {
		return carte.getValeurBase();
	}

	public int visit(Joker joker) {
		return 0;
	}
	public int getScore(Jest jest) {
		ArrayList<Carte> cartes = jest.getCartes();
		int score = jest.valeurTotaleCoeurs(); // On initialise à la valeur des coeurs qui est calculée au sein de la classe jest
		for (Carte carte : cartes) {
			
			
			CarteElement visitable = (CarteElement) carte;
			int valeurbasecourante = visitable.accept(this);
			// on vérifie d'abord si c'est le joker, dans quel cas on pourra calculer sa valeur en fonction du nombre de coeurs
			if(carte.getNom()=="Joker") {
				int nbrDeCoeurs = 0;
				for(Carte carteCourante:cartes) {
					if(carteCourante.getCouleur()==Couleur.COEUR) {
						nbrDeCoeurs+=1;
					}
				}
				if(nbrDeCoeurs==0) {
					score+=4;
				}
			}
			
			// Si la carte est coeur, on la passe

			if (carte.getCouleur() == Couleur.COEUR) {
				continue;
			}
			
			
			// Si la carte est carreau, on enleve les points de la carte
			if (carte.getCouleur() == Couleur.CARREAU) {
				if(valeurbasecourante == 1) {
					boolean estseulecartedecettecouleur = true;
					for (Carte autrecarte : cartes) {
						if (autrecarte.getCouleur()==carte.getCouleur() && autrecarte != carte) {
							estseulecartedecettecouleur = false;
							break;
						}
					}
					if (estseulecartedecettecouleur) {
						score -= 5;
					} 
					else {
						score -= 1;
					}
				}

				continue;
			}
			if(carte.getCouleur()==Couleur.PIQUE || carte.getCouleur()==Couleur.TREFLE) {
			
				if(valeurbasecourante == 1) {
					boolean estseulecartedecettecouleur = true;
					for (Carte autrecarte : cartes) {
						if (autrecarte.getCouleur()==carte.getCouleur() && autrecarte != carte) {
							estseulecartedecettecouleur = false;
							break;
						}
					}
					if (estseulecartedecettecouleur) {
			
						score += 5;
						
					} 
					else {
						score += 1;
					}
				}
				else {
					score += valeurbasecourante;
				}
				// gestion des pairs
				
				for(Carte carteCourante:cartes) {
					if(carte.getCouleur()==Couleur.PIQUE) {
						if(carteCourante.getCouleur()==Couleur.TREFLE && carteCourante.getValeurBase()==carte.getValeurBase()) {
							score+=1; // On ajoute 1 pour chacune des cartes de la paire = 2 en tout
						}
					}
					else {
						if(carteCourante.getCouleur()==Couleur.PIQUE && carteCourante.getValeurBase()==carte.getValeurBase()) {
							score+=1; // On ajoute 1 pour chacune des cartes de la paire = 2 en tout
						}
						
					}
				}
				
	        }
		}
		return score;
	}

}
