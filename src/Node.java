public class Node {

    public String data;
    public Node prev;
    public Node next;

    @Override
    public String toString() {
        return "Node{" + data + '}';
    }

    Node(String data)
    {
        this.data = data;
        this.next = null;
        this.prev = null;
    }

}
