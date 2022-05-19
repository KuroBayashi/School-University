public class TestListe {

    public static void main(String[] args) {
      Liste l;
      l = new ListeChainee();
      test1(l);
      System.out.println();
      l = new ListeChainee();
      test2(l);
      System.out.println();
      l = new ListeChainee();
      test3(l);
      System.out.println();
      l = new ListeChainee();
      test4(l);
      System.out.println();
      l = new ListeChainee();
      test5(l);
      System.out.println();
      l = new ListeChainee();
      test6(l);
      System.out.println();
    }

    /**
     * On ne cherche qu'à valider le fonctionnement de la structure.
     * On évite donc les cas problématiques
     */
    public static void test1(Liste l) {
		try {
			l.inserer(0, 'b');
			System.out.println("Liste : " + l.toString());
			l.inserer(1, 'c');
			l.inserer(2, 'd');
			l.inserer(0, 'a');
			System.out.println("Liste : " + l.toString());
			System.out.println("1er élément : " + l.ieme(1));
			System.out.println("3eme élément : " + l.ieme(3));
			System.out.println("Longueur de la liste : " + l.longueur());
			l.supprimer(2);
			l.supprimer(0);
			l.inserer(1, 'e');
			System.out.println("Liste : " + l.toString());
		} catch (ExceptionIndiceHorsLimites e) {
			System.out.println(e.getMessage());
		}
    }

    /*
     * Dans les fonctions qui suivent, on teste le 
     * comportement de la liste dans les cas particuliers
     */
    
    public static void test2(Liste l) {
		try {
			l.inserer(1, 'a');
			System.out.println("Liste : "+l.toString());
		} catch (ExceptionIndiceHorsLimites e) {
			System.out.println(e.getMessage());
		}
    }

    public static void test3(Liste l) {
		try {
			l.inserer(15, 'a');
			System.out.println("Liste : "+l.toString());
		} catch (ExceptionIndiceHorsLimites e) {
			System.out.println(e.getMessage());
		}
    }

    public static void test4(Liste l) {
		try {
			l.inserer(0, 'a');
			l.inserer(1, 'b');
			l.inserer(2, 'c');
			System.out.println("4ème élément : "+l.ieme(3));
		} catch (ExceptionIndiceHorsLimites e) {
			System.out.println(e.getMessage());
		}
    }

    public static void test5(Liste l) {
		try {
			l.inserer(0, 'a');
			l.inserer(1, 'b');
			l.inserer(2, 'c');
			l.supprimer(4);
			System.out.println("Liste : "+l.toString());
		} catch (ExceptionIndiceHorsLimites e) {
			System.out.println(e.getMessage());
		}
    }
    
    public static void test6(Liste l) {
		try {
			l.supprimer(-1);
			System.out.println("Liste : "+l.toString());
		} catch (ExceptionIndiceHorsLimites e) {
			System.out.println(e.getMessage());
		}
    }
 
}
