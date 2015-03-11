/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 2
 * Date Assigned: 2/12/2015
 * Date Due: 3/4/2015
 * Date Submitted: 3/11/2015 
 ***********************************/

package poset;

/**Abstract Class DAG Extends G - Directed Adjacency Graph*/
abstract class DAG extends G{
	
	/**private data members*/
	protected TopoSort ts;
	
	public void topoSort(int[][] listN, int numOfNodes){
		ts = new TopoSort(listN, numOfNodes, this);
	}
}
