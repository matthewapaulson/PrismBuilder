package prisms;

import java.util.HashMap;

public class PrismBuilder {
	HashMap<Coordinates,Character> matrix;
	Coordinates starting;
	Coordinates bottomRight;

	
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
	public PrismBuilder(HashMap<Coordinates,Character> matrix, Coordinates starting, Coordinates bottomRight) {
		this.matrix = matrix;
		this.starting = starting;
		this.bottomRight = bottomRight;
	}
	
	/* This function goes through the matrix HashMap and builds an appropriate sparse matrix with the correct directions*/
	public Prism build() {
		HashMap<String, Node> rowsAndCols = new HashMap<>();
		int maxX = bottomRight.getX();
		int maxY = bottomRight.getY();
		
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
				addNode(xHead,coords.getY(),coords.getX(),dir,coords.rowX(),rowsAndCols);
			}
			
			if (yHead == null) {
				yHead = new Node(coords.getX(),coords.getY(),dir,null,null);
				rowsAndCols.put(coords.colY(), yHead);
			} else {
				addNode(yHead,coords.getX(),coords.getY(),dir,coords.colY(),rowsAndCols);
			}
			
		}
		
		
		return new Prism(rowsAndCols,starting,maxX,maxY);
	}
	
	/**
	 * Helper function to add a node in order into the HashMap.
	*/
	public void addNode(Node nodeList, int valueToAdd, int rowOrColValue, Direction dir, String key, HashMap<String, Node> rowsAndCols) {
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
}
