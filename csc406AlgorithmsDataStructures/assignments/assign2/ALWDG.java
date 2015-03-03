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
}
