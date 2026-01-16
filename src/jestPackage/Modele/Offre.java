package jestPackage.Modele;

import java.io.Serializable;

/**
 * Représente une offre de cartes (une carte visible et une carte cachée).
 * <p>
 * Une offre est considérée complète lorsqu'elle contient à la fois une carte visible et une carte cachée.
 * Elle peut également être incomplète (une seule carte), notamment lors de la récupération de la carte restante.
 * </p>
 */
public class Offre implements Serializable {
	private static final long serialVersionUID = 1L;

	private Carte carteVisible;
	private Carte carteCachee;

	/**
	 * Construit une offre vide (sans carte visible ni carte cachée).
	 */
	public Offre() {
		this.carteVisible = null;
		this.carteCachee = null;
	}

	/**
	 * Construit une offre avec une carte visible et une carte cachée.
	 *
	 * @param carteVisible la carte visible de l'offre
	 * @param carteCachee la carte cachée de l'offre
	 */
	public Offre(Carte carteVisible, Carte carteCachee) {
		this.carteVisible = carteVisible;
		this.carteCachee = carteCachee;

	}

	/**
	 * Renvoie la carte visible de l'offre.
	 *
	 * @return la carte visible (peut être {@code null})
	 */
	public Carte getCarteVisible() {
		return carteVisible;
	}

	/**
	 * Définit la carte visible de l'offre.
	 *
	 * @param carteVisible la carte visible à définir
	 */
	public void setCarteVisible(Carte carteVisible) {
		this.carteVisible = carteVisible;
	}

	/**
	 * Renvoie la carte cachée de l'offre.
	 *
	 * @return la carte cachée (peut être {@code null})
	 */
	public Carte getCarteCachee() {
		return carteCachee;
	}

	/**
	 * Définit la carte cachée de l'offre.
	 *
	 * @param carteCachee la carte cachée à définir
	 */
	public void setCarteCachee(Carte carteCachee) {
		this.carteCachee = carteCachee;
	}

	/**
	 * Indique si l'offre est complète (carte visible et carte cachée non nulles).
	 *
	 * @return {@code true} si l'offre est complète, sinon {@code false}
	 */
	public boolean estComplete() {
		return this.carteVisible != null && this.carteCachee != null;
	}

	/**
	 * Renvoie l'unique carte restante lorsqu'une seule des deux cartes est présente dans l'offre,
	 * et la retire de l'offre (la référence correspondante est mise à {@code null}).
	 * <p>
	 * Si l'offre contient deux cartes ou aucune carte, cette méthode renvoie {@code null}.
	 * </p>
	 *
	 * @return la carte restante si l'offre est incomplète (une seule carte), sinon {@code null}
	 */
	public Carte getCarteRestante() {
		Carte carteRestante;
		if (this.carteVisible != null && this.carteCachee == null) {
			carteRestante = this.carteVisible;
			this.carteVisible = null;
			return carteRestante;
		} else if (this.carteCachee != null && this.carteVisible == null) {
			carteRestante = this.carteCachee;
			this.carteCachee = null;
			return carteRestante;
		} else {
			return null;
		}
	}

	/**
	 * Représentation textuelle de l'offre (principalement utilisée pour le debug).
	 *
	 * @return une description de l'offre
	 */
	public String toString() { //pour debug
		String str = "Offre : \n";
		str += " - Carte Visible : " + (carteVisible != null ? carteVisible.getNom() : "Aucune") + "\n";
		str += " - Carte Cachée : " + (carteCachee != null ? carteCachee.getNom() : "Aucune") + "\n";
		return str;
	}

}