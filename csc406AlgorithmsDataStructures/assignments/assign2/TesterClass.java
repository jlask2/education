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

import javax.swing.SwingUtilities;

/*
CSC406Assignment#1:  	Due on or before February 11, 2015		rajasethupathy

Project: Implement the weighted/unweighted directed graph data structures using both adjacency matrix and adjacency list representation. 

Partial list of operations on a graph data structure:

numNodes( ) – returns the number of nodes
numEdges( ) : returns the number of edges
existEdge( Edge e): returns true if e is an edge else returns false
existsEdge(int i, int j): returns true if there exists an edge between i and j else returns false
putEdge( Edge: e) : adds the edge e to the graph
putEdge( int i, int j) : adds the edge from i to j to the graph
removeEdge(Edge: e): deletes the edge e from the graph
removeEdge(int i, int j): deletes the edge from i to j from the graph
degree(Node: i): returns the degree of node i. this method is defined for undirected graphs only.
degree(int i): returns the degree of node i. this method is defined for undirected graphs only.
inDegree(Node: i): returns the in-degree of node i. this method is defined for directed graphs only.
inDegree(int: i): returns the in-degree of node i. this method is defined for directed graphs only.
outDegree(Node: i): returns the out-degree of node i. this method is defined for directed graphs only.
outDegree(int: i): returns the out-degree of node i. this method is defined for directed graphs only.
adjacentVertices(Node: i): returns the nodes that are adjacent to i
adjacentVertices(int: i):  returns the nodes that are adjacent to i
areAdjacent(Node i, Node j): returns true if the nodes i and j are adjacent else returns false.
areAdjacent(int i, int j): returns true if the nodes i and j are adjacent else returns false.

General instructions:
1.	Every graph has nodes and edges. So keep two variables, nodes and edges, in the root class.  Keep adjusting these variables whenever you perform operations on the graph that warrant changes to these variables.
2.	Implement fully as many methods as possible in the root class itself.
 numNodes( ) and numEdges( ) are such methods.
3.	You can also keep an array for outDegree in the root class. keep changing it whenever graph operations warrant changes to the outDegree of a node. Likewise for inDegree and degree. 
4.	You may assume that nodes are labeled by natural numbers.  
5.	Use an iterator to step thro elements of a collection. 
6.	Avoid repeating code.
7.	Include your name, course#, semester, assignment#, date assigned and date due.
8.	When using indices, don’t forget to do ‘range check’. A protected method?
9.	File I/O is required.
10.	Validation of input is not a requirement at this stage. However, it will be needed and required for future assignments.
11.	Including a root class and a tester class, you are expected to submit six classes: G (root class), AMWDG, AMDG, ALWDG, ALDG, TesterClass.
12.	Program has to be tested for at least one positive and one negative result.
13.	Submit readable hard copy and flash-drive versions of the source code, input data, test data and a test run.
14.	Seek immediate help if you need help in understanding/implementing your project.
*/

class TesterClass {

	private static BufferedReader br;
	private static BufferedWriter bw;
	private static FileReader fr;
	private static FileWriter fw;
	private static StringTokenizer token;
    private static BufferedWriter buffOut;
    private static BufferedReader buffIn;
    private static FileWriter fileOut;
    private static FileReader fileIn;
    
	public static void main(String[] args) {
	  SwingUtilities.invokeLater(new Runnable()
	  {
		  public void run(){
			  start();
		  }
	  });
	}

	protected static void start() {
		//local variables
	      Scanner sc1;
	      String line;
	      int numOfLines = 0;
	      
	      boolean weighted = false;
          boolean difference = false;
          int edgeNum = 0;
          int nodeNum = 0;
          int[] numOfTokens = {0, 0};
		
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
	                	  
	                	  token = new StringTokenizer(buffIn.readLine());
	                	  /*DEBUG*///System.out.println("breakpoint2" + token.countTokens());
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
		          			ALWDG alwdg = new ALWDG(weighted, inFile, numOfLines, fedges, fnodes);
		          			AMWDG amwdg = new AMWDG(weighted, inFile, numOfLines, fedges, fnodes);
		          		}else if((!difference)&&(!weighted)&&(numOfLines != 0)){
		          			ALDG aldg = new ALDG(weighted, inFile, numOfLines, fedges, fnodes);
		          			AMDG amdg = new AMDG(weighted, inFile, numOfLines, fedges, fnodes);
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
	                  
	                  do {
	                	  
	                	  token = new StringTokenizer(buffIn.readLine());
	                	  /*DEBUG*///System.out.println("breakpoint2.2");
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
		  	            /*DEBUG*///System.out.println("breakpoint2.5.2");
	                  }while((token != null) && (difference != true));//((!(token = new StringTokenizer(buffIn.readLine())).equals(""))&&(token != null)){
	                  
		                  if(difference == true){
		                	  System.out.println("Erroneous input");
		                  }
		                  
	                 }catch(NullPointerException np){
	                	 /*DEBUG*///System.out.println("breakpoint3");
		          		if((!difference)&&(weighted)&&(lineNum != 0)){
		          			ALWDG alwdg = new ALWDG(weighted, inFile, lineNum, fedges, fnodes);
		          			AMWDG amwdg = new AMWDG(weighted, inFile, lineNum, fedges, fnodes);
		          		}else if((!difference)&&(!weighted)&&(lineNum != 0)){
		          			ALDG aldg = new ALDG(weighted, inFile, lineNum, fedges, fnodes);
		          			AMDG amdg = new AMDG(weighted, inFile, lineNum, fedges, fnodes);
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