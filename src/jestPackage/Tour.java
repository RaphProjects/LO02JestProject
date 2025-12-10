package jestPackage;
import java.util.*;


public class Tour {
	private int numeroTour=0;
	private Jeu jeu;
	private ArrayList<Carte> cartesNonJouees = new ArrayList<Carte>();
	private ArrayList<Integer> joueursAyantJoueCeTour = new ArrayList<Integer>();

	public Tour(Jeu jeu) {
		this.jeu = jeu;
		this.numeroTour = 1;
	}

	public void passerAuTourSuivant() {
		joueursAyantJoueCeTour.clear();
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
	
	public int joueurAvecLaPlusGrandeValeurVisible(){
		// Trouver le joueur n'ayant pas deja joué avec la carte visible la plus élevée
			ArrayList<Carte> cartesVisibles = new ArrayList<Carte>();
			Map<Integer, Carte> cartePourJoueur = new HashMap<>(); //Associe la carte visible au numéro du joueur
			for (Joueur joueur : this.jeu.getJoueurs()) {
				// On inclus que les cartes des joueurs n'ayant pas encore joué ce tour
				if (!(joueur.getOffre().getCarteVisible() == null) && !joueursAyantJoueCeTour.contains(joueur.getNumJoueur())) {
						cartePourJoueur.put(joueur.getNumJoueur(),joueur.getOffre().getCarteVisible());
				}
			}
			
			// Isoler les cartes ayant la plus haute valeur
			
			Map<Integer, Carte> cartePlusHautePourJoueur = new HashMap<>();
			for (Map.Entry<Integer, Carte> entry : cartePourJoueur.entrySet()) {
				if (entry.getValue().getValeurBase() == Collections
						.max(cartePourJoueur.values(), Comparator.comparingInt(Carte::getValeurBase)).getValeurBase()) {
					cartePlusHautePourJoueur.put(entry.getKey(), entry.getValue());
				}
			}
			
			// Gérer les égalités;
			// On refait la meme chose mais avec la valeur de couleur
			
			for (Map.Entry<Integer, Carte> entry : cartePlusHautePourJoueur.entrySet()) {
				if (entry.getValue().getValeurCouleur() == Collections
						.max(cartePlusHautePourJoueur.values(), Comparator.comparingInt(Carte::getValeurCouleur))
						.getValeurCouleur()) {
					System.out.println("Le joueur " + this.jeu.getJoueurs().get(entry.getKey()-1).nom
							+ " a la plus grande valeur visible avec la carte " + entry.getValue().toString() + ".");
					System.out.println("C'est à lui de jouer.");
					return entry.getKey()-1;
				}
			}
		// On ne devrait jamais arriver ici
		System.out.println("Erreur : impossible de déterminer le joueur avec la plus grande valeur visible.");
		return -1;
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
		Joueur premierAjouer = this.jeu.getJoueurs().get(joueurAvecLaPlusGrandeValeurVisible());
		
		
		//Afficher toutes les cartes qu'il peut prendre (toutes les cartes des offres sauf les siennes)
		int indiceJoueur = 0; // utilisé pour numéroter les options
		Map<Integer, Integer> joueurPourChoix = new HashMap<>(); // Map avec les choix en indice et le joueur correspondant en element
		
		for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
			if (this.jeu.getJoueurs().get(i) != premierAjouer) {
				
				Offre offre = this.jeu.getJoueurs().get(i).getOffre();
				int affichageIndexVisible = indiceJoueur * 2 + 1;
		        int affichageIndexCache = indiceJoueur * 2 + 2;
				System.out.println("Offre de " + this.jeu.getJoueurs().get(i).nom + " :");
				System.out.println(affichageIndexVisible +" : Carte Visible - "
						+ offre.getCarteVisible().toString());
				joueurPourChoix.put(affichageIndexVisible, i);
				joueurPourChoix.put(affichageIndexCache, i);
				System.out.println(affichageIndexCache+" : Carte Cachée - Inconnue");
				indiceJoueur+=1;
			}
			
		}
		int numCarteChoisie = (premierAjouer.choisirPrise(indiceJoueur*2+2));
		System.out.println("Le joueur " + premierAjouer.nom + " a choisi la carte numéro " + numCarteChoisie + ".");
		// afficher joueurPourChoix pour debug
		for (Map.Entry<Integer, Integer> entry : joueurPourChoix.entrySet()) {
			System.out.println(
					"Choix : " + entry.getKey() + " -> Joueur : " + this.jeu.getJoueurs().get(entry.getValue()).nom);
		}
		this.joueursAyantJoueCeTour.add(premierAjouer.getNumJoueur());
		
		int numProchainJoueur = joueurPourChoix.get(numCarteChoisie);
		System.out.println("Le joueur " + this.jeu.getJoueurs().get(numProchainJoueur).nom + " s'est fait prendre une carte.");
		// on récupère la carte choisie dans le jest du prochainjoueur et on l'ajoute au jest du premier joueur
		premierAjouer.ajouterAsonJest(this.jeu.getJoueurs().get(numProchainJoueur).donnerCarte((numCarteChoisie % 2 == 0) ? false : true ));// renvoie true si carte visible choisie
		
		// Puis on continue avec le joueur qui s'est fait prendre une carte jusqu'à ce que tous les joueurs aient joué
		while (this.joueursAyantJoueCeTour.size() < this.jeu.getJoueurs().size()) {
			// Deux cas possibles ici : soit le joueur qui s'est fait prendre une carte a déjà joué,
			// dans quel cas on utilise la plus grande carte visible parmi les joueurs n'ayant pas encore joué,
			// soit il n'a pas encore joué, dans quel cas c'est son tour
			numProchainJoueur = numProchainJoueur+1;
			System.out.println("Valeur de numProchainJoueur avant vérification : " + numProchainJoueur);
			
			// debug : afficher tous les joueurs ayant joué ce tour
			System.out.print("Joueurs ayant joué ce tour : ");
			for (Integer numJoueur : this.joueursAyantJoueCeTour) {
				System.out.println(this.jeu.getJoueurs().get(numJoueur-1).nom + " ");
			}
			
			if (this.joueursAyantJoueCeTour.contains(numProchainJoueur)) {
				numProchainJoueur = joueurAvecLaPlusGrandeValeurVisible()+1;
			}
			
			numProchainJoueur = numProchainJoueur-1;
			
			
			
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
			
			// Afficher quelles offres sont complètes pour DEBUG
			for (int i = 0; i < joueursOffreEstComplete.size(); i++) {
				System.out.println("Joueur " + this.jeu.getJoueurs().get(i).nom + " offre complète : "
						+ joueursOffreEstComplete.get(i));
			}
			
			// identifier s'il ne reste que l'offre du joueur courant qui est complete

			if (Collections.frequency(joueursOffreEstComplete, true) <= 1 && joueursOffreEstComplete.get(joueurcourant.getNumJoueur()-1)) {
				// le joueur courant est le seul avec une offre complète, il doit choisir une carte de sa propre offre
				System.out.println("Toutes les autres offres sont vides. Vous devez choisir une carte de votre propre offre.");
				Offre offre = joueurcourant.getOffre();
				System.out.println("1 : Carte Visible - "
						+ offre.getCarteVisible().toString());
				System.out.println("2 : Carte Cachée - Inconnue");
				numCarteChoisie = (joueurcourant.choisirPrise(2));
				joueurcourant.ajouterAsonJest(joueurcourant.donnerCarte(numCarteChoisie == 1));// Donne la carte visible si numCarteChoisie = 1 sinon la carte cachée
				
				break;
			}
			else {
				for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
					if (this.jeu.getJoueurs().get(i) != joueurcourant
							&& this.jeu.getJoueurs().get(i).getOffre().estComplete()) {

						Offre offre = this.jeu.getJoueurs().get(i).getOffre();
						int affichageIndexVisible = indiceJoueur * 2 + 1;
				        int affichageIndexCache = indiceJoueur * 2 + 2;
						System.out.println("Offre de " + this.jeu.getJoueurs().get(i).nom + " :");
						System.out.println(
								affichageIndexVisible+ " : Carte Visible - " + offre.getCarteVisible().toString());
						joueurPourChoix.put(affichageIndexVisible, i);
						joueurPourChoix.put(affichageIndexCache, i);
						System.out.println(affichageIndexCache + " : Carte Cachée - Inconnue");
						indiceJoueur += 1;
					}

				}
				numCarteChoisie = (joueurcourant.choisirPrise(indiceJoueur * 2 + 2));
				// afficher le nom du joueur dont la carte a été choisie
				
				numProchainJoueur = joueurPourChoix.get(numCarteChoisie);
				System.out.println("Le joueur " + this.jeu.getJoueurs().get(numProchainJoueur).nom + " s'est fait prendre une carte.");
				// on récupère la carte choisie dans le jest du prochainjoueur et on l'ajoute au
				// jest du joueur courant
				joueurcourant.ajouterAsonJest(this.jeu.getJoueurs().get(numProchainJoueur).donnerCarte(numCarteChoisie % 2 != 0));
				this.joueursAyantJoueCeTour.add(joueurcourant.getNumJoueur());
				
				
			}
			System.out.println();
			System.out.println();
		}
		// debug : afficher tous les jests des joueurs
		for (Joueur joueur : this.jeu.getJoueurs()) {
			System.out.println("Jest de " + joueur.nom + " : ");
		joueur.jest.afficher();
		}
		
	}
	
	
}
