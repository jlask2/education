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

import java.util.Stack;

/**TopoSort Class: This classes objects perform a topological sort on a given DAG*/
public class TopoSort{
	
	/**private data members*/
	private Stack<Integer> st;
	
	/**TopoSort Constructor: accepts the number of nodes, the indegree list of nodes and a DAG*/
	TopoSort(int[][] listN, int numOfNodes, DAG dag){
		st = new Stack<Integer>();
		int[] output = new int[numOfNodes];
		int i = 1;
		int t = 0;
		for(int j = 0; j < listN[0].length; j++){
			if(listN[0][j] == 0){
				st.push(j+1);
			}
		}
		
		while(!st.isEmpty()){
			output[t] = st.pop();
			Node node = new Node(output[t]);
			i++;
			int[] adjNodes = dag.adjacentVertices(node);
			for(int k = 0; k < adjNodes.length; k++){
				listN[0][adjNodes[k]-1]--;
				if(listN[0][adjNodes[k]-1] == 0){
					 st.push(adjNodes[k]);
					 
				}
			}	
			t++;
		}	
		if(i > numOfNodes){
			System.out.print("The Topological Sorting of this graph results in");
			for(int h = 0; h < output.length; h++){
				System.out.print(" "+output[h]+" ");
			}
			System.out.println("\n");
		}else{
			System.out.println("This graph contains a cycle. Topological Sort not possible \n");
		}
	}
}