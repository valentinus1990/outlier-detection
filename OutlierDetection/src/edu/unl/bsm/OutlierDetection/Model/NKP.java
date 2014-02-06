package edu.unl.bsm.OutlierDetection.Model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import jxl.read.biff.BiffException;

public class NKP {
	
	private Node node;
	private final int k;
	private List<Node> neighbors;

	public NKP(Node node, int k, List<Node> neighbors) {
		this.node = node;
		this.k = k;
		this.neighbors = neighbors;
	}

	
	/**
	 * @return the node
	 */
	public Node getNode() {
		return node;
	}

	/**
	 * @param node the node to set
	 */
	public void setNode(Node node) {
		this.node = node;
	}

	/**
	 * @return the neighbors
	 */
	public List<Node> getNeighbors() {
		return neighbors;
	}

	/**
	 * @param neighbors the neighbors to set
	 */
	public void setNeighbors(List<Node> neighbors) {
		this.neighbors = neighbors;
	}

	/**
	 * @return the k
	 */
	public int getK() {
		return k;
	}
	
	/**
	 * get K neighbor by the index
	 * @param nodeList
	 * @param index
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 * @throws ParseException
	 */
	public NKP getKNeighborWithRD(List<Node> nodeList,int index, int k) throws BiffException, IOException, ParseException{
//		int k = 10;
		int duplicate = 0;
		int count = -1; //for d(p,q) < dk(p)
		Distance ds = new Distance();
		
		List<Distance> nodeNDistances = ds.getSortedDistancebyIndex(nodeList, index);
		//get the duplicate nodes
		for(int i = 1; i < nodeNDistances.size(); i++){
			if(nodeNDistances.get(i).getDistance() < 0.000001){
				duplicate++;
			}else{
				break;
			}
		}
		
		NKP nb = new NKP(null, k, null);
		List<Node>	neighborNodes = new ArrayList<Node>();
		// set k distance dk(p)
		Distance kDistance = new Distance(nodeNDistances.get(0).getNode1(), nodeNDistances.get(k+duplicate -1).getNode2());
	    nodeNDistances.get(0).getNode1().setkDistance(kDistance);
	    
	    
	    
		for(int i = 0; i < k+duplicate; i++){
			Node node = nodeNDistances.get(i).getNode2();
			Distance kd = nb.getKDistance(nodeList, nodeNDistances.get(i).getIndex(), k);
			Distance PODistance = nodeNDistances.get(i);
		
			if(kd.getDistance() <= PODistance.getDistance()){
				node.setReachDistance(PODistance);
			}else{
				node.setReachDistance(kDistance);
			}
			
			neighborNodes.add(node);
		}
		
		for(int i = k+duplicate-1; i >=0; i--){
			if(nodeNDistances.get(i).getDistance() == nodeNDistances.get(k+duplicate-1).getDistance()){
				count++;
			}else{
				break;
			}
		}
		
		//delete  nodes located on the circle
		for(int i = 0; i < count; i++){
			neighborNodes.remove(k-1-i);
		}
		
		
//		nodeNDistances.get(0).getNode1().setNeighbors(neighborNodes);
		NKP neighborNodeN = new NKP(nodeNDistances.get(0).getNode1(), k, neighborNodes);
		
//		System.out.println(neighborNodeN);
		
		return neighborNodeN;
	}
	
	
	public Distance getKDistance(List<Node> nodeList,int index, int k){
		int duplicate = 0;
	
		Distance ds = new Distance();
		
		List<Distance> nodeNDistances = ds.getSortedDistancebyIndex(nodeList, index);
		//get the duplicate nodes
		for(int i = 1; i < nodeNDistances.size(); i++){
			if(nodeNDistances.get(i).getDistance() < 0.000001){
				duplicate++;
			}else{
				break;
			}
		}
		
		
		
		// set k distance dk(p)
		Distance kDistance = new Distance(nodeNDistances.get(0).getNode1(), nodeNDistances.get(k+duplicate -1).getNode2());
//	    nodeNDistances.get(0).getNode1().setkDistance(kDistance);
//	    
//		for(int i = 0; i < k+duplicate; i++){
//			Node node = nodeNDistances.get(i).getNode2();
//			neighborNodes.add(node);
//		}
//		
//		for(int i = k+duplicate-1; i >=0; i--){
//			if(nodeNDistances.get(i).getDistance() == nodeNDistances.get(k+duplicate-1).getDistance()){
//				count++;
//			}else{
//				break;
//			}
//		}
//		
//		//delete  nodes located on the circle
//		for(int i = 0; i < count; i++){
//			neighborNodes.remove(k-1-i);
//		}
//		
//		
////		nodeNDistances.get(0).getNode1().setNeighbors(neighborNodes);
//		NKP neighborNodeN = new NKP(nodeNDistances.get(0).getNode1(), k, neighborNodes);
//		
////		System.out.println(neighborNodeN);
		
		return kDistance;
	}
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	}

}
