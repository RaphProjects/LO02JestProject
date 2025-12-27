package jestPackage.Modele;
import java.io.Serializable;
public class Joker extends Carte implements Serializable{
	private static final long serialVersionUID = 1L;
	private String nom = "Joker";
	public Joker(String nom, Trophee bandeauTrophee) {
		super("Joker", bandeauTrophee);
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
	public boolean estCouleur() {
		return false;
	}

	public int accept(VisiteurDeCarte visiteur) {
		return visiteur.visit(this);
	}
}
