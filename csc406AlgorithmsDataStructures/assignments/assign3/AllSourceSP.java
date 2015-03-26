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


/*Algorithm Floyd(W)
//Input: the weight matrix W of a graph with no negative-length cycle
//Output: the distance matrix of the shortest paths’ lengths
//initialize the non-diagonal zeros to ∞.

Step 1: D = W		//O(n^2)
Step 2: for k = 1..n do	//  O(n^3)
	   for i = 1..n do
   	      for j = 1..n do
      		dij = min{dij,  dik + dkj  );
Step 3: return D

Analysis: the above algorithm is O(n^3) time complexity.*/


/**AllSourceSP Class: All-Source Shortest Path uses Floyd's Algorithm to find the shortest path*/
public class AllSourceSP{
	
	/**private data members*/
	private int[][] D;
	private int[][] path;
	private int r;
	
	/**AllSourceSP Constructor: */
	AllSourceSP(int[][] W){
		r = 0;
		D = W;
		path = new int[D.length][D.length];
		for(int i = 0; i < D.length; i++){
			for(int j = 0; j < D.length; j++){
				if((D[i][j]) == 0 && (i != j)){
					D[i][j] = Integer.MAX_VALUE;
				}else if((D[i][j]) != 0 &&(i != j)){
					path[i][j] = j;
				}else{
					
				}
			}
		}
		System.out.println("The initial Matrix "+toString());
	}
	
	protected int[][] generateD(){
		for(int k = 0; k < D.length; k++){
			for(int i = 0; i < D.length; i++){
				for(int j = 0; j < D.length; j++){
					if(D[i][j] > (D[i][k] + D[k][j]) && (D[i][k] + D[k][j]) > -1 && D[i][k] != Integer.MAX_VALUE && D[k][j] != Integer.MAX_VALUE){
						D[i][j] = D[i][k] + D[k][j];
						path[i][j] = path[i][k];
					}
				}
			}
			System.out.println(" "+toString());
		}
		return D;
	}
	
	protected String getPath(int i, int j){
		int nodei = i;
		StringBuilder pathString = new StringBuilder();
		if(path[i-1][j-1] == Integer.MAX_VALUE || D[i-1][j-1] == 0){
			return "\nNo Path Exists between nodes "+i+" and "+j+"\n";
		}
		pathString.append(new Integer(i).toString()+",");
		while(i!=j){
			i = path[i-1][j-1]+1;
			pathString.append(i+",");
		}
		String resultPathString = "The shortest path between nodes "+nodei+" and "+j+" consists of: ";
		String[] sa = pathString.toString().split(",");
		for(int s = 0; s < sa.length; s++){
			if(s != sa.length-1){
				resultPathString += sa[s]+" -> ";
			}else{
				resultPathString += sa[s]+" with a weight of "+D[nodei-1][j-1]+"\n";
			}
		}
			return resultPathString;
	}
	
	@Override
	/**toString method converts the data structure to a readable string*/
	public String toString(){
		String resultMatrixN = "\n R"+r+" is: \n\n";
		for(int i  = 0; i < D.length; i++){
			resultMatrixN += "[";
			for(int j = 0; j < D[0].length; j++){
				if(D[i][j] == Integer.MAX_VALUE){
					resultMatrixN += " ∞"+Double.POSITIVE_INFINITY;
					//System.out.println(Character.toString('\u221E'));
				}else{
					resultMatrixN += " "+D[i][j];
				}
			}
			resultMatrixN += " ]\n";
		}
		//resultMatrixN +="\n";
		if(r >= D.length){
			r = D.length;
		}else{
			r++;
		}
		return resultMatrixN;
	}
}