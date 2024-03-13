import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;

public class Permutation {
    public static void main(String args[]){
        int k = Integer.parseInt(args[0]);
        RandomizedQueue<String> randomizedQueue = new RandomizedQueue<>();
        while(!StdIn.isEmpty()){
            String str = StdIn.readString();
            randomizedQueue.enqueue(str);
        }
        Iterator it = randomizedQueue.iterator();
        for(int i= 0;i<k;i++){
            StdOut.println(it.next());
        }
    }
}
