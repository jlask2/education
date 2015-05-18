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

/**
 * AllSourceSP Class: All-Source Shortest Path uses Floyd's Algorithm to find
 * the shortest path
 */
public class AllSourceSP {

	/** private data members */
	private final int[][] D; // holds the shortest distance between any two
								// given vertices
	private final int[][] path; // matrix holds the destination vertices of a
								// given edge
	private int r;// used to keep count of the number of transitional matrices
					// that are produced

	/**
	 * AllSourceSP Constructor: Initialize the non main-diagonal zeros to
	 * infinity
	 */
	AllSourceSP(int[][] W) {
		r = 0;
		D = W;
		path = new int[D.length][D.length];
		for (int i = 0; i < D.length; i++) {
			for (int j = 0; j < D.length; j++) {
				if ((D[i][j]) == 0 && (i != j)) { // if it is not on the main
													// diagonal
													// and the indices contain
													// zero
					D[i][j] = Integer.MAX_VALUE; // set to max value
				} else if ((D[i][j]) != 0 && (i != j)) { // else continue
															// transferring the
															// Adj matrix to the
															// new matrix
					path[i][j] = j;
				} else {

				}
			}
		}
		System.out.println("The initial Matrix " + toString());
	}

	/**
	 * generateD method: Basic implementation of Floyds algorithm with the
	 * addition of path reconstruction
	 */
	protected int[][] generateD() {
		for (int k = 0; k < D.length; k++) {
			for (int i = 0; i < D.length; i++) {
				for (int j = 0; j < D.length; j++) {
					if (D[i][j] > (D[i][k] + D[k][j])
							&& (D[i][k] + D[k][j]) > -1
							&& D[i][k] != Integer.MAX_VALUE
							&& D[k][j] != Integer.MAX_VALUE) {// check for
																// overflow
						D[i][j] = D[i][k] + D[k][j]; // replace with a shorter
														// distance
						path[i][j] = path[i][k]; // store the destination vertex
					}
				}
			}
			System.out.println(" " + toString()); // get each transitional
													// matrix
		}
		return D;
	}

	/**
	 * getPath method: Returns the reconstructed shortest path between a pair of
	 * vertices
	 * */
	protected String getPath(int i, int j) {
		final int nodei = i; // the initial vertex - starting point
		final StringBuilder pathString = new StringBuilder();
		if (D[i - 1][j - 1] == Integer.MAX_VALUE || D[i - 1][j - 1] == 0) {// check
																			// to
																			// see
																			// if
																			// the
																			// path
																			// exists
			return "\nNo Path Exists between nodes " + i + " and " + j + "\n";
		}
		pathString.append(new Integer(i).toString() + ","); // append the
															// starting point
		while (i != j) { // if i and j are equal you have reached the
							// destination vertex
			i = path[i - 1][j - 1] + 1; // set your initial vertex to the next
										// vertex
			pathString.append(i + ","); // append that vertex to the path
		}
		String resultPathString = "The shortest path between nodes " + nodei
				+ " and " + j + " consists of: ";
		final String[] sa = pathString.toString().split(",");
		for (int s = 0; s < sa.length; s++) { // pretty up the output
			if (s != sa.length - 1) {
				resultPathString += sa[s] + " -> ";
			} else {
				resultPathString += sa[s] + " with a weight of "
						+ D[nodei - 1][j - 1] + "\n";
			}
		}
		return resultPathString; // return the path string
	}

	@Override
	/**toString method: Converts the data structure to a readable string*/
	public String toString() {
		String resultMatrixN = "\n R" + r + " is: \n\n";
		for (int i = 0; i < D.length; i++) {
			resultMatrixN += "[\t";
			for (int j = 0; j < D[0].length; j++) {
				if (D[i][j] == Integer.MAX_VALUE) {
					resultMatrixN += " %\t";// + Double.POSITIVE_INFINITY; // attempting
																		// an
																		// output of
																		// infinity
																// but with no luck
					// System.out.println(Character.toString('\u221E'));
				} else {
					resultMatrixN += " " + D[i][j]+"\t";
				}
			}
			resultMatrixN += " ]\n";
		}
		if (r >= D.length) { // ensuring the correct labeling of the R matrices
			r = D.length;
		} else {
			r++;
		}
		return resultMatrixN;
	}
}