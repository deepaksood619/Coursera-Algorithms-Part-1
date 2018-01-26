/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 14-Jan-2017
 *  Last updated           : 14-Jan-2017
 * 
 *  Compilation            : javac-algs4 Point.java
 *  Execution              : java-algs4 Point
 *
 *  Purpose of the program : To calculate collinear points using brute force.
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 *  Score                  : 100/100
**********************************************************************************************************************/
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class BruteCollinearPoints {
    
    private final ArrayList<LineSegment> segments;

    /**
     * Finds all line segments containing 4 points
     */
    public BruteCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException("Points cannot be null");

        checkNullEntries(points);

        checkDuplicateEntries(points);

        Point[] pointCopy = points.clone();
        Arrays.sort(pointCopy);

        segments = new ArrayList<LineSegment>();

        for (int p = 0; p < pointCopy.length - 3; p++) {
            for (int q = p + 1; q < pointCopy.length - 2; q++) {
                for (int r = q + 1; r < pointCopy.length - 1; r++) {
                    for (int s = r + 1; s < pointCopy.length; s++) {
                        if (pointCopy[p].slopeTo(pointCopy[q]) == pointCopy[p].slopeTo(pointCopy[r]) &&
                                pointCopy[p].slopeTo(pointCopy[q]) == pointCopy[p].slopeTo(pointCopy[s])) {
                            segments.add(new LineSegment(pointCopy[p], pointCopy[s]));
                        }
                    }
                }
            }
        }
    }

    private void checkNullEntries(Point[] points) {
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("Null entries in array");
            }
        }
    }

    private void checkDuplicateEntries(Point[] points) {
        for (int i = 0; i < points.length - 1; i++) {
            for (int j = i+1; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    throw new IllegalArgumentException("Duplicate points in array");
                }
            }
        }
    }

    /**
     * The number of line segments
     */
    public int numberOfSegments() {
        return segments.size();
    }

    /**
     * The line segments
     */
    public LineSegment[] segments() {
        return segments.toArray(new LineSegment[segments.size()]);
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