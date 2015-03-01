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

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**Adjacecy List Weighted Directed Graph*/
public class ALWDG extends G{
	
	private List AL;
	
	/**Constructor calls the super constructor and passes a file with graph data*/
	public ALWDG(BufferedReader br, int type, int numOfNodes, int numOfEdges){
		super(br, type, numOfNodes, numOfEdges);
		System.out.println("Inside ALWDG Constructor\n");
	}
	
	protected void constructAD(){
		/*DEBUG*///System.out.println("breakpoint: inside constructAD");
		AL = new ArrayList(); 
	}
	
	@Override
	public String toString(){
		String p = "";
		boolean endfor = true;
		
		for(int r = 0; r < numEdges(); r++){
			p += " -------     -------\n";
			int n = 0;
			//int iLabel = getEdges()[r].getAdjNodei().getVLabel();
			//int jLabel = getEdges()[r].getAdjNodej().getVLabel();
			do{
				//if((endfor == true)&&(getEdges()[r].getAdjNodei().getVLabel() == iLabel)&&(getEdges()[r].getAdjNodej().getVLabel() == jLabel)){
				//	p += "| "+iLabel+" | --|-"+getEdges()[r].getWeight()+"-|>"+jLabel+" | ";
					endfor = false;
				//}else if((getEdges()[n].getAdjNodei().getVLabel() == iLabel)&&(getEdges()[n].getAdjNodej().getVLabel() == jLabel)){
				//	p += "| "+iLabel+"/"+getEdges()[n].getWeight()+" | --|-"+getEdges()[r].getWeight()+"-|>"+jLabel+" | ";
			//	}else{
					
				//}
				n++;
				if(n != numEdges()){
				//	iLabel = getEdges()[n-1].getAdjNodej().getVLabel();
				//	jLabel = getEdges()[n].getAdjNodei().getVLabel();
				}
			}while(n != numEdges());
			p += "^ |\n";
			p += " -------     -------\n";
			endfor = true;
		}
		
		return p;
	}
}
