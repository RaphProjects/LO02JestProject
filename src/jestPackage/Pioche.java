package jestPackage;
import java.util.*;


public class Pioche {
	private ArrayList<Carte> cartes;
	
	public Pioche() {
		this.cartes = new ArrayList<Carte>();
	}
	
	public void initialiserCartes() {
		//Tres long : il faut initialiser chaque carte manuellement car il n'y a pas de logique dans les trophées
		// Le joker
		this.cartes.add(new Joker("Joker", new TropheeIncolore(ConditionIncolore.PLUSGRANDJEST)));
		// As de coeur
		this.cartes.add(new CarteCouleur("As de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 1));
    }
	
	public void melangerCartes() {
		Collections.shuffle(this.cartes);
	}

}
