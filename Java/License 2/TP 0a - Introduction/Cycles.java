/**
 * Exercice 1 : Cycles
 */

public class Cycles {

  private String[] libelles = {"er", "nd", "ème"};
  private int[] durees = {3, 2, 3};

  private String getExtension(int i) {
    return (i == 0 || i == 1) ? libelles[i] : libelles[2];
  }

  public void show() {
    for (int i = 0; i < durees.length; i++) {
      System.out.println( String.format("Le %d%s cycle universitaire dure %d ans.", i+1, getExtension(i), durees[i]) );
    }
  }

  public static void main(String[] args) {
    Cycles c = new Cycles();
    c.show();
  }
}
