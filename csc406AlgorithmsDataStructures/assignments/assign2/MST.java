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
	UnionFind uf;
	ArrayList<Edge> resultTree;
	int i;
	int j;
	int n;
	int resultMinWeight;
	
	MST(PriorityQueue<Edge> pqe, int numOfNodes){
		//this.pqe = pqe;
		//A = new int[0];//numNodes()];
		uf = new UnionFind(pqe, numOfNodes);
	    resultTree = new ArrayList<Edge>();
	    n = numOfNodes - 1;
		
		while ((resultTree.size() < n)&&(!pqe.isEmpty())){
			Edge e;
				e = (Edge)pqe.poll();
				System.out.println(e.toString());
				i = e.getAdjNodei().getVLabel()-1;
				j = e.getAdjNodej().getVLabel()-1;
				System.out.println("i: "+i+"j: "+j);
			
			if(uf.findRoot(i) != uf.findRoot(j)){
				resultTree.add(e);
				uf.union(i, j);
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