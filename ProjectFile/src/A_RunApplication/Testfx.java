package A_RunApplication;
	
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import B_Graph.*;
import C_DijkstraAlgorithm.Dijkstra;
import D1_StroreData_LinkedList.LinkedList;
import D2_StoreData_Queue.Queue;
import D3_StoreData_MinHeap.MinHeap;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;


public class Testfx extends Application {
	
	// for fx
	Stage stage_fx=new Stage();
	DropShadow shadow = new DropShadow();
	ComboBox<String> cb_Source= new ComboBox<>();;
	ComboBox<String> cb_Destination= new ComboBox<>();;
	
	Building arr_Building[];
	Route arr_Route[];
	Dijkstra arr_dijkstraTable[];
	Queue<Route> queue_chossenPointRouteList=new Queue<>();
	MinHeap<String> minHeap_distance=new MinHeap<>();
	LinkedList<String> linkedlist_visitedBuilding=new LinkedList<>(); 
	LinkedList<String> linkedlist_shortestPath=new LinkedList<>();
	
	ObservableList<String> ol_building=FXCollections.observableArrayList();
	ObservableList<Route> init_ol_Route = FXCollections.observableArrayList();
	
	ObservableList<Button> ol_button_building = FXCollections.observableArrayList();
	ObservableList<Line> ol_line=FXCollections.observableArrayList();
	
