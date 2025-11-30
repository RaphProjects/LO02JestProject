package jestPackage;

public class CarteCouleur extends Carte{
	private Couleur couleur;
	private int valeurBase;
	public CarteCouleur(String nom, Trophee bandeauTrophee, Couleur couleur, int valeurBase) {
		super(nom, bandeauTrophee);
		this.couleur = couleur;
		this.valeurBase = valeurBase;
	}
	

}
