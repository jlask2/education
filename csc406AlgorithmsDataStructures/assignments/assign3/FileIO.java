/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 3
 * Date Assigned: 3/4/2015
 * Date Due: 3/25/2015
 * Date Submitted: 3/25/2015 
 ***********************************/

package graph;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**FileIO Class: Handles the file IO*/
class FileIO{
	
	/**private data members*/
	private Scanner sc1;
	private Scanner inputChoice; 
	private String inFileLine;
	private String line;
	private int choice;
	private int type = -1;
	private int numOfNodes = 0;
	private int numOfEdges = 0;
	private BufferedWriter buffOut = null;
	private BufferedReader buffIn = null;
	private FileReader fileIn;
	private FileWriter fileOut;
	
	private ALDG aldg;
	private ALWDG alwdg;
	private AMDG amdg;
	private AMWDG amwdg;
	private ALUG alug;
	private ALWUG alwug;
	private AMUG amug;
	private AMWUG amwug;
	private FileIO fileIO;
	private BufferedReader br;
	
	/**FileIO Constructor: Creates a new file if it does not exists otherwise it reads fromthe given filename*/
	public FileIO(){
		 try{
			 System.out.println("Welcome to my Data Structures and Algorithms Library! "
						+ "\n0) Enter infileD or a 0 as the type (The number of Nodes and Edges still need to be input) to exit the program" 
						+ "\n1) Unweigthed-DAG implementation"
						+ "\n2) Weigthed-DAG implementation"
						+ "\n3) Unweigthed-UAG implementation"
						+ "\n4) Weigthed-UAG implemetation"
						+ "\n5) Warshall's implementation"
						+ "\n6) Floyd's implemetation"
						+ "\n7) Knapsack 0-1"
						+ "\n8) MCM"
						+ "\nPlease select a choice: ");
			 inputChoice = new Scanner(System.in); 
			 choice = inputChoice.nextInt();
			 System.out.println("Outside while loop: "+choice);
			 while(choice != 0){
			 	mainMenu(choice);
			 	//System.out.print("Welcome to my Data Structures and Algorithms Library!\nPlease select a choice: ");
			 	//inputChoice.close();
			 	inputChoice = new Scanner(System.in);
				choice = inputChoice.nextInt();
				//System.out.println("Inside while loop: "+choice);
				//handleIO();
			 }
		 }catch(InputMismatchException ime){
			 System.out.println("Input mismatch! Must be of type integer between 0 - 8");
			 ime.printStackTrace();
		 }
		 if(choice == 0){
			 System.out.println("Didn't want to check it out huh. Too bad!");
				System.exit(0);
		 }else{
			 System.out.println("Something went wrong, exiting the program!");
				System.exit(0);
		 }
	}
	
