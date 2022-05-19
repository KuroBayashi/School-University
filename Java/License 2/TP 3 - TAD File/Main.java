import java.util.Random;

public class Main {

  public static void main(String[] args) {
    FilePriorite fp = new FilePriorite();
    Forme[] formes = {new Cercle(), new Rectangle()};
    Random rnd = new Random();

    for (int i = 0; i < 30; i++) {
      fp.enfiler(formes[rnd.nextInt(2)]);
    }
  }

}
