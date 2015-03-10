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

/**Adjacecy List Weighted Directed Graph*/
public class ALWDG extends G{
	
	/**private data members*/
	private BufferedReader br;
	private int numOfEdges;
	private int numOfNodes;
	private int weight;
	private ArrayList<Node>[] AL;
	private Iterator<Node> ite;
	//private Queue<Integer> pqw; 
	private PriorityQueue<Edge> pqe;
	private String line;
	private String fileInput;
	
	/**Constructor*/
	public ALWDG(BufferedReader br, int numOfNodes, int numOfEdges){
		super(br, numOfNodes, numOfEdges);
		System.out.println("Inside ALWDG Constructor\n");
		this.br = br;
		this.numOfNodes = numOfNodes;
		this.numOfEdges = numOfEdges;
		fileInput = "This is the file input data:\n\n1 "+numOfNodes+" "+numOfEdges+"\n";
		constructAD();
		System.out.println(fileInput);
		System.out.println(toString());
		topoSort(listNodes);
		findMST(pqe, numOfNodes);
	}
	
	/**constructAD method constructs the given adjacency data structure and populates it from the file input stream*/
	protected void constructAD(){
		try {
			AL = (ArrayList<Node>[])new ArrayList[numOfNodes];
			//pqw = new ArrayBlockingQueue<Integer>(numOfEdges);
			pqe = new PriorityQueue<Edge>();
			for(int e = 0; e < AL.length; e++){
				AL[e] = new ArrayList<Node>(numOfNodes);
			}
			br.mark(100);
		    while((line = br.readLine()) != null){	
				String[] lineArray = line.split(" ");
				Node node1 = new Node(Integer.parseInt(lineArray[0]));
				weight = (Integer.parseInt(lineArray[2]));
				EdgeNode node2 = new EdgeNode(Integer.parseInt(lineArray[1]), weight);				
				Edge e = new Edge(node1, node2, weight);
				pqe.add(e);
				//pqw.add(weight);
				AL[node1.getVLabel()-1].add(node2);
			    listNodes[1][node1.getVLabel()-1]++; 
			    listNodes[0][node2.getVLabel()-1]++;
			    listNodes[2][node1.getVLabel()-1]++;
			    listNodes[2][node2.getVLabel()-1]++;
			    fileInput += line+"\n";
		    }
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		/*Node nodei = new Node(3);
	    Node nodej = new Node(2);
		Edge edge = new Edge(nodei, nodej);
		System.out.println("Does Edge "+nodei.getVLabel()+" "+nodej.getVLabel()+" exist?: "+existsEdge(edge));
		putEdge(edge);
		System.out.println("After putEdge(), does Edge "+nodei.getVLabel()+" "+nodej.getVLabel()+" exist?: "+existsEdge(edge));
		Node nodeo = new Node(3);
		System.out.println("Are Nodes "+nodeo.getVLabel()+" and "+nodej.getVLabel()+" adjacent?: "+areAdjacent(nodeo, nodej));
		int[] adjNodes = adjacentVertices(3);
		System.out.print("The Adjacent Nodes to "+nodeo.getVLabel()+" are: ");
		for(int i = 0; i < adjNodes.length; i++){
			System.out.print(" "+adjNodes[i]+",");
		}
		System.out.print("\n");
		System.out.println("Node "+nodeo.getVLabel()+" has a inDegree of "+inDegree(nodeo)+" and a outDegree of "+outDegree(nodeo));	
		*/
		String p = "";
		p += "\nContents of the Adjcency List Data Structure\n";
		for(int i = 0; i < AL.length; i++){
			ite = ((ArrayList<Node>) AL[i]).iterator(); //note the case for i in iterator
			// repeat if there are more elements in the collection
			p += (i+1)+" --> ";
			/*p += " weight-";
			if(!pqw.isEmpty()){
				p += pqw.remove()+"-> ";//process node.
			}else{
				p += "1-> ";
			}*/
			while (ite.hasNext())  {
				EdgeNode en = (EdgeNode)ite.next();
	            p += " weight-"+en.getWeight()+"->"+en.getVLabel();	//get the next element from the collection
				/*if(ite.hasNext()){
					p += " weight-";
					if(!pqw.isEmpty()){
						p += pqw.remove()+"-> ";//process node.
					}else{
						p += "1-> ";
					}
				}*/
			}
			p += "\n";
		}
		return p;
	}
	
	/**Equal method compares data members of two objects*/
	@Override
	public boolean equals(Object child){      //note the type of the parameter
        ALWDG c = (ALWDG)child;               // cast the parameter before use
        return ALWDG.compare(this.AL, c.AL)  == 0;
    } 

	/**Compare method compares two Objects data members for integer equality, returns 0 if equal else a 1 if not*/
	private static int compare(ArrayList<Node>[] dataMember1, ArrayList<Node>[] dataMember2) {
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
	
	/**existEdge( Edge e): returns true if e is an edge else returns false*/
	protected boolean existsEdge(int i, int j){
		boolean found = false;
	    if(AL[i-1].contains(j)){
	    	found = true;
	    }
		return found;
	}
	
	/**putEdge( Edge: e) : adds the edge e to the graph*/
	protected void putEdge(int i, int j){
	    Node nodej = new Node(j);
	    if(AL[i-1].contains(nodej)){
	    }else{
	    	if(!(rangeCheck(i, j))){
	    		throw new ArrayIndexOutOfBoundsException("The index is out of bounds");
	    	}else{
	    		AL[i-1].add(nodej);
	    		listNodes[1][i-1]++;
	    		listNodes[0][j-1]++;
	    		listNodes[2][i-1]++;
	    		listNodes[2][j-1]++;
	    	}
	    }
	}
	
	/**removeEdge(Edge: e): deletes the edge e from the graph*/
	protected void removeEdge(int i, int j){
		if(existsEdge(i, j)){
			Node nodei = new Node(i);
		    Node nodej = new Node(j);
		    AL[i-1].remove(nodej);
		    if(AL[i-1].size() == 1){
		    	AL[i-1].remove(nodei);
		    }
		}else{
			throw new NullPointerException("This edge does not exist");
		}
	} //deletes the edge e from the graph
	
	/**inDegree(Node: i): returns the in-degree of node i. this method is defined for directed graphs only.*/
	protected int inDegree(int i){
		return listNodes[i-1][0];
	} //returns the in-degree of node i. this method is defined for directed graphs only.
	
	/**outDegree(Node: i): returns the out-degree of node i. this method is defined for directed graphs only.*/
	protected int outDegree(int i){
		return listNodes[i-1][1];
	} //returns the out-degree of node i. this method is defined for directed graphs only.
	
	protected int degree(int i){
		return listNodes[i-1][2];
	} //returns the degree of node i. this method is defined for undirected graphs only.
	
	/**adjacentVertices(Node: i): returns the nodes that are adjacent to i*/
	protected int[] adjacentVertices(int i){
		int[] adjNodes = new int[AL[i-1].size()];
		int m = 0;
		ite = AL[i-1].iterator();
		while(ite.hasNext()){
			adjNodes[m] = ite.next().getVLabel();
			m++;
		}
		return adjNodes;
	} //returns the nodes that are adjacent to i
	
	/**areAdjacent(Node i, Node j): returns true if the nodes i and j are adjacent else returns false.*/
	protected boolean areAdjacent(int i, int j){
		/*DEBUG*///System.out.println("AL.length = "+AL.length+" | i.getVLabel() = "+i.getVLabel());
		if(AL.length > (i-1)){
			if(AL[i-1].contains(j)&&(AL[i-1].get(0).getVLabel() != j)){
				return true;
			}else{
				return false;
			}
		}else{
			throw new ArrayIndexOutOfBoundsException("There is no index pertaining to this node");
		}
	} //returns true if the nodes i and j are adjacent else returns false.
}
