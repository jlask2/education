/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 3
 * Date Assigned: 3/4/2015
 * Date Due: 3/25/2015
 * Date Submitted: 3/25/2015 
 ***********************************/

package graph;

import java.util.Stack;

/**TransitiveClosure Class: Uses Warshall's Algorithm to show that a path exists between nodes*/
public class TransitiveClosure{
	
	/**private data members*/
	private int[][] R;
	/*
	Algorithm Warshall(A)
	//Input:  the adjacency matrix A of a digraph with n vertices
	//Output: the transitive closure of the digraph.

	Step 1: R = A		//O(n^2)
	Step 2: for k = 1..n do	//  O(n^3)
	  	   for i = 1..n do
	     	      for j = 1..n do
	        		if  rik = 1 and rkj =1 then rij = 1
	step 3: return R.

	Analysis: Warshall�s Algorithm is O(n^3) time complexity.

	Note:
	1. We don�t have to write the last step elaborately as     if (rijk-1 = 1) or ( rikk-1 = 1 and rkjk-1 = 1) then rijk = 1.
	2. What is the space complexity of the algorithm?
	3. Warshall�s algorithm when implemented on a bit matrix could be much more efficient with a better constant factor in O(n^3)  OR-operations 

	Step 1: R = A		//O(n^2)
	Step 2: for k = 1..n do	//  O(n^2)  OR-operations
	  	   for i = 1..n do
	         		if  rik = 1 then Ri = Ri OR Rk
	step 3: return R.*/

	
	/**TransitiveClosure Constructor: */
	TransitiveClosure(int[][] A){
		R = A;
	}
	
	/*protected int[][] generateR(){
		for(int k = 1; k < R.length; k++){
			for(int i = 1; i < R.length; i++){
				if(R[i][k] == 1){
					R[i] = R[i] || R[k];
				}
			}
		}
		return R;
	}*/
	
	protected int[][] generateR(){
		for(int k = 0; k < R.length; k++){
			for(int i = 0; i < R.length; i++){
				for(int j = 0; j < R.length; j++){
					if(R[i][k] == 1 && R[k][j] == 1){
						R[i][j] = 1;
					}
				}
			}
		}
		return R;
	}
	
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		String resultMatrixN = "\nThe resulting matrix N is: \n\n";
		for(int i  = 0; i < R.length; i++){
			resultMatrixN += "[";
			for(int j = 0; j < R[0].length; j++){
				resultMatrixN += " "+R[i][j];
			}
			resultMatrixN += " ]\n";
		}
		resultMatrixN +="\n";
		return resultMatrixN;
	}
}