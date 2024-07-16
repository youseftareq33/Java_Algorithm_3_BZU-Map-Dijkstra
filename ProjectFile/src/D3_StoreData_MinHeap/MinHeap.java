package D3_StoreData_MinHeap;

import java.util.ArrayList;

public class MinHeap<T> {
	
	//=== Attributes --------------------------------------------------------
    private ArrayList<Node<T>> heap;

    
    //=== Constructor --------------------------------------------------------
    public MinHeap() {
        heap = new ArrayList<>();
    }


    
    //=== Methods -----------------------------------------------------------
    
    // insert
    public void insert(T value, double priority) {
        Node<T> node = new Node<>(value, priority);
        heap.add(node);
        siftUp(heap.size() - 1);
    }
    /////////////////////////////////////
    
    // delete
    public void delete(T value) {
        int index = -1;
        for (int i = 0; i < heap.size(); i++) {
            if (heap.get(i).getValue().equals(value)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            return; // Item not found in the heap
        }

        Node<T> lastNode = heap.remove(heap.size() - 1);

        if (index < heap.size()) {
            heap.set(index, lastNode);
            siftUp(index);
            siftDown(index);
        }
    }
    /////////////////////////////////////
    
    // extract minimum data
    public T extractMin() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

        Node<T> minNode = heap.get(0);
        Node<T> lastNode = heap.remove(heap.size() - 1);

        if (!isEmpty()) {
            heap.set(0, lastNode);
            siftDown(0);
        }

        return minNode.getValue();
    }
    /////////////////////////////////////
    
    // used in insert and delete and extract
    private void siftUp(int index) {
        int parentIndex = (index - 1) / 2;

        while (index > 0 && heap.get(index).getPriority() < heap.get(parentIndex).getPriority()) {
            swap(index, parentIndex);
            index = parentIndex;
            parentIndex = (index - 1) / 2;
        }
    }
    
    private void siftDown(int index) {
        int leftChildIndex = 2 * index + 1;
        int rightChildIndex = 2 * index + 2;
        int smallestIndex = index;

        if (leftChildIndex < heap.size() && heap.get(leftChildIndex).getPriority() < heap.get(smallestIndex).getPriority()) {
            smallestIndex = leftChildIndex;
        }

        if (rightChildIndex < heap.size() && heap.get(rightChildIndex).getPriority() < heap.get(smallestIndex).getPriority()) {
            smallestIndex = rightChildIndex;
        }

        if (smallestIndex != index) {
            swap(index, smallestIndex);
            siftDown(smallestIndex);
        }
    }
    
    private void swap(int i, int j) {
        Node<T> temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
    /////////////////////////////////////
    
    // return size of heap
    public int size() {
        return heap.size();
    }
    /////////////////////////////////////
    
    // return if its empty
    public boolean isEmpty() {
        return heap.isEmpty();
    }
    /////////////////////////////////////
    
    // find specific data
    public boolean find(String target) {
        for (Node<T> node : heap) {
            if (node.getValue().equals(target)) {
                return true;
            }
        }
        return false;
    }
    /////////////////////////////////////
    
    // update priority
    public void updatePriority(String target, double newPriority) {
        for (Node<T> node : heap) {
            if (node.getValue().equals(target)) {
                node.setPriority(newPriority);
                // Perform sift-up and sift-down operations to maintain the heap property
                int index = heap.indexOf(node);
                siftUp(index);
                siftDown(index);
                return;
            }
        }
    }
    /////////////////////////////////////
    
    // get minimum value
    public T getMin() {
        if (isEmpty()) {
            throw new IndexOutOfBoundsException("Heap is empty");
        }

        Node<T> minNode = heap.get(0);
        return minNode.getValue();
    }
    
    /////////////////////////////////////
    
    // clear the heap
    public void clear() {
    	
        heap.clear();
    }
    /////////////////////////////////////
    
    // print data
    public void printData() {
    	if(isEmpty()) {
    		System.out.println("min Heap is empty");
    	}
    	else {
    		for (Node<T> node : heap) {
                System.out.println("Value: " + node.getValue() + ", Priority: " + node.getPriority());
            }
    	}
    }


    
}
