package edu.unl.bsm.OutlierDetection.Model;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jxl.read.biff.BiffException;

public class Main {

	public Main() {
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * get SBN edges based on SBNPath
	 * A set based nearsest edge w.r.t the SBN-path<p1, p2,..., pr> is defined as
	 * ei = (oi, pi+1) where oi is one of G, and d(ei) = d(oi, pi+1) = d({p1, p2, ..., pi}, {pi+1, pi+2, ..., pr})
	 * @param sbnPath
	 * @param nodesB
	 * @return
	 */
	public List<SBNEdge> getSBNEdges(List<Node> sbnPath, List<Node> nodesB){
		
		Distance distance = new Distance();
		List<SBNEdge> edges = new ArrayList<SBNEdge>();
		
		while(nodesB.size()>0){
			Distance minDs = distance.getMinDistance(sbnPath, nodesB);
			SBNEdge se = new SBNEdge(minDs.getNode1(), minDs.getNode2());
			edges.add(se);
			sbnPath.add(minDs.getNode2());
			nodesB.remove(minDs.getNode2());
		}
		
		for(int i = 0; i < edges.size(); i++){
			distance.setNode1(edges.get(i).getNode1());
			distance.setNode2(edges.get(i).getNode2());
			float dis = distance.getDistance();
//			System.out.println("Distance between "+ distance.getNode1().getNodeName() +" and " +  distance.getNode2().getNodeName() + " is " + dis);
		}
		
		return edges;
	}
	
	/**
	 * get SBN Trails 
	 * {e1, e2, ... , ek-1} is the set of SBN-edge w.r.t. the SBN-path of Nk(p)
	 * SBN Trails: tr(p, Os) = <e1, e2, ... , es> = <(p, o1), (o1, o2), ... , (os-1, os)>;
	 * @return
	 */
	public Set<SBNEdge> getSBNTrails(Node start, Node end, List<SBNEdge> sbnEdges){
		Node endNode = end;
		Set<SBNEdge> tr = new HashSet<SBNEdge>();
		SBNEdge tempEdge = null;
		int i = 0;
		while(i < sbnEdges.size()){
			//find the end of the point
			if(sbnEdges.get(i).getNode2() == endNode){
				tempEdge = sbnEdges.get(i);
				tr.add(tempEdge);
				i=0;
				endNode = null;
				continue;
			}
			
//			if(testNode != null){
//				continue;
//			}
			
			if(tempEdge != null && tempEdge.getNode1() == sbnEdges.get(i).getNode2()){
				tempEdge = sbnEdges.get(i);
				tr.add(tempEdge);
				i = 0;
				continue;
			}
			
			if(tempEdge != null&&tempEdge.getNode1() == start  ){
				break;
			}
			
			i++;
			
		}
		
		
//		System.out.println("----------tr------------------------");
		
//		System.out.println(start.getNodeName() + " to " + end.getNodeName());
		for(SBNEdge el: tr){
			Distance ds = new Distance();
			ds.setNode1(el.getNode1());
			ds.setNode2(el.getNode2());
			float dis = ds.getDistance();
//			System.out.println("Distance between "+ ds.getNode1().getNodeName() +" and " +  ds.getNode2().getNodeName() + " is " + dis);
		}
		
		return tr;
	}
	
	/**
	 * get Information Transfer function
	 * f(ei) = f((Oi, Oi+1)) = 1 - (d(Oi, Oi+1)/dk(p));
	 * @param sbnEdge
	 * @param latestOne
	 * @return
	 */
	public float getInformationTransferFunction(SBNEdge sbnEdge, Node latestOne){
		float result = 0;
		float dkp = latestOne.getkDistance().getDistance();
		Node node1 = sbnEdge.getNode1();
		Node node2 = sbnEdge.getNode2();
		Distance ds = new Distance(node1, node2);
		result = 1 - (ds.getDistance()/dkp);
		
		return result;
	}

	/**
	 * f(tr(p, os)) = f(e1)f(e2)...f(es)
	 * @param trails
	 * @param latestOne
	 * @return
	 */
	public float getInformationTransferFunctionOfSBNTrail(Set<SBNEdge> trails, Node latestOne){
		if(trails == null){
			throw new NullPointerException("SBN trails do not exist");
		}
		float result = 1;
		for(SBNEdge edge : trails){
			result *= getInformationTransferFunction(edge, latestOne);
		}
		return result;
	}
	
	
	/**
	 * Connectivity and density based local outlier factor based on latest time point;
	 * @return
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public float getCDLOF(int k, int hour) throws BiffException, IOException, ParseException{
		
		float sumNumerator  = 0;
		float sumDenominator = 0;
		float result = 0;
		Distance ds = new Distance();
		List<NKP> knb = ds.getKNeighborsForLatestNode(k, hour);
		List<Node> sbnPath = new ArrayList<Node>();
		Node latestOne = knb.get(0).getNode();
		
		Map<Node, NKP> map = new HashMap<Node, NKP>();
		
		for(NKP el: knb){
			map.put(el.getNode(), el);
		}
		
		List<NKP> temp = new ArrayList<NKP>(knb);
		List<Node> nodesB = new ArrayList<Node>();
		for(NKP el: temp){
			nodesB.add(el.getNode());
		}
		sbnPath.add(latestOne);
		nodesB.remove(latestOne);
		
		List<SBNEdge> sbnEdges = getSBNEdges(sbnPath, nodesB);
		
		float dkp = latestOne.getkDistance().getDistance();
//		System.out.println("dk(p) = " + dkp);
		
		float lrdForLatest = ds.getLocalReachabilityDensity(knb.get(0));
//		System.out.println("lrd for latest one : " + lrdForLatest);
		for(Node node: sbnPath){
			Set<SBNEdge> trails = getSBNTrails(latestOne, node, sbnEdges);
			float lrd = ds.getLocalReachabilityDensity(map.get(node));
			
			float ftr = getInformationTransferFunctionOfSBNTrail(trails, latestOne);
			sumNumerator += lrd*ftr;
			sumDenominator += ftr * lrdForLatest;
//			System.out.println("lrd : " + lrd);
//			System.out.println("InformationTransferFunctionOfSBNTrail : " + ftr);
		}
		
		result = sumNumerator/sumDenominator;
		
		System.out.println("CDLOF : " + result);
		return result;
	}
	
	
	
	/**
	 * @param args
	 * @throws ParseException 
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public static void main(String[] args) throws BiffException, IOException, ParseException {
		Main main = new Main();
		for(int i = 20; i < 100; i++){
			System.out.print("K: " + i + " ");
			main.getCDLOF(i, 2);
		}

	}

}
