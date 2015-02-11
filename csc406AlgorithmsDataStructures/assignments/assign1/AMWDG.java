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

/**Adjacecy Matrix Weighted Directed Graph*/
public class AMWDG extends G{
	
	private int[][] AM;

	/**Default constructor calls the super default constructor and initializes an empty graph*/
	public AMWDG(){
		super();
	}
	
	/**Constructor calls the super constructor and passes a file with graph data*/
	public AMWDG(boolean weighted, File inFile, int numOfLines, Edge[] fedges, Node[] fnodes){
		super(weighted, inFile, numOfLines, fedges, fnodes);
		System.out.println("\nInside AMWDG Constructor\n");
	}
	
	protected void constructAD(){
		/*DEBUG*///System.out.println("breakpoint: inside constructAM");
		AM = new int[numNodes()][numNodes()];
		associateIndexWithVLabel();
		for(int i = 0; i < numNodes(); i++){
			for(int j = 0; j < numNodes(); j++){
				AM[i][j] = 0;
			}
		}
		/*DEBUG*///System.out.println("breakpoint: "+ getEdges()[k].getAdjNodei().getIndex());
		/*DEBUG*///System.out.println("breakpoint: "+ numEdges());
		for(int k = 0;  k < numEdges(); k++){
			for(int p = 0; p < numNodes(); p++){
				/*DEBUG*///System.out.println("breakpoint: cnode: "+ getCnodes()[p].getVLabel()+"cnodeIndex: "+ getCnodes()[p].getIndex()+" adjiLabel: "+getEdges()[k].getAdjNodei().getVLabel()+""
						//+" adjjLabel: "+getEdges()[k].getAdjNodej().getVLabel());
				if(getCnodes()[p].getVLabel() == getEdges()[k].getAdjNodei().getVLabel()){
					getEdges()[k].getAdjNodei().setIndex(getCnodes()[p].getIndex());
					/*DEBUG*///System.out.println("breakpoint: adjiindex: "+ getEdges()[k].getAdjNodei().getIndex());
				}
				if(getCnodes()[p].getVLabel() == getEdges()[k].getAdjNodej().getVLabel()){
					getEdges()[k].getAdjNodej().setIndex(getCnodes()[p].getIndex());
					/*DEBUG*///System.out.println("breakpoint: adjjindex: "+ getEdges()[k].getAdjNodej().getIndex());
				}
				
			}
			int ai = getEdges()[k].getAdjNodei().getIndex();
			int aj = getEdges()[k].getAdjNodej().getIndex();
			AM[ai][aj] = getEdges()[k].getWeight();
			/*DEBUG*///System.out.println("\nbreakpoint: adjiIndex"+ ai+" adjjIndex"+ aj+"\n");
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
