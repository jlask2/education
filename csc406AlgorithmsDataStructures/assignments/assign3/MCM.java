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

/**MCM Class: Matrix Chain Multiplication determines the least number of matrix products needed*/
public class MCM{
	
	/**private data members*/
	private int[][] N;
	private int[] d;
	private int j;
	private int n;
	
/*	
	Step 1: for i = 0 to n-1 do  N i,i = 0
			Step 2: for  b = 1 to (n-1) do 
			Step 3:    for i = 0 to (n-b-1) do
					j = i + b 
			Step 4:             for k = i to (j-1) do 
			     		      Ni,j = min { N i,k + Nk+1,j + didk+1d j+1 }


			Analysis: On termination the algorithm gives N0,(n-1). The outer loop (Step 2), the middle loop (Step 3), and the innermost loop (Step 4) each is executed at most n times. Hence the total time complexity is O(n3). A more precise estimate is possible which is equal to O(n2).  

	*/
	/**MCM Constructor: Algorithm MCP(d0, d1, ….., dn)
//Input:  d0, d1, .., dn   represent the dimensions of  matrices A0, A1, A2, .., An-1 where the dimension of the matrix Ak is dk X dk+1.
//Output:  the minimal number of scalar multiplications to find the product of the matrices, A0, A1, A2, …, An-1.
*/
	MCM(int n, int[] dimArray){
		this.n = n;
		N = new int[n][n];
		d = dimArray;
		for(int i = 0; i < n; i++){
			N[i][i] = 0;
		}
	}
	
	protected void calculateMatrix(){
		for(int b = 1; b < n; b++){
			for(int i = 0; i < (n-b); i++){
					j = i + b; 
					for(int k = i; k < (j); k++){ 
							if((N[i][j] > N[i][k] + N[k+1][j] + d[i] * d[k+1] * d[j+1]) || (N[i][j] == 0)){
								N[i][j] = N[i][k] + N[k+1][j] + d[i] * d[k+1] * d[j+1];
							}
					}
			}
		}
	}
	
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		String resultMatrixN = "\nThe resulting matrix N is: \n\n";
		for(int i  = 0; i < N.length; i++){
			resultMatrixN += "[";
			for(int j = 0; j < N[0].length; j++){
				resultMatrixN += " "+N[i][j];
			}
			resultMatrixN += " ]\n";
		}
		resultMatrixN +="\n";
		return resultMatrixN;
	}
}