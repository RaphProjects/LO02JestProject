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
	public int getScore(ArrayList<Carte> cartes) {
		int score = 0;
		for (Carte carte : cartes) {
			// Si la carte est coeur, elle vaut 0
			if (carte.getCouleur() == Couleur.COEUR) {
				continue;
			}
			
			CarteElement visitable = (CarteElement) carte;
			int valeurbasecourante = visitable.accept(this);
			// Si la carte est carreau, on enleve les points de la carte
			if (carte.getCouleur() == Couleur.CARREAU) {
				if(valeurbasecourante == 1) {
					boolean estseulecartedecettecouleur = true;
					for (Carte autrecarte : cartes) {
						if (autrecarte.estCouleur() && autrecarte != carte) {
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
				else {
						score -= valeurbasecourante;
				}
				continue;
			}
			
			if(valeurbasecourante == 1) {
				boolean estseulecartedecettecouleur = true;
				for (Carte autrecarte : cartes) {
					if (autrecarte.estCouleur() && autrecarte != carte) {
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
			
           }
		
		
		return score;
	}

}
