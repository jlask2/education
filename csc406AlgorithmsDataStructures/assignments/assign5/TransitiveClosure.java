/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 3
 * Date Assigned: 3/4/2015
 * Date Due: 3/25/2015
 * Date Submitted: 3/26/2015
 ***********************************/

package algoData;

/**
 * TransitiveClosure Class: Uses Warshall's Algorithm to show that a path exists
 * between nodes
 */
public class TransitiveClosure {

	/** private data members */
	private final int[][] R; // holds the transitive closure between any pairs of
								// vertices
	private int r; // used to keep track of the number of transitional matrices
					// created

	/*
	 * Algorithm Warshall(A) //Input: the adjacency matrix A of a digraph with n
	 * vertices //Output: the transitive closure of the digraph.
	 * 
	 * Step 1: R = A //O(n^2) Step 2: for k = 1..n do // O(n^3) for i = 1..n do
	 * for j = 1..n do if rik = 1 and rkj =1 then rij = 1 step 3: return R.
	 * 
	 * Analysis: Warshalls Algorithm is O(n^3) time complexity.
	 * 
	 * Note: 1. We dont have to write the last step elaborately as if (rijk-1 =
	 * 1) or ( rikk-1 = 1 and rkjk-1 = 1) then rijk = 1. 2. What is the space
	 * complexity of the algorithm? 3. Warshalls algorithm when implemented on
	 * a bit matrix could be much more efficient with a better constant factor
	 * in O(n^3) OR-operations
	 * 
	 * Step 1: R = A //O(n^2) Step 2: for k = 1..n do // O(n^2) OR-operations
	 * for i = 1..n do if rik = 1 then Ri = Ri OR Rk step 3: return R.
	 */

	/** TransitiveClosure Constructor: Set parameter to a local variable */
	TransitiveClosure(int[][] A) {
		R = A;
		r = 0;
		System.out.println("The initial Matrix " + toString());
	}

	/** generateR method: Basic implementation of Warshall's algorithm */
	protected int[][] generateR() {
		for (int k = 0; k < R.length; k++) {
			for (int i = 0; i < R.length; i++) {
				for (int j = 0; j < R.length; j++) {
					if (R[i][k] == 1 && R[k][j] == 1) {
						R[i][j] = 1;
					}
				}
			}
			System.out.println(" " + toString());
		}
		return R;
	}

	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString() {
		String resultMatrixN = "\n R" + r + " is: \n\n";
		for (int i = 0; i < R.length; i++) {
			resultMatrixN += "[";
			for (int j = 0; j < R[0].length; j++) {
				resultMatrixN += " " + R[i][j];
			}
			resultMatrixN += " ]\n";
		}
		if (r >= R.length) { // ensures the correct labeling of the R matrices
			r = R.length;
		} else {
			r++;
		}
		return resultMatrixN;
	}
}