import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    private WordNet wordNet;
    public Outcast(WordNet wordnet)         // constructor takes a WordNet object
    {
        this.wordNet = wordnet;
    }
    public String outcast(String[] nouns)   // given an array of WordNet nouns, return an outcas
    {
        int sum = 0;
        String str = "";
        for(int i = 0;i< nouns.length;i++){
            int dis = 0;
            for(int j = 0;j< nouns.length;j++){
                dis += wordNet.distance(nouns[i],nouns[j]);
            }
            if(dis>sum){
                sum = dis;
                str = nouns[i];
            }
        }
        return str;
    }
    public static void main(String[] args) {
        String[] myArgs = {"synsets.txt", "hypernyms.txt", "outcast5.txt", "outcast8.txt", "outcast11.txt"};
        WordNet wordnet = new WordNet(myArgs[0], myArgs[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < myArgs.length; t++) {
            In in = new In(myArgs[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(myArgs[t] + ": " + outcast.outcast(nouns));
        }
    }
}