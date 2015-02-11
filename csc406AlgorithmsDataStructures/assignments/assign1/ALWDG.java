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
import java.util.ArrayList;
import java.util.List;

/**Adjacecy List Weighted Directed Graph*/
public class ALWDG extends G{
	
	private List AL;
	
	/**Default constructor calls the super default constructor and initializes an empty graph*/
	public ALWDG(){
		super();
	}
	
	/**Constructor calls the super constructor and passes a file with graph data*/
	public ALWDG(boolean weighted, File inFile, int numOfLines, Edge[] fedges, Node[] fnodes){
		super(weighted, inFile, numOfLines, fedges, fnodes);
		System.out.println("Inside ALWDG Constructor\n");
	}
	
	protected void constructAD(){
		/*DEBUG*///System.out.println("breakpoint: inside constructAD");
		AL = new ArrayList(); 
	}
	
	protected void print(){
		boolean endfor = true;
		
		for(int r = 0; r < numEdges(); r++){
			System.out.print(" -------     -------\n");
			int n = 0;
			int iLabel = getEdges()[r].getAdjNodei().getVLabel();
			int jLabel = getEdges()[r].getAdjNodej().getVLabel();
			do{
				if((endfor == true)&&(getEdges()[r].getAdjNodei().getVLabel() == iLabel)&&(getEdges()[r].getAdjNodej().getVLabel() == jLabel)){
					System.out.print("| "+iLabel+" | --|-"+getEdges()[r].getWeight()+"-|>"+jLabel+" | ");
					endfor = false;
				}else if((getEdges()[n].getAdjNodei().getVLabel() == iLabel)&&(getEdges()[n].getAdjNodej().getVLabel() == jLabel)){
					System.out.print("| "+iLabel+"/"+getEdges()[n].getWeight()+" | --|-"+getEdges()[r].getWeight()+"-|>"+jLabel+" | ");
				}else{
					
				}
				n++;
				if(n != numEdges()){
					iLabel = getEdges()[n-1].getAdjNodej().getVLabel();
					jLabel = getEdges()[n].getAdjNodei().getVLabel();
				}
			}while(n != numEdges());
			System.out.print("^ |\n");
			System.out.print(" -------     -------\n");
			endfor = true;
		}
	}
}
