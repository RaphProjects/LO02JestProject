package jestPackage;
import java.util.*;
import java.io.Serializable;

public class JoueurVirtuel extends Joueur implements Serializable{
	private static final long serialVersionUID = 1L;
	private StrategieOffre strategyOffre;
	private StrategiePrise strategyPrise;
	public JoueurVirtuel(String nom, int numJoueur, int numStrategy) {
		this.nom = nom;
		this.numJoueur = numJoueur;
		this.strategyOffre = new StrategieOffreDeuxSurTrois();
		if (numStrategy == 1)
			this.strategyPrise = new StrategiePrisePrudente();
		else if (numStrategy == 2)
			this.strategyPrise = new StrategiePriseAleatoire();
		else {
			System.out.println("Numéro de stratégie invalide, on utilise la stratégie prudente par défaut.");
			this.strategyPrise = new StrategiePrisePrudente();
		}
		
	}
	
	public boolean isVirtuel() {
		return true;
	}
	
	public Offre getOffre() {
		return this.offre;
	}
	
	public void setOffre(Offre offreChoisie) {
		this.offre = offreChoisie;
	}
	
	public void deciderOffre() {
		this.setOffre(this.strategyOffre.choisirOffre(this.main));
		
		// On enlève les cartes de la main du joueur
		this.main.clear();
	}
	
	public int choisirPrise(int nbpossibilite) {
		// temporaire en attendant d'avoir des strategies pour l'ia
		return 0;
	}
	
	public int choisirPriseDeSoi(Jest jestDeSoi, Offre offreDeSoi) {
		return this.strategyPrise.choisirPriseDeSoi(jestDeSoi, offreDeSoi);
	}
	
	public int choisirPriseAdverse(Jest jestDeSoi, ArrayList<Offre> offresAdverse) {
		return this.strategyPrise.choisirPriseAdverse(this.jest, offresAdverse);
	}

	



}
