package jestPackage;

public class TropheeCouleur extends Trophee{
	private OrdreTropheeCouleur ordre;
	private Couleur couleurDeCondition;
	
	public TropheeCouleur(OrdreTropheeCouleur ordre, Couleur couleurDeCondition) {
		this.ordre = ordre;
		this.couleurDeCondition = couleurDeCondition;
	}
}
