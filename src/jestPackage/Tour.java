package jestPackage;

public class Tour {
	private int numeroTour=0;
	private Jeu jeu;

	public Tour(Jeu jeu) {
		this.jeu = jeu;
		this.numeroTour = 1;
	}

	public void passerAuTourSuivant() {
        numeroTour++;
    }

	public void distribuerCartes() {
		
	}
	
	public void gererOffres() {
		
	}
	
	public void gererPrises() {
		
	}
	
	
}