	@Override
	public void start(Stage primaryStage) {
		try {
			
			// Read Data
			File file=new File("src\\D4_StoreData_FileData\\BuildingData.txt");
			readFile(file);
			
			if(file.exists()) {
				//page
				Scene scene = new Scene(MapPage());
				
				
				// stage
				stage_fx.setMaximized(true);
				stage_fx.setScene(scene);
				stage_fx.setTitle("BZU Map");
				stage_fx.setIconified(true);
				stage_fx.getIcons().add(new Image(getClass().getResourceAsStream("/E2_Graphic_icons/A1_Birzeit_Logo_Black.png")));
				//stage_fx.setFullScreen(true);
				
				stage_fx.show();
			}
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}
	
	
	
	//=== Page --------------------------------------------------------
	
	//-- Map Page
	public BorderPane MapPage() {
		BorderPane bp_mapPage = new BorderPane();
		
		
		//				 ======		Top		======
		//  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		HBox hb_Top=new HBox();
		/*
		 * Text(t1)
		 */
		
		//==================================
		
		Text t1=new Text("Birzeit University Map");
		t1.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 1.5; -fx-font-size: 40;-fx-font-weight: bold;");
		
		hb_Top.getChildren().add(t1);
		hb_Top.setPadding(new Insets(10, 0, 25, 550)); //top, right, bottom, left
		
		//  ************************************************************
		
		
		
		
		
		
		
		//		   	 ======		 Center	   	======
		//  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		HBox hb_center=new HBox(); // Contains vb_leftCenter+vb_rightCenter
		/*
		 * vb_leftCenter:  Image(image_map)+ImageView(imageView_map)
		 * 
		 * vb_rightCenter: GridPane(gp_rightCenter)<--
		 * 
		 * Text(t_source)+ComboBox<String>(cb_source)
		 * Text(t_target)+ComboBox<String>(cb_target)
		 * 
		 */
		
		//==================================
		
		//Left Center
		Pane pane_leftCenter = new Pane();
		pane_leftCenter.setPrefSize(500, 300);
		pane_leftCenter.setPadding(new Insets(20, 900, 580, 200)); //top, right, bottom, left
		pane_leftCenter.setStyle("-fx-background-image: url('" + getClass().getResource("/E3_Graphic_images/B_BirzeitUniversity_Map.png") + "'); -fx-background-size:cover,auto; -fx-border-color: #596558;");
		pane_leftCenter.getChildren().addAll(ol_button_building);
				
		
		//-------------
		
		//Right Center
		VBox vb_rightCenter=new VBox();
		//***
		HBox hb_Top_rightCenter=new HBox();
		
		//**********************************
		VBox vb_chossePoints_icon=new VBox();
		// circle
		ImageView circle_1 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_source_beside_combobox1.png") ));
		ImageView circle_2 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_source_beside_combobox2.png") ));
		// point
		ImageView point_1_1 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_point1.png") ));
		ImageView point_1_2 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_point1.png") ));
		ImageView point_1_3 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_point1.png") ));
		
		ImageView point_2_1 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_point2.png") ));
		ImageView point_2_2 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_point2.png") ));
		ImageView point_2_3 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_point2.png") ));
		
		VBox vb_point=new VBox();
		vb_point.getChildren().addAll(point_1_1,point_1_2,point_1_3);
		vb_point.setPadding(new Insets(5.2, 0, 7, 7.7)); //top, right, bottom, left
		// destination
		ImageView destination_1 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_destination1.png") ));
		ImageView destination_2 = new ImageView(new Image( getClass().getResourceAsStream("/E2_Graphic_icons/B_destination2.png") ));
		
		vb_chossePoints_icon.getChildren().addAll(circle_1,vb_point,destination_1);
		//**********************************
		VBox vb_chossePoints_cb=new VBox();
		// combobox source building
		cb_Source.setStyle("-fx-background-radius: 17; -fx-background-color: white; -fx-font-family: Comic Sans MS; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: black; -fx-border-color: #596558; -fx-border-width: 4; -fx-border-radius: 11;");
		cb_Source.setPrefSize(200, 50);
		cb_Source.setPromptText("Source Point ...");
		cb_Source.setItems(ol_building);
		
		// combobox destination building
		cb_Destination.setStyle("-fx-background-radius: 17; -fx-background-color: white; -fx-font-family: Comic Sans MS; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: black; -fx-border-color: #596558; -fx-border-width: 4; -fx-border-radius: 11;");
		cb_Destination.setPrefSize(200, 50);
		cb_Destination.setPromptText("Destination Point ...");
		cb_Destination.setItems(ol_building);
		
		
		
		
		
		
		vb_chossePoints_cb.getChildren().addAll(cb_Source,cb_Destination);
		
		vb_chossePoints_cb.setSpacing(8);
		vb_chossePoints_cb.setPadding(new Insets(9, 0, 0, 0)); //top, right, bottom, left
		
		
	
		//==================================
		hb_Top_rightCenter.getChildren().addAll(vb_chossePoints_icon,vb_chossePoints_cb);
		hb_Top_rightCenter.setSpacing(15);
		//  **********************************************************
		
		// bottom right center
		VBox vb_Bottom_rightCenter=new VBox();
		
		Button b_run=new Button("Run");
		b_run.setPrefSize(210, 50);
		b_run.setStyle("-fx-background-radius: 35; -fx-font-family: Comic Sans MS; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #596558; -fx-border-color: #596558; -fx-border-width: 4; -fx-border-radius: 35;");
		
		Button b_clear=new Button("Clear");
		b_clear.setPrefSize(210, 50);
		b_clear.setStyle("-fx-background-radius: 35; -fx-font-family: Comic Sans MS; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #596558; -fx-border-color: #596558; -fx-border-width: 4; -fx-border-radius: 35;");
		
		vb_Bottom_rightCenter.setSpacing(13);
		vb_Bottom_rightCenter.setPadding(new Insets(230, 0, 0, 37)); //top, right, bottom, left
		vb_Bottom_rightCenter.getChildren().addAll(b_run,b_clear);
		
		
		//*************************************************************************************************************
		vb_rightCenter.getChildren().addAll(hb_Top_rightCenter,vb_Bottom_rightCenter);
		vb_rightCenter.setPadding(new Insets(70, 0, 3, 30)); //top, right, bottom, left
		
		
		
		hb_center.getChildren().addAll(pane_leftCenter,vb_rightCenter);
		hb_center.setPadding(new Insets(0, 0, 0, 20)); //top, right, bottom, left
		vb_rightCenter.setPrefSize(370, 0);
		hb_center.setSpacing(20);
		vb_rightCenter.setStyle("-fx-border-color: #C0C0C0; -fx-border-width: 4; -fx-border-radius: 8; -fx-background-radius: 10;");
		
		//  **********************************************************
		
		
		
		
		
		
		
		//		    ======		Bottom		======
		//  ^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
		VBox vb_bottom=new VBox(); // Contains ScrollPane(sp_buttom)
		/* 
		 * GridPane(gp_bottom)
		 */
		
		//==================================
		ScrollPane sp_buttom;
		
		GridPane gp_bottom=new GridPane();
		
		Text t_path=new Text("Path         : ");
		t_path.setStyle("-fx-fill: #596558; -fx-font-size: 20;-fx-font-weight: bold;");
		Text sol_path=new Text("");
		sol_path.setStyle("-fx-fill: #008000; -fx-font-size: 15;-fx-font-weight: bold;");
		
		Text t_distance=new Text("Distance  : ");
		t_distance.setStyle("-fx-fill: #596558; -fx-font-size: 20;-fx-font-weight: bold;");
		Text sol_distance=new Text();
		sol_distance.setStyle("-fx-fill: black; -fx-font-size: 15;-fx-font-weight: bold;");
		
		gp_bottom.add(t_path, 2, 1);
		gp_bottom.add(sol_path, 3, 1);
		
		gp_bottom.add(t_distance, 2, 2);
		gp_bottom.add(sol_distance, 3, 2);
		
		gp_bottom.setHgap(10);
		gp_bottom.setVgap(7);
		
		sp_buttom = new ScrollPane(gp_bottom);
		sp_buttom.setHbarPolicy(ScrollPane.ScrollBarPolicy.AS_NEEDED);
		sp_buttom.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
        
		sp_buttom.setStyle("-fx-border-color: #C0C0C0; -fx-border-width: 4; -fx-background-color: white; -fx-border-radius: 8; -fx-background-radius: 10;");
		sp_buttom.setPrefSize(200, 87);
		
		vb_bottom.getChildren().add(sp_buttom);
		vb_bottom.setPadding(new Insets(15, 415, 100, 20)); //top, right, bottom, left
		
		
		//gp_bottom.setAlignment(Pos.CENTER_LEFT);
		////////////////////////////////////////////////////////////////
		
		//-------------
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		// Listener for combobox
		
		cb_Source.valueProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue != null) {
		    	// for data
		        ObservableList<String> updatedList = FXCollections.observableArrayList(ol_building);
		        updatedList.remove(newValue);
		        cb_Destination.setItems(updatedList);
		        
		        
		        for (Button sourcebutton : ol_button_building) {
					if(sourcebutton.getText().equals(newValue)) {
						BorderPane bp_pane=new BorderPane();
						
						ImageView BuildingPoint_icon2 = new ImageView(new Image(getClass().getResourceAsStream("/E2_Graphic_icons/C_Buldingpoints2.png")));
						
						Text buildingText = new Text(sourcebutton.getText());
						buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
						
						if(sourcebutton.getText().equals("Univirsity_Library") || sourcebutton.getText().equals("Univirsity_Musuem")) {
							bp_pane.setLeft(buildingText);
							bp_pane.setCenter(BuildingPoint_icon2);
						}
						else if(sourcebutton.getText().equals("SouthGate") || sourcebutton.getText().equals("KHOURY") || sourcebutton.getText().equals("BAHRAIN") ||
								sourcebutton.getText().equals("MASROUJI") || sourcebutton.getText().equals("Palestine_Museum")) {
							bp_pane.setLeft(buildingText);
							bp_pane.setRight(BuildingPoint_icon2);
						}
						else {
							bp_pane.setLeft(BuildingPoint_icon2);
							bp_pane.setRight(buildingText);
						}
						
						sourcebutton.setGraphic(bp_pane);
					}
					else if(!sourcebutton.getText().equals(cb_Destination.getValue())) {
						// set graphic to the button
						BorderPane bp_pane=new BorderPane();
						
						// Icon of this button
						ImageView BuildingPoint_icon1 = new ImageView(new Image(getClass().getResourceAsStream("/E2_Graphic_icons/C_Buldingpoints1.png")));

						
						// Name of this button
						Text buildingText = new Text(sourcebutton.getText());
						buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
						
						if(sourcebutton.getText().equals("Univirsity_Library") || sourcebutton.getText().equals("Univirsity_Musuem")) {
							bp_pane.setLeft(buildingText);
							bp_pane.setCenter(BuildingPoint_icon1);
						}
						else if(sourcebutton.getText().equals("SouthGate") || sourcebutton.getText().equals("KHOURY") || sourcebutton.getText().equals("BAHRAIN") ||
								sourcebutton.getText().equals("MASROUJI") || sourcebutton.getText().equals("Palestine_Museum")) {
							bp_pane.setLeft(buildingText);
							bp_pane.setRight(BuildingPoint_icon1);
						}
						else {
							bp_pane.setLeft(BuildingPoint_icon1);
							bp_pane.setRight(buildingText);
						}
						
						
						
						// event
						
						//- mouse entered
						sourcebutton.setOnMouseEntered(emouse->{
							buildingText.setStyle("-fx-fill: #000000; -fx-stroke: #FFFFFF; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
						});
						
						//- mouse exit
						sourcebutton.setOnMouseExited(exmouse->{
							buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
						});
						
						
						
						
						sourcebutton.setGraphic(bp_pane);
					}
				}
		        
		    }
		    
		});

