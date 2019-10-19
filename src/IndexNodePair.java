public class IndexNodePair {

    public int index;
    public Node node;

    public IndexNodePair(int index, Node node)
    {
        this.index = index;
        this.node = node;
    }

    @Override
    public String toString() {
        return "IndexNodePair{" +
                "index=" + index +
                ", node=" + node +
                '}';
    }
}
