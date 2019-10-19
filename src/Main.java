public class Main {

    public static void main(String[] args) {
//        IndexedList list = new IndexedList();
//        list.pushBack("foo");
//        list.pushBack("bar");
//        list.pushBack("baz");
//        list.popFront();
//        //list.insert("inserted", 2);
//        //System.out.println("K from last for inserted = " + list.kFromLast("inserted"));
//        list.print();

        Index index = new Index();

        index.put("foo", new NodeIndexPair(0, null));
        index.put("bar", new NodeIndexPair(1, null));
        index.put("baz", new NodeIndexPair(2, null));

        System.out.println(index);

        index.updateIndices(0, 3, 1);
        System.out.println(index);

        NodeIndexPair pair = index.get("foo");

        System.out.println(pair);

    }

}
