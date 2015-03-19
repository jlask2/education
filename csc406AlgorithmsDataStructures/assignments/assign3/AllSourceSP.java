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

import java.util.ArrayList;
import java.util.PriorityQueue;

/*Algorithm Floyd(W)
//Input: the weight matrix W of a graph with no negative-length cycle
//Output: the distance matrix of the shortest paths’ lengths
//initialize the non-diagonal zeros to ∞.

Step 1: D = W		//O(n^2)
Step 2: for k = 1..n do	//  O(n^3)
	   for i = 1..n do
   	      for j = 1..n do
      		dij = min{dij,  dik + dkj  );
Step 3: return D

Analysis: the above algorithm is O(n^3) time complexity.*/


/**AllSourceSP Class: All-Source Shortest Path uses Floyd's Algorithm to find the shortest path*/
public class AllSourceSP{
	
	/**private data members*/
	private int[][] D;
	
	/**AllSourceSP Constructor: */
	AllSourceSP(int[][] W){
		D = W;
		for(int i = 0; i < D.length; i++){
			for(int j = 0; j < D.length; j++){
				if((D[i][j]) == 0 && (i != j)){
					D[i][j] = Integer.MAX_VALUE;
				}
			}
		}
	}
	
	protected int[][] generateD(){
		for(int k = 0; k < D.length; k++){
			for(int i = 0; i < D.length; i++){
				for(int j = 0; j < D.length; j++){
					if(D[i][j] > (D[i][k] + D[k][j]) && (D[i][k] + D[k][j]) > -1){//D[i][k] != Integer.MAX_VALUE && D[k][j] != Integer.MAX_VALUE){
						D[i][j] = D[i][k] + D[k][j];
					}
				}
			}
		}
		return D;
	}
		
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		String resultMatrixN = "\nThe resulting matrix N is: \n\n";
		for(int i  = 0; i < D.length; i++){
			resultMatrixN += "[";
			for(int j = 0; j < D[0].length; j++){
				resultMatrixN += " "+D[i][j];
			}
			resultMatrixN += " ]\n";
		}
		resultMatrixN +="\n";
		return resultMatrixN;
	}
}