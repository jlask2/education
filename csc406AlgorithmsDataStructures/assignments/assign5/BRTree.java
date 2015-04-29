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

/** Class RBTree: Extends Abstract Class Tree */
public class BRTree extends Tree{

	/** protected data members */
    
    private String inorderString = "";
    private String preorderString = "";
    private String postorderString = "";
    private String commentString = "";
	
    private BRNode current;
    private BRNode parent;
    private BRNode grand;
    private BRNode great;
    private BRNode header;    
	private static BRNode nullNode;
	
    /* static initializer for nullNode */
    static 
    {
        nullNode = new BRNode(0);
        nullNode.left = nullNode;
        nullNode.right = nullNode;
    }
	
	 /** Constructor */
	 public BRTree(int negInf)
	 {
	     header = new BRNode(negInf);
	     header.left = nullNode;
	     header.right = nullNode;
	 }

/*********************************************************
* Methods for Inserting
**********************************************************/	 
	 
	 /** insert method: to insert a node given its nodeData */
	 public void insert(int item )
	 {
	     current = parent = grand = header;
	     nullNode.nodeData = item;
	     while (current.nodeData != item)
	     {            
	         great = grand; 
	         grand = parent; 
	         parent = current;
	         current = item < current.nodeData ? current.left : current.right;
	         // Check if two BRNode.RED children and fix if so            
	         if (current.left.color == BRNode.RED && current.right.color == BRNode.RED)
	             handleReorient( item );
	     }
	     // Insertion fails if already present
	     if (current != nullNode)
	         return;
	     current = new BRNode(item, nullNode, nullNode);
	     // Attach to parent
	     if (item < parent.nodeData)
	         parent.left = current;
	     else
	         parent.right = current;        
	     handleReorient( item );
	 }
	 
/*********************************************************
* Methods for Searching
**********************************************************/	 
 	 
	 /** search method: to search for a given nodes nodeData */
 	 public boolean search(int val)
 	 {
 	     return search(header.right, val);
 	 }
 	 
 	 /** search method: to find a node given its nodeData*/
 	 private boolean search(BRNode r, int val)
 	 {
 	     boolean found = false;
 	     while ((r != nullNode) && !found)
 	     {
 	         int rval = r.nodeData;
 	         if (val < rval)
 	             r = r.left;
 	         else if (val > rval)
 	             r = r.right;
 	         else
 	         {
 	             found = true;
 	             break;
 	         }
 	         found = search(r, val);
 	     }
 	     return found;
 	 }	 
	 
/*********************************************************
* Methods for Rotations or Recoloring
**********************************************************/	 
	 
	 private void handleReorient(int item)
	 {
	     // Do the color flip
	         current.color = BRNode.RED;
	         current.left.color = BRNode.BLACK;
	         current.right.color = BRNode.BLACK;
	 
	         if (parent.color == BRNode.RED)   
	         {
	             // Have to rotate
	         grand.color = BRNode.RED;
	         if (item < grand.nodeData != item < parent.nodeData)
	             parent = rotate( item, grand );  // Start dbl rotate
	         current = rotate(item, great );
	         current.color = BRNode.BLACK;
	     }
	     // Make root BRNode.BLACK
	     header.right.color = BRNode.BLACK; 
	 }      
	 
	 private BRNode rotate(int item, BRNode parent)
	 {
	     if(item < parent.nodeData)
	         return parent.left = item < parent.left.nodeData ? rotateWithLeftChild(parent.left) : rotateWithRightChild(parent.left) ;  
	     else
	         return parent.right = item < parent.right.nodeData ? rotateWithLeftChild(parent.right) : rotateWithRightChild(parent.right);  
	 }
	 
	 /** Rotate binary tree node with left child */
	 private BRNode rotateWithLeftChild(BRNode k2)
	 {
	     BRNode k1 = k2.left;
	     k2.left = k1.right;
	     k1.right = k2;
	     return k1;
	 }
	 
	 /** Rotate binary tree node with right child */
	 private BRNode rotateWithRightChild(BRNode k1)
	 {
	     BRNode k2 = k1.right;
	     k1.right = k2.left;
	     k2.left = k1;
	     return k2;
	 }

/*********************************************************
* Methods for Utility 
**********************************************************/	 
	 
	 /** countNodes method: to count the total number of nodes */
	 public int countNodes()
	 {
	     return countNodes(header.right);
	 }
	 
	 /** countNodes method: to count the total number of nodes */
	 private int countNodes(BRNode r)
	 {
	     if (r == nullNode)
	         return 0;
	     else
	     {
	         int l = 1;
	         l += countNodes(r.left);
	         l += countNodes(r.right);
	         return l;
	     }
	 }
	 
	 /** isEmpty method: to check if the tree is empty */
	 public boolean isEmpty()
	 {
	     return header.right == nullNode;
	 }
	 
	 /** clear method: clears the tree */
	 public void clear()
	 {
	     header.right = nullNode;
	 }
	 
/*********************************************************
* Methods for Output
**********************************************************/	 
	 
	 /** inorder method: for inorder traversal 
	 * @return **/ 
	 public String inorder()
	 {
	     return inorder(header.right);
	 }
	 
	 /** inorder method: to do recursive inorder traversal*/
	 private String inorder(BRNode r)
	 {
	     if (r != nullNode)
	     {
	         inorder(r.left);
	         char c = 'B';
	         if (r.color == 0)
	             c = 'R';
	         inorderString += r.nodeData +""+c+" ";
	         inorder(r.right);
	     }
	     return inorderString;
	 }
	 
	 /** preorder method: for preorder traversal 
	 * @return **/
	 public String preorder()
	 {
	     return preorder(header.right);
	 }
	 
	 /** preorder method: to do recursive preorder traversal*/
	 private String preorder(BRNode r)
	 {
	     if (r != nullNode)
	     {
	         char c = 'B';
	         if (r.color == 0)
	             c = 'R';
	         preorderString += r.nodeData +""+c+" ";
	         preorder(r.left);             
	         preorder(r.right);
	     }
	     return preorderString;
	 }
	 
	 /** postorder method: for postorder traversal 
	 * @return **/
	 public String postorder()
	 {
	     return postorder(header.right);
	 }
	 
	 /** postorder method: to do recursive postorder traversal*/
	 private String postorder(BRNode r)
	 {
	     if (r != nullNode)
	     {
	         postorder(r.left);             
	         postorder(r.right);
	         char c = 'B';
	         if (r.color == 0)
	             c = 'R';
	         postorderString += r.nodeData +""+c+" ";
	     }
	     return postorderString;
	 }
	 
    @Override
	/**toString method: converts the data structure to a readable string*/
    public String toString(){
    	inorderString = "";
    	preorderString = "";
    	postorderString = "";
    	String resultString = "";
    	resultString+="\nPost order : "+postorder();
    	resultString+="\nPre order : "+preorder();
        resultString+="\nIn order : "+inorder();
        resultString+=commentString;
        commentString = "";
    	return resultString;
    } 
}
