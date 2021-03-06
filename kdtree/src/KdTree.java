import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {

    private static final int VERTICAL = 0;
    private static final int HORIZONTAL = 1;

    private Node root;
    private double minDist;
    private Node nearestNode;
    private Stack<Point2D> inRange;

    /********************************************************
     // define structure of a node
     ********************************************************/
    private static class Node {
        private Node left;
        private Node right;
        private Point2D point;
        private int size;

        private Node(Point2D point, int size) {
            this.point = point;
            this.size = size;
        }
    }

    /********************************************************
     // construct an empty set of points
     ********************************************************/
    public KdTree() {
        this.root = null;
    }


    /********************************************************
     // is the set empty?
     ********************************************************/
    public boolean isEmpty() {
        return size() == 0;
    }


    /********************************************************
     // number of points in the set
     ********************************************************/
    public int size() {
        return size(root);
    }

    private int size(Node x) {
        if (x == null) return 0;
        return x.size;
    }


    /********************************************************
     // add the point to the set (if it is not already in the set)
     ********************************************************/
    public void insert(Point2D p) {
        // check if the point is null
        if (p == null) throw new IllegalArgumentException("calls insert() with a null point");

        root = insert(root, p, VERTICAL);
    }

    private Node insert(Node x, Point2D p, int step) {
        // check if the set is currently empty
        if (x == null) return new Node(p, 1);

        // compare the values of the current node to the given point
        double cmp = (step == HORIZONTAL) ? p.y() - x.point.y() : p.x() - x.point.x();
        int nextStep = (step == VERTICAL) ? HORIZONTAL : VERTICAL;

        // decide what to do based on the cmp
        if (cmp < 0)
            x.left = insert(x.left, p, nextStep);
        else if (cmp > 0 || !x.point.equals(p))
            x.right = insert(x.right, p, nextStep);

        // update the size
        x.size = size(x.left) + size(x.right) + 1;

        return x;
    }


    /********************************************************
     // create an iterator over all the points
     ********************************************************/
    private Iterable<Point2D> iterable() {
        // put all the points in a stack
        Stack<Point2D> stack = new Stack<Point2D>();
        addPoints(root, stack);

        return stack;
    }

    private void addPoints(Node x, Stack<Point2D> stack) {
        if (x == null) return;
        stack.push(x.point);
        addPoints(x.left, stack);
        addPoints(x.right, stack);
    }


    /********************************************************
     // does the set contain point p?
     ********************************************************/
    public boolean contains(Point2D p) {
        // check if the point is null
        if (p == null) throw new IllegalArgumentException("calls contains() with a null point");

        Node x = root;
        int step = VERTICAL;
        while (x != null) {
            double cmp = (step == HORIZONTAL) ? p.y() - x.point.y() : p.x() - x.point.x();
            if (cmp < 0)
                x = x.left;
            else if (cmp > 0)
                x = x.right;
            else {
                if (x.point.equals(p))
                    return true;
                else
                    x = x.right;
            }

            step = (step == VERTICAL) ? HORIZONTAL : VERTICAL;
        }

        return false;
    }


    /********************************************************
     // draw all points to standard draw
     ********************************************************/
    public void draw() {
        // iterate over all the points
        for (Point2D point : iterable())
            StdDraw.filledCircle(point.x(), point.y(), 0.005);
    }


    /********************************************************
     // all points that are inside the rectangle (or on the boundary)
     ********************************************************/
    public Iterable<Point2D> range(RectHV rect) {
        // check if the rectangle is null
        if (rect == null) throw new IllegalArgumentException();

        this.inRange = new Stack<Point2D>();

        rangeSearch(root, rect, VERTICAL, 0.0, 0.0, 1.0, 1.0);

        return this.inRange;
    }

    private void rangeSearch(Node node, RectHV rect, int step, double minX, double minY, double maxX, double maxY) {
        // if the node is null return
        if (node == null) return;

        // check if the current node is inside the rectangle
        if (rect.contains(node.point)) inRange.push(node.point);

        // info for checking left and right nodes
        Node firstSearchNode = node.left;
        Node secondSearchNode = node.right;
        double firstMaxX = (step == VERTICAL) ? node.point.x() : maxX;
        double firstMaxY = (step == HORIZONTAL) ? node.point.y() : maxY;
        double secondMinX = (step == VERTICAL) ? node.point.x() : minX;
        double secondMinY = (step == HORIZONTAL) ? node.point.y() : minY;

        int nextStep = (step == VERTICAL) ? HORIZONTAL : VERTICAL;

        // determine if the left, right, or both branches should be checked
        RectHV firstRect = new RectHV(minX, minY, firstMaxX, firstMaxY);
        RectHV secondRect = new RectHV(secondMinX, secondMinY, maxX, maxY);

        if (firstRect.intersects(rect))
            rangeSearch(firstSearchNode, rect, nextStep, minX, minY, firstMaxX, firstMaxY);
        if (secondRect.intersects(rect))
            rangeSearch(secondSearchNode, rect, nextStep, secondMinX, secondMinY, maxX, maxY);

    }


    /********************************************************
     // a nearest neighbor in the set to point p; null if the set is empty
     ********************************************************/
    public Point2D nearest(Point2D p) {
        // check if the point is null
        if (p == null) throw new IllegalArgumentException("calls nearest() with a null point");

        // check if the tree is empty
        if (isEmpty()) return null;

        this.minDist = 0;
        this.nearestNode = null;

        nearestNeighbour(root, p, VERTICAL, 0.0, 0.0, 1.0, 1.0);

        return nearestNode.point;
    }

    private void nearestNeighbour(Node node, Point2D p, int step, double minX, double minY, double maxX, double maxY) {
        // return if at a leaf of the tree
        if (node == null) return;

        // check the distance between the current node point and the point p
        double dist = node.point.distanceSquaredTo(p);
        if (this.nearestNode == null || dist < minDist) {
            this.minDist = dist;
            this.nearestNode = node;
        }

        // check if the search point is exactly the node point
        if (this.minDist == 0.0)
            return;

        // check the linear distance between the current point and the edge of the bounding box
        double checkDist = (step == VERTICAL) ? node.point.x() - p.x() : node.point.y() - p.y();
        Node firstSearchNode = (checkDist <= 0) ? node.right : node.left;
        Node secondSearchNode = (checkDist <= 0) ? node.left : node.right;
        double firstMinX = (checkDist <= 0 && step == VERTICAL) ? node.point.x() : minX;
        double firstMinY = (checkDist <= 0 && step == HORIZONTAL) ? node.point.y() : minY;
        double firstMaxX = (checkDist > 0 && step == VERTICAL) ? node.point.x() : maxX;
        double firstMaxY = (checkDist > 0 && step == HORIZONTAL) ? node.point.y() : maxY;
        double secondMinX = (checkDist > 0 && step == VERTICAL) ? node.point.x() : minX;
        double secondMinY = (checkDist > 0 && step == HORIZONTAL) ? node.point.y() : minY;
        double secondMaxX = (checkDist <= 0 && step == VERTICAL) ? node.point.x() : maxX;
        double secondMaxY = (checkDist <= 0 && step == HORIZONTAL) ? node.point.y() : maxY;

        int nextStep = (step == VERTICAL) ? HORIZONTAL : VERTICAL;

        // go down the path that contains the test point
        nearestNeighbour(firstSearchNode, p, nextStep, firstMinX, firstMinY, firstMaxX, firstMaxY);

        // check if the other direction is too far away - if not go down the path
        Node nextNode = checkDist > 0 ? node.right : node.left;
        RectHV secondSearchRect = secondSearchNode == null ? null :
                new RectHV(secondMinX, secondMinY, secondMaxX, secondMaxY);
        if (secondSearchRect == null || secondSearchRect.distanceSquaredTo(p) > this.minDist)
            return;
        nearestNeighbour(nextNode, p, nextStep, secondMinX, secondMinY, secondMaxX, secondMaxY);

    }

    /********************************************************
     // unit testing of the methods (optional)
     ********************************************************/
    public static void main(String[] args) {
        // left empty

    }

}
