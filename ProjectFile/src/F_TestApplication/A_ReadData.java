package F_TestApplication;

import java.io.File;
import java.util.Scanner;

import B_Graph.*;
import C_DijkstraAlgorithm.Dijkstra;
import D2_StoreData_Queue.Queue;


public class A_ReadData {

	static Building arr_Building[];
	static Route arr_Route[];
	static Dijkstra arr_dijkstraTable[];
	//static Queue<String> queue_building=new Queue<>();
	
	public static void main(String[] args) {
		
		File file=new File("src\\D4_StoreData_FileData\\Data1.txt");
		
		readFile(file);
		
		printBuildingData();
		System.out.println("------------------------------------"+"\n");
		printRouteData();
		System.out.println("------------------------------------"+"\n");
		printDijkstraTable();
		

	}
	

	
	//-- Read Data from file
	public static void readFile(File file) {
		
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

		// print data
		public static void printBuildingData() {
			System.out.println("Building: "+"\n");
			for(int i=0;i<arr_Building.length;i++) {
				System.out.println("Building("+(i+1)+"): "+arr_Building[i].toString());
			}
		}

		public static void printRouteData() {
			System.out.println("Route: "+"\n");
			for(int i=0;i<arr_Route.length;i++) {
				System.out.println("Route("+(i+1)+"): "+arr_Route[i].toString());
			}
		}

		public static void printDijkstraTable() {
			System.out.println("Dijkstra Table: "+"\n");
			
			System.out.println("current Building"+"         "+"shortest distance"+"          "+"previous Building");
			for(int i=0;i<arr_dijkstraTable.length;i++) {
				System.out.println("      "+arr_dijkstraTable[i].toString());
			}
		}
	

}
