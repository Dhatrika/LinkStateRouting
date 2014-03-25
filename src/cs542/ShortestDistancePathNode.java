package cs542;

import java.util.LinkedList;

public class ShortestDistancePathNode {
	
	private int nodeNmber;
	private int distance;
	private String shortestRoute = "";
	private LinkedList<DistanceNode> list = new LinkedList<DistanceNode>();
	
	public int getDistance() {
		return distance;
	}
	public void setDistance(int distance) {
		this.distance = distance;
	}
	public String getShortestRoute() {
		return shortestRoute;
	}
	public void setShortestRoute(String shortestRoute) {
		this.shortestRoute = shortestRoute;
	}
	public LinkedList<DistanceNode> getList() {
		return list;
	}
	public void setList(LinkedList<DistanceNode> list) {
		this.list = list;
	}
	public int getNodeNmber() {
		return nodeNmber;
	}
	public void setNodeNmber(int nodeNmber) {
		this.nodeNmber = nodeNmber;
	}
}
