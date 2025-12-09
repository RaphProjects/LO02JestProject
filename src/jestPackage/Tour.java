package jestPackage;
import java.util.*;


public class Tour {
	private int numeroTour=0;
	private Jeu jeu;
	private ArrayList<Carte> cartesNonJouees = new ArrayList<Carte>();

	public Tour(Jeu jeu) {
		this.jeu = jeu;
		this.numeroTour = 1;
	}

	public void passerAuTourSuivant() {
        numeroTour++;
    }

	public void distribuerCartes() {
		ArrayList<Carte> newcartes = new ArrayList<Carte>();
		newcartes.addAll(this.cartesNonJouees);
		while (newcartes.size() < this.jeu.getJoueurs().size()*2) {
			newcartes.add(this.jeu.getPioche().piocher());
		}
		Collections.shuffle(newcartes);
		for (Joueur joueur : this.jeu.getJoueurs()) {
			joueur.prendreCarte(newcartes.remove(0));
			joueur.prendreCarte(newcartes.remove(0));
		}
		
	}
	
	public void gererOffres() {
		for (Joueur joueur : this.jeu.getJoueurs()) {
			joueur.deciderOffre();
		}
	}
	
	public void afficherOffres() {
		System.out.println("Offres des joueurs :");
		for (Joueur joueur : this.jeu.getJoueurs()) {
			Offre offre = joueur.getOffre();
			String carteVisible = (offre.getCarteVisible() != null) ? offre.getCarteVisible().getNom() : "Aucune";
			String carteCachee = (offre.getCarteCachee() != null) ? offre.getCarteCachee().getNom() : "Aucune";
			System.out.println(joueur.nom + " - Carte Visible: " + carteVisible + ", Carte Cachee: " + carteCachee);
		}
	}
	
	public void gererPrises() {
		// Trouver le joueur avec la carte visible la plus élevée
		ArrayList<Carte> cartesVisibles = new ArrayList<Carte>();
		for (Joueur joueur : this.jeu.getJoueurs()) {
			cartesVisibles.add(joueur.getOffre().getCarteVisible());
		}
		//int numjoueurAvecCartePlusHaute = cartesVisibles.indexOf(Collections.max(cartesVisibles, Comparator.comparingInt(Carte::getValeurBase)));
		ArrayList<Integer> joueursAvecCartePlusHaute = new ArrayList<Integer>();
		ArrayList<Integer> valeursCouleursPlusHautes = new ArrayList<Integer>();
		for (int i = 0; i < cartesVisibles.size(); i++) {
			if (cartesVisibles.get(i).getValeurBase() == Collections.max(cartesVisibles, Comparator.comparingInt(Carte::getValeurBase)).getValeurBase()) {
				joueursAvecCartePlusHaute.add(i);
				valeursCouleursPlusHautes.add(cartesVisibles.get(i).getValeurCouleur());
			}
		}
		
		// Gérer les égalités;
		// récupérer l'indice de la plus haute valeur de couleur
		int indicePlusHauteValeurCouleur = valeursCouleursPlusHautes.indexOf(Collections.max(valeursCouleursPlusHautes));
		// retirer de la liste tous les éléments des indices de joueurs sauf celui avec l'indice de la meileure valeur de couleur
		for (int i = 0; i < joueursAvecCartePlusHaute.size(); i++) {
			if (i != indicePlusHauteValeurCouleur) {
				joueursAvecCartePlusHaute.remove(i);
			}
		}
		
		// La ligne suivante est juste pour du debug
		//System.out.println("Le joueur " + this.jeu.getJoueurs().get(joueursAvecCartePlusHaute.getFirst()).nom + " A la plus grande offre visible.");
		
		
		// On commence par le joueur qui a la carte visible la plus élevée
		Joueur premierAjouer = this.jeu.getJoueurs().get(joueursAvecCartePlusHaute.get(0);
		premierAjouer.choisirPrise();
		
		// Puis on continue avec le joueur qui s'est fait prendre une carte
		
		
	}
	
	
}
