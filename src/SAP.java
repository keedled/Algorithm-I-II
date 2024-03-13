
import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class SAP {

    private Digraph digraph;
    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G){
        if(G == null)throw new IllegalArgumentException("G is Null!");
        digraph = G;
    }
    private void validate(int v){
        if(v<0 || v>=digraph.V())throw new IllegalArgumentException("v is a Null!");
    }
    private void validate(Iterable v){
        if(v == null)throw new IllegalArgumentException("v is a Null!");
        Iterator<Integer> it = v.iterator();
        while(it.hasNext()){
            validate(it.next());
        }
    }
    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w){
        validate(v);
        validate(w);
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph,v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(digraph,w);
        int Min_dis = Integer.MAX_VALUE;
        for(int i = 0;i<digraph.V();i++){
            if(bfs1.hasPathTo(i) && bfs2.hasPathTo(i)){
                int dis = bfs1.distTo(i) + bfs2.distTo(i);
                Min_dis = Min_dis < dis ? Min_dis: dis;
            }
        }
        if(Min_dis == Integer.MAX_VALUE)return -1;
        return Min_dis;
    }
    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validate(v);
        validate(w);
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph, v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(digraph, w);
        int Min_dis = Integer.MAX_VALUE;
        int accestor = -1;
        for (int i = 0; i < digraph.V(); i++) {
            if (bfs1.hasPathTo(i) && bfs2.hasPathTo(i)) {
                int dis = bfs1.distTo(i) + bfs2.distTo(i);
                if (dis < Min_dis) {
                    Min_dis = dis;
                    accestor = i;
                }
            }
        }
        if(Min_dis == Integer.MAX_VALUE){
            return -1;
        }
        return accestor;
    }
    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w){
        validate(v);
        validate(w);

        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph,v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(digraph,w);
        int Min_dis = Integer.MAX_VALUE;
        for(int i = 0;i<digraph.V();i++){
            if(bfs1.hasPathTo(i) && bfs2.hasPathTo(i)){
                int dis = bfs1.distTo(i) + bfs2.distTo(i);
                if(Min_dis>dis)Min_dis = dis;
            }
        }
        if(Min_dis == Integer.MAX_VALUE)return -1;
        return Min_dis;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w){
        validate(v);
        validate(w);
        BreadthFirstDirectedPaths bfs1 = new BreadthFirstDirectedPaths(digraph,v);
        BreadthFirstDirectedPaths bfs2 = new BreadthFirstDirectedPaths(digraph,w);
        int Min_dis = Integer.MAX_VALUE;
        int ancestor = -1;
        for(int i = 0;i<digraph.V();i++){
            if(bfs1.hasPathTo(i) && bfs2.hasPathTo(i)){
                int dis = bfs1.distTo(i) + bfs2.distTo(i);
                if(Min_dis>dis){
                    Min_dis = dis;
                    ancestor = i;
                }
            }
        }
        return ancestor;
    }

    // do unit testing of this class
    public static void main(String[] args) {
        In in = new In("digraph1.txt");
        Digraph G = new Digraph(in);
        SAP sap = new SAP(G);
//        while (!StdIn.isEmpty()) {
//            int v = StdIn.readInt();
//            int w = StdIn.readInt();
//            int length   = sap.length(v, w);
//            int ancestor = sap.ancestor(v, w);
//            StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
//        }
        ArrayList<Integer> v = new ArrayList<>();
        v.add(13);
        v.add(23);
        v.add(24);
        ArrayList<Integer> w = new ArrayList<>();
        w.add(6);
        w.add(16);
        w.add(17);
        int length   = sap.length(v, w);
        int ancestor = sap.ancestor(v, w);
        StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    }
}