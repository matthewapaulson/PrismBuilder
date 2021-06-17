package prisms;

import java.util.HashMap;
/** This is a driver class for Prism. It creates sample data to operate on.
 */
public class Main {

	public static void main(String[] args) {
		Coordinates bottomRight = new Coordinates(8,7);
		Coordinates startingPoint = new Coordinates (2,3);
		
		HashMap<Coordinates,Character> matrix = new HashMap<>();
		matrix.put(new Coordinates(6,4),'V'); 
		matrix.put(new Coordinates(6,3),'V'); 
		matrix.put(new Coordinates(6,6),'<'); 
		matrix.put(new Coordinates(5,6),'^');
		matrix.put(new Coordinates(5,5),'<');
		matrix.put(new Coordinates(4,5),'V');
		matrix.put(new Coordinates(4,6),'<'); 
		matrix.put(new Coordinates(0,6),'^'); 
		matrix.put(new Coordinates(0,1),'^'); 
		matrix.put(new Coordinates(0,0),'>');
		matrix.put(new Coordinates(1,0),'>');
//		matrix.put(new Coordinates(2,0),'>'); 
		matrix.put(new Coordinates(8,0),'V');
		
		Prism test = new Prism(matrix,startingPoint,bottomRight);
		System.out.println(test.traverse());

	}

}