		cb_Destination.valueProperty().addListener((observable, oldValue, newValue) -> {
		    if (newValue != null) {
		        ObservableList<String> updatedList = FXCollections.observableArrayList(ol_building);
		        updatedList.remove(newValue);
		        cb_Source.setItems(updatedList);
		        
		        
		        for (Button destinationbutton : ol_button_building) {
		        	
					if(destinationbutton.getText().equals(newValue)) {
						BorderPane bp_pane=new BorderPane();
						
						ImageView BuildingPoint_destination = new ImageView(new Image(getClass().getResourceAsStream("/E2_Graphic_icons/B_destination2.png")));
						
						Text buildingText = new Text(destinationbutton.getText());
						buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
						
							if(destinationbutton.getText().equals("Univirsity_Library") || destinationbutton.getText().equals("Univirsity_Musuem")) {
								bp_pane.setLeft(buildingText);
								bp_pane.setCenter(BuildingPoint_destination);
							}
							else if(destinationbutton.getText().equals("SouthGate") || destinationbutton.getText().equals("KHOURY") || destinationbutton.getText().equals("BAHRAIN") ||
									destinationbutton.getText().equals("MASROUJI") || destinationbutton.getText().equals("Palestine_Museum")) {
								bp_pane.setLeft(buildingText);
								bp_pane.setRight(BuildingPoint_destination);
							}
							else {
								bp_pane.setLeft(BuildingPoint_destination);
								bp_pane.setRight(buildingText);
							}
							
							destinationbutton.setGraphic(bp_pane);

					}
					else if(!destinationbutton.getText().equals(cb_Source.getValue())) {
						// set graphic to the button
						BorderPane bp_pane=new BorderPane();
						
						// Icon of this button
						ImageView BuildingPoint_icon1 = new ImageView(new Image(getClass().getResourceAsStream("/E2_Graphic_icons/C_Buldingpoints1.png")));

						
						// Name of this button
						Text buildingText = new Text(destinationbutton.getText());
						buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
						
						
						
						if(destinationbutton.getText().equals("Univirsity_Library") || destinationbutton.getText().equals("Univirsity_Musuem")) {
							bp_pane.setLeft(buildingText);
							bp_pane.setCenter(BuildingPoint_icon1);
						}
						else if(destinationbutton.getText().equals("SouthGate") || destinationbutton.getText().equals("KHOURY") || destinationbutton.getText().equals("BAHRAIN") ||
								destinationbutton.getText().equals("MASROUJI") || destinationbutton.getText().equals("Palestine_Museum")) {
							bp_pane.setLeft(buildingText);
							bp_pane.setRight(BuildingPoint_icon1);
						}
						else {
							bp_pane.setLeft(BuildingPoint_icon1);
							bp_pane.setRight(buildingText);
						}
						
						
						
						// event
						
						//- mouse entered
						destinationbutton.setOnMouseEntered(emouse->{
							buildingText.setStyle("-fx-fill: #000000; -fx-stroke: #FFFFFF; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
						});
						
						//- mouse exit
						destinationbutton.setOnMouseExited(exmouse->{
							buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
						});
						
						
						
						
						destinationbutton.setGraphic(bp_pane);
					}
				}
		    }
		    
		});
		
		ChangeListener<String> comboBoxListener = (observable, oldValue, newValue) -> {
			
			if(cb_Source.getValue() != null  &&  cb_Destination.getValue() == null) {
				// for ui
				vb_point.getChildren().clear();
				vb_point.getChildren().addAll(point_2_1,point_2_2,point_1_3);
				
		        vb_chossePoints_icon.getChildren().clear();
		        vb_chossePoints_icon.getChildren().addAll(circle_2,vb_point,destination_1);
			}
			
			
		    if (cb_Source.getValue() != null && cb_Destination.getValue() != null) {
		    	
		    	//vb top
		    	vb_point.getChildren().clear();
				vb_point.getChildren().addAll(point_2_1,point_2_2,point_2_3);
				
		        vb_chossePoints_icon.getChildren().clear();
		        vb_chossePoints_icon.getChildren().addAll(circle_2,vb_point,destination_2);
		        
		       
		        
		        hb_Top_rightCenter.getChildren().clear();
		        hb_Top_rightCenter.getChildren().addAll(vb_chossePoints_icon,vb_chossePoints_cb);
		        
		        
		        
		        // vb bottom
		        b_run.setStyle("-fx-background-radius: 35; -fx-font-family: Comic Sans MS; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: black; -fx-border-color: #FEFF00; -fx-border-width: 4; -fx-border-radius: 35;");
				b_run.setOnAction(runapp->{
					
					
					
					String source=cb_Source.getSelectionModel().getSelectedItem();
					String destination=cb_Destination.getSelectionModel().getSelectedItem();
					
					
					runDijkstraAlgorithm(source, destination);
					
					t_path.setStyle("-fx-fill: black; -fx-font-size: 20;-fx-font-weight: bold;");
					sol_path.setText(shortestPath(cb_Source.getValue(), cb_Destination.getValue()));
					
					t_distance.setStyle("-fx-fill: black; -fx-font-size: 20;-fx-font-weight: bold;");
					sol_distance.setText(""+shortestDistance(destination));
					
					sp_buttom.setStyle("-fx-border-color: #FEFF00; -fx-border-width: 4; -fx-background-color: white; -fx-border-radius: 8; -fx-background-radius: 10;");
					
					b_clear.setStyle("-fx-background-radius: 35; -fx-font-family: Comic Sans MS; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: black; -fx-border-color: #FEFF00; -fx-border-width: 4; -fx-border-radius: 35;");
					
					b_run.setStyle("-fx-background-radius: 35; -fx-font-family: Comic Sans MS; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #596558; -fx-border-color: #596558; -fx-border-width: 4; -fx-border-radius: 35;");
					
					setline(pane_leftCenter,cb_Destination.getValue());
					
				});
		    }
		};

		
		cb_Source.valueProperty().addListener(comboBoxListener);
		cb_Destination.valueProperty().addListener(comboBoxListener);
		
		b_clear.setOnAction(clear->{
			
			
	        queue_chossenPointRouteList.clear();
	        minHeap_distance.clear();
	        linkedlist_visitedBuilding.clear();
	        linkedlist_shortestPath.clear();
			
			// combobox
			cb_Source.getSelectionModel().clearSelection();
			cb_Source.setValue(null);
			cb_Destination.getSelectionModel().clearSelection();
			cb_Destination.setValue(null);
			
			cb_Source.setItems(ol_building);
			cb_Destination.setItems(ol_building);
			
			//vb top
	    	vb_point.getChildren().clear();
			vb_point.getChildren().addAll(point_1_1,point_1_2,point_1_3);
			
	        vb_chossePoints_icon.getChildren().clear();
	        vb_chossePoints_icon.getChildren().addAll(circle_1,vb_point,destination_1);
	        
	        hb_Top_rightCenter.getChildren().clear();
	        hb_Top_rightCenter.getChildren().addAll(vb_chossePoints_icon,vb_chossePoints_cb);
	        
	        b_run.setStyle("-fx-background-radius: 35; -fx-font-family: Comic Sans MS; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #596558; -fx-border-color: #596558; -fx-border-width: 4; -fx-border-radius: 35;");
	        b_clear.setStyle("-fx-background-radius: 35; -fx-font-family: Comic Sans MS; -fx-font-size: 14; -fx-font-weight: bold; -fx-text-fill: #596558; -fx-border-color: #596558; -fx-border-width: 4; -fx-border-radius: 35;");
			
	        
	        
	        
	        // bottom
	        t_path.setStyle("-fx-fill: #596558; -fx-font-size: 20;-fx-font-weight: bold;");
	        sol_path.setText("");
	        t_distance.setStyle("-fx-fill: #596558; -fx-font-size: 20;-fx-font-weight: bold;");
	        sol_distance.setText("");
	        sp_buttom.setStyle("-fx-border-color: #C0C0C0; -fx-border-width: 4; -fx-background-color: white; -fx-border-radius: 8; -fx-background-radius: 10;");
			
	        
	        clearSelectedBuildingOnScreen(pane_leftCenter);
	        
	        
		});
		
		
		
		//-------------




		
		
		
		
		
		bp_mapPage.setTop(hb_Top);
		bp_mapPage.setCenter(hb_center);
		bp_mapPage.setBottom(vb_bottom);
		
		bp_mapPage.setStyle("-fx-background-image: url('" + getClass().getResource("/E3_Graphic_images/A_Background.png") + "');");
		return bp_mapPage;
	}
	
	
	
