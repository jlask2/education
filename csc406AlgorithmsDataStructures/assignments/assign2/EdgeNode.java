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

public class EdgeNode extends Node{

	/**private data members*/
	private int vLabel = 0;
	private int weight = 0;
	
	/**Constructor EdgeNode with parameters */
	public EdgeNode(int vLabel, int weight){
		this.vLabel = vLabel;
		this.setWeight(weight);
	}
	
	/**Equal method compares data members of two Node objects*/
	@Override
	public boolean equals(Object node){      //note the type of the parameter
        EdgeNode n1 = (EdgeNode)node;               // cast the parameter before use
        return EdgeNode.compare(this.vLabel, n1.vLabel)  == 0;
    } 

	/**Compare method compares two Node data members for integer equality, returns 0 if equal else a 1 if not*/
	private static int compare(int dataMember1, int dataMember2) {
		if(dataMember1 == dataMember2){
			return 0;
		}else{
			return 1;
		}
	}
	
	protected void setVLabel(int vLabel){
		this.vLabel = vLabel;
	}
	
	/**
	 * @param weight the weight to set
	 */
	protected void setWeight(int weight) {
		this.weight = weight;
	}
	
	/**Accessor Methods*/
	protected int getVLabel(){
		return vLabel;
	}

	/**
	 * @return the weight
	 */
	protected int getWeight() {
		return weight;
	}
}
