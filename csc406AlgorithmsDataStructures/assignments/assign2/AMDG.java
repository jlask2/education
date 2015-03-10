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
import java.io.IOException;

/**Adjacecy Matrix Directed Graph*/
public class AMDG extends G{
	
	/**private data members*/
	private BufferedReader br;
	private int numOfEdges;
	private int numOfNodes;
	private int[][] AM;
	private String line;
	private String fileInput;
	
	/**Constructor*/
	public AMDG(BufferedReader br, int numOfNodes, int numOfEdges){
		super(br, numOfNodes, numOfEdges);
		System.out.println("\n\nInside AMDG Constructor\n");
		this.br = br;
		this.numOfNodes = numOfNodes;
		this.numOfEdges = numOfEdges;
		fileInput = "This is the file input data:\n\n0 "+numOfNodes+" "+numOfEdges+"\n";
		constructAD();
		//System.out.println(fileInput);
		System.out.println(toString());
		topoSort(listNodes);
	}
	
	/**constructAD method constructs the given adjacency data structure and populates it from the file input stream*/
	protected void constructAD(){
		/*DEBUG*///System.out.println("breakpoint: inside constructAM");
		AM = new int[numOfNodes][numOfNodes];
		
		try {
			br.reset();
			while((line = br.readLine()) != null){	
				String[] lineArray = line.split(" ");
				int nodei = Integer.parseInt(lineArray[0]);
				int nodej = Integer.parseInt(lineArray[1]);
			
				AM[(nodei-1)][(nodej-1)] = 1;
				listNodes[1][nodei-1]++;
	    		listNodes[0][nodej-1]++;
	    		listNodes[2][nodei-1]++;
	    		listNodes[2][nodej-1]++;
	    		fileInput += line+"\n";
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
		/*Node nodei = new Node(3);
	    Node nodej = new Node(1);
		System.out.println("Does Edge "+nodei.getVLabel()+" "+nodej.getVLabel()+" exist?: "+existsEdge(3, 1));
		putEdge(3, 1);
		System.out.println("After putEdge(), does Edge "+nodei.getVLabel()+" "+nodej.getVLabel()+" exist?: "+existsEdge(3, 1));
		Node nodeo = new Node(1);
		System.out.println("Are Nodes 2 and 1 adjacent?: "+areAdjacent(2, 1));
		int[] adjNodes = adjacentVertices(1);
		System.out.print("The Adjacent Nodes to "+nodeo.getVLabel()+" are: ");
		for(int i = 0; i < adjNodes.length; i++){
			System.out.print(" "+adjNodes[i]+" ");
		}
		System.out.print("\n");
		System.out.println("Node "+nodeo.getVLabel()+" has a inDegree of "+inDegree(3)+" and a outDegree of "+outDegree(3));
		*/
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
		n +="\n";
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
	
	/**rangeCheck( ) : Checks to see if the node labeling is outside the range of 1 -> numOfNodes*/
	protected boolean rangeCheck(int i, int j){
		return i > 0 && i <= numOfNodes && j > 0 && i <= numOfNodes;
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
			if(!(rangeCheck(i, j))){
	    		throw new ArrayIndexOutOfBoundsException("The index is out of bounds");
	    	}else{
	    		AM[i-1][j-1] = 1;
	    		listNodes[1][i-1]++;
	    		listNodes[0][j-1]++;
	    		listNodes[2][i-1]++;
	    		listNodes[2][j-1]++;
	    		fileInput += "line";
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
		return listNodes[i-1][0];
	} //returns the in-degree of node i. this method is defined for directed graphs only.
	
	/**outDegree(int: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	protected int outDegree(int i){
		return listNodes[i-1][1];
	} //returns the out-degree of node i. this method is defined for directed graphs only.
	
	protected int degree(int i){
		return listNodes[i-1][2];
	} //returns the degree of node i. this method is defined for undirected graphs only.
	
	/**adjacentVertices(int: i):  returns the nodes that are adjacent to i*/
	protected int[] adjacentVertices(int i){
		   	int n = 0;
			int[] temp = new int[numOfNodes];
			for(int j = 0; j < numOfNodes; j++){
				if(AM[i-1][j] == 1){
					temp[j] = j+1;
					n++;
				}
			}
			int m = 0;
			int[] adjNodes = new int[n];
			for(int k = 0; k < temp.length; k++){
				if(temp[k] > 0){
					adjNodes[m] = temp[k];
					m++;
				}
			}
		return adjNodes;
	} // returns the nodes that are adjacent to i
	
	/**areAdjacent(int i, int j): returns true if the nodes i and j are adjacent else returns false.*/
	protected boolean areAdjacent(int i, int j){
		if(existsEdge(i, j)){
			return true;
		}
		return false;
	} //returns true if the nodes i and j are adjacent else returns false.	
}
