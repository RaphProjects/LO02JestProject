package jestPackage.Modele;

import java.util.ArrayList;
import java.io.Serializable;
public interface StrategiePrise {
	public int choisirPriseAdverse(Jest jest, ArrayList<Offre> offresAdverse);

	public int choisirPriseDeSoi(Jest jestDeSoi, Offre offreDeSoi);
}
