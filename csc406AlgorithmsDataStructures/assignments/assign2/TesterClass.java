/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 2
 * Date Assigned: 2/12/2015
 * Date Due: 3/4/2015
 * Date Submitted: 3/11/2015 
 ***********************************/

package poset;

import java.io.BufferedReader;
import java.io.IOException;

import javax.swing.SwingUtilities;

/**TesterClass: class to test out the package poset with its graph utilities*/
class TesterClass {
    
	/**private data members*/
	private static ALDG aldg;
	private static ALWDG alwdg;
	private static AMDG amdg;
	private static AMWDG amwdg;
	private static ALUG alug;
	private static ALWUG alwug;
	private static AMUG amug;
	private static AMWUG amwug;
	private static FileIO fileIO;
	private static BufferedReader br;
	private static int type;
	private static int numOfNodes;
	private static int numOfEdges;
	
	/**main method: runs the main program*/
	public static void main(String[] args) {
	  SwingUtilities.invokeLater(new Runnable()
	  {
		  public void run(){
			  start();
		  }
	  });
	}

	/**start method: starts the program*/
	protected static void start() {
		while(1 == 1){ // indefinite loop until user exits through the menu method
			menu();
		}
	}
	
	/**menu method: navigates the user to the correct implementation after creating or reading the file*/
	protected static void menu(){
		System.out.println("Welcome to my Graph program! "
				+ "\n0). Enter a 0 as the type for an Unweigthed-DAG implementation"
				+ "\n1). Enter a 1 as the type for an Weigthed-DAG implementation"
				+ "\n2). Enter a 2 as the type for an Unweigthed-UAG implementation"
				+ "\n3). Enter a 3 as the type for an Weigthed-UAG implemetation"
				+ "\n4). Enter infileD or a 4 as the type (The number of Nodes and Edges still need to be input) to exit the program\n");
		fileIO = new FileIO(); // new FileIO object to create or read from a file 
		br = fileIO.getBuffIn();
		type = fileIO.getType();
		numOfNodes = fileIO.getNumNodes();
		numOfEdges = fileIO.getNumEdges();
		try {
			br.mark(100);
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch(type){
		case 0:
			aldg = new ALDG(br, numOfNodes, numOfEdges);
			amdg = new AMDG(br, numOfNodes, numOfEdges);
			aldg.topoSort(aldg.listNodes, numOfNodes);
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 1:
			alwdg = new ALWDG(br, numOfNodes, numOfEdges); 
			amwdg = new AMWDG(br, numOfNodes, numOfEdges);
			alwdg.topoSort(alwdg.listNodes, numOfNodes);
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			alug = new ALUG(br, numOfNodes, numOfEdges); 
			amug = new AMUG(br, numOfNodes, numOfEdges);
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			alwug = new ALWUG(br, numOfNodes, numOfEdges); 
			amwug = new AMWUG(br, numOfNodes, numOfEdges);
			alwug.findMST(alwug.pqe, numOfNodes);
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			System.out.println("All Finished. Thanks for using this program. Exiting the program.");
			System.exit(0);
			break;
		default:
			System.out.println("Something went wrong, exiting the program!");
			System.exit(0);
			break;
		}
	}
}

/*
CSC406Assignment#1:  	Due on or before February 11, 2015		rajasethupathy

Project: Implement the weighted/unweighted directed graph data structures using both adjacency matrix and adjacency list representation. 

Partial list of operations on a graph data structure:

numNodes( ) � returns the number of nodes
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

General instructions:
1.	Every graph has nodes and edges. So keep two variables, nodes and edges, in the root class.  Keep adjusting these variables whenever you perform operations on the graph that warrant changes to these variables.
2.	Implement fully as many methods as possible in the root class itself.
 numNodes( ) and numEdges( ) are such methods.
3.	You can also keep an array for outDegree in the root class. keep changing it whenever graph operations warrant changes to the outDegree of a node. Likewise for inDegree and degree. 
4.	You may assume that nodes are labeled by natural numbers.  
5.	Use an iterator to step thro elements of a collection. 
6.	Avoid repeating code.
7.	Include your name, course#, semester, assignment#, date assigned and date due.
8.	When using indices, don�t forget to do �range check�. A protected method?
9.	File I/O is required.
10.	Validation of input is not a requirement at this stage. However, it will be needed and required for future assignments.
11.	Including a root class and a tester class, you are expected to submit six classes: G (root class), AMWDG, AMDG, ALWDG, ALDG, TesterClass.
12.	Program has to be tested for at least one positive and one negative result.
13.	Submit readable hard copy and flash-drive versions of the source code, input data, test data and a test run.
14.	Seek immediate help if you need help in understanding/implementing your project.

*************************************************************************************************************************

CSC406Assignment#2 	���	Due on or before March 4, 2015		raj 

Implement the following algorithms:

1.	Topological sort.
2.	Kruskal�s algorithm for finding a MST.

This assignment needs the implementation of undirected and weighted ( > 0 ) graphs. Such a graph must be implemented in both matrix and list representations. Must use Java�s PriorityQueue to implement a heap. This project needs to be built on top of the first assignment. 

As usual pay attention to the following additional requirements. More may be added to this list after I look through your first assignment.
 
1.	Clearly highlight the Assignment#2 code. 
2.	Don�t maintain a collection of edges. Maintaining such a collection is yet another way of representing a graph. We want to use only two rerpesentations of a  graph, namely, adjacency matrix and adjacency list representations. Storing the edges within each of these representations is redundant, and redundancy can lead to data integrity problems in addition to increasing the memory requirement by one hundred percent.
3.	Correctness of the code alone is not sufficient for full credit. Be aware of complexity of your code.
4.	Using an arrayList is generally costlier than using an array. So, if you know the size of the collection then use an array for the collection instead of an arrayList.
5.	Use Iterators wherever you have to step through each element of a collection from beginning to end. Using an iterator is very simple. Here is a skeleton code.

         ArrayList<Node>   aList;
         Iterator<Node>   ite  =  aList.iterator( );   //note the case for i in iterator
        // repeat if there are more elements in the collection
        while (ite.hasNext( )  )  {                    
Node node  =  ite.next(  );	//get the next element from the collection
	     �		   		//process node.
            }
  
6.	Provide appropriate documentation to explain complex code.
7.	When operating on a collection (such as an ArrayList ) write your own method only if an equivalent method is not available in the API listing for that collection. 
8.	While implementing Topological Sort don�t remove the edge from the graph every time through the loop. It is generally desirable to preserve the input as much as possible. So, don�t remove the edge from the graph; instead, simply adjust the inDegree.
9.	Operations such as search or delete elements from a collection involves comparison for equality between objects. So, don�t forget to add an equals method to the class whose instances may have to be compared.

For example, given the collection  ArrrayList<Node>   aList;
To perfrom a search on aList using contains method, aList.contains(n), you must have an equals method in the Node class with the following signature.

    public  boolean  equal(Object   node)  {          //note the type of the parameter
          Node   n1  =  (Node)  node;               // cast the parameter before use
          �.
} 

10.	In all versions of your program include your name, course#, semester, and assignment# along with date assigned and date submitted.
11.	Seek immediate help if you need help in understanding/implementing your project.

Addendum to assignment 2 instructions

1.	hard copies of several projects were simply unreadable. So here are some additional guidelines. 
print your hard copies with at least 12 font Times New Roman.
Print only one page of code on one side of the paper.
If lines are wrapped around, print in landscape.
You may print on both sides of the paper.
Arrange your classes in the level-order traversal of the  inheritance hierarchy.

2.	In assignment 1, you are asked to implement only two representations of a graph: adjacency matrix and adjacency list. 
storing all the edges of a graph is a third representation of the graph. It should not be done. Modify your accordingly in assignment 2.
3.	 Even if left unstated, in every class it is strongly recommended to include toString() and equals().
4.	 If you use your own helper methods, you must document it well.
5.	Those of you who did not implement a range check, do it in assignment 2 by including a method in the root class.
*/