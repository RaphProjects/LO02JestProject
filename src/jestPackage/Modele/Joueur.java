package jestPackage.Modele;

import java.util.*;
import java.io.Serializable;

/**
 * Représente un joueur (abstrait) dans une partie de Jest.
 * <p>
 * Un joueur possède :
 * </p>
 * <ul>
 *   <li>un nom</li>
 *   <li>un {@link Jest} (cartes gagnées)</li>
 *   <li>une {@link Offre} (carte visible + carte cachée proposées pendant un tour)</li>
 *   <li>une main (cartes actuellement en main durant le tour)</li>
 *   <li>un numéro de joueur</li>
 * </ul>
 * <p>
 * Les sous-classes (ex. joueur réel / joueur virtuel) doivent définir la manière de
 * constituer une offre et de choisir une prise.
 * </p>
 */
public abstract class Joueur implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * Nom du joueur.
	 */
	protected String nom;

	/**
	 * Jest du joueur (ensemble des cartes remportées).
	 */
	protected Jest jest = new Jest();

	/**
	 * Offre courante du joueur.
	 */
	protected Offre offre = new Offre();

	/**
	 * Main courante du joueur (cartes manipulées pendant le tour).
	 */
	protected ArrayList<Carte> main = new ArrayList<Carte>();

	/**
	 * Numéro du joueur dans la partie.
	 */
	protected int numJoueur;

	/**
	 * Ajoute une carte à la main du joueur.
	 *
	 * @param carte la carte à ajouter
	 */
	public void prendreCarte(Carte carte) {
		this.main.add(carte);
	}

	/**
	 * Retourne l'offre courante du joueur.
	 *
	 * @return l'offre
	 */
	public abstract Offre getOffre();

	/**
	 * Demande au joueur de décider/constituer son offre (carte visible et carte cachée).
	 */
	public abstract void deciderOffre();

	/**
	 * Demande au joueur de choisir une prise parmi un nombre de possibilités.
	 *
	 * @param nbpossibilite nombre de possibilités de prise
	 * @return l'index/choix correspondant à la prise sélectionnée
	 */
	public abstract int choisirPrise(int nbpossibilite);

	/**
	 * Indique si le joueur est virtuel (IA) ou réel.
	 *
	 * @return {@code true} si le joueur est virtuel, {@code false} sinon
	 */
	public abstract boolean isVirtuel();

	/**
	 * Retourne le Jest du joueur.
	 *
	 * @return le Jest
	 */
	public Jest getJest() {
		return this.jest;
	}

	/**
	 * Retire et renvoie une carte de l'offre du joueur.
	 * <p>
	 * Si {@code estVisible} est vrai, la carte visible est donnée et retirée de l'offre,
	 * sinon la carte cachée.
	 * </p>
	 *
	 * @param estVisible {@code true} pour donner la carte visible, {@code false} pour donner la carte cachée
	 * @return la carte donnée (peut être {@code null} si aucune carte correspondante dans l'offre)
	 */
	public Carte donnerCarte(boolean estVisible) {
		Carte carteDonnee;
		if (estVisible) {
			carteDonnee = this.offre.getCarteVisible();
			this.offre.setCarteVisible(null);
		} else {
			carteDonnee = this.offre.getCarteCachee();
			this.offre.setCarteCachee(null);
		}
		return carteDonnee;
	}

	/**
	 * Ajoute une carte au Jest du joueur.
	 *
	 * @param carte la carte à ajouter
	 */
	public void ajouterAsonJest(Carte carte) {
		this.jest.ajouterCarte(carte);
	}

	/**
	 * Retourne le numéro du joueur.
	 *
	 * @return le numéro du joueur
	 */
	public int getNumJoueur() {
		return this.numJoueur;
	}

	/**
	 * Retourne le nom du joueur.
	 *
	 * @return le nom
	 */
	public String getNom() {
		return this.nom;
	}

	/**
	 * Retourne la main du joueur.
	 *
	 * @return la main (liste de cartes)
	 */
	public ArrayList<Carte> getMain() {
		return this.main;
	}

}