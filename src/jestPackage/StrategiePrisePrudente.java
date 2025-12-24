package jestPackage;
import java.util.*;
import java.io.Serializable;
public class StrategiePrisePrudente implements StrategiePrise, Serializable {
	private static final long serialVersionUID = 1L;
	// choisir la carte la plus élevée des offres proposées.
	
	public int choisirPriseAdverse(Jest jest, ArrayList<Offre> offresAdverse) {
		// On a deux cartes par offre, le nombre à retourner va de 1 à offreAdverse.size*2
		// On renvoie le numéro de la carte visible avec la plus haute valeur (qui sera donc = 1 modulo 2
		int valeurMax = -1;
		for (Offre offreCourante : offresAdverse) {
			if (offreCourante.getCarteVisible().getValeurBase() > valeurMax) {
				valeurMax = offreCourante.getCarteVisible().getValeurBase();
			}
		}
		// On cherche l'offre qui contient cette carte 
		
		//TEMPORAIRE : il faudrait gérer les égalités pour prendre la meilleure carte de couleur en cas d'égalité, pas juste la premiere
		for (int i = 0; i < offresAdverse.size(); i++) {
			Offre offreCourante = offresAdverse.get(i);
			if (offreCourante.getCarteVisible().getValeurBase() == valeurMax) {
				// On renvoie le numéro de la carte visible
				return i * 2 + 1;
			}
		}
		// On ne devrait jamais arriver ici
		return -1;
		
		
	}

	@Override
	public int choisirPriseDeSoi(Jest jestDeSoi, Offre offreDeSoi) {
		// On utilise pas le jest dans cette stratégie
		// on renvoie simplement 1 si la carte visible est plus grande et 2 sinon
		// On a acces à la carte cachée car c'est notre propre offre
		if(offreDeSoi.getCarteVisible().getValeurBase()>offreDeSoi.getCarteCachee().getValeurBase()) {
			return 1;
		}
		return 2;
		
	}
	



}
