package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveFirstStrategy implements DequeuingStrategy {

	////////////////// METODO ///////////////////////////////////////
	
	public List<Vehicle> dequeue(List<Vehicle> q) {
		System.out.println("ME HE CREADO    MOVEFIRSTSTRATEGY");
		List<Vehicle> nueva = new ArrayList<Vehicle>();
		nueva.add(q.get(0));
		return nueva;
	}
}
