import java.util.Random;
import java.util.Vector;

/**
 * Exercice 3 - Tableaux dynamiques
 */
public class TableauDynamique {

  private Vector<Character> vect = new Vector();
  private Random rnd = new Random();
  private char[] caracteres = {'a', 'b', 'c', 'd', 'e'};


  public TableauDynamique() {
    for (int i = 0; i < 500; i++) {
      vect.add( caracteres[Math.abs(rnd.nextInt()) % 5] );
      System.out.println( String.format("ADD : Taille = %d, Capacité = %d", vect.size(), vect.capacity()) );
    }
    for (int i = 0; i < 300; i++) {
      vect.remove(0);
      vect.trimToSize();
      System.out.println( String.format("REMOVE : Taille = %d, Capacité = %d", vect.size(), vect.capacity()) );
    }
  }

  public static void main(String[] args) {
    TableauDynamique td = new TableauDynamique();
  }
}
