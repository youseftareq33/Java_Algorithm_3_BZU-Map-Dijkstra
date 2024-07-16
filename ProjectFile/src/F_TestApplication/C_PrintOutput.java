package F_TestApplication;

import java.io.File;
import java.util.Scanner;

import B_Graph.Building;
import B_Graph.Route;
import C_DijkstraAlgorithm.Dijkstra;
import D1_StroreData_LinkedList.LinkedList;
import D2_StoreData_Queue.Queue;
import D3_StoreData_MinHeap.MinHeap;

public class C_PrintOutput {
	static Building arr_Building[];
	static Route arr_Route[];
	static Dijkstra arr_dijkstraTable[];
	static Queue<Route> queue_chossenPointRouteList=new Queue<>();
	static MinHeap<String> minHeap_distance=new MinHeap<>();
	static LinkedList<String> linkedlist_visitedBuilding=new LinkedList<>(); 
	static LinkedList<String> linkedlist_shortestPath=new LinkedList<>();
	
	public static void main(String[] args) {
		
		File file=new File("src\\D4_StoreData_FileData\\Data1.txt");
		
		readFile(file);
		
		
		String source="B";
		String destination="C";
		
		
		startDijkstraAlgorithm(source, destination);
		
		System.out.println("shortest Path : "+shortestPath(source,destination));
		
		System.out.println("shortest distance: "+shortestDistance(destination));
		
//		double number = 4.000000000000000;
//        String numberAsString = Double.toString(number);
//        String decimalDigits = numberAsString.substring(numberAsString.indexOf('.') + 1);
//        double after_decimalPoint=Double.parseDouble(decimalDigits);
//        if(after_decimalPoint==0) {
//        	System.out.println("kkk");
//        }
//        else {
//        	System.out.println("mmmmmmmmmm");
//        }

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

	public static void startDijkstraAlgorithm(String source, String destination) {
			
			String switch_building="";
			String chossenPoint=source;
			double prev_distance=0;
			
				//********************* initialize the source Building in Dijkstra Table *********************
			
					for(int i=0;i<arr_dijkstraTable.length;i++) {
						// if we find the curr_Building (dijkstraTable array), that have the same name with source.
						if(arr_dijkstraTable[i].getCurr_Building().equals(source)) {
							//set the shortest distance zero to the source.
							arr_dijkstraTable[i].setShortestDistance(prev_distance);
							break;
						}
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

	
	
	public static String shortestPath(String source, String destination) {
		// back tracking from destination till source
		for(int i=0;i<arr_dijkstraTable.length;i++) {
			for(int j=0;j<arr_dijkstraTable.length;j++) {
				
				if(arr_dijkstraTable[j].getCurr_Building().equals(destination)) {
					linkedlist_shortestPath.insertAtHead(arr_dijkstraTable[j].getCurr_Building());
					
						destination=arr_dijkstraTable[j].getPrev_Building();
				}
				
			}
		}
		
		return linkedlist_shortestPath.toString();
	}
	
	

	public static double shortestDistance(String destination) {
		
		double shortestDistance=0;
		
		for(int i=0;i<arr_dijkstraTable.length;i++) {
			if(arr_dijkstraTable[i].getCurr_Building().equals(destination)) {
				shortestDistance =arr_dijkstraTable[i].getShortestDistance();
				break;
			}
		}	
		
		return shortestDistance;
	}
	
	
}
