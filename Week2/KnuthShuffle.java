import edu.princeton.cs.algs4.StdRandom;

public class KnuthShuffle {

    public static void shuffle(Comparable[] a) {
        int N = a.length;
        for (int i = 1; i < N; i++) {
            int rand = StdRandom.uniform(i);
            exch(a, i, rand);
        }
    }

    private static boolean less(Comparable v, Comparable w) {
        return v.compareTo(w) < 0;
    }

    private static void exch(Comparable[] a, int i, int j) {
        Comparable swap = a[i];
        a[i] = a[j];
        a[j] = swap;
    }

    public static void main(String[] args) {
        Integer[] arr = {1,2,3,4,5,6,7,8,9};
        KnuthShuffle.shuffle(arr);

        for (int i = 0; i < arr.length; i++) {
            System.out.print(arr[i] + " ");
        }
    }
}