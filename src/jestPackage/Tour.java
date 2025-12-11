package jestPackage;
import java.util.*;


public class Tour {
	private int numeroTour=1;
	private Jeu jeu;
	private ArrayList<Carte> cartesNonJouees = new ArrayList<Carte>();
	private ArrayList<Integer> joueursAyantJoueCeTour = new ArrayList<Integer>();

	public Tour(Jeu jeu) {
		this.jeu = jeu;
		this.numeroTour = 1;
	}
	
	public void afficherNumeroTour() {
		System.out.println("----- Tour " + this.numeroTour + " -----");
	}

	public void passerAuTourSuivant() {
		ajouterCarteNonJouee();
		joueursAyantJoueCeTour.clear();
        numeroTour++;
    }
	
	public void ajouterCarteNonJouee() {
		for (Joueur joueur : this.jeu.getJoueurs()) {
			this.cartesNonJouees.add(joueur.getOffre().getCarteRestante());
		}
	}

	public void distribuerCartes() {
		ArrayList<Carte> newcartes = new ArrayList<Carte>();
		// Par sécurité : On vérifie qu'il y a bien une carte nonJouee par joueur
		if (this.cartesNonJouees.size() != this.jeu.getJoueurs().size() && this.numeroTour > 1) {
			System.out.println("Erreur : le nombre de cartes non jouées ne correspond pas au nombre de joueurs.");
		}
		
		newcartes.addAll(this.cartesNonJouees); // On part des cartes non jouées du tour précédent
		while (newcartes.size() < this.jeu.getJoueurs().size()*2) {// On pioche des cartes jusqu'à en avoir assez pour distribuer 2 par joueur
			newcartes.add(this.jeu.getPioche().piocher());
		}
		Collections.shuffle(newcartes);
		for (Joueur joueur : this.jeu.getJoueurs()) {
			joueur.prendreCarte(newcartes.remove(0));
			joueur.prendreCarte(newcartes.remove(0));
		}
		this.cartesNonJouees.clear();
	}
	
	public int joueurAvecLaPlusGrandeValeurVisible(){
		// Trouver le joueur n'ayant pas deja joué avec la carte visible la plus élevée
		// Isoler le cas où tout le monde a déjà joué
		if (this.joueursAyantJoueCeTour.size() == this.jeu.getJoueurs().size()) {
			return -1;
		}
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
	    int indiceJoueurCourant = joueurAvecLaPlusGrandeValeurVisible(); // Le premier a jouer est toujours celui avec la carte visible la plus élevée
	    
	    // Boucle jusqu'à ce que tous les joueurs aient joué ce tour
	    while (this.joueursAyantJoueCeTour.size() < this.jeu.getJoueurs().size()) {
	        Joueur joueurCourant = this.jeu.getJoueurs().get(indiceJoueurCourant);
	        System.out.println("C'est au tour de " + joueurCourant.nom + " de jouer.");
	        
	        int indiceJoueur = 0;
	        Map<Integer, Integer> joueurPourChoix = new HashMap<>();
	        
	        // Vérifier si aucune offre est complète chez les autres joueurs
	        boolean aucunAutreAvecOffreComplete = true;

	        for (Joueur j : this.jeu.getJoueurs()) {
	            if (j != joueurCourant && j.getOffre().estComplete()) {
	                aucunAutreAvecOffreComplete = false;
	                break; // on peut s'arrêter dès qu'on trouve une autre offre complète
	            }
	        }

	        if (aucunAutreAvecOffreComplete) {
	            // Le joueur doit prendre une carte de sa propre offre
	            System.out.println("Toutes les autres offres sont vides. Choisissez dans votre offre.");
	            System.out.println("1 : Carte Visible - " + joueurCourant.getOffre().getCarteVisible());
	            System.out.println("2 : Carte Cachée - Inconnue");
	            int choix = joueurCourant.choisirPrise(2);
	            joueurCourant.ajouterAsonJest(joueurCourant.donnerCarte(choix == 1));
	            this.joueursAyantJoueCeTour.add(joueurCourant.getNumJoueur());
	            break;
	        }
	        
	        // Afficher les offres disponibles des autres joueurs
	        for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
	            Joueur j = this.jeu.getJoueurs().get(i);
	            if (j != joueurCourant && j.getOffre().estComplete()) {
	                Offre offre = j.getOffre();
	                int idxVisible = indiceJoueur * 2 + 1;
	                int idxCache = indiceJoueur * 2 + 2;
	                
	                System.out.println("Offre de " + j.nom + " :");
	                System.out.println(idxVisible + " : Carte Visible - " + offre.getCarteVisible());
	                System.out.println(idxCache + " : Carte Cachée - Inconnue");
	                
	                joueurPourChoix.put(idxVisible, i);
	                joueurPourChoix.put(idxCache, i);
	                indiceJoueur++;
	            }
	        }
	        
	        int numCarteChoisie = joueurCourant.choisirPrise(indiceJoueur * 2 + 2);
	        System.out.println("Le joueur " + joueurCourant.nom + " a choisi la carte " + numCarteChoisie);
	        
	        int prochainIndice = joueurPourChoix.get(numCarteChoisie);
	        // JoueurVole correspond au joueur qui s'est fait prendre une carte de son offre
	        Joueur joueurVole = this.jeu.getJoueurs().get(prochainIndice);
	        System.out.println("Le joueur " + joueurVole.nom + " s'est fait prendre une carte.");
	        
	        joueurCourant.ajouterAsonJest(joueurVole.donnerCarte(numCarteChoisie % 2 != 0));
	        this.joueursAyantJoueCeTour.add(joueurCourant.getNumJoueur());
	        
	        // Déterminer le prochain joueur
	        indiceJoueurCourant = this.joueursAyantJoueCeTour.contains(joueurVole.getNumJoueur()) ? joueurAvecLaPlusGrandeValeurVisible() : prochainIndice;
	    }
	    
	    // TEMPORAIRE Afficher les jests
	    for (Joueur joueur : this.jeu.getJoueurs()) {
	        System.out.println("Jest de " + joueur.nom + " : ");
	        joueur.jest.afficher();
	    }
	}
	
	
}
