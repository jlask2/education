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
	PriorityQueue<Edge> pqe;
	int numOfNodes;
	int[] A;
	
	UnionFind(PriorityQueue<Edge> pqe, int numOfNodes){
		//this.pqe = pqe;
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
	
	protected int find(int x){
		return A[x];
	}
	
	protected int findRoot(int x){
		while(A[x] > 0){
			int temp = A[x]-1;
			//System.out.println("x: "+x+" temp: "+temp+" A[x]= "+A[x]+" A[A[x]-1]= "+A[A[x]-1]);
			if(A[A[x]-1] >= 0){
				A[x] = A[A[x]-1];
			}
			x = temp;
			//System.out.println("after x: "+x+" temp: "+temp+" A[x]= "+A[x]);
		}
		return x;
	}
	
	protected void union(int x, int y){
		if(A[x] < A[y]){
			//System.out.println("A[x]: "+A[x]+" | A[y]: "+A[y]);
		   	A[x] = A[x] + A[y];
		   	A[y] = x+1;
		   	//System.out.println("after A[x]: "+A[x]+" | A[y]: "+A[y]);
		}else{
			A[x] = A[x] + A[y];
			A[y] = x+1;
			//System.out.println("else A[x]: "+A[x]+" | A[y]: "+A[y]);
		}
	}
	
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		String r = "\n";
		for(int i = 0; i <  numOfNodes; i++){
			r += A[i]+" ";
			//System.out.print(""+A[i]+", ");
		}
		return r;
	}
	
}