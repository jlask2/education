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
import java.util.ArrayList;
import java.util.List;

/**Root Class - Graph*/
abstract class G {
	
	/**private data members*/
	private int numOfEdges;
	private int numOfNodes;
	protected ArrayList<Node> inNodes;
	protected ArrayList<Node> outNodes;
	protected ArrayList<Node> degNodes;
	
	/**Constructor G */
	public G(BufferedReader br, int numOfNodes, int numOfEdges){
		inNodes = new ArrayList<Node>();
		outNodes = new ArrayList<Node>();
		degNodes = new ArrayList<Node>();
	}
	
	abstract protected void constructAD();
	abstract public String toString();
	
	abstract protected boolean existsEdge(int i, int j); //returns true if there exists an edge between i and j else returns false
	abstract protected boolean existsEdge(Edge e); //returns true if e is an edge else returns false
	abstract protected void putEdge(Edge e); //adds the edge e to the graph
	abstract protected void putEdge(int i, int j); //adds the edge from i to j to the graph
	abstract protected void removeEdge(Edge e); //deletes the edge e from the graph
	abstract protected void removeEdge(int i, int j); //deletes the edge from i to j from the graph
	abstract protected int degree(Node i); //returns the degree of node i. this method is defined for undirected graphs only.
	abstract protected int degree(int i); //returns the degree of node i. this method is defined for undirected graphs only.
	abstract protected int inDegree(Node i); //returns the in-degree of node i. this method is defined for directed graphs only.
	abstract protected int inDegree(int i); //returns the in-degree of node i. this method is defined for directed graphs only.
	abstract protected int outDegree(Node i); //returns the out-degree of node i. this method is defined for directed graphs only.
	abstract protected int outDegree(int i); //returns the out-degree of node i. this method is defined for directed graphs only.
	abstract protected Node adjacentVertices(Node i); //returns the nodes that are adjacent to i
	abstract protected Node adjacentVertices(int i); // returns the nodes that are adjacent to i
	abstract protected boolean areAdjacent(Node i, Node j); //returns true if the nodes i and j are adjacent else returns false.
	abstract protected boolean areAdjacent(int i, int j); //returns true if the nodes i and j are adjacent else returns false.

	/**numNodes( ) – returns the number of nodes*/
	protected int numNodes(){
		return numOfNodes;
	}
	
	/**numEdges( ) : returns the number of edges*/
	protected int numEdges(){
		return numOfEdges;
	}
	
	protected boolean rangeCheck(Object list){
		return true;
	}
	
	/**removeEdge(Edge: e): deletes the edge e from the graph*/
	/*private void removeEdge(Edge e){
		boolean found = false;
		for(int i = 0; i <= edges.length - 1; i++){
			if((e.equals(edges[i]))&&(i != (edges.length - 1))){
				found = true;
				edges[i] = edges[i+1];
			}else if((e.equals(edges[i]))&&(i == (edges.length - 1))){
				edges[i] = null;
			}else{
				if(found){
					edges[i] = edges[i+1];
				}
			}
		}
		if(found == false){
			System.out.println("Edge Not Found");
		}else{
			if(numOfEdges > 0){
				numOfEdges--;
				iedges--;
			}else{
				System.out.println("There are currently no edges");
			}
		}
	}*/
	
	/**removeEdge(int i, int j): deletes the edge from i to j from the graph*/
	/*private void removeEdge(int i, int j){
		boolean found = false;
		for(int k = 0; k <= edges.length - 1; k++){
			if((i == edges[k].getAdjNodei().getVLabel())&&(j == edges[k].getAdjNodej().getVLabel())&&(k != (edges.length - 1))){
				found = true;
				edges[k] = edges[k+1];
			}else if((i == edges[k].getAdjNodei().getVLabel())&&(j == edges[k].getAdjNodej().getVLabel())&&(k == (edges.length - 1))){
				edges[k] = null;
			}else{
				if(found){
					edges[k] = edges[k+1];
				}
			}
		}
		if(found == false){
			System.out.println("Edge Not Found");
		}else{
			if(numOfEdges > 0){
				numOfEdges--;
				iedges--;
			}else{
				System.out.println("There are currently no edges");
			}
		}
	}*/
	
	/**degree(Node: i): returns the degree of node i. this method is defined for undirected graphs only.*/
	/*private int degree(Node i){
		return i.getDegOfNode();
	}*/
	
	/**degree(int i): returns the degree of node i. this method is defined for undirected graphs only.*/
	/*private int degree(int i){
		Node node = new Node(i);
		return node.getDegOfNode();
	}*/
	
	/**inDegree(Node: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	/*private int inDegree(Node i){
		iNodes[] = i;
		return ;
	}*/
	
	/**inDegree(int: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	/*private int inDegree(int i){
		Node node = new Node(i);
		return node.getInDegOfNode();
	}*/
	
	/**outDegree(Node: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	/*private int outDegree(Node i){
		return i.getOutDegOfNode();
	}*/
	
	/**outDegree(int: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	/*private int outDegree(int i){
		Node node = new Node(i);
		return node.getOutDegOfNode();
	}*/
	
	/**adjacentVertices(Node: i): returns the nodes that are adjacent to i*/
	/*private Node adjacentVertices(Node i){		
		return i;
	}*/
	
	/**adjacentVertices(int: i):  returns the nodes that are adjacent to i*/
	/*private Node adjacentVertices(int i){
		Node adjNodes = new Node();
		return adjNodes;
	}*/
	
	/**areAdjacent(Node i, Node j): returns true if the nodes i and j are adjacent else returns false.*/
	/*private boolean areAdjacent(Node i, Node j){
		Edge e = new Edge(i, j);
		boolean found = false;
		for(int k = 0; k < numOfEdges; k++){
			if(e.equals(edges[k])){
				found = true;
			}
		}
		if(found == true){
			return true;
		}else{
			return false;
		}
	}*/
	
	/**areAdjacent(int i, int j): returns true if the nodes i and j are adjacent else returns false.*/
	/*private boolean areAdjacent(int i, int j){
		return existsEdge(i, j);
	}*/
}