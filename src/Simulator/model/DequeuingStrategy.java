package simulator.model;

import java.util.List;

public interface DequeuingStrategy {
	
	////////////////// METODO //////////////////////////////////////////
	
	List<Vehicle> dequeue(List<Vehicle> q);
}
