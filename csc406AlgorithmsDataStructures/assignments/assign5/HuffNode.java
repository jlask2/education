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

/** Class HuffNode: Huffman Node for the implementation of a Huffman Tree */
public class HuffNode extends TreeNode {

	/** private data members */
	private int nodeData;
	private HuffNode left;
	private HuffNode right;
 	private HuffNode root;
	
 	/**Constructor*/
	public HuffNode(int nodeData){
		setNodeData(nodeData);
		setLeft(null);
		setRight(null);
	}

	/**
	 * @return the left
	 */
	public HuffNode getLeft() {
		return left;
	}

	/**
	 * @param left the left to set
	 */
	public void setLeft(HuffNode left) {
		this.left = left;
	}

	/**
	 * @return the right
	 */
	public HuffNode getRight() {
		return right;
	}

	/**
	 * @param right the right to set
	 */
	public void setRight(HuffNode right) {
		this.right = right;
	}

	/**
	 * @return the root
	 */
	public HuffNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(HuffNode root) {
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
