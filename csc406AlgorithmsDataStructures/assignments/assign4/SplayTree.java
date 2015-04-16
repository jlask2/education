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

import java.util.Scanner;

/** SplayTree Class: A Top Down Implementation of a Splay Tree*/
class SplayTree
{
	/**Private Data Members*/
    private SNode root;
    private int count = 0;
    private String inorderString;
    private String preorderString;
    private String postorderString;

    /** Constructor */
    public SplayTree(){ 
        root = null;      // set up an empty tree
    }

/*********************************************************
* Methods for Inserting
**********************************************************/    

    /** insert method: to insert a node given its nodeData */
    public void insert(int ele){
        
    	boolean duplicate = false; // checks for duplicate nodes
    	SNode z = root;            // start at the root
        SNode p = null;            // set up a comparative parent node
        
        while (z != null){         // if the node taken on by z 
        	                       // starting with the root, is not null
            p = z;                 // set p to z
            if (ele < p.nodeData){ // if the node we are inserting 
            	                   // is less than the comparative node p
                z = z.left;        // traverse the left subtree 
            }else if (ele > p.nodeData){ // else if the data is greater than 
            	                         //the comparative node p
                z = z.right;             // traverse the right subtree   
            }else{                  // else we have a duplicate node 
            	duplicate = true;   // we have a duplicate node
            	z = null;           // set the node to null and exit loop
            }
        }
        
        if(duplicate == false){  // if we do not have a duplicate node
	        z = new SNode();     // create the new node we are inserting     
	        z.nodeData = ele;    // set the new nodes nodeData to the data
	        z.parent = p;        // set the parent of the new node to p
	        
	        if (p == null){      // if p is null 
	            root = z;        // than this must be the first node inserted 
	        }else if (ele < p.nodeData){  // else if the data is less than
	        	 						  // the parent nodes data 
	            p.left = z;            // set the new child as the left child
	        }else{                     // else the data is greater than 
	        					       // the parent nodes data
	            p.right = z;           // set the new child as the right child
	        }
	        splay(z);               // splay the newly inserted node to the root
	        count++;                // increment the total number of nodes
        }
    }

/*********************************************************
* Methods for Rotations
**********************************************************/    
    
    /** method: rotate **/
    public void makeLeftChildParent(SNode c, SNode p){
    	
    	// check to see if this is the right rotation being called
        if ((c == null) || (p == null) || (p.left != c) || (c.parent != p)){
            throw new RuntimeException("This is not the correct rotation");
    	}
    
        if (p.parent != null){    // if grandparent is not null
        	
            if (p == p.parent.left){  // if the parent is the left child of the grandparent 
                p.parent.left = c;    // set the child as the left child of the grandparent
        	}else{ 
                p.parent.right = c;  // else set the child as the right child of the grandparent
            }
        }
        
        if (c.right != null){  // if the childs right child is not null 
            c.right.parent = p;  // set the childs right childs parent to the parent
        }
        
        c.parent = p.parent; // set the childs parent to the grandparent   
        p.parent = c;       // set the parents parent to the child
        p.left = c.right;   // set the parents left child to the childs right child
        c.right = p;        // set the childs right child to the parent
    }

    /** method: rotate **/
    public void makeRightChildParent(SNode c, SNode p){
    	
    	// check to see if this is the right rotation being called
        if ((c == null) || (p == null) || (p.right != c) || (c.parent != p)){
            throw new RuntimeException("This is not the correct rotation");
        }
        
        if (p.parent != null){        // if grandparent is not null
        	
            if (p == p.parent.left){  // if the parent is the left child of the grandparent 
                p.parent.left = c;    // set the child as the left child of the grandparent
        	}else{ 
                p.parent.right = c;   // else set the child as the right child of the grandparent
            }
        }
        
        if (c.left != null){    // if the childs left child is not null 
            c.left.parent = p;  // set the childs left childs parent to the parent
        }
        
        c.parent = p.parent; // set the childs parent to the grandparent   
        p.parent = c;        // set the parents parent to the child
        p.right = c.left;    // set the parents right child to the childs left child
        c.left = p;          // set the childs left child to the parent
    }
    
    /** method:  */
    private void zig(){
    	
    }

/*********************************************************
* Methods for Splaying
**********************************************************/    

    /** splay method: splays the given node to the root of the tree */
    private void splay(SNode x){
    	
        while (x.parent != null){
            SNode Parent = x.parent;
            SNode GrandParent = Parent.parent;
            
            if (GrandParent == null){
                
            	if (x == Parent.left){
                    makeLeftChildParent(x, Parent);
            	}else{
                    makeRightChildParent(x, Parent);
                }
            }else{
                if (x == Parent.left){
                    
                	if (Parent == GrandParent.left){
                        makeLeftChildParent(Parent, GrandParent);
                        makeLeftChildParent(x, Parent);
                    }else{
                        makeLeftChildParent(x, x.parent);
                        makeRightChildParent(x, x.parent);
                    }
                }else{
                    if (Parent == GrandParent.left){
                        makeRightChildParent(x, x.parent);
                        makeLeftChildParent(x, x.parent);
                    }else{
                        makeRightChildParent(Parent, GrandParent);
                        makeRightChildParent(x, Parent);
                    }
                }
            }
        }
        root = x;
    }

/*********************************************************
* Methods for Deleting
**********************************************************/    

