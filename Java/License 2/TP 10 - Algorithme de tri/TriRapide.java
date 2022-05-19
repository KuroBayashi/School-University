import java.util.ArrayList;

public class TriRapide extends ArrayList<Integer> {

    public TriRapide sort(int inf, int sup) {
        int index = fragment(inf, sup);
        if (inf < index - 1)
            sort(inf, index - 1);
        if (index < sup)
            sort(index, sup);

        return this;
    }

    private int fragment(int inf, int sup) {
        int tmp;
        int pivot = this.get((inf + sup) / 2);

        while (inf <= sup) {
            while (this.get(inf) < pivot)
                inf++;
            while (this.get(sup) > pivot)
                sup--;
            if (inf <= sup) {
                tmp = this.get(inf);
                this.set(inf, this.get(sup));
                this.set(sup, tmp);
                inf++;
                sup--;
            }
        }

        return inf;
    }

}
