/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 2
 * Date Assigned: 2/12/2015
 * Date Due: 3/4/2015
 * Date Submitted: 3/11/2015 
 ***********************************/

package graph;

import java.util.PriorityQueue;

/**UnionFind Class: Contains methods for applying a weighted union find with a lazy path compression*/
public class UnionFind{
	
	/**private data members*/
	private PriorityQueue<Edge> pqe;
	private int numOfNodes;
	private int[] A;
	
	/**UnionFind Constructor: Initializes the Array with -1's*/
	UnionFind(PriorityQueue<Edge> pqe, int numOfNodes){
		this.numOfNodes = numOfNodes;
		A = new int[numOfNodes];
		for(int i = 0; i <  numOfNodes; i++){
			makeSet(i);
		}
		System.out.println(toString());
	}
	
	/**makeSet method: Simply creates the subset of the given element*/
	protected void makeSet(int x){
		A[x] = -1;
	}
	
	/**findRoot method: Finds the parent of the given element and determines what set the element belongs to*/
	protected int findRoot(int x){
		while(A[x] > 0){
			int temp = A[x]-1;
			if(A[A[x]-1] >= 0){
				A[x] = A[A[x]-1]; //lazy path compression
			}
			x = temp;
		}
		return x;
	}
	
	/**union method: Joins to subsets together, joining the smaller set to the larger set. Breaks ties discriminatly.*/
	protected void union(int x, int y){
		if(A[x] < A[y] || A[x] == A[y]){// if equal, join the set of y with the set of x
			A[x] = A[x] + A[y];
		   	A[y] = x+1;
		}else{
			A[y] = A[x] + A[y];
			A[x] = y+1;
		}
	}
	
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		String r = "\n";
		for(int i = 0; i <  numOfNodes; i++){
			r += A[i]+" ";
		}
		return r;
	}
}