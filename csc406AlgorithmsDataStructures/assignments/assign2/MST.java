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

public class MST{
	PriorityQueue<Edge> pqe;
	int[] A;
	
	MST(PriorityQueue<Edge> pqe){
		this.pqe = pqe;
		A = new int[0];//numNodes()];
	}
	
	protected void makeSet(int x){
		A[x] = x;
	}
	
	protected int find(int x){
		return A[x];
	}
	
	protected int findRoot(int x){
		while(A[x]>0){
			int temp = A[x];
			A[x] = A[A[x]];
			x = temp;
		}
		return x;
	}
	
	protected void union(int x, int y){
		find(x);
		A[y] = A[x];
	}
	
}