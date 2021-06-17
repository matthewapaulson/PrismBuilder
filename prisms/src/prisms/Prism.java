package prisms;

import java.util.HashMap;
import java.util.LinkedList;

/** Prism parses a HashMap that represents a 2-D array and finds the distance until an edge is hit 
 * following the directions provided from the HashMap. -1 will be returned in an infinite loop. Starting direction is East.
 * This implementation is designed for quick traversing through large sparse matrices.
 */
public class Prism {
	private HashMap<String, Node> rowsAndCols = new HashMap<>();
	private Coordinates starting;
	private int maxX;
	private int maxY;
	
	
	/**
	 * Constructor
	 * 
	 * @param matrix
	 * 			A HashMap that maps Coordinates to directions (^,>,<,^). Other characters will be ignores.
	 * @param starting
	 * 			The starting Coordinates.
	 * @param bottomRight
	 * 			The bottom right edge Coordinates indicating the maximum x and y.
	 */
	public Prism(HashMap<Coordinates,Character> matrix, Coordinates starting, Coordinates bottomRight) {
		//The coordinates parameters are assumed to be valid.
		this.starting = starting;
		maxX = bottomRight.getX();
		maxY = bottomRight.getY();
		
		for (Coordinates coords: matrix.keySet()) {
			
			if (coords.getX() > maxX || coords.getX() < 0 || coords.getY() > maxY || coords.getY() < 0) { //invalid coordinates
				continue;
			}
			Direction dir;
			char arrow = matrix.get(coords);
			if (arrow == '>') {
				dir = Direction.East;
			} else if (arrow == '<') {
				dir = Direction.West;
			} else if (arrow == 'V') {
				dir = Direction.South;
			} else if (arrow == '^') {
				dir = Direction.North;
			} else { //invalid character
				continue;
			}
			
			
			Node xHead = rowsAndCols.get(coords.rowX());
			Node yHead = rowsAndCols.get(coords.colY());
			
			if (xHead == null) {
				xHead = new Node(coords.getY(),coords.getX(),dir,null,null);
				rowsAndCols.put(coords.rowX(), xHead);
			} else {
				addNode(xHead,coords.getY(),coords.getX(),dir,coords.rowX());
			}
			
			if (yHead == null) {
				yHead = new Node(coords.getX(),coords.getY(),dir,null,null);
				rowsAndCols.put(coords.colY(), yHead);
			} else {
				addNode(yHead,coords.getX(),coords.getY(),dir,coords.colY());
			}
			
		}
	}
	
	/**
	 * Helper function to add a node in order into the HashMap.
	*/
	public void addNode(Node nodeList, int valueToAdd, int rowOrColValue, Direction dir, String key) {
		if (nodeList.getValue() > valueToAdd) {
			Node newNode = new Node(valueToAdd,rowOrColValue,dir,null,nodeList);
			nodeList.setPrev(newNode);
			rowsAndCols.put(key,newNode);
		} else {
			while (nodeList.getValue() < valueToAdd) {
				if (nodeList.getNext() == null) {
					Node newNode = new Node(valueToAdd,rowOrColValue,dir,nodeList,null);
					nodeList.setNext(newNode);
					return;
				}
				nodeList = nodeList.getNext();
			}
			if (nodeList.getValue() != valueToAdd) { //Only add if not duplicate elements
				Node newNode = new Node(valueToAdd,rowOrColValue,dir,nodeList.getPrev(),nodeList);
				nodeList.setPrev(newNode);
			}
		}
		
		
	}
	

	/** Iterates through the nodes to find distance from start to an edge. If there's an infinite loop, it returns -1.*/
	public int traverse() {
		int count = 1; //count starts at 1 because the starting point is always counted
		//Initial direction is East
		Direction dir = Direction.East;
		//finding the first node, if there is one
		Node listNode = rowsAndCols.get(starting.colY());
		
		//If no first node
		if (listNode == null) {
			return maxX - starting.getX() + 1;
		}
		
		//Finding first node East of starting point (inclusive)
		while (listNode.getValue() < starting.getX()) {
			if (listNode.getNext() == null) {
				return maxX - starting.getX() + 1;
			}
			listNode = listNode.getNext();
		}

		count += listNode.getValue() - starting.getX();
		try {
		return count + traverseHelper(listNode);
		} catch (Exception e) { //If a node was traversed multiple times, meaning an infinite loop
			return -1;
		}
	}
	 
