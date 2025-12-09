package jestPackage;

public class JoueurVirtuel extends Joueur {
	public JoueurVirtuel(String nom) {
		this.nom = nom;
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
	
	public Carte choisirPrise() {
		// temporaire en attendant d'avoir des strategies pour l'ia
		return this.offre.getCarteVisible();
	}

}
