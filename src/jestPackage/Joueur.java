package jestPackage;
import java.util.*;

public abstract class Joueur {
	protected String nom;
	protected Jest jest = new Jest();
	protected Offre offre = new Offre();
	protected ArrayList<Carte> main = new ArrayList<Carte>();
	
	public void prendreCarte(Carte carte) {
		this.main.add(carte);
	}
	
	public abstract Offre getOffre();
	public abstract void deciderOffre();
	public abstract Carte choisirPrise();
	
}
