import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Comparator;

public class FastCollinearPoints {
    // array that holds the line segments that were found
    private LineSegment[] lineSegments;
    private Point[] minPoints;
    private Point[] maxPoints;
    private int numLineSegments;

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
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

        // count the number of line segments
        // create an array of the lines segments and the min/max points
        numLineSegments = 0;
        lineSegments = new LineSegment[numPts];
        minPoints = new Point[numPts];
        maxPoints = new Point[numPts];

        // create an auxiliary array used for sorting the points
        Point[] copyPoints = new Point[numPts];
        for (int i = 0; i < numPts; i++) {
            copyPoints[i] = points[i];
        }

        // for each point compare the slopes and search collinear points
        Comparator<Point> myComparator;
        int start, curPos, index, segmentAlreadyExists;
        Point point1, point2;

        for (int i = 0; i < numPts; i++) {
            // get the comparator
            myComparator = points[i].slopeOrder();

            // sort the copy of the points
            Arrays.sort(copyPoints, myComparator);

            // search for collinear points
            // can start at the second entry since the first point is the current point
            start = 1;

            while (start < numPts - 2) {
                // define the starting point for searching for points
                curPos = start + 1;

                // move along the array to find collinear points
                while (true) {
                    if (curPos > numPts - 1) {
                        curPos--;
                        break;
                    }

                    if (myComparator.compare(copyPoints[start], copyPoints[curPos]) != 0) {
                        curPos--;
                        break;
                    } else {
                        curPos++;
                    }

                }

                // check how many points were found
                // if 4 or more then add the line
                if (curPos - start + 1 >= 3) {
                    // create an array for finding the end points of the line segment
                    Point[] tempPointsArray = new Point[curPos - start + 2];
                    tempPointsArray[0] = copyPoints[0];
                    for (int j = 1; j < curPos - start + 2; j++)
                        tempPointsArray[j] = copyPoints[start - 1 + j];

                    // sort this temporary array based on Point
                    Arrays.sort(tempPointsArray);

                    // get the points for the line segment
                    point1 = tempPointsArray[0];
                    point2 = tempPointsArray[curPos - start + 1];

                    // check if these points are already forming a line segment
                    segmentAlreadyExists = 0;
                    index = 0;
                    while (index < numLineSegments && segmentAlreadyExists == 0) {
                        if (minPoints[index].compareTo(point1) == 0 && maxPoints[index].compareTo(point2) == 0) {
                            segmentAlreadyExists = 1;
                        }
                        index++;
                    }

                    // if the points do not form a duplicate line segment then add it
                    if (segmentAlreadyExists == 0) {
                        // check first if the array needs to be resized
                        if (numLineSegments == lineSegments.length) resize(2 * lineSegments.length);

                        // add the new data and increment the count of line segment
                        lineSegments[numLineSegments] = new LineSegment(point1, point2);
                        minPoints[numLineSegments] = point1;
                        maxPoints[numLineSegments] = point2;
                        numLineSegments++;
                    }

                }

                // update the start position
                start = curPos + 1;

            }

        }

    }

    // resize the line segments array
    private void resize(int newLen) {
        // resizes the line segments and min/max points arrays

        // create new arrays and copy data over
        LineSegment[] copyLineSegments = new LineSegment[newLen];
        Point[] copyMinPoints = new Point[newLen];
        Point[] copyMaxPoints = new Point[newLen];

        for (int i = 0; i < numLineSegments; i++) {
            copyLineSegments[i] = lineSegments[i];
            copyMinPoints[i] = minPoints[i];
            copyMaxPoints[i] = maxPoints[i];
        }

        // set the arrays to point to these new larger arrays
        lineSegments = copyLineSegments;
        minPoints = copyMinPoints;
        maxPoints = copyMaxPoints;
    }

    // the number of line segments
    public int numberOfSegments() {
        return numLineSegments;
    }

    // the line segments
    public LineSegment[] segments() {

        // return a copy of the internal array
        LineSegment[] returnArray = new LineSegment[numLineSegments];
        for (int i = 0; i < numLineSegments; i++)
            returnArray[i] = lineSegments[i];

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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();

    }

}
