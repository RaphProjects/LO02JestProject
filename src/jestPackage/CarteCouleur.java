package jestPackage;

public class CarteCouleur extends Carte{
	private Couleur couleur;
	private int valeurBase;
	public CarteCouleur(String nom, Trophee bandeauTrophee, Couleur couleur, int valeurBase) {
		super(nom, bandeauTrophee);
		this.couleur = couleur;
		this.valeurBase = valeurBase;
	}
	
	public Couleur getCouleur() {
		return couleur;
	}
	
	public int getValeurBase() {
		return valeurBase;
	}
	
	public String toString() {
		return "CarteCouleur[nom=" + nom + ", couleur=" + couleur
				+ ", valeurBase=" + valeurBase + "]";
	}
	

}
