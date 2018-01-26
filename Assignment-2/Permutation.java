/*********************************************************************************************************************
 *  Author                 : Deepak Kumar Sood
 *  Date                   : 7-Jan-2017
 *  Last updated           : 7-Jan-2017
 * 
 *  Compilation            : use DrJava
 *  Execution              : java-algs4 Permutation 3 < distinct.txt
 *
 *  Purpose of the program : Client program for using RandomizedQueue
 *  Assingment link        : http://coursera.cs.princeton.edu/algs4/assignments/queues.html
 *  Score                  : 100/100
**********************************************************************************************************************/

import edu.princeton.cs.algs4.StdIn;

public class Permutation {
   public static void main(String[] args) {

        int k = Integer.parseInt(args[0]);

        RandomizedQueue<String> queue = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            queue.enqueue(StdIn.readString());
        }
        
        for (int i = 0; i < k; i++) {
            System.out.println(queue.dequeue());
        }
   }
}