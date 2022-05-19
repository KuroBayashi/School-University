
public class Tas<E extends NodeInterface> {

    private E[] values;
    private int lastIndex;

    private final static int DEFAULT_SIZE = 16;

    /*
     * Constructor
     */
    Tas() {
        this(DEFAULT_SIZE);
    }

    @SuppressWarnings("unchecked")
    Tas(int size) {
        this.values = (E[]) new NodeInterface[size];
        this.lastIndex = 0;
    }

    /*
     * Static Method
     */
    private static int getFatherIndex(int i) {
        return (i - 1) / 2;
    }

    private static int getLeftChildIndex(int i) {
        return 2 * i + 1;
    }

    private static int getRightChildIndex(int i) {
        return 2 * (i + 1);
    }

    /*
     * Method
     */
    private void swap(int index, int parentIndex) {
        int tmp = (this.values[index]);
    }

    private void sift() {

    }

    public Tas insert(Comparable element) {
        int parentIndex = Tas.getFatherIndex(this.lastIndex);

        this.values[this.lastIndex] = element;
        while (parentIndex >= 0 && this.values[parentIndex].compareTo(element) > 0) {

        }

        return this;
    }

    public Tas delete() {

        return this;
    }



}
