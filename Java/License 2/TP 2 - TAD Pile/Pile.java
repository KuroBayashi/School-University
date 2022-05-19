import java.util.Vector;

public class Pile {

  private Vector<Object> valeurs;
  private int sommet;

  Pile() {
    this.valeurs = new Vector<>();
    this.sommet = -1;
  }

  boolean est_vide() {
    return (this.sommet == -1);
  }

  Object sommet() throws Exception {
    if (est_vide())
      throw new Exception("Pile is empty");

    return this.valeurs.get(this.sommet);
  }

  void empiler(Object e) {
    this.valeurs.add(e);
    ++this.sommet;
  }

  void depiler() throws Exception {
    if (est_vide())
      throw new Exception("Pile is empty");

    this.valeurs.remove(this.sommet);
    --this.sommet;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    for(Object o: valeurs) {
      sb.append(o);
    }
    //sb.append("\t\t");
    //sb.append(sommet);
    return sb.toString();
  }
}
