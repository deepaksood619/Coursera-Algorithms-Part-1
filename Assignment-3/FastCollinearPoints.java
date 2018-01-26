/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 14-Jan-2017
 *  Last updated           : 14-Jan-2017
 * 
 *  Compilation            : javac-algs4 FastCollinearPoints.java
 *  Execution              : java-algs4 FastCollinearPoints
 *
 *  Purpose of the program : To calculate collinear points using a fast algorithm
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/collinear.html
 *  Score                  : 100/100
**********************************************************************************************************************/
import java.util.ArrayList;
import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdDraw;

public class FastCollinearPoints {

    private final ArrayList<LineSegment> segments = new ArrayList<>();

    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null)
            throw new IllegalArgumentException();

        checkNullEntries(points);

        checkDuplicateEntries(points);

        Point[] pointCopy = points.clone();
        Arrays.sort(pointCopy);
        
        for (int i = 0; i < pointCopy.length - 3; i++) {
            Arrays.sort(pointCopy);

            // Sort the points according to the slopes they makes with p.
            // Check if any 3 (or more) adjacent points in the sorted order
            // have equal slopes with respect to p. If so, these points,
            // together with p, are collinear.

            Arrays.sort(pointCopy, pointCopy[i].slopeOrder());

            for (int p = 0, first = 1, last = 2; last < pointCopy.length; last++) {
                // find last collinear to p point
                while (last < pointCopy.length
                        && Double.compare(pointCopy[p].slopeTo(pointCopy[first]), pointCopy[p].slopeTo(pointCopy[last])) == 0) {
                    last++;
                }
                // if found at least 3 elements, make segment if it's unique
                if (last - first >= 3 && pointCopy[p].compareTo(pointCopy[first]) < 0) {
                    segments.add(new LineSegment(pointCopy[p], pointCopy[last - 1]));
                }
                // Try to find next
                first = last;
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

    // the number of line segments
    public int numberOfSegments() {
        return segments.size();
    }

    // the line segments
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
                StdOut.println(segment);
                segment.draw();
        }
        StdDraw.show();
    }
}