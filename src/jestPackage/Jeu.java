package jestPackage;
import java.util.*;

public class Jeu {
	private ArrayList<Joueur> joueurs = new ArrayList<Joueur>();
	private ArrayList<Carte> trophees = new ArrayList<Carte>();
	private Pioche pioche;
	private Tour tour;

	
	public Jeu() {
		this.pioche = new Pioche(this);
	}
	
	public ArrayList<Joueur> getJoueurs() {
        return this.joueurs;
    }
	
	public Pioche getPioche() {
		return this.pioche;
	}
	
	public void setTropheeJeu() {
		for (int i = 0; i < 2; i++) {
			this.trophees.add(pioche.piocher());
		}
	}
	
	public ArrayList<Carte> getTrophees() {
		return this.trophees;
	}
	
	
	public void initialiserJoueurs() {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Bienvenue dans le jeu Jest !");
		int nombreJoueurs=0;
		while (nombreJoueurs < 3 || nombreJoueurs > 4) {
			System.out.println("Entrez le nombre de joueurs (3 ou 4) : ");
			nombreJoueurs = scanner.nextInt();
			if (nombreJoueurs < 3 || nombreJoueurs > 4) {
				System.out.println("Nombre de joueurs invalide. Le jeu nécessite 3 ou 4 joueurs.");
			}
		}
		int compteurJoueur = 1;
		while (compteurJoueur <= nombreJoueurs) {
			System.out.println("Est-ce que le joueur " + compteurJoueur + " est un reel ou virtuel ? (0 pour reel et 1 pour virtuel) : ");
			int joueurIsVirtuel = scanner.nextInt();
			if (joueurIsVirtuel == 0) {
				System.out.println("Creation du joueur reel \"Joueur " + compteurJoueur + "\".");
				this.joueurs.add(new JoueurReel("Joueur " + compteurJoueur)) ;
				compteurJoueur++;
			} else if (joueurIsVirtuel == 1) {
				System.out.println("Creation du joueur virtuel \"Joueur " + compteurJoueur + "\".");
				this.joueurs.add(new JoueurVirtuel("Joueur " + compteurJoueur)) ;
				compteurJoueur++;
			} else {
				System.out.println("Valeur invalide. Veuillez entrer 0 pour reel ou 1 pour virtuel.");
			}
		}
	}
	
	public void initialiserPioche() {
		this.pioche.initialiserCartes();
		this.pioche.melangerCartes();
	}
	
	
	public static void main(String[] args) {
		Jeu jeuCourant = new Jeu();
		jeuCourant.initialiserJoueurs();
		jeuCourant.initialiserPioche();
		jeuCourant.setTropheeJeu();
		
		Tour tourCourant = new Tour(jeuCourant);
		
		/* 
		// Boucle de jeu principale, desactivée pour l'instant pour tester le reste
		while(jeuCourant.pioche.estPiochable()) {
			tourCourant.distribuerCartes();
			tourCourant.gererOffres();
			tourCourant.gererPrises();
			tourCourant.passerAuTourSuivant();
		}
		*/
		
		// Main de test temporaire pour tester les offres
		tourCourant.distribuerCartes();
		tourCourant.gererOffres();
		tourCourant.afficherOffres();
		tourCourant.gererPrises();
		
		
	}
	
	
}
