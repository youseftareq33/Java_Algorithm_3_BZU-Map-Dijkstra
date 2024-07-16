package D1_StroreData_LinkedList;

public class LinkedList<T> {
	
	//=== Attributes --------------------------------------------------------
    private Node<T> head;
    
    
    //=== Methods -----------------------------------------------------------
    
    // insert at head
    public void insertAtHead(T data) {
        Node<T> newnode = new Node<>(data);
        if(head== null) {
            head = newnode;
        } else {
 
            newnode.setNext(head);
            head = newnode;
        }

    }
    /////////////////////////////////////
    
    // insert at last
    public void insertLast(T data) {
        Node<T> newnode = new Node<T>(data);
        
        if(head==null) {
        	head = newnode;
        }
        else {
            Node<T> curr = head;
            while (curr.getNext() != null) {
                curr = curr.getNext();
            }
            curr.setNext(newnode);
        }
    }
    /////////////////////////////////////
    
    // delete specific data
    public void deleteSpecificData(T data) {
        Node<T> curr = head;
        Node<T> prev = null;
        while (curr != null) {
            if (curr.getData().equals(data)) { // found
                if (prev == null) { // case at head
                    head = curr.getNext();
                } else { // case at middle or last
                    prev.setNext(curr.getNext());
                }
                break;
            } // not found
            prev = curr;
            curr = curr.getNext();
        }
    }
    /////////////////////////////////////
    
    // delete 
    public void deleteAtHead() {
        if (head != null) {
            head = head.getNext();
        }
    }
    /////////////////////////////////////
    
    // search
    public boolean search(T data) {
        Node<T> curr = head;

        while (curr != null) {
            if (curr.getData().equals(data)) {
                return true; // found
            } else {
                curr = curr.getNext();
            }
        }
        return false; // not found
    }
    /////////////////////////////////////
    
    // is Empty
    public boolean isEmpty() {
    	if(head==null) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
/////////////////////////////////////
    public T removeFirst() {
        if (head == null) {
           System.out.println("linked list is empty");
        }

        T data = head.getData();
        head = head.getNext();

        return data;
    }
    /////////////////////////////////////
    
    // clear
    public void clear() {
    	if(head!=null) {
    		head=null;
    	}
    	
    }
    /////////////////////////////////////
    
    // return size of linked list
    public int size() {
    	Node<T> curr=head;
    	int size=0;
    	while(curr!=null) {
    		size++;
    		curr=curr.getNext();
    	}
    	
    	return size;
    }
    /////////////////////////////////////
    
    // to String
    @Override
    public String toString() {
    	String res="";
    	Node<T> curr=head;
    	
    	while(curr!=null) {
    		if (curr.getNext()!=null) {
    			res+= curr.getData()+" --> ";
            } else {
            	res+= curr.getData();
            }
    		curr=curr.getNext();
    	}
    	
    	return res;
    }
    /////////////////////////////////////
    
    // print data
    public void printData() {
        Node<T> curr=head;
        
        while (curr!=null) {
            if (curr.getNext()!=null) {
                System.out.print(curr.getData()+" --> ");
            } else {
                System.out.print(curr.getData()+"\n");
            }
            curr = curr.getNext();
        }
    }

    
    public void printVisitedBuildingData() {
            Node<T> curr=head;
            
            while (curr!=null) {
                if (curr.getNext()!=null) {
                    System.out.print(curr.getData()+",");
                } else {
                    System.out.print(curr.getData()+"\n");
                }
                curr = curr.getNext();
            }
    }
    


}
