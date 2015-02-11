/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 1
 * Date Assigned: 1/25/2015
 * Date Due: 2/11/2015
 * Date Submitted: 2/11/2015 
 ***********************************/

package poset;

public class Node {

	/**private data members*/
	private int degOfNode;
	private int inDegOfNode;
	private int outDegOfNode;
	private int vLabel;
	private int index;
	
	/**Constructor Node */
	public Node(){
		degOfNode = 0;
		inDegOfNode = 0;
		outDegOfNode = 0;
		vLabel = 0;
	}
	
	/**Constructor Node with parameters */
	public Node(int degOfNode, int inDegOfNode, int outDegOfNode, int vlabel){
		this.degOfNode = degOfNode;
		this.inDegOfNode = inDegOfNode;
		this.outDegOfNode = outDegOfNode;
		this.vLabel = vLabel;
	}
	
	public Node(int vLabel){
		this.vLabel = vLabel;
	}
	
	/**Mutator Methods*/
	protected void setDegOfNode(int degOfNode){
		this.degOfNode = degOfNode;
	}
	
	protected void setInDegOfNode(int inDegOfNode){
		this.inDegOfNode = inDegOfNode;
	}
	
	protected void setOutDegOfNode(int outDegOfNode){
		this.outDegOfNode = outDegOfNode;
	}
	
	protected void setVLabel(int vLabel){
		this.vLabel = vLabel;
	}
	
	protected void setIndex(int index){
		this.index = index;
	}
	
	/**Accessor Methods*/
	protected int getDegOfNode(){
		return degOfNode;
	}
	
	protected int getInDegOfNode(){
		return inDegOfNode;
	}
	
	protected int getOutDegOfNode(){
		return outDegOfNode;
	}
	
	protected int getVLabel(){
		return vLabel;
	}
	
	protected int getIndex(){
		return index;
	}

}
