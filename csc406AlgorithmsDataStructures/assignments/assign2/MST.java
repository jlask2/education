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

import java.util.ArrayList;
import java.util.PriorityQueue;

public class MST{
	
	/**private data members*/
	private UnionFind uf;
	private ArrayList<Edge> resultTree;
	private int i;
	private int j;
	private int n;
	private int rooti;
	private int rootj;
	private int resultMinWeight;
	
	MST(PriorityQueue<Edge> pqe, int numOfNodes){
		uf = new UnionFind(pqe, numOfNodes);
	    resultTree = new ArrayList<Edge>();
	    n = numOfNodes - 1;
		
		while ((resultTree.size() < n)&&(!pqe.isEmpty())){
			Edge e;
				e = (Edge)pqe.poll();
				System.out.println(e.toString());
				i = e.getAdjNodei().getVLabel()-1;
				j = e.getAdjNodej().getVLabel()-1;
				rooti = uf.findRoot(i);
				rootj = uf.findRoot(j);
	
			if(rooti != rootj){
				resultTree.add(e);
				uf.union(rooti, rootj);
				resultMinWeight += e.getWeight();
				System.out.println(uf.toString());
			}
		}
	}
	
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		String resultString = "\nThe MST is: "+ resultMinWeight;
		for(int i  = 0; i < resultTree.size(); i++){
			resultString += "\n"+ resultTree.get(i).toString();
		}
		return resultString;
	}
}