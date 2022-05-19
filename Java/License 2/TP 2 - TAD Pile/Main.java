public class Main {

  public static void main(String[] args) {
    // VERIF
    String str = "e<-sommet(empiler(empiler(d ́epiler(empiler(pile_vide(),a)),b),c))";
    if (Syntaxe.verif(str))
      System.out.println("VERIF : OK");
    else
      System.out.println("VERIF : FAIL");

    // EVAL
    try {
      String ev = "95+7-5/4+";
      int result = Postfixee.eval(ev);
      System.out.println(
        ev +
        " = " +
        result
      );
    } catch (ExceptionExpressionMalFormee e) {
      System.out.println("EVAL : " + e.getMessage());
    }

    // CONVERSION
    String expr = "2+(5-1)/4=";
    try {
      System.out.println(
        expr +
        " devient " +
        Postfixee.conversion(expr) +
        " = " +
        Postfixee.eval(Postfixee.conversion(expr))
      );
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

}
