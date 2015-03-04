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

/**Adjacecy List Directed Graph*/
public class ALDG extends G{
	
	/**private data members*/
	private BufferedReader br;
	private int numOfEdges;
	private int numOfNodes;
	private ArrayList<Node>[] AL;
	private Iterator<Node> ite;
	private Queue<Edge> pqe;
	private String line;
	
	/**Forwarded declarations of abstract methods from the abstract class G
	 * These are not implemented in this child class*/
	protected boolean existsEdge(int i, int j){return false;} //returns true if there exists an edge between i and j else returns false
	protected void putEdge(int i, int j){} //adds the edge from i to j to the graph
	protected void removeEdge(int i, int j){} //deletes the edge from i to j from the graph
	protected int inDegree(int i){return 0;} //returns the in-degree of node i. this method is defined for directed graphs only.
	protected int outDegree(int i){return 0;} //returns the out-degree of node i. this method is defined for directed graphs only.
	protected ArrayList<Node> adjacentVertices(int i){ArrayList<Node> node = new ArrayList<Node>(); return node;} // returns the nodes that are adjacent to i
	protected boolean areAdjacent(int i, int j){return false;} //returns true if the nodes i and j are adjacent else returns false.
	protected int degree(Node i){return 0;} //returns the degree of node i. this method is defined for undirected graphs only.
	protected int degree(int i){return 0;} //returns the degree of node i. this method is defined for undirected graphs only.
	
