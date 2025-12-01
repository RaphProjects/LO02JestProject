package jestPackage;

public class JoueurVirtuel extends Joueur {
	public JoueurVirtuel(String nom) {
		this.nom = nom;
	}
	
	public boolean isVirtuel() {
		return true;
	}

}
