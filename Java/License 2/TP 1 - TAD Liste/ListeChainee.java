public class ListeChainee implements Liste {

  private Maillon tete;

  ListeChainee() {
    this.tete = null;
  }

  @Override
  public int longueur() {
    int s = 0;
    Maillon c = this.tete;

    while (c != null) {
      s++;
      c = c.succ;
    }

    return s;
  }

  @Override
  public Object ieme(int indice) throws ExceptionIndiceHorsLimites {
    if (indice < 0 || indice >= this.longueur())
      throw new ExceptionIndiceHorsLimites(indice);

    Maillon m = this.tete;
    while (indice > 0) {
      indice--;
      m = m.succ;
    }

    return m.valeur;
  }

  @Override
  public void inserer(int indice, Object element) throws ExceptionIndiceHorsLimites {
    System.out.println(this.toString());
    if (indice < 0 || indice > this.longueur())
      throw new ExceptionIndiceHorsLimites(indice);

    Maillon c = this.tete;
    Maillon m = new Maillon();
    m.valeur = element;

    while (indice > 0) {
      c = c.succ;
      indice--;
    }

    if (c == null) {
      m.succ = null;
      this.tete = m;
    }
    else {
      m.succ = c.succ;
      c.succ = m;
    }
  }

  @Override
  public void supprimer(int indice) throws ExceptionIndiceHorsLimites {
    if (indice < 0 || indice >= this.longueur())
      throw new ExceptionIndiceHorsLimites(indice);

    Maillon c = this.tete;

    while (indice > 1) {
      c = c.succ;
      indice--;
    }

    if (indice == 0) {
      this.tete = c.succ;
    }
    else {
      c.succ = c.succ.succ;
    }
  }

  @Override
  public String toString() {
    Maillon c = this.tete;
    StringBuilder sb = new StringBuilder();

    sb.append("[");
    while (c != null) {
      sb.append(" ").append(c.valeur);
      c = c.succ;
    }
    sb.append(" ]");
    return sb.toString();
  }
}
