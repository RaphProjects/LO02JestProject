package jestPackage;
import java.util.*;

public class Jeu {
	private Joueur[] joueurs;
	private Pioche pioche;
	private Tour tour;
	
	public Jeu() {
		this.pioche = new Pioche(this);
	}
	
	public Joueur[] getJoueurs() {
        return this.joueurs;
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
				this.joueurs[compteurJoueur-1] = new JoueurReel("Joueur " + compteurJoueur);
				compteurJoueur++;
			} else if (joueurIsVirtuel == 1) {
				System.out.println("Creation du joueur virtuel \"Joueur " + compteurJoueur + "\".");
				this.joueurs[compteurJoueur-1] = new JoueurVirtuel("Joueur " + compteurJoueur);
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
		Tour tourCourant = new Tour(jeuCourant);
		while(jeuCourant.pioche.estPiochable()) {
			tourCourant.distribuerCartes();
			tourCourant.gererOffres();
			tourCourant.gererPrises();
			tourCourant.passerAuTourSuivant();
			
		}
		
		
		
	}
	
	
}
