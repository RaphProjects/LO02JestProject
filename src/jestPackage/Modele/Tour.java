package jestPackage.Modele;
import java.util.*;
import java.io.Serializable;

/**
 * Représente un tour de jeu.
 * <p>
 * Un tour gère :
 * </p>
 * <ul>
 *   <li>la distribution des cartes (en tenant compte des cartes non jouées du tour précédent)</li>
 *   <li>la création des offres par chaque joueur</li>
 *   <li>l'ordre de jeu (déterminé par la plus grande carte visible)</li>
 *   <li>la gestion des prises (prendre dans une offre adverse ou sa propre offre si nécessaire)</li>
 * </ul>
 * <p>
 * Cette classe dépend de la vue et du contrôleur accessibles via {@link Jeu#vue} et
 * {@link Jeu#controleur} (utilisation indirecte via appels dans les méthodes).
 * </p>
 */
public class Tour implements Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * Numéro du tour (commence à 1).
	 */
	private int numeroTour=1;

	/**
	 * Référence au jeu auquel ce tour appartient.
	 */
	private Jeu jeu;

	/**
	 * Cartes non jouées à la fin d'un tour (cartes restantes dans les offres),
	 * qui seront redistribuées au tour suivant.
	 */
	private ArrayList<Carte> cartesNonJouees = new ArrayList<Carte>();

	/**
	 * Liste des numéros des joueurs ayant déjà joué pendant ce tour.
	 */
	private ArrayList<Integer> joueursAyantJoueCeTour = new ArrayList<Integer>();

	/**
	 * Construit un tour associé à un jeu.
	 *
	 * @param jeu instance du jeu concernée
	 */
	public Tour(Jeu jeu) {
		this.jeu = jeu;
		this.numeroTour = 1;
	}

	/**
	 * Affiche le numéro du tour via la vue.
	 */
	public void afficherNumeroTour() {
		Jeu.vue.afficherNumeroTour(this.numeroTour);
	}

	/**
	 * Retourne le numéro du tour.
	 *
	 * @return numéro du tour
	 */
	public int getNumeroTour() {
		return this.numeroTour;
	}

	/**
	 * Modifie le numéro du tour.
	 *
	 * @param numeroTour nouveau numéro de tour
	 */
	public void setNumeroTour(int numeroTour) {
		this.numeroTour = numeroTour;
	}

	/**
	 * Passe au tour suivant :
	 * <ul>
	 *   <li>stocke les cartes restantes (non jouées) des offres en fin de tour</li>
	 *   <li>réinitialise la liste des joueurs ayant joué</li>
	 *   <li>incrémente le numéro de tour</li>
	 * </ul>
	 */
	public void passerAuTourSuivant() {
		ajouterCarteNonJouee();
		joueursAyantJoueCeTour.clear();
        numeroTour++;
    }

	/**
	 * Ajoute aux {@link #cartesNonJouees} la carte restante de l'offre de chaque joueur.
	 * <p>
	 * La "carte restante" dépend de l'état de l'offre (carte visible/cachée encore présente).
	 * </p>
	 */
	public void ajouterCarteNonJouee() {
		for (Joueur joueur : this.jeu.getJoueurs()) {
			this.cartesNonJouees.add(joueur.getOffre().getCarteRestante());
		}
	}

	/**
	 * Distribue 2 cartes à chaque joueur.
	 * <p>
	 * Les cartes distribuées proviennent :
	 * </p>
	 * <ol>
	 *   <li>des cartes non jouées du tour précédent</li>
	 *   <li>puis de la pioche si nécessaire afin d'atteindre 2 cartes par joueur</li>
	 * </ol>
	 * <p>
	 * Les cartes sont ensuite mélangées avant distribution.
	 * </p>
	 */
	public void distribuerCartes() {
		ArrayList<Carte> newcartes = new ArrayList<Carte>();
		// Par sécurité : On vérifie qu'il y a bien une carte nonJouee par joueur
		if (this.cartesNonJouees.size() != this.jeu.getJoueurs().size() && this.numeroTour > 1) {
			Jeu.vue.afficherErreurNombreCartesNonJouees();
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

	/**
	 * Détermine le joueur (indice dans la liste {@code jeu.getJoueurs()}) ayant la plus grande
	 * valeur de carte visible parmi ceux n'ayant pas encore joué ce tour.
	 * <p>
	 * En cas d'égalité sur la valeur de base, départage sur la valeur de couleur.
	 * </p>
	 *
	 * @return l'indice (0-based) du joueur sélectionné, ou -1 en cas d'erreur
	 */
	public int joueurAvecLaPlusGrandeValeurVisible(){
		// Trouver le joueur n'ayant pas deja joué avec la carte visible la plus élevée
		// Isoler le cas où tout le monde a déjà joué
		if (this.joueursAyantJoueCeTour.size() == this.jeu.getJoueurs().size()) {
			Jeu.vue.afficherErreurTousJoueursOntJoue();
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
					Jeu.vue.afficherJoueurAvecPlusGrandeValeurVisible(this.jeu.getJoueurs().get(entry.getKey()-1).nom, entry.getValue().toString());
					Jeu.vue.afficherCestAuiDeJouer();
					return entry.getKey()-1;
				}
			}
		// On ne devrait jamais arriver ici
		Jeu.vue.afficherErreurDeterminerJoueurPlusGrandeValeurVisible();
		return -1;
	}

	/**
	 * Demande à chaque joueur de décider son offre pour le tour courant.
	 */
	public void gererOffres() {
		for (Joueur joueur : this.jeu.getJoueurs()) {
			joueur.deciderOffre();
		}
	}

	/**
	 * Affiche les offres de tous les joueurs via la vue.
	 * <p>
	 * Affiche le nom de la carte visible et un indicateur pour la carte cachée.
	 * </p>
	 */
	public void afficherOffres() {
		Jeu.vue.afficherOffresDesJoueurs();
		for (Joueur joueur : this.jeu.getJoueurs()) {
			Offre offre = joueur.getOffre();
			String carteVisible = (offre.getCarteVisible() != null) ? offre.getCarteVisible().getNom() : "Aucune";
			String carteCachee = (offre.getCarteCachee() != null) ? offre.getCarteCachee().getNom() : "Aucune";
			Jeu.vue.afficherOffreJoueur(joueur.nom, carteVisible, carteCachee);
		}
	}

	/**
	 * Gère la phase de prises du tour.
	 * <p>
	 * Le premier joueur à jouer est celui ayant la carte visible de plus grande valeur.
	 * Ensuite, chaque joueur prend une carte :
	 * </p>
	 * <ul>
	 *   <li>chez un adversaire (parmi les offres complètes disponibles)</li>
	 *   <li>ou dans sa propre offre si aucune offre adverse complète n'est disponible</li>
	 * </ul>
	 * <p>
	 * La méthode maintient la liste {@link #joueursAyantJoueCeTour} afin de savoir qui a joué.
	 * </p>
	 */
	public void gererPrises() {
	    int indiceJoueurCourant = joueurAvecLaPlusGrandeValeurVisible(); // Le premier a jouer est toujours celui avec la carte visible la plus élevée

	    // Boucle jusqu'à ce que tous les joueurs aient joué ce tour
	    while (this.joueursAyantJoueCeTour.size() < this.jeu.getJoueurs().size()) {
	    	// DEBUG AFFICHER LES MAINS DE TOUS LES JOUEURS
	    	for (Joueur j : this.jeu.getJoueurs()) {
	    		Jeu.vue.afficherMainJoueur(j.nom, j.getMain().toString());
	    	}

	        Joueur joueurCourant = this.jeu.getJoueurs().get(indiceJoueurCourant);
	        Jeu.vue.afficherTourJoueur(joueurCourant.nom);

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
	            Jeu.vue.afficherChoixDansPropreOffre();
	            Jeu.vue.afficherOptionCarteVisible(joueurCourant.getOffre().getCarteVisible().getNom());
	            Jeu.vue.afficherOptionCarteCachee();

	            int choix = -1;
				if (joueurCourant.isVirtuel()) {// On utilise des méthodes différentes entre joueur réel et virtuel pour le moment
					choix = ((JoueurVirtuel)joueurCourant).choisirPriseDeSoi(joueurCourant.getJest(), joueurCourant.getOffre());
					Jeu.vue.afficherChoixCarteJoueurVirtuel(joueurCourant.nom, choix);
				}
				else {
					choix = joueurCourant.choisirPrise(2);
				}
				joueurCourant.ajouterAsonJest(joueurCourant.donnerCarte(choix == 1));
	            this.joueursAyantJoueCeTour.add(joueurCourant.getNumJoueur());

	            break; // On a plus besoin de continuer la boucle principale car le tour est terminé. Un else aurait aussi pu faire l'affaire.
	        }

	        // On créé un arraylist des offres pour les joueurs virtuels
	        ArrayList<Offre> offresAdverse = new ArrayList<>();
	        // vider offres adverse au cas où
	        offresAdverse.clear();

	        // Afficher les offres disponibles des autres joueurs
	        for (int i = 0; i < this.jeu.getJoueurs().size(); i++) {
	            Joueur j = this.jeu.getJoueurs().get(i);
	            if (j != joueurCourant && j.getOffre().estComplete()) {
	                Offre offre = j.getOffre();
	                int idxVisible = indiceJoueur * 2 + 1;
	                int idxCache = indiceJoueur * 2 + 2;

	                offresAdverse.add(offre);

	                Jeu.vue.afficherOffreDeJoueur(j.nom);
	                Jeu.vue.afficherOptionCarteVisibleOffre(idxVisible, offre.getCarteVisible().getNom());
	                Jeu.vue.afficherOptionCarteCacheeOffre(idxCache);

	                joueurPourChoix.put(idxVisible, i);
	                joueurPourChoix.put(idxCache, i);
	                indiceJoueur++;
	            }
	        }

	        int numCarteChoisie = -1;
	        // Traiter le cas des joueurs virtuels
	        if (joueurCourant.isVirtuel()) {// On utilise des méthodes différentes entre joueur réel et virtuel pour le moment
	        	numCarteChoisie = ((JoueurVirtuel)joueurCourant).choisirPriseAdverse(joueurCourant.getJest(), offresAdverse);
				Jeu.vue.afficherChoixCarteJoueurVirtuelAdverse(joueurCourant.nom, numCarteChoisie);
			}
	        else{
	        	numCarteChoisie = joueurCourant.choisirPrise(indiceJoueur * 2 + 2);
	        	Jeu.vue.afficherChoixCarteJoueur(joueurCourant.nom, numCarteChoisie);
	        }

	        int prochainIndice = joueurPourChoix.get(numCarteChoisie);
	        // JoueurVole correspond au joueur qui s'est fait prendre une carte de son offre
	        Joueur joueurVole = this.jeu.getJoueurs().get(prochainIndice);
	        Jeu.vue.afficherJoueurSeFaitPrendreCarte(joueurVole.nom);

	        joueurCourant.ajouterAsonJest(joueurVole.donnerCarte(numCarteChoisie % 2 != 0));
	        this.joueursAyantJoueCeTour.add(joueurCourant.getNumJoueur());

	        // Déterminer le prochain joueur
	        indiceJoueurCourant = this.joueursAyantJoueCeTour.contains(joueurVole.getNumJoueur()) ? joueurAvecLaPlusGrandeValeurVisible() : prochainIndice;
	    }

	    // Afficher les jests, on désactive car c'est surtout pour debug, les joueurs ne sont pas censés connaitre les jests
	    /*
	    for (Joueur joueur : this.jeu.getJoueurs()) {
	        Jeu.vue.afficherJestDeJoueur(joueur.nom);
	        joueur.jest.afficher();
	    }
	    */
	}
}