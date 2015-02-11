/************************************
 * Jason Laske
 * Professor Rajasethupathy
 * CSC 406 01 Spring 2015
 * Assignment 1
 * Date Assigned: 1/25/2015
 * Date Due: 2/11/2015
 * Date Submitted: 2/11/2015 
 ***********************************/

package poset;

public class Edge {

	/**private data members*/
	private int weight;
	private Node adjNodei;
	private Node adjNodej;
	private int eLabel = 0;
	
	/**Constructor Edge */
	public Edge(){
		weight = 0;
		adjNodei = null;
		adjNodej = null;
		eLabel = 0;
	}
	
	/**Constructor Edge with parameters for weighted*/
	public Edge(int weight, Node adjNodei, Node adjNodej, int eLabel){
		this.weight = weight;
		this.adjNodei = adjNodei;
		this.adjNodej = adjNodej;
		this.eLabel = eLabel;
	}
	
	/**Constructor Edge with parameters for weighted*/
	public Edge(int i, int j, int weight){
		this.weight = weight;
		Node nodei = new Node(i);
		adjNodei = nodei;
		Node nodej = new Node(j);
		adjNodej = nodej;
		this.eLabel = eLabel++;
	}
	
	/**Constructor Edge with parameters for weighted*/
	public Edge(Node adjNodei, Node adjNodej, int weight){
		this.weight = weight;
		this.adjNodei = adjNodei;
		this.adjNodej = adjNodej;
	}
	
	/**Constructor Edge with parameters for unweighted*/
	public Edge(int i, int j){
		Node nodei = new Node(i);
		adjNodei = nodei;
		Node nodej = new Node(j);
		adjNodej = nodej;
		this.eLabel = eLabel++;
	}
	
	/**Constructor Edge with parameters for unweighted*/
	public Edge(Node adjNodei, Node adjNodej){
		this.adjNodei = adjNodei;
		this.adjNodej = adjNodej;
	}
	
	
	
	/**Mutator Methods*/
	protected void setWeight(int weight){
		this.weight = weight;
	}
	
	protected void setAdjNodei(Node adjNodei){
		this.adjNodei = adjNodei;
	}
	
	protected void setAdjNodej(Node adjNodej){
		this.adjNodej = adjNodej;
	}
	
	/**Accessor Methods*/
	protected int getWeight(){
		return weight;
	}
	
	protected Node getAdjNodei(){
		return adjNodei;
	}
	
	protected Node getAdjNodej(){
		return adjNodej;
	}
}
