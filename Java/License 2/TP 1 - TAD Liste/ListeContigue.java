class ListeContigue implements Liste {

  private Object[] valeurs;
  private int taille;

  private final static int N = 5;

  ListeContigue() {
    this.valeurs = new Object[N];
    this.taille = 0;
  }

  @Override
  public int longueur() {
    return this.taille;
  }

  @Override
  public Object ieme(int indice) throws ExceptionIndiceHorsLimites {
    if (indice < 0 || indice >= this.taille)
      throw new ExceptionIndiceHorsLimites(indice);

    return this.valeurs[indice];
  }

  @Override
  public void inserer(int indice, Object element) throws ExceptionIndiceHorsLimites {
    if (indice < 0 || indice > this.taille)
      throw new ExceptionIndiceHorsLimites(indice);

    int j = this.taille;
    while (j > indice)
      this.valeurs[j] = this.valeurs[--j];
    this.valeurs[indice] = element;
    this.taille++;
  }

  @Override
  public void supprimer(int indice) throws ExceptionIndiceHorsLimites {
    if (indice < 0 || indice >= this.taille)
      throw new ExceptionIndiceHorsLimites(indice);

    while (indice < this.taille)
      this.valeurs[indice] = this.valeurs[++indice];
    this.taille--;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("[");
    for (int i = 0; i < this.taille; i++){
        sb.append(" ").append(this.valeurs[i]);
    }
    sb.append(" ]");
    return sb.toString();
  }
}
