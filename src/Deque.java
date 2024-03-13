import java.util.Iterator;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private int Size;
    private Node head;
    private Node tail;
    private class Node{
        Item item;
        Node last;
        Node next;
    }
    // construct an empty deque
    public Deque(){
        Size = 0;
        head = null;
        tail = null;
    }

    // is the deque empty?
    public boolean isEmpty(){
        return Size == 0;
    }

    // return the number of items on the deque
    public int size(){
        return Size;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if(item == null){
            throw new IllegalArgumentException("The item is null!");
        }
        if (Size == 0) {
            Node new_head = new Node();
            new_head.item = item;
            head = new_head;
            tail = new_head;
            Size++;
        }
        else {
            Node old_head = head;

            Node new_head = new Node();
            new_head.item = item;

            head = new_head;
            new_head.next = old_head;
            old_head.last = head;

            Size++;
        }
    }
    // add the item to the back
    public void addLast(Item item) {
        if(item == null){
            throw new IllegalArgumentException("The item is null!");
        }
        if (Size == 0) {
            Node new_head = new Node();
            new_head.item = item;
            head = new_head;
            tail = new_head;
            Size++;
        } else {
            Node new_tail = new Node();
            new_tail.item = item;

            tail.next = new_tail;
            new_tail.last = tail;
            tail = new_tail;
            Size++;
        }
    }

    // remove and return the item from the front
    public Item removeFirst(){
        if(Size == 0){
            throw new java.util.NoSuchElementException("deque is empty");
        }
        Node old_head = head;
        head = head.next;
        Size --;
        return old_head.item;
    }

    // remove and return the item from the back
    public Item removeLast(){
        if(Size == 0){
            throw new java.util.NoSuchElementException("The deque is empty!");
        }
        tail = tail.last;
        Size --;
        return tail.next.item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator(){
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private Node currrent = head;
        public boolean hasNext(){
            return currrent != null;
        }
        public void remove(){
            throw new UnsupportedOperationException("unsupport method!");
        }
        public Item next(){
            if(currrent == null){
                throw new java.util.NoSuchElementException("The Iterator has benn empty!");
            }
            Item item = currrent.item;
            currrent = currrent.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        Deque<Integer> deque = new Deque<>();
        deque.addFirst(1);
        deque.addFirst(2);
        deque.addLast(10);
        Iterator<Integer> iterator = deque.iterator();
        while (iterator.hasNext()){
            StdOut.println(iterator.next());
        }
        //StdOut.println(iterator.next());
        int re = deque.removeFirst();
        StdOut.println(re);
        int re2 = deque.removeFirst();
        StdOut.println(re2);
        int re3 = deque.removeFirst();
        StdOut.println(re3);
        int re4 = deque.removeFirst();
        StdOut.println(re);
    }

}