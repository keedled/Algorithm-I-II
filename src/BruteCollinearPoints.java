import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class BruteCollinearPoints {
    private ArrayList<LineSegment> segmentLists = new ArrayList<>();
    public BruteCollinearPoints(Point[] points)    // finds all line segments containing 4 points
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
        if(points.length<4)return ;
        //Point[] tmp = Arrays.copyOf(points, points.length);
        Arrays.sort(points);

        for(int i = 0;i< points.length;i++){
            for(int j = i+1;j< points.length;j++){
                for(int k = j+1;k< points.length;k++){
                    for(int m = k +1 ;m<points.length;m++){
                        //如果斜率相等，将最小的点和最大的点构成的线段置入
                        if((points[i].slopeTo(points[j]) == points[i].slopeTo(points[k])) && (points[i].slopeTo(points[j]) == points[i].slopeTo(points[m]))){

                            Point[] segmentPoints = {points[i],points[j],points[k],points[m]};
                            Arrays.sort(segmentPoints);
                            segmentLists.add(new LineSegment(segmentPoints[0],segmentPoints[3]));

                        }
                    }
                }
            }
        }
    }
    public int numberOfSegments()        // the number of line segments
    {
        return segmentLists.size();
    }
    public LineSegment[] segments()                // the line segments
    {
        LineSegment[] result = new LineSegment[segmentLists.size()];
        return segmentLists.toArray(result);
    }

    public static void main(String[] args) {

        // read the n points from a file
//        In in = new In(args[0]);
//        int n = in.readInt();
//        Point[] points = new Point[n];
//        for (int i = 0; i < n; i++) {
//            int x = in.readInt();
//            int y = in.readInt();
//            points[i] = new Point(x, y);
//        }
        int n = 6;
        Point[] points = new Point[n];
        points[0] = new Point(19,10);
        points[1] = new Point(18,10);
        points[2] = new Point(32,10);
        points[3] = new Point(21,10);
        points[4] = new Point(12,34);
        points[5] = new Point(14,10);

        // draw the points
        StdDraw.enableDoubleBuffering();
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
        for (Point p : points) {
            p.draw();
        }
        StdDraw.show();

        // print and draw the line segments
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);

        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }



}