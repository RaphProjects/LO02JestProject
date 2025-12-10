package jestPackage;
import java.util.*;

public abstract class Joueur {
	protected String nom;
	protected Jest jest = new Jest();
	protected Offre offre = new Offre();
	protected ArrayList<Carte> main = new ArrayList<Carte>();
	protected int numJoueur;
	
	public void prendreCarte(Carte carte) {
		this.main.add(carte);
	}
	
	public abstract Offre getOffre();
	public abstract void deciderOffre();
	public abstract int choisirPrise(int nbpossibilite);
	
	public Carte donnerCarte(boolean estVisible) {
		Carte carteDonnee;
		if (estVisible) {
			carteDonnee = this.offre.getCarteVisible();
			this.offre.setCarteVisible(null);
		} else {
			carteDonnee = this.offre.getCarteCachee();
			this.offre.setCarteCachee(null);
		}
		return carteDonnee;
	}

	public void ajouterAsonJest(Carte carte) {
		this.jest.ajouterCarte(carte);
	}
	
	public int getNumJoueur() {
		return this.numJoueur;
	}
}
