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
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

/**Adjacecy List Directed Graph*/
public class ALDG extends G{
	
	//private BufferedReader br;
	private ArrayList<Node>[] AL;
	private Iterator<Node> ite;
	//private int[] test;
	
	private String line;
	
	
	/**Constructor calls the super constructor and passes a file with graph data*/
	public ALDG(BufferedReader br, int type, int numOfNodes, int numOfEdges){
		super(br, type, numOfNodes, numOfEdges);
		System.out.println("Inside ALDG Constructor\n");	
	}
	
	protected void constructAD(){
		try {
			int i = 0;
			AL = (ArrayList<Node>[])new ArrayList[numNodes()];
			for(int e = 0; e < AL.length; e ++){
				AL[e] = new ArrayList<Node>(numNodes());
				AL[e].ensureCapacity(numEdges()-1);
			}
			/*DEBUG*///System.out.println("AL.length: "+ AL.length);
			br.mark(100);
		    while((line = br.readLine()) != null){	
		    	boolean assigned = false;
		    	boolean nodeExists = false;
				String[] lineArray = line.split(" ");
				Node node1 = new Node(Integer.parseInt(lineArray[0]));
				Node node2 = new Node(Integer.parseInt(lineArray[1]));
				/*DEBUG*///System.out.println("node1 = " +node1.getVLabel()+ " - node2 = " +node2.getVLabel()+" - numEdges = "+numEdges()+" - numNodes = "+numNodes()+" - size of ArrayList["+i+"]  = "+AL[i].size() + " - The current readLine() is: "+line);
				for(int k = 0; k < AL.length; k++){
					/*DEBUG*///System.out.println("Entering the for loop k = "+k+" - is empty? : "+!AL[k].isEmpty());
					/*DEBUG*///System.out.println("Entering the for loop - index of node 1: "+AL[k].indexOf(node1));
					/*DEBUG*///System.out.println("The first index of ArrayList "+ k+" is: "+AL[k].get(0));
					if((!(AL[k].isEmpty()))&&(AL[k].get(0).equals(node1))&&(assigned == false)){
						/*DEBUG*///System.out.println("inside not empty");
						AL[k].add(node2);
						/*DEBUG*///System.out.println("AL["+i+"] = "+AL[i].get(0).getVLabel( +" - Number of elements in AL["+i+"] = "+AL[i].size());
						nodeExists = true;
						assigned = true;
					}else if(assigned == false){
						/*DEBUG*///System.out.println("inside empty");
						AL[i].add(0, node1);
						AL[i].add(node2);
						assigned = true;
						/*DEBUG*///System.out.println(k+" - equals?: "+AL[k].get(0).equals(node1));
					}
				}
				/*DEBUG*///System.out.println("made it outside the for loop");
				if(!nodeExists){
					i++;
				}
		    }
		    /*DEBUG*///.out.println("Outside the While Loop");
		    for(int t = 0; t < numNodes(); t++){
		    	AL[t].trimToSize();
		    }
		    
			//br.close();
		} catch (IOException e) {
			System.out.println("Somethingn bad happened");
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*DEBUG*///System.out.println("breakpoint: inside constructAD");*/
	}
	
	@Override
	public String toString(){
		String p = "";
		boolean endfor = true;
		p += "Contents of the Adjcency List Data Structure\n";
		for(int i = 0; i < AL.length; i++){
			ite = ((ArrayList<Node>) AL[i]).iterator(); //note the case for i in iterator
	        // repeat if there are more elements in the collection
			while (ite.hasNext())  {                    
	            p += ite.next().getVLabel()+", ";	//get the next element from the collection
				    	   					//process node.
			}
			p += "\n";
		}
		/*
		for(int s = 0; s < numNodes(); s++){
			p += AL[s]..toString() + "\n";
		}*/
		
		//for(int r = 0; r < numEdges(); r++){
			//p += " -------    -------\n";
			
			//int n = 0;
			//int iLabel = getEdges()[r].getAdjNodei().getVLabel();
			//int jLabel = getEdges()[r].getAdjNodej().getVLabel();
			//do{
			//	if((endfor == true)&&(getEdges()[r].getAdjNodei().getVLabel() == iLabel)&&(getEdges()[r].getAdjNodej().getVLabel() == jLabel)){
				//	p += "| "+iLabel+" | --|--|>"+jLabel+" | ";
				//	endfor = false;
			//	}else if((getEdges()[n].getAdjNodei().getVLabel() == iLabel)&&(getEdges()[n].getAdjNodej().getVLabel() == jLabel)){
			//		p += "| "+iLabel+" | --|--|>"+jLabel+" | ";
			//	}else{
					
			//	}
				//n++;
				//if(n != numEdges()){
					//iLabel = getEdges()[n-1].getAdjNodej().getVLabel();
					//jLabel = getEdges()[n].getAdjNodei().getVLabel();
				//}
			//}while(n != numEdges());
			//p += "^ |\n";
			//p += " -------    -------\n";
			//endfor = true;
		//}
		//System.out.println(" "+p);
		return p;
	}
}
