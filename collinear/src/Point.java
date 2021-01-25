/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param x the <em>x</em>-coordinate of the point
     * @param y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        // check if this point and that points are the same
        if (compareTo(that) == 0) return Double.NEGATIVE_INFINITY;

        // check if the x coordinates are the same
        if (this.x == that.x) return Double.POSITIVE_INFINITY;

        // check if the y coordinates are the same
        if (this.y == that.y) return (double) +0;

        // use the slope formula
        return (double) (that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     * point (x0 = x1 and y0 = y1);
     * a negative integer if this point is less than the argument
     * point; and a positive integer if this point is greater than the
     * argument point
     */
    public int compareTo(Point that) {
        if (this.y < that.y) return -1;
        if (this.y > that.y) return +1;
        if (this.x < that.x) return -1;
        if (this.x > that.x) return +1;
        return 0;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        return new BySlope(this);
    }

    private static class BySlope implements Comparator<Point> {
        // the point that is used to check the slope of the other 2 points given
        final Point p;

        public BySlope(Point p) {
            this.p = p;
        }

        public int compare(Point a, Point b) {
            // get the slope the points a and b make with this point
            double s1 = p.slopeTo(a);
            double s2 = p.slopeTo(b);

            // check the results
            return Double.compare(s1, s2);
        }
    }


    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        // create points for testing purposes
        Point myPt1 = new Point(19000, 10000);
        Point myPt2 = new Point(18000, 10000);
        Point myPt3 = new Point(32000, 10000);
        Point myPt4 = new Point(21000, 10000);
        Point myPt5 = new Point(1234, 5678);
        Point myPt6 = new Point(14000, 10000);

        // check the compare function
        StdOut.println("Use the compareTo function:");
        StdOut.println(myPt1.compareTo(myPt2));
        StdOut.println(myPt1.compareTo(myPt3));
        StdOut.println(myPt1.compareTo(myPt4));
        StdOut.println(myPt1.compareTo(myPt5));
        StdOut.println(myPt1.compareTo(myPt6));

        // check the slope function
        StdOut.println("\nUse the slopeTo function:");
        StdOut.println(myPt1.slopeTo(myPt2));
        StdOut.println(myPt1.slopeTo(myPt3));
        StdOut.println(myPt1.slopeTo(myPt4));
        StdOut.println(myPt1.slopeTo(myPt5));
        StdOut.println(myPt1.slopeTo(myPt6));

        // compare the slope order of points
        StdOut.println("\nUse the slopeOrder comparison function:");
        StdOut.println(myPt1.slopeOrder().compare(myPt2, myPt3));
    }
}
