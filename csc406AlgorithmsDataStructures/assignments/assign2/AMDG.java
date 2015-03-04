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
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**Adjacecy Matrix Directed Graph*/
public class AMDG extends G{
	
	/**private data members*/
	private BufferedReader br;
	private int numOfEdges;
	private int numOfNodes;
	private int[][] AM;
	private Iterator<Node> ite;
	private String line;
	
	/**Constructor*/
	public AMDG(BufferedReader br, int numOfNodes, int numOfEdges){
		super(br, numOfNodes, numOfEdges);
		System.out.println("Inside AMDG Constructor\n");
		this.br = br;
		this.numOfNodes = numOfNodes;
		this.numOfEdges = numOfEdges;
		System.out.println("Number of Edges: "+this.numOfEdges);
		System.out.println("Number of Nodes: "+this.numOfNodes);
		constructAD();
		System.out.println(toString());
	}
	
	/**constructAD method constructs the given adjacency data structure and populates it from the file input stream*/
	protected void constructAD(){
		/*DEBUG*///System.out.println("breakpoint: inside constructAM");
		AM = new int[numOfNodes][numOfNodes];
		for(int i = 0; i < numOfNodes; i++){
			
			for(int j = 0; j < numOfNodes; j++){
				AM[i][j] = 0;
			}
		}
		try {
			br.reset();
			while((line = br.readLine()) != null){	
				String[] lineArray = line.split(" ");
				int nodei = Integer.parseInt(lineArray[0]);
				int nodej = Integer.parseInt(lineArray[1]);
				Node node1 = new Node(nodei);
				Node node2 = new Node(nodej);
				///*DEBUG*/System.out.println("nodei: "+nodei+"| nodej: "+nodej);
				if(!outNodes.contains(node1)){
			    	outNodes.add(node1);
			    }
			    if(!inNodes.contains(node2)){
			    	inNodes.add(node2);
			    }
			    inNodes.get(inNodes.indexOf(node2)).incrementInDegree();
			    outNodes.get(outNodes.indexOf(node1)).incrementOutDegree();
				
				AM[(nodei-1)][(nodej-1)] = 1;
				
			}
			br.close();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		Node nodei = new Node(3);
	    Node nodej = new Node(1);
		Edge edge = new Edge(nodei, nodej);
		System.out.println("Does Edge "+nodei.getVLabel()+" "+nodej.getVLabel()+" exist?: "+existsEdge(3, 1));
		putEdge(3, 1);
		System.out.println("After putEdge(), does Edge "+nodei.getVLabel()+" "+nodej.getVLabel()+" exist?: "+existsEdge(3, 1));
		Node nodeo = new Node(3);
		System.out.println("Are Nodes 2 and 1 adjacent?: "+areAdjacent(2, 1));
		ArrayList<Node> adjNodes = adjacentVertices(3);
		ite = adjNodes.iterator(); //note the case for i in iterator
		System.out.print("The Adjacent Nodes to "+nodeo.getVLabel()+" are: ");
		while(ite.hasNext()){
			Node node = ite.next();
			if(node != null){
				System.out.print(node.getVLabel()+", ");
			}
		}
		System.out.print("\n");
		System.out.println("Node "+nodeo.getVLabel()+" has a inDegree of "+inDegree(3)+" and a outDegree of "+outDegree(3));
		
		String n = "";
		n += "\nIndex     |";
		for(int k = 0; k < numOfNodes; k++){
			int c = k;
			c++;
			n += " "+c;
		}
		n += "\n   VLabel |";
		
		for(int p = 0; p < numOfNodes; p++){
			
		}
		n += "\n-----------------------------";
		for(int i = 0; i < numOfNodes; i++){
			n += "\n  "+(i+1)+"       |";
			for(int j = 0; j < numOfNodes; j++){
				n += " "+ AM[i][j];
			}
			
		}
		return n;
	}
	
	/**Equal method compares data members of two objects*/
	@Override
	public boolean equals(Object child){      //note the type of the parameter
        AMDG c = (AMDG)child;               // cast the parameter before use
        return AMDG.compare(this.AM, c.AM)  == 0;
    } 

	/**Compare method compares two Objects data members for integer equality, returns 0 if equal else a 1 if not*/
	private static int compare(int[][] dataMember1, int[][] dataMember2) {
		if(dataMember1 == dataMember2){
			return 0;
		}else{
			return 1;
		}
	}
	
	/**rangeCheck( ) : compares the length of the array with the 
	 * incoming node label returns false if the their is not enough 
	 * room in the array*/
	protected boolean rangeCheck(Object list, int vLabel){
	    int[][] am = (int[][])list;
	    if(am.length < vLabel){
	    	return false;
	    }
		return true;
	}
	
	/**existsEdge(int i, int j): returns true if there exists an edge between i and j else returns false*/
	protected boolean existsEdge(int i, int j){
		boolean found = false;
		if(AM[i-1][j-1] == 1){
			found = true;
		}
		return found;
	}
	
	/**putEdge( int i, int j) : adds the edge from i to j to the graph*/
	protected void putEdge(int i, int j){
		if(!(AM[i-1][j-1] == 1)){
			if(!(rangeCheck(AM, i))){
	    		throw new ArrayIndexOutOfBoundsException("The index is out of bounds");
	    	}else{
	    		AM[i-1][j-1] = 1;
	    		Node nodei = new Node(i);
	    		Node nodej = new Node(j);
	    		if(!outNodes.contains(nodei)){
	    			outNodes.add(nodei);
			    }
				if(!inNodes.contains(nodej)){
			    	inNodes.add(nodej);
			    }
			    inNodes.get(inNodes.indexOf(nodej)).incrementInDegree();
			    outNodes.get(outNodes.indexOf(nodei)).incrementOutDegree();
	    	}
		}else{
			
		}
	}
	
	/**removeEdge(int i, int j): deletes the edge from i to j from the graph*/
	protected void removeEdge(int i, int j){
		AM[i-1][j-1] = 0;
	} //deletes the edge from i to j from the graph
	
	/**inDegree(int: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	protected int inDegree(int i){
		Node node = new Node(i);
		if(inNodes.contains(node)){
			return inNodes.get(inNodes.indexOf(node)).getInDegOfNode();
		}else{
			return 0;
		}
	} //returns the in-degree of node i. this method is defined for directed graphs only.
	
	/**outDegree(int: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	protected int outDegree(int i){
		Node node = new Node(i);
		if(outNodes.contains(node)){
			/*DEBUG*///System.out.println("outDegree Test");
			return outNodes.get(outNodes.indexOf(node)).getOutDegOfNode();
		}else{
			return 0;
		}
	} //returns the out-degree of node i. this method is defined for directed graphs only.
	
	/**adjacentVertices(int: i):  returns the nodes that are adjacent to i*/
	protected ArrayList<Node> adjacentVertices(int i){
		ArrayList<Node> adjNodes = new ArrayList<Node>();
			for(int j = 0; j < numOfNodes; j++){
				if(AM[i-1][j] == 1){
					Node node = new Node(j+1);
					adjNodes.add(node);
				}
			}
			for(int k = 0; k < numOfNodes; k++){
				if(AM[k][i-1] == 1){
					Node node = new Node(k+1);
					if(!adjNodes.contains(node)){
						adjNodes.add(node);
					}
				}
			}
		return adjNodes;
	} // returns the nodes that are adjacent to i
	
	/**areAdjacent(int i, int j): returns true if the nodes i and j are adjacent else returns false.*/
	protected boolean areAdjacent(int i, int j){
		if(existsEdge(i, j) || existsEdge(j, i)){
			return true;
		}
		return false;
	} //returns true if the nodes i and j are adjacent else returns false.

	/**Forwarded declarations of abstract methods from the abstract class G
	 * These are not implemented in this child class*/
	protected boolean existsEdge(Edge e){return false;} //returns true if e is an edge else returns false
	protected void putEdge(Edge e){} //adds the edge e to the graph
	protected void removeEdge(Edge e){} //deletes the edge e from the graph
	protected int inDegree(Node i){return 0;} //returns the in-degree of node i. this method is defined for directed graphs only.
	protected int outDegree(Node i){return 0;} //returns the out-degree of node i. this method is defined for directed graphs only.
	protected ArrayList<Node> adjacentVertices(Node i){ArrayList<Node> node = new ArrayList<Node>(); return node;} //returns the nodes that are adjacent to i
	protected boolean areAdjacent(Node i, Node j){return false;} //returns true if the nodes i and j are adjacent else returns false.
	protected int degree(Node i){return 0;} //returns the degree of node i. this method is defined for undirected graphs only.
	protected int degree(int i){return 0;} //returns the degree of node i. this method is defined for undirected graphs only.
}