	//=== Method ------------------------------------------------------
	
	// Data
	
	//-- Read Data from file
	public void readFile(File file) {
		
		// check if the file exist
		if(!file.exists()) {
			System.out.println("there isn't data file !!!");
		}
		else {
			// declare required variable. 
			int num_of_bulding;
			int num_of_route;
			
			
			try {
				// get data thats inside the file.
				Scanner in = new Scanner(file);

				String s_insideLine[] = null;
				int index_building=0;
				int index_route=0;
				boolean readBuilding=false;
				boolean readRoute=false;
				
				//////////////////////////////////
				// i, for the line.
				// index_building, to declare arr_Building.
				// index_route, to declare arr_Route.
				// readBuilding, to start read building data.
				// readRoute, to start read route data.
				
				for(int i=0; in.hasNextLine(); i++) {
					
					String line = in.nextLine();
					s_insideLine=line.split(",");
					
					if(s_insideLine.length>0) {
							// first line
							if(i==0) {
								num_of_bulding=Integer.parseInt(s_insideLine[0]);
								num_of_route=Integer.parseInt(s_insideLine[1]);
								arr_Building=new Building[num_of_bulding];
								arr_Route=new Route[num_of_route];
								arr_dijkstraTable=new Dijkstra[arr_Building.length];
								readBuilding=true; // to start read building data
							}
							// rest lines
							else if(!line.equals("")) {
								// to stop read building data, and start read route data.
								if(line.equals("...")) {
									readBuilding=false;
									readRoute=true;
								}
								// read building data
								else if(readBuilding==true) {
									// Initialize arr_Building and set data inside it.
									arr_Building[index_building]=new Building(null, 0, 0);
									
									arr_Building[index_building].setBuilding_name(s_insideLine[0]);
									arr_Building[index_building].setX_axis_layout(Double.parseDouble(s_insideLine[1]));
									arr_Building[index_building].setY_axis_layout(Double.parseDouble(s_insideLine[2]));
									
									
									// Initialize arr_dijkstraTable and set data inside it.
									arr_dijkstraTable[index_building]=new Dijkstra(null, Double.MAX_VALUE, "-");
									
									arr_dijkstraTable[index_building].setCurr_Building(s_insideLine[0]);
									
									
									// Insert the building on the screen.
									insertBuildingOnScreen(arr_Building[index_building]);
									
									// Insert building to observable
									ol_building.add(arr_Building[index_building].getBuilding_name());
									
									index_building++;
								}
								// read route data
								else if(readRoute==true) {
									// Initialize arr_Route
									arr_Route[index_route]=new Route(null, null, 0);
									
									arr_Route[index_route].setBuilding_point1(s_insideLine[0]);
									arr_Route[index_route].setBuilding_point2(s_insideLine[1]);
									if(Double.parseDouble(s_insideLine[2])>=0) {
										arr_Route[index_route].setDistance(Double.parseDouble(s_insideLine[2]));
										init_ol_Route.add(arr_Route[index_route]);
									}
									else {
										System.out.println("negative edge in line: "+(i+1)+" !!!");
										in.close();
										break;
									}
									
									
									index_route++;
								}
							}
						}
					}
				in.close();
			}
			catch(Exception e) {
				e.getMessage();
			}
			
			
			
		}
		
		
	}		

