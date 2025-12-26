package jestPackage;
import java.io.Serializable;
public abstract class Carte implements CarteElement, Serializable {
	private static final long serialVersionUID = 1L;
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
	
	public abstract int getValeurBase();// correspond à la face value de l'énoncé
	public abstract Couleur getCouleur();
	public abstract int getValeurCouleur(); // utilisé pour gérer les égalités
	public abstract boolean estCouleur(); 
	
	
}
