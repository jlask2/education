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
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;

/*
numNodes( ) – returns the number of nodes
numEdges( ) : returns the number of edges
existEdge( Edge e): returns true if e is an edge else returns false
existsEdge(int i, int j): returns true if there exists an edge between i and j else returns false
putEdge( Edge: e) : adds the edge e to the graph
putEdge( int i, int j) : adds the edge from i to j to the graph
removeEdge(Edge: e): deletes the edge e from the graph
removeEdge(int i, int j): deletes the edge from i to j from the graph
degree(Node: i): returns the degree of node i. this method is defined for undirected graphs only.
degree(int i): returns the degree of node i. this method is defined for undirected graphs only.
inDegree(Node: i): returns the in-degree of node i. this method is defined for directed graphs only.
inDegree(int: i): returns the in-degree of node i. this method is defined for directed graphs only.
outDegree(Node: i): returns the out-degree of node i. this method is defined for directed graphs only.
outDegree(int: i): returns the out-degree of node i. this method is defined for directed graphs only.
adjacentVertices(Node: i): returns the nodes that are adjacent to i
adjacentVertices(int: i):  returns the nodes that are adjacent to i
areAdjacent(Node i, Node j): returns true if the nodes i and j are adjacent else returns false.
areAdjacent(int i, int j): returns true if the nodes i and j are adjacent else returns false.
*/

/**Root Class - Graph*/
abstract class G {
	
	/**private data members*/
	BufferedReader br;
	private int type;
	private int numOfEdges;
	private int numOfNodes;
	
	/**Constructor G*/
	/*public G(Edge edge, Node nodei, Node nodej, int i, int j){
		numNodes();
		numEdges();
		putEdge(edge);
		putEdge(i, j);
		existsEdge(edge);
		existsEdge(i, j);
		removeEdge(edge);
		removeEdge(i, j);
		degree(nodei);
		degree(i);
		inDegree(nodei);
		inDegree(i);
		outDegree(nodei);
		outDegree(i);
		adjacentVertices(nodei);
		adjacentVertices(i);
		areAdjacent(nodei, nodej);
		areAdjacent(i, j);
	}*
	
	/**Constructor G accepts a file with graph data */
	public G(BufferedReader br, int type, int numOfNodes, int numOfEdges){
		this.br = br;
		this.type = type;
		this.numOfNodes = numOfNodes;
		this.numOfEdges = numOfEdges;
		System.out.println("Number of Edges: "+numOfEdges);
		System.out.println("Number of Nodes: "+numOfNodes);
		
		constructAD();
		//toString();
		System.out.println(toString());
	}
	
	abstract protected void constructAD();
	
	abstract public String toString();
	
	private void printNodeArray(Object[] array){
		Node[] nodeArray = (Node[])array;
		for(int i = 0; i < array.length; i++){
			System.out.println("index"+i+": "+nodeArray[i].getVLabel());
		}
	}
	
	private void printEdgeArray(Edge[] array){
		for(int i = 0; i < array.length; i++){
			System.out.println("index: "+i+" | adji: "+array[i].getAdjNodei().getVLabel()+" | adji: "+array[i].getAdjNodej().getVLabel()+" " );
		}
	}
	
	/**numNodes( ) – returns the number of nodes*/
	protected int numNodes(){
		return numOfNodes;
	}
	
	/**numEdges( ) : returns the number of edges*/
	protected int numEdges(){
		return numOfEdges;
	}
	
	/**existEdge( Edge e): returns true if e is an edge else returns false*/
	private boolean existsEdge(Edge e){
		boolean found = false;
		for(int i = 0; i < numOfEdges; i++){
			/*if(e.equals(edges[i])){
				found = true;
			}*/
		}
		return found;
	}
	
	/**existsEdge(int i, int j): returns true if there exists an edge between i and j else returns false*/
	private boolean existsEdge(int i, int j){
		boolean found = false;
		Edge e = new Edge(i, j);
		for(int k = 0; k < numOfEdges; k++){
			/*if(e.equals(edges[k])){
				found = true;
			}*/
		}
		return found;
	}
	
	/**putEdge( Edge: e) : adds the edge e to the graph*/
	private void putEdge(Edge e){
		/*edges[iedges] = e;
		iedges++;*/
		numOfEdges++;
	}
	
	/**putEdge( int i, int j) : adds the edge from i to j to the graph*/
	private void putEdge(int i, int j){
		Edge edge = new Edge(i, j);
		/*edges[iedges] = edge;
		iedges++;*/
		numOfEdges++;
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
	private int degree(Node i){
		return i.getDegOfNode();
	}
	
	/**degree(int i): returns the degree of node i. this method is defined for undirected graphs only.*/
	private int degree(int i){
		Node node = new Node(i);
		return node.getDegOfNode();
	}
	
	/**inDegree(Node: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	private int inDegree(Node i){
		return i.getInDegOfNode();
	}
	
	/**inDegree(int: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	private int inDegree(int i){
		Node node = new Node(i);
		return node.getInDegOfNode();
	}
	
	/**outDegree(Node: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	private int outDegree(Node i){
		return i.getOutDegOfNode();
	}
	
	/**outDegree(int: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	private int outDegree(int i){
		Node node = new Node(i);
		return node.getOutDegOfNode();
	}
	
	/**adjacentVertices(Node: i): returns the nodes that are adjacent to i*/
	private Node adjacentVertices(Node i){		
		return i;
	}
	
	/**adjacentVertices(int: i):  returns the nodes that are adjacent to i*/
	private Node adjacentVertices(int i){
		Node adjNodes = new Node();
		return adjNodes;
	}
	
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
	private boolean areAdjacent(int i, int j){
		return existsEdge(i, j);
	}
	
	private void printRawUnWeightedData(Node[] fnodes, Edge[] fedges) {
		System.out.println("\nUn-Weighted");
		int k = 0;
		for(int i = 0; i < numOfEdges; i++){
			System.out.println(fnodes[k].getVLabel() +" "+fnodes[k+1].getVLabel());
			k = k + 2;
		}
	}

	private void printRawWeightedData(Node[] fnodes, Edge[] fedges) {
		System.out.println("\nWeighted");
		int k = 0;
		for(int i = 0; i < numOfEdges; i++){
			System.out.println(fnodes[k].getVLabel() +" "+fnodes[k+1].getVLabel()+" "+fedges[i].getWeight());
			k = k + 2;
		}
	}
}