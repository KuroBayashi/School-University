class ExceptionExpressionMalFormee extends Exception {

  ExceptionExpressionMalFormee(String str) {
    super("Expression mal formée : " + str);
  }
}

class Postfixee {

  static int eval(String expr) throws ExceptionExpressionMalFormee {
    Pile p = new Pile();

    char c;
    int a, b;
    for (int i = 0; i < expr.length(); i++) { // while (expr.charAt(i) != '=')
      c = expr.charAt(i);

      if (c >= '0' && c <= '9')
        p.empiler(Character.getNumericValue(c));
      else {
        try {
          b = (int) p.sommet();
          p.depiler();
          a = (int) p.sommet();
          p.depiler();

          switch (c) {
            case '+':
              p.empiler(a + b);
              break;
            case '-':
              p.empiler(a - b);
              break;
            case '*':
              p.empiler(a * b);
              break;
            case '/':
              p.empiler(a / b); // b == 0 ?
              break;
            default:
              throw new Exception("Invalid operator");
          }
        } catch(Exception e) {
          throw new ExceptionExpressionMalFormee(e.getMessage());
        }
      }
    }

    try {
      int r = (int) p.sommet();
      p.depiler();

      if (p.est_vide())
        return r;

      throw new Exception("Too much digits compared to operators");
    } catch (Exception e) {
      throw new ExceptionExpressionMalFormee(e.getMessage());
    }
  }

  private static int priorite(char c) {
    switch (c) {
      case '+':
      case '-':
        return 1;
      case '*':
      case '/':
        return 2;
      default:
        return 0;
    }
  }

  static String conversion(String expr) throws Exception {
    Pile p = new Pile();
    StringBuilder sb = new StringBuilder();

    int i = 0;
    char c = expr.charAt(i);

    while (c != '=') {
      if (c >= '0' && c <= '9')
        sb.append(c);
      else if (c == '(')
        p.empiler(c);
      else if (c == ')') {
        while ( (char) p.sommet() != '(') {
          sb.append(p.sommet());
          p.depiler();
        }
        p.depiler();
      }
      else {
        while (!p.est_vide() && Postfixee.priorite((char) p.sommet()) >= priorite(c)) {
          sb.append((char) p.sommet());
          p.depiler();
        }
        p.empiler(c);
      }
      c = expr.charAt(++i);
    }

    while (!p.est_vide()) {
      sb.append(p.sommet());
      p.depiler();
    }

    return sb.toString();
  }
}
