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

}
