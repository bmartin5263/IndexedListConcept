public class Main {

    public static void main(String[] args) {
        IndexedList list = new IndexedList();
        list.pushBack("foo");
        list.pushBack("bar");
        list.pushBack("baz");
        list.popFront();
        //list.insert("inserted", 2);
        //System.out.println("K from last for inserted = " + list.kFromLast("inserted"));
        list.print();
    }

}
