/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 1
 * Date Assigned: 1/25/2015
 * Date Due: 2/11/2015
 * Date Submitted: 2/11/2015 
 ***********************************/

package poset;

import java.io.File;

/**Adjacecy Matrix Directed Graph*/
public class AMDG extends G{
	
	/**Default constructor calls the super default constructor and initializes an empty graph*/
	public AMDG(){
		super();
	}
	
	/**Constructor calls the super constructor and passes a file with graph data*/
	public AMDG(boolean weighted, File inFile, int numOfLines, Edge[] fedges, Node[] fnodes){
		super(weighted, inFile, numOfLines, fedges, fnodes);
		System.out.println("Inside AMDG Constructor");
	}
}
