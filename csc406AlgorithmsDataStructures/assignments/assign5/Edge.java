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

/** Edge Class: Implementation of an edge for a graph */
public class Edge implements Comparable<Edge> {

	/**
	 * Compare method compares two Edge data members for integer equality,
	 * returns 0 if equal else a 1 if not
	 */
	private static int compare(Node dataMember1, Node dataMember2) {
		if (dataMember1.equals(dataMember2)) {
			return 0;
		} else {
			return 1;
		}
	}

	/** private data members */
	private int weight = 1;
	private Node adjNodei;

	private Node adjNodej;

	/** Constructor Edge */
	public Edge() {
		weight = 0;
		adjNodei = null;
		adjNodej = null;
	}

	/** Constructor Edge with parameters for unweighted */
	public Edge(int i, int j) {
		final Node nodei = new Node(i);
		adjNodei = nodei;
		final Node nodej = new Node(j);
		adjNodej = nodej;
	}

	/** Constructor Edge with parameters for weighted */
	public Edge(int i, int j, int weight) {
		this.weight = weight;
		final Node nodei = new Node(i);
		adjNodei = nodei;
		final Node nodej = new Node(j);
		adjNodej = nodej;
	}

	/** Constructor Edge with parameters for weighted */
	public Edge(int weight, Node adjNodei, Node adjNodej) {
		this.weight = weight;
		this.adjNodei = adjNodei;
		this.adjNodej = adjNodej;
	}

	/** Constructor Edge with parameters for unweighted */
	public Edge(Node adjNodei, Node adjNodej) {
		this.adjNodei = adjNodei;
		this.adjNodej = adjNodej;
	}

	/** Constructor Edge with parameters for weighted */
	public Edge(Node adjNodei, Node adjNodej, int weight) {
		this.weight = weight;
		this.adjNodei = adjNodei;
		this.adjNodej = adjNodej;
	}

	@Override
	public int compareTo(Edge e) {
		if (this.getWeight() < e.getWeight()) {
			/* DEBUG */// System.out.println("compareTo <");
			return -1;
		} else if (this.getWeight() > e.getWeight()) {
			/* DEBUG */// System.out.println("compareTo >");
			return 1;
		} else {
			/* DEBUG */// System.out.println("compareTo equal");
			return 0;

		}
	}

	/** Equal method compares data members of two Node objects */
	@Override
	public boolean equals(Object edge) { // note the type of the parameter
		final Edge e = (Edge) edge; // cast the parameter before use
		return Edge.compare(this.adjNodei, e.adjNodei) == 0
				&& Edge.compare(this.adjNodej, e.adjNodej) == 0;
	}

	protected Node getAdjNodei() {
		return adjNodei;
	}

	protected Node getAdjNodej() {
		return adjNodej;
	}

	/** Accessor Methods */
	protected int getWeight() {
		return weight;
	}

	protected void setAdjNodei(Node adjNodei) {
		this.adjNodei = adjNodei;
	}

	protected void setAdjNodej(Node adjNodej) {
		this.adjNodej = adjNodej;
	}

	/** Mutator Methods */
	protected void setWeight(int weight) {
		this.weight = weight;
	}

	@Override
	/**toString method: Returns the given object as a readable string*/
	public String toString() {
		return "Edge --> Node i(" + getAdjNodei().getVLabel() + ") weight-"
				+ getWeight() + "-> Node j(" + getAdjNodej().getVLabel() + ")";
	}
}
