class Syntaxe {

  static boolean verif(String expr) {
    Pile p = new Pile();

    char c, l;
    for (int i = 0; i < expr.length(); i++) {
      c = expr.charAt(i);

      if (c == '(' || c == '[' || c == '{')
        p.empiler(c);
      else if (c == ')' || c == ']' || c == '}') {
        if (p.est_vide())
          return false;

        try {
          l = (char) p.sommet();

          if ((c == ')' && l == '(') || (c == ']' && l == '[') || (c == '}' && l == '{') )
            p.depiler();
          else
            return false;
        }
        catch (Exception e) {
          System.out.println(e.getMessage());
        }
      }
    }

    return p.est_vide();
  }

}