	protected void handleIO(int choice){
 			if(choice == 0){
 				System.exit(0);
 			}
			try{
				System.out.println("Please specify the name of the file you would like to read from or create. i.e. 'inFileX'\n");
				sc1 = new Scanner(System.in);
				String fileInName = sc1.nextLine();
				
				File inFile = new File(fileInName+".txt");
				/**DEBUG*///System.out.println(fileInName);   
				try{
				   if(!(inFile.exists())){            //if the file doesn't exist 
				      if(inFile.createNewFile()){      //create a new one with the given name
					         System.out.println("Please enter in the contents of the file. Hit enter twice in a row when you are finished: \n");
					         buffOut = new BufferedWriter(fileOut = new FileWriter(inFile));
					         buffOut.write(""+choice);
					         switch(choice){			 		
						 		case 1:
						 		case 2:
						 		case 3:
						 		case 4:
						 		case 5:
						 		case 6:
							 		 System.out.println("Please enter in the number of nodes. \n");
							         sc1 = new Scanner(System.in);
							         buffOut.write(" "+sc1.nextInt());
							         System.out.println("Please enter in the number of edges. \n");
							         sc1= new Scanner(System.in); 
							         buffOut.write(" "+sc1.nextInt());
							         buffOut.newLine();
							         System.out.println("Please enter in the edges. Hit enter twice in a row when you are finished: \n");
							         while(!(line = sc1.nextLine()).equals("")){   //write the contents of the file
							            /**DEBUG*///System.out.println(line);
							            buffOut.write(line);
							            buffOut.newLine();
							            sc1 = new Scanner(System.in);   
							         }
							         buffOut.close();
							         try{
							        	 buffIn = new BufferedReader(fileIn = new FileReader(inFile)); //attempt to read from the newly created file
							        	 inFileLine = buffIn.readLine();
							        	 String[] inFileLineArray = inFileLine.split(" ");
							        	 try{
							        		 if(inFileLineArray.length != 3){  //if the number of tokens is not 3 throw an exception
							        			 buffIn.close();
							        			 throw new IllegalArgumentException("Incorrect amount of inputs given");
							        		 }else{  //parse the first line
							        			 type = Integer.parseInt(inFileLineArray[0]); 
								                 numOfNodes = Integer.parseInt(inFileLineArray[1]); 
								                 numOfEdges = Integer.parseInt(inFileLineArray[2]);
								                 try {
								          			buffIn.mark(100);
									          	 } catch (IOException e) {
									          		  e.printStackTrace();
									             }
							        		 }
							             }catch(NullPointerException np){
							            	 System.out.println("Not enough arguments given");
							            	 np.printStackTrace();
							   		 }catch(IllegalArgumentException ie){
							   			 System.out.println("Invalid arguments given");
							   			 ie.printStackTrace();
							   		 }
							         }catch(NullPointerException np){
								        	np.printStackTrace();
								     }
							         break;
					        	 case 7:
					        	 case 8:
					        		 break;
					        	 default:
					        		 System.out.println("Something went wrong, exiting the program!");
					 				 System.exit(0);
					        		 break;
							  }
		       
				      }else{    //new file creation did not succeed, reading from the default file which should exit the program 
				         System.out.println("Failed to create a valid File. \n"+
				                "Reading from File in current working directory: inFileD.txt");
				         fileIn = new FileReader("inFileD.txt");         
				      }
				   }else{    //else just read the file if it exists already, pretty much the same code as the read part above
				   	  try{ 
				   		 buffIn = new BufferedReader(fileIn = new FileReader(inFile));
				        	  inFileLine = buffIn.readLine();
				        	  String[] inFileLineArray = inFileLine.split(" ");
					  	 try{
				   			  if(inFileLineArray.length != 3){
				   				 buffIn.close(); 
				                     throw new IllegalArgumentException("Incorrect amount of inputs given");
				   			  }else if(Integer.parseInt(inFileLineArray[0]) != choice){
				   				 buffIn.close(); 
				   				 	 throw new InputMismatchException("The choice given does not match the input type of the file");
					  	  	  }else{ 
				   				  type = Integer.parseInt(inFileLineArray[0]); 
				                  numOfNodes = Integer.parseInt(inFileLineArray[1]); 
				                  numOfEdges = Integer.parseInt(inFileLineArray[2]); 
			            		  try {
			            				buffIn.mark(100);
			            		  } catch (IOException e) {
			            				e.printStackTrace();
			            	      }
				   			  }
				   		 }catch(NullPointerException np){
				   			  System.out.println("Not enough arguments given");
				   			  np.printStackTrace();
				   		 }catch(IllegalArgumentException ie){
				   			  System.out.println("Invalid arguments given");
				   			  ie.printStackTrace();
				   		 }
				        }catch(NullPointerException np){
				        	np.printStackTrace();
				        }
				   }
				}catch(FileNotFoundException f){
				   System.out.println("Failed to create a valid File. "+
				                      "Reading from File in current working directory: inFileD.txt");
			       fileIn = new FileReader("inFileD.txt");
			    }
			 }catch(IOException io){
			    io.printStackTrace(); 
			 }
	}
	
