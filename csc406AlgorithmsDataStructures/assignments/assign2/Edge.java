package poset;

public class Edge {

	/**private data members*/
	private int weight;
	private Node adjNodei;
	private Node adjNodej;
	
	/**Constructor Edge */
	public Edge(){
		weight = 0;
		adjNodei = null;
		adjNodej = null;
	}
	
	/**Constructor Edge with parameters */
	public Edge(int weight, Node adjNodei, Node adjNodej){
		this.weight = weight;
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
