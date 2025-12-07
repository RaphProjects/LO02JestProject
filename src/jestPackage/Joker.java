package jestPackage;

public class Joker extends Carte{
	public Joker(String nom, Trophee bandeauTrophee) {
		super(nom, bandeauTrophee);
	}
	
	public String toString() {
		return "Joker[nom=" + nom + ", bandeauTrophee=" + bandeauTrophee + "]";
	}

}
