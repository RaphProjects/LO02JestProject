package jestPackage.Modele;
import java.util.*;
import java.io.Serializable;

public class JoueurReel extends Joueur implements Serializable{
	private static final long serialVersionUID = 1L;
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

		Jeu.vue.afficherChoixOffreJoueur(this.nom);
		Jeu.vue.afficherMainJoueur();
		for (int i = 0; i < this.main.size(); i++) {
			Jeu.vue.afficherCarteMain(i, this.main.get(i).toString());
		}
		Jeu.vue.afficherDemandeCarteVisible();
		int numchoix = scanner.nextInt();
		while (numchoix < 1 || numchoix > this.main.size()) {
			if (numchoix < 1 || numchoix > this.main.size()) {
				Jeu.vue.afficherChoixInvalide();
			}
			numchoix = scanner.nextInt();
		}
		Jeu.vue.afficherCarteChoisiePourOffre(this.main.get(numchoix-1).toString());

		// On met la carte choisie dans l'offre visible
		this.offre.setCarteVisible(this.main.get(numchoix-1));
		// On met la carte restante dans l'offre cachée
		this.offre.setCarteCachee(this.main.get( (numchoix == 1) ? 1 : 0 ));
		// On enlève les cartes de la main du joueur
		this.main.clear();

		Jeu.vue.demanderNettoyageConsole();
		int nettoyerConsole = scanner.nextInt();
		while (nettoyerConsole != 0 && nettoyerConsole != 1) {
			Jeu.vue.afficherValeurInvalideNettoyageConsole();
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
		Jeu.vue.afficherDemandePriseCarte(this.nom);
		int numchoix = scanner.nextInt();
		while (numchoix < 1 || numchoix > nbpossibilite) {
			if (numchoix < 1 || numchoix > nbpossibilite) {
				Jeu.vue.afficherChoixInvalidePrise();
			}
			numchoix = scanner.nextInt();
		}

		return numchoix;

	}
}
