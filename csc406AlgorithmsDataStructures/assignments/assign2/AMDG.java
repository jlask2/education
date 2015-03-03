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

/**Adjacecy Matrix Directed Graph*/
public class AMDG extends G{
	
	/**private data members*/
	private BufferedReader br;
	private int type;
	private int numOfEdges;
	private int numOfNodes;
	private int[][] AM;
	private String line;
	
	/**Constructor calls the super constructor and passes a file with graph data*/
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
				
				///*DEBUG*/System.out.println("nodei: "+nodei+"| nodej: "+nodej);
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
	public String toString(){
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
	
	/**existsEdge(int i, int j): returns true if there exists an edge between i and j else returns false*/
	protected boolean existsEdge(int i, int j){
		boolean found = false;
		Edge e = new Edge(i, j);
		for(int k = 0; k < numOfEdges; k++){
			/*if(e.equals(edges[k])){
				found = true;
			}*/
		}
		return found;
	}
	
	/**existEdge( Edge e): returns true if e is an edge else returns false*/
	protected boolean existsEdge(Edge e){
		boolean found = false;
		for(int i = 0; i < numOfEdges; i++){
			/*if(e.equals(edges[i])){
				found = true;
			}*/
		}
		return found;
	}
	
	/**putEdge( Edge: e) : adds the edge e to the graph*/
	protected void putEdge(Edge e){
		/*edges[iedges] = e;
		iedges++;*/
		numOfEdges++;
	}
	
	/**putEdge( int i, int j) : adds the edge from i to j to the graph*/
	protected void putEdge(int i, int j){
		Edge edge = new Edge(i, j);
		numOfEdges++;
	}
}
