package jestPackage;
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
		System.out.println("Score initial : " + score);
		for (Carte carte : cartes) {
			System.out.println("Carte : " + carte.getNom());
			
			
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
					System.out.println("Score += 4");
					score+=4;
				}
				else {
					System.out.println("Joker nul");
				}
			}
			
			// Si la carte est coeur, on la passe

			if (carte.getCouleur() == Couleur.COEUR) {
				System.out.println("Score += 0");
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
						System.out.println("Score -= 5");
						score -= 5;
					} 
					else {
						System.out.println("Score -= 1");
						score -= 1;
					}
				}
				else {
					System.out.println("Score -= " + valeurbasecourante);
						score -= valeurbasecourante;
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
			
						System.out.println("Score += 5");
						score += 5;
						
					} 
					else {
						System.out.println("Score += 1");
						score += 1;
					}
				}
				else {
					System.out.println("Score += " + valeurbasecourante);
					score += valeurbasecourante;
				}
				
	           }
		}
		System.out.println(" ");
		return score;
	}

}
