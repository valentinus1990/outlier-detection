package edu.unl.bsm.OutlierDetection.Model;

public class SBNEdge {
	
	private Node node1;
	private Node node2;

	public SBNEdge() {
		// TODO Auto-generated constructor stub
	}
	
	public SBNEdge(Node node1, Node node2){
		this.node1 = node1;
		this.node2 = node2;
	}

	/**
	 * @return the node1
	 */
	public Node getNode1() {
		return node1;
	}

	/**
	 * @param node1 the node1 to set
	 */
	public void setNode1(Node node1) {
		this.node1 = node1;
	}

	/**
	 * @return the node2
	 */
	public Node getNode2() {
		return node2;
	}

	/**
	 * @param node2 the node2 to set
	 */
	public void setNode2(Node node2) {
		this.node2 = node2;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
