package jestPackage;
import java.util.*;


public class JoueurReel extends Joueur {
	public JoueurReel(String nom, int numJoueur) {
		this.nom = nom;
		this.numJoueur = numJoueur;
		
	}
	
	public boolean isVirtuel() {
		return false;
	}
	
	
	public Offre getOffre() {
		return this.offre;
	}

	public void deciderOffre() {
		Scanner scanner = new Scanner(System.in);
		
		System.out.println("Joueur " + this.nom + ", veuillez choisir votre offre.");
		System.out.println("Votre main :");
		for (int i = 0; i < this.main.size(); i++) {
			System.out.println((i + 1) + ": " + this.main.get(i).toString());
		}
		System.out.println("Entrez le numéro de la carte que vous souhaitez offrir visible: ");
		int numchoix = scanner.nextInt();
		while (numchoix < 1 || numchoix > this.main.size()) {
			if (numchoix < 1 || numchoix > this.main.size()) {
				System.out.println("Choix invalide. Veuillez entrer un numéro valide : ");
			}
			numchoix = scanner.nextInt();
		}
		System.out.println("Vous avez choisi de faire une offre avec la carte : " + this.main.get(numchoix-1).toString());
		
		// On met la carte choisie dans l'offre visible
		this.offre.setCarteVisible(this.main.get(numchoix-1));
		// On met la carte restante dans l'offre cachée
		this.offre.setCarteCachee(this.main.get( (numchoix == 1) ? 1 : 0 ));
		// On enlève les cartes de la main du joueur
		this.main.clear();
		
		System.out.print("Voulez vous nettoyer la console pour dissimuler votre choix ? (0 pour oui, 1 pour non) : ");
		int nettoyerConsole = scanner.nextInt();
		while (nettoyerConsole != 0 && nettoyerConsole != 1) {
			System.out.print("Valeur invalide. Veuillez entrer 0 pour oui ou 1 pour non : ");
			nettoyerConsole = scanner.nextInt();
		}
		if (nettoyerConsole == 0) {
			for (int i = 0; i < 50; i++) {
				System.out.println();
			}
		}
	}
	
	public int choisirPrise(int nbpossibilite) {
		Scanner scanner = new Scanner(System.in);
		System.out.println("Joueur " + this.nom + ", veuillez entrer le numéro de la carte que vous souhaitez prendre : ");
		int numchoix = scanner.nextInt();
		while (numchoix < 1 || numchoix > nbpossibilite) {
			if (numchoix < 1 || numchoix > nbpossibilite) {
				System.out.println("Choix invalide. Veuillez entrer un numéro valide : ");
			}
			numchoix = scanner.nextInt();
		}
		
		return numchoix;
		//temporaire

	}
}
