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
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

class FileIO{
	
	FileIO(){
		//local variables
		Scanner sc1;
		String inFileLine;
		String line;
		
        int type = -1;
        int numOfNodes = 0;
        int numOfEdges = 0;
        
        BufferedWriter buffOut = null;
        BufferedReader buffIn = null;
        FileReader fileIn;
        FileWriter fileOut;
       
		//****HANDLE THE CASE OF INVALID SPECIFIED FILE WITH A DEFAULT FILE AND FILE CREATION(createNewFile)****//
	      try{
	         System.out.println("Please specify the name of the file you would like to read from or create. i.e. 'inFileX'\n");
	         sc1 = new Scanner(System.in);
	         String fileInName = sc1.nextLine();
	         
	         File inFile = new File(fileInName+".txt");
	         /**DEBUG*///System.out.println(fileInName);   
	         try{
	            if(!(inFile.exists())){
	               if(inFile.createNewFile()){
	                  System.out.println("Please enter in the contents of the file. Hit enter twice in a row when you are finished: \n");
	                  buffOut = new BufferedWriter(fileOut = new FileWriter(inFile));
	                  sc1 = new Scanner(System.in);
	                  while(!(line = sc1.nextLine()).equals("")){
	                     /**DEBUG*///System.out.println(line);
	                     buffOut.write(line);
	                     buffOut.newLine();
	                     sc1 = new Scanner(System.in);   
	                  }
	                  buffOut.close();
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
	            		  if(type == 0){
	            			   new ALDG(buffIn, numOfNodes, numOfEdges);
		          			   new AMDG(buffIn, numOfNodes, numOfEdges);
	            		  }else if(type == 1){
	            			  new ALWDG(buffIn, numOfNodes, numOfEdges);
		          			  new AMWDG(buffIn, numOfNodes, numOfEdges);
	            		  }else if(type == 2){
	            			  new ALUG(buffIn, numOfNodes, numOfEdges);
		          			  new AMUG(buffIn, numOfNodes, numOfEdges);
	            		  }else if(type == 3){
	            			  new ALWUG(buffIn, numOfNodes, numOfEdges);
		          			  new AMWUG(buffIn, numOfNodes, numOfEdges);
	            		  }else{
	            			  throw new IllegalArgumentException("The type "+type+", is an illegal graph type - Usage: 0-UDAG, 1-WDAG, 2-UUAG, 3-WUAG");
	            		  }
	                 }catch(NullPointerException np){
	                	np.printStackTrace();
	                 }
	               }else{
	                  System.out.println("Failed to create a valid File. \n"+
	                         "Reading from File in current working directory: inFileD.txt");
	                  fileIn = new FileReader("inFileD.txt");         
	               }
	            }else{
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
            		  	  if(type == 0){
	            			   new ALDG(buffIn, numOfNodes, numOfEdges);
		          			   new AMDG(buffIn, numOfNodes, numOfEdges);
	            		  }else if(type == 1){
	            			  new ALWDG(buffIn, numOfNodes, numOfEdges);
		          			  new AMWDG(buffIn, numOfNodes, numOfEdges);
	            		  }else if(type == 2){
	            			  new ALUG(buffIn, numOfNodes, numOfEdges);
		          			  new AMUG(buffIn, numOfNodes, numOfEdges);
	            		  }else if(type == 3){
	            			  new ALWUG(buffIn, numOfNodes, numOfEdges);
		          			  new AMWUG(buffIn, numOfNodes, numOfEdges);
	            		  }else{
	            			  throw new IllegalArgumentException("The type "+type+", is an illegal graph type - Usage: 0-UDAG, 1-WDAG, 2-UUAG, 3-WUAG");
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
}