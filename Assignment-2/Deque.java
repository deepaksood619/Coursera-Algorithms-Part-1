/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 7-Jan-2017
 *  Last updated           : 7-Jan-2017
 * 
 *  Compilation            : use DrJava
 *  Execution              : java-algs4 Dequeue
 *
 *  Purpose of the program : A generic Double Ended Queue with Iterator
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 *  Score                  : 100/100
**********************************************************************************************************************/

import java.util.NoSuchElementException;
import java.util.Iterator;

public class Deque<Item> implements Iterable<Item> {

    private int first, last, n;
    private Item[] dq;

    /**
     * construct an empty deque
     */
    public Deque() {
        first = 0;
        last = 0;
        n = 0;
        dq = (Item[]) new Object[2];
    }

    /**
     * is the deque empty?
     */
    public boolean isEmpty() {
        return n == 0;
    }

    /**
     * return the number of items on the deque
     */
    public int size() {
        return n;
    }

    /**
     * add the item to the front
     */
    public void addFirst(Item item) {
        if (item == null) throw new IllegalArgumentException();
        
        if (n == dq.length) resize(dq.length*2);

        if (first == last && dq[first] == null) {
            dq[first] = item;
        } else if (first == 0) {
            first = dq.length-1;
            dq[first] = item;
        } else {
            first--;
            dq[first] = item;
        }
        n++;
    }

    /**
     * add the item to the end
     */
    public void addLast(Item item) {
        if (item == null) throw new IllegalArgumentException();
        
        if (n == dq.length) resize(dq.length*2);
        
        if (first == last && dq[last] == null) {
            dq[last] = item;
        } else if (last == dq.length-1) {
            last = 0;
            dq[last] = item;
        } else {
            last++;
            dq[last] = item;
        }
        n++;
    }

    private void resize(int capacity) {
        Item[] temp = (Item[]) new Object[capacity];
        for (int i = 0; i < n; i++) {
            temp[i] = dq[(first + i) % dq.length];
        }
        dq = temp;
        first = 0;
        last = n-1;
    }

    /**
     * remove and return the item from the front
     */
    public Item removeFirst() {
        if (!isEmpty()) {
            Item item = dq[first];
            dq[first] = null;

            if(first == dq.length-1) {
                first = 0;
            } else {
                first++;
            }
            
            n--;
            
            if (n > 0 && n == dq.length/4) resize(dq.length/2);
            
            return item;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * remove and return the item from the end
     */
    public Item removeLast() {
        if (!isEmpty()) {
            Item item = dq[last];
            dq[last] = null;
            if (last == 0 && n > 1) {
                last = dq.length - 1;
            } else if (last != 0 && n != 1) {
                last--;
            }
            
            n--;
            
            if (n > 0 && n == dq.length/4) resize(dq.length/2);

            return item;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * return an iterator over items in order from front to end
     */
    public Iterator<Item> iterator() {
        return new ArrayIterator();
    }

    private class ArrayIterator implements Iterator<Item> {
        private int i = 0;
        public boolean hasNext() {
            return i < n;
        }

        public void remove() {
            throw new UnsupportedOperationException();
        }

        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            Item item = dq[(i + first) % dq.length];
            i++;
            return item;
        }
    }
}