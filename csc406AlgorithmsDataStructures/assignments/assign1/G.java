/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 1
 * Date Assigned: 1/25/2015
 * Date Due: 2/11/2015
 * Date Submitted: 2/11/2015 
 ***********************************/

package poset;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
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
	private int numOfEdges;
	private int numOfNodes;
	private int inodes;
	private int iedges;
	private int iinDegs;
	private int ioutDegs;
	private int idegrees;
	
	private Node[] nodes;
	private Edge[] edges;
	private Edge[] inDegs;
	private Edge[] outDegs;
	private Edge[] degrees;
		
	private BufferedReader br;
	private BufferedWriter bw;
	private FileReader fr;
	private FileWriter fw;
	
	/**Constructor G*/
	public G(Edge edge, Node nodei, Node nodej, int i, int j){
		numNodes();
		numEdges();
		existsEdge(edge);
		existsEdge(i, j);
		putEdge(i, j);
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
	}
	
	/**Default constructor G initializes empty arrays of nodes and edges*/
	public G(){
		numOfNodes = 0;
		numOfEdges = 0;
		
		inodes = 0;
		iedges = 0;
		iinDegs = 0;
		ioutDegs = 0; 
		idegrees = 0;
		
		nodes = new Node[0];
		edges = new Edge[0];
		inDegs = new Edge[0];
		outDegs = new Edge[0];
		degrees = new Edge[0];
	}
	
	/**Constructor G accepts a file with graph data*/
	public G(boolean weighted, File inFile, int numOfLines, Edge[] fedges, Node[] fnodes){
		numOfNodes = 0;
		numOfEdges = numOfLines;
		System.out.println("Number of Edges: "+numOfEdges);
		
		inodes = 0;
		iedges = 0;
		iinDegs = 0;
		ioutDegs = 0; 
		idegrees = 0;
		
		nodes = fnodes;
		edges = fedges;
		
		if(weighted == true){
			printRawWeightedData(fnodes, fedges);
		}else if(weighted == false){
			printRawUnWeightedData(fnodes, fedges);
		}else{
			System.out.println("No Raw Data To Print");
		}
		
		inDegs = new Edge[0];
		outDegs = new Edge[0];
		degrees = new Edge[0];
	}

	/**numNodes( ) – returns the number of nodes*/
	private int numNodes(){
		return numOfNodes;
	}
	
	/**numEdges( ) : returns the number of edges*/
	private int numEdges(){
		return numOfEdges;
	}
	
	/**existEdge( Edge e): returns true if e is an edge else returns false*/
	private boolean existsEdge(Edge e){
		boolean found = false;
		for(int i = 0; i < numOfEdges; i++){
			if(e.equals(edges[i])){
				found = true;
			}
		}
		if(found == true){
			return true;
		}else{
			return false;
		}
	}
	
	/**existsEdge(int i, int j): returns true if there exists an edge between i and j else returns false*/
	private boolean existsEdge(int i, int j){
		boolean found = false;
		Edge e = new Edge(i, j);
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
	}
	
	/**putEdge( Edge: e) : adds the edge e to the graph*/
	private void putEdge(Edge e){
		edges[iedges] = e;
		iedges++;
		numOfEdges++;
	}
	
	/**putEdge( int i, int j) : adds the edge from i to j to the graph*/
	private void putEdge(int i, int j){
		Edge edge = new Edge(i, j);
		edges[iedges] = edge;
		iedges++;
		numOfEdges++;
	}
	
	/**removeEdge(Edge: e): deletes the edge e from the graph*/
	private void removeEdge(Edge e){
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
	}
	
	/**removeEdge(int i, int j): deletes the edge from i to j from the graph*/
	private void removeEdge(int i, int j){
		boolean found = false;
		//Edge e = new Edge(i, j);
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
	}
	
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
		//if(existsEdge(i)){
			
		//}
		return i;//adjNodes;
	}
	
	/**adjacentVertices(int: i):  returns the nodes that are adjacent to i*/
	private Node adjacentVertices(int i){
		Node adjNodes = new Node();
		return adjNodes;
	}
	
	/**areAdjacent(Node i, Node j): returns true if the nodes i and j are adjacent else returns false.*/
	private boolean areAdjacent(Node i, Node j){
		if(true){
			return true;
		}else{
			return false;
		}
	}
	
	/**areAdjacent(int i, int j): returns true if the nodes i and j are adjacent else returns false.*/
	private boolean areAdjacent(int i, int j){
		if(true){
			return true;
		}else{
			return false;
		}
	}
	
	private void printRawUnWeightedData(Node[] fnodes, Edge[] fedges) {
		System.out.println("Un-Weighted");
		int k = 0;
		for(int i = 0; i < numOfEdges; i++){
			System.out.println(fnodes[k].getVLabel() +" "+fnodes[k+1].getVLabel());
			k = k + 2;
		}
	}

	private void printRawWeightedData(Node[] fnodes, Edge[] fedges) {
		System.out.println("Weighted");
		int k = 0;
		for(int i = 0; i < numOfEdges; i++){
			System.out.println(fnodes[k].getVLabel() +" "+fnodes[k+1].getVLabel()+" "+fedges[i].getWeight());
			k = k + 2;
		}
	}
}