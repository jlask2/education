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

/** Class Black and Red Node: Black and Red Node for the implementation of a Black and Red Tree */
public class BRNode extends TreeNode {

	/** private data members */
	private int nodeData;
	private BRNode left;
	private BRNode right;
 	private BRNode root;
	
 	/**Constructor*/
	public BRNode(int nodeData){
		setNodeData(nodeData);
		setLeft(null);
		setRight(null);
	}

	/**
	 * @return the left
	 */
	public BRNode getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(BRNode left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public BRNode getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(BRNode right) {
		this.right = right;
	}

	/**
	 * @return the root
	 */
	public BRNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(BRNode root) {
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
