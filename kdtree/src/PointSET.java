import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

import java.util.TreeSet;

public class PointSET {
    private TreeSet<Point2D> pSet;

    /********************************************************
     // construct an empty set of points
     ********************************************************/
    public PointSET() {
        pSet = new TreeSet<>();
    }


    /********************************************************
     // is the set empty?
     ********************************************************/
    public boolean isEmpty() {
        return pSet.isEmpty();
    }


    /********************************************************
     // number of points in the set
     ********************************************************/
    public int size() {
        return pSet.size();
    }


    /********************************************************
     // add the point to the set (if it is not already in the set)
     ********************************************************/
    public void insert(Point2D p) {
        // check if the point is null
        if (p == null) throw new IllegalArgumentException();

        // add to the point set
        pSet.add(p);
    }


    /********************************************************
     // does the set contain point p?
     ********************************************************/
    public boolean contains(Point2D p) {
        // check if the point is null
        if (p == null) throw new IllegalArgumentException();

        // check if the set contains the point
        return pSet.contains(p);
    }


    /********************************************************
     // draw all points to standard draw
     ********************************************************/
    public void draw() {
        // iterate over all the points
        for (Point2D point : pSet) {
            StdDraw.filledCircle(point.x(), point.y(), 0.005);
        }

    }


    /********************************************************
     // all points that are inside the rectangle (or on the boundary)
     ********************************************************/
    public Iterable<Point2D> range(RectHV rect) {
        // check if the rectangle is null
        if (rect == null) throw new IllegalArgumentException();

        // create a stack with all the points that are in the rectangle
        Stack<Point2D> stackPts = new Stack<Point2D>();

        // iterate over all the points
        // check if a point is in the given rectangle
        for (Point2D point : pSet) {
            if (rect.contains(point)) stackPts.push(point);
        }

        // return the stack
        return stackPts;
    }


    /********************************************************
     // a nearest neighbor in the set to point p; null if the set is empty
     ********************************************************/
    public Point2D nearest(Point2D p) {
        // check if the point is null
        if (p == null) throw new IllegalArgumentException();

        // find the nearest neighbour by checking all the points in the set
        Point2D nearestPt = null;
        double minDist = 0;
        for (Point2D point : pSet) {
            double curDist = point.distanceSquaredTo(p);
            if (nearestPt == null || curDist < minDist) {
                minDist = curDist;
                nearestPt = point;
            }
        }

        // return the nearest neighbour
        return nearestPt;
    }


    /********************************************************
     // unit testing of the methods (optional)
     ********************************************************/
    public static void main(String[] args) {
        // left empty
    }

}
