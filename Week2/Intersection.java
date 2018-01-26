import edu.princeton.cs.algs4.Merge;

public class Intersection {
    class Point implements Comparable<Point> {
        private int x;
        private int y;

        public Point(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public int compareTo(Point that) {
            if (that.x > this.x) return -1;
            if (that.x < this.x) return 1;
            if (that.y > this.y) return -1;
            if (that.y < this.y) return 1;
            return 0;
        }

        public int countIntersection(Point[] a, Point[] b) {
            Merge.sort(a);
            Merge.sort(b);

            int i = 0;
            int j = 0;
            int count = 0;

            while (i < a.length && j < b.length) {
                int val = a[i].compareTo(b[i]);
                if (val == 0) {
                    count++;
                    i++;
                    j++;
                } else if (val < 0) i++;
                else j++;
            }
            return count;
        }
    }
}