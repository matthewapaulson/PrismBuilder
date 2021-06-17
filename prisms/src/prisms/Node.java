package prisms;

/** Node represents a doubly linked list.
 */
public class Node {
	private int compareValue;
	private int valueOfRowOrCol;
	private Node next;
	private Node prev;
	private boolean alreadyTraversed = false;
	private Direction dir;
	
	/**
	 * @param value
	 * 		The value that will be compared.
	 * @param alternateValue
	 * 		The value of the row or column that this node is currently in.
	 * @param dir
	 * 		The direction this node is marked with.
	 * @param prev
	 * 		The node previous to the current one.
	 * @param next
	 * 		The node following the current one.
	 */
	public Node(int value, int alternateValue, Direction dir, Node prev, Node next) {
		compareValue = value;
		this.valueOfRowOrCol = alternateValue;
		this.next = next;
		this.prev = prev;
		this.dir = dir;
	}
	/**
	 * @return
	 * 		Returns the main value.
	 */
	public int getValue() {
		return compareValue;
	}
	
	/**
	 * @return
	 * 		Returns the row or column it's part of.
	 */
	public int getRowOrColValue() {
		return valueOfRowOrCol;
	}
	
	/**
	 * @return
	 * 		Returns if it's been traversed yet.
	 */
	public boolean alreadyTraversed() {
		return alreadyTraversed;
	}
	
	/** This marks the node as being traversed.
	 */
	public void traverse() {
		alreadyTraversed = true;
	}
	/**
	 * @return
	 * 		Returns the dirextion.
	 */
	public Direction getDirection() {
		return dir;
	}
	
	/**
	 * @param prev
	 * `	Previous node
	 */
	public void setPrev(Node prev) {
		this.prev = prev;
	}
	
	/**
	 * @param next
	 * `	Next node
	 */
	public void setNext(Node next) {
		this.next = next;
	}
	
	/**
	 * @return
	 * 		Returns the next node
	 */
	public Node getNext() {
		return next;
	}
	
	/**
	 * @return
	 * 		Returns the previous node
	 */
	public Node getPrev() {
		return prev;
	}
	
	/** Tester function to print all the nodes in the list.
	 */
	public void printNodeValues() {
		Node current = this;
		while (current != null) {
			System.out.print(current.getValue() + " ");
			current = current.next;
		}
		System.out.println();
	}
}
