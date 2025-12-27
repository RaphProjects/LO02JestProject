package jestPackage.Modele;
import java.io.Serializable;
public class TropheeIncolore extends Trophee implements Serializable{
	private static final long serialVersionUID = 1L;
	private ConditionIncolore condition;
	private int valeurAssociée = -1; // utile uniquement pour le trophée majorité
	
	public TropheeIncolore(ConditionIncolore condition) {
		this.condition = condition;
	}
	
	public TropheeIncolore(ConditionIncolore condition, int valeurAssociée) {
        this.condition = condition;
        this.valeurAssociée = valeurAssociée;
	}
	public boolean estTropheeCouleur() {
        return false;
    }
	
	public ConditionIncolore getCondition() {
		return condition;
	}
	
	public int getValeurAssociée() {
		return valeurAssociée;
	}
	
	public String toString() {
		if (valeurAssociée == -1) {
			return "TropheeIncolore[condition=" + condition + "]";
		}
		return "TropheeIncolore[condition=" + condition + ", valeurAssociée=" + valeurAssociée + "]";
	}
	
}
