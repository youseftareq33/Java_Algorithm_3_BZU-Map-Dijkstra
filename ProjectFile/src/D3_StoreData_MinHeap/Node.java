package D3_StoreData_MinHeap;

public class Node<T> {
	
	//=== Attributes --------------------------------------------------------
    private T value;
    private double priority;

    
    //=== Constructor --------------------------------------------------------
    public Node(T value, double priority) {
        this.value = value;
        this.priority = priority;
    }


    
    //=== Getter and Setter --------------------------------------------------
    public T getValue() {
        return value;
    }
    public void setValue(T value) {
    	this.value=value;
    }
    /////////////////////////////////////
    public double getPriority() {
        return priority;
    }
	public void setPriority(double priority) {
		this.priority = priority;
	}
    
    
	
}