	protected void mainMenu(int choice){ 
		/*try {
			buffIn.mark(100);
		} catch (IOException e) {
			e.printStackTrace();
		}*/
		handleIO(choice);
		switch(choice){
		case 0:
			System.out.println("All Finished. Thanks for using this program. Exiting the program.");
			System.exit(0);
			break;
		case 1:
			aldg = new ALDG(buffIn, numOfNodes, numOfEdges);
			amdg = new AMDG(buffIn, numOfNodes, numOfEdges);
			aldg.topoSort(aldg.listNodes, numOfNodes);
			try {
				buffIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 2:
			alwdg = new ALWDG(buffIn, numOfNodes, numOfEdges); 
			amwdg = new AMWDG(buffIn, numOfNodes, numOfEdges);
			alwdg.topoSort(alwdg.listNodes, numOfNodes);
			try {
				buffIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 3:
			alug = new ALUG(buffIn, numOfNodes, numOfEdges); 
			amug = new AMUG(buffIn, numOfNodes, numOfEdges);
			try {
				buffIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 4:
			alwug = new ALWUG(buffIn, numOfNodes, numOfEdges); 
			amwug = new AMWUG(buffIn, numOfNodes, numOfEdges);
			alwug.findMST(alwug.pqe, numOfNodes);
			try {
				buffIn.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 5:
			amdg = new AMDG(buffIn, numOfNodes, numOfEdges);
			TransitiveClosure tc = new TransitiveClosure(amdg.getAM());
			tc.generateR();
			System.out.println("The Final Resulting Matrix "+tc.toString());
			break;
		case 6:
			amwdg = new AMWDG(buffIn, numOfNodes, numOfEdges);
			AllSourceSP sp = new AllSourceSP(amwdg.getAM());
			sp.generateD();
			System.out.println("The Final Resulting Matrix "+sp.toString());
			System.out.println(sp.getPath(3, 1));
			break;
		case 7:
			int n = 4;
			int[] w = {2, 1, 3, 2};
			int[] v = {12, 10, 20, 15};
			int W = 5;
			KnapSack kp = new KnapSack(n, w, v, W);
			System.out.println("The resulting optimal solution is: "+kp.getKnapSack()+"\n");
			System.out.println(kp.toString());
			break;
		case 8:
			//int[] dimArray = {10, 20, 50, 1, 100};
			int[] dimArray = {4, 2, 3, 1, 2, 2, 3};
			//d0 = 4, d1 = 2, d2 = 3,  d3 = 1, d4 = 2, d5 = 2, d6 = 3
			MCM mcm1 = new MCM(dimArray);
			mcm1.calculateMatrix();
			System.out.println(mcm1.toString());
			System.out.println(mcm1.printParenthesizations());
			break;
		default:
			System.out.println("Something went wrong, exiting the program!");
			System.exit(0);
			break;
		}
		System.out.println(""
				+ "\n0) Enter infileD or a 0 as the type (The number of Nodes and Edges still need to be input) to exit the program" 
				+ "\n1) Unweigthed-DAG implementation"
				+ "\n2) Weigthed-DAG implementation"
				+ "\n3) Unweigthed-UAG implementation"
				+ "\n4) Weigthed-UAG implemetation"
				+ "\n5) Warshall's implementation"
				+ "\n6) Floyd's implemetation"
				+ "\n7) Knapsack 0-1"
				+ "\n8) MCM"
				+ "\nPlease select a choice: ");
		//inputChoice = new Scanner(System.in); 
		//choice = inputChoice.nextInt();
	}
	
	/**Accessor Methods*/
	protected BufferedReader getBuffIn(){
		return buffIn;
	}
	
	protected int getNumNodes(){
		return numOfNodes;
	}
	
	protected int getNumEdges(){
		return numOfEdges;
	}
	
	protected int getType(){
		return type;
	}
	
	
}