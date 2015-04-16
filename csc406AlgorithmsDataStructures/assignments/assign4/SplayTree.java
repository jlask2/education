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
        root = null;
    }

/*********************************************************
* Methods for Inserting
**********************************************************/    

    /** function to insert nodeData */
    public void insert(int ele){
        
    	SNode z = root;
        SNode p = null;
        
        while (z != null){
            p = z;
            if (ele < p.nodeData){
                z = z.left;
            }else{
                z = z.right;
            }
        }
        
        z = new SNode();
        z.nodeData = ele;
        z.parent = p;
        
        if (p == null){
            root = z;
        }else if (ele < p.nodeData){
            p.left = z;
        }else{
            p.right = z;
        }
        Splay(z);
        count++;
    }

/*********************************************************
* Methods for Rotations
**********************************************************/    
    
    /** rotate **/
    public void makeLeftChildParent(SNode c, SNode p){
        if ((c == null) || (p == null) || (p.left != c) || (c.parent != p)){
            throw new RuntimeException("WRONG");
    	}
    
        if (p.parent != null){
        	
            if (p == p.parent.left){
                p.parent.left = c;
        	}else{ 
                p.parent.right = c;
            }
        }
        
        if (c.right != null){
            c.right.parent = p;
        }
        
        c.parent = p.parent;
        p.parent = c;
        p.left = c.right;
        c.right = p;
    }

    /** rotate **/
    public void makeRightChildParent(SNode c, SNode p)
    {
        if ((c == null) || (p == null) || (p.right != c) || (c.parent != p)){
            throw new RuntimeException("WRONG");
        }
        
        if (p.parent != null){
            
        	if (p == p.parent.left){
                p.parent.left = c;
        	}else{
                p.parent.right = c;
        	}	
        }
        
        if (c.left != null){
            c.left.parent = p;
        }
        
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
    }
    
    private void zig(){
    	
    }

/*********************************************************
* Methods for Splaying
**********************************************************/    

    /** function splay **/
    private void Splay(SNode x){
    	
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

    /** function to remove nodeData */
    public void remove(int ele)
    {
        SNode node = findNode(ele);
        delete(node);
    }

    /** function to remove node */
    private void delete(SNode node)
    {
        if (node == null){
        	return;
        }
 
        Splay(node);
        
        if( (node.left != null) && (node.right !=null)){ 
            
        	SNode max = node.left;
            
            while(max.right!=null){
            	max = max.right;
            }
            
            max.right = node.right;
            node.right.parent = max;
            node.left.parent = null;
            root = node.left;
        
        }else if (node.right != null){
            
        	node.right.parent = null;
            root = node.right;
        
        }else if( node.left !=null){
        
        	node.left.parent = null;
            root = node.left;
        
        }else{
            root = null;
        }
        
        node.parent = null;
        node.left = null;
        node.right = null;
        node = null;
        count--;
    }

/*********************************************************
* Methods for Searching
**********************************************************/
    
    /** Functions to search for nodeData */
    public boolean search(int val)
    {
    	SNode sn = findNode(val);
    	System.out.println("sn.nodeData: "+sn.nodeData);
        return sn != null;
    }
    
    /** Function to find a node*/
    private SNode findNode(int ele){
        SNode z = root;
        //System.out.println("b4z.nodeData: "+z.nodeData);
        while (z != null)
        {
            if (ele < z.nodeData){
                z = z.left;
                //System.out.println("leftz.nodeData: "+z.nodeData);
            }else if (ele > z.nodeData){
                z = z.right;
                //System.out.println("rightz.nodeData: "+z.nodeData);
            }else{
            	//System.out.println("b4z.nodeData: "+z.nodeData);
            	Splay(z);
            	//System.out.println("afz.nodeData: "+z.nodeData);
                return z;
            }
        }
        return null;
    }
    
/*********************************************************
* Methods for Checks and Clearing
**********************************************************/

    /** Functions to count number of nodes */
    public int countNodes()
    {
        return count;
    }
    
    /** Function to check if tree is empty */
    public boolean isEmpty()
    {
        return root == null;
    }

    /** clear tree */
    public void clear()
    {
        root = null;
    }
    
/*********************************************************
* Methods for Output
**********************************************************/
    
    /** Function for inorder traversal **/ 
    public String inorder()
    {
        return inorder(root);
    }
    
    /** Function to do recursive inorder traversal*/
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

    /** Function for preorder traversal **/
    public String preorder()
    {
        return preorder(root);
    }
    
    /** Function to do recursive preorder traversal*/
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

    /** Function for postorder traversal **/
    public String postorder()
    {
        return postorder(root);
    }
    
    /** Function to do recursive postorder traversal*/
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
	/**toString method converts the data structure to a readable string*/
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
