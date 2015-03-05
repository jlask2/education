/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 2
 * Date Assigned: 2/12/2015
 * Date Due: 3/4/2015
 * Date Submitted: 3/4/2015 
 ***********************************/

package poset;

import java.util.Stack;

public class TopoSort{
	
	private Stack<Integer> st;
	
	TopoSort(){
		st = new Stack<Integer>();
	}
	
	protected Integer push(Integer node){
	    return st.push(node);
	}
	
	protected Integer peek(){
		return st.peek();
	}
	
	protected Integer pop(){
		return st.pop();
	}
	
	protected boolean isEmpty(){
		return st.isEmpty();
	}
}