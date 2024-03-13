
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.DirectedCycle;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private SAP sap;
    private Digraph digraph;//存储上位关系的有向图
    private Map<String,ArrayList<Integer>> map;//存储字符串到Id的映射关系
    private ArrayList<Integer> nouns;
    private ST<Integer,String> st2;

    private void hasCycle() {
        DirectedCycle cycle = new DirectedCycle(digraph);
        if (cycle.hasCycle()) {
            throw new IllegalArgumentException("word net should not have a cycle in it");
        }
    }

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        if (synsets == null || hypernyms == null) throw new IllegalArgumentException("there is a Null arguments!");

        In synsetsIn = new In(synsets);
        In hypernymsIn = new In(hypernyms);
        map = new HashMap<>();
        st2 = new ST<>();
        while (synsetsIn.hasNextLine()) {

            String line = synsetsIn.readLine();
            String[] tmp = line.split(",");
            for(String noun:tmp[1].split(" ")){
                ArrayList<Integer> vertices = map.getOrDefault(noun,new ArrayList<>());
                vertices.add(Integer.parseInt(tmp[0]));
                map.put(noun,vertices);
            }
            st2.put(Integer.parseInt(tmp[0]),tmp[1]);
        }
        digraph = new Digraph(st2.size());
        while (hypernymsIn.hasNextLine()) {
            String line = hypernymsIn.readLine();
            String[] tmp = line.split(",");
            //System.out.println(tmp[0]);
            //System.out.println(tmp.length);
            for (int i = 1; i < tmp.length; i++) {
                digraph.addEdge(Integer.parseInt(tmp[0]), Integer.parseInt(tmp[i]));
            }
        }
        //System.out.println(digraph.V());
        //判断是否只有一个根
        int count = 0;
        for(int i = 0;i<digraph.V();i++){
            //System.out.println(i);
            if(digraph.outdegree(i) == 0){
                count ++ ;
            }
        }
        if(count!=1)throw new IllegalArgumentException("there is no Root!");
        //判断是否是一个DAG
        hasCycle();
        sap = new SAP(digraph);
    }
    // returns all WordNet nouns
    public Iterable<String> nouns(){
        return map.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word){
        if(word == null)throw new IllegalArgumentException("the argusment is null!");
        return map.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB){
        if(!isNoun(nounA) || !isNoun(nounB))throw new IllegalArgumentException("there is an arguments that is illegal!");
        ArrayList id_a = map.get(nounA);
        ArrayList id_b = map.get(nounB);
        return sap.length(id_a,id_b);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB)
    {
        if(!isNoun(nounA)||!isNoun(nounB))throw new IllegalArgumentException("there is an arguments that is illegal!");
        ArrayList id_a = map.get(nounA);
        ArrayList id_b = map.get(nounB);
        int anc = sap.ancestor(id_a,id_b);
        return st2.get(anc);
    }
    // do unit testing of this class
    public static void main(String[] args) {
        WordNet wordNet = new WordNet("synsets1.txt", "hyper1.txt");
        System.out.println(wordNet.distance("1750s", "1780s"));
        System.out.println(wordNet.sap("1750s", "1780s"));
        //System.out.println(wordNet.get("zymosis"));
        //System.out.println(wordNet.nouns2id.keySet().size());
        //System.out.println(wordNet.nouns.size());
    }
}