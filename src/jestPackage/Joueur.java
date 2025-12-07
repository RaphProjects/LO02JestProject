package jestPackage;
import java.util.*;

public abstract class Joueur {
	protected String nom;
	protected Jest jest;
	protected Offre offre;
	protected ArrayList<Carte> main;
	
	public void prendreCarte(Carte carte) {
		this.main.add(carte);
	}
	
}
