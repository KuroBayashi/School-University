package fr.cours.starter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Synthese {

    // Exercice 1
    private static ArrayList<String> dico = new ArrayList<>();
    private static String poeme = "Quand le ciel bas et lourd pèse comme un couvercle," +
            "Sur l'esprit gémissant en proie aux longs ennuis," +
            "Et que de l'horizon embrassant tout le cercle," +
            "II nous verse un jour noir plus triste que les nuits";

    // Exercice 2
    private static ArrayList<String> arche = new ArrayList<>();

    // Show All
    public void showAll(){
        this.showExo1();
        this.showExo2();
    }

    private void showExo1(){
        String explode[] = Synthese.poeme.split(",");

        for( String s: explode ){
            Matcher matcher = Pattern.compile("(\\b\\w{2,}\\b)").matcher(s);

            int count = 0;

            while( matcher.find() ){
                if( !Synthese.dico.contains(matcher.group(1)) ){
                    Synthese.dico.add(matcher.group(1));
                }
                else{
                    count++;
                }
            }

            System.out.println("Cours 4 - Exo 1 : " + count + " word(s) in (" + s + ")");
        }
    }

    private void showExo2(){
        String animaux[] = {"alligator", "antilope", "bouc", "caïman", "caribou", "cheval", "chevreuil", "éléphant", "grenouille", "hamster", "lapin", "manchot", "mérou", "perruche", "pingouin", "pintade", "rossignol", "rouge-gorge", "termite", "tigre", "tortue", "rhinocéros", "vache"};
        Collections.addAll(Synthese.arche, animaux);

        int size = Synthese.arche.size();

        for( int i=0; i<size; i++ ){
            String nom = Synthese.arche.get(i);

            for( int k=i+1; k<size; k++ ){
                String nom2 = Synthese.arche.get(k);

                if( nom.substring(nom.length()-3).equals( nom2.substring(0, 3)) ){
                    System.out.println("Cours 4 - Exo 2 : " + nom + " + " + nom2 + " = " + nom.substring(0, nom.length()-3) + nom2.substring(3));
                }
                else if( nom2.substring(nom2.length()-3).equals( nom.substring(0, 3)) ){
                    System.out.println("Cours 4 - Exo 2 : " + nom2 + " + " + nom + " = " + nom2.substring(0, nom2.length()-3) + nom.substring(3));
                }
            }
        }
    }
}
