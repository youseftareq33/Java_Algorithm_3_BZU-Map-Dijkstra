package C_DijkstraAlgorithm;


public class Dijkstra {
	
	//=== Attributes --------------------------------------------------------
	private String curr_Building;
	private double shortestDistance;
	private String prev_Building;

	
	//=== Constructor --------------------------------------------------------
	public Dijkstra(String curr_Building, double shortestDistance, String prev_Building) {
		this.curr_Building = curr_Building;
		this.shortestDistance = shortestDistance;
		this.prev_Building = prev_Building;
	}

	
	
	//=== Getter and Setter --------------------------------------------------
	public String getCurr_Building() {
		return curr_Building;
	}
	public void setCurr_Building(String curr_Building) {
		this.curr_Building = curr_Building;
	}
	/////////////////////////////////////
	public double getShortestDistance() {
		return shortestDistance;
	}
	public void setShortestDistance(double shortestDistance) {
		this.shortestDistance = shortestDistance;
	}
	/////////////////////////////////////
	public String getPrev_Building() {
		return prev_Building;
	}
	public void setPrev_Building(String prev_Building) {
		this.prev_Building = prev_Building;
	}
	
	
	//=== Methods ============================================================

	// toString
	@Override
	public String toString() {
		return curr_Building + "            |  " + shortestDistance + "    |          " + prev_Building;
	}
	
	
	
}
