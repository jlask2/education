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

import java.util.ArrayList;

/**
 * KnapSack Class: Uses dynamic programming strategy to find the optimal value
 * that a knapsack can hold subject to the constraints of a maximum holding
 * capacity and a set of weights with associated values
 */
public class KnapSack {

	/** private data members */
	private final int[][] ks;
	private final int n;
	private final int[] w;
	private final int[] v;
	private final int W;
	private final ArrayList<Integer> optimalSets;

	/**
	 * Knapsack constructor: Initializes the parameters and instantiates a new
	 * matrix and array list to hold the optimal sets
	 */
	public KnapSack(int n, int[] w, int[] v, int W) {
		this.n = n;
		this.w = w;
		this.v = v;
		this.W = W;
		ks = new int[n + 1][W + 1];
		optimalSets = new ArrayList<Integer>();
	}

	/**
	 * getKnapSack method: Basic knapsack 0-1 algorithm implementation
	 * Initialize the first row and column with 0's Replace the ks[i][j] index
	 * with the max value
	 */
	protected int getKnapSack() {
		for (int j = 0; j < W + 1; j++) {
			ks[0][j] = 0;
			for (int i = 1; i < n + 1; i++) {
				for (int h = 0; h < W + 1; h++) {
					if ((h - w[i - 1]) >= 0) {
						ks[i][h] = max(ks[i - 1][h], v[i - 1]
								+ ks[i - 1][h - w[i - 1]]); // either the cell
															// above
						// or the cell above and to the left wi positions plus
						// the new vi
					} else {
						ks[i][h] = ks[i - 1][h]; // the cell above current index
					}
				}
			}
		}
		return ks[n][W];
	}

	/**
	 * getOptimalSets method: returns a String containing the list of items that
	 * are included in the knapsack This should be used only after the matrix ks
	 * is computed To compute the optimal sets we start at the bottom right
	 * corner of the matrix (Where the optimal value is given)
	 */
	protected String getOptimalSets() {
		int i = ks.length - 1; // the last row corresponding to the last item
		for (int j = ks[0].length - 1; j >= 0 && i > 0; i--) { // loop through
																// by
																// decrementing
																// till 0
			if (ks[i][j] != ks[i - 1][j]) { // if the cell above is different
				optimalSets.add(i); // add the item/index to the optimal sets
				j -= w[i - 1]; // shift the column left by the weight of the
								// previous item
			}
		} // repeat until you get to the first row
		String os = "The optimal sets are: { ";
		for (int index = 0; index < optimalSets.size(); index++) {
			os += optimalSets.get(index) + " "; // concatenate the sets to the
												// string os
		}
		os += "}";
		return os; // return the string os - "optimal sets" String
	}

	/**
	 * max method: return the maximum of the two values given If equal, return
	 * value1
	 */
	private int max(int value1, int value2) {
		if (value1 >= value2) {
			return value1;
		} else {
			return value2;
		}
	}

	@Override
	/**toString method: returns this data structure as a readable string*/
	public String toString() {
		String resultString = "The number of items observed n is: " + n
				+ "\nThe max weight of the Knapsack W is: " + W
				+ "\nThe set of weights w consists of: {";
		for (int s = 0; s < w.length; s++) {
			resultString += " " + w[s];
		}
		resultString += " }\nThe set of values v consists of: {";
		for (int r = 0; r < v.length; r++) {
			resultString += " " + v[r];
		}
		resultString += " }\n\nThe resulting matrix ks results in: \n\n";
		for (int i = 0; i < n + 1; i++) {
			resultString += "[";
			for (int j = 0; j < W + 1; j++) {
				resultString += " " + ks[i][j];
			}
			resultString += " ]\n";
		}
		resultString += "\n" + getOptimalSets() + "\n";
		return resultString;
	}
}