	//-- Run Dijkstra Algorithm
	public void runDijkstraAlgorithm(String source, String destination) {
		
		String switch_building="";
		String chossenPoint=source;
		double prev_distance=0;
		
			//********************* initialize the source Building in Dijkstra Table and route array *********************
		
				for(int i=0;i<arr_dijkstraTable.length;i++) {
					// if we find the curr_Building (dijkstraTable array), that have the same name with source.
					if(arr_dijkstraTable[i].getCurr_Building().equals(source)) {
						//set the shortest distance zero to the source.
						arr_dijkstraTable[i].setShortestDistance(prev_distance);
					}
					else {
						arr_dijkstraTable[i].setShortestDistance(Double.MAX_VALUE);
						arr_dijkstraTable[i].setPrev_Building("-");
					}
				}
				
				
				
				for(int i=0;i<init_ol_Route.size();i++) {
					arr_Route[i]=init_ol_Route.get(i);
				}
			//********************* set cursor position for (source and destination) *********************
				
				for(int i=0;i<arr_Route.length;i++) {
					
					//-1- inverse the cursor for source(if any point that reference to source) ...
					// if we find the building_point2 (route array), that have the same name with source.
					if(arr_Route[i].getBuilding_point2().equals(source)) {
						// take the building_point2 and switch it with source building.
						switch_building = arr_Route[i].getBuilding_point1();
						
						arr_Route[i].setBuilding_point1(source);
						arr_Route[i].setBuilding_point2(switch_building);
					}
					
					//--------------------------------
					
					//-2- inverse the cursor for destination(if the destination reference to any point) ...
					// if we find the building_point1 (route array), that have the same name with destination.
					if(arr_Route[i].getBuilding_point1().equals(destination)) {
						// take the building_point1 and switch it with destination building.
						switch_building = arr_Route[i].getBuilding_point2();
						
						arr_Route[i].setBuilding_point1(switch_building);
						arr_Route[i].setBuilding_point2(destination);
					}
				}
			
			//************************************ set Dijkstra Table ************************************
				
				//insert source to the heap.
				minHeap_distance.insert(chossenPoint, prev_distance);
				
				// while the heap is not empty do below steps.
				while(!minHeap_distance.isEmpty()) {
					//-1- enqueue all the routes that chossenPoint Building can go to it on (queue_chossenPointRouteList).
					for(int i=0;i<arr_Route.length;i++) {
						// if cursor reference in correct position and the next building dosen't visited then --> enqueue it.
						if(arr_Route[i].getBuilding_point1().equals(chossenPoint) && (!linkedlist_visitedBuilding.search(arr_Route[i].getBuilding_point2()))) {
							queue_chossenPointRouteList.enqueue(arr_Route[i]);
						}
						// else if cursor reference in false position and the next building dosen't visited then --> fix cursor then enqueue it.
						else if(arr_Route[i].getBuilding_point2().equals(chossenPoint) && (!linkedlist_visitedBuilding.search(arr_Route[i].getBuilding_point1()))) {
							// fix cursor: take the building_point2 and switch it with chossenPoint building.
							switch_building = arr_Route[i].getBuilding_point1();
							
							arr_Route[i].setBuilding_point1(chossenPoint);
							arr_Route[i].setBuilding_point2(switch_building);
							
							// enqueue it.
							queue_chossenPointRouteList.enqueue(arr_Route[i]);
						}
					}
					
					//--------------------------------
					
					//-2- set data after processing inside Dijkstra table.
					/*
					 * processing steps:
					 * 
					 * 1) compare the distance between:
					 * [curr_Building (dijkstraTable array)] and [front of queue(that we get it from route table) + previous distance(between point1 and point 2)]
					 * 
					 * 2) after data updated inside table (shortest distance and previous building) then --> :
					 *                                           if this data do not exist in minHeap insert it.
					 *                                           if this data exist in minHeap just the priority will updated. 
					 *                                       
					 */
					
					int queue_length=queue_chossenPointRouteList.length();
					
					for(int i=0;i<queue_length;i++) {
						for(int j=0;j<arr_dijkstraTable.length;j++) {
							if(arr_dijkstraTable[j].getCurr_Building().equals( (queue_chossenPointRouteList.getFront().getBuilding_point2()) )) {
								if(arr_dijkstraTable[j].getShortestDistance() > (queue_chossenPointRouteList.getFront().getDistance() + prev_distance) ) {
								arr_dijkstraTable[j].setShortestDistance((queue_chossenPointRouteList.getFront().getDistance() + prev_distance));
								arr_dijkstraTable[j].setPrev_Building(queue_chossenPointRouteList.getFront().getBuilding_point1());
									if(!minHeap_distance.find(queue_chossenPointRouteList.getFront().getBuilding_point2())) {
										minHeap_distance.insert(queue_chossenPointRouteList.getFront().getBuilding_point2(), arr_dijkstraTable[j].getShortestDistance());
									}
									else if(minHeap_distance.find(queue_chossenPointRouteList.getFront().getBuilding_point2())) {
										minHeap_distance.updatePriority(queue_chossenPointRouteList.getFront().getBuilding_point2(), arr_dijkstraTable[j].getShortestDistance());
									}
								}
							}
						}
						queue_chossenPointRouteList.dequeue();
					}
					
					//--------------------------------
					//-3- set the next point we should be in it
					
					// insert the high priority to visited Building
					linkedlist_visitedBuilding.insertAtHead(chossenPoint);
					
					// extract(remove) the high priority
					minHeap_distance.extractMin();
					
					// Choose next priority node
					chossenPoint=minHeap_distance.getMin();
					
					// if this node who was chosen is the destination then break
					if(chossenPoint.equals(destination)) {
						break;
					}
					// else find the previous shortest distance from Dijkstra table
					else if(!chossenPoint.equals(destination)) {
						for (int j = 0; j < arr_dijkstraTable.length; j++) {
							// if currentBuilding (dijkstraTable array) equal the chossenPoint take it's shortest distance.
							if (arr_dijkstraTable[j].getCurr_Building().equals(chossenPoint)) {
								prev_distance = arr_dijkstraTable[j].getShortestDistance();
								break;
							}
						}
					}
					
				}
	}
	
