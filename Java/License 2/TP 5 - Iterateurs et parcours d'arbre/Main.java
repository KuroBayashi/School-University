import java.util.ArrayList;

public class Main {

  ArrayList<Object> r = new ArrayList<>();

  private void parcoursProfondeurInfixe(ArbreBinaire a){
    if(!a.sag().estVide())
      parcoursProfondeurInfixe(a.sag());
    System.out.print(a.racine());
    if(!a.sad().estVide())
      parcoursProfondeurInfixe(a.sad());
  }

  private ArrayList<Object> parcoursProfondeurPrefixe(ArbreBinaire a){
    ArrayList<Object> r = new ArrayList<>();
    Pile p = new Pile(a.taille());
    ArbreBinaire n;

    p.empiler(a);
    while (!p.estVide()) {
      n = (ArbreBinaire) p.sommet();
      p.dépiler();
      r.add(n.racine());
      if (!n.sag().estVide())
        p.empiler(n.sag());
      if (!n.sad().estVide())
        p.empiler(n.sad());
    }

    return r;
  }

  private ArrayList<Object> parcoursLargeur(ArbreBinaire a){
    ArrayList<Object> r = new ArrayList<>();
    File f = new FileChaînée();
    ArbreBinaire n;

    f.enfiler(a);
    while (!f.estVide()) {
      n = (ArbreBinaire) f.premier();
      f.défiler();
      r.add(n.racine());
      if (!n.sag().estVide())
        f.enfiler(n.sag());
      if (!n.sad().estVide())
        f.enfiler(n.sad());
    }
  }

  private void iterate() {

  }

  public static void main(String[] args){
    ArbreBinaire expr = new ArbreBinaire('+',
        new ArbreBinaire('-',
            new ArbreBinaire('5', new ArbreBinaire(), new ArbreBinaire()),
            new ArbreBinaire('*',
                new ArbreBinaire('2', new ArbreBinaire(), new ArbreBinaire()),
                new ArbreBinaire('6', new ArbreBinaire(), new ArbreBinaire())
            )
        ),
        new ArbreBinaire('*',
            new ArbreBinaire('+',
                new ArbreBinaire('5', new ArbreBinaire(), new ArbreBinaire()),
                new ArbreBinaire('/',
                    new ArbreBinaire('6', new ArbreBinaire(), new ArbreBinaire()),
                    new ArbreBinaire('2', new ArbreBinaire(), new ArbreBinaire())
                )
            ),
            new ArbreBinaire('3', new ArbreBinaire(), new ArbreBinaire())
        )
    );

    Main m = new Main();
    m.parcoursProfondeurInfixe(expr);
  }
}
