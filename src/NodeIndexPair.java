public class NodeIndexPair {

    public int index;
    public Node node;

    public NodeIndexPair(int index, Node node)
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
