package D2_StoreData_Queue;

import B_Graph.Building;

public class Queue_Test {
	public static void main(String[] args) {
		Queue q=new Queue();
		
		q.enqueue(new Building(null, 0, 0));
		q.enqueue("B");
		q.enqueue("C");
		
		
//		q.dequeue();
//		q.dequeue();
		
		
		//System.out.println(q.getFront().toString());
		
		System.out.println(q.defPrintData());
	}
}
