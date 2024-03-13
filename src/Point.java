import java.util.Comparator;
import edu.princeton.cs.algs4.StdDraw;
public class Point implements Comparable<Point> {
    private final int x;
    private final int y;

    public Point(int x, int y)                         // constructs the point (x, y)
    {
        this.x = x;
        this.y = y;
    }
    public   void draw()                               // draws this point
    {
        StdDraw.point(x, y);
    }
    public   void drawTo(Point that)                   // draws the line segment from this point to that point
    {
        StdDraw.line(this.x, this.y, that.x, that.y);
    }
    public String toString()                           // string representation
    {
        return "(" + x + ", " + y + ")";
    }

    public int compareTo(Point that)     // compare two points by y-coordinates, breaking ties by x-coordinates
    {
        if(this.y < that.y)return -1;
        else if(this.y > that.y)return 1;
        else{
            if(this.x < that.x)return -1;
            else if(this.x > that.x)return 1;
            return 0;
        }
    }
    public double slopeTo(Point that)       // the slope between this point and that point
    {
        if(this.y == that.y)return +0.0;
        else if(this.x == that.x && this.y == that.y)return Double.NEGATIVE_INFINITY;
        else if(this.x == that.x)return Double.POSITIVE_INFINITY;
        else return (this.y - that.y) / (this.x - that.x);
    }
    public Comparator<Point> slopeOrder()            // compare two points by slopes they make with this point
    {
//        return (point1, point2) -> {                  //lambda版本
//            double slope1 = slopeTo(point1);
//            double slope2 = slopeTo(point2);
//            return Double.compare(slope1, slope2);
//        };
        return new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {
                return Double.compare(slopeTo(p1), slopeTo(p2));
            }
        };
    }
}