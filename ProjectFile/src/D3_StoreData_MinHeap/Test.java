package D3_StoreData_MinHeap;

public class Test {

	public static void main(String[] args) {
		MinHeap<String> m_heap= new MinHeap<>();
		
		m_heap.insert("A", 5);
		m_heap.insert("B", 3);
		m_heap.insert("C", 4);
		m_heap.insert("D", 8);
		m_heap.insert("E", 12);
		
		m_heap.printData();
		
		System.out.println(m_heap.extractMin());
		System.out.println(m_heap.extractMin());
	}

}
