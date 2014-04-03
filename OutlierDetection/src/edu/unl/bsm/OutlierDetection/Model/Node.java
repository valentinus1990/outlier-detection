package edu.unl.bsm.OutlierDetection.Model;

import java.util.Comparator;
import java.util.List;

public class Node {
	
	private float temperature;
	private float coolingLoad;
	private String nodeName;
	private float normalCL;
	private float normalTemp;
	private Distance kDistance;
	private Distance reachDistance;
	private SBNPath sbnPath;
//	private List<Node> neighbors;
//	private int k;
	

	public Node(){
		
	}
	
	public Node(float temperature, float coolingLoad){
		this.temperature = temperature;
		this.coolingLoad = coolingLoad;
	}
	
	public Node(float temperature, float coolingLoad, String nodeName){
		this( temperature,  coolingLoad);
		this.nodeName = nodeName;
	}
	
	/**
	 * Copy Constructor
	 * @param node
	 */
	public Node(Node node){
		this.temperature = node.getTemperature();
		this.coolingLoad = node.getCoolingLoad();
		this.nodeName = node.getNodeName();
		this.normalCL = node.getNormalCL();
		this.kDistance = node.getkDistance();
		this.reachDistance = node.getReachDistance();
		this.sbnPath = node.getSbnPath();
//		this.neighbors = node.getNeighbors();
		
	}
	/**
	 * @return the temperature
	 */
	public float getTemperature() {
		return temperature;
	}

	/**
	 * @param temperature the temperature to set
	 */
	public void setTemperature(float temperature) {
		this.temperature = temperature;
	}

	/**
	 * @return the coolingLoad
	 */
	public float getCoolingLoad() {
		return coolingLoad;
	}

	/**
	 * @param coolingLoad the coolingLoad to set
	 */
	public void setCoolingLoad(float coolingLoad) {
		this.coolingLoad = coolingLoad;
	}

	
	/**
	 * @return the nodeName
	 */
	public String getNodeName() {
		return nodeName;
	}

	/**
	 * @param nodeName the nodeName to set
	 */
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	
	/**
	 * @return the normalCL
	 */
	public float getNormalCL() {
		return normalCL;
	}

	/**
	 * @param normalCL the normalCL to set
	 */
	public void setNormalCL(float normalCL) {
		this.normalCL = normalCL;
	}

	/**
	 * @return the normalTemp
	 */
	public float getNormalTemp() {
		return normalTemp;
	}

	/**
	 * @param normalTemp the normalTemp to set
	 */
	public void setNormalTemp(float normalTemp) {
		this.normalTemp = normalTemp;
	}

	/**
	 * @return the kDistance
	 */
	public Distance getkDistance() {
		return kDistance;
	}

	/**
	 * @param kDistance the kDistance to set
	 */
	public void setkDistance(Distance kDistance) {
		this.kDistance = kDistance;
	}

	/**
	 * @return the reachDistance
	 */
	public Distance getReachDistance() {
		return reachDistance;
	}

	/**
	 * @param reachDistance the reachDistance to set
	 */
	public void setReachDistance(Distance reachDistance) {
		this.reachDistance = reachDistance;
	}

	/**
	 * @return the sbnPath
	 */
	public SBNPath getSbnPath() {
		return sbnPath;
	}

	/**
	 * @param sbnPath the sbnPath to set
	 */
	public void setSbnPath(SBNPath sbnPath) {
		this.sbnPath = sbnPath;
	}

//	/**
//	 * @return the neighbors
//	 */
//	public List<Node> getNeighbors() {
//		return neighbors;
//	}
//
//	/**
//	 * @param neighbors the neighbors to set
//	 */
//	public void setNeighbors(List<Node> neighbors) {
//		this.neighbors = neighbors;
//	}
//
//	/**
//	 * @return the k
//	 */
//	public int getK() {
//		return k;
//	}
//
//	/**
//	 * @param k the k to set
//	 */
//	public void setK(int k) {
//		this.k = k;
//	}

	@Override
	public String toString(){
		return this.nodeName;
	}
	
	class SortByCoolingLoad implements Comparator<Object> {

		@Override
		public int compare(Object o1, Object o2) {
			int a1 = (int)((Node)o1).getCoolingLoad();
			int a2 = (int)((Node)o2).getCoolingLoad();
			if(a1 < a2){
				return 1;
			}
			if(a1 == a2){
				return 0;
			}
			return -1;
		}
	}
	
	class SortByTemperature implements Comparator<Object>{
		
		@Override
		public int compare(Object o1, Object o2) {
			int a1 = (int)((Node)o1).getTemperature();
			int a2 = (int)((Node)o2).getTemperature();
			if(a1 < a2){
				return 1;
			}
			if(a1 == a2){
				return 0;
			}
			return -1;
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
