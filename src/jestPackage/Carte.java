package jestPackage;

public abstract class Carte implements CarteElement {
	protected String nom;
	protected Trophee bandeauTrophee;
	
	public Carte(String nom, Trophee bandeauTrophee) {
		this.nom = nom;
		this.bandeauTrophee = bandeauTrophee;
	}

	public String getNom() {
		return nom;
	}

	public Trophee getBandeauTrophee() {
		return bandeauTrophee;
	}

}
