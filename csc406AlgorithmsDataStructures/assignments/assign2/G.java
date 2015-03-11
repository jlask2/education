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

import java.io.BufferedReader;
import java.util.PriorityQueue;
import java.util.TreeSet;

/**Root Class - Graph*/
abstract class G {
	
	/**private data members*/
	private int numOfEdges;
	private int numOfNodes;
	protected int[][] listNodes;
	protected TopoSort ts;
	
	/**Forwarded abstract methods to be implemented by the child classes*/
	abstract protected void constructAD();
	abstract public String toString();
	abstract public boolean equals(Object graph);      //note the type of the parameter // cast the parameter before use
	abstract protected boolean rangeCheck(int i, int j);
	
	abstract protected boolean existsEdge(int i, int j); //returns true if there exists an edge between i and j else returns false
	abstract protected void putEdge(int i, int j); //adds the edge from i to j to the graph
	abstract protected void removeEdge(int i, int j); //deletes the edge from i to j from the graph
	abstract protected int degree(int i); //returns the degree of node i. this method is defined for undirected graphs only.
	abstract protected int inDegree(int i); //returns the in-degree of node i. this method is defined for directed graphs only.
	abstract protected int outDegree(int i); //returns the out-degree of node i. this method is defined for directed graphs only.
	abstract protected int[] adjacentVertices(int i); // returns the nodes that are adjacent to i
	abstract protected boolean areAdjacent(int i, int j); //returns true if the nodes i and j are adjacent else returns false.
	
	/**existEdge( Edge e): returns true if e is an edge else returns false*/
	protected boolean existsEdge(Edge e){
		Node nodei = e.getAdjNodei();
		Node nodej = e.getAdjNodej();
		return existsEdge(nodei.getVLabel(), nodej.getVLabel());
	}//returns true if e is an edge else returns false
	
	/**putEdge( Edge: e) : adds the edge e to the graph*/
	protected void putEdge(Edge e){
		Node nodei = e.getAdjNodei();
		Node nodej = e.getAdjNodej();
		putEdge(nodei.getVLabel(), nodej.getVLabel());
	} //adds the edge e to the graph
	
	/**removeEdge(Edge: e): deletes the edge e from the graph*/
	protected void removeEdge(Edge e){ //deletes the edge e from the graph
		Node nodei = e.getAdjNodei();
		Node nodej = e.getAdjNodej();
		removeEdge(nodei.getVLabel(), nodej.getVLabel());
	}
	
	/**degree(Node: i): returns the degree of node i. this method is defined for undirected graphs only.*/
	protected int degree(Node i){
		return degree(i.getVLabel());
	} //returns the degree of node i. this method is defined for undirected graphs only.
	
	/**inDegree(Node: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	protected int inDegree(Node i){
		return inDegree(i.getVLabel());
	}//returns the in-degree of node i. this method is defined for directed graphs only.
	
	/**outDegree(Node: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	protected int outDegree(Node i){
		return outDegree(i.getVLabel());
	}//returns the out-degree of node i. this method is defined for directed graphs only.
	
	/**adjacentVertices(Node: i): returns the nodes that are adjacent to i*/
	protected int[] adjacentVertices(Node i){
		return adjacentVertices(i.getVLabel());
	}//returns the nodes that are adjacent to i
	
	/**areAdjacent(Node i, Node j): returns true if the nodes i and j are adjacent else returns false.*/
	protected boolean areAdjacent(Node i, Node j){
		return areAdjacent(i.getVLabel(), j.getVLabel());
	}//returns true if the nodes i and j are adjacent else returns false.

	protected boolean rangeCheck(Edge e){
		return rangeCheck(e.getAdjNodei().getVLabel(), e.getAdjNodej().getVLabel());
	}
	
	/**numNodes( ) – returns the number of nodes*/
	protected int numNodes(){
		return numOfNodes;
	}
	
	/**numEdges( ) : returns the number of edges*/
	protected int numEdges(){
		return numOfEdges;
	}
}
