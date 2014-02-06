package edu.unl.bsm.OutlierDetection.Model;

import java.util.ArrayList;
import java.util.List;

public class SBNPath {
	
	Node node;
	List<Node> neighbors;
	List<SBNEdge> sbnEdges;

	public SBNPath() {
		
	}
	
	public SBNPath(Node node){
		this.node = node;
		neighbors = new ArrayList<Node>();
		sbnEdges = new ArrayList<SBNEdge>();
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
