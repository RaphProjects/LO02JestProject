package jestPackage;

public abstract class Joueur {
	protected String nom;
	protected Jest jest;
	protected Offre offre;
	protected Carte[] main;
	
	public void setMain(Carte[] main) {
		this.main = main;
	}

}
