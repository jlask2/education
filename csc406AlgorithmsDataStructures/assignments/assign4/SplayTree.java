/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 4
 * Date Assigned: 2/12/2015
 * Date Due: 3/26/2015
 * Date Submitted: 4/15/2015
 ***********************************/

package algoData;

/** Class SplayTree: Extends Abstract Class Tree */
public class SplayTree extends Tree{

	/** protected data members */
	private SNode root;
    private SNode parent;
    private SNode child;
	
    /** Constructor*/
	public SplayTree(){
		root = null;
	}
	
	/** search method: */
	protected void search(SNode node){
		splay();
	}
	
	/** insert method: */
	protected void insert(SNode node){
		search(node);
	}
	
	/** delete method: */
	protected void delete(SNode node){
		search(node);
	}
	
	/** splay method: */
	private void splay(){
		
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
