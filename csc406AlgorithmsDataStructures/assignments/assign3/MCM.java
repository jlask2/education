/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 3
 * Date Assigned: 3/4/2015
 * Date Due: 3/25/2015
 * Date Submitted: 3/25/2015 
 ***********************************/

package graph;

import java.util.ArrayList;
import java.util.PriorityQueue;

/**MCM Class: Matrix Chain Multiplication determines the least number of matrix products needed*/
public class MCM{
	
	/**private data members*/
	private UnionFind uf;
	private ArrayList<Edge> resultTree;
	private int i;
	private int j;
	private int n;
	private int rooti;
	private int rootj;
	private int resultMinWeight;
	
	/**MCM Constructor: accepts a priority queue and the number of nodes*/
	MCM(PriorityQueue<Edge> pqe, int numOfNodes){
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
		String resultString = "\nThe MST's minimum weight is: "+ resultMinWeight;
		for(int i  = 0; i < resultTree.size(); i++){
			resultString += "\n"+ resultTree.get(i).toString();
		}
		resultString +="\n";
		return resultString;
	}
}