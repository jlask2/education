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
import java.util.Scanner;

/**FileIO Class: Handles the file IO*/
class FileIO{
	
	/**private data members*/
	private Scanner sc1;
	private String inFileLine;
	private String line;
	private int type = -1;
	private int numOfNodes = 0;
	private int numOfEdges = 0;
	private BufferedWriter buffOut = null;
	private BufferedReader buffIn = null;
	private FileReader fileIn;
	private FileWriter fileOut;
	
	/**FileIO Constructor: Creates a new file if it does not exists otherwise it reads fromthe given filename*/
	public FileIO(){
		//****HANDLE THE CASE OF INVALID SPECIFIED FILE WITH A DEFAULT FILE AND FILE CREATION(createNewFile)****//
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
	                  sc1 = new Scanner(System.in);
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
	            			  }else{ 
	            				  type = Integer.parseInt(inFileLineArray[0]); 
			                	  numOfNodes = Integer.parseInt(inFileLineArray[1]); 
		                		  numOfEdges = Integer.parseInt(inFileLineArray[2]); 
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
	
	/**Accessor Methods*/
	protected BufferedReader getBuffIn(){
		return buffIn;
	}
	
	protected int getNumNodes(){
		return numOfEdges;
	}
	
	protected int getNumEdges(){
		return numOfNodes;
	}
	
	protected int getType(){
		return type;
	}
	
	
}