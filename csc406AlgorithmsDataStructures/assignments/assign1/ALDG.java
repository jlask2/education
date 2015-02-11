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

/**Adjacecy List Directed Graph*/
public class ALDG extends G{
	
	private int[][] AM;
	
	/**Default constructor calls the super default constructor and initializes an empty graph*/
	public ALDG(){
		super();
	}
	
	/**Constructor calls the super constructor and passes a file with graph data*/
	public ALDG(boolean weighted, File inFile, int numOfLines, Edge[] fedges, Node[] fnodes){
		super(weighted, inFile, numOfLines, fedges, fnodes);
		System.out.println("Inside ALDG Constructor");	
	}
	
	protected void constructAM(){
		/*DEBUG*///System.out.println("breakpoint: inside constructAM");
		AM = new int[numNodes()][numNodes()];
		associateIndexWithVLabel();
		for(int i = 0; i < numNodes(); i++){
			for(int j = 0; j < numNodes(); j++){
				AM[i][j] = 0;
			}
		}
		//int c = 0;
		/*DEBUG*///System.out.println("breakpoint: "+ getEdges()[k].getAdjNodei().getIndex());
		for(int k = 0;  k < getEdges().length; k++){
			for(int p = 0; p < numNodes(); p++){
				if(getCnodes()[p].getVLabel() == getEdges()[k].getAdjNodei().getVLabel()){
					getEdges()[k].getAdjNodei().setIndex(getCnodes()[p].getIndex());
				}
				if(getCnodes()[p].getVLabel() == getEdges()[k].getAdjNodej().getVLabel()){
					getEdges()[k].getAdjNodej().setIndex(getCnodes()[p].getIndex());
				}
			}
			AM[getEdges()[k].getAdjNodei().getIndex()][getEdges()[k].getAdjNodei().getIndex()] = 1;
			/*DEBUG*/System.out.println("breakpoint: "+ getEdges()[k].getAdjNodei().getIndex());
		}
	}
	
	protected void print(){
		
	}
}
