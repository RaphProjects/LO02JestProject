package jestPackage.Controleur;

import java.util.ArrayList;
import javax.swing.*;
import jestPackage.Vue.*;

/**
 * Implémentation graphique du contrôleur (à développer)
 */
public class ControleurGraphique implements IControleur {
    
    private VueGraphique vue;
    
    public ControleurGraphique(VueGraphique vue) {
        this.vue = vue;
    }
    
    @Override
    public int demanderNombreJoueurs() {
        String[] options = {"3 joueurs", "4 joueurs"};
        int choix = JOptionPane.showOptionDialog(null, 
            "Combien de joueurs ?", 
            "Nombre de joueurs",
            JOptionPane.DEFAULT_OPTION, 
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);
        return choix + 3; // Retourne 3 ou 4
    }
    
    // ... autres méthodes avec JOptionPane ou composants personnalisés
    
    @Override
    public int demanderTypeJoueur(int numeroJoueur) {
        String[] options = {"Joueur réel", "Joueur virtuel (IA)"};
        return JOptionPane.showOptionDialog(null,
            "Type du joueur " + numeroJoueur + " ?",
            "Configuration joueur",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null, options, options[0]);
    }

	@Override
	public int demanderStrategieJoueurVirtuel(int numeroJoueur) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int demanderExtension() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int demanderVariante() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int afficherMenuPrincipal() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int afficherMenuPause() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String demanderNomSauvegarde() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int demanderChoixSauvegarde(ArrayList<String> sauvegardes) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int demanderSauvegardeASupprimer(ArrayList<String> sauvegardes) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String attendreEntreePause() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int demanderCarteARetourner(int numeroJoueur, ArrayList<String> nomsCarte) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int demanderJoueurCible(int numeroJoueurActuel, ArrayList<Integer> joueursDisponibles) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int demanderTypeCarte(int numeroJoueurCible, String carteVisibleNom, boolean carteCacheeDisponible) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean demanderConfirmation(String message) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int demanderCarteVisibleOffre(String nomJoueur, ArrayList<String> cartes) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean demanderNettoyageConsole() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int demanderChoixPrise(String nomJoueur, int nbPossibilites, ArrayList<String> descriptionsChoix) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int demanderChoixPrise(String nomJoueur, int nbPossibilites) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}