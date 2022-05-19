/**
 * Implémentation du type ArbreBinaire + fonction ususelles
 * @author David Panzoli
 */
class ArbreBinaire {

	private Object racine;
	private ArbreBinaire sag;
	private ArbreBinaire sad;

	ArbreBinaire() {
		this.racine = null;
		this.sag = null;
		this.sad = null;
	}

	ArbreBinaire(Object r, ArbreBinaire g, ArbreBinaire d) {
		this.racine = r;
		this.sag = g;
		this.sad = d;
	}

	public boolean estVide() {
		return this.racine==null;
	}

	Object racine() {
		if (this.estVide()) throw new ExceptionArbreVide();
		return this.racine;
	}

	ArbreBinaire sag() {
		if (this.estVide()) throw new ExceptionArbreVide();
		return this.sag;
	}

	ArbreBinaire sad() {
		if (this.estVide()) throw new ExceptionArbreVide();
		return this.sad;
	}

	int taille() {
		if (this.estVide()) return 0;
		else return 1 + this.sag().taille() + this.sad().taille();
	}

	int hauteur() {
		if (this.estVide()) return 0;
		else return 1 + Math.max(this.sag().hauteur(), this.sad().hauteur());
	}

}
