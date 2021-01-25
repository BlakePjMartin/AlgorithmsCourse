import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;

public class BruteCollinearPoints {
    // array that holds the line segments that were found
    private LineSegment[] theLineSegments;
    private int numLineSegments = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        // check that the input is not null
        if (points == null) throw new IllegalArgumentException();

        // check that none of the points are null
        for (Point point : points) if (point == null) throw new IllegalArgumentException();

        // get the number of points that was passed
        int numPts = points.length;

        // check that there are no duplicate points
        for (int i = 0; i < numPts - 1; i++) {
            for (int j = i + 1; j < numPts; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException();
                }
            }
        }

        // create an oversized array of the lines segments
        this.theLineSegments = new LineSegment[numPts];

        // iterate over groups of four to find line segments
        double s1, s2, s3;
        for (int i = 0; i < numPts - 3; i++) {
            for (int j = i + 1; j < numPts - 2; j++) {
                for (int k = j + 1; k < numPts - 1; k++) {
                    // check at this point if the slope i->j and i->k are the same
                    // if not then we do not need to check the ii point
                    s1 = points[i].slopeTo(points[j]);
                    s2 = points[i].slopeTo(points[k]);

                    if (s1 == s2) {
                        for (int ii = k + 1; ii < numPts; ii++) {
                            s3 = points[i].slopeTo(points[ii]);
                            if (s1 == s3) {
                                addLineSegment(points[i], points[j], points[k], points[ii]);
                            }
                        }
                    }
                }
            }
        }

    }

    // helper function to add line segments
    private void addLineSegment(Point a, Point b, Point c, Point d) {
        // create an array of these points and sort them
        Point[] arrPoints = {a, b, c, d};
        Arrays.sort(arrPoints);

        // get the min and max points
        Point minPoint = arrPoints[0];
        Point maxPoint = arrPoints[3];

        theLineSegments[numLineSegments++] = new LineSegment(minPoint, maxPoint);

    }

    // the number of line segments
    public int numberOfSegments() {
        return numLineSegments;
    }

    // the line segments
    public LineSegment[] segments() {
        // create an array with the correct size
        LineSegment[] returnArray = new LineSegment[numLineSegments];

        // populate the array
        for (int i = 0; i < numLineSegments; i++) {
            returnArray[i] = theLineSegments[i];
        }

        return returnArray;
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
        BruteCollinearPoints collinear = new BruteCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }

}
