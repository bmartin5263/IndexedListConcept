import java.util.Arrays;

public class Index {

    private static final int INITIAL_CAPACITY = 16;

    private HashEntry[] entries;
    private int size;
    private int capacity;

    public Index()
    {
        this(INITIAL_CAPACITY);
    }

    public Index(int capacity)
    {
        this.entries = new HashEntry[capacity];
        this.size = 0;
        this.capacity = capacity;
    }

    public void remove(String key)
    {
        int index = hash(key);
        for (; entries[index] != null; index = (index+1) % capacity) {
            if (entries[index].key.equals(key)) {
                entries[index] = null;
                if (entries[index + 1] != null) {
                    // Fast forward to the last entry in this block of entries and move it to the now empty space of index
                    for (int j = (index + 1) % capacity; entries[(j + 1) % capacity] != null; j = (j + 1) % capacity) {
                        entries[index] = entries[j];
                        entries[j] = null;
                    }
                }
                --size;
            }
        }
    }

    /**
     * Hash the key and add it to the table.
     *
     * O(1) amortized
     */
    public void put(String key, NodeIndexPair pair)
    {
        HashEntry hashEntry = new HashEntry(key, pair);
        int index = hash(key);
        for (; entries[index] != null; index = (index+1) % capacity)
            if (entries[index].key.equals(key)) break;

        entries[index] = hashEntry;
        size++;

        if (size / capacity >= .75f) grow();
    }

    /**
     * Hash the key and return the NodeIndexPair
     *
     * O(1)
     */
    public NodeIndexPair get(String key)
    {
        int index = hash(key);
        for (; entries[index] != null; index = (index+1) % capacity)
            if (entries[index].key.equals(key)) return entries[index].value;
        return null;
    }

    /**
     * Go through the table and update the indices for each NodeIndexPair
     *
     * O(n) - need to go throw entire table
     */
    public void updateIndices(int start, int end, int amount)
    {
        for (HashEntry entry : entries)
        {
            if (entry != null && entry.value.index >= start && entry.value.index < end)
            {
                entry.value.index += amount;
            }
        }
    }

    /**
     * Return an index into the hash table
     *
     * O(1)
     */
    private int hash(String key)
    {
        return (key.hashCode() & 0x7fffffff) % capacity;
    }

    /**
     * Grow the hash table when it gets too small
     *
     * O(n)
     */
    private void grow() {
        Index newIndex = new Index();
        for (int i = 0; i < capacity; i++) {
            if (entries[i] != null) newIndex.put(entries[i].key, entries[i].value);
        }

        this.entries = newIndex.entries;
        this.capacity = newIndex.capacity;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("{");
        for (int i = 0; i < capacity; ++i)
        {
            if (entries[i] != null) builder.append(entries[i].key).append(" : ").append(entries[i].value.toString()).append(", ");
        }
        builder.append("}");
        return builder.toString();
    }
}
