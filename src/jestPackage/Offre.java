package jestPackage;

public class Offre {
	private Carte carteVisible;
	private Carte carteCachee;
	
	public Offre() {
		this.carteVisible = null;
		this.carteCachee = null;
	}
	
	public Carte getCarteVisible() {
		return carteVisible;
	}
	
	public void setCarteVisible(Carte carteVisible) {
		this.carteVisible = carteVisible;
	}
	
	public Carte getCarteCachee() {
		return carteCachee;
	}
	
	public void setCarteCachee(Carte carteCachee) {
		this.carteCachee = carteCachee;
	}
	
	public boolean estComplete() {
		return this.carteVisible != null && this.carteCachee != null;
	}
	
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

}
