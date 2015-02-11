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
	
	private int[][] AM;
	
	/**Default constructor calls the super default constructor and initializes an empty graph*/
	public AMDG(){
		super();
	}
	
	/**Constructor calls the super constructor and passes a file with graph data*/
	public AMDG(boolean weighted, File inFile, int numOfLines, Edge[] fedges, Node[] fnodes){
		super(weighted, inFile, numOfLines, fedges, fnodes);
		System.out.println("\n\nInside AMDG Constructor");
		constructAM();
		print();
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
				/*DEBUG*/System.out.println("breakpoint: cnode: "+ getCnodes()[p].getVLabel()+" adj: "+getEdges()[k].getAdjNodei().getVLabel());
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
		System.out.print("\nIndex     |");
		for(int k = 0; k < numNodes(); k++){
			int c = k;
			c++;
			System.out.print(" "+c);
		}
		System.out.print("\n   VLabel |");
		
		for(int p = 0; p < numNodes(); p++){
			System.out.print(" "+getCnodes()[p].getVLabel());
		}
		System.out.print("\n-----------------------------");
		for(int i = 0; i < numNodes(); i++){
			int c = i;
			c++;
			System.out.print("\n "+c+"   "+getCnodes()[i].getVLabel()+"    |");
			for(int j = 0; j < numNodes(); j++){
				System.out.print(" "+ AM[i][j]);
			} 
		}
	}
}
