import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {
    private WeightedQuickUnionUF ufFoFull;//定判断是否full的UF
    private boolean[][] ifOpen;
    private WeightedQuickUnionUF uf;//定义连通块的UF
    //public int top,bottom;//定义最上方和最下方的两个虚拟根节点
    private int Size;//传入n的大小
    private int opensites;//open状态点的数量
    private int[] x = {-1, 1, 0, 0};
    private int[] y = {0, 0, -1, 1};//四个方向的索引
    private boolean isPercolated;//是否渗透

    public Percolation(int n) {
        if (n <= 0) {
            throw new IllegalArgumentException("n must be greater than 0");
        }
        Size = n;
        uf = new WeightedQuickUnionUF(n * n + 2);//仅包含top top映射0位置(暂时加上1，看下效果如何,目前看来没什么问题，后续再说吧)
        ufFoFull = new WeightedQuickUnionUF(n * n + 2);//包含top 和 bottom top映射0位置 bottom映射到n*n+1位置
        isPercolated = false;
        ifOpen = new boolean[n + 1][n + 1];
        opensites = 0;
    }

    private int calcIndex(int row, int col) {//row col 都 > 0
        return (row - 1) * Size + col;
    }

    private boolean isInGrid(int row, int col) {
        if (row <= 0 || row > Size || col <= 0 || col > Size) {
            return false;
        }
        return true;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("args must be greater than 0");
        }
        if (!ifOpen[row][col]) {
            ifOpen[row][col] = true;
            int index = calcIndex(row, col);
            if (row == 1) {
                //uf.union(0,index);
                uf.union(0, index);
            }
            if (row == Size) {
                uf.union(Size * Size + 1, index);
            }
            collectNeighbours(row, col);
            opensites++;
        }
    }

    private void collectNeighbours(int row, int col) {
        int index = calcIndex(row, col);
        for (int i = 0; i <= 3; i++) {
            if (isInGrid(row + x[i], col + y[i]) && isOpen(row + x[i], col + y[i])) {
                int index_next = calcIndex(row + x[i], col + y[i]);
                uf.union(index_next, index);
            }
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("args must be greater than 0");
        }
        return ifOpen[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (!isInGrid(row, col)) {
            throw new IllegalArgumentException("args must be greater than 0");
        }
        if (uf.find(0) == uf.find(calcIndex(row, col))) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return opensites;
    }

    // does the system percolate?
    public boolean percolates() {
        if(isPercolated == true){
            return true;
        }
        if (uf.find(0) == uf.find(Size * Size + 1)) {
            isPercolated = true;
            return true;
        }
        return false;
    }

    // test client (optional)
    public static void main(String[] args){
        Percolation percolation = new Percolation(5);
        percolation.open(1,3);
        percolation.open(2,3);
        percolation.open(3,3);
        percolation.open(4,3);
        percolation.open(4,4);
        percolation.open(3,4);
        percolation.open(2,2);
        System.out.println(percolation.percolates());
        System.out.println(percolation.numberOfOpenSites());
    }
}
