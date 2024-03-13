import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;


public class Solver {

    // 定义一个搜索树，方便进行 A* 搜索
    // 搜索树的结点，递归的定义
    private static class GameTreeNode implements Comparable<GameTreeNode> {
        private final Board board; // 结点
        private final GameTreeNode parent; // 父亲
        private final boolean twin;
        private final int moves;
        private final int distance;
        private final int priority;

        // 初始节点，parent 为 null，需要区分是不是双胞胎
        public GameTreeNode(Board board, boolean twin) {
            this.board = board;
            parent = null;
            this.twin = twin;
            moves = 0;
            distance = board.manhattan();
            priority = distance + moves;
        }

        // 之后的结点，twin 状态跟从 parent
        public GameTreeNode(Board board, GameTreeNode parent) {
            this.board = board;
            this.parent = parent;
            twin = parent.twin;
            moves = parent.moves + 1;
            distance = board.manhattan();
            priority = distance + moves;
        }

        public Board getBoard() {
            return board;
        }

        public GameTreeNode getParent() {
            return parent;
        }

        public boolean isTwin() {
            return twin;
        }

        @Override
        public int compareTo(GameTreeNode node) {
            if (priority == node.priority) {
                return Integer.compare(distance, distance);
            } else {
                return Integer.compare(priority, node.priority);
            }
        }

        @Override
        public boolean equals(Object node) {
            if (node == null) {
                return false;
            }
            if (this == node) {
                return true;
            }
            if (node.getClass() != this.getClass()) {
                return false;
            }
            GameTreeNode that = (GameTreeNode) node;
            return getBoard().equals(that.getBoard());
        }

        @Override
        public int hashCode() {
            return 1;
        }
    }

    private int moves;
    private boolean solvable;
    private Iterable<Board> solution;
    private final Board initial;

    public Solver(Board initial) {
        if (initial == null) {
            throw new IllegalArgumentException();
        }
        this.initial = initial;
        cache();
    }

    public boolean isSolvable() {
        return solvable;
    }

    public int moves() {
        return moves;
    }

    public Iterable<Board> solution() {
        return this.solution;
    }

    // 构造的时候直接跑出结果，然后缓存，否则没有 solution 的话，moves 和 solvable 也拿不到
    private void cache() {

        MinPQ<GameTreeNode> pq = new MinPQ<>();
        // 把当前状态和双胞胎状态一起压入队列，做 A* 搜索
        pq.insert(new GameTreeNode(initial, false));
        pq.insert(new GameTreeNode(initial.twin(), true));
        GameTreeNode node = pq.delMin();
        Board b = node.getBoard();
        //  要么是棋盘本身，要么是棋盘的双胞胎，总有一个会做到 isGoal()
        while (!b.isGoal()) {
            for (Board bb : b.neighbors()) {
                //避免重复操作
                if (node.getParent() == null || !bb.equals(node.getParent().getBoard())) {
                    pq.insert(new GameTreeNode(bb, node));
                }
            }
            // 理论上这里 pq 永远不可能为空
            node = pq.delMin();
            b = node.getBoard();
        }
        // 如果是自己做出了结果，那么就是可解的，如果是双胞胎做出了结果，那么就是不可解的
        solvable = !node.isTwin();

        if (!solvable) {
            // 注意不可解的地图，moves 是 -1，solution 是 null
            moves = -1;
            solution = null;
        } else {
            // 遍历，沿着 parent 走上去
            ArrayList<Board> list = new ArrayList<>();
            while (node != null) {
                list.add(node.getBoard());
                node = node.getParent();
            }
            // 有多少个状态，减 1 就是操作次数
            moves = list.size() - 1;
            // 做一次反转
            Collections.reverse(list);
            solution = list;
        }
    }

    // test client (see below)
    public static void main(String[] args) {

        // create initial board from file
        In in = new In(args[0]);
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board initial = new Board(tiles);

        // solve the puzzle
        Solver solver = new Solver(initial);

        // print solution to standard output
        if (!solver.isSolvable()) {
            StdOut.println("No solution possible");
        } else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution()) {
                StdOut.println(board);
            }
        }
    }

}