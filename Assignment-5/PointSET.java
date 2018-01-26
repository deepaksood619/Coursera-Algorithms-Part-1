/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 25-Jan-2017
 *  Last updated           : 25-Jan-2017
 * 
 *  Compilation            : javac-algs4 PointSET.java
 *  Execution              : java-algs4 PointSET
 *
 *  Purpose of the program : Brute Force implementation for range search and nearest neighbor search
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/8puzzle.html
 *  Score                  : 99/100
**********************************************************************************************************************/
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.Point2D;

import edu.princeton.cs.algs4.StdDraw;

import edu.princeton.cs.algs4.Stack;

public class PointSET {

    private final SET<Point2D> set;

    // construct an empty set of points 
    public PointSET() {
        set = new SET<Point2D>();
    }

    // is the set empty? 
    public boolean isEmpty() {
        return set.isEmpty();
    }

    // number of points in the set 
    public int size() {
        return set.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        set.add(p);
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();
        return set.contains(p);
    }

    // draw all points to standard draw 
    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);

        for (Point2D dot : set)
            StdDraw.point(dot.x(), dot.y());
    }
   
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Stack<Point2D> stack = new Stack<Point2D>();

        for (Point2D dot : set)
            if (rect.contains(dot))
                stack.push(dot);
        
        return stack;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (isEmpty()) return null;

        double dist, minDist = Double.POSITIVE_INFINITY;
        Point2D nn = null;

        for (Point2D dot : set) {
            dist = p.distanceSquaredTo(dot);
            if (dist < minDist) {
                minDist = dist;
                nn = dot;
            }
        }
        return nn;
    }
}