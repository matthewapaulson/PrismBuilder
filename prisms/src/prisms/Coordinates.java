package prisms;

/** Coordinates represents an immutable set of x and y ints representing a point in a 2-D array.
 */
public class Coordinates {
	
	private int xCoord;
	private int yCoord;
	
	/**
	 * @param xCoord
	 * 		The x coordinate
	 * @param yCoord
	 * 		The y coordinate
	 */
	public Coordinates(int xCoord, int yCoord) {
		this.xCoord = xCoord;
		this.yCoord = yCoord;
	}
	
	/**
	 * @return
	 * 		returns the x coordinate.
	 */
	public int getX() {
		return xCoord;
	}

	/**
	 * @return
	 * 		returns the y coordinate.
	 */
	public int getY() {
		return yCoord;
	}
	/** @return
	 * 		returns X followed by the x coordinate and then Y followed by the y coordinate.
	 */
	public String toString() {
		return "X" + xCoord + "Y" + yCoord;
	}
	
	/** @return
	 * 		returns X followed by the x coordinate
	 */
	public String rowX() {
		return "X" + xCoord;
	}
	
	/** @return
	 * 		returns Y followed by the y coordinate
	 */
	public String colY() {
		return "Y" + yCoord;
	}

}

