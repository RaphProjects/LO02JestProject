package jestPackage;
import java.util.*;
import java.io.Serializable;

public class Pioche implements Serializable{
	private static final long serialVersionUID = 1L;
	private ArrayList<Carte> cartes;
	private Jeu jeu;
	
	public Pioche(Jeu jeu) {
		this.cartes = new ArrayList<Carte>();
		this.jeu = jeu;
	}
	
	public void initialiserCartes() {
		if(this.jeu.getExtension()==0) {
		//Tres long : il faut initialiser chaque carte manuellement car il n'y a pas de logique dans les trophées
		// Le joker
		this.cartes.add(new Joker("Joker", new TropheeIncolore(ConditionIncolore.PLUSGRANDJEST)));
		// As de coeur
		this.cartes.add(new CarteCouleur("As de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 1));
		// 2 de coeur
		this.cartes.add(new CarteCouleur("2 de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 2));
		// 3 de coeur
		this.cartes.add(new CarteCouleur("3 de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 3));
		// 4 de coeur
		this.cartes.add(new CarteCouleur("4 de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 4));
		// As de carreau
		this.cartes.add(new CarteCouleur("As de carreau", new TropheeIncolore(ConditionIncolore.MAJORITÉ,4),Couleur.CARREAU, 1));
		// 2 de carreau
		this.cartes.add(new CarteCouleur("2 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND,Couleur.CARREAU),Couleur.CARREAU, 2));
		// 3 de carreau
		this.cartes.add(new CarteCouleur("3 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.CARREAU),Couleur.CARREAU, 3));
		// 4 de carreau
		this.cartes.add(new CarteCouleur("4 de carreau", new TropheeIncolore(ConditionIncolore.PLUSGRANDJEST_SANSJOKER),Couleur.CARREAU, 4));
		// As de trefle
		this.cartes.add(new CarteCouleur("As de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND,Couleur.PIQUE),Couleur.TREFLE, 1));
		// 2 de trefle
		this.cartes.add(new CarteCouleur("2 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.COEUR),Couleur.TREFLE, 2));
		// 3 de trefle
		this.cartes.add(new CarteCouleur("3 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND,Couleur.COEUR),Couleur.TREFLE, 3));
		// 4 de trefle
		this.cartes.add(new CarteCouleur("4 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.PIQUE),Couleur.TREFLE, 4));
		// As de pique
		this.cartes.add(new CarteCouleur("As de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND,Couleur.TREFLE),Couleur.PIQUE, 1));
		// 2 de pique
		this.cartes.add(new CarteCouleur("2 de pique", new TropheeIncolore(ConditionIncolore.MAJORITÉ,3),Couleur.PIQUE, 2));
		// 3 de pique
		this.cartes.add(new CarteCouleur("3 de pique", new TropheeIncolore(ConditionIncolore.MAJORITÉ,2),Couleur.PIQUE, 3));
		// 4 de pique
		this.cartes.add(new CarteCouleur("4 de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.TREFLE),Couleur.PIQUE, 4));
		}
		
		else if(this.jeu.getExtension()==1) {
			// Le joker
			this.cartes.add(new Joker("Joker", new TropheeIncolore(ConditionIncolore.PLUSGRANDJEST)));
			// As de coeur
			this.cartes.add(new CarteCouleur("As de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 1));
			// 2 de coeur
			this.cartes.add(new CarteCouleur("2 de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 2));
			// 3 de coeur
			this.cartes.add(new CarteCouleur("3 de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 3));
			// 4 de coeur
			this.cartes.add(new CarteCouleur("4 de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 4));
			// As de carreau
			this.cartes.add(new CarteCouleur("As de carreau", new TropheeIncolore(ConditionIncolore.MAJORITÉ,4),Couleur.CARREAU, 1));
			// 2 de carreau
			this.cartes.add(new CarteCouleur("2 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND,Couleur.CARREAU),Couleur.CARREAU, 2));
			// 3 de carreau
			this.cartes.add(new CarteCouleur("3 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.CARREAU),Couleur.CARREAU, 3));
			// 4 de carreau
			this.cartes.add(new CarteCouleur("4 de carreau", new TropheeIncolore(ConditionIncolore.PLUSGRANDJEST_SANSJOKER),Couleur.CARREAU, 4));
			// As de trefle
			this.cartes.add(new CarteCouleur("As de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND,Couleur.PIQUE),Couleur.TREFLE, 1));
			// 2 de trefle
			this.cartes.add(new CarteCouleur("2 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.COEUR),Couleur.TREFLE, 2));
			// 3 de trefle
			this.cartes.add(new CarteCouleur("3 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND,Couleur.COEUR),Couleur.TREFLE, 3));
			// 4 de trefle
			this.cartes.add(new CarteCouleur("4 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.PIQUE),Couleur.TREFLE, 4));
			// As de pique
			this.cartes.add(new CarteCouleur("As de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND,Couleur.TREFLE),Couleur.PIQUE, 1));
			// 2 de pique
			this.cartes.add(new CarteCouleur("2 de pique", new TropheeIncolore(ConditionIncolore.MAJORITÉ,3),Couleur.PIQUE, 2));
			// 3 de pique
			this.cartes.add(new CarteCouleur("3 de pique", new TropheeIncolore(ConditionIncolore.MAJORITÉ,2),Couleur.PIQUE, 3));
			// 4 de pique
			this.cartes.add(new CarteCouleur("4 de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.TREFLE),Couleur.PIQUE, 4));
			
			// Cartes de la variante étendue
			
			// 5 de coeur
			this.cartes.add(new CarteCouleur("5 de coeur", new TropheeIncolore(ConditionIncolore.JOKER),Couleur.COEUR, 5));
			// 5 de carreau
			this.cartes.add(new CarteCouleur("5 de carreau", new TropheeCouleur(OrdreTropheeCouleur.PLUSGRAND,Couleur.CARREAU),Couleur.CARREAU, 5));
			// 5 de trefle
			this.cartes.add(new CarteCouleur("5 de trefle", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.PIQUE),Couleur.TREFLE, 5));
			// 5 de pique
			this.cartes.add(new CarteCouleur("5 de pique", new TropheeCouleur(OrdreTropheeCouleur.PLUSPETIT,Couleur.TREFLE),Couleur.PIQUE, 5));
		}
		
		this.melangerCartes();
	}	
	
	
	public void melangerCartes() {
		Collections.shuffle(this.cartes);
	}
	
	public boolean estPiochable() {
		return this.cartes.size()>=(this.jeu.getJoueurs().size()); // A chaque tour on recupere une carte par joueur (cartes non jouéees au tour precedent)
		// Il faut donc contenir au moins une carte par joueur pour que chaque joueur ait deux cartes au total apres distribution
	}
	
	public Carte piocher() {
		if (this.cartes.isEmpty()) {
			System.out.println("La pioche est vide.");
			return null;
		}
		return this.cartes.remove(0);
	}
}
