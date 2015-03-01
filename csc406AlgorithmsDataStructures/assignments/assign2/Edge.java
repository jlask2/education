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

public class Edge {

	/**private data members*/
	private int eLabel = 0;
	private int weight;
	private Node adjNodei;
	private Node adjNodej;
	
	/**Constructor Edge */
	public Edge(){
		eLabel = 0;
		weight = 0;
		adjNodei = null;
		adjNodej = null;
	}
	
	/**Constructor Edge with parameters for weighted*/
	public Edge(int eLabel, int weight, Node adjNodei, Node adjNodej){
		this.eLabel = eLabel;
		this.weight = weight;
		this.adjNodei = adjNodei;
		this.adjNodej = adjNodej;
	}
	
	/**Constructor Edge with parameters for weighted*/
	public Edge(int i, int j, int weight){
		this.eLabel = eLabel++;
		this.weight = weight;
		Node nodei = new Node(i);
		adjNodei = nodei;
		Node nodej = new Node(j);
		adjNodej = nodej;
	}
	
	/**Constructor Edge with parameters for weighted*/
	public Edge(Node adjNodei, Node adjNodej, int weight){
		this.weight = weight;
		this.adjNodei = adjNodei;
		this.adjNodej = adjNodej;
	}
	
	/**Constructor Edge with parameters for unweighted*/
	public Edge(int i, int j){
		this.eLabel = eLabel++;
		Node nodei = new Node(i);
		adjNodei = nodei;
		Node nodej = new Node(j);
		adjNodej = nodej;
	}
	
	/**Constructor Edge with parameters for unweighted*/
	public Edge(Node adjNodei, Node adjNodej){
		this.adjNodei = adjNodei;
		this.adjNodej = adjNodej;
	}
	
	/**Mutator Methods*/
	protected void seteLabel(int eLabel){
		this.eLabel = eLabel;
	}
	
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
	protected int geteLabel(){
		return eLabel;
	}
	
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