	/**Constructor*/
	public ALDG(BufferedReader br, int numOfNodes, int numOfEdges){
		super(br, numOfNodes, numOfEdges);
		System.out.println("Inside ALDG Constructor\n");
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
		try {
			int i = 0;
			AL = (ArrayList<Node>[])new ArrayList[numOfNodes];
			pqe = new PriorityQueue<Edge>();
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
				
				for(int k = 0; k < AL.length; k++){
					if((!(AL[k].isEmpty()))&&(AL[k].get(0).equals(node1))&&(assigned == false)){
						AL[k].add(node2);
					    if(!inNodes.contains(node2)){
					    	inNodes.add(node2);
					    }
					    inNodes.get(inNodes.indexOf(node2)).incrementInDegree();
					    outNodes.get(outNodes.indexOf(node1)).incrementOutDegree();
						nodeExists = true;
						assigned = true;
					}else if(assigned == false){
						AL[i].add(0, node1);
						AL[i].add(node2);
						outNodes.add(node1);
						if(!inNodes.contains(node2)){
					    	inNodes.add(node2);
					    }
					    inNodes.get(inNodes.indexOf(node2)).incrementInDegree();
					    outNodes.get(outNodes.indexOf(node1)).incrementOutDegree();
						assigned = true;
					}
				}
				if(!nodeExists){
					i++;
				}
		    }
		    if(AL[numOfNodes-1].isEmpty()){
		    	Node lastNode = new Node(numOfNodes);
		    	AL[numOfNodes-1].add(lastNode);
		    }
		    for(int t = 0; t < numOfNodes; t++){
		    	AL[t].trimToSize();
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		Node nodei = new Node(3);
	    Node nodej = new Node(4);
		Edge edge = new Edge(nodei, nodej);
		System.out.println("Does Edge "+nodei.getVLabel()+" "+nodej.getVLabel()+" exist?: "+existsEdge(edge));
		putEdge(edge);
		System.out.println("After putEdge(), does Edge "+nodei.getVLabel()+" "+nodej.getVLabel()+" exist?: "+existsEdge(edge));
		Node nodeo = new Node(4);
		System.out.println("Are Nodes "+nodeo.getVLabel()+" and "+nodej.getVLabel()+" adjacent?: "+areAdjacent(nodeo, nodej));
		ArrayList<Node> adjNodes = adjacentVertices(nodeo);
		ite = adjNodes.iterator(); //note the case for i in iterator
		System.out.print("The Adjacent Nodes to "+nodeo.getVLabel()+" are: ");
		while(ite.hasNext()){
			Node node = ite.next();
			if(node != null){
				System.out.print(node.getVLabel()+", ");
			}
		}
		System.out.print("\n");
		System.out.println("Node "+nodeo.getVLabel()+" has a inDegree of "+inDegree(nodeo)+" and a outDegree of "+outDegree(nodeo));
		
		
		String p = "";
		p += "\nContents of the Adjcency List Data Structure\n";
		for(int i = 0; i < AL.length; i++){
			ite = ((ArrayList<Node>) AL[i]).iterator(); //note the case for i in iterator
			// repeat if there are more elements in the collection
			while (ite.hasNext())  {                    
	            p += ite.next().getVLabel();	//get the next element from the collection
	            if(ite.hasNext()){					
					p += ", ";
				}    	   							
			}
			p += "\n";
		}
		return p;
	}
	
	/**Equal method compares data members of two objects*/
	@Override
	public boolean equals(Object child){      //note the type of the parameter
        ALDG c = (ALDG)child;               // cast the parameter before use
        return ALDG.compare(this.AL, c.AL)  == 0;
    } 

	/**Compare method compares two data members for integer equality, returns 0 if equal else a 1 if not*/
	private static int compare(ArrayList<Node>[] dataMember1, ArrayList<Node>[] dataMember2) {
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
	    ArrayList<Node>[] al = (ArrayList<Node>[])list;
	    if(al.length < vLabel){
	    	return false;
	    }
		return true;
	}
	
	/**existEdge( Edge e): returns true if e is an edge else returns false*/
	protected boolean existsEdge(Edge e){
		Node nodei = e.getAdjNodei();
	    Node nodej = e.getAdjNodej();
		boolean found = false;
	    if(AL[nodei.getVLabel()-1].contains(nodei) && AL[nodei.getVLabel()-1].contains(nodej)){
	    	found = true;
	    }
		return found;
	}
	
	/**putEdge( Edge: e) : adds the edge e to the graph*/
	protected void putEdge(Edge e){
	    Node nodei = e.getAdjNodei();
	    Node nodej = e.getAdjNodej();
	    if(AL[nodei.getVLabel()-1].contains(nodej)){
	    }else{
	    	if(!(rangeCheck(AL, nodei.getVLabel()))){
	    		throw new ArrayIndexOutOfBoundsException("The index is out of bounds");
	    	}else{
	    		AL[nodei.getVLabel()-1].add(nodej);
	    		outNodes.add(nodei);
				if(!inNodes.contains(nodej)){
			    	inNodes.add(nodej);
			    }
			    inNodes.get(inNodes.indexOf(nodej)).incrementInDegree();
			    outNodes.get(outNodes.indexOf(nodei)).incrementOutDegree();
	    	}
	    }
	}
	
	/**removeEdge(Edge: e): deletes the edge e from the graph*/
	protected void removeEdge(Edge e){
		if(existsEdge(e)){
			Node nodei = e.getAdjNodei();
		    Node nodej = e.getAdjNodej();
		    AL[nodei.getVLabel()-1].remove(nodej);
		    if(AL[nodei.getVLabel()-1].size() == 1){
		    	AL[nodei.getVLabel()-1].remove(nodei);
		    }
		}else{
			throw new NullPointerException("This edge does not exist");
		}
	} //deletes the edge e from the graph
	
	/**inDegree(Node: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	protected int inDegree(Node i){
		if(inNodes.contains(i)){
			return inNodes.get(inNodes.indexOf(i)).getInDegOfNode();
		}else{
			return 0;
		}
	} //returns the in-degree of node i. this method is defined for directed graphs only.
	
	/**outDegree(Node: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	protected int outDegree(Node i){
		if(outNodes.contains(i)){
			return outNodes.get(outNodes.indexOf(i)).getOutDegOfNode();
		}else{
			return 0;
		}
	} //returns the out-degree of node i. this method is defined for directed graphs only.
	
	/**adjacentVertices(Node: i): returns the nodes that are adjacent to i*/
	protected ArrayList<Node> adjacentVertices(Node i){
		//AL[i.getVLabel()].
		int numOfAdjNodes = i.getInDegOfNode() + i.getOutDegOfNode();
		ArrayList<Node> adjNodes = new ArrayList<Node>();
		for(int k = 0; k < AL.length; k++){
			if(AL[k].contains(i)){
				if(k == (i.getVLabel()-1)){
					ite = ((ArrayList<Node>) AL[k]).iterator(); //note the case for i in iterator
					ite.next();
					while(ite.hasNext()){
						Node node = ite.next();
						if(!(adjNodes.contains(node))){
							adjNodes.add(node);
						}		
					}
				}else{
					adjNodes.add(AL[k].get(0));
				}
			}
		}
		return adjNodes;
	} //returns the nodes that are adjacent to i
	
	/**areAdjacent(Node i, Node j): returns true if the nodes i and j are adjacent else returns false.*/
	protected boolean areAdjacent(Node i, Node j){
		/*DEBUG*///System.out.println("AL.length = "+AL.length+" | i.getVLabel() = "+i.getVLabel());
		if(AL.length > (i.getVLabel()-1)){
			if(AL[i.getVLabel()-1].contains(j)&&(AL[i.getVLabel()-1].get(0).getVLabel() != i.getVLabel())){
				return true;
			}else{
				return false;
			}
		}else{
			throw new ArrayIndexOutOfBoundsException("There is no index pertaining to this node");
		}
		
	} //returns true if the nodes i and j are adjacent else returns false.
}