    /** remove method: to remove a node given a nodes nodeData */
    public void remove(int ele){
        SNode node = findNode(ele); // find the node in question
        delete(node);               // to delete it
    }

    /** delete method: to delete the given node */
    private void delete(SNode node){
    	
        if (node == null){ // if the node in question is null  
        	return;        // and could not be found, do nothing
        }
 
        splay(node); // bring the node to the root to be deleted
        
        //handle the case of a split tree due to the deletion
        if( (node.left != null) && (node.right !=null)){  
                         // if the node has both left and right children 
        	SNode max = node.left; // find the max node of the left subtree
            
            while(max.right!=null){ // traverse the left subtree until 
            	//you have the right most child of the left subtree 
            	//which should be the max node of the left subtree
            	max = max.right;
            }
            
            max.right = node.right; // set the max node of the left subtrees  
                                    // right child as the nodes right child
            node.right.parent = max; // set the nodes right childs parent as the max node
            node.left.parent = null; // set the nodes left childs parent to null
            root = node.left; // set the nodes left child as the new root
        
        }else if (node.right != null){ // else if the node has a right child(subtree) 
        	                           // but no left child(subtree)
        	node.right.parent = null; // set the nodes right childs parent to null 
            root = node.right;        // set the nodes right child to the new root
        
        }else if( node.left !=null){ // else if the node has a left child(subtree) 
        	                         // but no right child(subtree)
        	node.left.parent = null; // set the nodes left childs parent to null
            root = node.left;        // set the nodes left child as the new root
        
        }else{                  // else the node has no children thus is the last node
            root = null;           // set the root to null 
        }
        
        //delete the node in question by setting all parameters to null 
        node.parent = null;  // set the nodes parent to null
        node.left = null;    // set the nodes left child to null  
        node.right = null;   // set the nodes right child to null 
        node = null;         // set the node to null
        count--;             // decrement the total number of nodes 
    }

/*********************************************************
* Methods for Searching
**********************************************************/
    
    /** search method: to search for a given nodes nodeData */
    public boolean search(int val)
    {
    	SNode sn = findNode(val); // find the node 
    	//System.out.println("sn.nodeData: "+sn.nodeData);
        return sn != null; // return whether found or not
    }
    
    /** findNode method: to find a node given its nodeData*/
    private SNode findNode(int ele){
        SNode z = root;                   // start at the root
        //System.out.println("b4z.nodeData: "+z.nodeData);
        while (z != null){ // while the root is not null
        	
            if (ele < z.nodeData){ // if the nodes nodeData is less than 
            	                   // the dummy nodes nodeData 
                z = z.left;        // traverse the left subtree
                //System.out.println("leftz.nodeData: "+z.nodeData);
            }else if (ele > z.nodeData){ // else if the nodes nodeData is 
            					// greater than the dummy nodes nodeData 
                z = z.right;    // traverse the right subtree
                //System.out.println("rightz.nodeData: "+z.nodeData);
            }else{
            	//System.out.println("b4z.nodeData: "+z.nodeData);
            	splay(z);// splay on the node that is found and bring to the root
            	//System.out.println("afz.nodeData: "+z.nodeData);
                return z;
            }
        }
        return null;  // did not find the node we were looking for
    }
    
/*********************************************************
* Methods for Utility 
**********************************************************/

    /** countNodes method: to count the total number of nodes */
    public int countNodes()
    {
        return count;
    }
    
    /** isEmpty method: to check if the tree is empty */
    public boolean isEmpty()
    {
        return root == null;
    }

    /** clear method: clears the tree */
    public void clear()
    {
        root = null;
    }
    
/*********************************************************
* Methods for Output
**********************************************************/
    
    /** inorder method: for inorder traversal **/ 
    public String inorder()
    {
        return inorder(root);
    }
    
    /** inorder method: to do recursive inorder traversal*/
    private String inorder(SNode r)
    {
        if (r != null)
        {
            inorder(r.left);
            inorderString += r.nodeData +" ";
            inorder(r.right);
        }
        return inorderString;
    }

    /** preorder method: for preorder traversal **/
    public String preorder()
    {
        return preorder(root);
    }
    
    /** preorder method: to do recursive preorder traversal*/
    private String preorder(SNode r)
    {
        if (r != null)
        {
        	preorderString += r.nodeData +" ";
            preorder(r.left);             
            preorder(r.right);
        }
        return preorderString;
    }

    /** postorder method: for postorder traversal **/
    public String postorder()
    {
        return postorder(root);
    }
    
    /** postorder method: to do recursive postorder traversal*/
    private String postorder(SNode r)
    {
        if (r != null)
        {
            postorder(r.left);             
            postorder(r.right);
            postorderString += r.nodeData +" ";
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
        if(root != null){
        	resultString+="\nroot: "+root.nodeData+"\n"; 
        }
    	return resultString;
    }
}