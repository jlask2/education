/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 4
 * Date Assigned: 3/26/2015
 * Date Due: 4/15/2015
 * Date Submitted: 4/16/2015
 ***********************************/

package algoData;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

/** FileIO Class: Handles the file IO */
class FileIO {

	/** private data members */
	private Scanner sc1;
	private Scanner inputChoice;
	private String inFileLine;
	private String line;
	private int choice;
	private int numOfNodes = 0;
	private int numOfEdges = 0;
	private BufferedWriter buffOut = null;
	private BufferedReader buffIn = null;

	private ALDG aldg;
	private ALWDG alwdg;
	private AMDG amdg;
	private AMWDG amwdg;
	private ALUG alug;
	private ALWUG alwug;
	private AMUG amug;
	private AMWUG amwug;

	/**
	 * FileIO Constructor: Creates a new file if it does not exists otherwise it
	 * reads from the given filename and loops trough the main menu until either an
	 * exception occurs or the user exits
	 */
	public FileIO() {
		try {
			do {
				System.out
						.println("Welcome to my Data Structures and Algorithms Library! "
								+ "\n\t0) Enter 0 to exit the program"
								+ "\n\t1) Unweigthed-DAG implementation"
								+ "\n\t2) Weigthed-DAG implementation"
								+ "\n\t3) Unweigthed-UAG implementation"
								+ "\n\t4) Weigthed-UAG implemetation"
								+ "\n\t5) Warshall's implementation"
								+ "\n\t6) Floyd's implemetation with path reconstruction"
								+ "\n\t7) Knapsack 0-1 with optimal sets"
								+ "\n\t8) MCM with Optimized Parenthesization"
								+ "\n\t9) Splay Tree"
								+ "\n\t10) AVL Tree - coming soon"
								+ "\n\t11) Black and Red Tree - coming soon"
								+ "\n\t12) B-Tree - coming soon"
								+ "\n\t13) B+ Tree - coming soon"
								+ "\nPlease select a choice: ");
				inputChoice = new Scanner(System.in);
				choice = inputChoice.nextInt();
				mainMenu(choice);
			} while (choice != 0);
			if (choice == 0) {
				System.out.println("All Finished. Thanks for using this program. " +
																	"Exiting the program.");
				System.exit(0);
			} else {
				System.out.println("Something went wrong, exiting the program!: Main Loop");
				System.exit(0);
			}
		} catch (final InputMismatchException ime) {
			System.out.println("Input mismatch! Must be of type integer and the choice " +
													"must agree with the given file read.");
			ime.printStackTrace();
		}
	}

