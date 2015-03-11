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

import java.util.Stack;

public class TopoSort{
	
	private Stack<Integer> st;
	
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
	
	protected Integer push(Integer node){
	    return st.push(node);
	}
	
	protected Integer peek(){
		return st.peek();
	}
	
	protected Integer pop(){
		return st.pop();
	}
	
	protected boolean isEmpty(){
		return st.isEmpty();
	}
}