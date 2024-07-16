package F_TestApplication;

import java.io.File;
import java.util.Scanner;

import B_Graph.Building;
import B_Graph.Route;
import C_DijkstraAlgorithm.Dijkstra;
import D1_StroreData_LinkedList.LinkedList;
import D2_StoreData_Queue.Queue;
import D3_StoreData_MinHeap.MinHeap;

public class B_DijkstraOperation {
	static Building arr_Building[];
	static Route arr_Route[];
	static Dijkstra arr_dijkstraTable[];
	static Queue queue_building=new Queue<>();
	static MinHeap<String> minHeap_distance=new MinHeap<>();
	
	public static void main(String[] args) {
		File file=new File("src\\D4_StoreData_FileData\\Data.txt");
		
		readFile(file);
		
		
		String source="A";
		String destination="C";
		
		double prev_distance=0;
		
		
		/////////////////////////////////////////////////////////////////////////////////
		
		printRouteData();
		System.out.println("---------------------------------------------------"+"\n");
		
		// set source point
		printDijkstraTable();
		queue_building.printData();
		System.out.println("---------------------------------------------------"+"\n");
		System.out.println("Set source point");
		
		for (int i = 0; i < arr_dijkstraTable.length; i++) {
			// if we find the index that have the same name with source do the operation then stop.
			if (arr_dijkstraTable[i].getCurr_Building().equals(source)) {
				// if the source not from the first building on graph.
				if (i != 0) {
					
					System.out.println("switch between, source:"+source+" and the first index:"+arr_dijkstraTable[0].getCurr_Building());
					
					// take the first building on graph and switch it with source building.
					String switch_building = arr_dijkstraTable[0].getCurr_Building();
					arr_dijkstraTable[i].setCurr_Building(switch_building);
					arr_dijkstraTable[0].setCurr_Building(source);

					// clear queue and restore the new data pattern inside it.
					queue_building.clear();
					for (int j = 0; j < arr_dijkstraTable.length; j++) {
						queue_building.enqueue(arr_dijkstraTable[j].getCurr_Building());
					}
				}
				//set the distance zero to the source.
				arr_dijkstraTable[0].setShortestDistance(prev_distance);
				break;
			}
		}
		
		printDijkstraTable();
		
		queue_building.printData();
		System.out.println("---------------------------------------------------"+"\n");
		
		//////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
//		 Set other point
		for(int i=0;i<arr_Route.length;i++) {
			if(!queue_building.getFront().equals(arr_Route[i].getBuilding_point1())) {
				queue_building.dequeue();
				for(int k=0;k<arr_dijkstraTable.length;k++) {
					if(arr_dijkstraTable[k].getCurr_Building().equals(queue_building.getFront())) {
						prev_distance=arr_dijkstraTable[k].getShortestDistance();
					}
				}
			}
			
			if(queue_building.getFront().equals(arr_Route[i].getBuilding_point1())) {
				System.out.println(arr_Route[i].getBuilding_point1()+": "+"\n\n");
				for(int j=0;j<arr_dijkstraTable.length;j++) {
					if(arr_dijkstraTable[j].getCurr_Building().equals(arr_Route[i].getBuilding_point2())) {
						
						System.out.println(arr_Route[i].getBuilding_point1()+" to "+arr_dijkstraTable[j].getCurr_Building()+", distance cost = "+arr_Route[i].getDistance()+": "+"\n");
						if(arr_dijkstraTable[j].getShortestDistance()==Double.MAX_VALUE) {
							arr_dijkstraTable[j].setShortestDistance(arr_Route[i].getDistance()+prev_distance);
							arr_dijkstraTable[j].setPrev_Building(arr_Route[i].getBuilding_point1());
						}
						else if(arr_dijkstraTable[j].getShortestDistance()> (arr_Route[i].getDistance() + prev_distance) ) {
							arr_dijkstraTable[j].setShortestDistance(arr_Route[i].getDistance()+prev_distance);
							arr_dijkstraTable[j].setPrev_Building(arr_Route[i].getBuilding_point1());
							
						}
					}
				}
			}
			printDijkstraTable();
			queue_building.printData();
			System.out.println("---------------------------------------------------"+"\n");
		}
		
		/////////////////////////////////////////////////////////////////////////////////
		
		
		
		
		
		

	}
		
		
	
	
	
