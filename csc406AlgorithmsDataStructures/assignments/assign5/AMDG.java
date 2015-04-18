/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 3
 * Date Assigned: 3/4/2015
 * Date Due: 3/25/2015
 * Date Submitted: 3/25/2015
 ***********************************/

package algoData;

import java.io.BufferedReader;
import java.io.IOException;

/** Adjacency Matrix Directed Graph */
public class AMDG extends DAG {

	/**
	 * Compare method compares two Objects data members for integer equality,
	 * returns 0 if equal else a 1 if not
	 */
	private static int compare(int[][] dataMember1, int[][] dataMember2) {
		if (dataMember1 == dataMember2) {
			return 0;
		} else {
			return 1;
		}
	}

	/** private data members */
	private final BufferedReader br;
	private final int numOfEdges;
	private final int numOfNodes;
	private int[][] AM;
	private String line;

	private String fileInput;

	/** Constructor */
	public AMDG(BufferedReader br, int numOfNodes, int numOfEdges) {
		System.out.println("\n\nInside AMDG Constructor\n");
		this.br = br;
		this.numOfNodes = numOfNodes;
		this.numOfEdges = numOfEdges;
		fileInput = "This is the file input data:\n\n1 " + numOfNodes + " "
				+ numOfEdges + "\n";
		constructAD();
		System.out.println(fileInput);
		System.out.println(toString());
	}

	/** adjacentVertices(int: i): returns the nodes that are adjacent to i */
	@Override
	protected int[] adjacentVertices(int i) {
		int n = 0;
		final int[] temp = new int[numOfNodes];
		for (int j = 0; j < numOfNodes; j++) {
			if (AM[i - 1][j] == 1) {
				temp[j] = j + 1;
				n++;
			}
		}
		int m = 0;
		final int[] adjNodes = new int[n];
		for (int k = 0; k < temp.length; k++) {
			if (temp[k] > 0) {
				adjNodes[m] = temp[k];
				m++;
			}
		}
		return adjNodes;
	} // returns the nodes that are adjacent to i

	/**
	 * areAdjacent(int i, int j): returns true if the nodes i and j are adjacent
	 * else returns false.
	 */
	@Override
	protected boolean areAdjacent(int i, int j) {
		if (rangeCheck(i, j)) {
			if (existsEdge(i, j)) {
				return true;
			}
			return false;
		} else {
			throw new ArrayIndexOutOfBoundsException("The indexes i = " + i
					+ " and j = " + j + " are out of bounds");
		}
	} // returns true if the nodes i and j are adjacent else returns false.

	/**
	 * constructAD method constructs the given adjacency data structure and
	 * populates it from the file input stream
	 */
	@Override
	protected void constructAD() {
		/* DEBUG */// System.out.println("breakpoint: inside constructAM");
		listNodes = new int[3][numOfNodes];
		AM = new int[numOfNodes][numOfNodes];

		try {
			br.reset();
			br.mark(100);
			while ((line = br.readLine()) != null) {
				final String[] lineArray = line.split(" ");
				final int nodei = Integer.parseInt(lineArray[0]);
				final int nodej = Integer.parseInt(lineArray[1]);

				AM[(nodei - 1)][(nodej - 1)] = 1;
				listNodes[1][nodei - 1]++;
				listNodes[0][nodej - 1]++;
				listNodes[2][nodei - 1]++;
				listNodes[2][nodej - 1]++;
				fileInput += line + "\n";
			}
		} catch (final NumberFormatException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected int degree(int i) {
		return listNodes[i - 1][2];
	} // returns the degree of node i. this method is defined for undirected
		// graphs only.

	/** Equal method compares data members of two objects */
	@Override
	public boolean equals(Object child) { // note the type of the parameter
		final AMDG c = (AMDG) child; // cast the parameter before use
		return AMDG.compare(this.AM, c.AM) == 0;
	}

	/**
	 * existsEdge(int i, int j): returns true if there exists an edge between i
	 * and j else returns false
	 */
	@Override
	protected boolean existsEdge(int i, int j) {
		boolean found = false;
		if (rangeCheck(i, j)) {
			if (AM[i - 1][j - 1] == 1) {
				found = true;
			}
			return found;
		} else {
			throw new ArrayIndexOutOfBoundsException("The indexes i = " + i
					+ " and j = " + j + " are out of bounds");
		}
	}

	protected int[][] getAM() {
		if (AM != null) {
			return AM;
		} else {
			throw new NullPointerException(
					"The Adjacency Matrix has not been created yet");
		}
	}

	/**
	 * inDegree(int: i): returns the in-degree of node i. this method is defined
	 * for directed graphs only.
	 */
	@Override
	protected int inDegree(int i) {
		return listNodes[i - 1][0];
	} // returns the in-degree of node i. this method is defined for directed
		// graphs only.

	/**
	 * outDegree(int: i): returns the out-degree of node i. this method is
	 * defined for directed graphs only.
	 */
	@Override
	protected int outDegree(int i) {
		return listNodes[i - 1][1];
	} // returns the out-degree of node i. this method is defined for directed
		// graphs only.

	/** putEdge( int i, int j) : adds the edge from i to j to the graph */
	@Override
	protected void putEdge(int i, int j) {
		if (rangeCheck(i, j)) {
			if (!(existsEdge(i, j))) {
				AM[i - 1][j - 1] = 1;
				listNodes[1][i - 1]++;
				listNodes[0][j - 1]++;
				listNodes[2][i - 1]++;
				listNodes[2][j - 1]++;
				fileInput += line + "\n";
			} else {
				throw new IllegalArgumentException("The edge " + i + " --> "
						+ j + " cannot be added because it already exists");
			}
		} else {
			throw new ArrayIndexOutOfBoundsException("The indexes i = " + i
					+ " and j = " + j + " are out of bounds");
		}
	}

	/**
	 * rangeCheck( ) : Checks to see if the node labeling is outside the range
	 * of 1 -> numOfNodes
	 */
	@Override
	protected boolean rangeCheck(int i, int j) {
		return i > 0 && i <= numOfNodes && j > 0 && j <= numOfNodes;
	}

	/** removeEdge(int i, int j): deletes the edge from i to j from the graph */
	@Override
	protected void removeEdge(int i, int j) {
		if (rangeCheck(i, j)) {
			if (existsEdge(i, j)) {
				AM[i - 1][j - 1] = 0;
				listNodes[1][i - 1]--;
				listNodes[0][j - 1]--;
			} else {
				throw new NullPointerException("The edge " + i + " --> " + j
						+ " cannot be removed because it does not exist");
			}
		} else {
			throw new ArrayIndexOutOfBoundsException("The indexes i = " + i
					+ " and j = " + j + " are out of bounds");
		}
	} // deletes the edge from i to j from the graph

	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString() {
		String n = "";
		n += "\nContents of the Adjcency Matrix Data Structure\n";
		n += "\nIndex     |";
		for (int k = 0; k < numOfNodes; k++) {
			int c = k;
			c++;
			n += " " + c;
		}
		n += "\n-----------------------------";
		for (int i = 0; i < numOfNodes; i++) {
			n += "\n  " + (i + 1) + "       |";
			for (int j = 0; j < numOfNodes; j++) {
				n += " " + AM[i][j];
			}

		}
		n += "\n";
		return n;
	}
}
