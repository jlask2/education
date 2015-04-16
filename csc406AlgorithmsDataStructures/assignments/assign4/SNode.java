/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 4
 * Date Assigned: 3/26/2015
 * Date Due: 4/15/2015
 * Date Submitted: 4/16/2015
 ***********************************/

package algoData;

/** Class SNode: Splay Node for the implementation of a Splay Tree*/
public class SNode extends TreeNode {

	/** private data members */
	protected int nodeData;
	protected SNode left;
	protected SNode right;
 	protected SNode parent;
	
 	/**Constructor*/
	public SNode(int nodeData){
		setNodeData(nodeData);
		setParent(null);
		setLeft(null);
		setRight(null);
	}

	/**Constructor*/
	public SNode(int nodeData, SNode left, SNode right, SNode parent){
		setNodeData(nodeData);
		setParent(parent);
		setLeft(left);
		setRight(right);
	}
	
	public SNode() {
		setNodeData(0);
		setParent(null);
		setLeft(null);
		setRight(null);
	}

	/**
	 * @return the left
	 */
	protected SNode getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	protected void setLeft(SNode left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	protected SNode getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	protected void setRight(SNode right) {
		this.right = right;
	}

	/**
	 * @return the parent
	 */
	protected SNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	protected void setParent(SNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the nodeData
	 */
	protected int getNodeData() {
		return nodeData;
	}

	/**
	 * @param nodeData the nodeData to set
	 */
	protected void setNodeData(int nodeData) {
		this.nodeData = nodeData;
	}
}
