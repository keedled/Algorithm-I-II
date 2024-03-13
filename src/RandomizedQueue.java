import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Iterator;


public class RandomizedQueue<Item> implements Iterable<Item> {
    private int Size;
    private Node head;
    private Node tail;
    private class Node{
        Item item;
        Node last;
        Node next;
        boolean is_print;
    }
    // construct an empty randomized queue
    public RandomizedQueue(){
        Size = 0;
        head = null;
        tail = null;
    }

    // is the randomized queue empty?
    public boolean isEmpty(){
        return Size == 0;
    }

    // return the number of items on the randomized queue
    public int size(){
        return Size;
    }

    // add the item
    public void enqueue(Item item){
        if(item == null){
            throw new IllegalArgumentException("The item is null!");
        }
        if(Size == 0){
            Node node = new Node();
            node.item = item;
            node.is_print = false;
            head = node;
            tail = node;
        }
        else{
            Node new_head = new Node();
            new_head.item = item;
            new_head.is_print = false;

            new_head.next = head;
            head = new_head;
        }
        Size ++;
    }

    // remove and return a random item
    public Item dequeue(){

        if(Size == 0){
            throw new java.util.NoSuchElementException("The queue is empty!");
        }
        int i = 0;
        int RanDom_num = StdRandom.uniformInt(Size);
        Node tmp_head = head;
        while(tmp_head!=null){
            if(i++ == RanDom_num){
                if(tmp_head == head){
                    Item item = head.item;
                    head = head.next;
                    Size --;
                    return item;
                }
                else if(tmp_head == tail){
                    Item item = tail.item;
                    tail = tail.last;
                    Size --;
                    return item;
                }
                else{
                    Item item = tmp_head.item;
                    tmp_head.next.last = tmp_head.last;
                    tmp_head.last.next = tmp_head.next;
                    Size --;
                    return item;
                }
            }
            tmp_head = tmp_head.next;
        }
        return tmp_head.item;
    }

    // return a random item (but do not remove it)
    public Item sample(){
        if(Size == 0){
            throw new java.util.NoSuchElementException("The queue is empty!");
        }
        int i = 0;
        int RanDom_num = StdRandom.uniformInt(Size);
        Node tmp_head = head;
        while(tmp_head!=null){
            if(i++ == RanDom_num){
                if(tmp_head == head){
                    tmp_head.is_print = true;
                    Item item = head.item;
                    return item;
                }
                else if(tmp_head == tail){
                    Item item = tail.item;
                    return item;
                }
                else{
                    Item item = tmp_head.item;
                    return item;
                }
            }
            tmp_head = tmp_head.next;
        }
        return tmp_head.item;
    }
    private Node sample_node(){
        if(Size == 0){
            throw new java.util.NoSuchElementException("The queue is empty!");
        }
        int i = 0;
        int RanDom_num = StdRandom.uniformInt(Size);
        Node tmp_head = head;
        while(tmp_head!=null){
            if(i++ == RanDom_num){
                if(tmp_head == head){
                    //tmp_head.is_print = true;
                    Item item = head.item;
                    return tmp_head;
                }
                else if(tmp_head == tail){
                    Item item = tail.item;
                    return tmp_head;
                }
                else{
                    Item item = tmp_head.item;
                    return tmp_head;
                }
            }
            tmp_head = tmp_head.next;
        }
        return tmp_head;
    }
    // return an independent iterator over items in random order
    public Iterator<Item> iterator(){
        Node tmp_head = head;
        while(tmp_head != null){
            tmp_head.is_print = false;
            tmp_head = tmp_head.next;
        }
        return new ListIterator();
    }
    private class ListIterator implements Iterator<Item>{
        private int has_print = 0;
        public boolean hasNext(){
            return has_print != Size;
        }
        public void remove(){
            throw new UnsupportedOperationException("unsupport method!");
        }
        public Item next(){
            if(has_print == Size){
                throw new java.util.NoSuchElementException("The Iterator has benn empty!");
            }
            Node node = sample_node();
            while(node.is_print != false){
                node = sample_node();
            }
            node.is_print = true;
            has_print ++;
            return node.item;
        }
    }

    // unit testing (required)
    public static void main(String[] args){
        RandomizedQueue<Integer> randonqueue = new RandomizedQueue<>();
        randonqueue.enqueue(10);
        randonqueue.enqueue(11);
        randonqueue.enqueue(12);
        randonqueue.enqueue(13);
        randonqueue.enqueue(14);
        randonqueue.enqueue(15);
        randonqueue.enqueue(16);
        Iterator it = randonqueue.iterator();
        while(it.hasNext()){
            StdOut.println(it.next());
        }
        //StdOut.println(it.next());
        Iterator it2 = randonqueue.iterator();
        while(it2.hasNext()){
            StdOut.println(it2.next());
        }
    }
}
