import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {
    private ArrayList<LineSegment> lineSegments = new ArrayList<>();
    public FastCollinearPoints(Point[] points)     // finds all line segments containing 4 or more points
    {
        if(points == null)throw new IllegalArgumentException("there is no points!");
        for(Point point : points){
            if(point == null){
                throw new IllegalArgumentException("there is one point which is null!");
            }
        }
        for(int i = 0;i< points.length;i++) {

            for (int j = i + 1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("there is one point like another point!");
                }
            }
        }
        if (points.length < 4) {
            return;
        }
        int len = points.length;
        Point[] tmp = Arrays.copyOf(points,points.length);

        for(Point p : points){
            Arrays.sort(tmp,p.slopeOrder());
            for(int i = 1;i< points.length;){
                int j = i+1;
                while (j< points.length && p.slopeTo(tmp[j]) == p.slopeTo(tmp[i])){
                    j++;
                }
                if(j-i>=3 && min(tmp, i, j - 1).compareTo(p) > 0){
                    lineSegments.add(new LineSegment(tmp[0], max(tmp, i, j - 1)));
                }
                i = j;
            }
        }
    }
    private Point min(Point[] arrarys,int lo,int hi){
        Point point = arrarys[lo];
        for(int i = lo + 1;i<=hi;i++){
            if(point.compareTo(arrarys[i]) > 0){
                point = arrarys[i];
            }
        }
        return point;
    }
    private Point max(Point[] arrarys,int lo,int hi){
        Point point = arrarys[lo];
        for(int i = lo + 1;i<=hi;i++){
            if(point.compareTo(arrarys[i]) < 0){
                point = arrarys[i];
            }
        }
        return point;
    }
    public int numberOfSegments()        // the number of line segments
    {
        return lineSegments.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] res = new LineSegment[lineSegments.size()];
        return lineSegments.toArray(res);
    }
    public static void main(String[] args) {

        // read the n points from a file
        In in = new In(args[0]);
        int n = in.readInt();
        Point[] points = new Point[n];
        for (int i = 0; i < n; i++) {
            int x = in.readInt();
            int y = in.readInt();
            points[i] = new Point(x, y);
        }

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }


}