	public static void readFile(File file) {
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
								
								
								// enqueue the data inside queue
								queue_building.enqueue(arr_Building[index_building].getBuilding_name());
								
								index_building++;
							}
							// read route data
							else if(readRoute==true) {
								// Initialize arr_Route
								arr_Route[index_route]=new Route(null, null, 0);
								
								arr_Route[index_route].setBuilding_point1(s_insideLine[0]);
								arr_Route[index_route].setBuilding_point2(s_insideLine[1]);
								arr_Route[index_route].setDistance(Double.parseDouble(s_insideLine[2]));
								
								index_route++;
							}
						}
					}
				}
			in.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void printDijkstraTable() {
		System.out.println("Dijkstra Table: "+"\n");
		
		System.out.println("current Building"+"        "+"shortest distance"+"           "+"previous Building");
		for(int i=0;i<arr_dijkstraTable.length;i++) {
			System.out.println("      "+arr_dijkstraTable[i].toString());
		}
	}
	
	public static void printRouteData() {
		System.out.println("Route: "+"\n");
		for(int i=0;i<arr_Route.length;i++) {
			System.out.println("Route("+(i+1)+"): "+arr_Route[i].toString());
		}
	}
	
	public static void runDijkstraAlgorithm(String source, String destination) {
		
		double prev_distance=0;
		String switch_building="";
		
			//********************* set source point *********************

			for (int i = 0; i < arr_dijkstraTable.length; i++) {
				// if we find the currentBuilding (dijkstraTable array), that have the same name with source, then do these steps then stop.
				if (arr_dijkstraTable[i].getCurr_Building().equals(source)) {
					// if the source not the first building on graph.
					if (i != 0) {
						
						// take the first building on graph and switch it with source building.
						switch_building = arr_dijkstraTable[0].getCurr_Building(); // first building on graph
						arr_dijkstraTable[i].setCurr_Building(switch_building);
						arr_dijkstraTable[0].setCurr_Building(source);
	
						// clear queue and restore the new building data inside it.
						queue_building.clear();
						for (int j = 0; j < arr_dijkstraTable.length; j++) {
							queue_building.enqueue(arr_dijkstraTable[j].getCurr_Building());
						}
					}
					
					//set the distance zero to the source.
					arr_dijkstraTable[0].setShortestDistance(prev_distance);
					break;
				}
			}
		
		
			
			
			
			
			//********************* set other point *********************
			
			for (int i = 0; i < arr_Route.length; i++) {
				// if building_point1 (route array) dosen't equal the front on queue (when we finish the routes for building_point1 (route array)).
				if (!arr_Route[i].getBuilding_point1().equals(queue_building.getFront())) {
					// dequeue the building from queue (like we make it as visited)
					queue_building.dequeue();
					
					// so now we want to get previous shortest distance
					for (int j = 0; j < arr_dijkstraTable.length; j++) {
						// if currentBuilding (dijkstraTable array) equal the front on queue take it's shortest distance.
						if (arr_dijkstraTable[j].getCurr_Building().equals(queue_building.getFront())) {
							prev_distance = arr_dijkstraTable[j].getShortestDistance();
							break;
						}
					}
				}
				
				
				// if building_point1 (route array) equal the front on queue.
				if (arr_Route[i].getBuilding_point1().equals(queue_building.getFront())) {
					for (int j = 0; j < arr_dijkstraTable.length; j++) {
						// if there is switch between source and the first currentBuilding (dijkstraTable array).
						if (!switch_building.equals("")) {
							
						}
						// else there is no switch.
						else {
							// if building_point2 (route array) equal the currentBuilding (dijkstraTable array), then do these steps.
							if (arr_Route[i].getBuilding_point2().equals(arr_dijkstraTable[j].getCurr_Building())) {
								// if the shortest distance on(dijkstraTable array) for current building equal maxValue(infinity)
								if (arr_dijkstraTable[j].getShortestDistance() == Double.MAX_VALUE) {
									/* set its shortest distance: 
									 * 
									 *( distance between (curr_Building and next_Building) from (route array) + Zero )
									 * 
									 */
									arr_dijkstraTable[j].setShortestDistance(arr_Route[i].getDistance() + prev_distance);
									arr_dijkstraTable[j].setPrev_Building(arr_Route[i].getBuilding_point1());
								}
								else if (arr_dijkstraTable[j].getShortestDistance() > (arr_Route[i].getDistance() + prev_distance)) {
									arr_dijkstraTable[j].setShortestDistance(arr_Route[i].getDistance() + prev_distance);
									arr_dijkstraTable[j].setPrev_Building(arr_Route[i].getBuilding_point1());
								}
							}
						}
						
					}
				}
				
			}
			

		
	}

}
