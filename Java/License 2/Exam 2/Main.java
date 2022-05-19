import java.util.List;

public class Main {

    public static void main(String[] args) {
        Lexico l = new Lexico();

        l.ajouterMot("arbre");
        l.ajouterMot("bac");
        l.ajouterMot("barrer");
        l.ajouterMot("camion");
        l.ajouterMot("caler");
        l.ajouterMot("carte");
        l.ajouterMot("cale");
        l.ajouterMot("carton");
        l.ajouterMot("citer");
        l.ajouterMot("citron");
        l.ajouterMot("cintre");
        l.ajouterMot("cire");
        l.ajouterMot("cours");
        l.ajouterMot("court");
        l.ajouterMot("cou");

        List<String> list = l.suggestions("a");

        for (int i=0; i<list.size(); i++) {
            System.out.println(list.get(i));
        }


    }

}