	/** This is a recursive helper function that goes through the nodes until an edge is hit or a node is repeated.*/
	public int traverseHelper(Node node) throws Exception {
		
		if (node.alreadyTraversed()) { //throws an exception if a node was traversed multiple times
			throw new Exception("Node was traversed twice");
		}
		node.traverse(); //marking it as visited
		
		Direction directionGoing = node.getDirection();
		Node newNode;
		switch (directionGoing) { 
		/*This finds the direction and if there's a node, it returns the distance between them and 
		then performs this function recursively on the next node. In the case of no nodes, it returns the distance to the edge.*/
			case East:
					newNode = rowsAndCols.get("Y" + node.getValue());
					if (newNode == null) { 
						return maxX - node.alternateValue();
					}
					while (newNode.getValue() != node.alternateValue()) {
						newNode = newNode.getNext();
						if (newNode == null) { //if no node East of the Node
							return maxX - node.alternateValue();
						}
					}
					newNode = newNode.getNext();
					if (newNode == null) { //if no node East of the Node
						return maxX - node.alternateValue() ;
					}
					while (newNode.getDirection() == node.getDirection()) {/*If it's coming from the same direction, 
						you can't compare alternateValue, so you have to just skip it*/
						newNode = newNode.getNext();
						if (newNode == null) { //if no node North of the Node
							return maxX - node.alternateValue();
						}
					}
					return newNode.getValue() - node.alternateValue() + traverseHelper(newNode);
			case West:
					newNode = rowsAndCols.get("Y" + node.getValue());
					if (newNode == null) {
						return node.alternateValue();
					}
					while (newNode.getValue() != node.alternateValue()) {
						newNode = newNode.getNext();
						if (newNode == null) { //if no node West of the Node
							return node.alternateValue();
						}
					}
					newNode = newNode.getPrev();
					if (newNode == null) { //if no node West of the Node
						return node.alternateValue();
					}
					while (newNode.getDirection() == node.getDirection()) {/*If it's coming from the same direction, 
						you can't compare alternateValue, so you have to just skip it*/
							newNode = newNode.getPrev();
							if (newNode == null) { //if no node North of the Node
								return node.alternateValue();
							}
						}
					return node.alternateValue() - newNode.getValue() + traverseHelper(newNode);
			case South:
					newNode = rowsAndCols.get("X" + node.getValue());
					if (newNode == null) {	
						return maxY - node.alternateValue();
						
					}
					
					while (newNode.getValue() != node.alternateValue()) {
						newNode = newNode.getNext();
						if (newNode == null) { //if no node South of the Node
							return maxY - node.alternateValue();
						}
					}
					newNode = newNode.getNext();
					if (newNode == null) { //if no node South of the Node
						return maxY - node.alternateValue();
					}
					while (newNode.getDirection() == node.getDirection()) {/*If it's coming from the same direction, 
						you can't compare alternateValue, so you have to just skip it*/
							newNode = newNode.getNext();
							if (newNode == null) { //if no node North of the Node
								return maxY - node.alternateValue();
							}
						}
					return newNode.getValue() - node.alternateValue() + traverseHelper(newNode);
			case North:
					
					newNode = rowsAndCols.get("X" + node.getValue());	
					if (newNode == null) {
						return node.alternateValue();
					}
					
					while (newNode.getValue() != node.alternateValue()) {
						newNode = newNode.getNext();
						if (newNode == null) { //if no node North of the Node
							return node.alternateValue();
							
						}
					}
					newNode = newNode.getPrev();
					if (newNode == null) { //if no node North of the Node
						return node.alternateValue();
					}
					while (newNode.getDirection() == node.getDirection()) {/*If it's coming from the same direction, 
					you can't compare alternateValue, so you have to just skip it*/
						newNode = newNode.getPrev();
						if (newNode == null) { //if no node North of the Node
							return node.alternateValue();
						}
					}
					
					return node.alternateValue() - newNode.getValue() + traverseHelper(newNode);
			}
		throw new Exception("It should never reach here. Direction's enums have been checked.");
	}
	
	
	/** This is a tester function to see all the nodes*/
	private void printLists() {
		for (Node rAndC: rowsAndCols.values()) {
			int count = 0;
			do {
				System.out.println(rAndC.getValue() + " " + count++ + " " + rAndC.getDirection());
				rAndC = rAndC.getNext();
			} while (rAndC != null);
		}
	}
	
}
