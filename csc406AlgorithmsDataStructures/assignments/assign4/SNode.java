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

/** Class SNode: Splay Node for the implementation of a Splay Tree*/
public class SNode extends TreeNode {

	/** private data members */
	private int nodeData;
	private SNode left;
	private SNode right;
 	private SNode root;
	
 	/**Constructor*/
	public SNode(int nodeData){
		setNodeData(nodeData);
		setLeft(null);
		setRight(null);
	}

	/**
	 * @return the left
	 */
	public SNode getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(SNode left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public SNode getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(SNode right) {
		this.right = right;
	}

	/**
	 * @return the root
	 */
	public SNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(SNode root) {
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
