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

/** Class SplayTree **/
class SplayTree
{
    private SNode root;
    private int count = 0;

    /** Constructor **/
    public SplayTree()
    {
        root = null;
    }

    /** Function to check if tree is empty **/
    public boolean isEmpty()
    {
        return root == null;
    }

    /** clear tree **/
    public void clear()
    {
        root = null;
    }

    /** function to insert nodeData */
    public void insert(int ele)
    {
        SNode z = root;
        SNode p = null;
        while (z != null)
        {
            p = z;
            if (ele < p.nodeData)
                z = z.right;
            else
                z = z.left;
        }
        z = new SNode();
        z.nodeData = ele;
        z.parent = p;
        if (p == null)
            root = z;
        else if (ele < p.nodeData)
            p.right = z;
        else
            p.left = z;
        Splay(z);
        count++;
    }
    
    /** rotate **/
    public void makeLeftChildParent(SNode c, SNode p)
    {
        if ((c == null) || (p == null) || (p.left != c) || (c.parent != p))
            throw new RuntimeException("WRONG");

        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else 
                p.parent.right = c;
        }
        if (c.right != null)
            c.right.parent = p;

        c.parent = p.parent;
        p.parent = c;
        p.left = c.right;
        c.right = p;
    }

    /** rotate **/
    public void makeRightChildParent(SNode c, SNode p)
    {
        if ((c == null) || (p == null) || (p.right != c) || (c.parent != p))
            throw new RuntimeException("WRONG");
        if (p.parent != null)
        {
            if (p == p.parent.left)
                p.parent.left = c;
            else
                p.parent.right = c;
        }
        if (c.left != null)
            c.left.parent = p;
        c.parent = p.parent;
        p.parent = c;
        p.right = c.left;
        c.left = p;
    }

    /** function splay **/
    private void Splay(SNode x)
    {
        while (x.parent != null)
        {
            SNode Parent = x.parent;
            SNode GrandParent = Parent.parent;
            if (GrandParent == null)
            {
                if (x == Parent.left)
                    makeLeftChildParent(x, Parent);
                else
                    makeRightChildParent(x, Parent);                 
            } 
            else
            {
                if (x == Parent.left)
                {
                    if (Parent == GrandParent.left)
                    {
                        makeLeftChildParent(Parent, GrandParent);
                        makeLeftChildParent(x, Parent);
                    }
                    else 
                    {
                        makeLeftChildParent(x, x.parent);
                        makeRightChildParent(x, x.parent);
                    }
                }
                else 
                {
                    if (Parent == GrandParent.left)
                    {
                        makeRightChildParent(x, x.parent);
                        makeLeftChildParent(x, x.parent);
                    } 
                    else 
                    {
                        makeRightChildParent(Parent, GrandParent);
                        makeRightChildParent(x, Parent);
                    }
                }
            }
        }
        root = x;
    }

    /** function to remove nodeData **/
    public void remove(int ele)
    {
        SNode node = findNode(ele);
       remove(node);
    }

    /** function to remove node **/
    private void remove(SNode node)
    {
        if (node == null)
            return;

        Splay(node);
        if( (node.left != null) && (node.right !=null))
        { 
            SNode min = node.left;
            while(min.right!=null)
                min = min.right;

            min.right = node.right;
            node.right.parent = min;
            node.left.parent = null;
            root = node.left;
        }
        else if (node.right != null)
        {
            node.right.parent = null;
            root = node.right;
        } 
        else if( node.left !=null)
        {
            node.left.parent = null;
            root = node.left;
        }
        else
        {
            root = null;
        }
        node.parent = null;
        node.left = null;
        node.right = null;
        node = null;
        count--;
    }

    /** Functions to count number of nodes **/
    public int countNodes()
    {
        return count;
    }

    /** Functions to search for an nodeData **/
    public boolean search(int val)
    {
        return findNode(val) != null;
    }
    
    private SNode findNode(int ele)
    {
        SNode z = root;
        while (z != null)
        {
            if (ele < z.nodeData)
                z = z.right;
            else if (ele > z.nodeData)
                z = z.left;
            else
                return z;
        }
        return null;
    }

    /** Function for inorder traversal **/ 
    public void inorder()
    {
        inorder(root);
    }
    private void inorder(SNode r)
    {
        if (r != null)
        {
            inorder(r.left);
            System.out.print(r.nodeData +" ");
            inorder(r.right);
        }
    }

    /** Function for preorder traversal **/
    public void preorder()
    {
        preorder(root);
    }
    private void preorder(SNode r)
    {
        if (r != null)
        {
            System.out.print(r.nodeData +" ");
            preorder(r.left);             
            preorder(r.right);
        }
    }

    /** Function for postorder traversal **/
    public void postorder()
    {
        postorder(root);
    }
    
    private void postorder(SNode r)
    {
        if (r != null)
        {
            postorder(r.left);             
            postorder(r.right);
            System.out.print(r.nodeData +" ");
        }
    }     
}
