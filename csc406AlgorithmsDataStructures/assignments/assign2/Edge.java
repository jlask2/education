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

public class Edge implements Comparable{

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
	
	/**Equal method compares data members of two Node objects*/
	@Override
	public boolean equals(Object edge){      //note the type of the parameter
        Edge e = (Edge)edge;               // cast the parameter before use
        return Edge.compare(this.eLabel, e.eLabel)  == 0;
    } 

	/**Compare method compares two Edge data members for integer equality, returns 0 if equal else a 1 if not*/
	private static int compare(int dataMember1, int dataMember2) {
		if(dataMember1 == dataMember2){
			return 0;
		}else{
			return 1;
		}
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

	@Override
	public int compareTo(Object obj) {
		Edge e = (Edge)obj;
		return Edge.compare(this.weight, e.weight);
	}
}
