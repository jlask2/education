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
import java.io.IOException;

/**Adjacecy Matrix Directed Graph*/
public class AMDG extends G{
	
	private int[][] AM;
	private String line;
	
	/**Constructor calls the super constructor and passes a file with graph data*/
	public AMDG(BufferedReader br, int type, int numOfNodes, int numOfEdges){
		super(br, type, numOfNodes, numOfEdges);
		System.out.println("\nInside AMDG Constructor\n");
	}
	
	protected void constructAD(){
		/*DEBUG*///System.out.println("breakpoint: inside constructAM");
		AM = new int[numNodes()][numNodes()];
		for(int i = 0; i < numNodes(); i++){
			for(int j = 0; j < numNodes(); j++){
				AM[i][j] = 0;
			}
		}///*DEBUG*/System.out.println("test");
		try {///*DEBUG*/System.out.println("test");
			br.reset();
			while((line = br.readLine()) != null){	
				///*DEBUG*/System.out.println("test");
				String[] lineArray = line.split(" ");
				int nodei = Integer.parseInt(lineArray[0]);
				int nodej = Integer.parseInt(lineArray[1]);
				///*DEBUG*/System.out.println("nodei: "+nodei+"| nodej: "+nodej);
				AM[(nodei-1)][(nodej-1)] = 1;
				
			}
			///*DEBUG*/System.out.println("test3");
			br.close();
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			//br.close();
			///*DEBUG*/System.out.println("test");
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			//br.close();
			///*DEBUG*/System.out.println("test2");
			e.printStackTrace();
		}
	}
		//associateIndexWithVLabel();
		
		/*DEBUG*///System.out.println("breakpoint: "+ getEdges()[k].getAdjNodei().getIndex());
		/*DEBUG*///System.out.println("breakpoint: "+ numEdges());
		//for(int k = 0;  k < numEdges(); k++){
			//for(int p = 0; p < numNodes(); p++){
				/*DEBUG*///System.out.println("breakpoint: cnode: "+ getCnodes()[p].getVLabel()+"cnodeIndex: "+ getCnodes()[p].getIndex()+" adjiLabel: "+getEdges()[k].getAdjNodei().getVLabel()+""
						//+" adjjLabel: "+getEdges()[k].getAdjNodej().getVLabel());
				/*if(getCnodes()[p].getVLabel() == getEdges()[k].getAdjNodei().getVLabel()){
					getEdges()[k].getAdjNodei().setIndex(getCnodes()[p].getIndex());
					/*DEBUG*///System.out.println("breakpoint: adjiindex: "+ getEdges()[k].getAdjNodei().getIndex());
				//}
				//if(getCnodes()[p].getVLabel() == getEdges()[k].getAdjNodej().getVLabel()){
					//getEdges()[k].getAdjNodej().setIndex(getCnodes()[p].getIndex());
					/*DEBUG*///System.out.println("breakpoint: adjjindex: "+ getEdges()[k].getAdjNodej().getIndex());
				//}
				
			//}
			//int ai = getEdges()[k].getAdjNodei().getIndex();
			///int aj = getEdges()[k].getAdjNodej().getIndex();*/
			//AM[ai][aj] = 1;
			/*DEBUG*///System.out.println("\nbreakpoint: adjiIndex"+ ai+" adjjIndex"+ aj+"\n");
		//}
//	}
	
	@Override
	public String toString(){
		String n = "";
		n += "\nIndex     |";
		for(int k = 0; k < numNodes(); k++){
			int c = k;
			c++;
			n += " "+c;
		}
		n += "\n   VLabel |";
		
		for(int p = 0; p < numNodes(); p++){
	//		n += " "+getCnodes()[p].getVLabel();
		}
		n += "\n-----------------------------";
		for(int i = 0; i < numNodes(); i++){
			int c = i;
			c++;
			n += "\n  "+(i+1)+"       |";
			//n += "\n "+c+"   "+getCnodes()[i].getVLabel()+"    |";
			for(int j = 0; j < numNodes(); j++){
				n += " "+ AM[i][j];
			}
			
		}
		return n;
	}
}
