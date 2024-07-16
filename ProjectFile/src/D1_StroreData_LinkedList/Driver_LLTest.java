package D1_StroreData_LinkedList;

import B_Graph.Building;

public class Driver_LLTest {

	public static void main(String[] args) {
		LinkedList<Object> ll=new LinkedList<>();
		
		ll.insertLast("A");
		ll.insertLast("B");
		ll.insertLast("C");
		ll.insertLast("D");
		ll.insertLast("E");
		ll.insertLast("F");
		ll.insertLast(new Building(null, 0, 0));
		ll.printData();
		
		System.out.println("----------------------");
		
		ll.deleteAtHead();
		
		ll.printData();
		
		System.out.println("----------------------");
		
		System.out.println(ll.search("Z"));
		
		System.out.println("----------------------");
		
		System.out.println(ll.size());
//		ll.clear();
//		
//		ll.printData();
		
		//System.out.println(ll.getData());
		

	}

}
