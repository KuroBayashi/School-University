import java.util.ArrayList;
import java.util.Random;

public class ListeEntier extends ArrayList<Integer> {

    ListeEntier() {
        Random rand = new Random();

        for (int i = 1; i < Math.pow(10, 4); i++) {
            if (rand.nextBoolean())
                this.add(i);
        }
    }

    Boolean has(int n) {
        int inf = 0;
        int sup = this.size();
        int p;

        while (inf != sup -1) {
            p = (inf + sup) / 2;

            if (n >= this.get(p))
                inf = p;
            else
                sup = p;
        }

        return (this.get(inf) == n);
    }

}
