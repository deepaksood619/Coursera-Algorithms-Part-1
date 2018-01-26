/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 7-Jan-2017
 *  Last updated           : 7-Jan-2017
 * 
 *  Compilation            : use DrJava
 *  Execution              : java-algs4 RandomizedQueue
 *
 *  Purpose of the program : A generic Randomized Queue with Iterator
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 *  Score                  : 100/100
**********************************************************************************************************************/

import java.util.NoSuchElementException;
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private int n;
    private Item[] q;

    /**
     * construct an empty randomized queue
     */
    public RandomizedQueue() {
        n = 0;
        q = (Item[]) new Object[2];
    }
    
    /**
     * is the randomized queue empty?
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * return the number of items on the randomized queue
     */
    public int size() {
        return n;
    }

    /**
     * add the item
     */
    public void enqueue(Item item) {
        if (item == null) { throw new IllegalArgumentException(); }
        if (n == q.length) resize(q.length*2);

        q[n++] = item;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = q[i];
        }
        q = temp;
    }

    /**
     * remove and return a random item
     */
    public Item dequeue() {
        if (isEmpty())  { throw new NoSuchElementException(); }
        int index = StdRandom.uniform(n);
        Item item = q[index];
        q[index] = q[--n];
        q[n] = null;

        if (n > 0 && n == q.length/4) resize(q.length / 2);

        return item;
    }

    /**
     * return a random item (but do not remove it)
     */
    public Item sample() {
        if (isEmpty())  { throw new NoSuchElementException(); }

        return q[StdRandom.uniform(n)];
    }

    /**
     * return an independent iterator over items in random order
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int current;
        private int[] random;

        public ArrayIterator() {
            this.random = new int[n];
            for (int i = 0; i < n; i++) {
                random[i] = i;
            }
            StdRandom.shuffle(random);
            current = 0;
        }
        
        public boolean hasNext() { return current != random.length; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (!hasNext()) throw new NoSuchElementException();
            return q[random[current++]];
        }
    }
}