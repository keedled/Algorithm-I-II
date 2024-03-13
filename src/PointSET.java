//import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Iterator;

public class PointSET {
    private SET<Point2D> sets;
    public         PointSET()                               // construct an empty set of points
    {
        sets = new SET<>();
    }
    public           boolean isEmpty()                      // is the set empty?
    {
        return sets.isEmpty();
    }
    public               int size()                         // number of points in the set
    {
        return sets.size();
    }
    public              void insert(Point2D p)              // add the point to the set (if it is not already in the set)
    {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }
        sets.add(p);
    }
    public           boolean contains(Point2D p)            // does the set contain point p?
    {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }
        return sets.contains(p);
    }
    public              void draw()                         // draw all points to standard draw
    {
        Iterator iter = sets.iterator();
        while(iter.hasNext()){
            Point2D p = (Point2D) iter.next();
            p.draw();
        }
    }
    public Iterable<Point2D> range(RectHV rect)             // all points that are inside the rectangle (or on the boundary)、
    {
        if (rect == null) {
            throw new IllegalArgumentException("rect is null");
        }
        ArrayList<Point2D> Range_Sets = new ArrayList<>();
        for(Point2D p : sets){
            if(rect.contains(p)){
                Range_Sets.add(p);
            }
        }
        return Range_Sets;
    }

    public           Point2D nearest(Point2D p)             // a nearest neighbor in the set to point p; null if the set is empty
    {
        if (p == null) {
            throw new IllegalArgumentException("point is null");
        }
        Point2D min_p = null;
        double min_distance = Double.POSITIVE_INFINITY;
        //对于 Double 类型，无限大可以用 Double.POSITIVE_INFINITY 表示正无限大，而 Double.NEGATIVE_INFINITY 表示负无限大。这些值可以通过某些数学运算得到，比如除以零。
        //对于 Float 类型，同样有 Float.POSITIVE_INFINITY 和 Float.NEGATIVE_INFINITY。
        for(Point2D ps : sets){
            double dis = p.distanceTo(ps);
            if(dis < min_distance){
                min_distance = dis;
                min_p = ps;
            }
        }
        if(min_distance == Double.POSITIVE_INFINITY){
            return null;
        }
        return min_p;
    }
    public static void main(String[] args)                  // unit testing of the methods (optional)
    {
        ArrayList<Point2D> point2DS = new ArrayList<>();
        PointSET set = new PointSET();
        for(int i = 1;i<=5;i++){
            Point2D p = new Point2D(i,5);
            point2DS.add(p);
            set.insert(p);
        }
        StdOut.println(set.contains(point2DS.get(2)));
        set.draw();
        StdOut.println(set.nearest(new Point2D(3,2)));
    }
}