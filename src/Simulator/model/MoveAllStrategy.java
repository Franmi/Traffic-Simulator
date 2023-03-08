package simulator.model;

import java.util.ArrayList;
import java.util.List;

public class MoveAllStrategy implements DequeuingStrategy {
	
	////////////////// METODO ///////////////////////////////////////
	
	public List<Vehicle> dequeue(List<Vehicle> q){
		System.out.println("ME HE CREADO    MOVEALLSTRATEGY");
		List<Vehicle> nueva = new ArrayList<Vehicle>();
		nueva.addAll(q);
		return nueva;
	}
}
