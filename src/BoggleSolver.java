import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.In;

import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.TrieSET;

public class BoggleSolver
{
    private Node root;
    private class Node{
        Node[] next = new Node[26];
        Boolean end = false;
    }
    private boolean get(String key){
        Node x = get(root,key,0);
        return x != null;
    }
    private Node get(Node node,String key,int index){
        if(node == null)return null;
        if(index == key.length()){
            if(node.end){
                return node;
            }
            else return null;
        }
        return get(node.next[key.charAt(index) - 'A'],key,index+1);
    }
    private void put(String key){
        root = put(root,key,0);
    }
    private Node put(Node node,String key,int index){
        if(node == null){
            node = new Node();
        }
        if(index == key.length()){
            node.end = true;
            return node;
        }
        node.next[key.charAt(index) - 'A'] = put(node.next[key.charAt(index) - 'A'],key,index+1);
        return node;
    }
    private SET<String> ValidStrings;
    // Initializes the data structure using the given array of strings as the dictionary.
    // (You can assume each word in the dictionary contains only the uppercase letters A through Z.)
    public BoggleSolver(String[] dictionary){//这个地方用个tri树存一下
        //预处理一dictionary
        String[] newStr = new String[dictionary.length];
        for(int i = 0;i<dictionary.length;i++){
            StringBuilder strs = new StringBuilder();
            for(int j = 0;j<dictionary[i].length();j++){
                if(dictionary[i].charAt(j) == 'U' && j != 0 && dictionary[i].charAt(j-1) == 'Q'){
                    continue;
                }
                strs.append(dictionary[i].charAt(j));
            }
            newStr[i] = strs.toString();
        }
        root = new Node();
        for(String s : newStr){
            put(s);
        }
    }
    private boolean check(BoggleBoard board,int i,int j){
        if(i<0||j<0||i>= board.rows()||j>= board.cols())return false;
        return true;
    }
    private void DFS(Boolean[][] Arral,BoggleBoard board,int i,int j,StringBuilder str){
        if(Arral[i][j])return;
        Arral[i][j] = true;
        str.append(board.getLetter(i,j));
        if(str.length()>2){//大于2，检查是否在字典中
            if(get(str.toString())){
                ValidStrings.add(str.toString());
            }
        }
        int[] dx = {-1,1,0,0,-1,-1,1,1};//斜着
        int[] dy = {0,0,-1,1,-1,1,1,-1};
        for(int k = 0;k<8;k++){
            if(check(board,i + dx[k],j + dy[k])){
                DFS(Arral,board,i + dx[k],j + dy[k],str);
            }
        }
        //结束，修改条件
        str.deleteCharAt(str.length() - 1);
        Arral[i][j] = false;
    }
    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board){
        //在搜索出的Q的后面加上U这个字符，或者不加，作为两种情况，如果有一种情况Valid,那就加分。
        //我能想到的方法，只有深度优先搜索，但是这有点太慢了吧，要递归很多种情况呢。不管怎么说，试一试吧。
        ValidStrings = new SET<>();
        Boolean[][] Arral = new Boolean[board.rows()][board.cols()];
        for(int ii = 0;ii< board.rows();ii++) {
            for (int jj = 0; jj < board.cols(); jj++)
                Arral[ii][jj] = false;
        }
        for(int i = 0;i<board.rows();i++)
            for(int j = 0;j<board.cols();j++){
                Boolean[][] newArral = Arral.clone();
                StringBuilder str = new StringBuilder();
                DFS(newArral,board,i,j,str);
            }
        SET<String> newValid = new SET<>();
        for(String s : ValidStrings){
            if(s.contains("Q")){
                String ss = s.replace("Q","QU");
                newValid.add(ss);
            }else{
                newValid.add(s);
            }
        }
        return newValid;
    }

    // Returns the score of the given word if it is in the dictionary, zero otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word){
        int Len = word.length();

        if(Len == 0)return 0;
        if(Len < 5)return 1;
        if(Len < 6)return 2;
        if(Len < 7)return 3;
        if(Len < 8)return 5;

        return 11;
    }
    public static void main(String[] args) {
        In in = new In(args[0]);
        String[] dictionary = in.readAllStrings();
        BoggleSolver solver = new BoggleSolver(dictionary);
        BoggleBoard board = new BoggleBoard(args[1]);
        int score = 0;
        for (String word : solver.getAllValidWords(board)) {
            StdOut.println(word);
            score += solver.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}