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

import java.awt.Color;

/** Class Black and Red Node: Black and Red Node for the implementation of a Black and Red Tree */
public class BRNode extends TreeNode {

	/** private data members */
	protected int nodeData;
	protected int color;
	private Color br;	
	private BRNode parent;
	protected BRNode left;
	protected BRNode right;
    
    /* Black - 1  RED - 0 */
    static final int BLACK = 1;    
    static final int RED   = 0;
	
 	/**Constructor*/
	/*public BRNode(int nodeData){
		setNodeData(nodeData);
		setParent(null);
		setLeft(null);
		setRight(null);
		setColor(null);	
	}*/

    
    /** Constructor */
    public BRNode(int nodeData)
    {
        this( nodeData, null, null );
    } 
    
    /** Constructor */
    public BRNode(int nodeData, BRNode lt, BRNode rt)
    {
        left = lt;
        right = rt;
        this.nodeData = nodeData;
        color = 1;
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
	 * @return the parent
	 */
	public BRNode getParent() {
		return parent;
	}

	/**
	 * @param parent the parent to set
	 */
	public void setParent(BRNode parent) {
		this.parent = parent;
	}

	/**
	 * @return the br
	 */
	public Color getColor() {
		return br;
	}

	/**
	 * @param br the br to set
	 */
	public void setColor(Color br) {
		this.br = br;
	}
	
	@Override
	public String toString(){
		return new String("");
	}
}
