/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 5
 * Date Assigned: 4/18/2015
 * Date Due: 4/29/2015
 * Date Submitted: 4/29/2015
 ***********************************/

package algoData;

/** Class HuffTree: Extends Abstract Class Tree */
public class HuffTree extends Tree{

	/** protected data members */
    private HuffNode node;
	private HuffNode root;
    
	public HuffTree(){
		root = null;
	}
	
	/** search method: */
	protected void search(HuffNode node){
		
	}
	
	/** insert method: */
	protected void insert(HuffNode node){
		search(node);
	}
	
	/** delete method: */
	protected void delete(HuffNode node){
		search(node);
	}
	
	/** zigRotation method: */
	private void zigRotation(){
		
	}
	
	/** zigZigRotation method: */
	private void zigZigRotation(){
		
	}
	
	/** zigZagRotation method: */
	private void zigZagRotation(){
		
	}
	
	/** is Parent method: */
	private boolean isParent(){
		return false;
	}
	
	/** isChild method: */
	private boolean isChild(){
		return false;
	}	
	
	@Override
	/** toString method: */
	public String toString(){
		String resultTree = "";
		return resultTree;
	}
}
