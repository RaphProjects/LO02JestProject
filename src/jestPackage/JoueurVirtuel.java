package jestPackage;

public class JoueurVirtuel extends Joueur {
	public JoueurVirtuel(String nom, int numJoueur) {
		this.nom = nom;
		this.numJoueur = numJoueur;
	}
	
	public boolean isVirtuel() {
		return true;
	}
	
	public Offre getOffre() {
		return this.offre;
	}
	
	public void deciderOffre() {
		//Offre offreChoisie = StrategieVirtuelle.choisirOffre(this.main);
		//this.offre = offreChoisie;
	}
	
	public int choisirPrise(int nbpossibilite) {
		// temporaire en attendant d'avoir des strategies pour l'ia
		return 0;
	}

}
