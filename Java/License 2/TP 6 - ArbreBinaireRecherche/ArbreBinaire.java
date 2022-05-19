/**
 * Implémentation du type ArbreBinaire + fonction ususelles
 * @author David Panzoli
 */
public class ArbreBinaire {

	protected Object racine;
	protected ArbreBinaire sag;
	protected ArbreBinaire sad;

	public ArbreBinaire() {
		this.racine = null;
		this.sag = null;
		this.sad = null;
	}

	public ArbreBinaire(Object r, ArbreBinaire g, ArbreBinaire d) {
		this.racine = r;
		this.sag = g;
		this.sad = d;
	}

	public boolean estVide() {
		return this.racine==null;
	}

	public Object racine() {
		if (this.estVide()) throw new ExceptionArbreVide();
		return this.racine;
	}

	public ArbreBinaire sag() {
		if (this.estVide()) throw new ExceptionArbreVide();
		return this.sag;
	}

	public ArbreBinaire sad() {
		if (this.estVide()) throw new ExceptionArbreVide();
		return this.sad;
	}

	public int taille() {
		if (this.estVide()) return 0;
		else return 1 + this.sag().taille() + this.sad().taille();
	}

	public int hauteur() {
		if (this.estVide()) return 0;
		else return 1 + Math.max(this.sag().hauteur(), this.sad().hauteur());
	}

}