	/**
	 * handleIO method: Handles the creation and writing of new files and/or
	 * makes the call to the readFile() method
	 */
	protected void handleIO(int choice) {
		if (choice == 0) { 					// exit if the choice is 0
			System.out.println("All Finished. Thanks for using this program. Exiting " +
																"the program.: handleIO");
			System.exit(0);
		}
		try {
			System.out.println("Please specify the name of the file you would like to " +
												"read from or create. i.e. 'inFileX'\n");
			sc1 = new Scanner(System.in);
			final String fileInName = sc1.nextLine();
			final File inFile = new File(fileInName + ".txt");
			try {
				if (!(inFile.exists())) { 				// if the file doesn't exist
					if (inFile.createNewFile()) { 		// create a new one with the
														// given name
						buffOut = new BufferedWriter(new FileWriter(inFile));
						buffOut.write(choice + "");
						switch (choice) { 				// handle the creation of the file
														// depending on the user choice
														// given
						
						case 1: 						// Unweigthed-DAG implementation
						case 2: 						// Weigthed-DAG implementation
						case 3: 						// Unweigthed-UAG implementation
						case 4: 						// Weighted-UAG implementation
						case 5: 						// Warshalls
						case 6: 						// Floyds
							System.out
									.println("Please enter in the number of nodes. \n");
							sc1 = new Scanner(System.in);
							buffOut.write(" " + sc1.nextInt() + " ");
							System.out.println("Please enter in the number of edges. \n");
							sc1 = new Scanner(System.in);
							buffOut.write(sc1.nextInt()+"");
							buffOut.newLine();
							if (choice == 1 || choice == 3 || choice == 5) {
								System.out.println("Please enter in the edges. Hit enter " +
												"twice in a row when you are finished: \n");
							} else {
								System.out.println("Please enter in the edges with weights. " +
											"Hit enter twice in a row when you are finished: \n");
							}
							sc1 = new Scanner(System.in);
							while (!(line = sc1.nextLine()).equals("")) { 

								buffOut.write(line);
								buffOut.newLine();
								sc1 = new Scanner(System.in);
							}
							buffOut.close();
							break;
						case 7: 											// Knapsack
							buffOut.newLine();
							System.out.println("Please enter in the number of items n. ");
							sc1 = new Scanner(System.in);
							final int numOfItems = sc1.nextInt();
							buffOut.write(numOfItems + "");
							buffOut.newLine();
							System.out.println("Please enter in the max weight W. ");
							sc1 = new Scanner(System.in);
							buffOut.write(sc1.nextInt() + "");
							buffOut.newLine();
							System.out.println("Please enter in the set of weights w. ");
							sc1 = new Scanner(System.in);
							buffOut.write(sc1.nextLine());
							buffOut.newLine();
							System.out.println("Please enter in the set of values v. ");
							sc1 = new Scanner(System.in);
							buffOut.write(sc1.nextLine() + " ");
							buffOut.close();
							break;
						case 8: 											// MCM
							buffOut.newLine();
							System.out.println("Please enter in the set of dimensions d. ");
							sc1 = new Scanner(System.in);
							buffOut.write(sc1.nextLine() + " ");
							buffOut.close();
							break;
						case 9:												// Splay Tree
							buffOut.newLine();
							System.out.println("Enter in the initial nodes for this " +
																			"Splay Tree. ");
							sc1 = new Scanner(System.in);
							buffOut.write(sc1.nextLine() + " ");
							buffOut.close();
							break;
						case 10:										// AVL Tree
							break;
						case 11:										// Black and Red Tree
							break;
						case 12:										// B - Tree
							break;
						case 13:										// B+ - Tree
							break;
						default:
							System.out.println("Something went wrong, exiting the program!" +
																				": handleIO");
							System.exit(0);
							break;
						}
						readFile(inFile);
					} else { 			// new file creation did not succeed, reading from
										// the default file which should exit the program
						System.out.println("Failed to create a valid File. \n"
								+ "Reading from File in current working directory: inFileD.txt");
						new FileReader("inFileD.txt");
					}
				} else { 				// else just read the file if it exists already
					readFile(inFile);
				}
			} catch (final FileNotFoundException f) {
				System.out.println("Failed to create a valid File. "
								+ "Reading from File in current working directory: inFileD.txt");
				new FileReader("inFileD.txt");
			}
		} catch (final IOException io) {
			io.printStackTrace();
		}
	}

	/**
	 * readFile method: reads from file passed as a parameter and handles file
	 * marking and processing of the first line
	 */
	private void readFile(File inFile) {
		try {
			buffIn = new BufferedReader(new FileReader(inFile)); 
												// attempt to read from the file
			inFileLine = buffIn.readLine();
			final String[] inFileLineArray = inFileLine.split(" ");
			if(Integer.parseInt(inFileLineArray[0]) != choice){
				throw new InputMismatchException("The choice must agree with " +
														"the given file read");
			}
			try{
			switch (choice) {
			case 1: 	// same implementations corresponding to the comments in
																	// handleIO()
			case 2:
			case 3:
			case 4:
			case 5:
			case 6:
					if (inFileLineArray.length != 3) { 	// if the number of
														// tokens is not 3 throw
														// an exception
						buffIn.close();
						throw new IllegalArgumentException(
								"Incorrect amount of inputs given");
					} else { 	// parse the first line and mark the second line of
																		// the file
						numOfNodes = Integer.parseInt(inFileLineArray[1]);
						numOfEdges = Integer.parseInt(inFileLineArray[2]);
							buffIn.mark(100);
					}
				break;
			case 7:
			case 8:
			case 9:												// Splay Tree
				if (inFileLineArray.length != 1) { 	// if the number of
													// tokens is not 1 throw
													// an exception
					buffIn.close();
					throw new IllegalArgumentException(
							"Incorrect amount of inputs given");
				} else { 					// mark the second line in the file
						buffIn.mark(100);
				}
				break;
			case 10:											// AVL Tree
				break;
			case 11:											// Black and Red Tree
				break;
			case 12:											// B - Tree
				break;
			case 13:											// B+ - Tree
				break;
			default:
				System.out.println("Something went wrong, exiting the program!" +
																	": readFile()");
				System.exit(0);
				break;
			}
			} catch (final NullPointerException np) {
				System.out.println("Not enough arguments given");
				np.printStackTrace();
			} catch (final IllegalArgumentException ie) {
				System.out.println("Invalid arguments given");
				ie.printStackTrace();
			}
		} catch (NullPointerException | IOException np) {
			np.printStackTrace();
		}
	}

