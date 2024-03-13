import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BurrowsWheeler {

    // apply Burrows-Wheeler transform,
    // reading from standard input and writing to standard output
    public static void transform(){
        String str = StdIn.readString();
        int length = str.length();
        CircularSuffixArray circularSuffixArray = new CircularSuffixArray(str);
        int start = 0;
        StringBuilder stringBuilder = new StringBuilder();

        for(int i = 0;i<length;i++){
            int index = circularSuffixArray.index(i) - 1;
            if(index<0){
                start = i;
                index += 12;
            }
            stringBuilder.append(str.charAt(index));
        }

        BinaryStdOut.write(start);
        str = stringBuilder.toString();
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(str.charAt(i));
        }
        BinaryStdOut.close();
    }
//    public static void transform() {
//        // input
//        StringBuilder sb = new StringBuilder();
//        while (!BinaryStdIn.isEmpty()) {
//            sb.append(BinaryStdIn.readChar());
//        }
//        int length = sb.length();
//        CircularSuffixArray csa = new CircularSuffixArray(sb.toString());
//
//        // find "first" and output
//        for (int i = 0; i < length; i++) {
//            if (csa.index(i) == 0) {
//                // write a 32-bit int
//                BinaryStdOut.write(i);
//                break;
//            }
//        }
//
//        // find the string "t"
//        for (int i = 0; i < length; i++) {
//            // the i-th original suffix string
//            int index = csa.index(i);
//            // get the index of its last character
//            int lastIndex = (length - 1 + index) % length;
//            // append these characters, and then we get "t"
//            BinaryStdOut.write(sb.charAt(lastIndex));
//        }
//        BinaryStdOut.close();
//    }

    // apply Burrows-Wheeler inverse transform,
    // reading from standard input and writing to standard output
//    public static void inverseTransform(){
//        int first = BinaryStdIn.readInt();
//        BinaryStdOut.write(first);
//        StringBuilder stringBuilder = new StringBuilder();//获得t[]数组
//        while(!BinaryStdIn.isEmpty()){
//            char c = BinaryStdIn.readChar();
//            stringBuilder.append(c);
//        }
//
//        String str = stringBuilder.toString();
//        int length = stringBuilder.length();
//
//        //获得next数组
//        int MaxCharCount = 256;
//        int[] count = new int[MaxCharCount+1];
//        for(int i = 0;i<length;i++){
//            count[str.charAt(i)]++;
//        }
//        //进行前缀和求坐标
//        for(int i = 0;i<MaxCharCount;i++){
//            count[i+1] += count[i];
//        }
//        //获得next数组
//        int[] next = new int[length];
//        for(int i =0;i<length;i++){
//            char c = str.charAt(i);
//            next[count[c-1]] = i;//我们寻找的是c-1的位置，这样才能反映真实坐标，而且方便我们对count进行加操作
//            count[c-1]++;
//        }
//        StringBuilder stringBuilder1 = new StringBuilder();
//        if (first < 0 || first >= length) {
//            throw new IllegalArgumentException("Invalid value of first: " + first);
//        }
//        int ptr = next[first];
//        while(ptr != first){
//            stringBuilder1.append(str.charAt(ptr));
//            ptr =  next[ptr];
//        }
//
//        stringBuilder1.append(str.charAt(ptr));
//        str = stringBuilder1.toString();
//
//        for(int i = 0;i<length;i++)BinaryStdOut.write(str.charAt(i));
//        BinaryStdOut.close();
//    }

    public static void inverseTransform() {

        // input
        int first = BinaryStdIn.readInt();
        StringBuilder t = new StringBuilder();
        while (!BinaryStdIn.isEmpty()) {
            t.append(BinaryStdIn.readChar());
        }
        int length = t.length();

        // compute frequency counts
        final int EXTENDED_ASCII_INDEX_MAX = 256;
        int[] count = new int[EXTENDED_ASCII_INDEX_MAX + 1];
        for (int i = 0; i < length; i++) {
            count[t.charAt(i) + 1]++;
        }
        // transform counts to indices
        for (int i = 0; i < EXTENDED_ASCII_INDEX_MAX; i++) {
            count[i + 1] += count[i];
        }
        // generate next array
        int[] next = new int[length];
        for (int i = 0; i < length; i++) {
            char c = t.charAt(i);
            next[count[c]] = i;
            count[c]++;
        }

        // output
        for (int i = 0; i < length; i++) {
            BinaryStdOut.write(t.charAt(next[first]));
            first = next[first];
        }
        BinaryStdOut.close();

    }

    // if args[0] is "-", apply Burrows-Wheeler transform
    // if args[0] is "+", apply Burrows-Wheeler inverse transform
    public static void main(String[] args){
        if ("-".equals(args[0])) {
            transform();
        } else {
            inverseTransform();
    }
    }
}