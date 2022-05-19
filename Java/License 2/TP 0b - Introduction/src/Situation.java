import java.util.ArrayList;
import java.util.List;

class Situation {

  private String description;
  List<Choix> choix = new ArrayList<>();

  Situation ajouterChoix(Choix c) {
    this.choix.add(c);
    return this;
  }

  public boolean estFinale() {
    return this.choix.isEmpty();
  }

  Situation(String desc) {
    this.description = desc;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append(this.description);
    sb.append("\n");

    int i = 0;
    for (Choix c : this.choix) {
      sb.append("[");
      sb.append(i++);
      sb.append("] ");
      sb.append( c.description );
      sb.append("\n");
    }

    return sb.toString();
  }
}
