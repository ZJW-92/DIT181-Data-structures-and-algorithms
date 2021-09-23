
class SinglyLinkedList<Item> {

    private int size;
    private Node<Item> head;

    // -----------------------------------------------------------------------------------
    /**
     * ----------------- Helper methods -------------------------------------
     */
    /**
     * Linked list node helper data type
     *
     * @param <Item>
     */
    private static class Node<Item> {

        Node<Item> next = null;
        Item el = null;

    }

    public int size() {
        return size;
    }

    // -----------------------------------------------------------------------------------
    /**
     * ----------------- ToDo: methods for you to implement
     * ---------------------------
     *
     */
    // -----------------------------------------------------------------------------------
    /**
     * Constructor of an empty Singly linked list
     *
     */
    public SinglyLinkedList() {
    	
    }

    /**
     * Method to get an item from a specified index
     *
     * @param index of the element to return
     * @return the item from the specified index
     */
    public Item get(int index) {

		if (index < 0 || index >= size) {
			throw new IllegalArgumentException("The index is out of boundtries");
		}
		Node<Item> present = head.next;
		for (int i = 0; i < index; i++) {
			present = present.next;
		}
		return present.el;
	}
    
    /**
     * Method to insert element newElement at specified index in the list
     *
     * @param index where you want to insert new element
     * @param newElement the object of type Item you want to insert
     */
    public void insertAt(int index, Item newElement) {
		
		if (index < 0 || index > size) {
			throw new IllegalArgumentException("The index is out of boundaries");
		}
		Node<Item> present = head;
		for (int i = 0; i < index; i++) {
			present = present.next;
		}
		Node<Item> newNode = new Node<Item>();
		newNode.el = newElement;
		newNode.next = present.next;
		present.next= newNode;
		
		size++;
	}

    /**
     * Method to remove the element at the specified index from the list
     *
     * @param index the position to remove the item from
     */
    public void removeAt(int index) {
		
		if (index < 0 || index >= size) {
			throw new UnsupportedOperationException("The index is out of boundaries");
		}

		Node<Item> present = head;
		for (int i = 0; i < size; i++) {
			present = present.next;
		}
		if (present.next.next == null) {
			present.next = null;
		} else {
			present.next = present.next.next;
		}
		size--;
	}
    
    /**
     * Reverse the list
     *
     */
    public void reverse() {
		Node <Item> reversed = null;
	    Node<Item> present = head.next;
		
		while( present != null) {
			Node<Item> next = present.next;
			present.next = reversed;
			reversed= present;
			present = next; 
		}
		    head.next= reversed;
	}

    public static void main(String[] args) {
        SinglyLinkedList<Integer> testList = new SinglyLinkedList<>();
        System.out.println(testList.size());
    }
}
