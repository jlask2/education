/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 2
 * Date Assigned: 2/12/2015
 * Date Due: 3/4/2015
 * Date Submitted: 3/11/2015 
 ***********************************/

package poset;

import java.util.PriorityQueue;

/**Abstract Class UAG Extends G - Undirected Adjacency Graph*/
abstract class UAG extends G{
	
    /**findMST method is used only for weighted implementations of UAG*/
	public void findMST(PriorityQueue<Edge> pqe, int numOfNodes){
		System.out.println("\nPerforming Kruskals Minimum Spanning Tree");
		MST kruskal = new MST(pqe, numOfNodes);
		System.out.println(kruskal.toString());
	}
}
