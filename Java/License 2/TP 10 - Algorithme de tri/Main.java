import java.util.Date;
import java.util.Random;

public class Main {

    public static void main(String[] args) {
        System.out.println("" +
                "----------------------------------------\n" +
                "-- Exercice 1 --------------------------\n" +
                "----------------------------------------\n"
        );

        Random rand = new Random();
        ListeEntier le = new ListeEntier();

        // Exo 1 : Quest 2
        System.out.println("1) La liste contient : " + le.size());

        // Exo 1 : Quest 3
        int n;
        double success_rate = 100;
        long time_start, time_end, time_full = 0;
        boolean exist;

        for (int i = 0; i < 1000; i++) {
            n = rand.nextInt(10000);

            time_start = System.currentTimeMillis();
                exist = le.contains(n);
            time_end = System.currentTimeMillis();

            time_full += time_end - time_start;

            if (exist)
                success_rate -= 0.1;
        }

        System.out.println("2) Le taux de succès est de " + Math.round(success_rate * 100) / 100f + "% et le temps total est de " + time_full + "ms");

        // Exo 1 : Quest 4
        success_rate = 100;
        time_full = 0;

        for (int i = 0; i < 1000; i++) {
            n = rand.nextInt(10000);

            time_start = System.currentTimeMillis();
                exist = le.has(n);
            time_end = System.currentTimeMillis();

            time_full += time_end - time_start;

            if (exist)
                success_rate -= 100/1000f;
        }

        System.out.println("3) Le taux de succès est de " + Math.round(success_rate * 100) / 100f + "% et le temps total est de " + time_full + "ms");

        // --------------------------------------------------------------------

        System.out.println("\n" +
                "----------------------------------------\n" +
                "-- Exercice 2 --------------------------\n" +
                "----------------------------------------\n"
        );

        String str = "les sanglots longs des violons de l'automne blessent mon coeur d'une langueur monotone";

        System.out.println("1) Text : \"" + str + "\"\n");
        System.out.println("3) Text sorted : \"" + TriSelection.sort(str) + "\"");

        // --------------------------------------------------------------------

        System.out.println("\n" +
                "----------------------------------------\n" +
                "-- Exercice 3 --------------------------\n" +
                "----------------------------------------\n"
        );

        TriRapide tr = new TriRapide();

        for (int i = 0; i < 30; i++) {
            tr.add(rand.nextInt(100));
        }

        StringBuilder sb = new StringBuilder();

        for (Integer i : tr) {
            sb.append(i);
            sb.append(", ");
        }

        System.out.println("1) List : [" + sb.toString().substring(0, sb.toString().length() - 2) + "]");

        tr.sort(0, tr.size()-1);
        sb.setLength(0);

        for (Integer i : tr) {
            sb.append(i);
            sb.append(", ");
        }

        System.out.println("3) List sorted : [" + sb.toString().substring(0, sb.toString().length() - 2) + "]");


    }

}
