public class Main {

    public static void main(String[] args) {
        Tas t = new Tas<IntNode>();

        t.insert(new IntNode(15))
            .insert(new IntNode(5))
            .insert(new IntNode(1))
            .insert(new IntNode(1))
            .insert(new IntNode(6))
            .insert(new IntNode(2))
            .insert(new IntNode(13))
            .insert(new IntNode(7))
            .insert(new IntNode(9))
            .insert(new IntNode(4))
        ;
    }

}
