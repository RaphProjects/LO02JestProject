package jestPackage;
import java.util.*;
public class Jest {
	private ArrayList<Carte> cartes = new ArrayList<Carte>();
	
	public void ajouterCarte(Carte carte) {
		this.cartes.add(carte);
	}
	
	public void afficherJest() {
		System.out.println("Cartes dans le Jest :");
		for (Carte carte : cartes) {
			System.out.println("- " + carte.getNom());
		}
	}

	public void afficher() {
		for (Carte carte : cartes) {
            System.out.println("- " + carte.getNom());
        }

	}
	

}
