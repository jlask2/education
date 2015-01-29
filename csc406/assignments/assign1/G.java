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
	
	/**Constructor G*/
	public G(Edge edge, Node nodei, Node nodej, int i, int j){
		numNodes();
		numEdges();
		existEdge(edge);
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
	
	/**numNodes( ) – returns the number of nodes*/
	private int numNodes(){
		return numOfNodes;
	}
	
	/**numEdges( ) : returns the number of edges*/
	private int numEdges(){
		return numOfEdges;
	}
	
	/**existEdge( Edge e): returns true if e is an edge else returns false*/
	private boolean existEdge(Edge e){
		if(true){
			return true;
		}else{
			return false;
		}
	}
	
	/**existsEdge(int i, int j): returns true if there exists an edge between i and j else returns false*/
	private boolean existsEdge(int i, int j){
		if(true){
			return true;
		}else{
			return false;
		}
	}
	
	/**putEdge( Edge: e) : adds the edge e to the graph*/
	private void putEdge(Edge e){
		
	}
	
	/**putEdge( int i, int j) : adds the edge from i to j to the graph*/
	private void putEdge( int i, int j){
		
	}
	
	/**removeEdge(Edge: e): deletes the edge e from the graph*/
	private void removeEdge(Edge e){
		
	}
	
	/**removeEdge(int i, int j): deletes the edge from i to j from the graph*/
	private void removeEdge(int i, int j){
		
	}
	
	/**degree(Node: i): returns the degree of node i. this method is defined for undirected graphs only.*/
	private int degree(Node i){
		int degOfNode = 0;
		return degOfNode;
	}
	
	/**degree(int i): returns the degree of node i. this method is defined for undirected graphs only.*/
	private int degree(int i){
		int degOfNode = 0;
		return degOfNode;
	}
	
	/**inDegree(Node: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	private int inDegree(Node i){
		int inDegOfNode = 0; 
		return inDegOfNode;
	}
	
	/**inDegree(int: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	private int inDegree(int i){
		int inDegOfNode = 0;
		return inDegOfNode;
	}
	
	/**outDegree(Node: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	private int outDegree(Node i){
		int outDegOfNode = 0;
		return outDegOfNode;
	}
	
	/**outDegree(int: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	private int outDegree(int i){
		int outDegOfNode = 0;
		return outDegOfNode;
	}
	
	/**adjacentVertices(Node: i): returns the nodes that are adjacent to i*/
	private Node adjacentVertices(Node i){		
		Node adjNodes = new Node();
		return adjNodes;
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
}
