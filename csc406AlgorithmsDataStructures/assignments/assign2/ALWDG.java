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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.PriorityQueue;
import java.util.Queue;

/**Adjacecy List Weighted Directed Graph*/
public class ALWDG extends G{
	
	/**private data members*/
	private BufferedReader br;
	private int numOfEdges;
	private int numOfNodes;
	private int weight;
	private ArrayList<Node>[] AL;
	private Iterator<Node> ite;
	private Queue<Integer> pq; 
	private String line;
	
	/**Forwarded declarations of abstract methods from the abstract class G
	 * These are not implemented in this child class*/
	protected boolean existsEdge(int i, int j){return false;} //returns true if there exists an edge between i and j else returns false
	protected void putEdge(int i, int j){} //adds the edge from i to j to the graph
	protected void removeEdge(int i, int j){} //deletes the edge from i to j from the graph
	protected int inDegree(int i){return 0;} //returns the in-degree of node i. this method is defined for directed graphs only.
	protected int outDegree(int i){return 0;} //returns the out-degree of node i. this method is defined for directed graphs only.
	protected Node adjacentVertices(int i){Node node = new Node(0); return node;} // returns the nodes that are adjacent to i
	protected boolean areAdjacent(int i, int j){return false;} //returns true if the nodes i and j are adjacent else returns false.
	protected int degree(Node i){return 0;} //returns the degree of node i. this method is defined for undirected graphs only.
	protected int degree(int i){return 0;} //returns the degree of node i. this method is defined for undirected graphs only.
	
	/**Constructor calls the super constructor and passes a file with graph data*/
	public ALWDG(BufferedReader br, int numOfNodes, int numOfEdges){
		super(br, numOfNodes, numOfEdges);
		System.out.println("Inside ALWDG Constructor\n");
		this.br = br;
		this.numOfNodes = numOfNodes;
		this.numOfEdges = numOfEdges;
		System.out.println("Number of Edges: "+this.numOfEdges);
		System.out.println("Number of Nodes: "+this.numOfNodes);
		constructAD();
		System.out.println(toString());
	}
	
	protected void constructAD(){
		try {
			int i = 0;
			AL = (ArrayList<Node>[])new ArrayList[numOfNodes];
			pq = new PriorityQueue<Integer>();
			for(int e = 0; e < AL.length; e ++){
				AL[e] = new ArrayList<Node>(numOfNodes);
				AL[e].ensureCapacity(numOfEdges-1);
			}
			br.mark(100);
		    while((line = br.readLine()) != null){	
		    	boolean assigned = false;
		    	boolean nodeExists = false;
				String[] lineArray = line.split(" ");
				Node node1 = new Node(Integer.parseInt(lineArray[0]));
				Node node2 = new Node(Integer.parseInt(lineArray[1]));
				pq.add(Integer.parseInt(lineArray[2]));		
				
				for(int k = 0; k < AL.length; k++){
					if((!(AL[k].isEmpty()))&&(AL[k].get(0).equals(node1))&&(assigned == false)){
						AL[k].add(node2);
						nodeExists = true;
						assigned = true;
					}else if(assigned == false){
						AL[i].add(0, node1);
						AL[i].add(node2);
						assigned = true;
					}
				}
				if(!nodeExists){
					i++;
				}
		    }
		    for(int t = 0; t < numOfNodes; t++){
		    	AL[t].trimToSize();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public String toString(){
		String p = "";
		p += "\nContents of the Adjcency List Data Structure\n";
		for(int i = 0; i < AL.length; i++){
			ite = ((ArrayList<Node>) AL[i]).iterator(); //note the case for i in iterator
			// repeat if there are more elements in the collection
			while (ite.hasNext())  {                    
	            p += ite.next().getVLabel();	//get the next element from the collection
				if(ite.hasNext()){
					p += " weight-"+pq.remove()+"-> ";//process node.
				}
			}
			p += "\n";
		}
		return p;
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
	
	/**removeEdge(Edge: e): deletes the edge e from the graph*/
	protected void removeEdge(Edge e){} //deletes the edge e from the graph
	
	/**inDegree(Node: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	protected int inDegree(Node i){return 0;} //returns the in-degree of node i. this method is defined for directed graphs only.
	
	/**outDegree(Node: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	protected int outDegree(Node i){return 0;} //returns the out-degree of node i. this method is defined for directed graphs only.
	
	/**adjacentVertices(Node: i): returns the nodes that are adjacent to i*/
	protected Node adjacentVertices(Node i){Node node = new Node(0); return node;} //returns the nodes that are adjacent to i
	
	/**areAdjacent(Node i, Node j): returns true if the nodes i and j are adjacent else returns false.*/
	protected boolean areAdjacent(Node i, Node j){return false;} //returns true if the nodes i and j are adjacent else returns false.
}
