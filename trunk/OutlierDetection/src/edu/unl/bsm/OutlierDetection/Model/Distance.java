package edu.unl.bsm.OutlierDetection.Model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jxl.read.biff.BiffException;

public class Distance {
	
	private Node node1;
	private Node node2;
	private int index;
	private float distance;
	
	public Distance(){
		
	}

	public Distance(Node node1, Node node2) {
		this.node1 = node1;
		this.node2 = node2;
		this.index = -1;
		this.distance = -1;
	}
	/**
	 * get the distance of two nodes
	 * @return distance of two nodes
	 */
	public float getDistance(){
		float clDiff = node1.getNormalCL() -node2.getNormalCL();
		float tempDiff = node1.getNormalTemp() - node2.getNormalTemp();
		this.distance = clDiff*clDiff + tempDiff*tempDiff;
		
		return this.distance;
	}
	
	public void setDistance(float distance){
		this.distance = distance;
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
	 * @return the index
	 */
	public int getIndex() {
		return index;
	}
	/**
	 * @param index the index to set
	 */
	public void setIndex(int index) {
		this.index = index;
	}
	
	public List<Node> getNodeList(int hour) throws BiffException, IOException, ParseException{
		// read data from database and group them into the list
				OutlierWeekDayProvider wd = new OutlierWeekDayProvider();
				wd.setHistdata();
				wd.setOutlierModel(hour);
				
//				OutlierWeekendProvider we = new OutlierWeekendProvider();
//				we.setHistdata();
//				we.setOutlierModel(hour);
				List<Node[]> test = wd.getOutlierModel();
				for(Node[] el: test){
					for(int i = 0; i < el.length; i++){
//						if(el[i] != null)
//						System.out.print(el[i].getNodeName()+ " "+ el[i].getTemperature()+" "+el[i].getCoolingLoad()+ " ");
					}
//					System.out.println();
				}
			
				
				//++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
				
				
				//original node list
				List<Node> nodeList = new ArrayList<Node>();
				for(Node[] element: test){
					for(int i = 0; i < element.length; i++){
						if(element[i]!=null){
							nodeList.add(element[i]);
						}
					}
				}
				
				
				List<Node> clSorted = new ArrayList<Node>(nodeList);
				List<Node> tempSorted = new ArrayList<Node>(nodeList);
				
				Collections.sort(clSorted, new Node().new SortByCoolingLoad());
				Collections.sort(tempSorted, new Node().new SortByTemperature());
				//get the max and min coolingload data
				float minCL = clSorted.get(0).getCoolingLoad();
				float maxCL = clSorted.get(clSorted.size()-1).getCoolingLoad();
				
				//get the max and min temperature data
				float minTemp = tempSorted.get(0).getTemperature();
				float maxTemp = tempSorted.get(tempSorted.size()-1).getTemperature();
				
				//set the value of normal Coolingload and temperature value based on the formula where it is (x-min)/(max-min)
				for(Node element : nodeList){
					float normalCL = (element.getCoolingLoad() - minCL)/(maxCL-minCL);
					element.setNormalCL(normalCL);
					
					float normalTemp = (element.getTemperature() - minTemp)/(maxTemp - minTemp);
					element.setNormalTemp(normalTemp);
				}
				
				
				return nodeList;
				
	}
	
	public List<Distance> getSortedDistancebyIndex(List<Node> nodeList, int index){
		List<Distance> unSorted = new ArrayList<Distance>();
		
		//a list of the distance between node N and node i
		for(int i = 0; i < nodeList.size(); i++){
			unSorted.add(new Distance(nodeList.get(index), nodeList.get(i)));
		}
		
		
		for(int i = 0; i < unSorted.size(); i++){
			unSorted.get(i).setIndex(i);
		}
		
		
		
//		System.out.println("==========Unsorted============");
		
//		for(Distance element : unSorted){
//			float disValue = element.getDistance();
////			System.out.println(disValue);
//			
//		}
		
		List<Distance> sorted = new ArrayList<Distance>(unSorted);
		System.setProperty("java.util.Arrays.useLegacyMergeSort", "true");
		
		Collections.sort(sorted, new Distance().new SortByDistance());
		
		
//		System.out.println("==========Sorted============");
		
		
//		for(Distance element : sorted){	
//			
//			float disValue = element.getDistance();
////			System.out.printf("D(%s, %s): %.6f , Original Distance Index: %d\n",element.getNode1().toString(), element.getNode2().toString(), disValue, element.getIndex() );
//				
//		}
		
		return sorted;
		
	}
	
	
	public List<NKP> getKNeighborsForLatestNode(int k, List<Node> nodeList) throws BiffException, IOException, ParseException{
		int duplicate = 0;
		int count = -1;
		Distance ds = new Distance();
		
		int latestOne = nodeList.size()-2;
		//sorted distance for latest node.
		List<Distance> sortedDistance = ds.getSortedDistancebyIndex(nodeList, latestOne);
		for(int i = 1; i < sortedDistance.size(); i++){
			if(sortedDistance.get(i).getDistance() < 0.000001){
				duplicate++;
			}else{
				break;
			}
		}
		
		NKP nb = new NKP(null, k, null);
		List<NKP> kNeighborsForLatestNode = new ArrayList<NKP>();
		
		
		/*set Neighbor and reachability distance*/
		for(int i = 0 ; i < k+duplicate; i++){
			NKP kNeighbor = nb.getKNeighborWithRD(nodeList, sortedDistance.get(i).getIndex(), k);
			Distance PODistance = sortedDistance.get(i);
			Distance kDistance = kNeighbor.getNode().getkDistance();
		
			if(kDistance.getDistance() <= PODistance.getDistance()){
				kNeighbor.getNode().setReachDistance(PODistance);
			}else{
				kNeighbor.getNode().setReachDistance(kDistance);
			}
			kNeighborsForLatestNode.add(kNeighbor);
		}
		
		for(int i = k+duplicate-1; i >=0; i--){
			if(sortedDistance.get(i).getDistance() == sortedDistance.get(k+duplicate-1).getDistance()){
				count++;
			}else{
				break;
			}
		}
		
		//delete  nodes located on the circle
		for(int i = 0; i < count; i++){
			kNeighborsForLatestNode.remove(k-1-i);
		}
		
		
		
//		System.out.println(kNeighborsForLatestNode);
		
		return kNeighborsForLatestNode;
	}
	
	/**
	 * lrdk(p) = |Nk(p)| / sum of rdk(p,o)
	 * @param kNeighborsForLatestNode
	 * @return
	 */
	public float getLocalReachabilityDensity(NKP nkp){
		if(nkp == null){
			throw new NullPointerException();
		}
		
		
		
		float lrd = 0;
		int numberofKDistanceNeighbor = nkp.getNeighbors().size();
		float sum = 0;
		for(Node el : nkp.getNeighbors()){
			sum += el.getReachDistance().getDistance();
		}
		
//		System.out.println("Sum: " + sum);
		
		
		lrd = numberofKDistanceNeighbor/sum;
		
//		System.out.println("LRD: " + lrd);
		
		
		return lrd;
		
	}
	
	
	public Distance getMinDistance(List<Node> set1, List<Node> set2){
		if(set1== null || set2 == null){
			throw new NullPointerException();
		}
		
		float min = Float.MAX_VALUE;
		Distance minDS = null;
		for(Node node1 : set1){
			for(Node node2: set2){
				Distance dis = new Distance(node1, node2);
				if(dis.getDistance() < min){
					min = dis.getDistance();
					minDS = dis;
				}
			}
		}
		
		
		return minDS;
		
	}
	
	
	
	
class SortByDistance implements Comparator<Object> {

	@Override
	public int compare(Object o1, Object o2) {
		int a1 = (int) (((Distance)o1).getDistance()*10000000);
		int a2 = (int) (((Distance)o2).getDistance()*10000000);
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
	 * @throws IOException 
	 * @throws BiffException 
	 * @throws ParseException 
	 */
	public static void main(String[] args) throws BiffException, IOException, ParseException {
		
	}

}
