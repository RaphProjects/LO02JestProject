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
		
	}
	
	
}
