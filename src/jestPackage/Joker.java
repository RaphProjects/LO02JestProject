package jestPackage;

public class Joker extends Carte{
	public Joker(String nom, Trophee bandeauTrophee) {
		super(nom, bandeauTrophee);
	}
	
	public String toString() {
		return "Joker[nom=" + nom + "]";
	}
	
	public int getValeurBase() {
		return 0;
	}

	public Couleur getCouleur() {
		return null;
	}
	public int getValeurCouleur() {
		return 0;
	}

}
