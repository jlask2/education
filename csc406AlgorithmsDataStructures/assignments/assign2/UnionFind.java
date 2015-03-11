/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 2
 * Date Assigned: 2/12/2015
 * Date Due: 3/4/2015
 * Date Submitted: 3/4/2015 
 ***********************************/

package poset;

import java.util.PriorityQueue;

public class UnionFind{
	
	/**private data members*/
	private PriorityQueue<Edge> pqe;
	private int numOfNodes;
	private int[] A;
	
	UnionFind(PriorityQueue<Edge> pqe, int numOfNodes){
		this.numOfNodes = numOfNodes;
		A = new int[numOfNodes];
		for(int i = 0; i <  numOfNodes; i++){
			makeSet(i);
		}
		System.out.println(toString());
	}
	
	protected void makeSet(int x){
		A[x] = -1;
	}
	
	protected int findRoot(int x){
		while(A[x] > 0){
			int temp = A[x]-1;
			if(A[A[x]-1] >= 0){
				A[x] = A[A[x]-1];
			}
			x = temp;
		}
		return x;
	}
	
	protected void union(int x, int y){
		if(A[x] < A[y] || A[x] == A[y]){
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