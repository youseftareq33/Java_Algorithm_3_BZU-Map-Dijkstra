package D2_StoreData_Queue;

public class Queue<T> {
	
	//=== Attributes --------------------------------------------------------
	private Node<T> first; 
	private Node<T> last; 
	
	
	//=== Methods -----------------------------------------------------------
    
	// add data
	public void enqueue(T data) {
		Node<T> newNode = new Node<T>(data);
		if (isEmpty()) {
			first = newNode;
			last = newNode;
		} else {
			last.setNext(newNode);
			last = newNode;
		}
	}
    /////////////////////////////////////
	
	// delete data
	public T dequeue() {
		
		if(!isEmpty()) {
			T front = first.getData();
	        first = first.getNext();

	        if (first == null) {
	            last = null;
	        }

	        return front;
		}
		else {
			System.out.println("queue is empty");
			return null;
		}
	}
    /////////////////////////////////////

	// return the front data
	public T getFront() {
		
		if (!isEmpty()) {
			return first.getData();
		}
		else {
			return null;
		}
			
	}
    /////////////////////////////////////

	// the queue is empty or not
	public boolean isEmpty() {
		return (first == null) && (last == null);
	}
    /////////////////////////////////////

	// remove all entries in the queue
	public void clear() {
		first = null;
		last = null;
	}
    /////////////////////////////////////

	// return length of queue
	public int length() {
		int length = 0;
		Node<T> curr = first;

		while (curr != null) {
			length++;
			curr = curr.getNext();
		}

		return length;
	}
    /////////////////////////////////////

	// show data
	@Override
	public String toString() {
		Node<T> curr=first;
		
		String s="First";
		if(isEmpty()==true) {
			System.out.println("|"+"The Queue is Empty"+" |");
			return "";
		}
		
		else{
			while(curr != null) {
				s+=" --> "+curr.getData();
				curr=curr.getNext();
			}
			
			return "|"+s+" <-- Last|";
	}

}
    /////////////////////////////////////
	
	// default print
	public String defPrintData() {
		String res="";
		 if (isEmpty()) {
	            System.out.println("The Queue is Empty");
	        } else {
	            Node<T> curr = first;
	            while(curr!=null) {
	        		if (curr.getNext()!=null) {
	        			res+= curr.getData()+" --> ";
	                } else {
	                	res+= curr.getData();
	                }
	        		curr=curr.getNext();
	        	}
	        }
		 
		 return res;
	}
	/////////////////////////////////////
	// print Data
	public void printData() {
        if (isEmpty()) {
            System.out.println("| The Queue is Empty |");
        } else {
            Node<T> curr = first;
            StringBuilder sb = new StringBuilder("First");

            while (curr != null) {
                sb.append(" --> ").append(curr.getData());
                curr = curr.getNext();
            }

            System.out.println("|" + sb.toString() + " <-- Last|");
        }
    }
	
	
	
}