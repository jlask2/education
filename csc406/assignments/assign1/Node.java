package poset;

public class Node {

	/**private data members*/
	private int degOfNode;
	private int inDegOfNode;
	private int outDegOfNode;
	
	/**Constructor Node */
	public Node(){
		degOfNode = 0;
		inDegOfNode = 0;
		outDegOfNode = 0;
	}
	
	/**Constructor Node with parameters */
	public Node(int degOfNode, int inDegOfNode, int outDegOfNode){
		this.degOfNode = degOfNode;
		this.inDegOfNode = inDegOfNode;
		this.outDegOfNode = outDegOfNode;
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
}
