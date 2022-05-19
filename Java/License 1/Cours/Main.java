import fr.cours.starter.*;
import fr.cours.controle.*;
import fr.cours.awt.*;

public class Main {

    public static void main(String[] args) {
        // Starter
        //showStarter();

        // Awt
        //showAwt();

        // Controle
        //showControle();
    }

    private static void showStarter(){
        // Cours 1
        Cours_1 c1 = new Cours_1();
        //c1.showAll();

        // Cours 2
        Cours_2 c2 = new Cours_2();
        //c2.showAll();

        // Cours 3
        Cours_3 c3 = new Cours_3();
        //c3.showAll();

        // Cours 4
        Cours_4 c4 = new Cours_4();
        //c4.showAll();

        // Synthese
        Synthese synt = new Synthese();
        synt.showAll();
    }

    private static void showAwt(){
        // Arrows GameController
        //Cours_5_1 c51 = new Cours_5_1();

        // Paint Circles
        //Cours_5_2 c52 = new Cours_5_2();

        // Move Block
        //Cours_5_3 c53 = new Cours_5_3();

        // Color Buttons
        Cours_5_4 c54 = new Cours_5_4();
    }

    private static void showControle(){
        // Segments
        //Controle_1 c1 = new Controle_1();

        // Matrice GameController
        Controle_2 c2 = new Controle_2();
    }

}

