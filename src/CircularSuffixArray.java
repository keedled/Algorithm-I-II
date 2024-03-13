import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;
import java.util.Comparator;

public class CircularSuffixArray {
    private String str ;
    private int length ;
    private Integer[] indexs;
    private void IndexOutOfRange(){
        throw new IllegalArgumentException("Index is out of the prescribed Range!");
    }
    // circular suffix array of s
    public CircularSuffixArray(String s){//很久没有感觉到的寸步难行的感觉了。
        if(s == null)throw new IllegalArgumentException("S is Null!");
        str = s;
        length = s.length();
        Comparator cmp = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                int entry = o1;
                while(s.charAt(o1) == s.charAt(o2)){
                    o1 = (o1 + 1)%length;
                    o2 = (o2 + 1)%length;
                    if(o1 == entry)return 0;
                }
                return Integer.compare(s.charAt(o1), s.charAt(o2));
            }
        };
        indexs = new Integer[length];
        for(int i = 0;i<length;i++)indexs[i] = i;
        Arrays.sort(indexs,cmp);
        //BinaryStdOut.close();
    }

    // length of s
    public int length(){
        return length;
    }

    // returns index of ith sorted suffix
    public int index(int i){
        if(i<0||i>=length)IndexOutOfRange();
        return indexs[i];
    }

    // unit testing (required)
    public static void main(String[] args){
        String original = "ABRACADABRA!";
        CircularSuffixArray csa = new CircularSuffixArray(original);
        for (int i = 0; i < csa.length(); i++) {
            System.out.println("index[" + i + "] = " + csa.index(i));
        }
    }
}