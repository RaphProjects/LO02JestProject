package jestPackage.Modele;
import java.io.Serializable;
public class TropheeCouleur extends Trophee implements Serializable{
	private static final long serialVersionUID = 1L;
	private OrdreTropheeCouleur ordre;
	private Couleur couleurDeCondition;
	
	public TropheeCouleur(OrdreTropheeCouleur ordre, Couleur couleurDeCondition) {
		this.ordre = ordre;
		this.couleurDeCondition = couleurDeCondition;
	}
	
	public boolean estTropheeCouleur() {
        return true;
    }
	
	public OrdreTropheeCouleur getOrdre() {
		return ordre;
	}

	public Couleur getCouleurDeCondition() {
		return couleurDeCondition;
	}
	
	public String toString() {
		return "TropheeCouleur[ordre=" + ordre + ", couleurDeCondition=" + couleurDeCondition + "]";
	}
}
