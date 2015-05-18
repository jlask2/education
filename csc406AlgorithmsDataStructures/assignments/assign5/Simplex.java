/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 5
 * Date Assigned: 4/18/2015
 * Date Due: 4/29/2015
 * Date Submitted: 4/29/2015
 ***********************************/

package algoData;

import java.text.DecimalFormat;

public class Simplex {
    private double[][] a;   // table
    private int M;          // number of constraints
    private int N;          // number of original variables

    private static String compiledResult = "";

    /** constructor Simplex: sets up the simplex table and 
     * finds the max optimal value
     */
    public Simplex(double[][] conmatrix, double[] objrow) {
        M = conmatrix.length;
        N = conmatrix[0].length - 1;
        a = new double[M+1][N+M+1];
        for (int i = 0; i < M; i++)
            for (int j = 0; j < N; j++)
                a[i][j] = conmatrix[i][j];
        for (int i = 0; i < M; i++) a[i][N+i] = 1.0;
        for (int j = 0; j < N; j++) a[M][j]   = objrow[j];
        for (int i = 0; i < M; i++) a[i][M+N] = conmatrix[i][N];

        compiledResult = "";
        compiledResult += toString();
        System.out.println(toString());
        
        solve();
    }

    /** method solve: run simplex algorithm */ 
    private void solve() {
        while (true) {
            // find entering variable column q
            int q = choosePivotColumn();
            if (q == -1) break;  // optimal

            // find departing variable row p
            int p = minRatioRule(q);
            if (p == -1) throw new ArithmeticException(compiledResult +="Linear program is unbounded\n");

            // pivot
            pivot(p, q);

            compiledResult += toString();
            System.out.println(toString());
        }
    }

    /** method choosePivotColumn: lowest index of a non-basic column with a negative cost */
    private int choosePivotColumn() {
        for (int j = 0; j < M + N; j++)
            if (a[M][j] < 0) return j;
        return -1;  		// optimal
    }

    /** method minRatioRule: find row p using min ratio rule (-1 if no such row)*/
    private int minRatioRule(int q) {
        int p = -1;
        for (int i = 0; i < M; i++) {
            if (a[i][q] <= 0) continue;
            else if (p == -1) p = i;
            else if ((a[i][M+N] / a[i][q]) < (a[p][M+N] / a[p][q])) p = i;
        }
        return p;
    }

    /** method pivot: pivot on entry (p, q) */
    private void pivot(int p, int q) {

        // everything but row p and column q
        for (int i = 0; i <= M; i++)
            for (int j = 0; j <= M + N; j++)
                if (i != p && j != q) a[i][j] -= a[p][j] * a[i][q] / a[p][q];

        // zero out column q
        for (int i = 0; i <= M; i++)
            if (i != p) a[i][q] = 0.0;

        // scale row p
        for (int j = 0; j <= M + N; j++)
            if (j != q) a[p][j] /= a[p][q];
        a[p][q] = 1.0;
    }

    /** method value: return optimal objective value*/
    public double value() {
        return a[M][M+N];
    }

    /** method getCompiledResult: return the results for file output */
    public static String getCompiledResults(){
    	return compiledResult;
    }
    
    /** method test: set up the new Simplex and test it*/
    public static void test(double[][] A, double[] c) {
        Simplex lp = new Simplex(A, c);
        compiledResult += "Optimal Maximized Value = " + lp.value()+"\n";
        System.out.println("Optimal Maximized Value = " + lp.value()+"\n");
    }
    
    /** method toString: print the iteration of the table*/
    public String toString() {
    	String resultString = "table\t";
    	DecimalFormat df = new DecimalFormat("###.##");
    	for(int x = 0; x < N; x++){
    		resultString += "x"+(x+1)+"\t";
    	}
    	for(int s = N-1; s <= M ; s++){
    		resultString += "s"+s+"\t";
    	}
		resultString += "ans\n";
        for (int i = 0; i <= M; i++) {
        	if(i == M){
        		resultString += "obj\t";
        	}else{
        		resultString += "s"+(i+1)+"\t";
        	}
        	for (int j = 0; j <= M + N; j++) {
            	resultString += ""+df.format(a[i][j])+"\t"; 
            }
            resultString += "\n";
        }
        resultString += "\nvalue = " + value()+"\n";
        resultString += "\n";
        return resultString;
    }
}