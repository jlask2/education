/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 4
 * Date Assigned: 3/26/2015
 * Date Due: 4/15/2015
 * Date Submitted: 4/15/2015
 ***********************************/

package algoData;

/** Class AVLNode: AVL Node for the implementation of a AVL Tree */
public class AVLNode extends TreeNode {

	/** private data members */
	private int nodeData;
	private AVLNode left;
	private AVLNode right;
 	private AVLNode root;
	
	public AVLNode(int nodeData){
		setNodeData(nodeData);
		setLeft(null);
		setRight(null);
	}

	/**
	 * @return the left
	 */
	public AVLNode getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(AVLNode left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public AVLNode getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(AVLNode right) {
		this.right = right;
	}

	/**
	 * @return the root
	 */
	public AVLNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(AVLNode root) {
		this.root = root;
	}

	/**
	 * @return the nodeData
	 */
	public int getNodeData() {
		return nodeData;
	}

	/**
	 * @param nodeData the nodeData to set
	 */
	public void setNodeData(int nodeData) {
		this.nodeData = nodeData;
	}
}
