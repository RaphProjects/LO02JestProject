package jestPackage;
import java.io.Serializable;
public abstract class Trophee implements Serializable{
	private static final long serialVersionUID = 1L;
	public abstract boolean estTropheeCouleur();
	public abstract String toString();
}
