package B_Graph;

public class Route {
	
	//=== Attributes --------------------------------------------------------
	private String building_point1;
	private String building_point2;
	private double distance;
	
	
	//=== Constructor --------------------------------------------------------
	public Route(String building_point1, String building_point2, double distance) {
		this.building_point1 = building_point1;
		this.building_point2 = building_point2;
		this.distance = distance;
	}

	
	
	//=== Getter and Setter --------------------------------------------------
	public String getBuilding_point1() {
		return building_point1;
	}
	public void setBuilding_point1(String building_point1) {
		this.building_point1 = building_point1;
	}
	/////////////////////////////////////
	public String getBuilding_point2() {
		return building_point2;
	}
	public void setBuilding_point2(String building_point2) {
		this.building_point2 = building_point2;
	}
	/////////////////////////////////////
	public double getDistance() {
		return distance;
	}
	public void setDistance(double distance) {
		this.distance = distance;
	}


	//=== Methods ============================================================
	
	//to String
	@Override
	public String toString() {
		return "[building_point1: " + building_point1 + ",   building_point2: " + building_point2 + ",   distance:" + distance+ "]";
	}
    /////////////////////////////////////
	
	// Print Data
	public void printData() {
		System.out.println("building_point1 : " + building_point1 + "\n" +
						   "building_point2 : " + building_point2 + "\n" +
				           "distance      : " + distance);
	}

	
	
}
