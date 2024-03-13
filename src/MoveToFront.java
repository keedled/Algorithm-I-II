//import edu.princeton.cs.algs4.BinaryStdIn;
//import edu.princeton.cs.algs4.BinaryStdOut;
//
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.Set;
//
//public class MoveToFront {
//    // apply move-to-front encoding, reading from standard input and writing to standard output
//    public static void encode(){
//        char[] chars = new char[256];
//        for(int i = 0;i<256;i++){
//            chars[i] = (char)i;
//        }
//        while (!BinaryStdIn.isEmpty()){
//            char c = BinaryStdIn.readChar();
//            int index = 0;
//            for(int i = 0;i<256;i++){
//                if(chars[i] == c){
//                    BinaryStdOut.write(i,8);
//                    index = i;
//                    break;
//                }
//            }
//            for(int i = index;i>0;i--){
//                chars[i] = chars[i-1];
//            }
//            chars[0] = c;
//        }
//        BinaryStdOut.close();
//    }
//
//    // apply move-to-front decoding, reading from standard input and writing to standard output
//    public static void decode(){
//        char[] chars = new char[256];
//        while(!BinaryStdIn.isEmpty()){
//            int index = BinaryStdIn.readChar(8);
//            BinaryStdOut.write(chars[index]);
//            char c = chars[index];
//            for(int i = index;i>0;i--){
//                chars[i] = chars[i-1];
//            }
//            chars[0] = c;
//        }
//        BinaryStdOut.close();
//    }
//
//    // if args[0] is "-", apply move-to-front encoding
//    // if args[0] is "+", apply move-to-front decoding
//    public static void main(String[] args){
//        if(true){
//            encode();
//        }
//        else{
//            decode();
//        }
//    }
//
//}
import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.LinkedList;

public class MoveToFront {

    private static final int RELEVANT_BITS = 8;

    // An ordered sequence of the 256 extended ASCII characters.
    private static LinkedList<Character> generateInitialSequence() {
        final int EXTENDED_ASCII_INDEX_MAX = 256;
        LinkedList<Character> sequence = new LinkedList<>();
        for (int i = 0; i < EXTENDED_ASCII_INDEX_MAX; i++) {
            sequence.add((char) i);
        }
        return sequence;
    }

    // apply move-to-front encoding, reading from standard input and writing to standard output
    public static void encode() {
        LinkedList<Character> sequence = generateInitialSequence();

        while (!BinaryStdIn.isEmpty()) {
            char c = BinaryStdIn.readChar();
            int index = sequence.indexOf(c);
            BinaryStdOut.write(index, RELEVANT_BITS);
            // remove the object, not the one at the index
            sequence.remove((Object) c);
            sequence.addFirst(c);
        }

        // Remember to flush the cache
        BinaryStdOut.close();
    }

    // apply move-to-front decoding, reading from standard input and writing to standard output
    public static void decode() {
        LinkedList<Character> sequence = generateInitialSequence();

        while (!BinaryStdIn.isEmpty()) {
            int index = BinaryStdIn.readInt(RELEVANT_BITS);
            char c = sequence.get(index);
            BinaryStdOut.write(c);
            // remove the object, not the one at the index
            sequence.remove((Object) c);
            sequence.addFirst(c);
        }

        // Remember to flush the cache
        BinaryStdOut.close();
    }

    // if args[0] is "-", apply move-to-front encoding
    // if args[0] is "+", apply move-to-front decoding
    public static void main(String[] args) {
        if ("-".equals(args[0])) {
            encode();
        } else {
            decode();
        }

    }

}