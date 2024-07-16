package B_Graph;

import javafx.scene.control.Button;

public class Building {

	//=== Attributes --------------------------------------------------------
	private String building_name;
	private double x_axis_layout;
	private double y_axis_layout;
	
	
	//=== Constructor --------------------------------------------------------
	public Building(String building_name, double x_axis_layout, double y_axis_layout) {
		this.building_name = building_name;
		this.x_axis_layout = x_axis_layout;
		this.y_axis_layout = y_axis_layout;
	}



	//=== Getter and Setter --------------------------------------------------
	public String getBuilding_name() {
		return building_name;
	}
	public void setBuilding_name(String building_name) {
		this.building_name = building_name;
	}
	/////////////////////////////////////
	public double getX_axis_layout() {
		return x_axis_layout;
	}
	public void setX_axis_layout(double x_axis_layout) {
		this.x_axis_layout = x_axis_layout;
	}
	/////////////////////////////////////
	public double getY_axis_layout() {
		return y_axis_layout;
	}
	public void setY_axis_layout(double y_axis_layout) {
		this.y_axis_layout = y_axis_layout;
	}


	//=== Methods ============================================================
	
	// toString
	@Override
	public String toString() {
		return "[name: " + building_name + ",   X_axis_layout: " + x_axis_layout + ",   Y_axis_layout: " + y_axis_layout+"]";
	}
    /////////////////////////////////////
	
	// Print Data
	public void printData() {
		System.out.println("Building Name : "+building_name+"\n"
						  +"X_axis_layout : "+x_axis_layout+"\n"
						  +"Y_axis_layout : "+y_axis_layout);
	}
    /////////////////////////////////////
	
	// Create Building Button
	public Button createBuildingButton() {
		Button button = new Button();

		button.setText(getBuilding_name());
		button.setLayoutX(getX_axis_layout());
		button.setLayoutY(getY_axis_layout());

		return button;
	}
    
	
	
}