	/**
	 * mainMenu method: navigates the user to the correct implementation after
	 * creating or reading the file
	 */
	protected void mainMenu(int choice) {
		if(choice < 10 && choice >= 0){ // applying restraints to the user selection
			handleIO(choice);
		}else{
			choice = -1;
			System.out.println("\nThis menu choice is not available yet.\n" +
					"Please select another valid choice");
		}
		switch (choice) { 	//  same implementations corresponding to the
							// comments in handleIO()
		case -1 :									//Loops the menu in case of bad input
			System.out.println("Looping the Main Menu\n"); 
		    break;
		case 0:													//Exit the program
			System.out.println("All Finished. Thanks for using this program. " +
																"Exiting the program.");
			System.exit(0);
			break;
		case 1:													//DAG - Un-weighted
			aldg = new ALDG(buffIn, numOfNodes, numOfEdges);
			amdg = new AMDG(buffIn, numOfNodes, numOfEdges);
			aldg.topoSort(aldg.listNodes, numOfNodes);
			try {
				buffIn.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			break;
		case 2:													//DAG - Weighted
			alwdg = new ALWDG(buffIn, numOfNodes, numOfEdges);
			amwdg = new AMWDG(buffIn, numOfNodes, numOfEdges);
			alwdg.topoSort(alwdg.listNodes, numOfNodes);
			try {
				buffIn.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			break;
		case 3:													//UAG - Un-weighted
			alug = new ALUG(buffIn, numOfNodes, numOfEdges);
			amug = new AMUG(buffIn, numOfNodes, numOfEdges);
			try {
				buffIn.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			break;
		case 4:													//UAG - Weighted
			alwug = new ALWUG(buffIn, numOfNodes, numOfEdges);
			amwug = new AMWUG(buffIn, numOfNodes, numOfEdges);
			alwug.findMST(alwug.pqe, numOfNodes);
			try {
				buffIn.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			break;
		case 5:													//Warshalls
			amdg = new AMDG(buffIn, numOfNodes, numOfEdges);
			final TransitiveClosure tc = new TransitiveClosure(amdg.getAM());
			tc.generateR();
			System.out.println("The Final Resulting Matrix " + tc.toString());
			try {
				buffIn.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			break;
		case 6:													//Floyds
			amwdg = new AMWDG(buffIn, numOfNodes, numOfEdges);
			final AllSourceSP sp = new AllSourceSP(amwdg.getAM());
			sp.generateD();
			System.out.println("The Final Resulting Matrix " + sp.toString());
			System.out.println(sp.getPath(3, 1));
			try {
				buffIn.close();
			} catch (final IOException e) {
				e.printStackTrace();
			}
			break;
		case 7:													//Knapsack
			try {
				int n = 0;
				int W = 0;
				int[] w = null;
				int[] v = null;
				if ((line = buffIn.readLine()) != null) {
					n = Integer.parseInt(line + "");
				}
				if ((line = buffIn.readLine()) != null) {
					W = Integer.parseInt(line + "");
				}
				if ((line = buffIn.readLine()) != null) {
					final String[] lineArray = line.split(" ");
					w = new int[n];
					for (int s = 0; s < n; s++) {
						w[s] = Integer.parseInt(lineArray[s]);
					}
				}
				if ((line = buffIn.readLine()) != null) {
					final String[] lineArray = line.split(" ");
					v = new int[n];
					for (int s = 0; s < n; s++) {
						v[s] = Integer.parseInt(lineArray[s]);
					}
				}
				final KnapSack kp = new KnapSack(n, w, v, W);
				System.out.println("The resulting optimal solution is: "
						+ kp.getKnapSack() + "\n");
				System.out.println(kp.toString());
				buffIn.close();
			} catch (final IOException io) {
				io.printStackTrace();
			} catch (final NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			break;
		case 8:														//MCM	
			try {
				int[] dimArray = null;
				if ((line = buffIn.readLine()) != null) {
					System.out.println("The file contents are: "+line);
					final String[] lineArray = line.split(" ");
					dimArray = new int[lineArray.length];
					for (int s = 0; s < lineArray.length; s++) {
						dimArray[s] = Integer.parseInt(lineArray[s]);
					}
				}
				final MCM mcm1 = new MCM(dimArray);
				mcm1.calculateMatrix();
				System.out.println(mcm1.toString());
				System.out.println(mcm1.printParenthesizations());
				buffIn.close();
			} catch (final IOException io) {
				io.printStackTrace();
			} catch (final NumberFormatException nfe) {
				nfe.printStackTrace();
			}
			break;
		case 9:												// Splay Tree
			try {
				   Scanner scan = new Scanner(System.in);
			       int option;
			       
			       SplayTree spt = new SplayTree();         
			       
			       if ((line = buffIn.readLine()) != null) {
			    	   System.out.println("The file contents are: "+line);
	        		   final String[] lineArray = line.split(" ");
	        		   for (int s = 0; s < lineArray.length; s++) {
							spt.insert(Integer.parseInt(lineArray[s]));
					   }
	        	   }
			       //Display tree 
			       System.out.println(spt.toString());
		          
			       //Perform tree operations
			       do    
			       {
			           System.out.println("\n\nSplay Tree Operations\n"
			           +"\t0. Read from a file (note: reading from a file will clear " +
			           												"the current tree)\n" 
			           +"\t1. Insert\n"
			           +"\t2. Delete\n"
			           +"\t3. Search\n"
			           +"\t4. Size of n(T) - the total number of nodes in the tree\n"
			           +"\t5. Check if empty\n"
			           +"\t6. Clear the tree\n"
			           +"\t7. Go back to Main Menu\n");
			           
			           option = scan.nextInt();            
			           switch (option){
			           case 0:
			        	   spt.clear();
			        	   handleIO(choice);
			        	   if ((line = buffIn.readLine()) != null) {
			        		   System.out.println("The file contents were: "+line);
			        		   final String[] lineArray = line.split(" ");
			        		   for (int s = 0; s < lineArray.length; s++) {
									spt.insert(Integer.parseInt(lineArray[s]));
							   }
			        	   }
			        	   //Display tree 
					       System.out.println(spt.toString());
			               break;
			           case 1 : 
			               System.out.println("Enter integer nodeData to insert");
			               spt.insert( scan.nextInt() );                     
			               //Display tree 
					       System.out.println(spt.toString());
			               break;
			           case 2 : 
			               System.out.println("Enter integer nodeData to remove");
			               spt.remove( scan.nextInt() );             
			               //Display tree 
					       System.out.println(spt.toString());
			               break;                          
			           case 3 : 
			               System.out.println("Enter integer nodeData to search");
			               System.out.println("Search result : "
			            		   					+ spt.search( scan.nextInt() ));
			               //Display tree 
					       System.out.println(spt.toString());
			               break;                                          
			           case 4 : 
			               System.out.println("Nodes = "+ spt.countNodes());
			               break;     
			           case 5 : 
			               System.out.println("Empty status = "+ spt.isEmpty());
			               break;     
			           case 6 : 
			               System.out.println("\nTree Cleared");
			               spt.clear();
			               //Display tree 
					       System.out.println(spt.toString());
			               break;  
			           case 7 : 
			        	   System.out.println("Going back to the Main Menu");
			        	   break;
			           default :
			        	   option = 7;
			               System.out.println("Not a valid entry. Going back to " +
			               										"the Main Menu\n ");
			               break;   
			           }
			       } while (option != 7);
			       
			} catch (final NumberFormatException nfe) {
				nfe.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		case 10:											// AVL Tree
			break;
		case 11:											// Black and Red Tree
			break;
		case 12:											// B - Tree
			break;
		case 13:											// B+ - Tree
			break;
		default:
			System.out.println("Something went wrong, exiting the program!: mainMenu()");
			System.exit(0);
			break;
		}
	}
}