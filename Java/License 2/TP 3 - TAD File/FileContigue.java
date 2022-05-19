import java.util.ArrayList;

class FileContigue extends ArrayList {

  boolean est_vide() {
    return this.isEmpty();
  }

  Object premier() throws ExceptionFileVide {
    if (this.est_vide())
      throw new ExceptionFileVide();

    return this.get(0);
  }

  FileContigue enfiler(Object e) {
    this.add(e);
    return this;
  }

  FileContigue defiler() throws ExceptionFileVide {
    if (this.est_vide())
      throw new ExceptionFileVide();

    this.remove(0);
    return this;
  }

}
