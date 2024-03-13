import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.Stack;
//import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Set;

public class KdTree {
    private double nearest_dis = Double.POSITIVE_INFINITY;
    private Node Nearest_point = null;
    private static class Node{
        RectHV Rect;
        Point2D p;
        boolean vertical;//是否垂直划分
        Node left_child;//左子树
        Node right_child;//右子树
        Node Far;//父节点
        public Node(boolean vertical_par,Point2D p,RectHV rect,Node far){
            vertical = !vertical_par;
            left_child = null;
            right_child = null;
            this.p = p;
            this.Rect = rect;
            Far = far;
        }

    }
    private int Size = 0;//当前搜索树的大小
    private boolean has_root = false;//是否有了根节点
    private Node root;//根节点的地址
    private boolean vertical_root = true;//根节点是垂直划分的
    public KdTree()                               // construct an empty set of points
    {
    }
    public           boolean isEmpty()                      // is the set empty?
    {
        return Size == 0;
    }
    public               int size()                         // number of points in the set
    {
        return Size;
    }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if(p == null){
            throw new IllegalArgumentException("p is null!");
        }
        if(!has_root){//没有建树，初始化根节点
            root = new Node(!vertical_root,p,new RectHV(0,0,1,1),null);
            has_root = !has_root;
            Size++;
            return ;
        }
        if(!contains(p)){//如果未包含P,可以进行插入操作
            boolean vertical_par = true;
            Node node = root;
            Node node_next;
            while(node != null){
                vertical_par = node.vertical;
                if(node.vertical){//如果该节点是垂直划分的，则其子节点中的vertical则是水平的，即false
                    if(p.x()<node.p.x()){//如果是左子树的话
                        node_next = node.left_child;//node_next = 左子树地址
                        if(node_next == null){
                            node.left_child = new Node(vertical_par,p,new RectHV(node.Rect.xmin(),node.Rect.ymin(),node.p.x(),node.Rect.ymax()),node);
                            break;
                        }
                    }
                    else{//右子树的情况
                        node_next = node.right_child;
                        if(node_next == null){
                            node.right_child = new Node(vertical_par,p,new RectHV(node.p.x(),node.Rect.ymin(),node.Rect.xmax(),node.Rect.ymax()),node);
                            break;
                        }
                    }
                }
                else{//该节点是水平划分子区间的，则是如下操作
                    if(p.y()<node.p.y()){//左子树
                        node_next = node.left_child;
                        if(node_next == null){
                            node.left_child = new Node(vertical_par,p,new RectHV(node.Rect.xmin(),node.Rect.ymin(),node.Rect.xmax(),node.p.y()),node);
                            break;
                        }
                    }
                    else{
                        node_next = node.right_child;
                        if(node_next == null){
                            node.right_child = new Node(vertical_par,p,new RectHV(node.Rect.xmin(),node.p.y(),node.Rect.xmax(),node.Rect.ymax()),node);
                            break;
                        }
                    }
                }

                node = node_next;
            }
            Size++;
        }
    }
    private boolean equals(Point2D p1,Point2D p2){
        if(p1.x() == p2.x() && p1.y() == p2.y()){
            return true;
        }
        return false;
    }
    private boolean contains(Node node, Point2D p) {
        if (node == null) {
            return false;
        }
        if (p.equals(node.p)) {
            return true;
        }
        if (node.vertical) {
            if (p.x() < node.p.x()) {
                return contains(node.left_child, p);
            } else {
                return contains(node.right_child, p);
            }
        } else {
            if (p.y() < node.p.y()) {
                return contains(node.left_child, p);
            } else {
                return contains(node.right_child, p);
            }
        }
    }
    // does the set contain point p?
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return contains(root, p);
    }
    private void draw(Node node, Node parent) {
    if (node == null) {
        return;
    }
    if (parent == null) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        StdDraw.line(node.p.x(), 0, node.p.x(), 1);
    } else if (node.vertical) {
        StdDraw.setPenColor(StdDraw.RED);
        StdDraw.setPenRadius();
        StdDraw.line(node.p.x(), node.Rect.ymin(), node.p.x(), node.Rect.ymax());
    } else {
        StdDraw.setPenColor(StdDraw.BLUE);
        StdDraw.setPenRadius();
        StdDraw.line(node.Rect.xmin(), node.p.y(), node.Rect.xmax(), node.p.y());
    }

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());
        draw(node.left_child, node);
        draw(node.right_child, node);
    }

    // draw all points to standard draw
    public void draw() {
        draw(root, null);
    }
    private void Range_Search(Node node,RectHV Rect,ArrayList<Point2D> arr){
        if(node == null){
            return ;
        }
        if(Rect.contains(node.p)){
            arr.add(node.p);
        }
        if(node.left_child != null && node.left_child.Rect.intersects(Rect)){
            Range_Search(node.left_child,Rect,arr);
        }
        if(node.right_child != null && node.right_child.Rect.intersects(Rect)){
            Range_Search(node.right_child,Rect,arr);
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)
    {
        if(rect == null){
            throw new IllegalArgumentException("the rect can't be null!");
        }
        ArrayList<Point2D> arr = new ArrayList<>();
        Range_Search(root,rect,arr);
        return arr;
    }
    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        nearest_help(root,p);
        return Nearest_point.p;
    }

    private void nearest_help(Node node,Point2D p) {
        if (nearest_dis > node.p.distanceSquaredTo(p)) {
            nearest_dis = node.p.distanceSquaredTo(p);
            Nearest_point = node;
        }
        boolean left = false, right = false;
        if (node.left_child != null && node.left_child.Rect.distanceSquaredTo(p) < nearest_dis) {
            left = true;
        }
        if (node.right_child != null && node.right_child.Rect.distanceSquaredTo(p) < nearest_dis) {
            right = true;
        }
        if (left && right) {
            if (node.left_child.vertical) {
                if (p.x() < node.p.x()) {
                    nearest_help(node.left_child, p);
                    if (node.right_child.Rect.distanceSquaredTo(p) < nearest_dis) {
                        nearest_help(node.right_child, p);
                    }
                } else {
                    nearest_help(node.right_child, p);
                    if (node.left_child.Rect.distanceSquaredTo(p) < nearest_dis) {
                        nearest_help(node.left_child, p);
                    }
                }
            } else {
                if (p.y() < node.p.y()) {
                    nearest_help(node.left_child, p);
                    if (node.right_child.Rect.distanceSquaredTo(p) < nearest_dis) {
                        nearest_help(node.right_child, p);
                    }
                } else {
                    nearest_help(node.right_child, p);
                    if (node.left_child.Rect.distanceSquaredTo(p) < nearest_dis) {
                        nearest_help(node.left_child, p);
                    }
                }
            }
        }
        else if(left){//仅有左子树需要搜索的情况
            nearest_help(node.left_child,p);
        }
        else if(right){
            nearest_help(node.right_child,p);
        }
        return ;
    }

    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
//        KdTree kdTree = new KdTree();
//        for(int i = 1;i<=5;i++){
//            Point2D p = new Point2D(i*1.0/10,i*1.0/10);
//            kdTree.insert(p);
//        }
//        //kdTree.draw();
//        Point2D p = kdTree.nearest(new Point2D(0.3,0.2));
//        StdOut.println(p);
//        Iterable arr = kdTree.range(new RectHV(0,0,0.5,0.6));
//        Iterator<Point2D> itr = arr.iterator();
//        while(itr.hasNext()){
//            StdOut.println(itr.next());
//        }
        KdTree kdtree = new KdTree();
        kdtree.insert(new Point2D(0.7, 0.2));
        kdtree.insert(new Point2D(0.5, 0.4));

        kdtree.insert(new Point2D(0.2, 0.3));
        kdtree.insert(new Point2D(0.4, 0.7));
        kdtree.insert(new Point2D(0.9, 0.6));

        Point2D query_point = new Point2D(0.22, 0.731);
        Point2D nearest = kdtree.nearest(query_point);
        StdOut.println(nearest);
        StdOut.println(nearest.distanceSquaredTo(query_point));
    }
}
