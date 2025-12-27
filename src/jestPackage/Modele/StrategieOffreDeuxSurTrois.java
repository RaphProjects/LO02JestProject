package jestPackage.Modele;

import java.util.*;
import java.util.Random;
import java.io.Serializable;
public class StrategieOffreDeuxSurTrois implements StrategieOffre, Serializable {
	private static final long serialVersionUID = 1L;
    
    public Offre choisirOffre(ArrayList<Carte> main) {
        // Trouver la carte la plus faible et la plus forte
        Carte carteFaible = main.get(0);
        Carte carteForte = main.get(1);
        
        if (carteFaible.getValeurBase() > carteForte.getValeurBase()) {
            // Échanger les références
            Carte temp = carteFaible;
            carteFaible = carteForte;
            carteForte = temp;
        }
        
        // 2 chances sur 3 de donner la carte faible en visible
        Random random = new Random();
        int tirage = random.nextInt(3);  // Génère 0, 1 ou 2
        
        if (tirage < 2) {
            // 2/3 des cas (tirage = 0 ou 1) : carte faible visible
        	
        	Jeu.vue.afficherCarteChoisieIA(carteFaible.toString());
            return new Offre(carteFaible, carteForte);
        } 
        else {
            // 1/3 des cas (tirage = 2) : carte forte visible
        	Jeu.vue.afficherCarteChoisieIA(carteForte.toString());
            return new Offre(carteForte, carteFaible);
        }
    }
}