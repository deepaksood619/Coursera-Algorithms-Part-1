/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 25-Jan-2017
 *  Last updated           : 25-Jan-2017
 * 
 *  Compilation            : javac-algs4 KdTree.java
 *  Execution              : java-algs4 KdTree
 *
 *  Purpose of the program : Implementation of KdTree for range search and nearest neighbor search
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/kdtree.html
 *  Score                  : 99/100
**********************************************************************************************************************/
import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.Queue;

public class KdTree {

    private Node root;
    private int size;

    private static class Node {
        // the 2d point
        private final Point2D p;

        // the axis-aligned rectangle corresponding to this node
        private final RectHV rect;

        // the left/bottom subtree
        private Node lb;

        // the right/top subtree
        private Node rb;

        // public constructor
        public Node(Point2D p, RectHV rect) {
            this.p = p;

            RectHV r = rect;
            if (r == null)
                r = new RectHV(0, 0, 1, 1);
            this.rect = r;
        }
    }

    // construct an empty set of points 
    public KdTree() {
        root = null;
        size = 0;
    }

    // is the set empty? 
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set 
    public int size() {
        return size;
    }

    // insert a horizontal node
    private Node insertH(Node node, Point2D p, RectHV rect) {
        if (node == null) {
            size++;
            return new Node(p, rect);
        }
        if (node.p.equals(p)) return node;

        RectHV r;
        int cmp = Point2D.Y_ORDER.compare(node.p, p);
        if (cmp > 0) {
            if (node.lb == null)
                r = new RectHV(rect.xmin(), rect.ymin(), rect.xmax(), node.p.y());
            else
                r = node.lb.rect;
            node.lb = insertV(node.lb, p, r);
        } else {
            if (node.rb == null)
                r = new RectHV(rect.xmin(), node.p.y(), rect.xmax(), rect.ymax());
            else
                r = node.rb.rect;
            node.rb = insertV(node.rb, p, r);
        }

        return node;
    }

    // insert a vertical node
    private Node insertV(Node node, Point2D p, RectHV rect) {
        if (node == null) {
            size++;
            return new Node(p, rect);
        }

        // if two points are same
        if (node.p.equals(p)) return node;

        RectHV r;
        int cmp = Point2D.X_ORDER.compare(node.p, p);
        if (cmp > 0) {
            if (node.lb == null)
                r = new RectHV(rect.xmin(), rect.ymin(), node.p.x(), rect.ymax());
            else
                r = node.lb.rect;
            node.lb = insertH(node.lb, p, r);
        } else {
            if (node.rb == null)
                r = new RectHV(node.p.x(), rect.ymin(), rect.xmax(), rect.ymax());
            else
                r = node.rb.rect;
            node.rb = insertH(node.rb, p, r);
        }

        return node;
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (isEmpty())
            root = insertV(root, p, null);
        else
            root = insertV(root, p, root.rect);
    }

    private boolean contains(Node node, Point2D p, boolean vert) {
        if (node == null) return false;
        if (node.p.equals(p)) return true;
        int cmp;
        if (vert)
            cmp = Point2D.X_ORDER.compare(node.p, p);
        else
            cmp = Point2D.Y_ORDER.compare(node.p, p);
        if (cmp > 0)
            return contains(node.lb, p, !vert);
        else
            return contains(node.rb, p, !vert);
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        return contains(root, p, true);
    }

    private void draw(Node node, boolean vert) {
        if (node.lb != null) draw(node.lb, !vert);
        if (node.rb != null) draw(node.rb, !vert);

        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(node.p.x(), node.p.y());

        double xmin, ymin, xmax, ymax;
        if (vert) {
            StdDraw.setPenColor(StdDraw.RED);
            xmin = node.p.x();
            xmax = node.p.x();
            ymin = node.rect.ymin();
            ymax = node.rect.ymax();
        } else {
            StdDraw.setPenColor(StdDraw.BLUE);
            ymin = node.p.y();
            ymax = node.p.y();
            xmin = node.rect.xmin();
            xmax = node.rect.xmax();
        }
        StdDraw.setPenRadius();
        StdDraw.line(xmin, ymin, xmax, ymax);
    }

    // draw all points to standard draw 
    public void draw() {
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        if (isEmpty()) return;
        draw(root, true);
    }

    private void range(Node node, RectHV rect, Queue<Point2D> queue) {
        if (node == null)
            return;
        if (rect.contains(node.p))
            queue.enqueue(node.p);
        if (node.lb != null && rect.intersects(node.lb.rect))
            range(node.lb, rect, queue);
        if (node.rb != null && rect.intersects(node.rb.rect))
            range(node.rb, rect, queue);
    }
   
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) throw new IllegalArgumentException();

        Queue<Point2D> queue = new Queue<Point2D>();
        range(root, rect, queue);
        return queue;
    }

    private Point2D nearest(Node node, Point2D p, Point2D mp, boolean vert) {
        Point2D min = mp;

        if (node == null) return min;
        if (p.distanceSquaredTo(node.p) < p.distanceSquaredTo(min))
            min = node.p;

        if (vert) {
            if (node.p.x() < p.x()) {
                min = nearest(node.rb, p, min, !vert);
                if (node.lb != null && (min.distanceSquaredTo(p) > node.lb.rect.distanceSquaredTo(p)))
                    min = nearest(node.lb, p, min, !vert);
            } else {
                min = nearest(node.lb, p, min, !vert);
                if (node.rb != null && (min.distanceSquaredTo(p) > node.rb.rect.distanceSquaredTo(p)))
                    min = nearest(node.rb, p, min, !vert);
            }
        } else {
            if (node.p.y() < p.y()) {
                min = nearest(node.rb, p, min, !vert);
                if (node.lb != null && (min.distanceSquaredTo(p) > node.lb.rect.distanceSquaredTo(p)))
                    min = nearest(node.lb, p, min, !vert);
            } else {
                min = nearest(node.lb, p, min, !vert);
                if (node.rb != null && (min.distanceSquaredTo(p) > node.rb.rect.distanceSquaredTo(p)))
                    min = nearest(node.rb, p, min, !vert);
            }
        }
        return min;
    }

    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) throw new IllegalArgumentException();

        if (isEmpty()) return null;
        return nearest(root, p, root.p, true);
    }
}