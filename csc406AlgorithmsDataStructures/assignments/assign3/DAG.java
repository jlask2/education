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

/**Abstract Class DAG Extends G - Directed Adjacency Graph*/
abstract class DAG extends G{
	
	/**protected data members*/
	protected TopoSort ts;
	
	/**topoSort method: calls a topological sort on the given DAG*/
	public void topoSort(int[][] listN, int numOfNodes){
		ts = new TopoSort(listN, numOfNodes, this);
	}
}
