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

import java.util.Properties;
import java.util.Vector;

/** Class BPNode: B+ Node for the implementation of a B+ Tree*/
public class BPNode extends TreeNode {

	/** private data members */
	private int d;
	private int nodeData;
	private Vector<Properties> keys;
	private Properties pointers;
 	private BPNode root;
	
 	/**Constructor*/
	public BPNode(int nodeData){
		setNodeData(nodeData);
	}

	/**
	 * @return the d
	 */
	public int getD() {
		return d;
	}

	/**
	 * @param d the d to set
	 */
	public void setD(int d) {
		this.d = d;
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

	/**
	 * @return the keys
	 */
	public Vector<Properties> getKeys() {
		return keys;
	}

	/**
	 * @param keys the keys to set
	 */
	public void setKeys(Vector<Properties> keys) {
		this.keys = keys;
	}

	/**
	 * @return the pointers
	 */
	public Properties getPointers() {
		return pointers;
	}

	/**
	 * @param pointers the pointers to set
	 */
	public void setPointers(Properties pointers) {
		this.pointers = pointers;
	}

	/**
	 * @return the root
	 */
	public BPNode getRoot() {
		return root;
	}

	/**
	 * @param root the root to set
	 */
	public void setRoot(BPNode root) {
		this.root = root;
	}	
}
