public class FilePriorite extends FileChaine {

  protected Object[] tampon;

  private void avancer() {

  }

  @Override
  FilePriorite enfiler(Object e) {
    Maillon m = new Maillon();
    m.value = e;

    return this;
  }
}
