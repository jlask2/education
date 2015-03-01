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
import java.util.StringTokenizer;

class FileIO{
	
	FileIO(){
		//local variables
		Scanner sc1;
		String inFileLine;
		String line;
	    int numOfLines = 0;
	      
	    boolean weighted = false;
        boolean difference = false;
        boolean firstLine = true;
        int type = -1;
        int numOfNodes = 0;
        int numOfEdges = 0;
        int edgeNum = 0;
        int nodeNum = 0;
        int[] numOfTokens = {0, 0};
		
        BufferedReader br;
        BufferedWriter bw;
        FileReader fr;
        FileWriter fw;
        StringTokenizer token;
        BufferedWriter buffOut;
        BufferedReader buffIn = null;
        FileWriter fileOut;
        FileReader fileIn;
        
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
	                	 numOfLines++;
	                     buffOut.write(line);
	                     buffOut.newLine();
	                     sc1 = new Scanner(System.in);   
	                  }
	                  
	                  buffOut.close();
	                  Edge[] fedges = new Edge[numOfLines];
	                  Node[] fnodes = new Node[numOfLines*2];
	                  
	              try{ 
	     
	                  buffIn = new BufferedReader(fileIn = new FileReader(inFile));
	           
	                  /*DEBUG*///System.out.println("breakpoint1: ");//+ buffIn.lines().count());
	                  
	                  do {
	                	  inFileLine = buffIn.readLine();
	                	  String[] inFileLineArray = new String[5];
	                	  inFileLineArray = inFileLine.split(" ");
	                	  
	                	  for(int numOfCharacters = 0; numOfCharacters < inFileLineArray.length; numOfCharacters++){
	                		  System.out.println(inFileLineArray[numOfCharacters]+" :"+ numOfCharacters);
	                	  }
	                	  token = new StringTokenizer(buffIn.readLine());
	                	  /*DEBUG*/System.out.println("breakpoint2" + token.countTokens());
		  	              if(token != null){
		                	  if(token.countTokens() == 2){
			  	            	  numOfTokens[0] = token.countTokens();
			  	              }else if(token.countTokens() == 3){
			  	            	  numOfTokens[1] = token.countTokens();
			  	              }else{
			  	            	  numOfTokens[0] = 0;
			  	            	  numOfTokens[1] = 0;
			  	            	  difference = true;
			  	              }
		                	  
		                	  
		                	  
			  	              if((difference != true) && (numOfTokens[0] == 2) && (numOfTokens[1] == 0)){
			  	            	  Node nodei = new Node(Integer.parseInt(token.nextToken()));
			  	            	  Node nodej = new Node(Integer.parseInt(token.nextToken()));
			  	            	  Edge edge = new Edge(nodei, nodej);
			  	            	/*DEBUG*///System.out.println("breakpoint2.3");
			  	            	  fnodes[nodeNum] = nodei;
			  	            	  nodeNum++;
			  	            	  fnodes[nodeNum] = nodej;
			  	            	  nodeNum++;
			  	            	  fedges[edgeNum] = edge;
			  	            	  edgeNum++;
			  	            	  weighted = false;
			  	              }else if((difference != true) && (numOfTokens[1] == 3) && (numOfTokens[0] == 0)){
			  	            	  Node nodei = new Node(Integer.parseInt(token.nextToken()));
			  	            	  Node nodej = new Node(Integer.parseInt(token.nextToken()));
			  	            	  Edge edge = new Edge(nodei, nodej, Integer.parseInt(token.nextToken()));
			  	            	  fnodes[nodeNum] = nodei;
			  	            	  nodeNum++;
			  	            	  fnodes[nodeNum] = nodej;
			  	            	  nodeNum++;
			  	            	  fedges[edgeNum] = edge;
			  	            	  edgeNum++;
			  	            	  weighted = true;
			  	              }else if(((numOfTokens[0] == 2) && (numOfTokens[1] == 3))||((numOfTokens[0] == 0) && (numOfTokens[1] == 0))){
		              			  difference = true;
	              		      }
		  	              }
		  	            /*DEBUG*///System.out.println("breakpoint2.5");
	                  }while((token != null) && (difference != true));//((!(token = new StringTokenizer(buffIn.readLine())).equals(""))&&(token != null)){
	                  
		                  if(difference == true){
		                	  System.out.println("Erroneous input");
		                  }
	                 
	                 }catch(NullPointerException np){
	                	 /*DEBUG*///System.out.println("breakpoint3");
		          		if((!difference)&&(weighted)&&(numOfLines != 0)){
		          			//ALWDG alwdg = new ALWDG(br, type, numOfNodes, numOfEdges);
		          			//AMWDG amwdg = new AMWDG(br, type, numOfNodes, numOfEdges);
		          		}else if((!difference)&&(!weighted)&&(numOfLines != 0)){
		          			//ALDG aldg = new ALDG(br, type, numOfNodes, numOfEdges);
		          			//AMDG amdg = new AMDG(br, type, numOfNodes, numOfEdges);
		          		}else{
		          			System.out.println("Erroneous input");
		          		}
		          		buffIn.close();
	                 }
	               }else{
	            	   /*DEBUG*///System.out.println("breakpoint4");
	                  System.out.println("Failed to create a valid File. \n"+
	                         "Reading from File in current working directory: inFileD.txt");
	                  fileIn = new FileReader("inFileD.txt");         
	               }
	            }else{
	            	/*DEBUG*///System.out.println("breakpoint5");
	            	buffIn = new BufferedReader(fileIn = new FileReader(inFile));
	            	buffIn.mark(100);
	            	int lineNum = Integer.valueOf(Long.toString(buffIn.lines().count()));
	                  Edge[] fedges = new Edge[lineNum];
	                  Node[] fnodes = new Node[lineNum*2];
	                  buffIn.reset();
	              try{ 
	            	 
	            	  /*DEBUG*///System.out.println("breakpoint1.2: ");//+ buffIn.lines().count());
	                  
	                  //do {
	                	  inFileLine = buffIn.readLine();
	                	  String[] inFileLineArray = inFileLine.split(" ");
	                	 // if(firstLine){ 
	                		  try{
	                			  if(inFileLineArray.length != 3){
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
	                		 // buffIn.close();
	                		  if(type == 0){
	                			   new ALDG(buffIn, type, numOfNodes, numOfEdges);
	  		          			   new AMDG(buffIn, type, numOfNodes, numOfEdges);
	                		  }else{
	                			  new ALWDG(buffIn, type, numOfNodes, numOfEdges);
	  		          			  new AMWDG(buffIn, type, numOfNodes, numOfEdges);
	                		  }
	                	  
	                	  
	                	  
	                	  /*for(int numOfCharacters = 0; numOfCharacters < inFileLineArray.length; numOfCharacters++){
	                		  System.out.println(inFileLineArray[numOfCharacters]+" :"+ numOfCharacters);
	                	  }*/
	                	 // buffIn.reset();
	                	 // token = new StringTokenizer(buffIn.readLine());
	                	  /*DEBUG*///System.out.println("breakpoint2.2");
		  	              /*if(token != null){
		                	  if(token.countTokens() == 2){
			  	            	  numOfTokens[0] = token.countTokens();
			  	              }else if(token.countTokens() == 3){
			  	            	  numOfTokens[1] = token.countTokens();
			  	              }else{
			  	            	  numOfTokens[0] = 0;
			  	            	  numOfTokens[1] = 0;
			  	            	  difference = true;
			  	              }
		                	  
			  	              if((difference != true) && (numOfTokens[0] == 2) && (numOfTokens[1] == 0)){
			  	            	  Node nodei = new Node(Integer.parseInt(token.nextToken()));
			  	            	  Node nodej = new Node(Integer.parseInt(token.nextToken()));
			  	            	  Edge edge = new Edge(nodei, nodej);
			  	            	  fnodes[nodeNum] = nodei;
			  	            	  nodeNum++;
			  	            	  fnodes[nodeNum] = nodej;
			  	            	  nodeNum++;
			  	            	  fedges[edgeNum] = edge;
			  	            	  edgeNum++;
			  	            	  weighted = false;
			  	              }else if((difference != true) && (numOfTokens[1] == 3) && (numOfTokens[0] == 0)){
			  	            	  Node nodei = new Node(Integer.parseInt(token.nextToken()));
			  	            	  Node nodej = new Node(Integer.parseInt(token.nextToken()));
			  	            	  Edge edge = new Edge(nodei, nodej, Integer.parseInt(token.nextToken()));
			  	            	  fnodes[nodeNum] = nodei;
			  	            	  nodeNum++;
			  	            	  fnodes[nodeNum] = nodej;
			  	            	  nodeNum++;
			  	            	  fedges[edgeNum] = edge;
			  	            	  edgeNum++;
			  	            	  weighted = true;
			  	              }else if(((numOfTokens[0] == 2) && (numOfTokens[1] == 3))||((numOfTokens[0] == 0) && (numOfTokens[1] == 0))){
		              			  difference = true;
	              		      }
		  	              }*/
		  	            /*DEBUG*///System.out.println("breakpoint2.5.2");
	                  //}while(buffIn.ready());//((token != null) && (difference != true));//((!(token = new StringTokenizer(buffIn.readLine())).equals(""))&&(token != null)){
	                  
		                  if(difference == true){
		                	  System.out.println("Erroneous input");
		                  }
		                  
	                 }catch(NullPointerException np){
	                	 /*DEBUG*///System.out.println("breakpoint3");
		          		if((!difference)&&(weighted)&&(lineNum != 0)){
		          			//ALWDG alwdg = new ALWDG(weighted, inFile, lineNum, fedges, fnodes);
		          			//AMWDG amwdg = new AMWDG(weighted, inFile, lineNum, fedges, fnodes);
		          		}else if((!difference)&&(!weighted)&&(lineNum != 0)){
		          			//ALDG aldg = new ALDG(weighted, inFile, lineNum, fedges, fnodes);
		          			//AMDG amdg = new AMDG(weighted, inFile, lineNum, fedges, fnodes);
		          		}else{
		          			System.out.println("Erroneous input");
		          		}
		          		buffIn.close();
	                 }
	            }
	         }catch(FileNotFoundException f){
	        	 /*DEBUG*///System.out.println("breakpoint6");
	            System.out.println("Failed to create a valid File. "+
	                               "Reading from File in current working directory: inFileD.txt");
	                  fileIn = new FileReader("inFileD.txt");
	         }
	      }catch(IOException io){
	         io.printStackTrace(); 
	      }
	}
}