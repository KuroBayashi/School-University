public class Pile {

    private Object[] valeurs;
    private int sommet;
    
    public Pile(int taille_initiale) {
	this.valeurs = new Object[taille_initiale];
	this.sommet = -1;
    }
    
    public boolean estVide() {
	return this.sommet==-1;
    }
    
    public void empiler(Object o) {
	this.sommet++;
	this.valeurs[this.sommet] = o;
    }
    
    public void dépiler() throws ExceptionPileVide {
	if (this.sommet<0) throw new ExceptionPileVide();
	this.sommet--;
    }
    
    public Object sommet() throws ExceptionPileVide {
	if (this.sommet<0) throw new ExceptionPileVide();
	return this.valeurs[this.sommet];
    }
    
    public String toString() {
	String out = "[";
	for (int i=0; i<this.sommet+1; i++) {
	    out += this.valeurs[i] + " ";
	}
	out += "]";
	return out;
    }
}