	//-- Shortest Path
	public String shortestPath(String source, String destination) {
		// back tracking from destination till source
		for(int i=0;i<arr_dijkstraTable.length;i++) {
			for(int j=0;j<arr_dijkstraTable.length;j++) {
				
				if(arr_dijkstraTable[j].getCurr_Building().equals(destination)) {
					
					if(!linkedlist_shortestPath.search(arr_dijkstraTable[j].getCurr_Building())) {
						linkedlist_shortestPath.insertAtHead(arr_dijkstraTable[j].getCurr_Building());
					}
					
					
					destination=arr_dijkstraTable[j].getPrev_Building();
				}
				
			}
		}
		
		
		return linkedlist_shortestPath.toString();
	}
	
	//-- Shortest Distance
	public double shortestDistance(String destination) {
		double shortestDistance=0;
		
		for(int i=0;i<arr_dijkstraTable.length;i++) {
			if(arr_dijkstraTable[i].getCurr_Building().equals(destination)) {
				shortestDistance =arr_dijkstraTable[i].getShortestDistance();
				break;
			}
		}	
		
		return shortestDistance;
	}
	

	// UI
	
	//-- Insert building as a button on screen
	public void insertBuildingOnScreen(Building building) {
		
		// set graphic to the button
		BorderPane bp_pane=new BorderPane();
		
		// Icon of this button
		ImageView BuildingPoint_icon1 = new ImageView(new Image(getClass().getResourceAsStream("/E2_Graphic_icons/C_Buldingpoints1.png")));

		
		// Name of this button
		Text buildingText = new Text(building.getBuilding_name());
		buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
		
		
		
		
		if(building.getBuilding_name().equals("Univirsity_Library") || building.getBuilding_name().equals("Univirsity_Musuem")) {
			bp_pane.setLeft(buildingText);
			bp_pane.setCenter(BuildingPoint_icon1);
		}
		else if(building.getBuilding_name().equals("SouthGate") || building.getBuilding_name().equals("KHOURY") || building.getBuilding_name().equals("BAHRAIN") ||
				building.getBuilding_name().equals("MASROUJI") || building.getBuilding_name().equals("Palestine_Museum")) {
			bp_pane.setLeft(buildingText);
			bp_pane.setRight(BuildingPoint_icon1);
		}
		else {
			bp_pane.setLeft(BuildingPoint_icon1);
			bp_pane.setRight(buildingText);
		}
		
		//----------
		
		Button building_button = new Button(building.getBuilding_name());
		building_button.setStyle("-fx-background-color: transparent;-fx-text-fill: transparent;");
		building_button.setGraphic(bp_pane);
	
		// Set the position of the button
		building_button.setLayoutX(building.getX_axis_layout());
		building_button.setLayoutY(building.getY_axis_layout());
		
		// event
		
		//- mouse entered
		building_button.setOnMouseEntered(emouse->{
			buildingText.setStyle("-fx-fill: #000000; -fx-stroke: #FFFFFF; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
		});
		
		//- mouse exit
		building_button.setOnMouseExited(exmouse->{
			buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
		});
		
		building_button.setOnAction(e->{
			
			// for source
			if(cb_Source.getSelectionModel().isEmpty()) {
				ImageView BuildingPoint_icon2 = new ImageView(new Image(getClass().getResourceAsStream("/E2_Graphic_icons/C_Buldingpoints2.png")));
				
				if(building.getBuilding_name().equals("Univirsity_Library") || building.getBuilding_name().equals("Univirsity_Musuem")) {
					bp_pane.setLeft(buildingText);
					bp_pane.setCenter(BuildingPoint_icon2);
				}
				else if(building.getBuilding_name().equals("SouthGate") || building.getBuilding_name().equals("KHOURY")) {
					bp_pane.setLeft(buildingText);
					bp_pane.setRight(BuildingPoint_icon2);
				}
				else {
					bp_pane.setLeft(BuildingPoint_icon2);
					bp_pane.setRight(buildingText);
				}
				
				building_button.setGraphic(bp_pane);
				
				cb_Source.getSelectionModel().select(building_button.getText());
				
			}
			else if(cb_Destination.getSelectionModel().isEmpty()) {
				
				ImageView BuildingPoint_destination = new ImageView(new Image(getClass().getResourceAsStream("/E2_Graphic_icons/B_destination2.png")));
				
				if(!building.getBuilding_name().equals(cb_Source.getValue())) {
					if(building.getBuilding_name().equals("Univirsity_Library") || building.getBuilding_name().equals("Univirsity_Musuem")) {
						bp_pane.setLeft(buildingText);
						bp_pane.setCenter(BuildingPoint_destination);
					}
					else if(building.getBuilding_name().equals("SouthGate")) {
						bp_pane.setLeft(buildingText);
						bp_pane.setRight(BuildingPoint_destination);
					}
					else {
						bp_pane.setLeft(BuildingPoint_destination);
						bp_pane.setRight(buildingText);
					}
					
					building_button.setGraphic(bp_pane);
					
					cb_Destination.getSelectionModel().select(building_button.getText());
				}
				
			}
			
			
			
			
		});
		
		ol_button_building.add(building_button);
	}
	
	//-- clear selected building on screen
	public void clearSelectedBuildingOnScreen(Pane pane_leftCenter) {

		pane_leftCenter.getChildren().clear();
		
		
		
		for (Button building_button : ol_button_building) {
			// set graphic to the button
			BorderPane bp_pane=new BorderPane();
			
			// Icon of this button
			ImageView BuildingPoint_icon1 = new ImageView(new Image(getClass().getResourceAsStream("/E2_Graphic_icons/C_Buldingpoints1.png")));

			
			// Name of this button
			Text buildingText = new Text(building_button.getText());
			buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
			
			if(building_button.getText().equals("Univirsity_Library") || building_button.getText().equals("Univirsity_Musuem")) {
				bp_pane.setLeft(buildingText);
				bp_pane.setCenter(BuildingPoint_icon1);
			}
			else if(building_button.getText().equals("SouthGate")) {
				bp_pane.setLeft(buildingText);
				bp_pane.setRight(BuildingPoint_icon1);
			}
			else {
				bp_pane.setLeft(BuildingPoint_icon1);
				bp_pane.setRight(buildingText);
			}
			
			
			
			// event
			
			//- mouse entered
			building_button.setOnMouseEntered(emouse->{
				buildingText.setStyle("-fx-fill: #000000; -fx-stroke: #FFFFFF; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
			});
			
			//- mouse exit
			building_button.setOnMouseExited(exmouse->{
				buildingText.setStyle("-fx-fill: #FFFFFF; -fx-stroke: #000000; -fx-stroke-width: 0.5; -fx-font-size: 13;-fx-font-weight: bold;");
			});
			
			
			
			
			building_button.setGraphic(bp_pane);
		}
		
		
		
		pane_leftCenter.getChildren().addAll(ol_button_building);
	}

	// setlines
	public void setline(Pane pane_leftCenter,String destination) {
	    Button b1 = null;
	    Button b2 = null;
	    ol_line.clear(); // Clear the list before adding new lines

	    while (!linkedlist_shortestPath.isEmpty()) {
	    	String value = linkedlist_shortestPath.removeFirst();

	        for (Button buttonBuilding : ol_button_building) {
	            if (buttonBuilding.getText().equals(value)) {

	            	if(b1==null) {
	            		b1 = buttonBuilding;
	            	}
	            	else if(b2==null && b1!=null) {
	            		b2 = buttonBuilding;
	            		ol_line.add(createLine(b1, b2));
	            		
	            		if(b2.getText().equals(destination)) {
	            			break;
	            		}
	            		else {
	            			b1 = b2;
		                    b2 = null;
	            		}
	            	}
	            	
	            }
	        }
	    }

	    pane_leftCenter.getChildren().addAll(ol_line); // Add lines to the parent
	}

	//-- create Line
	public Line createLine(Button button1, Button button2) {
		Line line=new Line();
		line.setStroke(Color.RED);
	    line.setStrokeWidth(2);
	    
        line.startXProperty().bind(button1.layoutXProperty().add(button1.widthProperty().divide(4)));
        line.startYProperty().bind(button1.layoutYProperty().add(button1.heightProperty().divide(2)));
        
        line.endXProperty().bind(button2.layoutXProperty().add(button2.widthProperty().divide(4)));
        line.endYProperty().bind(button2.layoutYProperty().add(button2.heightProperty().divide(2)));
        
        return line;
    }
	
	
	//==================================================================
	
	public static void main(String[] args) {
		launch(args);
	}
}
