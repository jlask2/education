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

import java.util.Stack;

/**KnapSack Class: Implements the use of overlapping sub problems to solve an overall problem*/
public class KnapSack{
	
	/**private data members*/
	private int[][] ks;
	private int[] n;
	private int[] w;
	private int[] v;
	private int W;
	
	public KnapSack(int[] n, int[] w, int[] v, int W){
		this.n = n;
		this.w = w;
		this.v = v;
		this.W = W;
		ks = new int[n.length+1][W + 1];
	}
	
	/**KnapSack Constructor: accepts 
	 * @return */
	protected int getKnapSack(){
	      for(int j = 0; j < W+1; j++){ 
	    	  ks[0][j] = 0;
	             for (int i = 1; i < n.length + 1; i++){ 
	                   for (int h = 0; h < W+1; h++){
	                         if ((h-w[i-1]) >= 0){
	                        	 ks[i][h] = max(ks[i-1][h], v[i-1] + ks[i-1][h-w[i-1]]);
	                        	 //System.out.println("v[i-1] + ks[i-1][h-w[i-1]] = "+(v[i-1] + ks[i-1][h-w[i-1]])+" where v[i-1] = "+v[i-1]+", and ks[i-1][h-w[i-1]] = "+ks[i-1][h-w[i-1]]);
	                        	 //System.out.println("ks[i][h] = "+ks[i][h]+" where i = "+i+", and h = "+h);
	                         }else{
	                        	 ks[i][h] = ks[i-1][h];
	                        	 //System.out.println("else ks[i][h] = "+ks[i][h]+" where i = "+i+", and h = "+h);
	                         }
	                   }
	             }
	      }
	      return ks[n.length][W];
	}
	      
	private int max(int value1, int value2){
		if(value1 >= value2){
			return value1;
		}else{
			return value2;
		}
	}
	
	@Override
	/**toString method: */
	public String toString(){
		String resultString = "";
		for(int i = 0; i < ks.length; i++){
			resultString += "[";
			for(int j = 0; j < ks[0].length; j++){
				resultString += " "+ks[i][j];
			}
			resultString += " ]\n";
		}
		return resultString;
	}
}