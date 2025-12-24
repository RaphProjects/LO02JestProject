package jestPackage;

import java.util.*;

import java.io.Serializable;
public class StrategiePriseAleatoire implements StrategiePrise, Serializable {
	private static final long serialVersionUID = 1L;
	// choisir la carte la plus élevée des offres proposées.
	
	public int choisirPriseAdverse(Jest jest, ArrayList<Offre> offresAdverse) {
		// On a deux cartes par offre, le nombre à retourner va de 1 à offreAdverse.size*2
		Random rand = new Random();
		return rand.nextInt(offresAdverse.size()*2) + 1;
		
		
		
		
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

