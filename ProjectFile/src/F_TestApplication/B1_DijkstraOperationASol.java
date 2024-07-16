package F_TestApplication;

import java.io.File;
import java.util.Scanner;

import B_Graph.Building;
import B_Graph.Route;
import C_DijkstraAlgorithm.Dijkstra;
import D1_StroreData_LinkedList.LinkedList;
import D2_StoreData_Queue.Queue;
import D3_StoreData_MinHeap.MinHeap;

public class B1_DijkstraOperationASol {
	static Building arr_Building[];
	static Route arr_Route[];
	static Dijkstra arr_dijkstraTable[];
	static Queue<Route> queue_chossenPointRouteList=new Queue<>();
	static MinHeap<String> minHeap_distance=new MinHeap<>();
	static LinkedList<String> linkedlist_visitedBuilding=new LinkedList<>(); 
	
	public static void main(String[] args) {
		File file=new File("src\\D4_StoreData_FileData\\Data3.txt");
		
		readFile(file);
		
		
		String source="B";
		String destination="D";
		
		
		runDijkstraAlgorithm(source, destination);
		
		
		

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
		
				System.out.println("1) Initalize table according the source: "+"\n");
				printDijkstraTable();
				System.out.println("==========================================================================");
			
			//********************* set cursor position for (source and destination) *********************
				
				System.out.println("2) set correct cursor position for source and destination: "+"\n");
				System.out.println("Route (before) change cursor position: "+"\n");
				printRouteData();
				
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
			
				System.out.println("---------------------------------------------------------------------");
				System.out.println("\n"+"Route (after) change cursor position if any cursor have uncorrect position: "+"\n");
				printRouteData();
				System.out.println("==========================================================================");

			//************************************ set Dijkstra Table ************************************
				
				System.out.println("3) set Dijkstra Table steps: "+"\n");
				
				//insert source to the heap.
				minHeap_distance.insert(chossenPoint, prev_distance);
				
				System.out.println("step(1): insert source to the minHeap: "+"\n");
				System.out.println("minHeap Data: "+"\n");
				minHeap_distance.printData();
				System.out.println("---------------------------------------------------------------------");
				
				System.out.println("step(2): enter to while loop that will still loop till minHeap is:"+"\n"+
						  "1) empty"+"\n"+
						  "2) or get the destination and it have the high priority then break"+"\n");
				System.out.println("inside the loop: "+"\n");
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
					System.out.println("1- enqueue all the routes that chossenPoint Building can go to it on (queue_chossenPointRouteList): "+"\n");
					System.out.println("     "+"1) if cursor reference in correct position and the next building dosen't visited then --> enqueue it.");
					System.out.println("     "+"2) if cursor reference in false position and the next building dosen't visited then --> fix cursor then enqueue it."+"\n");
					System.out.println("          "+"queue Data ("+chossenPoint+") can go to : ");
					System.out.println("          "+queue_chossenPointRouteList.toString());
					
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
					
					System.out.println("\n"+"2- set data after processing inside Dijkstra table."+"\n"+
					           " processing steps:");
					System.out.println("     "+"1) compare shortest distance between:"+"\n"+
									   "          "+ " curr_Building (dijkstraTable array)"+"\n"+
									   "          "+ " and front of queue(that we get it from route table) + previous distance(between point1 and point 2) "+"\n\n"+
									   "     "+"if front of queue and prev is small than curr bulding then update it on dijkstra table"+"\n");
					printDijkstraTable();
					System.out.println("-------------------------------------");
					System.out.println("     "+"2) set data in minHeap:"+"\n"+
									   "          "+"if this data do not exist in minHeap insert it."+"\n"+
									   "          "+"if this data exist in minHeap just the priority will updated."+"\n");
					System.out.println("minHeap Data: "+"\n");
					minHeap_distance.printData();
					System.out.println("---------------------------------------------------------------------"+"\n");
					
					
					//--------------------------------
					//-3- set the next point we should be in it
					// remove the high priority node
					
					System.out.println("3- set next point we should in by these steps:"+"\n"+
							           "     "+"1)insert the high priority to visited Building(linkedlist_visitedBuilding) "+"\n"+
							           "     "+"2)extract(remove) the high priority"+"\n"+
							           "     "+"3)Choose next high priority"+"\n"+
							           "           "+"if chossenPoint equal destination then --> break (cause we find the way) "+"\n"+
							           "           "+"if chossenPoint dosen't equal destination then --> find the previous shortest distance(Dijkstra Table) to this chossenPoint"+"\n");
					
					// insert the high priority to visited Building
					linkedlist_visitedBuilding.insertAtHead(chossenPoint);
					
					System.out.println("linked list data (Visited Building): ");
					linkedlist_visitedBuilding.printData();
					
					// extract(remove) the high priority
					minHeap_distance.extractMin();
					System.out.println("\n"+"minHeap Data:");
					minHeap_distance.printData();
					
					// Choose next priority node
					chossenPoint=minHeap_distance.getMin();
					
					// if this node who was chosen is the destination then break
					if(chossenPoint.equals(destination)) {
						System.out.println("u reached the destination.");
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
						System.out.println("\n"+"next Building: ("+chossenPoint+") and its previous distance is: "+prev_distance);
					}
					
					System.out.println("=====================**************************=============================");
				}

			System.out.println("#####################################################################################"+"\n");
			System.out.println("Solution: ");
			printDijkstraTable();


	}

}
