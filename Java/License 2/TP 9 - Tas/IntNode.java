public class IntNode implements NodeInterface<Integer> {

    private int value;

    IntNode(int v){
        this.value = v;
    }

    @Override
    public Integer getValue() {
        return this.value;
    }

    @Override
    public void setValue(Integer v) {
        this.value = v;
    }

    @Override
    public int compareTo(Object o) {
        return (this.value <= ((IntNode) o).value) ? 1 : -1;
    }
}
