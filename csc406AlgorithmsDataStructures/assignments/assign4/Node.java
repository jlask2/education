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

/**Node Class: Implementation of a simple node for a graph*/
public class Node {

	/**private data members*/
	private int vLabel = 0;
	
	/**Constructor Node */
	public Node(){
		vLabel = 0;
	}
	
	/**Constructor Node with 1 parameter */
	public Node(int vLabel){
		this.vLabel = vLabel;
	}
	
	/**Equal method compares data members of two Node objects*/
	@Override
	public boolean equals(Object node){      //note the type of the parameter
        Node n1 = (Node)node;               // cast the parameter before use
        return Node.compare(this.vLabel, n1.vLabel)  == 0;
    } 

	/**Compare method compares two Node data members for integer equality, returns 0 if equal else a 1 if not*/
	private static int compare(int dataMember1, int dataMember2) {
		if(dataMember1 == dataMember2){
			return 0;
		}else{
			return 1;
		}
	}
	
	/**Mutator Methods*/
	protected void setVLabel(int vLabel){
		this.vLabel = vLabel;
	}
	
	/**Accessor Methods*/
	protected int getVLabel(){
		return vLabel;
	}
}
