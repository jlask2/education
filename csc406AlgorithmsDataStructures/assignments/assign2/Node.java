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

public class Node {

	/**private data members*/
	private int vLabel = 0;
	private int index;
	private int degOfNode = 0;
	private int inDegOfNode = 0;
	private int outDegOfNode = 0;
	
	/**Constructor Node */
	public Node(){
		vLabel = 0;
		index = 0;
	}
	
	/**Constructor Node with parameters */
	public Node(int vLabel, int index){
		this.vLabel = vLabel;
		this.index = index;
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
	protected void incrementInDegree(){
		this.inDegOfNode++;
	}
	
	protected void decrementInDegree(){
		this.inDegOfNode--;
		if(this.inDegOfNode < 0){
			this.inDegOfNode = 0;
			throw new IllegalArgumentException("Can't have an inDegree of less than 0");
		}
	}
	
	protected void incrementOutDegree(){
		this.outDegOfNode++;
	}
	
	protected void decrementOutDegree(){
		this.outDegOfNode--;
		if(this.outDegOfNode < 0){
			this.outDegOfNode = 0;
			throw new IllegalArgumentException("Can't have an outDegree of less than 0");
		}
	}
	
	protected void incrementDegree(){
		this.degOfNode++;
	}
	
	protected void decrementDegree(){
		this.degOfNode--;
		if(this.degOfNode < 0){
			this.degOfNode = 0;
			throw new IllegalArgumentException("Can't have an Degree of less than 0");
		}
	}
	
	protected void setVLabel(int vLabel){
		this.vLabel = vLabel;
	}
	
	protected void setIndex(int index){
		this.index = index;
	}
	
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
	protected int getVLabel(){
		return vLabel;
	}
	
	protected int getIndex(){
		return index;
	}
	
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
