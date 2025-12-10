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
		System.out.println("Le joueur " + this.jeu.getJoueurs().get(joueursAvecCartePlusHaute.getFirst()).nom + " commence car il a la plus grande offre visible.");
		
		
		int numjoueursayantjoue = 0;
		// On commence par le joueur qui a la carte visible la plus élevée
		Joueur premierAjouer = this.jeu.getJoueurs().get(joueursAvecCartePlusHaute.get(0));
		
		
		//Afficher toutes les cartes qu'il peut prendre (toutes les cartes des offres sauf les siennes)
		int indiceJoueur = 0; // utilisé pour numéroter les options
		ArrayList<Integer> joueurPourChoix = new ArrayList<>(); // Liste avec les choix en indice et le joueur correspondant en element
		
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i) != premierAjouer) {
				
				Offre offre = this.jeu.getJoueurs().get(i).getOffre();
				System.out.println("Offre de " + this.jeu.getJoueurs().get(i).nom + " :");
				System.out.println(indiceJoueur*2+1 +" : Carte Visible - "
						+ offre.getCarteVisible().toString());
				joueurPourChoix.set(indiceJoueur*2+1,i);
				System.out.println(indiceJoueur*2+2+" : Carte Cachée - Inconnue");
				indiceJoueur+=1;
			}
			
		}
		int numCarteChoisie = (premierAjouer.choisirPrise(indiceJoueur*2+2));
		int numProchainJoueur = joueurPourChoix.get(numCarteChoisie);
		// on récupère la carte choisie dans le jest du prochainjoueur et on l'ajoute au jest du premier joueur
		premierAjouer.ajouterAsonJest(this.jeu.getJoueurs().get(numProchainJoueur).donnerCarte((numCarteChoisie % 2 == 0) ? true : false ));
		numjoueursayantjoue+=1;

		// Puis on continue avec le joueur qui s'est fait prendre une carte jusqu'à ce que tous les joueurs aient joué
		while (numjoueursayantjoue < this.jeu.getJoueurs().size()) {
			Joueur joueurcourant= this.jeu.getJoueurs().get(numProchainJoueur);
			System.out.println("C'est au tour de " + this.jeu.getJoueurs().get(numProchainJoueur).nom + " de jouer.");
			// Afficher toutes les cartes qu'il peut prendre (toutes les cartes des offres completes
			// sauf les siennes)
			indiceJoueur = 0; // utilisé pour numéroter les options
			joueurPourChoix.clear(); // Liste avec les choix en indice et le joueur correspondant en element
			// Identifier les joueurs avec des offres complètes
			ArrayList<Boolean> joueursOffreEstComplete = new ArrayList<>();
			for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
				joueursOffreEstComplete.add(this.jeu.getJoueurs().get(i).getOffre().estComplete());
			}
			// identifier s'il ne reste que l'offre du joueur courant qui est complete
			if (Collections.frequency(joueursOffreEstComplete, true) <= 1) {
				// plus qu'un seul joueur avec une offre complète, le joueur courant doit choisir une carte de sa propre offre
				System.out.println("Toutes les autres offres sont vides. Vous devez choisir une carte de votre propre offre.");
				Offre offre = joueurcourant.getOffre();
				System.out.println("1 : Carte Visible - "
						+ offre.getCarteVisible().toString());
				System.out.println("2 : Carte Cachée - Inconnue");
				numCarteChoisie = (joueurcourant.choisirPrise(2));
				joueurcourant.ajouterAsonJest(joueurcourant.donnerCarte(numCarteChoisie == 1));// Donne la carte visible si numCarteChoisie = 1 sinon la carte cachée
				break;
			}
		}
		
		
	}
	
	
}
