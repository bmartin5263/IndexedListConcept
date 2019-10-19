import java.util.HashMap;
import java.util.Map;

public class IndexedList {

    // In practice, this would have to be written from scratch, but we're the designers, so thats a problem for the
    // dev team. 
    private HashMap<String, IndexNodePair> index;

    private Node head;
    private Node tail;
    private int size;

    public IndexedList()
    {
        this.head = null;
        this.tail = null;
        this.index = new HashMap<>();
        this.size = 0;
    }

    public int size()
    {
        return size;
    }

    public boolean isEmpty()
    {
        return size == 0;
    }

    /**
     * Insert a node at the end of the list.
     *
     * Runtime O(1)
     */
    public void pushBack(String key)
    {
        Node newNode = new Node(key);
        if (isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            tail.next = newNode;
            newNode.prev = tail;
            tail = newNode;
        }
        index.put(key, new IndexNodePair(size, newNode));
        ++size;
    }

    /**
     * Insert a node at the beginning of the list.
     *
     * Runtime O(n) - each index must be updated in the hash map
     */
    public void pushFront(String key)
    {
        Node newNode = new Node(key);
        if (isEmpty())
        {
            head = newNode;
            tail = newNode;
        }
        else
        {
            head.prev = newNode;
            newNode.next = head;
            head = newNode;
        }
        for (IndexNodePair pair : index.values())
        {
            pair.index++;
        }
        index.put(key, new IndexNodePair(0, newNode));
        ++size;
    }


    /**
     * Return a node for a given key
     *
     * O(1) with the hashmap
     */
    public Node get(String key)
    {
        return index.get(key).node;
    }

    /**
     * Return how far away a given key is from the end of the list
     *
     * O(1) with the hashmap
     */
    public int kFromLast(String key)
    {
        int keyIndex = index.get(key).index;
        return size - 1 - keyIndex;
    }

    /**
     * Insert a node at a random position
     *
     * O(n) since the index needs to be updated
     */
    public void insert(String key, int position)
    {
        Node newNode = new Node(key);
        if (position >= size || head == null) pushBack(key);
        else if (position == 0) pushFront(key);
        else
        {
            boolean startLeft = (size - position) < size / 2;

            // cut the amount of nodes we have to traverse in half
            if (startLeft)
            {
                Node before = head;
                for (int i = 0; i < position - 1; ++i)
                {
                    before = before.next;
                }
                Node oldNext = before.next;
                before.next = newNode;
                newNode.prev = before;
                newNode.next = oldNext;
                oldNext.prev = newNode;
            }
            else
            {
                Node before = tail;
                for (int i = 0; i < size - position; ++i)
                {
                    before = before.prev;
                }
                Node oldNext = before.next;
                before.next = newNode;
                newNode.prev = before;
                newNode.next = oldNext;
                oldNext.prev = newNode;
            }
            // Update the index, very important
            for (IndexNodePair pair : index.values())
            {
                if (pair.index >= position)
                {
                    pair.index++;
                }
            }
            index.put(key, new IndexNodePair(position, newNode));
            ++size;
        }
    }

    /**
     * Delete node based on key
     *
     * O(1) with hashmap
     */
    public void delete(String key)
    {
        Node n = index.get(key).node;
        if (n.prev != null) n.prev.next = n.next;
        if (n.next != null) n.next.prev = n.prev;
        index.remove(key);
        size--;
    }

    /**
     * Delete from the front
     *
     * O(n) indices need updating
     */
    public void popFront()
    {
        assert size > 0;
        String key = head.data;
        if (tail == head)
        {
            tail = null;
            head = null;
        }
        else
        {
            head = head.next;
            head.prev = null;
        }
        index.remove(key);
        for (IndexNodePair pair : index.values())
        {
            pair.index--;
        }
        size--;
    }

    /**
     * Delete from the back
     *
     * O(1) no indices need updating
     */
    public void popBack()
    {
        assert size > 0;
        String key = tail.data;
        if (tail == head) {
            tail = null;
            head = null;
        }
        else
        {
            tail = tail.prev;
            tail.next = null;
        }
        index.remove(key);
        size--;
    }

    /**
     * Delete at a specified position
     *
     * O(n) to update index
     */
    public void deleteAt(int position)
    {
        // empty list
        if (size > 0)
        {
            // Delete at the end
            if (position >= (size - 1))
            {
                popBack();
            }
            // Delete at the front
            else if (position == 0)
            {
                popFront();
            }
            // delete in the middle
            else
            {
                /**
                 * I'm not going to write this part since it is irrelevant to the design, just know that
                 * this operation would take O(n). See insert for how it would be coded, just the opposite
                 */
                Node n = null;
                String key = n.data;
                for (IndexNodePair pair : index.values())
                {
                    if (pair.index >= position)
                    {
                        pair.index++;
                    }
                }
                index.remove(key);
                size--;
            }
        }
    }

    public void print()
    {
        System.out.println("Size = " + size);
        for (Node n = head; n != null; n = n.next)
        {
            System.out.print(n.data + " -> ");
        }
        System.out.println("null");
        for (Node n = tail; n != null; n = n.prev)
        {
            System.out.print(n.data + " <- ");
        }
        System.out.println("null");
        System.out.println(index.toString());
    }

}
